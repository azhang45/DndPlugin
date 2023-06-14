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
import org.bukkit.inventory.meta.ItemMeta;
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
//            if (rollNum == 1) {
//                ItemStack HarmPotion = new ItemStack(Material.SPLASH_POTION);
//                PotionMeta potionMeta = (PotionMeta) HarmPotion.getItemMeta();
//
//                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 20 * 5, 0), true);
//
//                HarmPotion.setItemMeta(potionMeta);
//
//                ThrownPotion thrownPotion = (ThrownPotion) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPLASH_POTION);
//                thrownPotion.setItem(HarmPotion);
//            }

            //summon an iron sword with sharpness lvl equal to die roll / 2
            if (ModeCommands.modeStatus[0]) {
                removeDndItem(p);
                ItemStack StoneSword = new ItemStack(Material.STONE_SWORD);
                ItemMeta stoneSwordMeta = StoneSword.getItemMeta();
                if(rollNum > 1){
                    stoneSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, (rollNum/2), true);
                }
                stoneSwordMeta.setDisplayName("D&D Sword");
                StoneSword.setItemMeta(stoneSwordMeta);
                p.getWorld().dropItemNaturally(p.getLocation(), StoneSword);

                //summon an iron pickaxe with efficiency lvl equal to die roll / 3
            } else if (ModeCommands.modeStatus[1]) {
                removeDndItem(p);
                ItemStack IronPick = new ItemStack(Material.IRON_PICKAXE);
                ItemMeta ironPickMeta = IronPick.getItemMeta();
                if(rollNum > 2){
                    ironPickMeta.addEnchant(Enchantment.DIG_SPEED, (rollNum/3), true);

                }
                ironPickMeta.setDisplayName("D&D Pickaxe");
                IronPick.setItemMeta(ironPickMeta);
                p.getWorld().dropItemNaturally(p.getLocation(), IronPick);
            }
        }
        else if (eggName.equalsIgnoreCase("d10")){
            rollNum = (int) (Math.random() * 10) + 1;
            isDice = true;
            ItemStack offhandItem = p.getInventory().getItemInOffHand();

            if(offhandItem.getType().equals(Material.WOODEN_SWORD) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Strength")){
                throwPotion(p, rollNum, PotionEffectType.INCREASE_DAMAGE);

            }

            else if(offhandItem.getType().equals(Material.FEATHER) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Dexterity")){
                throwPotion(p, rollNum, PotionEffectType.SPEED);

            }

            else if(offhandItem.getType().equals(Material.LEATHER_CHESTPLATE) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Constitution")){
                if (rollNum > 1) {
                    ItemStack Potion = new ItemStack(Material.SPLASH_POTION);
                    PotionMeta potionMeta = (PotionMeta) Potion.getItemMeta();

                    potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * (rollNum * 10), rollNum / 3), true);

                    Potion.setItemMeta(potionMeta);

                    ThrownPotion thrownPotion = (ThrownPotion) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPLASH_POTION);
                    thrownPotion.setItem(Potion);
                }
            }

            else if(offhandItem.getType().equals(Material.SPYGLASS) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Intelligence")){
                throwPotion(p, rollNum, PotionEffectType.INVISIBILITY);

            }

            else if(offhandItem.getType().equals(Material.BOOK) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Wisdom")){
                throwPotion(p, rollNum * 40, PotionEffectType.LUCK);
                throwPotion(p, rollNum * 40, PotionEffectType.NIGHT_VISION);

            }

            else if(offhandItem.getType().equals(Material.EMERALD) && offhandItem.getItemMeta().getDisplayName().equalsIgnoreCase("Charisma")){
                throwPotion(p, rollNum * (rollNum * 20), PotionEffectType.HERO_OF_THE_VILLAGE);

            }
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

    public static boolean removeDndItem(Player p){
        ItemStack[] contents = p.getInventory().getContents();
        boolean removed = false;
        for(int i = 0; i < contents.length; i++){
            if(contents[i] != null && contents[i].getItemMeta().getDisplayName().contains("D&D")){
                p.getInventory().removeItem(contents[i]);
                removed = true;
            }
        }

        return removed;
    }

    public static void throwPotion(Player p, int rollNum, PotionEffectType effect){
        if (rollNum > 1) {
            ItemStack Potion = new ItemStack(Material.SPLASH_POTION);
            PotionMeta potionMeta = (PotionMeta) Potion.getItemMeta();

            potionMeta.addCustomEffect(new PotionEffect(effect, 20 * (rollNum * 10), rollNum), true);

            Potion.setItemMeta(potionMeta);

            ThrownPotion thrownPotion = (ThrownPotion) p.getWorld().spawnEntity(p.getLocation(), EntityType.SPLASH_POTION);
            thrownPotion.setItem(Potion);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
