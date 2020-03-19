package main;

import main.Classes.FKLocation;
import main.Classes.PlayerScheduler;
import main.Commands.CommandHandler;
import main.Listener.GUIListener;
import main.Listener.Listener;
import main.Listener.LoggingListener;
import main.Listener.SpectateListener;
import main.Spectating.SpectatePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by FKPro on 21.09.2018.
 */
public class FKStats extends JavaPlugin{
    private Listener l;
    private GUIListener guil;
    private LoggingListener ll;
    private SpectateListener sl;

    public ArrayList<SpectatePlayer> spectators = new ArrayList<SpectatePlayer>();
    public boolean stat_logging = true;
    public ArrayList<FKLocation> locations = new ArrayList<>();
    public ArrayList<PlayerScheduler> schedulers = new ArrayList<>();

    private void registerListener() {
        l = new Listener(this);
        guil = new GUIListener(this);
        ll = new LoggingListener(this);
        sl = new SpectateListener(this);
    }

    @Override
    public void onEnable() {
        System.out.println("FKStats v" + this.getDescription().getVersion() + " enabled!");
        stat_logging = this.getConfig().getBoolean("config.stat_logging", true);
        loadLocations();
        registerListener();
    }

    private void loadLocations() {
        int i = 0;
        if(this.getConfig().isConfigurationSection("locations")) {
            for (String s : this.getConfig().getConfigurationSection("locations").getKeys(false)) {
                World loc_w = this.getServer().getWorld(this.getConfig().getString("locations." + s + ".w"));
                int loc_x = this.getConfig().getInt("locations." + s + ".x");
                int loc_y = this.getConfig().getInt("locations." + s + ".y");
                int loc_z = this.getConfig().getInt("locations." + s + ".z");

                Location loc = new Location(loc_w, loc_x, loc_y, loc_z);

                locations.add(new FKLocation(loc, s));
                i++;
            }
        }


    }


    @Override
    public void onDisable() {
        saveConfigChanges();
        System.out.println("FKStats v" + this.getDescription().getVersion() + " disabled!");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandHandler cmdHandler = new CommandHandler(this);
        cmdHandler.handleCommand(sender, command, args);

        return true;
    }

    public void saveConfigChanges(){
        this.saveConfig();
    }

    public void refreshPlayerinList(Player p) {
        int deathcount = this.getConfig().getInt("stats." + p.getDisplayName() + ".deaths");

        if(p.getWorld().getEnvironment() == World.Environment.NETHER) {
            p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.RED + " [Im Nether] " + ChatColor.GOLD + "[Tode: " + deathcount + "]");
        }
        else if(p.getWorld().getEnvironment() == World.Environment.THE_END) {
            p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.YELLOW + " [Im End] " + ChatColor.GOLD + "[Tode: " + deathcount + "]");
        }
        else{
            double distance = -1;
            FKLocation closest = null;
            for(FKLocation loc : locations){
                if(distance == -1 || p.getLocation().distance(loc.location) < distance){
                    closest = loc;
                    distance = p.getLocation().distance(loc.location);
                }
            }
            if(closest != null) {
                p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GREEN + " [" + closest.name + "] " + ChatColor.GOLD + "[Tode: " + deathcount + "]");
            }
            else{
                p.setPlayerListName(ChatColor.DARK_AQUA + p.getDisplayName() + ChatColor.GREEN + " [In der Oberwelt] " + ChatColor.GOLD + "[Tode: " + deathcount + "]");
            }
        }
    }
}
