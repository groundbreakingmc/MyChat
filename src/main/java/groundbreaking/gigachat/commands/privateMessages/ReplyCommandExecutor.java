package groundbreaking.gigachat.commands.privateMessages;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.collections.*;
import groundbreaking.gigachat.utils.Utils;
import groundbreaking.gigachat.utils.config.values.Messages;
import groundbreaking.gigachat.utils.config.values.PrivateMessagesValues;
import groundbreaking.gigachat.utils.vanish.IVanishChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class ReplyCommandExecutor implements CommandExecutor, TabCompleter {

    private final GigaChat plugin;
    private final PrivateMessagesValues pmValues;
    private final Messages messages;
    private final IVanishChecker vanishChecker;
    private final ConsoleCommandSender consoleSender;
    private final Cooldowns cooldowns;

    private final String[] placeholders = { "{from-prefix}", "{from-name}", "{from-suffix}", "{to-prefix}", "{to-name}", "{to-suffix}" };

    public ReplyCommandExecutor(final GigaChat plugin) {
        this.plugin = plugin;
        this.pmValues = plugin.getPmValues();
        this.messages = plugin.getMessages();
        this.vanishChecker = plugin.getVanishChecker();
        this.consoleSender = plugin.getServer().getConsoleSender();
        this.cooldowns = plugin.getCooldowns();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage(this.messages.getPlayerOnly());
            return true;
        }

        if (!sender.hasPermission("gigachat.command.reply")) {
            sender.sendMessage(this.messages.getNoPermission());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(this.messages.getReplyUsageError());
            return true;
        }

        final String senderName = sender.getName();
        if (this.hasCooldown(playerSender, senderName)) {
            this.sendMessageHasCooldown(playerSender, senderName);
            return true;
        }

        final String recipientName = Reply.getRecipientName(senderName);

        if (recipientName == null) {
            sender.sendMessage(this.messages.getNobodyToAnswer());
            return true;
        }

        final Player recipient = Bukkit.getPlayer(recipientName);

        if (recipient == null || !playerSender.canSee(recipient) || this.vanishChecker.isVanished(recipient)) {
            sender.sendMessage(this.messages.getPlayerNotFound());
            return true;
        }

        if (!sender.hasPermission("gigachat.bypass.ignore.private")) {
            if (isIgnored(recipientName, senderName)) {
                sender.sendMessage(this.messages.getRecipientIgnoresSender());
                return true;
            }

            if (isIgnored(senderName, recipientName)) {
                sender.sendMessage(this.messages.getSenderIgnoresRecipient());
                return true;
            }
        }


        final String senderPrefix = this.getPrefix(playerSender);
        final String senderSuffix = this.getSuffix(playerSender);
        final String recipientPrefix = this.getPrefix(recipient), recipientSuffix = getSuffix(recipient);

        final String[] replacementList = { senderPrefix, senderName, senderSuffix, recipientPrefix, recipientName, recipientSuffix};
        final String formattedMessageForSender, formattedMessageForRecipient, formattedMessageForConsole, formattedMessageForSocialSpy;

        final String message = this.getMessage(playerSender, args);
        formattedMessageForSender = this.getFormattedMessage(playerSender, message, this.pmValues.getSenderFormat(), replacementList);
        formattedMessageForRecipient = this.getFormattedMessage(playerSender, message, this.pmValues.getRecipientFormat(),replacementList);
        formattedMessageForConsole = this.getFormattedMessage(playerSender, message, this.pmValues.getConsoleFormat(),replacementList);
        formattedMessageForSocialSpy = this.getFormattedMessage(playerSender, message, this.pmValues.getSocialSpyFormat(),replacementList);

        Reply.add(recipientName, senderName);
        this.processLogs(formattedMessageForConsole);
        SocialSpy.sendAll(playerSender, recipient, formattedMessageForSocialSpy);

        sender.sendMessage(formattedMessageForSender);
        recipient.sendMessage(formattedMessageForRecipient);

        this.playSound(recipient);

        return true;
    }

    private boolean hasCooldown(final Player playerSender, final String senderName) {
        return this.cooldowns.hasCooldown(playerSender, senderName, "gigachat.bypass.cooldown.socialspy", this.cooldowns.getPrivateCooldowns());
    }

    private void sendMessageHasCooldown(final Player playerSender, final String senderName) {
        final long timeLeftInMillis = this.cooldowns.getPrivateCooldowns().get(senderName) - System.currentTimeMillis();
        final int result = (int) (this.pmValues.getPmCooldown() / 1000 + timeLeftInMillis / 1000);
        final String restTime = Utils.getTime(result);
        final String message = this.messages.getCommandCooldownMessage().replace("{time}", restTime);
        playerSender.sendMessage(message);
    }

    private boolean isIgnored(final String ignoredName, final String ignoringName) {
        return Ignore.isIgnoredPrivate(ignoringName, ignoredName);
    }

    private String getPrefix(final Player player) {
        return this.plugin.getChat().getPlayerPrefix(player);
    }

    private String getSuffix(final Player player) {
        return this.plugin.getChat().getPlayerSuffix(player);
    }

    public String getFormattedMessage(final Player player, final String message, final String format, final String[] replacementList) {
        final String formatted = this.pmValues.getMessagesColorizer().colorize(
                Utils.replacePlaceholders(
                        player,
                        Utils.replaceEach(format, this.placeholders, replacementList)
                )
        );

        return formatted.replace("{message}", message).replace("%", "%%");
    }

    private String getMessage(final Player sender, final String[] args) {
        return this.pmValues.getMessagesColorizer().colorize(sender, String.join(" ", args).trim() );
    }

    private void playSound(final Player recipient) {
        final Sound sound = PmSounds.getSound(recipient.getName());
        if (this.pmValues.isSoundEnabled()) {
            final Location recipientLocation = recipient.getLocation();
            final float volume = this.pmValues.getSoundVolume();
            final float pitch = this.pmValues.getSoundPitch();
            recipient.playSound(recipientLocation, sound, volume, pitch);
        }
    }

    private void processLogs(final String formattedMessage) {
        this.consoleSender.sendMessage(formattedMessage);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
