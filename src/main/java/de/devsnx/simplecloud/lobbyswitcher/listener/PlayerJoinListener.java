package de.devsnx.simplecloud.lobbyswitcher.listener;

import de.devsnx.simplecloud.lobbyswitcher.Lobbyswitcher;
import de.devsnx.simplecloud.lobbyswitcher.utils.ItemCreator;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        event.getPlayer().getInventory().setItem(Lobbyswitcher.getCfg().getInt("itemonjoin.slot"), new ItemCreator()
                .displayName(Lobbyswitcher.getCfg().getString("itemonjoin.name"))
                .material(Material.valueOf(Lobbyswitcher.getCfg().getString("itemonjoin.item")))
                .build());

    }

}