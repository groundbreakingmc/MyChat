package groundbreaking.gigachat.utils.vanish;

import com.earth2me.essentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class EssentialsChecker implements IVanishChecker {

    private final Essentials essentials;

    public EssentialsChecker(final Plugin essentials) {
        this.essentials = (Essentials) essentials;
    }

    @Override
    public boolean isVanished(final Player player) {
        return this.essentials.getUser(player).isVanished();
    }
}
