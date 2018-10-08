package main.Commands;

import main.FKStats;
import main.GUI.StatsGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

;

/**
 * Created by FKPro on 01.10.2018.
 */
public class CommandHandler {

    private FKStats plugin;
    private final String FKSTATS = "[FKSTATS]: ";
    private final String USEFORHELP = "Nutze '/fkstats h' für Hilfe!";


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
                    Commands.startMenu(p,plugin);
                }
            }
            else if (args.length == 1){
                if(args[0].equals("h") && p != null){
                    Commands.printhelp(p, plugin);
                }
                else if(args[0].equals("s") && p != null) {
                    Commands.printStats(p, plugin);
                }
                else if(args[0].equals("ownstatsgui") && p != null) {
                    Commands.ownStatsGUI(p, plugin);
                }
                else{
                    p.sendMessage(ChatColor.GRAY + USEFORHELP);
                }
            }
            else if (args.length == 2){
                if(args[0].equals("s") && p != null){
                    Commands.printStats(p, args[1], plugin);
                }
                else if(args[0].equals("lastseen") && p != null){
                    Commands.printLastSeen(p, args[1], plugin);
                }
            }
        }

    }


}
