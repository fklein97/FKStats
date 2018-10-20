package main.Commands;

import main.FKStats;
import main.GUI.ChoosePlayerGUI;
import main.GUI.StatsGUI;
import main.Spectating.SpectateHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FKPro on 08.10.2018.
 */
public class Commands {
    private final static String FKSTATS = "[FKSTATS]: ";
    private final static String CMDDESCRIPTION_HELP = "/fkstats h - öffnet das hier";
    private final static String CMDDESCRIPTION_STATS = "/fkstats s - zeigt deine eigenen Stats";
    private final static String CMDDESCRIPTION_STATSP= "/fkstats s Spieler - zeigt die Stats eines Spielers an";

    public static void ownStatsGUI(Player p, FKStats plugin){
        p.openInventory(new StatsGUI(plugin).create(p.getDisplayName()));
    }

    public static void printLastSeen(Player p, String statsplayer, FKStats plugin) {
        String lastseen = "Niemals";
        Collection<Player> players = (Collection<Player>) plugin.getServer().getOnlinePlayers();
        ArrayList<String> playernames = new ArrayList<String>();
        for(Player player : players){
            playernames.add(player.getDisplayName());
        }
        if(playernames.contains(statsplayer)){
            lastseen = "Der Spieler ist momentan online";
        }
        else{
            if(plugin.getConfig().contains("stats." + statsplayer)) {
                lastseen = plugin.getConfig().getString("stats."+ statsplayer + ".last_seen");
            }
        }

        p.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + statsplayer + " wurde zuletzt gesehen:");
        p.sendMessage(ChatColor.GRAY + lastseen);
    }

    public static void printhelp(Player player, FKStats plugin){
        player.sendMessage(ChatColor.DARK_AQUA + plugin.getDescription().getName() + " v." + plugin.getDescription().getVersion());
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_HELP);
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_STATS);
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_STATSP);
    }

    public static void printStats(Player player, FKStats plugin){
        printStats(player, player.getDisplayName(), plugin);
    }

    public static void printStats(Player player, String statsplayer, FKStats plugin){
        plugin.saveConfigChanges();
        player.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + "Hier sind die Stats von " + statsplayer + ":");
        player.sendMessage(ChatColor.GRAY + "Tode: " + plugin.getConfig().getInt("stats."+ statsplayer + ".deaths"));
        player.sendMessage(ChatColor.GRAY + "Getötete Mobs: " + plugin.getConfig().getInt("stats."+ statsplayer + ".kills"));
        player.sendMessage(ChatColor.GRAY + "Zerstörte Blöcke: " + plugin.getConfig().getInt("stats."+ statsplayer + ".blocks_destroyed"));
        player.sendMessage(ChatColor.GRAY + "Platzierte Blöcke: " + plugin.getConfig().getInt("stats."+ statsplayer + ".blocks_placed"));
    }

    public static void startMenu(Player p, FKStats plugin) {
        p.openInventory(new ChoosePlayerGUI(plugin).create(1));
    }

    public static void exitSpectating(Player p, FKStats plugin) {
            SpectateHandler sh = new SpectateHandler(plugin);
            sh.stopSpectating(p);
    }
}
