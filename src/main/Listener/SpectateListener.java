package main.Listener;

import main.FKStats;
import main.GUI.StatsGUI;
import main.Spectating.SpectateHandler;
import main.Spectating.SpectatePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by FKPro on 21.09.2018.
 */
public class SpectateListener implements org.bukkit.event.Listener{
    private FKStats plugin;
    private SpectateHandler sh;

    public SpectateListener(FKStats plugin){
        this.plugin = plugin;
        sh = new SpectateHandler(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(event.getPlayer() != null){
            for(SpectatePlayer sp : plugin.spectators){
                if(sp.getPlayer() == event.getPlayer()){
                    sh.stopSpectating(sp);
                    break;
                }
                else if(sp.getTarget() == event.getPlayer()){
                    sh.stopSpectating(sp);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(event.getPlayer() != null){
            for(SpectatePlayer sp : plugin.spectators){
                if(sp.getPlayer() == event.getPlayer()){
                    event.setCancelled(true);
                    break;
                }
                else if(sp.getTarget() == event.getPlayer()){
                    sp.getPlayer().teleport(sp.getTarget());
                }
            }
        }
    }

}
