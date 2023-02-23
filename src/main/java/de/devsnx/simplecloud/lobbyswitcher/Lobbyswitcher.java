package de.devsnx.simplecloud.lobbyswitcher;

import de.devsnx.simplecloud.lobbyswitcher.manager.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author DevSnx
 * @since 23.02.2023
 */
public final class Lobbyswitcher extends JavaPlugin {
    
    public static Lobbyswitcher instance;
    public InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        inventoryManager = new InventoryManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }

    public static Lobbyswitcher getInstance() {
        return instance;
    }
}
