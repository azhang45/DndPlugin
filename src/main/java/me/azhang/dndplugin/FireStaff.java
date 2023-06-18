package me.azhang.dndplugin;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FireStaff {

    public static int charges = 0;
    public static void shoot (Player p){
        if(charges > 0){
            p.sendMessage("Charges: " + charges);
        }

        if(charges == 0){
            p.getInventory().clear(p.getInventory().getHeldItemSlot());
            p.playSound(p.getLocation(), Sound.ITEM_SHIELD_BREAK, 1, 1);
        }
            Location loc = p.getEyeLocation().toVector().add(p.getLocation().getDirection().multiply(2)).
                    toLocation(p.getWorld(), p.getLocation().getYaw(), p.getLocation().getPitch());
            Fireball fireball = p.getWorld().spawn(loc, Fireball.class);
            fireball.setShooter(p);

        charges--;

    }


    public static void giveStaff(int rollNum, Player p){
        ItemStack fireStaff = new ItemStack(Material.LIGHTNING_ROD);
        ItemMeta fireStaffMeta = fireStaff.getItemMeta();

        charges = rollNum * 2;

        List<String> lore = new ArrayList<>();

        lore.add("Ancient Relic of ");
        lore.add("the Dragon King");
        fireStaffMeta.setLore(lore);

        fireStaffMeta.addEnchant(Enchantment.LUCK, 1, true);
        fireStaffMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        fireStaffMeta.setDisplayName("Staff of Fireball");
        fireStaff.setItemMeta(fireStaffMeta);

        p.getWorld().dropItemNaturally(p.getLocation(), fireStaff);

    }



}
