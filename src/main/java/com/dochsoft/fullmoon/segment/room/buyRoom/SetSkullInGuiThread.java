package com.dochsoft.fullmoon.segment.room.buyRoom;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class SetSkullInGuiThread extends Thread {
    private Inventory inventory;
    private String roomName;
    private Integer location;

    public SetSkullInGuiThread(Inventory inventory, String roomName, int location) {
        this.inventory = inventory;
        this.roomName = roomName;
        this.location = location;
    }

    public void run() {
        Reference.setGuiHeadItem(RoomReference.getRoomOwnerSkullName(roomName), inventory, location, "§f방 현재 주인: §6" +
                RoomReference.getRoomOwner(roomName), Arrays.asList("", "§f현재 가격: §6" + RoomArea.roomArea.get(roomName + ".Price")));
    }
}

