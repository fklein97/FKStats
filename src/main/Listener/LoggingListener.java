package main.Listener;

import main.Classes.PlayerScheduler;
import main.FKStats;
import main.GUI.StatsGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FKPro on 08.10.2018.
 */
public class LoggingListener implements org.bukkit.event.Listener {
    private FKStats plugin;
    private StatsGUI gm;

    private DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

    public LoggingListener(FKStats plugin){
        this.plugin = plugin;
        gm = new StatsGUI(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        if(plugin.stat_logging = true) {
            Player p = event.getEntity();
            int deaths = plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths");
            deaths++;
            plugin.getConfig().set("stats." + p.getDisplayName() + ".deaths", deaths);
            plugin.saveConfigChanges();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        plugin.refreshPlayerinList(p);
        Date now = new Date();
        plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
        plugin.saveConfigChanges();

        int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if(p.isOnline()) {
                    plugin.refreshPlayerinList(p);
                }
            }
            } ,20 * 5 ,20 * 5);
        plugin.schedulers.add(new PlayerScheduler(id,p));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(plugin.stat_logging = true) {
            Entity e = event.getEntity();
            if (e instanceof Monster) {
                Entity killer = ((Monster) e).getKiller();
                if (killer instanceof Player) {
                    Player p = (Player) killer;
                    int kills = plugin.getConfig().getInt("stats." + p.getDisplayName() + ".kills");
                    kills++;
                    plugin.getConfig().set("stats." + p.getDisplayName() + ".kills", kills);
                    plugin.saveConfigChanges();
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(plugin.stat_logging = true) {
            if (event.getPlayer() != null) {
                Player p = event.getPlayer();
                int placed = plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_placed");
                placed++;
                plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_placed", placed);
                plugin.saveConfigChanges();
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(plugin.stat_logging = true) {
            if (event.getPlayer() != null) {
                Player p = event.getPlayer();
                int destroyed = plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_destroyed");
                //plugin.getServer().broadcastMessage(""+ destroyed);
                destroyed++;
                //plugin.getServer().broadcastMessage(""+ destroyed);
                plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_destroyed", destroyed);
                plugin.saveConfigChanges();
                //plugin.getServer().broadcastMessage(p.getDisplayName() + " hat einen Block zerstört (Debug)");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            Date now = new Date();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
            plugin.saveConfigChanges();

            int i = -1;
            for(PlayerScheduler ps : plugin.schedulers){
                if(ps.p == p){
                    i = plugin.schedulers.indexOf(ps);
                    Bukkit.getScheduler().cancelTask(ps.id);
                }
            }

            if(i != -1){
                plugin.schedulers.remove(i);
            }
        }
    }
}
