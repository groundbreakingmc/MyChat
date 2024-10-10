package groundbreaking.gigachat.commands.other;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.collections.DisabledChat;
import groundbreaking.gigachat.database.DatabaseQueries;
import groundbreaking.gigachat.utils.config.values.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class DisableOwnChatExecutor implements CommandExecutor, TabCompleter {

    private final Messages messages;

    public DisableOwnChatExecutor(final GigaChat plugin) {
        this.messages = plugin.getMessages();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player playerSender)) {
            sender.sendMessage(this.messages.getPlayerOnly());
            return true;
        }

        if (!sender.hasPermission("gigachat.command.disableownchat")) {
            sender.sendMessage(this.messages.getNoPermission());
            return true;
        }

        return this.processDisable(playerSender);
    }

    private boolean processDisable(final Player sender) {
        final String name = sender.getName();
        if (DisabledChat.contains(name)) {
            sender.sendMessage(this.messages.getOwnChatDisabled());
            DisabledChat.remove(name);
            DatabaseQueries.removePlayerFromDisabledChat(name);
        } else {
            sender.sendMessage(this.messages.getOwnChatEnabled());
            DisabledChat.add(name);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}