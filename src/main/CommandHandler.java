package main;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by FKPro on 01.10.2018.
 */
public class CommandHandler {

    private FKStats plugin;

    private final String FKSTATS = "[FKSTATS]: ";
    private final String USEFORHELP = "Nutze '/fkstats h' für Hilfe!";
    private final String CMDDESCRIPTION_HELP = "/fkstats h - öffnet das hier";
    private final String CMDDESCRIPTION_STATS = "/fkstats s - zeigt deine eigenen Stats";
    private final String CMDDESCRIPTION_STATSP= "/fkstats s Spieler - zeigt die Stats eines Spielers an";

    public CommandHandler(FKStats plugin){
        this.plugin = plugin;
    }

    public void handleCommand(CommandSender sender , Command command, String[] args){
        Player p = null;
        if(sender instanceof Player){
            p = (Player) sender;
        }

        if(command.getName().equals("fkstats")){
            if (args.length == 0){
                if(p != null){
                    p.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + USEFORHELP);
                }
            }
            else if (args.length == 1){
                if(args[0].equals("h") && p != null){
                    printhelp(p);
                }
                else if(args[0].equals("s") && p != null) {
                    printStats(p);
                }
                else{
                    p.sendMessage(ChatColor.GRAY + USEFORHELP);
                }
            }
            else if (args.length == 2){
                if(args[0].equals("s") && p != null){
                    printStats(p, args[1]);
                }
                else if(args[0].equals("lastseen") && p != null){
                    printLastSeen(p, args[1]);
                }
            }
        }

    }

    private void printLastSeen(Player p, String statsplayer) {
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

        plugin.saveConfigChanges();
        p.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + statsplayer + " wurde zuletzt gesehen:");
        p.sendMessage(ChatColor.GRAY + lastseen);
    }

    private void printhelp(Player player){
        player.sendMessage(ChatColor.DARK_AQUA + plugin.getDescription().getName() + " v." + plugin.getDescription().getVersion());
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_HELP);
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_STATS);
        player.sendMessage(ChatColor.GRAY + CMDDESCRIPTION_STATSP);
    }

    private void printStats(Player player){
        printStats(player, player.getDisplayName());
    }

    private void printStats(Player player, String statsplayer){
        plugin.saveConfigChanges();
        player.sendMessage(ChatColor.DARK_AQUA + FKSTATS + ChatColor.GRAY + "Hier sind die Stats von " + statsplayer + ":");
        player.sendMessage(ChatColor.GRAY + "Tode: " + plugin.getConfig().getInt("stats."+ statsplayer + ".deaths"));
        player.sendMessage(ChatColor.GRAY + "Getötete Mobs: " + plugin.getConfig().getInt("stats."+ statsplayer + ".kills"));
        player.sendMessage(ChatColor.GRAY + "Zerstörte Blöcke: " + plugin.getConfig().getInt("stats."+ statsplayer + ".blocks_destroyed"));
        player.sendMessage(ChatColor.GRAY + "Platzierte Blöcke: " + plugin.getConfig().getInt("stats."+ statsplayer + ".blocks_placed"));
    }
}
