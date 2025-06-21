package com.jeongmin.discordwhitelist;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;

public class DiscordBotManager extends ListenerAdapter {
    private final JavaPlugin plugin;
    private JDA jda;

    public DiscordBotManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startBot() {
        String token = plugin.getConfig().getString("discord.token");
        String guildId = plugin.getConfig().getString("discord.guild_id");
        String commandName = plugin.getConfig().getString("discord.command_name", "whitelist");
        try {
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(this)
                    .build();
            jda.awaitReady();
            Guild guild = jda.getGuildById(guildId);
            if (guild != null) {
                guild.upsertCommand(Commands.slash(commandName, "마인크래프트 화이트리스트에 플레이어를 추가합니다.")
                        .addOption(net.dv8tion.jda.api.interactions.commands.OptionType.STRING, "player", "당신의 마인크래프트 플레이어 이름을 입력해주세요.", true)
                ).queue();
            }
        } catch (InterruptedException e) {
            plugin.getLogger().severe("디스코드 봇 시작 실패: " + e.getMessage());
        }
    }

    public void shutdown() {
        if (jda != null) {
            jda.shutdown();
        }
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String commandName = plugin.getConfig().getString("discord.command_name", "whitelist");
        if (!event.getName().equals(commandName)) return;
        OptionMapping option = event.getOption("player");
        if (option == null) {
            event.replyEmbeds(EmbedUtil.errorEmbed("플레이어 이름을 입력해주세요.")).queue();
            return;
        }
        String playerName = option.getAsString();
        Bukkit.getScheduler().runTask(plugin, () -> {
            OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
            if (player == null || player.getName() == null) {
                event.replyEmbeds(EmbedUtil.errorEmbed("존재하지 않는 플레이어 이름입니다."))
                        .queue();
                return;
            }
            player.setWhitelisted(true);
            event.replyEmbeds(EmbedUtil.successEmbed(playerName, Bukkit.getVersion()))
                    .queue();
        });
    }
} 