package com.dochsoft.fullmoon.segment.room;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.Segment;
import com.dochsoft.fullmoon.segment.TutorialMessageThread;
import com.dochsoft.fullmoon.segment.addon.UseAddonGui;
import com.dochsoft.fullmoon.segment.item.ChangeLocationItem;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.room.door.CreateCoupon;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RoomBukkitEvent implements Listener {

    @EventHandler
    public void playerClickBlock(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemstack = event.getItem();
        if ((itemstack != null) && (itemstack.getType() == Material.STONE_AXE) && (player.isOp())) {
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                event.setCancelled(true);
                RoomArea.areaSetLeftClick.put(player.getUniqueId(), block);
                player.sendMessage(Reference.prefix_normal + "지정된 첫 번째 위치: §e" + block.getX() + ", " + block.getY() + ", " + block.getZ());
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);
                RoomArea.areaSetRightClick.put(player.getUniqueId(), block);
                player.sendMessage(Reference.prefix_normal + "지정된 두 번째 위치: §e" + block.getX() + ", " + block.getY() + ", " + block.getZ());
            }
        }
    }

    @EventHandler
    public void playerClickDoor(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }
        Player player = event.getPlayer();
        String clickRoomName = null;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location block = event.getClickedBlock().getLocation();
            for (int i=0; i < RoomArea.roomAreaList.size(); i++) {
                String roomName = (String) RoomArea.roomAreaList.get(i);
                for (int i2=0; i2 < Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorNumber")); i2++) {
                    for (int i3=0; i3 < 2; i3++) {
                        String InandOut = null;
                        if (i3 == 0) {
                            InandOut = "in";
                        } else {
                            InandOut = "out";
                        }

                        if (RoomReference.isPlayerClickDoor(roomName, (i2 + 1), InandOut, block, "X") && RoomReference.isPlayerClickDoor(roomName, (i2 + 1), InandOut, block, "Y") && RoomReference.isPlayerClickDoor(roomName, (i2 + 1), InandOut, block, "Z")) {
                            clickRoomName = roomName;
                            RoomReference.teleportRoom(player, clickRoomName, InandOut, (i2 + 1));
                            return;
                        }
                    }
                    //if (RoomArea.doorArea.get(roomName + "_" + i2 + ".Direction").equalsIgnoreCase("floor")) { //바닥
                }
            }
        }
    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        String fromArea = RoomArea.inAreaCheck(from); //맞을경우 구역 이름, 틀릴경우 String형으로 false
        String toArea = RoomArea.inAreaCheck(to); //맞을경우 구역 이름, 틀릴경우 String형으로 false
        if (fromArea.equals(toArea)) {
            RoomArea.playerNowAreaName.put(player.getUniqueId(), toArea);
            //return;
        } else if (toArea.equals("false")) { //구역안에 있지 않을 경우
            RoomArea.playerNowAreaName.put(player.getUniqueId(), null);
        } else {
            RoomArea.playerNowAreaName.put(player.getUniqueId(), toArea);
        }

        if (toArea.equals("히든방") && fromArea.equals("1층-5")) {
            Reference.sendOpMessage(player.getName() + "이 히든방에 진입하였습니다.");
        }
    }

    public static boolean hasClickedTop(InventoryClickEvent event)
    {
        return event.getRawSlot() == event.getSlot();
    }

    @EventHandler
    public void chooseBuyOrOpenGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("방 구매 & 문 잠금"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event)) {
            event.setCancelled(true);

            if (event.getSlot() == 2) {
                player.closeInventory();
                player.performCommand("buyroom");
            } else if (event.getSlot() == 4) {
                player.closeInventory();
                UseAddonGui.openUseAddonGui(player, RoomArea.playerNowAreaName.get(player.getUniqueId()));
            } else if (event.getSlot() == 6) {
                player.closeInventory();
                player.performCommand("opendoor");
            }
        }
    }

    @EventHandler
    public void BuyRoomGui(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        String guiName = event.getView().getTitle();

        if ((guiName.contains("방 구매")) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            if (hasClickedTop(event) && event.getSlot() == 43) {
                RoomReference.buyRoom(player, guiName.substring(guiName.lastIndexOf("- ") + 2));
            }
        }
    }

    @EventHandler
    public void OpenDoorGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String guiName = event.getView().getTitle();

        if (((event.getView().getTitle().contains("방 문 열기"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event)) {
            event.setCancelled(true);
            String roomName = RoomReference.openDoorGuiRoomName.get(player.getUniqueId());

            if (event.getSlot() == 53) {
                player.closeInventory();
                CreateCoupon.getCouponGui(player);
            } else if (event.getSlot() == 28) {
                if (RoomReference.hasPlayerRoomCoupon(player, roomName, "잠금해제")) {
                    RoomReference.setRoomDoorAllow(roomName, true);
                    RoomArea.roomArea.put(roomName + ".doorOpenPlayer", player.getName());
                    player.closeInventory();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "성공적으로 문이 열렸습니다.")));
                } else {
                    player.closeInventory();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "카드키 아이템이 존재하지 않거나 카드키 가격이 현재 문 가격보다 낮습니다. 다시 시도해주세요.")));
                }

                if (roomName.equals("튜토리얼3")) {
                    player.sendMessage("§f카드키 튜토리얼이 끝났습니다! 다음 방으로 이동해주세요.");
                }
            } else if (event.getSlot() == 34) {
                if (RoomReference.hasPlayerRoomCoupon(player, roomName, "잠금")) {
                    RoomReference.setRoomDoorAllow(roomName, false);
                    RoomArea.roomArea.put(roomName + ".doorOpenPlayer", player.getName());
                    player.closeInventory();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "성공적으로 문이 잠겼습니다.")));

                    if (roomName.equals("튜토리얼3")) {
                        Runnable runnable = new TutorialMessageThread(player, "3-1");
                        new Thread(runnable).start();
                    }
                } else {
                    player.closeInventory();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "카드키 아이템이 존재하지 않거나 카드키 가격이 현재 문 가격보다 낮습니다. 다시 시도해주세요.")));
                }
            }
        }
    }

    @EventHandler
    public void CreateCouponGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String guiName = event.getView().getTitle();

        if (((event.getView().getTitle().contains("문 카드키 생성"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event)) {
            event.setCancelled(true);

            int clickedNumber = RoomReference.numberKeypad.get(player.getUniqueId());
            String roomName = RoomReference.openDoorGuiRoomName.get(player.getUniqueId());

            if (event.getSlot() == 32) {
                RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                event.getInventory().clear(14);
                event.getInventory().clear(15);
                event.getInventory().clear(16);
            } else if (event.getSlot() == 34) {
                if (event.getInventory().getItem(14) == null && event.getInventory().getItem(15) == null && event.getInventory().getItem(16) == null) {
                    player.closeInventory();
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "금액을 정확하게 입력해주세요.")));
                } else {
                    int playerSetDoorPrice = RoomReference.getCouponPriceFromItem(event.getInventory().getItem(14), event.getInventory().getItem(15), event.getInventory().getItem(16));
                    if (Segment.econ.getBalance(player) < playerSetDoorPrice) {
                        player.closeInventory();
                        RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "현재 코인을 충분히 가지고 있지 않습니다. 다시 시도해주세요.")));
                    } else {
                        if (playerSetDoorPrice < Integer.parseInt(RoomArea.roomArea.get(roomName + ".DoorPrice"))) {
                            player.closeInventory();
                            RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "설정하신 카드키 금액이 기존 문 가격보다 작습니다. 다시 시도해주세요.")));
                        } else {
                            player.closeInventory();
                            RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                            CreateCoupon.playerGiveDoorCoupon(player, roomName, playerSetDoorPrice);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "카드키가 발급되었습니다.")));

                            if (roomName.equals("튜토리얼3")) {
                                player.sendMessage("§f제작한 카드키 중 §6[잠금]§f용 카드키를 사용하여 해당 방의 문을 잠그십시오.");
                            }
                        }
                    }
                }
            }

            if (clickedNumber != 3) {
                if (event.getSlot() == 10) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberOneItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 11) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberTwoItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 12) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberThreeItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 19) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberFourItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 20) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberFiveItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 21) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberSixItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 28) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberSevenItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 29) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberEightItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 30) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberNineItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                } else if (event.getSlot() == 38) {
                    Reference.setGuiItemNoLore("", BuyRoomGui.NumberZeroItem, 0, 1, 14 + clickedNumber, event.getClickedInventory());
                    RoomReference.numberKeypad.put(player.getUniqueId(), clickedNumber + 1);
                }
            }
        }
    }
}
