package main.Listener;

import main.FKStats;
import main.GUI.PlayerProfileGUI;
import main.GUI.StatsGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_14_R1.block.CraftCreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FKPro on 21.09.2018.
 */
public class Listener implements org.bukkit.event.Listener{
    private FKStats plugin;

    private DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
    private final String FKSTATS = "[FKSTATS]: ";

    public Listener(FKStats plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            if(event.getRightClicked() != null && event.getRightClicked() instanceof Player){
                Player ip = (Player) event.getRightClicked();
                p.openInventory(new PlayerProfileGUI(plugin).create(ip.getDisplayName()));
            }
        }
    }

/*    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getPlayer() != null) {
            Player p = event.getPlayer();
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(event.getClickedBlock().getType() == Material.SPAWNER){
                    BlockState blockstate = event.getClickedBlock().getState();
                    CreatureSpawner cs = ((CreatureSpawner)blockstate);
                        cs.setRequiredPlayerRange(30);
                        p.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + "Dieser Spawner hat nun eine Range von 30");
                        blockstate.update();

                }
            }
        }
    }*/

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        if(event.getPlayer() != null) {
            Player p = event.getPlayer();
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() { public void run() {plugin.refreshPlayerinList(p);}} ,20 * 5);
        }
    }

}
