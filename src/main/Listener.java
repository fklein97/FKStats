package main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Created by FKPro on 21.09.2018.
 */
public class Listener implements org.bukkit.event.Listener{
    private FKStats plugin;

    public Listener(FKStats plugin){
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        plugin.getConfig().set("stats." + p.getDisplayName() + ".deaths", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths") + 1);
        p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GRAY + " Tode: " + plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GRAY + " Tode: " + plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths"));
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity e = event.getEntity();
        if(e instanceof Monster){
            Entity killer = ((Monster) e).getKiller();
            if(killer instanceof Player){
                Player p = (Player) killer;
                plugin.getConfig().set("stats." + p.getDisplayName() + ".kills", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".kills") + 1);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_placed", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_placed") + 1);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_destroyed", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_destroyed") + 1);
        }
    }
}
