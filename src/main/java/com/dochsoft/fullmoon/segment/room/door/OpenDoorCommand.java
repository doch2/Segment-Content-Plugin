package com.dochsoft.fullmoon.segment.room.door;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenDoorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            DoorOpenCloseGui.openDoorGui(player, null);
        } else if (args.length == 1) {
            DoorOpenCloseGui.openDoorGui(player, args[0]);
        }
        return false;
    }
}
