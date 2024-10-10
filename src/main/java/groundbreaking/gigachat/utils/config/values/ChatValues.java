package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.basic.IColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.AbstractColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.ChatColorizer;
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
    private boolean
            noOneHearEnabled,
            noOneHearHideHidden,
            noOneHearHideVanished,
            noOneHearHideSpectators;

    @Getter
    private boolean isGlobalForce;

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

    public ChatValues(final GigaChat plugin) {
        this.plugin = plugin;
        this.chatsColorizer = new ChatColorizer(plugin);
    }

    public void setValues() {
        final FileConfiguration config = new ConfigLoader(this.plugin).loadAndGet("chats", 1.0);

        this.setupLocal(config);
        this.setupGlobal(config);
        this.setupSettings(config);
        this.setupHover(config);
        this.setupAdminHover(config);
    }

    private void setupSettings(final FileConfiguration config) {
        final ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            final String priority = settings.getString("listener-priority").toUpperCase(Locale.ENGLISH);
<<<<<<< Updated upstream
            setupPriority(priority);
            formatsColorizer = plugin.getColorizer(config, "settings.serializer-for-formats");
=======
            this.setupPriority(priority);

            this.formatsColorizer = plugin.getColorizer(config, "settings.use-minimessage-for-formats");
>>>>>>> Stashed changes
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"settings\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupLocal(final FileConfiguration config) {
        final ConfigurationSection local = config.getConfigurationSection("local");
        if (local != null) {
            this.localFormat = local.getString("format");
            this.localSpyFormat = local.getString("spy-format");
            this.localDistance = local.getInt("distance");
            this.localCooldown = local.getInt("cooldown");
            this.noOneHearEnabled = local.getBoolean("no-one-hear-you.enable");
            this.noOneHearHideHidden = local.getBoolean("no-one-hear-you.hide-hidden");
            this.noOneHearHideVanished = local.getBoolean("no-one-hear-you.hide-vanished");
            this.noOneHearHideSpectators = local.getBoolean("no-one-hear-you.hide-spectators");

            final ConfigurationSection groupsColors = local.getConfigurationSection("groups-colors");
            if (groupsColors != null) {
                this.localGroupsColors.clear();
                for (String key : groupsColors.getKeys(false)) {
                    this.localGroupsColors.put(key, groupsColors.getString(key));
                }
            }
            else {
                this.plugin.getMyLogger().warning("Failed to load section \"local.groups-colors\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
                this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"local\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupGlobal(final FileConfiguration config) {
        final ConfigurationSection global = config.getConfigurationSection("global");
        if (global != null) {
            this.globalFormat = global.getString("format");
            this.globalSymbol = global.getString("symbol").charAt(0);
            this.isGlobalForce = global.getBoolean("force");
            this.globalCooldown = global.getInt("cooldown");

            final ConfigurationSection groupsColors = global.getConfigurationSection("groups-colors");
            if (groupsColors != null) {
                this.globalGroupsColors.clear();
                for (String key : groupsColors.getKeys(false)) {
                    this.globalGroupsColors.put(key, groupsColors.getString(key));
                }
            }
            else {
                this.plugin.getMyLogger().warning("Failed to load section \"local.groups-colors\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
                this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
            }
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"global\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupHover(final FileConfiguration config) {
        final ConfigurationSection hover = config.getConfigurationSection("hover");
        if (hover != null) {
            this.isHoverEnabled = hover.getBoolean("enable");
            this.hoverAction = hover.getString("click-action");
            this.hoverValue = hover.getString("click-value");
            this.hoverText = hover.getString("text");
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"hover\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupAdminHover(final FileConfiguration config) {
        final ConfigurationSection hover = config.getConfigurationSection("admin-hover");
        if (hover != null) {
            this.isAdminHoverEnabled = hover.getBoolean("enable");
            this.adminHoverAction = hover.getString("click-action");
            this.adminHoverValue = hover.getString("click-value");
            this.adminHoverText = hover.getString("text");
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"hover\" from file \"chats.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupPriority(final String priority) { // todo remake for unregistration of events
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
