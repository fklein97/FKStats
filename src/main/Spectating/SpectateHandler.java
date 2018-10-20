package main.Spectating;

import main.FKStats;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

/**
 * Created by FKPro on 10.10.2018.
 */
public class SpectateHandler {

    private FKStats plugin;

    public SpectateHandler(FKStats plugin){
        this.plugin = plugin;
    }

    public void startSpectating(Player player, Player target){
        SpectatePlayer splayer = new SpectatePlayer(player, player.getInventory(), target, player.getLocation());
        plugin.spectators.add(splayer);

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(target);
        player.sendMessage(ChatColor.DARK_AQUA + "[FKStats]" + ChatColor.RED + "Zum Verlassen des Zuschauermodus : '/fkstats exit' nutzen!");
    }

    public void stopSpectating(Player player){
        SpectatePlayer spectator = null;
        for(SpectatePlayer sp : plugin.spectators){
            if(sp.getPlayer() == player){
                spectator = sp;
            }
        }

        if(spectator != null){
            stopSpectating(spectator);
        }
    }

    public void stopSpectating(SpectatePlayer splayer){
        Player p = splayer.getPlayer();
        for(int i = 0; i < 36; i++){
            p.getInventory().setItem(i, splayer.getInventory().getItem(i));
        }
        p.teleport(splayer.getLocation());
        p.setGameMode(GameMode.SURVIVAL);
        plugin.spectators.remove(splayer);
    }
}
