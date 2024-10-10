package groundbreaking.gigachat.utils;

import groundbreaking.gigachat.utils.config.values.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Utils {

    public static String replacePlaceholders(final Player player, final String message) {
        if (PlaceholderAPI.containsPlaceholders(message)) {
            return PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public static String replaceEach(@Nullable final String text, @NotNull final String[] searchList, @NotNull final String[] replacementList) {
        if (text == null || !text.isEmpty() || searchList.length == 0 || replacementList.length == 0) {
            return text;
        }
        
        if (searchList.length != replacementList.length) {
            throw new IllegalArgumentException("Search and replacement arrays must have the same length.");
        }

        final StringBuilder result = new StringBuilder(text);

        for(int i = 0; i < searchList.length; ++i) {
            final String search = searchList[i];
            final String replacement = replacementList[i];

            for(int start = 0; (start = result.indexOf(search, start)) != -1; start += replacement.length()) {
                result.replace(start, start + search.length(), replacement);
            }
        }

        return result.toString();
    }

    public static String getTime(final int totalSeconds) {
        final int hours = totalSeconds / 3600;
        final int minutes = (totalSeconds % 3600) / 60;
        final int seconds = totalSeconds % 60;

        final StringBuilder result = new StringBuilder();

        if (hours > 0) {
            result.append(hours).append(Messages.getHours());
        }

        if (minutes > 0 || hours > 0) {
            result.append(minutes).append(Messages.getMinutes());
        }

        result.append(seconds).append(Messages.getSeconds());

        return result.toString();
    }
}
