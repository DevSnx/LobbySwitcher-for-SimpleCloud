package de.devsnx.simplecloud.lobbyswitcher.manager;

import de.devsnx.simplecloud.lobbyswitcher.Lobbyswitcher;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public class InventoryManager {

    private Inventory inv;

    public InventoryManager(){

        inv = Bukkit.createInventory(null,
                Lobbyswitcher.getInstance().getConfig().getInt(""),
                Lobbyswitcher.getInstance().getConfig().getString("").replace("&", "§"));

        refreshLobbys();
    }

    private void refreshLobbys(){










    }

    public Inventory getInv() {
        return inv;
    }
}
