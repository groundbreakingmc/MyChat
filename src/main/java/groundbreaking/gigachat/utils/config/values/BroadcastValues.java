package groundbreaking.gigachat.utils.config.values;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.basic.IColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.AbstractColorizer;
import groundbreaking.gigachat.utils.colorizer.messages.BroadcastColorizer;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;

public final class BroadcastValues {

    private final GigaChat plugin;

    @Getter
    private String format;

    @Getter
    private int cooldown;

    @Getter
    private boolean isHoverEnabled;

    @Getter
    private String hoverAction, hoverValue, hoverText;

    @Getter
    private IColorizer colorizer;

    @Getter
    private final AbstractColorizer messageColorizer;

    public BroadcastValues(final GigaChat plugin) {
        this.plugin = plugin;
        this.messageColorizer = new BroadcastColorizer(plugin);
    }

    public void setValues() {
        final ConfigurationSection broadcast = plugin.getConfig().getConfigurationSection("broadcast");
        if (broadcast != null) {
            this.colorizer = plugin.getColorizer(plugin.getConfig(), "broadcast.use-minimessages");
            this.format = colorizer.colorize(broadcast.getString("format"));
            this.cooldown = broadcast.getInt("cooldown");

            this.setupHover(broadcast);
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"broadcast\" from file \"config.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }

    private void setupHover(final ConfigurationSection broadcast) {
        final ConfigurationSection hover = broadcast.getConfigurationSection("hover");
        if (hover != null) {
            this.isHoverEnabled = hover.getBoolean("enable");
            this.hoverAction = hover.getString("click-action");
            this.hoverValue = hover.getString("click-value");
            this.hoverText = hover.getString("text");
        }
        else {
            this.plugin.getMyLogger().warning("Failed to load section \"broadcast.hover\" from file \"config.yml\". Please check your configuration file, or delete it and restart your server!");
            this.plugin.getMyLogger().warning("If you think this is a plugin error, leave a issue on the https://github.com/grounbreakingmc/GigaChat/issues");
        }
    }
}
