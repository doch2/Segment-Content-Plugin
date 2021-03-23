package com.dochsoft.fullmoon.segment;

import org.bukkit.entity.Player;

public class PlayerDieSendMessageThread extends Thread {
    private Player player;
    private String killerNick;

    public PlayerDieSendMessageThread(Player player, String killerNick) {
        this.player = player;
        this.killerNick = killerNick;

    }

    public void run() {
        try {
            messagePlayerDie(player, killerNick);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void messagePlayerDie(Player player, String killPlayerNick) throws InterruptedException {
        player.sendTitle("§6" + killPlayerNick + "§f님이 당신을 공격하였습니다.", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("세그먼트 탈락 처리 프로그램이 작동됩니다.", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("[.]", "", 10, 120, 10);
        Thread.sleep(400);
        player.sendTitle("[..]", "", 10, 120, 10);
        Thread.sleep(350);
        player.sendTitle("[...]", "", 10, 120, 10);
        Thread.sleep(400);
        player.sendTitle("[....]", "", 10, 120, 10);
        Thread.sleep(550);
        player.sendTitle("[.....]", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("§l§4당신은 사망하셨습니다.", "", 10, 60, 20);
    }
}

