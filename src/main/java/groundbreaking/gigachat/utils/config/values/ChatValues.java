package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.chatsColorizer.AbstractColorizer;
import groundbreaking.gigachat.utils.chatsColorizer.ChatColorizer;
import groundbreaking.gigachat.utils.colorizer.IColorizer;
import groundbreaking.gigachat.utils.config.ConfigLoader;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class ChatValues {

    private final GigaChat plugin;

    @Getter
    private String localFormat, localSpyFormat, globalFormat;

    @Getter
    private int localDistance;

    @Getter
    private int localCooldown, globalCooldown;

    @Getter
    private char globalSymbol;

    @Getter
    private boolean noOneHearEnabled, noOneHearHideHidden, noOneHearHideVanished, noOneHearHideSpectators, isGlobalForce;

    @Getter
    private final Map<String, String>
            localGroupsColors = new HashMap<>(),
            globalGroupsColors = new HashMap<>();

    @Getter
    private boolean
            isListenerPriorityLowest,
            isListenerPriorityLow,
            isListenerPriorityNormal,
            isListenerPriorityHigh,
            isListenerPriorityHighest;

    @Getter
    private IColorizer formatsColorizer;

    @Getter
    private final AbstractColorizer chatsColorizer;

    @Getter
    private boolean isHoverEnabled, isAdminHoverEnabled;

    @Getter
    private String
            hoverText, hoverAction, hoverValue,
            adminHoverText, adminHoverAction, adminHoverValue;

    public ChatValues(GigaChat plugin) {
        this.plugin = plugin;
        chatsColorizer = new ChatColorizer(plugin);
    }

    public void setValues() {
        final FileConfiguration config = new ConfigLoader(plugin).loadAndGet("chats", 1.0);


        setupLocal(config);
        setupGlobal(config);
        setupSettings(config);
        setupHover(config);
        setupAdminHover(config);
    }

    private void setupSettings(final FileConfiguration config) {
        final ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            final String priority = settings.getString("listener-priority").toUpperCase(Locale.ENGLISH);
            setupPriority(priority);
            formatsColorizer = plugin.getColorizer(config, "settings.serializer-for-formats");
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"settings\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupLocal(final FileConfiguration config) {
        final ConfigurationSection local = config.getConfigurationSection("local");
        if (local != null) {
            localFormat = local.getString("format");
            localSpyFormat = local.getString("spy-format");
            localDistance = local.getInt("distance");
            localCooldown = local.getInt("cooldown");
            noOneHearEnabled = local.getBoolean("no-one-hear-you.enable");
            noOneHearHideHidden = local.getBoolean("no-one-hear-you.hide-hidden");
            noOneHearHideVanished = local.getBoolean("no-one-hear-you.hide-vanished");
            noOneHearHideSpectators = local.getBoolean("no-one-hear-you.hide-spectators");
            final ConfigurationSection groupsColors = local.getConfigurationSection("groups-colors");
            if (groupsColors != null) {
                localGroupsColors.clear();
                for (String key : groupsColors.getKeys(false)) {
                    localGroupsColors.put(key, groupsColors.getString(key));
                }
            }
            else {
                plugin.getMyLogger().warning("Failed to load section \"local.groups-colors\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
                plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            }
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"local\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupGlobal(final FileConfiguration config) {
        final ConfigurationSection global = config.getConfigurationSection("global");
        if (global != null) {
            globalFormat = global.getString("format");
            globalSymbol = global.getString("symbol").charAt(0);
            isGlobalForce = global.getBoolean("force");
            globalCooldown = global.getInt("cooldown");
            final ConfigurationSection groupsColors = global.getConfigurationSection("groups-colors");
            if (groupsColors != null) {
                globalGroupsColors.clear();
                for (String key : groupsColors.getKeys(false)) {
                    globalGroupsColors.put(key, groupsColors.getString(key));
                }
            }
            else {
                plugin.getMyLogger().warning("Failed to load section \"local.groups-colors\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
                plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            }
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"global\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupHover(final FileConfiguration config) {
        final ConfigurationSection hover = config.getConfigurationSection("hover");
        if (hover != null) {
            isHoverEnabled = hover.getBoolean("enable");
            hoverAction = hover.getString("click-action");
            hoverValue = hover.getString("click-value");
            hoverText = hover.getString("text");
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"hover\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupAdminHover(final FileConfiguration config) {
        final ConfigurationSection hover = config.getConfigurationSection("admin-hover");
        if (hover != null) {
            isAdminHoverEnabled = hover.getBoolean("enable");
            adminHoverAction = hover.getString("click-action");
            adminHoverValue = hover.getString("click-value");
            adminHoverText = hover.getString("text");
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"hover\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
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
