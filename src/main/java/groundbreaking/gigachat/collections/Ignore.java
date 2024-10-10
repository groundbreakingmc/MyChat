package groundbreaking.gigachat.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Ignore {

    private static final HashMap<String, List<String>> ignoredChat = new HashMap<>();
    private static final HashMap<String, List<String>> ignoredPrivate = new HashMap<>();

    public static boolean ignoredChatContains(final String name) {
        return !ignoredChat.isEmpty() && ignoredChat.containsKey(name);
    }

    public static boolean ignoredPrivateContains(final String name) {
        return !ignoredPrivate.isEmpty() && ignoredPrivate.containsKey(name);
    }

    public static boolean playerIgnoresChatAnyOne(final String name) {
        return !ignoredChat.isEmpty()
                && ignoredChat.containsKey(name)
                && ignoredChat.get(name) != null
                && !ignoredChat.get(name).isEmpty();
    }

    public static boolean playerIgnoresPrivateAnyOne(final String name) {
        return !ignoredPrivate.isEmpty()
                && ignoredPrivate.containsKey(name)
                && ignoredPrivate.get(name) != null
                && !ignoredPrivate.get(name).isEmpty();
    }

    public static boolean ignoredChatContains(final String name, final String targetName) {
        return !ignoredChat.isEmpty() && ignoredChat.get(name).contains(targetName);
    }

    public static boolean ignoredPrivateContains(final String name, final String targetName) {
        return !ignoredPrivate.isEmpty() && ignoredPrivate.get(name).contains(targetName);
    }

    public static void addToIgnoredChat(final String name, final List<String> list) {
        ignoredChat.put(name, list);
    }


    public static void addToIgnoredPrivate(final String name, final List<String> list) {
        ignoredPrivate.put(name, list);
    }

    public static void addToIgnoredChat(final String name, final String targetName) {
        ignoredChat.get(name).add(targetName);
    }

    public static void addToIgnoredPrivate(final String name, final String targetName) {
        ignoredPrivate.get(name).add(targetName);
    }

    public static void removeFromIgnoredChat(final String name) {
        ignoredChat.remove(name);
    }

    public static void removeFromIgnoredPrivate(final String name) {
        ignoredPrivate.remove(name);
    }

    public static void removeFromIgnoredChat(final String name, final String targetName) {
        ignoredChat.get(name).remove(targetName);
    }

    public static void removeFromIgnoredPrivate(final String name, final String targetName) {
        ignoredPrivate.get(name).remove(targetName);
    }

    public static boolean isIgnoredChat(final String playerName, final String targetName) {
        if (ignoredChat.isEmpty() || !ignoredChat.containsKey(playerName)) {
            return false;
        }

        return ignoredChat.get(playerName).contains(targetName);
    }

    public static boolean isIgnoredPrivate(final String playerName, final String targetName) {
        if (ignoredPrivate.isEmpty() || !ignoredPrivate.containsKey(playerName)) {
            ignoredPrivate.put(playerName, new ArrayList<>());
            return false;
        }

        return ignoredPrivate.get(playerName).contains(targetName);
    }

    public static List<String> getAllIgnoredChat(final String name) {
        return ignoredChat.get(name);
    }

    public static List<String> getAllIgnoredPrivate(final String name) {
        return ignoredPrivate.get(name);
    }
}
