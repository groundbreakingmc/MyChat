package groundbreaking.gigachat.listeners;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.collections.*;
import groundbreaking.gigachat.database.DatabaseQueries;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class DisconnectListener implements Listener {

    private final Cooldowns cooldowns;
    private final DisabledPrivateMessages disabledPrivateMessages;

    public DisconnectListener(final GigaChat plugin) {
        this.cooldowns = plugin.getCooldowns();
        this.disabledPrivateMessages = plugin.getDisabled();
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final String name = event.getPlayer().getName();
        this.loadData(name);
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final String name = event.getPlayer().getName();
        this.saveData(name);
        this.removeCooldown(name);
    }

    @EventHandler(ignoreCancelled = true)
    public void onKick(final PlayerKickEvent event) {
        final String name = event.getPlayer().getName();
        this.saveData(name);
        this.removeCooldown(name);
    }

    private void loadData(final String name) {
        if (DatabaseQueries.disabledChatContainsPlayer(name)) {
            DisabledChat.add(name);
        }
        if (DatabaseQueries.disabledPrivateMessagesContainsPlayer(name)) {
            this.disabledPrivateMessages.add(name);
        }
        if (DatabaseQueries.ignoreChatContains(name)) {
            Ignore.addToIgnoredChat(name, DatabaseQueries.getIgnoredChat(name));
        }
        if (DatabaseQueries.ignorePrivateContainsPlayer(name)) {
            Ignore.addToIgnoredPrivate(name, DatabaseQueries.getIgnoredPrivate(name));
        }
        if (DatabaseQueries.localSpyContainsPlayer(name)) {
            LocalSpy.add(name);
        }
        if (PmSounds.contains(name)) {
            PmSounds.setSound(name, DatabaseQueries.getSound(name));
        }
        if (DatabaseQueries.socialSpyContainsPlayer(name)) {
            SocialSpy.add(name);
        }
    }

    private void saveData(final String name) {
        if (DisabledChat.contains(name)) {
            DatabaseQueries.addPlayerToDisabledChat(name);
        }
        if (this.disabledPrivateMessages.contains(name)) {
            DatabaseQueries.addPlayerToDisabledPrivateMessages(name);
        }
        if (Ignore.playerIgnoresChatAnyOne(name)) {
            DatabaseQueries.addPlayerToIgnoreChat(name, Ignore.getAllIgnoredChat(name));
        }
        if (Ignore.playerIgnoresPrivateAnyOne(name) ) {
            DatabaseQueries.addPlayerToIgnorePrivate(name, Ignore.getAllIgnoredPrivate(name));
        }
        if (LocalSpy.contains(name)) {
            DatabaseQueries.addPlayerToLocalSpy(name);
        }
        if (PmSounds.contains(name)) {
            DatabaseQueries.addPlayerPmSoundToPmSounds(name, PmSounds.getSound(name).toString());
        }
        if (SocialSpy.contains(name)) {
            DatabaseQueries.addPlayerToSocialSpy(name);
        }
    }

    private void removeCooldown(final String name) {
        this.cooldowns.removePlayerLocalCooldown(name);
        this.cooldowns.removePlayerGlobalCooldown(name);
        this.cooldowns.removePlayerPrivateCooldown(name);
        this.cooldowns.removePlayerIgnoreCooldown(name);
        this.cooldowns.removePlayerSpyCooldown(name);
        this.cooldowns.removeBroadcastCooldown(name);
        DisabledChat.remove(name);
        this.disabledPrivateMessages.remove(name);
        Ignore.removeFromIgnoredChat(name);
        Ignore.removeFromIgnoredPrivate(name);
        LocalSpy.remove(name);
        PmSounds.remove(name);
        Reply.removeFromAll(name);
        SocialSpy.remove(name);
    }
}
