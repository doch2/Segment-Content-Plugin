package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.Segment;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.UUID;

public class StopItem {
    public static HashMap<UUID, Boolean> playerStopItem = new HashMap();
    public static HashMap<UUID, Integer> playerStopTime = new HashMap();

    public static void choosePlayerGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_normal + "스탑 아이템 사용");

        int playerSkullLoc = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp() && onlinePlayer != player) {
                Runnable runnable = new GuiSetSkullThread(inv, onlinePlayer, playerSkullLoc, "을 10초동안 정지");
                new Thread(runnable).start();

                playerSkullLoc = playerSkullLoc + 1;
            }
        }

        player.openInventory(inv);
    }

    public static void runBukkitScheduler() {
        Bukkit.getScheduler().runTaskTimer(Segment.getInstance(), new Runnable() {
            public void run() {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    int time1 = StopItem.playerStopTime.get(onlinePlayer.getUniqueId());
                    if (time1 != 0) {
                        StopItem.playerStopTime.put(onlinePlayer.getUniqueId(), time1 - 1);
                        if (time1 == 5) {
                            onlinePlayer.sendMessage(Reference.prefix_normal + "행동정지 시간이 5초 남았습니다.");
                        }
                    } else if (StopItem.playerStopItem.get(onlinePlayer.getUniqueId())) {
                        onlinePlayer.sendMessage(Reference.prefix_normal + "행동정지가 끝났습니다.");
                        StopItem.playerStopItem.put(onlinePlayer.getUniqueId(), false);
                    }
                }
            }
        }, 0L, 20L); //10틱당 돌아가게, 20틱 = 1초
    }

    public static void stopPlayer(Player player) {
        //Runnable runnable = new StopPlayerThread(player);
        //new Thread(runnable).start();

        playerStopItem.put(player.getUniqueId(), true);
        playerStopTime.put(player.getUniqueId(), 10);
    }
}
