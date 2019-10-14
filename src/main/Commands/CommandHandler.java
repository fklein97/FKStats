package main.Commands;

import main.FKStats;
import main.GUI.StatsGUI;
import main.Spectating.SpectatePlayer;
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
                    if(isNoSpecator(p)) {
                        Commands.startMenu(p, plugin);
                    }
                }
            }
            else if (args.length == 1){
                if(args[0].equals("h") && p != null){
                    Commands.printhelp(p, plugin);
                }
                else if(args[0].equals("exit") && p != null) {
                    Commands.exitSpectating(p, plugin);
                }
                else if(args[0].equals("statlogging") && p != null) {
                    Commands.toggleStatLogging(p, plugin);
                }
                else{
                    p.sendMessage(ChatColor.GRAY + USEFORHELP);
                }
            }
            else if (args.length == 2){
                if(args[0].equals("location") && p != null) {
                    if(args[1].equals("list")){
                        Commands.listLocation(p,plugin);
                    }
                }
            }
            else if (args.length == 3){
                if(args[0].equals("location") && p != null) {
                    if(args[1].equals("add")){
                        Commands.addLocation(args[2],p,plugin);
                    }
                    else if(args[1].equals("delete")){
                        Commands.deleteLocation(args[2],p,plugin);

                    }
                }
            }
        }

    }

    private boolean isNoSpecator(Player p){
        for(SpectatePlayer sp : plugin.spectators){
            if(sp.getPlayer() == p){
                return false;
            }
        }

        return true;
    }

}
