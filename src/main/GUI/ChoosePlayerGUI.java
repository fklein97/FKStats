package main.GUI;

import main.FKStats;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by FKPro on 08.10.2018.
 */
public class ChoosePlayerGUI {

    private FKStats plugin;

    public ChoosePlayerGUI(FKStats plugin){
        this.plugin = plugin;
    }

    public Inventory create(int site){
        String[] playernames_array  = new String[plugin.getConfig().getConfigurationSection("stats").getKeys(false).size()];
        int a = 0;
        for (String s: plugin.getConfig().getConfigurationSection("stats").getKeys(false)) {
            playernames_array[a++] = s;
        }
        ArrayList<String> playernames = new ArrayList<>(Arrays.asList(playernames_array));

        Inventory gui = plugin.getServer().createInventory(null,18,"[FKStats][Choose Player]: Wähle einen Spieler:");

        for(int i = 0; i < 9; i++){
            if(i+(site-1)*9 >= playernames.size()){
                break;
            }
            ItemStack player_stack = new ItemStack(Material.PLAYER_HEAD,1);
            SkullMeta player_meta = (SkullMeta) player_stack.getItemMeta();
            player_meta.setOwningPlayer(Bukkit.getOfflinePlayer(playernames.get(i+(site-1)*9)));
            player_meta.setDisplayName(playernames.get(i+(site-1)*9));
            player_stack.setItemMeta(player_meta);

            gui.setItem(i, player_stack);
        }

        if(site > 1){
            ItemStack back_stack = new ItemStack(Material.GREEN_BANNER,1);
            ItemMeta back_meta = back_stack.getItemMeta();
            back_meta.setDisplayName("Seite zurück");
            back_stack.setItemMeta(back_meta);

            gui.setItem(9, back_stack);
        }

        return gui;
    }
}
