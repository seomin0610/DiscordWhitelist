package com.jeongmin.discordwhitelist;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmbedUtil {
    public static MessageEmbed successEmbed(String playerName, String serverVersion) {
        String message = "**" + playerName + "** 님이 화이트리스트에 등록되었습니다!";
        String headUrl = "https://mc-heads.net/head/" + playerName;
        String profileUrl = "https://laby.net/@" + playerName;
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return new EmbedBuilder()
                .setTitle("화이트리스트 등록 완료!")
                .setDescription(message + "\n\n[프로필 보기](" + profileUrl + ")")
                .setColor(new Color(0x4CAF50))
                .setThumbnail(headUrl)
                .addField("플레이어 이름", playerName, true)
                .addField("등록 시각", time, true)
                .addField("서버", serverVersion, false)
                .setFooter("[디스코드 화이트리스터](github.com/seomin0610/)")
                .build();
    }

    public static MessageEmbed errorEmbed(String message) {
        return new EmbedBuilder()
                .setTitle("🚫 등록 실패")
                .setDescription("**오류:** " + message)
                .setColor(new Color(0xE53935))
                .setFooter("[디스코드 화이트리스터](github.com/seomin0610/)")
                .build();
    }
} 