package com.dochsoft.fullmoon.segment.room;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.Segment;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class RoomReference {
    public static Integer defaultRoomPrice = 25;

    public static HashMap<String, ItemStack> roomAddon = new HashMap();

    public static HashMap<UUID, Integer> numberKeypad = new HashMap();

    public static HashMap<UUID, String> openDoorGuiRoomName = new HashMap();

    public static String getRoomOwner(String roomName) {
        String ownerName = RoomArea.roomArea.get(roomName + ".Owner");

        if(ownerName == null || ownerName.equals("null")) {
            ownerName = "없음";
        }
        return ownerName;
    }

    public static String getRoomOwnerSkullName(String roomName) {
        String ownerName = RoomArea.roomArea.get(roomName + ".Owner");

        if(ownerName == null || ownerName.equals("null")) {
            ownerName = "vvvvvvvvvvvv";
        }
        return ownerName;
    }

    public static void setRoomOwner(String roomName, Player player) {
        RoomConfig.setRoomOwner(roomName, player);
        RoomArea.roomArea.put(roomName + ".Owner", player.getName());
    }

    public static Integer getRoomPurchasePrice(String roomName) {
        int nowPrice = Integer.parseInt(RoomArea.roomArea.get(roomName + ".Price"));

        int PurchasePrice = (int) (nowPrice + (nowPrice * 0.25));

        if (roomAddon.get(roomName) != null) {
            if (roomAddon.get(roomName).getTypeId() == Reference.DEFAULT_ADDON_CODE) {
                String addonName = roomAddon.get(roomName).getItemMeta().getDisplayName();

                String addonPercentage = addonName.substring(2, addonName.lastIndexOf("§f%"));

                PurchasePrice = PurchasePrice + (nowPrice * (Integer.parseInt(addonPercentage) / 100));
            }
        }

        return PurchasePrice;
    }

    public static void setRoomPrice(String roomName, int roomPrice) {
        RoomConfig.setRoomPrice(roomName, roomPrice);
        RoomArea.roomArea.put(roomName + ".Price", Integer.toString(roomPrice));
    }

    public static void setRoomDoorPrice(String roomName, int doorPrice) {
        RoomConfig.setDoorPrice(roomName, doorPrice);
        RoomArea.roomArea.put(roomName + ".DoorPrice", Integer.toString(doorPrice));
    }

    public static void setRoomDoorAllow(String roomName, Boolean allow) {
        RoomConfig.setDoorAllow(roomName, allow);
        RoomArea.roomArea.put(roomName + ".DoorAllow", Boolean.toString(allow));
    }

    public static boolean hasPlayerRoomCoupon(Player player, String roomName, String lockKind) {
        Boolean hasItem = false;
        Inventory playerInv = player.getInventory();

        for (int i=0; i < playerInv.getSize(); i++) {
            ItemStack itemStack = playerInv.getItem(i);

            if (itemStack != null && itemStack.getTypeId() == Reference.DOOR_OPEN_COUPON) {
                String itemName = itemStack.getItemMeta().getDisplayName();
                if (itemName.contains(roomName + "§f방 " + lockKind + " 카드키")) {
                    int keycardValue = Integer.parseInt(itemName.substring(itemName.lastIndexOf("- ") + 2, itemName.length() - 2));

                    if (keycardValue > Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice"))) {
                        hasItem = true;
                        RoomReference.setRoomDoorPrice(roomName, keycardValue); //문 돈 정하기, 코드 만들기 귀찮아서 여기에 집어넣음
                        if (!RoomReference.getRoomOwner(roomName).equalsIgnoreCase("없음")) {
                            Reference.givePlayerMoney(Bukkit.getPlayer(RoomReference.getRoomOwner(roomName)), keycardValue);
                            RoomReference.setRoomDoorPrice(roomName, keycardValue); //문 돈 정하기, 코드 만들기 귀찮아서 여기에 집어넣음
                        }

                        Reference.removeInventoryItems(playerInv, itemStack, 1);
                    } else if (keycardValue >= Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice")) && RoomArea.roomArea.get(roomName + ".doorOpenPlayer").equalsIgnoreCase(player.getName())) {
                        hasItem = true;

                        Reference.removeInventoryItems(playerInv, itemStack, 1);
                    }
                }
            } else if (itemStack != null && itemStack.getTypeId() == Reference.DOOR_CHOOSE_LOCK_COUPON) {
                String itemName = itemStack.getItemMeta().getDisplayName();
                if (itemName.contains(roomName + "§f방 " + lockKind + " 카드키")) {
                    int keycardValue = Integer.parseInt(itemName.substring(itemName.lastIndexOf("- ") + 2, itemName.length() - 2));

                    if (keycardValue > Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice"))) {
                        hasItem = true;
                        Reference.removeInventoryItems(playerInv, itemStack, 1);
                        //if (Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice")) == 0) {
                        //    RoomReference.setRoomDoorPrice(roomName, keycardValue); //문 돈 정하기, 코드 만들기 귀찮아서 여기에 집어넣음
                        //}
                        RoomReference.setRoomDoorPrice(roomName, keycardValue); //문 돈 정하기, 코드 만들기 귀찮아서 여기에 집어넣음
                    } else if (keycardValue >= Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice")) && RoomArea.roomArea.get(roomName + ".doorOpenPlayer").equalsIgnoreCase(player.getName())) {
                        hasItem = true;
                        Reference.removeInventoryItems(playerInv, itemStack, 1);
                    }
                }
            }
        }
        return hasItem;
    }

    public static int getCouponPriceFromItem(ItemStack item1, ItemStack item2, ItemStack item3) {
        int result = 0;

        if (item1.getTypeId() == BuyRoomGui.NumberOneItem) {
            result = 100;
        } else if (item1.getTypeId() == BuyRoomGui.NumberTwoItem) {
            result = 200;
        } else if (item1.getTypeId() == BuyRoomGui.NumberThreeItem) {
            result = 300;
        } else if (item1.getTypeId() == BuyRoomGui.NumberFourItem) {
            result = 400;
        } else if (item1.getTypeId() == BuyRoomGui.NumberFiveItem) {
            result = 500;
        } else if (item1.getTypeId() == BuyRoomGui.NumberSixItem) {
            result = 600;
        } else if (item1.getTypeId() == BuyRoomGui.NumberSevenItem) {
            result = 700;
        } else if (item1.getTypeId() == BuyRoomGui.NumberEightItem) {
            result = 800;
        } else if (item1.getTypeId() == BuyRoomGui.NumberNineItem) {
            result = 900;
        }

        if (item2.getTypeId() == BuyRoomGui.NumberOneItem) {
            result = result + 10;
        } else if (item2.getTypeId() == BuyRoomGui.NumberTwoItem) {
            result = result + 20;
        } else if (item2.getTypeId() == BuyRoomGui.NumberThreeItem) {
            result = result + 30;
        } else if (item2.getTypeId() == BuyRoomGui.NumberFourItem) {
            result = result + 40;
        } else if (item2.getTypeId() == BuyRoomGui.NumberFiveItem) {
            result = result + 50;
        } else if (item2.getTypeId() == BuyRoomGui.NumberSixItem) {
            result = result + 60;
        } else if (item2.getTypeId() == BuyRoomGui.NumberSevenItem) {
            result = result + 70;
        } else if (item2.getTypeId() == BuyRoomGui.NumberEightItem) {
            result = result + 80;
        } else if (item2.getTypeId() == BuyRoomGui.NumberNineItem) {
            result = result + 90;
        }

        if (item3.getTypeId() == BuyRoomGui.NumberOneItem) {
            result = result + 1;
        } else if (item3.getTypeId() == BuyRoomGui.NumberTwoItem) {
            result = result + 2;
        } else if (item3.getTypeId() == BuyRoomGui.NumberThreeItem) {
            result = result + 3;
        } else if (item3.getTypeId() == BuyRoomGui.NumberFourItem) {
            result = result + 4;
        } else if (item3.getTypeId() == BuyRoomGui.NumberFiveItem) {
            result = result + 5;
        } else if (item3.getTypeId() == BuyRoomGui.NumberSixItem) {
            result = result + 6;
        } else if (item3.getTypeId() == BuyRoomGui.NumberSevenItem) {
            result = result + 7;
        } else if (item3.getTypeId() == BuyRoomGui.NumberEightItem) {
            result = result + 8;
        } else if (item3.getTypeId() == BuyRoomGui.NumberNineItem) {
            result = result + 9;
        }

        return result;
    }

    public static boolean isPlayerClickDoor(String roomName, int doorNumber, String InandOut, Location block, String XYZKind) {
        int pos1Loc = 0;
        int pos2Loc = 0;
        int blockLoc = 0;

        if (RoomArea.doorArea.get(roomName + "_" + doorNumber + "_" + InandOut + ".locatePos1" + XYZKind) != null) {
            pos1Loc = Integer.parseInt(RoomArea.doorArea.get(roomName + "_" + doorNumber + "_" + InandOut + ".locatePos1" + XYZKind));
        }

        if (RoomArea.doorArea.get(roomName + "_" + doorNumber + "_" + InandOut + ".locatePos2" + XYZKind) != null) {
            pos2Loc = Integer.parseInt(RoomArea.doorArea.get(roomName + "_" + doorNumber + "_" + InandOut + ".locatePos2" + XYZKind));
        }
        
        if (XYZKind.equalsIgnoreCase("X")) {
            blockLoc = block.getBlockX();
        } else if (XYZKind.equalsIgnoreCase("Y")) {
            blockLoc = block.getBlockY();
        } else if (XYZKind.equalsIgnoreCase("Z")) {
            blockLoc = block.getBlockZ();
        }

        int maxLoc = 0;
        int minLoc = 0;
        if (pos1Loc < pos2Loc) { //a가 높아야하는데 낮을경우 계산
            maxLoc = pos2Loc;
            minLoc = pos1Loc;
        } else if (pos1Loc > pos2Loc) {
            maxLoc = pos1Loc;
            minLoc = pos2Loc;
        } else {
            minLoc = pos1Loc;
            maxLoc = pos1Loc;
        }

        if (minLoc <= blockLoc && blockLoc <= maxLoc) {
            return true;
        }

        return false;
    }

    public static void teleportRoom(Player player, String roomName, String InandOut, int doorNumber) {
        if (RoomArea.roomArea.get(roomName + ".DoorAllow").equalsIgnoreCase("true")) {
            if (InandOut.equalsIgnoreCase("in")) {
                int pos1LocX = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos1X"));
                int pos2LocX = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos2X"));
                int pos1LocY = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos1Y"));
                int pos2LocY = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos2Y"));
                int pos1LocZ = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos1Z"));
                int pos2LocZ = Integer.parseInt(RoomArea.hallwayArea.get(roomName + "_" + doorNumber + ".locatePos2Z"));

                int maxLocY = 0;
                int minLocY = 0;
                if (pos1LocY < pos2LocY) { //a가 높아야하는데 낮을경우 계산
                    maxLocY = pos2LocY;
                    minLocY = pos1LocY;
                } else if (pos1LocY > pos2LocY) {
                    maxLocY = pos1LocY;
                    minLocY = pos2LocY;
                }

                Location location = new Location(player.getWorld(), (pos1LocX + pos2LocX) / 2, minLocY + 1, (pos1LocZ + pos2LocZ) / 2, player.getLocation().getYaw(), player.getLocation().getPitch());
                player.teleport(location);
                //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_normal + roomName + "의 복도로 이동하였습니다."));
            } else if (InandOut.equalsIgnoreCase("out")) {
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

                Location location = new Location(player.getWorld(), (pos1LocX + pos2LocX) / 2, minLocY + 1, (pos1LocZ + pos2LocZ) / 2, player.getLocation().getYaw(), player.getLocation().getPitch());
                player.teleport(location);
                //player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_normal + roomName + "으로 이동하였습니다."));
            }
        } else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_error + roomName + "의 방문이 현재 닫혀있습니다. 문을 여실려면 카드키 구매후 문을 열어주세요."));
        }
    }

    public static void buyRoom(Player player, String roomName) {

        if (Segment.econ.getBalance(player) - getRoomPurchasePrice(roomName) < 0) {
            player.closeInventory();
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_error + "가지고 있는 금액이 부족합니다."));
        } else if (roomAddon.get(roomName) != null && roomAddon.get(roomName).getTypeId() == Reference.ITEM_ADDON_CODE) {

            Boolean hasItem = false;
            Inventory playerInv = player.getInventory();

            for (int i=0; i < playerInv.getSize(); i++) {
                ItemStack itemStack = playerInv.getItem(i);

                if (itemStack != null) {
                    if (itemStack.getTypeId() == roomAddon.get(roomName + ".NeedItem").getTypeId()) {
                        hasItem = true;
                    }
                }
            }

            if (hasItem) {
                player.closeInventory();
                player.playSound(player.getLocation(), "ui.shop_buy", SoundCategory.PLAYERS, 1.0E8F, 1.0F);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_normal + roomName + "방을 " + getRoomPurchasePrice(roomName) + "을 주고 구매하셨습니다. | 현재 남은 금액: §6" + (int) Segment.econ.getBalance(player)));
                if (!RoomReference.getRoomOwner(roomName).equalsIgnoreCase("없음") && !RoomReference.getRoomOwner(roomName).equalsIgnoreCase(player.getName())) {
                    Player owner = Bukkit.getPlayer(RoomReference.getRoomOwner(roomName));
                    Reference.givePlayerMoney(owner, getRoomPurchasePrice(roomName));
                    owner.sendMessage(Reference.prefix_normal + player.getName() + "님이 당신의 방을 인수하였습니다.");
                }
                Reference.removePlayerMoney(player, getRoomPurchasePrice(roomName));
                Reference.removeInventoryItems(player.getInventory(), roomAddon.get(roomName + ".NeedItem").getType(), roomAddon.get(roomName + ".NeedItem").getData().getData(), 1);
                RoomReference.setRoomOwner(roomName, player);
                RoomReference.roomAddon.put(roomName, null);
                RoomConfig.setRoomAddon(roomName, null, null);
                Reference.sendOpMessage(roomName + "방을 " + player.getName() + "가 " + getRoomPurchasePrice(roomName) + "를 지불하고 구매하였습니다.");
                setRoomPrice(roomName, getRoomPurchasePrice(roomName));
            } else {
                player.closeInventory();
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_error + "방을 사기 위해 필요한 아이템이 현재 인벤토리에 존재하지 않습니다. 아이템을 얻은 후 다시 시도해주세요."));
            }
        } else {
            player.closeInventory();
            player.playSound(player.getLocation(), "ui.shop_buy", SoundCategory.PLAYERS, 1.0E8F, 1.0F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Reference.prefix_normal + roomName + "방을 " + getRoomPurchasePrice(roomName) + "을 주고 구매하셨습니다. | 현재 남은 금액: §6" + (int) Segment.econ.getBalance(player)));
            if (!RoomReference.getRoomOwner(roomName).equalsIgnoreCase("없음") && !RoomReference.getRoomOwner(roomName).equalsIgnoreCase(player.getName())) {
                Player owner = Bukkit.getPlayer(RoomReference.getRoomOwner(roomName));
                Reference.givePlayerMoney(owner, getRoomPurchasePrice(roomName));
                owner.sendMessage(Reference.prefix_normal + player.getName() + "님이 당신의 방을 인수하였습니다.");
            }

            Reference.removePlayerMoney(player, getRoomPurchasePrice(roomName));
            RoomReference.setRoomOwner(roomName, player);
            RoomReference.roomAddon.put(roomName, null);
            Reference.sendOpMessage(roomName + "방을 " + player.getName() + "가 " + getRoomPurchasePrice(roomName) + "를 지불하고 구매하였습니다.");
            setRoomPrice(roomName, getRoomPurchasePrice(roomName));

            if (roomName.equals("튜토리얼1")) {
                player.sendMessage("§f방 구매 튜토리얼을 완료하셨습니다! 다음 방으로 넘어가주세요.");
            } else if (roomName.equals("튜토리얼2")) {
                player.sendMessage("§f방 인수 튜토리얼을 완료하셨습니다! 다음 방으로 넘어가주세요.");
            }
        }
    }
}
