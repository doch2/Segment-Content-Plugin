package com.dochsoft.fullmoon.segment.room.setRoomArea;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.minigame.DoorArea;
import com.dochsoft.fullmoon.segment.room.RoomConfig;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRoomCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§6/roomArea setRoom §b<RoomName> <DoorNumber> - §7방 위치를 저장합니다.");
            player.sendMessage("§6/roomArea setDoor §b<RoomName> <DoorNumber> <DoorKind> <In&OutSide> - §7방의 문 위치를 저장합니다. \n - DoorKind 종류: floor(바닥), wall(벽면)\n - 방안에 있는 문일경우 in, 복도에 있는 뭉리경우 out");
            player.sendMessage("§6/roomArea setHallway §b<RoomName> <DoorNumber> - §7방의 복도 위치를 지정합니다. \n - DoorNumber: 복도와 붙어있는 문 이름");
            player.sendMessage("§6/roomArea setMinigameDoor §b<DoorName> <password> - §7미니게임에 사용할 상자 위치를 지정합니다. \n - password - 비밀번호");
            player.sendMessage("§6/roomArea nowRoom - §7플레이어의 현재 방 위치를 확인합니다.");
            return true;
        }

        if (args[0].equalsIgnoreCase("setRoom")) {
            if (args.length != 3) {
                player.sendMessage("§6/roomArea setRoom §b<RoomName> <DoorNumber> - §7방 위치를 저장합니다.");
            } else {
                try {
                    if (!RoomArea.areaSetLeftClick.containsKey(player.getUniqueId()) || !RoomArea.areaSetLeftClick.containsKey(player.getUniqueId())) {
                        player.sendMessage(Reference.prefix_error + "지정할 구역이 지정되지 않았습니다. 돌 검 아이템으로 구역을 설정한 후 다시 시도해주세요");
                        player.sendMessage("§6/roomArea setRoom §b<RoomName> <DoorNumber> - §7방 위치를 저장합니다.");
                        return true;
                    } else {
                        Block pos1 = RoomArea.areaSetLeftClick.get(player.getUniqueId());
                        Block pos2 = RoomArea.areaSetRightClick.get(player.getUniqueId());
                        if (pos1.getWorld() != pos2.getWorld()) {
                            player.sendMessage("두개의 좌표의 월드가 동일하지 않습니다. 확인 후 다시 시도해주세요.");
                            player.sendMessage("첫 번째 좌표 월드: " + pos1.getWorld() + ", " + "두 번째 좌표 월드: " + pos2.getWorld());
                            return true;
                        }
                        RoomConfig.createRoomConfig(args[1], pos1, pos2, Integer.parseInt(args[2]));
                        Reference.sendOpMessage("§6" + player.getName() + "§f님이 " + args[1] + "방을 지정하였습니다.");

                        RoomReference.roomAddon.put(args[1], null);
                        RoomArea.roomArea.put(args[1] + ".Owner", "null");
                        RoomArea.roomArea.put(args[1] + ".DoorPrice", Integer.toString(0));
                        RoomArea.roomArea.put(args[1] + ".Price", Integer.toString(RoomReference.defaultRoomPrice));
                        RoomArea.roomArea.put(args[1] + ".DoorAllow", Boolean.toString(true));
                        RoomArea.roomArea.put(args[1] + ".DoorNumber", args[2]);
                        RoomArea.roomArea.put(args[1] + ".locateWorld", pos1.getWorld().getName());
                        RoomArea.roomArea.put(args[1] + ".locatePos1X", Integer.toString(pos1.getX()));
                        RoomArea.roomArea.put(args[1] + ".locatePos1Y", Integer.toString(pos1.getY()));
                        RoomArea.roomArea.put(args[1] + ".locatePos1Z", Integer.toString(pos1.getZ()));
                        RoomArea.roomArea.put(args[1] + ".locatePos2X", Integer.toString(pos2.getX()));
                        RoomArea.roomArea.put(args[1] + ".locatePos2Y", Integer.toString(pos2.getY()));
                        RoomArea.roomArea.put(args[1] + ".locatePos2Z", Integer.toString(pos2.getZ()));
                        RoomArea.roomAreaList.add(args[1]);

                    }
                } catch (CommandException e) {}
            }
        } else if (args[0].equalsIgnoreCase("setDoor")) {
            if (args.length != 5) {
                player.sendMessage("§6/roomArea setDoor §b<RoomName> <DoorNumber> <DoorKind> <In&OutSide> - §7방의 문 위치를 저장합니다. \n - DoorKind 종류: floor(바닥), wall(벽면)\n - 방안에 있는 문일경우 inside, 복도에 있는 뭉리경우 outside");
            } else {
                try {
                    if (!RoomArea.areaSetLeftClick.containsKey(player.getUniqueId()) || !RoomArea.areaSetLeftClick.containsKey(player.getUniqueId())) {
                        player.sendMessage(Reference.prefix_error + "지정할 구역이 지정되지 않았습니다. 돌 도끼 아이템으로 구역을 설정한 후 다시 시도해주세요");
                        player.sendMessage("§6/roomArea setDoor §b<RoomName> <DoorNumber> <DoorKind> <In&OutSide> - §7방의 문 위치를 저장합니다. \n - DoorKind 종류: floor(바닥), wall(벽면)\n - 방안에 있는 문일경우 in, 복도에 있는 뭉리경우 out");
                        return true;
                    } else {
                        Block pos1 = RoomArea.areaSetLeftClick.get(player.getUniqueId());
                        Block pos2 = RoomArea.areaSetRightClick.get(player.getUniqueId());
                        if (pos1.getWorld() != pos2.getWorld()) {
                            player.sendMessage("두개의 좌표의 월드가 동일하지 않습니다. 확인 후 다시 시도해주세요.");
                            player.sendMessage("첫 번째 좌표 월드: " + pos1.getWorld() + ", " + "두 번째 좌표 월드: " + pos2.getWorld());
                            return true;
                        }
                        RoomConfig.createDoorConfig(args[1], Integer.parseInt(args[2]), pos1, pos2, args[3], args[4]);
                        Reference.sendOpMessage("§6" + player.getName() + "§f님이 " + args[1] + "방의 " + args[2] + "번째 문을 지정하였습니다.");

                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".Direction", args[3]);
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locateWorld", pos1.getWorld().getName());
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos1X", Integer.toString(pos1.getX()));
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos1Y", Integer.toString(pos1.getY()));
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos1Z", Integer.toString(pos1.getZ()));
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos2X", Integer.toString(pos2.getX()));
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos2Y", Integer.toString(pos2.getY()));
                        RoomArea.doorArea.put(args[1] + "_" + args[2] + "_" + args[4] + ".locatePos2Z", Integer.toString(pos2.getZ()));

                    }
                } catch (CommandException e) {}
            }
        } else if (args[0].equalsIgnoreCase("setHallway")) {
            if (args.length != 3) {
                player.sendMessage("§6/roomArea setHallway §b<RoomName> <DoorNumber> - §7방의 복도 위치를 지정합니다. \n - DoorNumber: 복도와 붙어있는 문 이름");
            } else {
                try {
                    if (!RoomArea.areaSetLeftClick.containsKey(player.getUniqueId()) || !RoomArea.areaSetLeftClick.containsKey(player.getUniqueId())) {
                        player.sendMessage(Reference.prefix_error + "지정할 구역이 지정되지 않았습니다. 돌 도끼 아이템으로 구역을 설정한 후 다시 시도해주세요");
                        player.sendMessage("§6/roomArea setHallway §b<RoomName> <DoorNumber> - §7방의 복도 위치를 지정합니다. \n - DoorNumber: 복도와 붙어있는 문 이름");
                        return true;
                    } else {
                        Block pos1 = RoomArea.areaSetLeftClick.get(player.getUniqueId());
                        Block pos2 = RoomArea.areaSetRightClick.get(player.getUniqueId());
                        if (pos1.getWorld() != pos2.getWorld()) {
                            player.sendMessage("두개의 좌표의 월드가 동일하지 않습니다. 확인 후 다시 시도해주세요.");
                            player.sendMessage("첫 번째 좌표 월드: " + pos1.getWorld() + ", " + "두 번째 좌표 월드: " + pos2.getWorld());
                            return true;
                        }
                        RoomConfig.createHallwayConfig(args[1], Integer.parseInt(args[2]), pos1, pos2);
                        Reference.sendOpMessage("§6" + player.getName() + "§f님이 " + args[1] + "방의 " + args[2] + "번째 문이 붙어있는 복도를 지정하였습니다.");

                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locateWorld", pos1.getWorld().getName());
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos1X", Integer.toString(pos1.getX()));
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos1Y", Integer.toString(pos1.getY()));
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos1Z", Integer.toString(pos1.getZ()));
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos2X", Integer.toString(pos2.getX()));
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos2Y", Integer.toString(pos2.getY()));
                        RoomArea.hallwayArea.put(args[1] + "_" + args[2] + ".locatePos2Z", Integer.toString(pos2.getZ()));

                    }
                } catch (CommandException e) {}
            }
        } else if (args[0].equalsIgnoreCase("setMinigameDoor")) {
            if (args.length != 3) {
                player.sendMessage("§6/roomArea setMinigameDoor §b<DoorName> <password> - §7미니게임에 사용할 상자 위치를 지정합니다. \n - password - 비밀번호");
            } else {
                try {
                    if (!RoomArea.areaSetLeftClick.containsKey(player.getUniqueId()) || !RoomArea.areaSetLeftClick.containsKey(player.getUniqueId())) {
                        player.sendMessage(Reference.prefix_error + "지정할 구역이 지정되지 않았습니다. 돌 도끼 아이템으로 구역을 설정한 후 다시 시도해주세요");
                        player.sendMessage("§6/roomArea setMinigameDoor §b<DoorName> <password> - §7미니게임에 사용할 상자 위치를 지정합니다. \n - password - 비밀번호");
                        return true;
                    } else {
                        Block pos1 = RoomArea.areaSetLeftClick.get(player.getUniqueId());
                        Block pos2 = RoomArea.areaSetRightClick.get(player.getUniqueId());
                        if (pos1.getWorld() != pos2.getWorld()) {
                            player.sendMessage("두개의 좌표의 월드가 동일하지 않습니다. 확인 후 다시 시도해주세요.");
                            player.sendMessage("첫 번째 좌표 월드: " + pos1.getWorld() + ", " + "두 번째 좌표 월드: " + pos2.getWorld());
                            return true;
                        }
                        DoorArea.createDoorConfig(args[1], pos1, pos2, Integer.parseInt(args[2]));
                        Reference.sendOpMessage("§6" + player.getName() + "§f님이 미니게임에 사용될 " + args[1] + "상자를 지정하였고, 비밀번호는 " + args[2] + "로 지정하였습니다.");

                        DoorArea.doorArea.put(args[1] + ".Password", args[2]);
                        DoorArea.doorArea.put(args[1] + ".locateWorld", pos1.getWorld().getName());
                        DoorArea.doorArea.put(args[1] + ".locatePos1X", Integer.toString(pos1.getX()));
                        DoorArea.doorArea.put(args[1] + ".locatePos1Y", Integer.toString(pos1.getY()));
                        DoorArea.doorArea.put(args[1] + ".locatePos1Z", Integer.toString(pos1.getZ()));
                        DoorArea.doorArea.put(args[1] + ".locatePos2X", Integer.toString(pos2.getX()));
                        DoorArea.doorArea.put(args[1] + ".locatePos2Y", Integer.toString(pos2.getY()));
                        DoorArea.doorArea.put(args[1] + ".locatePos2Z", Integer.toString(pos2.getZ()));

                        DoorArea.doorList.add(args[1]);

                    }
                } catch (CommandException e) {}
            }
        } else if (args[0].equalsIgnoreCase("nowRoom")) {

            if (args.length != 1) {
                player.sendMessage("§6/roomArea nowRoom - §7플레이어의 현재 방 위치를 확인합니다.");
            } else {
                String temp = null;

                if (RoomArea.playerNowAreaName.get(player.getUniqueId()) == null || RoomArea.playerNowAreaName.get(player.getUniqueId()).equals("false")) {
                    temp = "방에 위치하지 않음";
                } else {
                    temp = RoomArea.playerNowAreaName.get(player.getUniqueId());
                }

                player.sendMessage(Reference.prefix_normal + player.getName() + "의 현재 방 위치는 §6" + temp + "§f입니다.");
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (RoomArea.roomArea.get(args[1] + ".Price") == null) {
                player.sendMessage(Reference.prefix_error + "삭제하려는 방이 존재하지 않습니다. 이름을 다시 확인해주세요.");
            } else {
                RoomConfig.removeRoomConfig(args[1]);
                RoomArea.roomArea.remove(args[1] + ".Owner");
                RoomArea.roomArea.remove(args[1] + ".Price");
                RoomArea.roomArea.remove(args[1] + ".locateWorld");
                RoomArea.roomArea.remove(args[1] + ".locatePos1X");
                RoomArea.roomArea.remove(args[1] + ".locatePos1Y");
                RoomArea.roomArea.remove(args[1] + ".locatePos1Z");
                RoomArea.roomArea.remove(args[1] + ".locatePos2X");
                RoomArea.roomArea.remove(args[1] + ".locatePos2Y");
                RoomArea.roomArea.remove(args[1] + ".locatePos2Z");
                RoomArea.roomAreaList.remove(args[1]);
                player.sendMessage(Reference.prefix_normal + "정상적으로 " + args[1] + "구역이 삭제되었습니다");
            }
        }
        return false;
    }
}
