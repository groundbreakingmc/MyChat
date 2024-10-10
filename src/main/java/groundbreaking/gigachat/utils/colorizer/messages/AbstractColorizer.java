package groundbreaking.gigachat.utils.colorizer.messages;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.colorizer.basic.IColorizer;
import it.unimi.dsi.fastutil.chars.CharOpenHashSet;
import it.unimi.dsi.fastutil.chars.CharSet;
import org.bukkit.entity.Player;

public abstract class AbstractColorizer {

    protected static final CharSet COLOR_CODES = new CharOpenHashSet(new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f',
            'A', 'B', 'C', 'D', 'E', 'F'
    });

    protected static final CharSet STYLE_CODES = new CharOpenHashSet(new char[]{
            'k', 'l', 'm', 'n', 'o', 'r', 'x',
            'K', 'L', 'M', 'N', 'O', 'R', 'X'
    });

    protected final IColorizer messagesColorizer;
    protected static final char COLOR_CHAR = '&';

    public AbstractColorizer(final GigaChat plugin) {
        this.messagesColorizer = plugin.getColorizerByVersion();
    }

    public abstract String colorize(Player player, String message);

    public abstract String colorize(String message);
}
