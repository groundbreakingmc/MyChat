package groundbreaking.gigachat.listeners;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.Utils;
import groundbreaking.gigachat.utils.config.values.NewbieChatValues;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class NewbieChatListener implements Listener {

    private final GigaChat plugin;
    private final NewbieChatValues newbieValues;

    public NewbieChatListener(final GigaChat plugin) {
        this.plugin = plugin;
        this.newbieValues = plugin.getNewbieChat();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMessageSendLowest(final AsyncPlayerChatEvent event) {
        if (this.newbieValues.isListenerPriorityLowest()) {
            processEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onMessageSendLow(final AsyncPlayerChatEvent event) {
        if (this.newbieValues.isListenerPriorityLow()) {
            this.processEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onMessageSendNormal(final AsyncPlayerChatEvent event) {
        if (this.newbieValues.isListenerPriorityNormal()) {
            this.processEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onMessageSendHigh(final AsyncPlayerChatEvent event) {
        if (this.newbieValues.isListenerPriorityHigh()) {
            this.processEvent(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMessageSendHighest(final AsyncPlayerChatEvent event) {
        if (this.newbieValues.isListenerPriorityHighest()) {
            this.processEvent(event);
        }
    }

    private void processEvent(final AsyncPlayerChatEvent event) {
        if (!this.newbieValues.isEnabled()) {
            return;
        }

        final Player player = event.getPlayer();

        if (player.hasPermission("gigachat.bypass.chatnewbie")) {
            return;
        }

        final long time = this.newbieValues.getCounter().count(player);

        if (this.newbieValues.isGiveBypassPermissionEnabled()) {
            if (time <= this.newbieValues.getRequiredTimeToGetBypassPerm()) {
                final String bypassPermission = "gigachat.bypass.chatnewbie";
                plugin.getPerms().playerAdd(player, bypassPermission);
            }
        }

        if (time > this.newbieValues.getRequiredTime()) {
            return;
        }

        this.sendMessage(player, time);

        if (this.newbieValues.isDenySoundEnabled()) {
            this.playSound(player);
        }

        event.setCancelled(true);
    }

    private void sendMessage(final Player player, final long time) {
        final String restTime = Utils.getTime((int) (this.newbieValues.getRequiredTime() - time));
        final String denyMessage = this.newbieValues.getDenyMessage().replace("{time}", restTime);
        player.sendMessage(denyMessage);
    }

    private void playSound(final Player player) {
        final Location playerLocation = player.getLocation();
        final Sound denySound = this.newbieValues.getDenySound();
        final float denySoundVolume = this.newbieValues.getDenySoundVolume();
        final float denySoundPitch = this.newbieValues.getDenySoundPitch();
        player.playSound(playerLocation, denySound, denySoundVolume, denySoundPitch);
    }
}
