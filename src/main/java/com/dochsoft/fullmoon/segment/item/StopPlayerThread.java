package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class StopPlayerThread extends Thread {
    private Player player;

    public StopPlayerThread(Player player) {
        this.player = player;
    }

    public void run() {
        try {
            Thread.sleep(5000);
            player.sendMessage(Reference.prefix_normal + "행동정지 시간이 5초 남았습니다.");
            Thread.sleep(5000);
            player.sendMessage(Reference.prefix_normal + "행동정지가 끝났습니다.");
            StopItem.playerStopItem.put(player.getUniqueId(), false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

