package main.Spectating;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by FKPro on 10.10.2018.
 */
public class SpectatePlayer {
    private Player player;
    private Inventory inventory;
    private Player target;
    private Location location;

    public SpectatePlayer(Player player, Inventory inventory, Player target, Location location){
        this.player = player;
        this.inventory = inventory;
        this.target = target;
        this.location = location;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
