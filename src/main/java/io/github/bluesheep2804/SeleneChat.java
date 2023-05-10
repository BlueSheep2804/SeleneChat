package io.github.bluesheep2804;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "selenechat", name = "SeleneChat", version = "0.1.0-SNAPSHOT",
        description = "Chat plugin for Velocity inspired by Lunachat", authors = {"BlueSheep2804"})
public class SeleneChat {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public SeleneChat(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there! I made my first plugin with Velocity.");
    }
}