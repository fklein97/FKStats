package main;

import main.Commands.CommandHandler;
import main.Listener.GUIListener;
import main.Listener.Listener;
import main.Listener.LoggingListener;
import main.Listener.SpectateListener;
import main.Spectating.SpectatePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

    private void registerListener() {
        l = new Listener(this);
        guil = new GUIListener(this);
        ll = new LoggingListener(this);
        sl = new SpectateListener(this);
    }

    @Override
    public void onEnable() {
        System.out.println("FKStats v" + this.getDescription().getVersion() + " enabled!");
        registerListener();
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
//        this.reloadConfig();
    }

}
