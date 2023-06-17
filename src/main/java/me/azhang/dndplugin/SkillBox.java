package me.azhang.dndplugin;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class SkillBox {
    public static ItemStack getSkillBox(){
        ItemStack itemStack = new ItemStack(Material.PURPLE_SHULKER_BOX);
        BlockStateMeta blockmeta = (BlockStateMeta) itemStack.getItemMeta();
        ShulkerBox box = (ShulkerBox) blockmeta.getBlockState();

        Inventory i = box.getInventory();
        i.addItem(makeSkillItem(Material.WOODEN_SWORD, "Strength"));
        i.addItem(makeSkillItem(Material.FEATHER, "Dexterity"));
        i.addItem(makeSkillItem(Material.LEATHER_CHESTPLATE, "Constitution"));
        i.addItem(makeSkillItem(Material.SPYGLASS, "Intelligence"));
        i.addItem(makeSkillItem(Material.BOOK, "Wisdom"));
        i.addItem(makeSkillItem(Material.EMERALD, "Charisma"));

        blockmeta.setDisplayName("Skill Items");
        blockmeta.setBlockState(box);
        itemStack.setItemMeta(blockmeta);
        return itemStack;
    }

    private static ItemStack makeSkillItem(Material m, String name){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
