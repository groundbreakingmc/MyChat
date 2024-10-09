package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.IColorizer;
import groundbreaking.gigachat.utils.config.ConfigLoader;
import groundbreaking.gigachat.utils.counter.FirstEntryCounter;
import groundbreaking.gigachat.utils.counter.ICounter;
import groundbreaking.gigachat.utils.counter.OnlineTimeCounter;
import lombok.Getter;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class NewbieCommandsValues {

    private final GigaChat plugin;

    @Getter
    private boolean isEnabled, isDenySoundEnabled;


    @Getter
    private boolean
            isListenerPriorityLowest,
            isListenerPriorityLow,
            isListenerPriorityNormal,
            isListenerPriorityHigh,
            isListenerPriorityHighest;
    @Getter
    private ICounter counter;

    @Getter
    private boolean giveBypassPermissions;

    @Getter
    private int requiredTime, bypassRequiredTime;

    @Getter
    private String denyMessage;

    @Getter
    private Sound denySound;

    @Getter
    private float denySoundVolume, denySoundPitch;

    @Getter
    private final List<String> blockedCommands = new ArrayList<>();

    public NewbieCommandsValues(GigaChat plugin) {
        this.plugin = plugin;
    }

    public void setValues() {
        final FileConfiguration config = new ConfigLoader(plugin).loadAndGet("newbie-commands", 1.0);
        final IColorizer colorizer = plugin.getColorizer(config, "settings.serializer");

        setupSettings(config, colorizer);
    }

    private void setupSettings(final FileConfiguration config, final IColorizer colorizer) {
        final ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            isEnabled = settings.getBoolean("enable");
            final String priority = settings.getString("listener-priority").toLowerCase(Locale.ENGLISH);
            setupPriority(priority);
            counter = settings.getBoolean("count-time-from-first-join") ? new FirstEntryCounter() : new OnlineTimeCounter();
            requiredTime = settings.getInt("required-time");
            giveBypassPermissions = settings.getBoolean("if-reached.give-permission");
            bypassRequiredTime = settings.getInt("if-reached.required-time");
            denyMessage = colorizer.colorize(settings.getString("deny-message"));

            setupSound(settings);

            blockedCommands.clear();
            blockedCommands.addAll(settings.getStringList("blocked-commands"));
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"settings\" from file \"newbie-commands.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupSound(final ConfigurationSection settings) {
        final String soundString = settings.getString("deny-sound");
        if (soundString == null) {
            plugin.getMyLogger().warning("Failed to load sound on path \"settings.deny-sound\" from file \"newbie-chat.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            isDenySoundEnabled = false;
        } else if (soundString.equalsIgnoreCase("disabled")) {
            isDenySoundEnabled = false;
        } else {
            isDenySoundEnabled = true;
            final String[] params = soundString.split(";");
            denySound = params.length == 1 && params[0] != null ? Sound.valueOf(params[0].toUpperCase(Locale.ENGLISH)) : Sound.BLOCK_BREWING_STAND_BREW;
            denySoundVolume = params.length == 2 && params[1] != null ? Float.parseFloat(params[1]) : 1.0f;
            denySoundPitch = params.length == 3 && params[2] != null ? Float.parseFloat(params[2]) : 1.0f;
        }
    }

    private void setupPriority(final String priority) {
        switch (priority) {
            case "LOWEST" -> {
                isListenerPriorityLowest = true;
                isListenerPriorityLow = false;
                isListenerPriorityNormal = false;
                isListenerPriorityHigh = false;
                isListenerPriorityHighest = false;
            }
            case "LOW" -> {
                isListenerPriorityLowest = false;
                isListenerPriorityLow = true;
                isListenerPriorityNormal = false;
                isListenerPriorityHigh = false;
                isListenerPriorityHighest = false;
            }
            case "NORMAL" -> {
                isListenerPriorityLowest = false;
                isListenerPriorityLow = false;
                isListenerPriorityNormal = true;
                isListenerPriorityHigh = false;
                isListenerPriorityHighest = false;
            }
            case "HIGH" -> {
                isListenerPriorityLowest = false;
                isListenerPriorityLow = false;
                isListenerPriorityNormal = false;
                isListenerPriorityHigh = true;
                isListenerPriorityHighest = false;
            }
            case "HIGHEST" -> {
                isListenerPriorityLowest = false;
                isListenerPriorityLow = false;
                isListenerPriorityNormal = false;
                isListenerPriorityHigh = false;
                isListenerPriorityHighest = true;
            }
        }
    }
}
