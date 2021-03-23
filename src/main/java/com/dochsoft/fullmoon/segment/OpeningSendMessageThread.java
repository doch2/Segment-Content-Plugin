package com.dochsoft.fullmoon.segment;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class OpeningSendMessageThread extends Thread {
    private Player player;

    public OpeningSendMessageThread(Player player) {
        this.player = player;
    }

    public void run() {
        try {
            Reference.sendOpMessage("1초 카운팅이 끝나면 운영자 중 한 분은 야생월드에서 tpall을 사용해주세요.");
            player.sendTitle("SEGMENT가 곧 시작됩니다...", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("10", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("9", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("8", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("7", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("6", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("5", "", 10, 120, 10);
            if (!player.isOp()) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 120, 3));
            } else {
                player.sendMessage(Reference.prefix_normal + "OP 플레이어로 확인되어 실명 및 이속 감소가 적용되지 않습니다.");
            }
            Thread.sleep(1000);
            player.sendTitle("4", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("3", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("2", "", 10, 120, 10);
            Thread.sleep(1000);
            player.sendTitle("1", "", 10, 10, 10);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

