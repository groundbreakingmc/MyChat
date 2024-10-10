package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.basic.IColorizer;
import groundbreaking.gigachat.utils.config.ConfigLoader;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public final class Messages {

    private final GigaChat plugin;

    @Getter
    private String
            playerOnly,
            noPermission,
            reloadMessage,
            nobodyToAnswer,
            playerNotFound,
            soundNotFound,
            chatCooldownMessage,
            commandCooldownMessage,
            pmUsageError,
            ignoreUsageError,
            replyUsageError,
            setpmsoundUsageError,
            broadcastUsageError,
            nonExistArgument,
            argumentUsageError,
            soundAdditionalArgs,
            cannotChatWithHimself,
            cannotIgnoreHimself,
            recipientIgnoresSender,
            senderIgnoresRecipient,
            hasDisabledPm,
            spyEnabled,
            spyDisabled,
            chatIgnoreEnabled,
            chatIgnoreDisabled,
            ownChatEnabled,
            ownChatDisabled,
            privateIgnoreEnabled,
            privateIgnoreDisabled,
            pmEnabled,
            pmDisabled,
            chatHasBeenClearedByAdministrator,
            chatHasBeenCleared,
            serverChatEnabled,
            serverChatDisabled,
            serverChatIsDisabled,
            noOneHear,
            targetPmSoundSet,
            targetPmSoundRemoved,
            PmSoundSet,
            PmSoundRemoved,
            localSpyEnabled,
            localSpyDisabled,
            helpMessage;

    @Getter
    private static String hours, minutes, seconds;

    public Messages(final GigaChat plugin) {
        this.plugin = plugin;
    }

    public void setupMessages() {
<<<<<<< Updated upstream
        final FileConfiguration config = new ConfigLoader(plugin).loadAndGet("messages", 1.0);
        final IColorizer colorizer = plugin.getColorizer(config, "settings.serializer");
=======
        final FileConfiguration config = new ConfigLoader(this.plugin).loadAndGet("messages", 1.0);
        final IColorizer colorizer = this.plugin.getColorizer(config, "settings.use-minimessage");
>>>>>>> Stashed changes

        this.setupMessages(config, colorizer);
        this.setupTimes(config);
    }

    private void setupMessages(final FileConfiguration config, final IColorizer colorizer) {
        this.playerOnly = getMessage(config,"player-only", colorizer);
        this.noPermission = this.getMessage(config, "no-perm", colorizer);
        this.reloadMessage = this.getMessage(config, "reload", colorizer);
        this.nobodyToAnswer = this.getMessage(config, "nobody-to-answer", colorizer);
        this.playerNotFound = this.getMessage(config, "player-not-found", colorizer);
        this.soundNotFound = this.getMessage(config, "sound-not-found", colorizer);
        this.chatCooldownMessage = this.getMessage(config, "chat-cooldown-message", colorizer);
        this.commandCooldownMessage = this.getMessage(config, "command-cooldown-message", colorizer);
        this.pmUsageError = this.getMessage(config, "pm-usage-error", colorizer);
        this.ignoreUsageError = this.getMessage(config, "ignore-usage-error", colorizer);
        this.replyUsageError = this.getMessage(config, "reply-usage-error", colorizer);
        this.setpmsoundUsageError = this.getMessage(config, "setpmsound-usage-error", colorizer);
        this.broadcastUsageError = this.getMessage(config, "broadcast-usage-error", colorizer);
        this.nonExistArgument = this.getMessage(config, "non-exist-arg", colorizer);
        this.argumentUsageError = this.getMessage(config, "arg-usage-error", colorizer);
        this.soundAdditionalArgs = this.getMessage(config, "sound-additional-args", colorizer);
        this.cannotChatWithHimself = this.getMessage(config, "cannot-pm-himself", colorizer);
        this.cannotIgnoreHimself = this.getMessage(config, "cannot-ignore-himself", colorizer);
        this.recipientIgnoresSender = this.getMessage(config, "recipient-ignores-sender", colorizer);
        this.senderIgnoresRecipient = this.getMessage(config, "sender-ignores-himself", colorizer);
        this.hasDisabledPm = this.getMessage(config, "has-disabled-private-messages", colorizer);
        this.spyEnabled = this.getMessage(config, "socialspy-enabled", colorizer);
        this.spyDisabled = this.getMessage(config, "socialspy-disabled", colorizer);
        this.chatIgnoreEnabled = this.getMessage(config, "chat-ignore-enabled", colorizer);
        this.chatIgnoreDisabled = this.getMessage(config, "chat-ignore-disabled", colorizer);
        this.ownChatEnabled = this.getMessage(config, "own-chat-enabled", colorizer);
        this.ownChatDisabled = this.getMessage(config, "own-chat-disabled", colorizer);
        this.privateIgnoreEnabled = this.getMessage(config, "private-ignore-enabled", colorizer);
        this.privateIgnoreDisabled = this.getMessage(config, "private-ignore-disabled", colorizer);
        this.pmEnabled = this.getMessage(config, "private-messages-enabled", colorizer);
        this.pmDisabled = this.getMessage(config, "private-messages-disabled", colorizer);
        this.chatHasBeenClearedByAdministrator = this.getMessage(config, "chat-has-been-cleared-by-administrator", colorizer);
        this.chatHasBeenCleared = this.getMessage(config, "chat-has-been-cleared", colorizer);
        this.serverChatEnabled = this.getMessage(config, "server-chat-enabled", colorizer);
        this.serverChatDisabled = this.getMessage(config, "server-chat-disabled", colorizer);
        this.serverChatIsDisabled = this.getMessage(config, "server-chat-is-disabled", colorizer);
        this.noOneHear = this.getMessage(config, "no-one-hear", colorizer);
        this.targetPmSoundSet = this.getMessage(config, "target-pm-sound-set", colorizer);
        this.targetPmSoundRemoved = this.getMessage(config, "target-pm-sound-removed", colorizer);
        this.PmSoundSet = this.getMessage(config, "pm-sound-set", colorizer);
        this.PmSoundRemoved = this.getMessage(config, "pm-sound-removed", colorizer);
        this.localSpyEnabled = this.getMessage(config, "local-spy-enabled", colorizer);
        this.localSpyDisabled = this.getMessage(config, "local-spy-disabled", colorizer);
        this.helpMessage = this.getMessage(config, "help-messaпe", colorizer);
    }

    public String getMessage(final FileConfiguration config, final String path, final IColorizer colorizer) {
        final String message = config.getString(path, "&4(!) &cFailed to get message from: " + path);
        return colorizer.colorize(message);
    }

    private void setupTimes(final FileConfiguration config) {
        hours = config.getString("time.hours");
        minutes = config.getString("time.minutes");
        seconds = config.getString("time.seconds");
    }
}
