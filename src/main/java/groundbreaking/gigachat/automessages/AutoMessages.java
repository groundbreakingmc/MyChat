package groundbreaking.gigachat.automessages;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.config.values.AutoMessagesValues;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

// todo Ускорить
public final class AutoMessages {

    private final Random random = new Random();
    private int i = 0;

    private final GigaChat plugin;
    private final AutoMessagesValues autoMessagesValues;

    public AutoMessages(final GigaChat plugin) {
        this.plugin = plugin;
        this.autoMessagesValues = plugin.getAutoMessagesValues();
    }

    public void run() {
        (new BukkitRunnable() {
            public void run() {
                process();
            }
        }).runTaskTimerAsynchronously(this.plugin, 20L, this.autoMessagesValues.getSendInterval() * 20L);
    }

    private void process() {
        final String key = getAutoMessage();
        if (key == null) {
            return;
        }

        final List<String> autoMessages = this.autoMessagesValues.getAutoMessages().get(key);
        final String soundString = this.autoMessagesValues.getAutoMessagesSounds().get(key);

        boolean isSoundEnabled = false;
        Sound sound = null; float soundVolume = 1.0f, soundPitch = 1.0f;
        if (soundString != null) {
            isSoundEnabled = true;
            final String[] params = soundString.split(";");
            sound = params.length == 1 && params[0] != null ? Sound.valueOf(params[0].toUpperCase(Locale.ENGLISH)) : Sound.BLOCK_BREWING_STAND_BREW;
            soundVolume = params.length == 2 && params[1] != null ? Float.parseFloat(params[1]) : 1.0f;
            soundPitch = params.length == 3 && params[2] != null ? Float.parseFloat(params[2]) : 1.0f;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("gigachat.automessages")) {
                continue;
            }

            if (isSoundEnabled) {
                for (int i = 0; i < autoMessages.size(); i++) {
                    player.sendMessage(autoMessages.get(i));
                    player.playSound(player.getLocation(), sound, soundVolume, soundPitch);
                }
            } else {
                for (int i = 0; i < autoMessages.size(); i++) {
                    player.sendMessage(autoMessages.get(i));
                }
            }
        }
    }

    private String getAutoMessage() {
        final int size = this.autoMessagesValues.getAutoMessages().size();
        final int iterationsAmount = this.autoMessagesValues.isRandom() ? this.random.nextInt(size) : i;

        if (i >= size) {
            i = 0;
        }

        String randomKey = null;

        final Iterator<String> iterator = this.autoMessagesValues.getAutoMessages().keySet().iterator();
        for (int i = 0; i < iterationsAmount; i++) {
            if (iterator.hasNext()) {
                randomKey = iterator.next();
            }
        }

        return randomKey;
    }
}