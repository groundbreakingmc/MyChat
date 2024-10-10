package groundbreaking.gigachat.commands;

import groundbreaking.gigachat.GigaChat;
import groundbreaking.gigachat.utils.config.values.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class MainCommandHandler implements CommandExecutor, TabCompleter {

    private final Messages messages;

    private final HashMap<String, ArgsConstructor> arguments = new HashMap<>();

    public MainCommandHandler(final GigaChat plugin) {
        this.messages = plugin.getMessages();
    }

    public void registerArgument(final ArgsConstructor argument) {
        this.arguments.put(argument.getName(), argument);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandlabel, @NotNull String[] args) {

        if (args.length < 1) {
            if (this.hasAnyPluginPermission(sender)) {
                sender.sendMessage(this.messages.getNonExistArgument());
            } else {
                sender.sendMessage(this.messages.getNoPermission());
            }
            return true;
        }

        final ArgsConstructor argument = this.arguments.get(args[0]);

        if (argument == null) {
            sender.sendMessage(this.messages.getNonExistArgument());
            return true;
        }

        if (!sender.hasPermission(argument.getPermission())) {
            sender.sendMessage(this.messages.getNoPermission());
            return true;
        }

        return argument.execute(sender, args);
    }

    private boolean hasAnyPluginPermission(final CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }

        return sender.hasPermission("gigachat.command.reload")
                || sender.hasPermission("gigachat.command.disablechat")
                || sender.hasPermission("gigachat.command.setpmsound")
                || sender.hasPermission("gigachat.command.localspy")
                || sender.hasPermission("gigachat.command.clearchat");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        final List<String> list = new ArrayList<>();
        final String input = args[args.length - 1].toUpperCase();

        if (args.length == 1) {

            if (sender.hasPermission("gigachat.command.clearchat") && "clearchat".startsWith(input)) {
                list.add("clearchat");
            }
            if (sender.hasPermission("gigachat.command.disablechat") && "disablechat".startsWith(input)) {
                list.add("disablechat");
            }
            if (sender.hasPermission("gigachat.command.localspy") && "localspy".startsWith(input)) {
                list.add("localspy");
            }
            if (sender.hasPermission("gigachat.command.reload") && "reload".startsWith(input)) {
                list.add("reload");
            }
            if (sender.hasPermission("gigachat.command.setpmsound") && "setpmsound".startsWith(input)) {
                list.add("setpmsound");
            }

            return list;
        }

        final boolean isSetpmsoundArg = args[0].equalsIgnoreCase("setpmsound");
        if (isSetpmsoundArg) {
            final boolean hasSetpmsoundPerm = sender.hasPermission("gigachat.command.setpmsound");
            if (args.length == 2 && hasSetpmsoundPerm) {
                final List<String> playerNames = this.getPlayers(input);
                list.addAll(playerNames);
            }
            else if (args.length == 3 && hasSetpmsoundPerm) {
                if ("none".startsWith(input.toLowerCase())) {
                    list.add("none");
                }

                for (final Sound sound : Sound.values()) {
                    final String soundName = sound.name();
                    if (soundName.startsWith(input)) {
                        list.add(soundName);
                    }
                }
            }
        }

        return list;
    }

    private List<String> getPlayers(final String input) {
        final List<String> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            final String playerName = player.getName();
            if (playerName.toLowerCase().startsWith(input)) {
                players.add(playerName);
            }
        }

        return players;
    }
}
