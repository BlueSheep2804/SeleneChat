package io.github.bluesheep2804.selenechat;

import io.github.bluesheep2804.selenechat.listener.ChatListenerBungee;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SeleneChatBungee extends Plugin {
    public static ProxyServer proxy;
    public BungeeAudiences adventure;

    public @NotNull BungeeAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Cannot retrieve audience provider while plugin is not enabled");
        }
        return this.adventure;
    }
    @Override
    public void onEnable() {
        proxy = getProxy();
        proxy.getPluginManager().registerListener(this, new ChatListenerBungee(this));
        this.adventure = BungeeAudiences.create(this);
        getLogger().info("Loaded");
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
