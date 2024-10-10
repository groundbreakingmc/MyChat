package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.commands.privateMessages.IgnoreCommandExecutor;
import groundbreaking.gigachat.commands.privateMessages.PrivateMessageCommandExecutor;
import groundbreaking.gigachat.commands.privateMessages.ReplyCommandExecutor;
import groundbreaking.gigachat.commands.privateMessages.SocialSpyCommandExecutor;
import groundbreaking.gigachat.utils.colorizer.basic.IColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.AbstractColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.PrivateMessagesColorizer;
import groundbreaking.gigachat.utils.config.ConfigLoader;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public final class PrivateMessagesValues {

    private final GigaChat plugin;

    @Getter
    private boolean printLogsToConsole, isSoundEnabled, commandsRegistered = false;

    @Getter
    private int pmCooldown, ignoreCooldown, spyCooldown;

    @Getter
    private String sound;

    @Getter
    private float soundVolume, soundPitch;

    @Getter
    private String senderFormat, recipientFormat, socialSpyFormat, consoleFormat;

    @Getter
    public IColorizer formatsColorizer;

    @Getter
    public final AbstractColorizer messagesColorizer;

    public PrivateMessagesValues(final GigaChat plugin) {
        this.plugin = plugin;
        this.messagesColorizer = new PrivateMessagesColorizer(plugin);
    }

    public void setValues() {
        final FileConfiguration config = new ConfigLoader(this.plugin).loadAndGet("private-messages", 1.0);

        this.setupSettings(config);
        this.setupCommands(config);
        this.setupFormats(config);
    }

    private void setupSettings(final FileConfiguration config) {
        final ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            this.printLogsToConsole = settings.getBoolean("print-to-console");

            this.setupSound(settings);

<<<<<<< Updated upstream
            formatsColorizer = plugin.getColorizer(config, "settings.serializer-for-formats");
=======
            this.formatsColorizer = plugin.getColorizer(config, "settings.use-minimessage-for-formats");
>>>>>>> Stashed changes
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"settings\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupCommands(final FileConfiguration config) {
        final ConfigurationSection pmCommand = config.getConfigurationSection("private-message-command");
        if (pmCommand != null) {
            this.pmCooldown = pmCommand.getInt("cooldown");
            if (!this.commandsRegistered) {
                final PrivateMessageCommandExecutor pmExecutor = new PrivateMessageCommandExecutor(plugin);
                this.plugin.registerCommand(pmCommand.getString("command"),
                        pmCommand.getStringList("aliases"), pmExecutor, pmExecutor);
            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"private-message-command\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }

        final ConfigurationSection replyCommand = config.getConfigurationSection("reply-command");
        if (replyCommand != null) {
            if (!this.commandsRegistered) {
                final ReplyCommandExecutor replyExecutor = new ReplyCommandExecutor(plugin);
                this.plugin.registerCommand(replyCommand.getString("command"),
                        replyCommand.getStringList("aliases"), replyExecutor, replyExecutor);
            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"reply-command\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }

        final ConfigurationSection ignoreCommand = config.getConfigurationSection("ignore-command");
        if (ignoreCommand != null) {
            this.ignoreCooldown = ignoreCommand.getInt("cooldown");
            if (!this.commandsRegistered) {
                final IgnoreCommandExecutor ignoreExecutor = new IgnoreCommandExecutor(plugin);
                this.plugin.registerCommand(ignoreCommand.getString("command"),
                        ignoreCommand.getStringList("aliases"), ignoreExecutor, ignoreExecutor);

            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"ignore-command\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }

        final ConfigurationSection spyCommand = config.getConfigurationSection("socialspy-command");
        if (spyCommand != null) {
            this.spyCooldown = spyCommand.getInt("cooldown");
            if (!this.commandsRegistered) {
                final SocialSpyCommandExecutor socialspyExecutor = new SocialSpyCommandExecutor(plugin);
                this.plugin.registerCommand(spyCommand.getString("command"),
                        spyCommand.getStringList("aliases"), socialspyExecutor, socialspyExecutor);
            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"socialspy-command\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }

        this.commandsRegistered = true;
    }

    private void setupFormats(final FileConfiguration config) {
        final ConfigurationSection formats = config.getConfigurationSection("formats");
        if (formats != null) {
            this.senderFormat = formats.getString("sender-format");
            this.recipientFormat = formats.getString("recipient-format");
            this.socialSpyFormat = formats.getString("socialspy-format");
            this.consoleFormat = formats.getString("console-format");
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"formats\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupSound(final ConfigurationSection settings) {
        final String soundString = settings.getString("sound");
        if (soundString == null) {
            this.plugin.getMyLogger().warning("Failed to load sound on path \"settings.deny-sound\" from file \"private-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            this.isSoundEnabled = false;
        }
        else if (soundString.equalsIgnoreCase("disabled")) {
            this.isSoundEnabled = false;
        }
        else {
            this.isSoundEnabled = true;
            final String[] params = soundString.split(";");
            this.sound = params.length == 1 && params[0] != null ? params[0].toUpperCase(Locale.ENGLISH) : "BLOCK_BREWING_STAND_BREW";
            this.soundVolume = params.length == 2 && params[1] != null ? Float.parseFloat(params[1]) : 1.0f;
            this.soundPitch = params.length == 3 && params[2] != null ? Float.parseFloat(params[2]) : 1.0f;
        }
    }
}
