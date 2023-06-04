package me.azhang.dndplugin;

import me.azhang.dndplugin.commands.ModeCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.plugin.java.JavaPlugin;

public final class DndPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        ModeCommands cmds = new ModeCommands();
        Bukkit.getPluginManager().registerEvents(this,this);
        getCommand("combat").setExecutor(cmds);
        getCommand("mining").setExecutor(cmds);
        getCommand("deactivate").setExecutor(cmds);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        event.setJoinMessage("Welcome to the world, " + event.getPlayer().getDisplayName() + ".");
    }

    @EventHandler
    public void onPlayerEggThrow(PlayerEggThrowEvent event){
        String eggName = event.getEgg().getItem().getItemMeta().getDisplayName();
        boolean isDice = false;
        int rollNum = 0;
        if(eggName.equalsIgnoreCase("d20")){
            rollNum = (int) (Math.random() * 20) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d12")){
            rollNum = (int) (Math.random() * 12) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d10")){
            rollNum = (int) (Math.random() * 10) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d8")){
            rollNum = (int) (Math.random() * 8) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d6")){
            rollNum = (int) (Math.random() * 6) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d4")){
            rollNum = (int) (Math.random() * 4) + 1;
            isDice = true;
        }
        else if (eggName.equalsIgnoreCase("d100")){
            rollNum = (int) (Math.random() * 100) + 1;
            isDice = true;
        }

        if(isDice){
            Player player = event.getPlayer();
            player.sendMessage("Rolled: " + rollNum);
        }
        isDice = false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
