package main.Listener;

import main.FKStats;
import main.GUI.GUIManager;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by FKPro on 08.10.2018.
 */
public class GUIListener implements org.bukkit.event.Listener{
    private FKStats plugin;
    private GUIManager gm;

    public GUIListener(FKStats plugin){
        this.plugin = plugin;
        gm = new GUIManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getTitle().startsWith("Stats")){
            event.setCancelled(true);
        }
    }
}
