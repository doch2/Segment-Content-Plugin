package com.dochsoft.fullmoon.segment;

import com.dochsoft.fullmoon.segment.minigame.DoorArea;
import com.dochsoft.fullmoon.segment.room.RoomConfig;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import com.dochsoft.fullmoon.segment.timer.TimerReference;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import java.util.Random;

public class SegmentCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    Runnable runnable = new OpeningSendMessageThread(onlinePlayer);
                    new Thread(runnable).start();
                }
            } else if (args[0].equalsIgnoreCase("tpSegment")) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (!onlinePlayer.isOp()) {
                        Random random = new Random();
                        int randomInt = random.nextInt(4) + 1;
                        int randomInt2 = random.nextInt(24) + 1;
                        String roomName = randomInt + "층-" + randomInt2;

                        int pos1LocX = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos1X"));
                        int pos2LocX = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos2X"));
                        int pos1LocY = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos1Y"));
                        int pos2LocY = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos2Y"));
                        int pos1LocZ = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos1Z"));
                        int pos2LocZ = Integer.parseInt(RoomArea.roomArea.get(roomName + ".locatePos2Z"));

                        int maxLocY = 0;
                        int minLocY = 0;
                        if (pos1LocY < pos2LocY) { //a가 높아야하는데 낮을경우 계산
                            maxLocY = pos2LocY;
                            minLocY = pos1LocY;
                        } else if (pos1LocY > pos2LocY) {
                            maxLocY = pos1LocY;
                            minLocY = pos2LocY;
                        }

                        Location location = new Location(Bukkit.getWorld("flat1"), (pos1LocX + pos2LocX) / 2, minLocY + 1, (pos1LocZ + pos2LocZ) / 2, player.getLocation().getYaw(), player.getLocation().getPitch());
                        onlinePlayer.teleport(location);
                    }
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                try {
                    RoomConfig.loadHallwayArea();
                    RoomConfig.loadDoorArea();
                    RoomConfig.loadRoomArea();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                Reference.sendOpMessage(player.getName() + "에 의해 실행된 리로딩이 완료되었습니다.");
            } else if (args[0].equalsIgnoreCase("rollback")) {
                try {
                    RoomConfig.rollbackRoom();
                    RoomConfig.loadRoomArea();
                } catch (InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                player.sendMessage(Reference.prefix_opMessage + "§6" + player.getPlayer().getName() + "§f님이 진행한 롤백이 완료되었습니다.");
            } else if (args[0].equalsIgnoreCase("rollbackDoor")) {
                for (int i=0; i < DoorArea.doorList.size(); i++) {
                    int pos1LocX = Integer.parseInt(DoorArea.doorArea.get(DoorArea.doorList.get(i) + ".locatePos1X"));
                    int pos1LocY = Integer.parseInt(DoorArea.doorArea.get(DoorArea.doorList.get(i) + ".locatePos1Y"));
                    int pos1LocZ = Integer.parseInt(DoorArea.doorArea.get(DoorArea.doorList.get(i) + ".locatePos1Z"));

                    Block block = new Location(Bukkit.getWorld("flat1"), pos1LocX, pos1LocY, pos1LocZ).getBlock();
                    BlockState blockState = block.getState();
                    if(((Door) blockState.getData()).isTopHalf()) {
                        blockState = block.getRelative(BlockFace.DOWN).getState();
                    }

                    Openable openable = (Openable) blockState.getData();
                    openable.setOpen(false);
                    blockState.setData((MaterialData) openable);
                    blockState.update();
                }
                player.sendMessage(Reference.prefix_opMessage + "§6" + player.getPlayer().getName() + "§f님이 진행한 미니게임 문 롤백이 완료되었습니다.");
            } else {
                sendCommandHelpMessage(player);
            }
        } else if (args.length > 1) {
            if (args[0].equalsIgnoreCase("notice")) {
                String message = "";

                for (int i = 1; i < args.length; i++) {
                    message = message + args[i] + " ";
                }

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.sendMessage(Reference.prefix_normal + message);
                }
            } else if (args[0].equalsIgnoreCase("gotoRoom")) {
                RoomReference.teleportRoom(player, args[1], "out", 1);
            } else if (args[0].equalsIgnoreCase("getroomlist")) {
                Player listPlayer = Bukkit.getPlayer(args[1]);
                String message = "§6" + listPlayer.getName() + "§f님의 방 리스트: ";
                for (int i=0; i < RoomArea.roomAreaList.size(); i++) {
                    if (RoomArea.roomArea.get(RoomArea.roomAreaList.get(i) + ".Owner").equalsIgnoreCase(listPlayer.getName())) {
                        message = message + RoomArea.roomAreaList.get(i) + ", ";
                    }
                }
                player.sendMessage(Reference.prefix_normal + message.substring(0, message.length() - 2));
            } else if (args[0].equalsIgnoreCase("ending")) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    Runnable runnable = new EndingSendMessageThread(onlinePlayer, args[1], args[2], args[3]);
                    new Thread(runnable).start();
                }
            } else if (args[0].equalsIgnoreCase("kill")) {
                Runnable runnable = new PlayerDieSendMessageThread(Bukkit.getPlayer(args[2]), args[1]);
                new Thread(runnable).start();
            } else if (args[0].equalsIgnoreCase("tutorial")) {
                Runnable runnable = new TutorialMessageThread(Bukkit.getPlayer(args[1]), args[2]);
                new Thread(runnable).start();
            } else {
                sendCommandHelpMessage(player);
            }
        } else {
            sendCommandHelpMessage(player);
        }


        return false;
    }

    private static void sendCommandHelpMessage(Player player) {
        player.sendMessage("§6/segment start - §7게임을 시작합니다.");
        player.sendMessage("§6/segment tpsegment - §7각 방으로 랜덤 티피됩니다.");
        player.sendMessage("§6/segment reload - §7방과 다른 구역을을 리로딩합니다.");
        player.sendMessage("§6/segment rollback - §7방의 가격과 애드온을 초기화시킵니다.");
        player.sendMessage("§6/segment rollbackDoor - §7미니게임의 문을 다시 닫습니다.");
        player.sendMessage("§6/segment notice - §7공지를 합니다");
        player.sendMessage("§6/segment gotoRoom §b<roomName> §7- 원하는 방으로 이동합니다.");
        player.sendMessage("§6/segment ending §b<winnerName> <winnerrNick> <messageMod> §7- 엔딩 메세지를 재생합니다. \n - 일반, 히든");
        player.sendMessage("§6/segment kill §b<killerName> <deadPlayerNick> §7- 죽은 사람에게 게임 종료 메세지를 재생합니다.");
        player.sendMessage("§6/segment tutorial §b<playerName> <tutorialKind> §7- 튜토리얼 메세지를 불러옵니다. \n - 1, 2, 3, 4");
        player.sendMessage("§6/segment getroomlist §b<playerName> §7- 방 리스트를 가져옵니다.");
    }
}
