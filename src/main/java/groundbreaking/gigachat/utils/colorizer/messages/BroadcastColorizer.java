package groundbreaking.gigachat.utils.colorizer.messages;

import groundbreaking.gigachat.GigaChat;
import org.bukkit.entity.Player;

public final class BroadcastColorizer extends AbstractColorizer {

    public BroadcastColorizer(final GigaChat plugin) {
        super(plugin);
    }

    @Override
    public String colorize(final Player player, final String message) {
        if (player.hasPermission("gigachat.broadcast.hex")) {
            return super.messagesColorizer.colorize(message);
        }

        final char[] letters = message.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == COLOR_CHAR) {
                final char code = letters[i + 1];
                if (COLOR_CODES.contains(code) && player.hasPermission("gigachat.color.broadcast." + code)) {
                    letters[i++] = '§';
                    letters[i] = Character.toLowerCase(letters[i]);
                }
                else if (STYLE_CODES.contains(code) && player.hasPermission("gigachat.style.broadcast." + code)) {
                    letters[i++] = '§';
                    letters[i] = Character.toLowerCase(letters[i]);
                }
            }
        }

        return new String(letters);
    }

    @Override
    public String colorize(final String message) {
        return super.messagesColorizer.colorize(message);
    }
}
