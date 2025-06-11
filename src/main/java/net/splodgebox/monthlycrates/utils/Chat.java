package net.splodgebox.monthlycrates.utils;
import de.tr7zw.nbtapi.utils.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    public static String color(String message) {
        if (message == null || message.isEmpty())
            return message;

        return translate(message);
    }

    public static void msg(Player player, String... messages) {
        Arrays.stream(messages).forEach((s) -> {
            player.sendMessage(color(s));
        });
    }

    public static void msg(CommandSender sender, String... messages) {
        Arrays.stream(messages).forEach((s) -> {
            sender.sendMessage(color(s));
        });
    }

    public static void msgAll(String... messages) {
        Bukkit.getOnlinePlayers().forEach((o) -> {
            Arrays.stream(messages).forEach((s) -> {
                o.sendMessage(color(s));
            });
        });
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public static String translate(String message) {
        if(MinecraftVersion.getVersion().getVersionId() >= 1161) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
    }
}