package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.IColorizer;
import groundbreaking.gigachat.utils.config.ConfigLoader;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public final class AutoMessagesValues {

    private final GigaChat plugin;

    @Getter
    private boolean isEnabled, isRandom;

    @Getter
    private int sendInterval;

    @Getter
    private String defaultSound;

    @Getter
    private final HashMap<String, String>
            autoMessagesSounds = new HashMap<>();

    @Getter
    private final HashMap<String, List<String>>
            autoMessages = new HashMap<>();

    public AutoMessagesValues(GigaChat plugin) {
        this.plugin = plugin;
    }

    public void setValues() {
        final FileConfiguration config = new ConfigLoader(plugin).loadAndGet("auto-messages", 1.0);
        final IColorizer colorizer = plugin.getColorizer(config, "settings.serializer");

        setupSettings(config);
        setupAutoMessages(config, colorizer);
    }

    private void setupSettings(final FileConfiguration config) {
        final ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            isEnabled = settings.getBoolean("enable");
            isRandom = settings.getBoolean("random");
            sendInterval = settings.getInt("send-interval");
            defaultSound = settings.getString("default-sound");
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"settings\" from file \"auto-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupAutoMessages(final FileConfiguration config, final IColorizer colorizer) {
        final ConfigurationSection autoMessagesSection = config.getConfigurationSection("auto-messages");
        if (autoMessagesSection != null) {
            autoMessagesSounds.clear();
            autoMessages.clear();

            final List<String> autoMessagesKeys = autoMessagesSection.getKeys(false).stream().toList();
            for (int i = 0; i < autoMessagesKeys.size(); i++) {
                final String key = autoMessagesKeys.get(i);
                autoMessagesSounds.put(key, autoMessagesSection.getString(key + ".sound", defaultSound));
                final List<String> messages = autoMessagesSection.getStringList(key + ".messages");
                for (int r = 0; r < messages.size(); r++) {
                    messages.set(r, colorizer.colorize(messages.get(r)));
                }
                autoMessages.put(key, messages);
            }
        }
        else {
            plugin.getMyLogger().warning("Failed to load section \"auto-messages\" from file \"auto-messages.yml\". Please check your configuration file, or delete it and restart your server!");
            plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }
}
