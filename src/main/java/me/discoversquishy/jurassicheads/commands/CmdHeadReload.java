package me.discoversquishy.jurassicheads.commands;

import me.discoversquishy.jurassicheads.JurassicHeads;
import me.discoversquishy.jurassicheads.util.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class CmdHeadReload implements CommandExecutor {
    private JurassicHeads plugin = JurassicHeads.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("jurassicheads.reload")) {
            Lang.MISSING_PERM.sendMessage(sender, null, true);
            return true;
        }
        long startTime = System.currentTimeMillis();

        plugin.reloadConfig();
        Lang.CONFIG_RELOADED.sendMessage(sender, Collections.singletonList("%time%;" + Long.toString(System.currentTimeMillis() - startTime)), true);
        return true;
    }
}
