package main.Listener;

import main.FKStats;
import main.GUI.StatsGUI;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by FKPro on 08.10.2018.
 */
public class GUIListener implements org.bukkit.event.Listener{
    private FKStats plugin;
    private StatsGUI gm;

    public GUIListener(FKStats plugin){
        this.plugin = plugin;
        gm = new StatsGUI(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getTitle().startsWith("[FKStats]")){
            event.setCancelled(true);
        }
    }
}
