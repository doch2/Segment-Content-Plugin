package com.dochsoft.fullmoon.segment.room.door;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.room.buyRoom.SetSkullInGuiThread;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

public class DoorOpenCloseGui {

    public static void openDoorGui(Player player, String InputroomName) {
        String roomName;
        if (InputroomName == null) {
            roomName = RoomArea.playerNowAreaName.get(player.getUniqueId());
        } else {
            roomName = InputroomName;
        }

        RoomReference.openDoorGuiRoomName.put(player.getUniqueId(), roomName);

        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_normal + "방 문 열기");

        for (int i=0; i < 54; i++) {
            if (i != 12 && i != 13 && i != 14) {
                Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
            }
        }

        Reference.setGuiItemNoLore("§f방 열기", Reference.GUI_AGREE_ITEM, 0, 1, 28, inv);
        Reference.setGuiItemNoLore("§f방 닫기", Reference.GUI_DISAGREE_ITEM, 0, 1, 34, inv);

        Reference.setGuiItemNoLore("§f카드키 만들러 가기", 4461, 0, 1, 53, inv);

        List<Integer> integerList = Reference.getNumberList(Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice")));

        for (int i=1; i < 4; i++) {
            if (integerList.get(i) == 1) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberOneItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 2) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberTwoItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 3) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberThreeItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 4) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberFourItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 5) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberFiveItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 6) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberSixItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 7) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberSevenItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 8) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberEightItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 9) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberNineItem, 0, 1, 11 + i, inv);
            } else if (integerList.get(i) == 0) {
                Reference.setGuiItemNoLore(" ", BuyRoomGui.NumberZeroItem, 0, 1, 11 + i, inv);
            }
        }

        player.openInventory(inv);

    }
}
