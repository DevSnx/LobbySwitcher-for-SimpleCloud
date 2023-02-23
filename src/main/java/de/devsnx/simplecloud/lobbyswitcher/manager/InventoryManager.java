package de.devsnx.simplecloud.lobbyswitcher.manager;

import de.devsnx.simplecloud.lobbyswitcher.Lobbyswitcher;
import de.devsnx.simplecloud.lobbyswitcher.utils.ItemCreator;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.plugin.startup.CloudPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public class InventoryManager {

    private Inventory inv;

    public InventoryManager(){

        inv = Bukkit.createInventory(null,
                Lobbyswitcher.getInstance().getConfig().getInt("gui.rows"),
                Lobbyswitcher.getInstance().getConfig().getString("gui.name"));

        refreshLobbys();
    }

    private void refreshLobbys(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Lobbyswitcher.getInstance(), new Runnable() {
            @Override
            public void run() {

                if(CloudAPI.getInstance().getCloudServiceGroupManager().getLobbyGroups() != null){

                    FileConfiguration cfg = Lobbyswitcher.getInstance().getConfig();

                    for(ICloudService service : CloudAPI.getInstance().getCloudServiceManager().getAllCachedObjects()){

                        if(service.getName().startsWith("Lobby") || service.getName().startsWith("PremiumLobby") || service.getName().startsWith("SilentLobby")){

                            int servers = 0;

                            if(service.isOnline()){

                                if(CloudPlugin.getInstance().thisService().getName().equalsIgnoreCase(service.getName())){

                                    ArrayList<String> lore = new ArrayList<>();

                                    for(String message : cfg.getStringList("layout.connected.item")){
                                        message = message.replace("%MAXPLAYER%", String.valueOf(service.getMaxPlayers()));
                                        message = message.replace("%ONLINEPLAYERS%", String.valueOf(service.getOnlineCount()));
                                        message = message.replace("%MOTD%", service.getMOTD());

                                        lore.add(message);
                                    }

                                    inv.setItem(servers, new ItemCreator().material(cfg.getString("layout.connected.item")).lore(lore).build());

                                    servers++;

                                }else{

                                    if(service.getOnlineCount() == service.getMaxPlayers()){

                                        ArrayList<String> lore = new ArrayList<>();

                                        for(String message : cfg.getStringList("layout.full.item")){
                                            message = message.replace("%MAXPLAYER%", String.valueOf(service.getMaxPlayers()));
                                            message = message.replace("%ONLINEPLAYERS%", String.valueOf(service.getOnlineCount()));
                                            message = message.replace("%MOTD%", service.getMOTD());

                                            lore.add(message);
                                        }

                                        inv.setItem(servers, new ItemCreator().material(cfg.getString("layout.empty.item")).lore(lore).build());

                                        servers++;

                                    }else if(service.getOnlineCount() < service.getMaxPlayers()){

                                        ArrayList<String> lore = new ArrayList<>();

                                        for(String message : cfg.getStringList("layout.empty.item")){
                                            message = message.replace("%MAXPLAYER%", String.valueOf(service.getMaxPlayers()));
                                            message = message.replace("%ONLINEPLAYERS%", String.valueOf(service.getOnlineCount()));
                                            message = message.replace("%MOTD%", service.getMOTD());

                                            lore.add(message);
                                        }

                                        inv.setItem(servers, new ItemCreator().material(cfg.getString("layout.empty.item")).lore(lore).build());

                                        servers++;

                                    }

                                }

                            }else{

                                ArrayList<String> lore = new ArrayList<>();

                                for(String message : cfg.getStringList("layout.offline.item")){
                                    message = message.replace("%MAXPLAYER%", String.valueOf(service.getMaxPlayers()));
                                    message = message.replace("%ONLINEPLAYERS%", String.valueOf(service.getOnlineCount()));
                                    message = message.replace("%MOTD%", service.getMOTD());

                                    lore.add(message);
                                }

                                inv.setItem(servers, new ItemCreator().material(cfg.getString("layout.offline.item")).lore(lore).build());

                                servers++;

                            }

                        }else{

                            inv.setItem(4, new ItemCreator().material("RED_WOOL").displayName("§cKeine Lobbyserver gefunden!").build());

                        }

                    }

                }else{

                    inv.setItem(4, new ItemCreator().material("RED_WOOL").displayName("§cKeine Lobbyserver gefunden!").build());

                }

            }

        }, 60L, 1);

    }


    public Inventory getInv() {

        return inv;

    }

}
