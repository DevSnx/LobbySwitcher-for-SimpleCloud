package de.devsnx.simplecloud.lobbyswitcher.listener;

import de.devsnx.simplecloud.lobbyswitcher.Lobbyswitcher;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractItem(PlayerInteractEvent event){

        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getPlayer().getItemInHand();

        FileConfiguration cfg = Lobbyswitcher.getInstance().getConfig();

        if(item.getItemMeta().getDisplayName().equalsIgnoreCase(cfg.getString("itemonjoin.name"))){

            event.setCancelled(true);

            if(item.getType().toString() == cfg.getString("itemonjoin.item")){

                player.openInventory(Lobbyswitcher.getInstance().getInventoryManager().getInv());

            }

        }

    }

}
