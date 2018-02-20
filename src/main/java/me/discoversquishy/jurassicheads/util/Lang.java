package me.discoversquishy.jurassicheads.util;

import me.discoversquishy.jurassicheads.JurassicHeads;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public enum Lang {
    CONFIG_RELOADED("Messages.Config-Reloaded", "Configuration files reloaded! (&c%time%ms&7)"),
    DECAPITATED("Messages.Decapitated", "You have been decapitated by %player%!"),
    HEAD_DROPPED("Messages.Head-Dropped", "You have decapitated %player%!"),
    MISSING_PERM("Messages.Missing-Perm", "You don't have permission to do this!"),
    PREFIX("Messages.Prefix", "&c&lJurassicHeads &8&l> &7"),
    REWARD_CLAIMED("Messages.Reward-Claimed", "Reward claimed!");

    private final String path;
    private final String def;
    private static FileConfiguration LANG;

    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(FileConfiguration fileConfiguration) {
        LANG = fileConfiguration;
    }

    @Override
    public String toString() {
        JurassicHeads plugin = JurassicHeads.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        String message = LANG.getString(path, def);

        if (message.equals("")) {
            return null;
        }

        if (plugin.getConfig().getBoolean("Use-Prefix")) {
            stringBuilder.append(Lang.LANG.getString("prefix", PREFIX.def));
        }

        stringBuilder.append(LANG.getString(path, def));

        return Util.colorize(stringBuilder.toString());
    }

    public String toString(boolean addPrefix) {
        JurassicHeads plugin = JurassicHeads.getInstance();

        StringBuilder stringBuilder = new StringBuilder();

        if (addPrefix && plugin.getConfig().getBoolean("Use-Prefix")) {
            stringBuilder.append(Lang.LANG.getString("prefix", PREFIX.def));
        }

        stringBuilder.append(LANG.getString(path, def));

        return Util.colorize(stringBuilder.toString());
    }

    public void sendMessage(CommandSender sender, List<String> replacements, boolean addPrefix) {
        JurassicHeads plugin = JurassicHeads.getInstance();
        StringBuilder stringBuilder = new StringBuilder();
        String message = toString(false);

        if (message.equals("")) {
            return;
        }

        if (replacements != null) {
            for (String replacement : replacements) {
                if (!replacement.contains(";")) {
                    continue;
                }

                message = message.replace(replacement.split(";")[0], replacement.split(";")[1]);
            }
        }

        if (addPrefix && plugin.getConfig().getBoolean("Use-Prefix")) {
            stringBuilder.append(Lang.LANG.getString("prefix", PREFIX.def));
        }

        stringBuilder.append(message);
        sender.sendMessage(Util.colorize(stringBuilder.toString()));
    }
}