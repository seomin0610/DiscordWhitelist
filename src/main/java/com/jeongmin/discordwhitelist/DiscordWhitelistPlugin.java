package com.jeongmin.discordwhitelist;

import org.bukkit.plugin.java.JavaPlugin;

public class DiscordWhitelistPlugin extends JavaPlugin {
    private DiscordBotManager botManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        botManager = new DiscordBotManager(this);
        botManager.startBot();
        getLogger().info("DiscordWhitelist 플러그인이 활성화되었습니다.");
    }

    @Override
    public void onDisable() {
        if (botManager != null) {
            botManager.shutdown();
        }
        getLogger().info("DiscordWhitelist 플러그인이 비활성화되었습니다.");
    }
} 