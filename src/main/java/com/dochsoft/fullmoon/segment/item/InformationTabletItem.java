package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.addon.UseAddonGui;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InformationTabletItem implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§6/getInfoTablet §b<TabletKind> §7- 플레이어에게 정보 태블릿 아이템을 지급합니다. <tabletKind>는 아이템종류입니다.\n애드온 종류 - ???, 방개수, 플레이어, 히든1, 2, 3, 4");
        } else {
            if (player.getInventory().firstEmpty() != -1) {
                if (args[0].equalsIgnoreCase("방개수") || args[0].equalsIgnoreCase("플레이어")) {
                    player.getInventory().addItem(Reference.getInfoTabletToPlayer(args[0]));
                } else if (args[0].contains("히든") || args[0].equalsIgnoreCase("???")) {
                    player.getInventory().addItem(Reference.getHiddenInfoTabletToPlayer(args[0]));
                }
            } else {
                player.sendMessage(Reference.prefix_error + "인벤토리에 공간이 부족합니다. 공간을 마련해주세요.");
            }
        }
        return false;
    }

    public static void PlayerInformationBook(Player player) {
        String playerList = "§6";
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp() && !Reference.isPlayerDeath.get(onlinePlayer.getUniqueId())) {
                playerList = playerList + onlinePlayer.getName() + "§f, §6";
            }
        }
        if (playerList.length() > 5) {
            playerList = playerList.substring(0, playerList.length() - 4);
        } else {
            playerList = "없음";
        }

        player.sendMessage(Reference.prefix_normal + "§f[ 현재 SEGMENT에 참여하고 있는 플레이어 ]: " + playerList);

        Reference.removeInventoryItems(player.getInventory(), Reference.getInfoTabletToPlayer("플레이어"), 1);
    }

    public static void RoomInformationBook(Player player) {
        String result = "현재 참여한 플레이어들의 방 개수 리스트: \n - ";

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            int playerRoomNumber = 0;
            if (!onlinePlayer.isOp()) {
                for (int i2=0; i2 < RoomArea.roomAreaList.size(); i2++) {
                    if (RoomArea.roomArea.get(RoomArea.roomAreaList.get(i2) + ".Owner").equalsIgnoreCase(onlinePlayer.getName())) {
                        playerRoomNumber = playerRoomNumber + 1;
                    }
                }

                result = result + "§6" + onlinePlayer.getName() + "§f님의 현재 방 갯수: " + playerRoomNumber + "개\n - ";
            }
        }

        player.sendMessage(Reference.prefix_normal + result.substring(0, result.length() - 4));

        Reference.removeInventoryItems(player.getInventory(), Reference.getInfoTabletToPlayer("방개수"), 1);
    }
}
