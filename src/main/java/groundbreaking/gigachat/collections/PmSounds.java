package groundbreaking.gigachat.collections;

import groundbreaking.gigachat.GigaChat;
import org.bukkit.Sound;

import java.util.HashMap;

public final class PmSounds {

    private static final HashMap<String, String> sounds = new HashMap<>();
    private static String defaultSound;

    public PmSounds(final GigaChat plugin) {
        PmSounds.defaultSound = plugin.getPmValues().getSound();
    }

    public static Sound getSound(final String name) {
        if (sounds.isEmpty() || !sounds.containsKey(name)) {
            return Sound.valueOf(defaultSound);
        }

        return Sound.valueOf(sounds.getOrDefault(name, defaultSound));
    }

    public static void setSound(final String name, final String soundName) {
        sounds.put(name, soundName);
    }

    public static void remove(final String name) {
        sounds.remove(name);
    }

    public static boolean contains(final String name) {
        return sounds.containsKey(name);
    }
}
