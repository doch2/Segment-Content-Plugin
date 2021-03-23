package com.dochsoft.fullmoon.segment.addon;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.Segment;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetAddonItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§6/getAddon §b<AddonKind> <percentage> §7- 플레이어에게 애드온을 지급합니다. <percentage>는 일반애드온일 경우에만 작성합니다.\n애드온 종류 - 일반애드온, 아이템애드온");
        } else {
            if (player.getInventory().firstEmpty() != -1) {
                if (args[0].equalsIgnoreCase("일반애드온")) {
                    if (50 <= Integer.parseInt(args[1]) && Integer.parseInt(args[1]) <= 200) {
                        player.getInventory().addItem(Reference.getDefaultAddonToPlayer(Integer.parseInt(args[1])));
                    } else {
                        player.sendMessage(Reference.prefix_error + "일반애드온은 최대 50%에서 200%까지 설정이 가능합니다.");
                    }
                } else if (args[0].equalsIgnoreCase("아이템애드온")) {
                    player.getInventory().addItem(Reference.getItemAddonToPlayer());
                }
            } else {
                player.sendMessage(Reference.prefix_error + "인벤토리에 공간이 부족합니다. 공간을 마련해주세요.");
            }
        }
        return false;
    }
}
