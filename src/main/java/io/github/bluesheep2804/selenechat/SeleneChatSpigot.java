package io.github.bluesheep2804.selenechat;

import io.github.bluesheep2804.selenechat.listener.ChatListenerSpigot;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public class SeleneChatSpigot extends JavaPlugin {
    public static Server server;
    private static SeleneChatSpigot instance;
    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        server = getServer();
        server.getPluginManager().registerEvents(new ChatListenerSpigot(), this);
        this.adventure = BukkitAudiences.create(this);

        server.getMessenger().registerOutgoingPluginChannel(this, "selenechat:message");
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public static SeleneChatSpigot getInstance() {
        if (instance == null) {
            instance = (SeleneChatSpigot) Bukkit.getPluginManager().getPlugin("selenechat");
        }
        return instance;
    }

    public void sendPluginMessage(byte[] msg) {
        server.sendPluginMessage(this, "selenechat:message", msg);
    }
}
