package groundbreaking.gigachat.commands.other;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.collections.Cooldowns;
import groundbreaking.gigachat.utils.Utils;
import groundbreaking.gigachat.utils.config.values.BroadcastValues;
import groundbreaking.gigachat.utils.config.values.Messages;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class BroadcastCommand implements CommandExecutor, TabCompleter {

    private final GigaChat plugin;
    private final BroadcastValues broadcastValues;
    private final Messages messages;
    private final Cooldowns cooldowns;
    private final ConsoleCommandSender consoleCommandSender;

    private final String[] placeholders = { "{player}", "{prefix}", "{suffix}", "{message}" };

    public BroadcastCommand(final GigaChat plugin) {
        this.plugin = plugin;
        this.broadcastValues = plugin.getBroadcastValues();
        this.messages = plugin.getMessages();
        this.cooldowns = plugin.getCooldowns();
        this.consoleCommandSender = plugin.getServer().getConsoleSender();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("gigachat.command.broadcast")) {
            sender.sendMessage(this.messages.getNoPermission());
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(this.messages.getBroadcastUsageError());
            return true;
        }

        final boolean isPlayerSender = sender instanceof Player;
        if (isPlayerSender) {
            final Player playerSender = (Player) sender;
            final String senderName = playerSender.getName();
            if (this.hasCooldown(playerSender, senderName)) {
                this.sendMessageHasCooldown(playerSender, senderName);
                return true;
            }
        }

        final List<Player> recipients = new ArrayList<>(Bukkit.getOnlinePlayers());
        final String message = this.getMessage(sender, args, isPlayerSender);

        if (isPlayerSender && this.broadcastValues.isHoverEnabled()) {
            sendHover((Player) sender, message, recipients);
        } else {
            for (int i = 0; i < recipients.size(); i++) {
                recipients.get(i).sendMessage(message);
            }
        }

        if (isPlayerSender) {
            this.cooldowns.addCooldown(sender.getName(), this.cooldowns.getBroadcastCooldowns());
        }

        this.consoleCommandSender.sendMessage(message);

        return true;
    }

    private boolean hasCooldown(final Player playerSender, final String senderName) {
        return this.cooldowns.hasCooldown(playerSender, senderName, "gigachat.bypass.cooldown.broadcast", cooldowns.getBroadcastCooldowns());
    }

    private void sendMessageHasCooldown(final Player playerSender, final String senderName) {
        final long timeLeftInMillis = this.cooldowns.getBroadcastCooldowns().get(senderName) - System.currentTimeMillis();
        final int result = (int) (this.broadcastValues.getCooldown() / 1000 + timeLeftInMillis / 1000);
        final String restTime = Utils.getTime(result);
        final String message = this.messages.getCommandCooldownMessage().replace("{time}", restTime);
        playerSender.sendMessage(message);
    }

    private String getMessage(final CommandSender sender, final String[] args, final boolean isPlayerSender) {
        final String name = sender.getName();
        String prefix = "", suffix = "";
        final String message;

        if (isPlayerSender) {
            final Player playerSender = (Player) sender;
            prefix = this.plugin.getChat().getPlayerPrefix(playerSender);
            suffix = this.plugin.getChat().getPlayerSuffix(playerSender);
            message = this.broadcastValues.getMessageColorizer().colorize((Player) sender, String.join(" ", Arrays.copyOfRange(args, 0, args.length)).trim());
        } else {
            message = this.broadcastValues.getColorizer().colorize(String.join(" ", Arrays.copyOfRange(args, 0, args.length)).trim());
        }

        final String[] replacementList = { name, prefix, suffix, message };

        return Utils.replaceEach(this.broadcastValues.getFormat(), replacementList, this.placeholders);
    }

    private void sendHover(final Player sender, final String formattedMessage, final List<Player> recipients) {
        final String hoverString = this.broadcastValues.getColorizer().colorize(
                Utils.replacePlaceholders(sender, this.broadcastValues.getHoverText())
        );
        final ClickEvent.Action hoverAction = ClickEvent.Action.valueOf(this.broadcastValues.getHoverAction());
        final String hoverValue = this.broadcastValues.getHoverValue().replace("{player}", sender.getName());

        final HoverEvent hoverText = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(TextComponent.fromLegacyText(hoverString)));
        final ClickEvent clickEvent = new ClickEvent(hoverAction, hoverValue);
        final BaseComponent[] comp = TextComponent.fromLegacyText(formattedMessage);

        for (int i = 0; i < comp.length; i++) {
            comp[i].setHoverEvent(hoverText);
            comp[i].setClickEvent(clickEvent);
        }
        for (int i = 0; i < recipients.size(); i++) {
            recipients.get(i).spigot().sendMessage(comp);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
