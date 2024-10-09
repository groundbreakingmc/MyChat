package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.IColorizer;
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

    public Messages(GigaChat plugin) {
        this.plugin = plugin;
    }

    public void setupMessages() {
        final FileConfiguration config = new ConfigLoader(plugin).loadAndGet("messages", 1.0);
        final IColorizer colorizer = plugin.getColorizer(config, "settings.serializer");

        setupMessages(config, colorizer);
        setupTimes(config);
    }

    private void setupMessages(final FileConfiguration config, final IColorizer colorizer) {
        playerOnly = colorizer.colorize(config.getString("player-only"));
        noPermission = colorizer.colorize(config.getString("no-perm"));
        reloadMessage = colorizer.colorize(config.getString("reload"));
        nobodyToAnswer = colorizer.colorize(config.getString("nobody-to-answer"));
        playerNotFound = colorizer.colorize(config.getString("player-not-found"));
        soundNotFound = colorizer.colorize(config.getString("sound-not-found"));
        chatCooldownMessage = colorizer.colorize(config.getString("chat-cooldown-message"));
        commandCooldownMessage = colorizer.colorize(config.getString("command-cooldown-message"));
        pmUsageError = colorizer.colorize(config.getString("pm-usage-error"));
        ignoreUsageError = colorizer.colorize(config.getString("ignore-usage-error"));
        replyUsageError = colorizer.colorize(config.getString("reply-usage-error"));
        setpmsoundUsageError = colorizer.colorize(config.getString("setpmsound-usage-error"));
        broadcastUsageError = colorizer.colorize(config.getString("broadcast-usage-error"));
        nonExistArgument = colorizer.colorize(config.getString("non-exist-arg"));
        argumentUsageError = colorizer.colorize(config.getString("arg-usage-error"));
        soundAdditionalArgs = colorizer.colorize(config.getString("sound-additional-args"));
        cannotChatWithHimself = colorizer.colorize(config.getString("cannot-pm-himself"));
        cannotIgnoreHimself = colorizer.colorize(config.getString("cannot-ignore-himself"));
        recipientIgnoresSender = colorizer.colorize(config.getString("recipient-ignores-sender"));
        senderIgnoresRecipient = colorizer.colorize(config.getString("sender-ignores-himself"));
        hasDisabledPm = colorizer.colorize(config.getString("has-disabled-private-messages"));
        spyEnabled = colorizer.colorize(config.getString("socialspy-enabled"));
        spyDisabled = colorizer.colorize(config.getString("socialspy-disabled"));
        chatIgnoreEnabled = colorizer.colorize(config.getString("chat-ignore-enabled"));
        chatIgnoreDisabled = colorizer.colorize(config.getString("chat-ignore-disabled"));
        ownChatEnabled = colorizer.colorize(config.getString("own-chat-enabled"));
        ownChatDisabled = colorizer.colorize(config.getString("own-chat-disabled"));
        privateIgnoreEnabled = colorizer.colorize(config.getString("private-ignore-enabled"));
        privateIgnoreDisabled = colorizer.colorize(config.getString("private-ignore-disabled"));
        pmEnabled = colorizer.colorize(config.getString("private-messages-enabled"));
        pmDisabled = colorizer.colorize(config.getString("private-messages-disabled"));
        chatHasBeenClearedByAdministrator = colorizer.colorize(config.getString("chat-has-been-cleared-by-administrator"));
        chatHasBeenCleared = colorizer.colorize(config.getString("chat-has-been-cleared"));
        serverChatEnabled = colorizer.colorize(config.getString("server-chat-enabled"));
        serverChatDisabled = colorizer.colorize(config.getString("server-chat-disabled"));
        serverChatIsDisabled = colorizer.colorize(config.getString("server-chat-is-disabled"));
        noOneHear = colorizer.colorize(config.getString("no-one-hear"));
        targetPmSoundSet = colorizer.colorize(config.getString("target-pm-sound-set"));
        targetPmSoundRemoved = colorizer.colorize(config.getString("target-pm-sound-removed"));
        PmSoundSet = colorizer.colorize(config.getString("pm-sound-set"));
        PmSoundRemoved = colorizer.colorize(config.getString("pm-sound-removed"));
        localSpyEnabled = colorizer.colorize(config.getString("local-spy-enabled"));
        localSpyDisabled = colorizer.colorize(config.getString("local-spy-disabled"));
        helpMessage = colorizer.colorize(config.getString("help-messaпe"));
    }

    private void setupTimes(final FileConfiguration config) {
        hours = config.getString("time.hours");
        minutes = config.getString("time.minutes");
        seconds = config.getString("time.seconds");
    }
}
