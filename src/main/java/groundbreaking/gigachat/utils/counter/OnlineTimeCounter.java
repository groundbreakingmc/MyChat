package groundbreaking.gigachat.utils.counter;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public final class OnlineTimeCounter implements ICounter {

    @Override
    public long count(final Player player) {
        return player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20;
    }
}
