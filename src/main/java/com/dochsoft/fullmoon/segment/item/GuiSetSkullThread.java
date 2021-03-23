package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class GuiSetSkullThread extends Thread {
    private Inventory inventory;
    private Player player;
    private int playerSkullLoc;
    private String itemTitle;

    public GuiSetSkullThread(Inventory inventory, Player player, int playerSkullLoc, String itemTitle) {
        this.inventory = inventory;
        this.player = player;
        this.playerSkullLoc = playerSkullLoc;
        this.itemTitle = itemTitle;
    }

    public void run() {
        Reference.setGuiHeadItem(player.getName(), inventory, playerSkullLoc, "§f" + player.getName() + itemTitle, Arrays.asList(""));
    }
}

