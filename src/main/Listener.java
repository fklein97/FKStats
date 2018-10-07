package main;

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
    private DateFormat df = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

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
        Date now = new Date();
        plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
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

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            Date now = new Date();
            plugin.getConfig().set("stats." + p.getDisplayName() + ".last_seen", df.format(now));
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event){
        if(event.getPlayer() != null){
            Player p = event.getPlayer();
            if(event.getRightClicked() != null && event.getRightClicked() instanceof Player){
                Player ip = (Player) event.getRightClicked();
                Inventory gui = createStatsInventory(ip);


                p.openInventory(gui);
            }
        }
    }

    public Inventory createStatsInventory(Player ip){
        ItemStack deaths_stack = new ItemStack(Material.SKELETON_SKULL, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".deaths"));
        ItemMeta deaths_meta = deaths_stack.getItemMeta();
        deaths_meta.setDisplayName("Tode: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".deaths"));
        deaths_meta.addEnchant(Enchantment.DURABILITY,1,true);
        deaths_stack.setItemMeta(deaths_meta);

        ItemStack kills_stack = new ItemStack(Material.IRON_SWORD, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".kills"));
        ItemMeta kills_meta = kills_stack.getItemMeta();
        kills_meta.setDisplayName("Getötete Monster: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".kills"));
        kills_meta.addEnchant(Enchantment.DURABILITY,1,true);
        kills_stack.setItemMeta(kills_meta);

        ItemStack placed_stack = new ItemStack(Material.GRASS_BLOCK, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_placed"));
        ItemMeta placed_meta = placed_stack.getItemMeta();
        placed_meta.setDisplayName("Platzierte Blöcke: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_placed"));
        placed_meta.addEnchant(Enchantment.DURABILITY,1,true);
        placed_stack.setItemMeta(placed_meta);

        ItemStack destroyed_stack = new ItemStack(Material.IRON_PICKAXE, plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_destroyed"));
        ItemMeta destroyed_meta = destroyed_stack.getItemMeta();
        destroyed_meta.setDisplayName("Zerstörte Blöcke: " + plugin.getConfig().getInt("stats." + ip.getDisplayName() + ".blocks_destroyed"));
        destroyed_meta.addEnchant(Enchantment.DURABILITY,1,true);
        destroyed_stack.setItemMeta(destroyed_meta);

        Inventory gui = plugin.getServer().createInventory(null,9,"Stats von " + ip.getDisplayName());
        gui.setItem(0,deaths_stack);
        gui.setItem(1,kills_stack);
        gui.setItem(2,placed_stack);
        gui.setItem(3,destroyed_stack);

        return gui;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(event.getInventory().getTitle().startsWith("Stats")){
            event.setCancelled(true);
        }
    }
}
