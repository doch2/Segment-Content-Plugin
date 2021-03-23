package com.dochsoft.fullmoon.segment;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EndingSendMessageThread extends Thread {
    private Player player;
    private String winnerName;
    private String winnerNick;
    private String messageMod;

    public EndingSendMessageThread(Player player, String winnerName, String winnerNick, String messageMod) {
        this.player = player;
        this.winnerName = winnerName;
        this.winnerNick = winnerNick;
        this.messageMod = messageMod;
    }

    public void run() {
        try {
            if (messageMod.equals("일반")) {
                messageDefaultWin(player, winnerNick, winnerName);
            } else if (messageMod.equals("히든")) {
                messageHiddenWin(player, winnerNick, winnerName);
            } else if (messageMod.equals("킬")) {
                messagePlayerDie(player, winnerNick);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void messageDefaultWin(Player player, String winnerNick, String winnerName) throws InterruptedException {
        if (player.getName().equalsIgnoreCase(winnerNick)) {
            player.sendTitle("축하합니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("당신은 승리하셨습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("당신을 제외한 참가자들은 패배의 대가로 목숨을 빼앗깁니다.", "", 10, 120, 10);
            Thread.sleep(3000);
            player.sendTitle("즐기십시오.", "", 10, 120, 10);
        } else {
            player.sendTitle("세그먼트의 승리자는 §6" + winnerName + "§f님입니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("당신은 패배하셨습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("§l§c패배의 대가는 목숨입니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("§l§4안녕히 가십시오.", "", 10, 120, 10);
        }
    }

    private static void messageHiddenWin(Player player, String winnerNick, String winnerName) throws InterruptedException {
        if (player.getName().equalsIgnoreCase(winnerNick)) {
            player.sendTitle("축하합니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("당신은 승리하셨습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("당신을 제외한 모든 참가자는 사망하셨습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("지금부터 당신은 세그먼트의 다음 주인입니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("영원히 세그먼트 속에서 살아가십시오.", "", 10, 120, 10);
        } else {
            player.sendTitle("§6" + winnerName + "§f님이 모두를 살해하셨습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("히든퀘스트의 모든 조건이 충족되었습니다.", "", 10, 120, 10);
            Thread.sleep(2000);
            player.sendTitle("§6" + winnerName + "§f님이 승리하셨습니다.", "", 10, 120, 10);
        }
    }

    private static void messagePlayerDie(Player player, String killPlayerNick) throws InterruptedException {
        player.sendTitle("§6" + killPlayerNick + "§f님이 당신을 공격하였습니다.", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("세그먼트 탈락 처리 프로그램이 작동됩니다.", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("[.....]", "", 10, 120, 10);
        Thread.sleep(2000);
        player.sendTitle("§l§4당신은 사망하셨습니다.", "", 10, 120, 10);
    }
}

