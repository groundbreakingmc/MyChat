package groundbreaking.gigachat.collections;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.map.ExpiringMap;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public final class Cooldowns {

    private final GigaChat plugin;

    @Getter
    private ExpiringMap<String, Long> localCooldowns;
    @Getter
    private ExpiringMap<String, Long> globalCooldowns;
    @Getter
    private ExpiringMap<String, Long> privateCooldowns;
    @Getter
    private ExpiringMap<String, Long> ignoreCooldowns;
    @Getter
    private ExpiringMap<String, Long> spyCooldowns;
    @Getter
    private ExpiringMap<String, Long> broadcastCooldowns;

    public Cooldowns(final GigaChat plugin) {
        this.plugin = plugin;
    }
    
    public void setCooldowns() {
        this.localCooldowns = new ExpiringMap<>(this.plugin.getChatValues().getLocalCooldown(), TimeUnit.MILLISECONDS);
        this.globalCooldowns = new ExpiringMap<>(this.plugin.getChatValues().getGlobalCooldown(), TimeUnit.MILLISECONDS);
        this.privateCooldowns = new ExpiringMap<>(this.plugin.getPmValues().getPmCooldown(), TimeUnit.MILLISECONDS);
        this.ignoreCooldowns = new ExpiringMap<>(this.plugin.getPmValues().getIgnoreCooldown(), TimeUnit.MILLISECONDS);
        this.spyCooldowns = new ExpiringMap<>(this.plugin.getPmValues().getSpyCooldown(), TimeUnit.MILLISECONDS);
        this.broadcastCooldowns = new ExpiringMap<>(this.plugin.getBroadcastValues().getCooldown(), TimeUnit.MILLISECONDS);
    }

    public void removePlayerLocalCooldown(final String playerName) {
        this.localCooldowns.remove(playerName);
    }

    public void removePlayerGlobalCooldown(final String playerName) {
        this.globalCooldowns.remove(playerName);
    }

    public void removePlayerPrivateCooldown(final String playerName) {
        this.privateCooldowns.remove(playerName);
    }

    public void removePlayerIgnoreCooldown(final String playerName) {
        this.ignoreCooldowns.remove(playerName);
    }

    public void removePlayerSpyCooldown(final String playerName) {
        this.spyCooldowns.remove(playerName);
    }

    public void removeBroadcastCooldown(final String playerName) {
        this.broadcastCooldowns.remove(playerName);
    }

    public boolean hasCooldown(final Player player, final String name, final String permission, final ExpiringMap<String, Long> playerCooldown) {
        if (player.hasPermission(permission)) {
            return false;
        }

        return playerCooldown.containsKey(name);
    }

    public void addCooldown(final String name, final ExpiringMap<String, Long> playerCooldown) {
        playerCooldown.put(name, System.currentTimeMillis());
    }
}
