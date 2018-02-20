package me.discoversquishy.jurassicheads.listeners;

import me.discoversquishy.jurassicheads.JurassicHeads;
import me.discoversquishy.jurassicheads.util.ItemUtil;
import me.discoversquishy.jurassicheads.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayerActivity implements Listener {
    private JurassicHeads plugin = JurassicHeads.getInstance();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = e.getEntity().getKiller();
        Random random = new Random();
        int chance = 0;

        if (killer == null) {
            return;
        }


        for (int i = 0; i <= 100; i++) {
            if (!killer.hasPermission("headdrop." + i)) {
                return;
            }

            chance = i;
        }

        if (random.nextInt(100) > chance) {
            return;
        }
        ItemStack headItem = ItemUtil.createItem(Material.SKULL_ITEM, 1, 3, plugin.getConfig().getString("Head-Name").replace("%player%", player.getName()), plugin.getConfig().getStringList("Head-Lore"));

        player.getWorld().dropItem(player.getLocation(), headItem);
        Lang.HEAD_DROPPED.sendMessage(killer, Collections.singletonList("%player%;" + player.getName()), true);
        Lang.DECAPITATED.sendMessage(player, Collections.singletonList("%player%;" + killer.getName()), true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        List<String> rewards = plugin.getConfig().getStringList("Rewards");
        Random random = new Random();
        int min = plugin.getConfig().getInt("Min-Rewards");
        int max = plugin.getConfig().getInt("Max-Rewards");
        int amount = random.nextInt(max + 1 - min) + min;

        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.SKULL_ITEM) || player.getItemInHand().getDurability() != (short) 3 || !plugin.getConfig().contains("Locations." + block.getLocation())) {
            return;
        }

        for (int i = 0; i < amount; i++) {
            String command = rewards.get(random.nextInt(rewards.size() - 1));
            command = command.replace("%player%", player.getName());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        }

        Lang.REWARD_CLAIMED.sendMessage(player, null, true);
        if (!plugin.getConfig().getBoolean("Remove-Heads")) {
            return;
        }

        if (player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
    }
}