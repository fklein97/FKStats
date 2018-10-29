package main.GUI;

import main.FKStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FKPro on 08.10.2018.
 */
public class PlayerProfileGUI {

    private FKStats plugin;

    public PlayerProfileGUI(FKStats plugin){
        this.plugin = plugin;
    }

    public Inventory create(String playername){
        Inventory gui = plugin.getServer().createInventory(null,9,"[FKStats][PlayerProfileGUI]: Profil von " + playername);

        ItemStack lastseen_stack;
        String lastseen = getLastSeen(playername);

        if(lastseen.equals("Der Spieler ist momentan online")){
            lastseen_stack = new ItemStack(Material.GREEN_WOOL,1);
        }
        else{
            lastseen_stack = new ItemStack(Material.RED_WOOL,1);
        }
        ItemMeta lastseen_meta = lastseen_stack.getItemMeta();
        lastseen_meta.setDisplayName("Zuletzt Online: " + lastseen);
        lastseen_stack.setItemMeta(lastseen_meta);
        gui.setItem(7,lastseen_stack);

        ItemStack head_stack = new ItemStack(Material.PLAYER_HEAD,1);
        SkullMeta head_meta = (SkullMeta) head_stack.getItemMeta();
        head_meta.setOwningPlayer(Bukkit.getOfflinePlayer(playername));
        head_meta.setDisplayName("Profil von " + playername);
        head_stack.setItemMeta(head_meta);
        gui.setItem(8,head_stack);

        ItemStack stats_stack = new ItemStack(Material.IRON_PICKAXE,1);
        ItemMeta stats_meta = stats_stack.getItemMeta();
        stats_meta.setDisplayName("Zeige Stats an");
        stats_stack.setItemMeta(stats_meta);
        gui.setItem(0,stats_stack);

        ItemStack inventory_stack = new ItemStack(Material.CHEST,1);
        ItemMeta inventory_meta = inventory_stack.getItemMeta();
        inventory_meta.setDisplayName("Zeige Inventar");
        inventory_stack.setItemMeta(inventory_meta);
        gui.setItem(1,inventory_stack);

        ItemStack spectate_stack = new ItemStack(Material.GLASS_PANE,1);
        ItemMeta spectate_meta = spectate_stack.getItemMeta();
        spectate_meta.setDisplayName("Zuschauen");
        spectate_stack.setItemMeta(spectate_meta);
        gui.setItem(2,spectate_stack);

        return gui;
    }

    public String getLastSeen(String playername){
        String lastseen = "Niemals";
        Collection<Player> players = (Collection<Player>) plugin.getServer().getOnlinePlayers();
        ArrayList<String> playernames = new ArrayList<String>();

        for(Player player : players){
            playernames.add(player.getDisplayName());
        }
        if(playernames.contains(playername)){
            lastseen = "Der Spieler ist momentan online";
        }
        else{
            if(plugin.getConfig().contains("stats." + playername)) {
                lastseen = plugin.getConfig().getString("stats."+ playername + ".last_seen");
            }
        }

        if(lastseen == null){
            lastseen = "Noch nie";
        }

        return lastseen;
    }
}
