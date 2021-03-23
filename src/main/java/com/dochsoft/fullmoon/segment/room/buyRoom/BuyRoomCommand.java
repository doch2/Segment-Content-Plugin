package com.dochsoft.fullmoon.segment.room.buyRoom;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuyRoomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            if (RoomArea.playerNowAreaName.get(player.getUniqueId()) == null) {
                player.sendMessage(Reference.prefix_error + "현재 방에 위치하고 있지 않습니다. 방에 들어간 후 다시 시도해 주세요.");
                return true;
            } else {
                BuyRoomGui.buyRoomGui(player);
            }
        }
        return false;
    }
}
