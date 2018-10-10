package main.GUI;

import main.FKStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FKPro on 08.10.2018.
 */
public class PlayerInventoryGUI {

    private FKStats plugin;

    public PlayerInventoryGUI(FKStats plugin){
        this.plugin = plugin;
    }

    public Inventory create(String playername){
        Inventory gui = plugin.getServer().createInventory(null, InventoryType.PLAYER,"[FKStats][PlayerInventoryGUI]: Inventar von " + playername);

        Collection<Player> players = (Collection<Player>) plugin.getServer().getOnlinePlayers();
        ArrayList<String> playernames = new ArrayList<String>();
        Player p = null;

        for(Player player : players) {
            if(player.getDisplayName().equals(playername)) {
                p = player;
                break;
            }
        }

        if(p == null){
            ItemStack offline_stack = new ItemStack(Material.RED_WOOL,1);
            ItemMeta offline_meta = offline_stack.getItemMeta();
            offline_meta.setDisplayName(playername + " ist Offline!");
            offline_stack.setItemMeta(offline_meta);
            for(int i = 0; i < 36; i++){
                gui.setItem(i,offline_stack);
            }
        }
        else{
            for(int i = 0; i < 36; i++){
                gui.setItem(i,p.getInventory().getItem(i));
            }
            //gui.setItem(100,p.getInventory().getItem(100));
            //gui.setItem(101,p.getInventory().getItem(101));
            //gui.setItem(102,p.getInventory().getItem(102));
            //gui.setItem(103,p.getInventory().getItem(103));
            //gui.setItem(-106,p.getInventory().getItem(-106));
        }

        return gui;
    }
}
