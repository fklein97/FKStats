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
public class GUIManager {

    private FKStats plugin;

    public GUIManager(FKStats plugin){
        this.plugin = plugin;
    }

    public Inventory createStatsGUI(Player ip){
        ItemStack deaths_stack;
        if(plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".deaths") > 0) {
            deaths_stack = new ItemStack(Material.SKELETON_SKULL, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".deaths"));
        }
        else{
            deaths_stack = new ItemStack(Material.SKELETON_SKULL, 1);
        }
        ItemMeta deaths_meta = deaths_stack.getItemMeta();
        deaths_meta.setDisplayName("Tode: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".deaths"));
        deaths_stack.setItemMeta(deaths_meta);

        ItemStack kills_stack = new ItemStack(Material.IRON_SWORD, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".kills"));
        ItemMeta kills_meta = kills_stack.getItemMeta();
        kills_meta.setDisplayName("Getötete Monster: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".kills"));
        kills_stack.setItemMeta(kills_meta);

        ItemStack placed_stack = new ItemStack(Material.GRASS_BLOCK, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_placed"));
        ItemMeta placed_meta = placed_stack.getItemMeta();
        placed_meta.setDisplayName("Platzierte Blöcke: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_placed"));
        placed_stack.setItemMeta(placed_meta);

        ItemStack destroyed_stack = new ItemStack(Material.IRON_PICKAXE, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_destroyed"));
        ItemMeta destroyed_meta = destroyed_stack.getItemMeta();
        destroyed_meta.setDisplayName("Zerstörte Blöcke: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_destroyed"));
        destroyed_stack.setItemMeta(destroyed_meta);

        Inventory gui = plugin.getServer().createInventory(null,9,"Stats von " + ip.getDisplayName());

        gui.setItem(0,deaths_stack);
        gui.setItem(1,kills_stack);
        gui.setItem(2,placed_stack);
        gui.setItem(3,destroyed_stack);

        return gui;
    }
}
