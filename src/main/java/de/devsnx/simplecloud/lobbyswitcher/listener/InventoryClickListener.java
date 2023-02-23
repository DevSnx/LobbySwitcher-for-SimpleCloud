package de.devsnx.simplecloud.lobbyswitcher.listener;

import de.devsnx.simplecloud.lobbyswitcher.Lobbyswitcher;
import org.bukkit.event.Listener;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.player.SimpleCloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public class InventoryClickListener implements Listener {


    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (event.getHotbarButton() != -1) {
            item = event.getView().getBottomInventory().getItem(event.getHotbarButton());
            if (item == null) {
                item = event.getCurrentItem();
            }
        }
        if ((item == null) || (item.getType() == Material.AIR)) {
            return;
        }

        if(event.getView().getTitle().equalsIgnoreCase(Lobbyswitcher.getCfg().getString("gui.name"))){

            event.setCancelled(true);

            String serverName = item.getItemMeta().getDisplayName().replace(Lobbyswitcher.getCfg().getString("gui.item-color"), "");

            if(item.getType() == Material.valueOf(Lobbyswitcher.getCfg().getString("layout.connected.item"))){
                getMessages("messages.connected", player, serverName);
                return;
            }

            if(CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getName()) == null){
                return;
            }

            ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player.getName());

            if(CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(serverName) == null){
                return;
            }

            player.closeInventory();
            ICloudService service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(serverName);

            getMessages("messages.send", player, serverName);

            cloudPlayer.connect(service);


        }

    }

    private void getMessages(String path, Player player, String service) {

        String message = Lobbyswitcher.getCfg().getString(path);

        message = message.replace("%SERVER%", service);

        player.sendMessage(message);

    }

}