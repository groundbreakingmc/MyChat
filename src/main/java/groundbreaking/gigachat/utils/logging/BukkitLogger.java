package groundbreaking.gigachat.utils.logging;

import groundbreaking.gigachat.GigaChat;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class BukkitLogger implements ILogger {

    private final Logger logger;

    public BukkitLogger(final GigaChat plugin) {
        this.logger = plugin.getServer().getLogger();
    }

    public void info(final String msg) {
        this.logger.log(Level.INFO, msg);
    }

    public void warning(final String msg) {
        this.logger.log(Level.WARNING, msg);
    }
}
