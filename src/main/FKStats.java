package main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by FKPro on 21.09.2018.
 */
public class FKStats extends JavaPlugin{
    private Listener l;
    private void registerListener() {
        l = new Listener(this);
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
