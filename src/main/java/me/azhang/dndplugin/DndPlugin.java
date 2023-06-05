package me.azhang.dndplugin;

import me.azhang.dndplugin.commands.ModeCommands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
        Player p = event.getPlayer();
        String eggName = event.getEgg().getItem().getItemMeta().getDisplayName();
        boolean isDice = false;
        int rollNum = 0;
        if (eggName.equalsIgnoreCase("d20")) {
            rollNum = (int) (Math.random() * 20) + 1;
            isDice = true;

            //if rolled a 1, summon splash potion of harming
            if (rollNum == 1) {
                ItemStack HarmPotion = new ItemStack(Material.SPLASH_POTION);
                PotionMeta potionMeta = (PotionMeta) HarmPotion.getItemMeta();

                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 20 * 5, 0), true);

                HarmPotion.setItemMeta(potionMeta);

                ThrownPotion thrownPotion = (ThrownPotion) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPLASH_POTION);
                thrownPotion.setItem(HarmPotion);
            }

            //summon an iron sword with sharpness lvl equal to die roll - 5
            else if (ModeCommands.modeStatus[0]) {
                ItemStack IronSword = new ItemStack(Material.IRON_SWORD);
                IronSword.getItemMeta().addEnchant(Enchantment.DAMAGE_ALL, rollNum - 5, false);
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(IronSword));

                //summon an iron pickaxe with efficiency lvl equal to die roll - 5
            } else if (ModeCommands.modeStatus[1]) {
                ItemStack IronPickaxe = new ItemStack(Material.IRON_PICKAXE);
                IronPickaxe.getItemMeta().addEnchant(Enchantment.DIG_SPEED, rollNum - 5, false);
                p.getWorld().dropItemNaturally(p.getLocation(), new ItemStack(IronPickaxe));
            }
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
