package main.Listener;

import main.FKStats;
import main.GUI.GUIManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by FKPro on 21.09.2018.
 */
public class Listener implements org.bukkit.event.Listener{
    private FKStats plugin;
    private GUIManager gm;

    private DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

    public Listener(FKStats plugin){
        this.plugin = plugin;
        gm = new GUIManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        plugin.getConfig().set("stats." + p.getDisplayName() + ".deaths", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths") + 1);
        plugin.saveConfigChanges();
        p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GRAY + " Tode: " + plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths"));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GRAY + " Tode: " + plugin.getConfig().getInt("stats." + p.getDisplayName() + ".deaths"));
        Date now = new Date();
        plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
        plugin.saveConfigChanges();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        Entity e = event.getEntity();
        if(e instanceof Monster){
            Entity killer = ((Monster) e).getKiller();
            if(killer instanceof Player){
                Player p = (Player) killer;
                plugin.getConfig().set("stats." + p.getDisplayName() + ".kills", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".kills") + 1);
                plugin.saveConfigChanges();
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_placed", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_placed") + 1);
            plugin.saveConfigChanges();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".blocks_destroyed", plugin.getConfig().getInt("stats." + p.getDisplayName() + ".blocks_destroyed") + 1);
            plugin.saveConfigChanges();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            Date now = new Date();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
            plugin.saveConfigChanges();
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            if(event.getRightClicked() != null && event.getRightClicked() instanceof Player){
                Player ip = (Player) event.getRightClicked();
                Inventory gui = gm.createStatsGUI(ip);

                p.openInventory(gui);
            }
        }
    }

}
