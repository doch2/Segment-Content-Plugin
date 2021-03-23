package com.dochsoft.fullmoon.segment.room.door;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class DoorChooseBuyOrOpen implements CommandExecutor {
    public static FileConfiguration data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            openChooseBuyOrOpenGui(player);
        }

        return false;
    }

    public static void openChooseBuyOrOpenGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, Reference.prefix_normal + "방 구매 & 문 잠금");

        for (int i=0; i < 9; i++) {
            Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
        }

        Reference.setGuiItemNoLore("§f방 사기", Reference.GUI_ROOMPRICE_ITEM, 0, 1, 2, inv);
        Reference.setGuiItemNoLore("§f방 애드온 적용하기", Reference.DEFAULT_ADDON_CODE, 0, 1, 4, inv);
        Reference.setGuiItemNoLore("§f방 문 열기", Reference.DOOR_CHOOSE_LOCK_COUPON, 0, 1, 6, inv);

        player.openInventory(inv);

    }
}
