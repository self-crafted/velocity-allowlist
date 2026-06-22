package com.github.selfcrafted.velocity.allowlist;

import com.google.inject.Inject;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;

@Plugin(id = "selfcrafted-velocity-allowlist", name = Versions.NAME, version = Versions.VERSION,
        url = "https://github.com/self-crafted/velocity-allowlist",
        description = "Dead simple player allowlist for Velocity proxy",
        authors = {"offby0point5"})
public class VelocityAllowlist {
    private static final ResultedEvent.ComponentResult ALLOWED = ResultedEvent.ComponentResult.allowed();
    private static final ResultedEvent.ComponentResult DENIED = ResultedEvent.ComponentResult.denied(
            Component.text("You're not invited to the party...", NamedTextColor.RED));

    @Inject
    private Logger logger;
    @Inject
    private ProxyServer server;

    private final Allowlist allowlist;
    private final Settings settings;

    public VelocityAllowlist() {
        this.settings = new Settings(logger, Path.of("").toFile());
        this.settings.read();
        this.allowlist = new Allowlist(settings.getState().getSource(), logger);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        server.getScheduler().buildTask(this, allowlist::reload).repeat(
                Duration.ofSeconds(settings.getState().getReloadInterval())).schedule();
    }

    @Subscribe
    public void onPlayerJoin(LoginEvent event) {
        if (allowlist.getAllowlist().contains(event.getPlayer().getUniqueId())) event.setResult(ALLOWED);
        else event.setResult(DENIED);
    }
}
