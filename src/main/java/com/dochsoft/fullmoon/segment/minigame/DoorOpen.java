package com.dochsoft.fullmoon.segment.minigame;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

public class DoorOpen {
    public static String doorGuiName = "미니게임 문 열기 - ";

    public static void getDoorOpenGui(Player player, String chestName) {

        Inventory inv = Bukkit.createInventory(null, 45, Reference.prefix_normal + doorGuiName + chestName);

        for (int i=0; i < 45; i++) {
            if (i != 14 && i != 15 && i != 16) {
                Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
            }
        }

        Reference.setGuiItemNoLore("", BuyRoomGui.NumberOneItem, 0, 1, 10, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberTwoItem, 0, 1, 11, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberThreeItem, 0, 1, 12, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberFourItem, 0, 1, 19, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberFiveItem, 0, 1, 20, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberSixItem, 0, 1, 21, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberSevenItem, 0, 1, 28, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberEightItem, 0, 1, 29, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberNineItem, 0, 1, 30, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberZeroItem, 0, 1, 38, inv);


        Reference.setGuiItemNoLore("§f비밀번호 초기화", 251, 14, 1, 32, inv);
        Reference.setGuiItemNoLore("§f문 열기", 251, 5, 1, 34, inv);

        player.openInventory(inv);

    }

    public static void openDoor(Player player, String doorName) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(Reference.prefix_normal + "§6" + player.getName() + "§f님의 문이 열렸습니다!");
        }

        Block doorBlock = player.getWorld().getBlockAt(Integer.parseInt(DoorArea.doorArea.get(doorName + ".locatePos1X")), Integer.parseInt(DoorArea.doorArea.get(doorName + ".locatePos1Y")), Integer.parseInt(DoorArea.doorArea.get(doorName + ".locatePos1Z")));
        BlockState blockState = doorBlock.getState();
        if(((Door) blockState.getData()).isTopHalf()){
            blockState = doorBlock.getRelative(BlockFace.DOWN).getState();
        }

        Openable openable = (Openable) blockState.getData();
        openable.setOpen(true);
        blockState.setData((MaterialData) openable);
        blockState.update();
    }
}
