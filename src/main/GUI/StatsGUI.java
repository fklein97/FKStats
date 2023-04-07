package main.GUI;

import main.FKStats;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * Created by FKPro on 08.10.2018.
 */
public class StatsGUI {

    private FKStats plugin;

    public StatsGUI(FKStats plugin){
        this.plugin = plugin;
    }

    public Inventory create(String playername){
        ItemStack deaths_stack;
        if(plugin.getConfig().getInt("stats." + playername + ".deaths") > 0) {
            deaths_stack = new ItemStack(Material.SKELETON_SKULL, plugin.getConfig().getInt("stats." + playername + ".deaths"));
        }
        else{
            deaths_stack = new ItemStack(Material.SKELETON_SKULL, 1);
        }
        ItemMeta deaths_meta = deaths_stack.getItemMeta();
        deaths_meta.setDisplayName("Tode: " + plugin.getConfig().getInt("stats." + playername + ".deaths"));
        deaths_stack.setItemMeta(deaths_meta);

        ItemStack kills_stack = new ItemStack(Material.IRON_SWORD, plugin.getConfig().getInt("stats." + playername + ".kills"));
        ItemMeta kills_meta = kills_stack.getItemMeta();
        kills_meta.setDisplayName("Getötete Monster: " + plugin.getConfig().getInt("stats." + playername + ".kills"));
        kills_stack.setItemMeta(kills_meta);

        ItemStack placed_stack = new ItemStack(Material.GRASS_BLOCK, plugin.getConfig().getInt("stats." + playername + ".blocks_placed"));
        ItemMeta placed_meta = placed_stack.getItemMeta();
        placed_meta.setDisplayName("Platzierte Blöcke: " + plugin.getConfig().getInt("stats." + playername + ".blocks_placed"));
        placed_stack.setItemMeta(placed_meta);

        ItemStack destroyed_stack = new ItemStack(Material.IRON_PICKAXE, plugin.getConfig().getInt("stats." + playername + ".blocks_destroyed"));
        ItemMeta destroyed_meta = destroyed_stack.getItemMeta();
        destroyed_meta.setDisplayName("Zerstörte Blöcke: " + plugin.getConfig().getInt("stats." + playername + ".blocks_destroyed"));
        destroyed_stack.setItemMeta(destroyed_meta);

        Inventory gui = plugin.getServer().createInventory(null,9,"Stats von " + playername + "[FKStats]");

        gui.setItem(0,deaths_stack);
        gui.setItem(1,kills_stack);
        gui.setItem(2,placed_stack);
        gui.setItem(3,destroyed_stack);

        return gui;
    }
}
