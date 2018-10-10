package main.Spectating;

import main.FKStats;
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
        player.getInventory().clear();

        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(target);
    }

    public void stopSpectating(Player player){
        for(SpectatePlayer sp : plugin.spectators){
            if(sp.getPlayer() == player){
                stopSpectating(sp);
            }
        }
    }

    public void stopSpectating(SpectatePlayer splayer){
        Player p = splayer.getPlayer();
        p.getInventory().clear();
        for(int i = 0; i < 36; i++){
            p.getInventory().setItem(i, splayer.getInventory().getItem(i));
        }
        p.teleport(splayer.getLocation());
        p.setGameMode(GameMode.SURVIVAL);

    }
}
