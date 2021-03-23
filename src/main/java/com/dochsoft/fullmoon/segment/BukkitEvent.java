package com.dochsoft.fullmoon.segment;

import com.dochsoft.fullmoon.segment.addon.AddonReference;
import com.dochsoft.fullmoon.segment.addon.UseAddonGui;
import com.dochsoft.fullmoon.segment.addon.UseAddonGuiItemAddonThread;
import com.dochsoft.fullmoon.segment.beforeLocation.PlayerBeforeLocation;
import com.dochsoft.fullmoon.segment.item.ChangeLocationItem;
import com.dochsoft.fullmoon.segment.item.IceItem;
import com.dochsoft.fullmoon.segment.item.InformationTabletItem;
import com.dochsoft.fullmoon.segment.item.StopItem;
import com.dochsoft.fullmoon.segment.minigame.DoorArea;
import com.dochsoft.fullmoon.segment.minigame.DoorOpen;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.shop.ShopGui;
import com.dochsoft.fullmoon.segment.shop.ShopReference;
import com.nametagedit.plugin.NametagEdit;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;


public class BukkitEvent implements Listener {

    /* @EventHandler
    public void useItemMoneyCoupon (PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;
        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == 4456)  {
            String itemName = event.getItem().getItemMeta().getDisplayName();
            Reference.givePlayerMoney(player, Integer.parseInt(itemName.substring(2, itemName.lastIndexOf("§f"))));
            Reference.removeInventoryItems(player.getInventory(), Material.getMaterial(4456), 1);
            player.sendMessage(Reference.prefix_normal + "쿠폰을 사용하였습니다.");
        }
    } */

    @EventHandler
    public static void playerJoinEvent(PlayerJoinEvent event) {
        StopItem.playerStopItem.put(event.getPlayer().getUniqueId(), false);
        StopItem.playerStopTime.put(event.getPlayer().getUniqueId(), 0);
        RoomReference.numberKeypad.put(event.getPlayer().getUniqueId(), 0);
        PlayerConfig.createPlayerConfig(event.getPlayer(), null);
        Reference.playerBeforeWorld.put(event.getPlayer().getUniqueId(), PlayerBeforeLocation.getPlayerBeforeLoc(event.getPlayer()));
        Reference.isPlayerDeath.put(event.getPlayer().getUniqueId(), Reference.getPlayerBeforeDeath(event.getPlayer()));

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        org.bukkit.scoreboard.Team nameTagInvisiable = scoreboard.registerNewTeam("nameTagInvisible");
        nameTagInvisiable.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        nameTagInvisiable.addPlayer(event.getPlayer());
    }

    @EventHandler
    public static void playerQuitEvent(PlayerQuitEvent event) {
        //StopItem.playerStopItem.put(event.getPlayer().getUniqueId(), false);
    }

    @EventHandler
    public static void playerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (event.getEntity().getWorld() == Bukkit.getWorld("flat1")) {
            Reference.isPlayerDeath.put(event.getEntity().getUniqueId(), true);
            PlayerConfig.setPlayerDeath(event.getEntity(), true);
        }
        Reference.sendOpMessage("§6" + player.getKiller().getName() + "§f님이 §6" + player.getName() + "§f님을 죽였습니다.");
    }

    @EventHandler
    public void PlayerChatEvent(PlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat("§r<" + NametagEdit.getApi().getNametag(player).getPrefix() + player.getName() + NametagEdit.getApi().getNametag(player).getSuffix() + "§r> " + event.getMessage());
    }

    public static boolean hasClickedTop(InventoryClickEvent event) {
        return event.getRawSlot() == event.getSlot();
    }

    @EventHandler
    public void clockUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == 347) {
            ClockGui.openClockGui(player);
        }
    }

    @EventHandler
    public void clockGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("시계 메뉴"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event)) {
            event.setCancelled(true);

            if (event.getSlot() == 1) {
                player.closeInventory();
                player.performCommand("shop open 야생상점_광물");
            } else if (event.getSlot() == 2) {
                player.closeInventory();
                player.performCommand("shop open 야생상점_채집");
            } else if (event.getSlot() == 3) {
                player.closeInventory();
                player.performCommand("shop open 야생상점_낚시");
            } else if (event.getSlot() == 4) {
                player.closeInventory();
                player.performCommand("shop open 애드온상점");
            } else if (event.getSlot() == 5) {
                player.closeInventory();
                player.performCommand("shop open 아이템상점");
            } else if (event.getSlot() == 7) {
                player.closeInventory();
                ClockGui.openRecycleBinGui(player);
            }
        }
    }

    @EventHandler
    public void recycleBinGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("휴지통"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event) && event.getSlot() == 35) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler
    public void itemUseEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Boolean rightClick = event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR;

        if (StopItem.playerStopItem.get(player.getUniqueId())) {
            System.out.println(event.getItem().getTypeId());
            if (event.getItem().getTypeId() != Reference.CHANGE_LOCATION_ITEM_CODE && event.getItem().getTypeId() != Reference.ICE_ITEM_CODE && event.getItem().getTypeId() != Reference.STOP_ITEM_CODE) {
                event.setCancelled(true);
                return;
            }
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.CHANGE_LOCATION_ITEM_CODE)  { //위치 변환기
            ChangeLocationItem.choosePlayerGui(player);
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.ICE_ITEM_CODE)  { //아이스
            IceItem.stopAllPlayerBehavior(player);
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.STOP_ITEM_CODE)  { //스탑
            StopItem.choosePlayerGui(player);
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.INFORMATION_TABLET_ITEM && event.getItem().getItemMeta().getDisplayName().contains("방개수"))  { //정보태블릿
            InformationTabletItem.RoomInformationBook(player);
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.INFORMATION_TABLET_ITEM && event.getItem().getItemMeta().getDisplayName().contains("플레이어"))  { //정보태블릿
            InformationTabletItem.PlayerInformationBook(player);
        }

        if (rightClick && event.getItem() != null && event.getItem().getTypeId() == Reference.WORLD_CHANGE_ITEM)  { //이전위치 아이템
            player.teleport(PlayerBeforeLocation.getPlayerBeforeLoc(player));
        }
    }

    @EventHandler
    public void useAddonGuiEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("애드온 사용할 방 선택"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
            String roomName = itemName.substring(itemName.lastIndexOf("§6") + 2, itemName.length());

            player.closeInventory();
            UseAddonGui.openUseAddonGui(player, roomName);
        }

        if (((event.getView().getTitle().contains("애드온 사용 - "))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            if (hasClickedTop(event)) {
                ItemStack addonItem = event.getInventory().getItem(10);
                String roomName = event.getView().getTitle().substring(event.getView().getTitle().lastIndexOf("- ") + 2);

                if (event.getSlot() != 10 && event.getSlot() != 16) {
                    event.setCancelled(true);
                }

                Runnable runnable = new UseAddonGuiItemAddonThread(event);
                new Thread(runnable).start();

                if (event.getSlot() == 22) {
                    if (addonItem == null) {
                        player.closeInventory();
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "애드온 아이템을 넣지 않았습니다. 아이템을 넣어주신 후 다시 시도해주세요.")));
                    } else if (addonItem.getTypeId() == Reference.DEFAULT_ADDON_CODE) {
                        player.closeInventory();
                        AddonReference.applyAddon(roomName, addonItem, event.getInventory().getItem(16));
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "애드온이 성공적으로 적용되었습니다!")));

                        if (roomName.equals("튜토리얼4")) {
                            Reference.fourthTutoRoomAddon = Reference.fourthTutoRoomAddon + 1;
                            if (Reference.fourthTutoRoomAddon == 2) {
                                player.sendMessage("§f애드온 튜토리얼이 완료되었습니다! 다음 방으로 이동해주세요.");
                            }
                        }
                    } else if (addonItem.getTypeId() == Reference.ITEM_ADDON_CODE) {
                        if (event.getInventory().getItem(16) != null) {
                            player.closeInventory();
                            AddonReference.applyAddon(roomName, addonItem, event.getInventory().getItem(16));
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "애드온이 성공적으로 적용되었습니다!")));

                            if (roomName.equals("튜토리얼4")) {
                                Reference.fourthTutoRoomAddon = Reference.fourthTutoRoomAddon + 1;
                                if (Reference.fourthTutoRoomAddon == 2) {
                                    player.sendMessage("§f애드온 튜토리얼이 완료되었습니다! 다음 방으로 이동해주세요.");
                                }
                            }
                        } else {
                            player.closeInventory();
                            AddonReference.applyAddon(roomName, addonItem, event.getInventory().getItem(16));
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "아이템 애드온 적용시 원하는 아이템을 등록하지 않으셨습니다. 아이템을 넣어주신 후 다시 시도해주세요.")));
                        }
                    } else {
                        player.getInventory().addItem(addonItem);
                        player.closeInventory();
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_normal + "애드온 아이템이 아닙니다. 다시 시도해 주십시오.")));
                    }
                }
            } else {
                Runnable runnable = new UseAddonGuiItemAddonThread(event);
                new Thread(runnable).start();
            }
        }
    }

    @EventHandler
    public void changeLocationPlayerChooseGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("위치변환기"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
            Player player2 = Bukkit.getPlayer(itemName.substring(2, itemName.lastIndexOf("에")));

            ChangeLocationItem.changePlayerLocation(player, player2);

            player.sendMessage(Reference.prefix_normal + "위치 변환기 아이템을 사용하여 " + player2.getName() + "의 위치로 이동하였습니다.");
            player2.sendMessage(Reference.prefix_normal + player.getName() + "가 위치 변환기 아이템을 사용하여 " + player.getName() + "의 위치로 이동되었습니다.");
            Reference.removeInventoryItems(player.getInventory(), Material.getMaterial(Reference.CHANGE_LOCATION_ITEM_CODE), (byte) 0, 1);
            player.closeInventory();
        }
    }

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        if (StopItem.playerStopItem.get(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void stopItemPlayerChooseGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (((event.getView().getTitle().contains("스탑 아이템"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
            Player player2 = Bukkit.getPlayer(itemName.substring(2, itemName.lastIndexOf("을 10초")));

            StopItem.stopPlayer(player2);

            player.sendMessage(Reference.prefix_normal + "스탑 아이템을 사용하여 " + player2.getName() + "을 10초동안 행동정지 시켰습니다.");
            player2.sendMessage(Reference.prefix_normal + "누군가가 스탑 아이템을 사용하여 10초동안 행동이 정지 됩니다.");
            Reference.removeInventoryItems(player.getInventory(), Material.getMaterial(Reference.STOP_ITEM_CODE), (byte) 0, 1);
            player.closeInventory();
        }
    }

    @EventHandler
    public void shopGuiClickEvent(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();

        if ((event.getView().getTitle().contains(Reference.prefix_shop)) && ((event.getView().getTitle().contains("구매"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            String shopName = event.getView().getTitle().substring(Reference.prefix_shop.length(), event.getView().getTitle().length() - 9);
            if (hasClickedTop(event)) {
                if (event.getSlot() < 36) {
                    if ((event.isLeftClick()) && (!event.isRightClick()) && (!event.isShiftClick())) {
                        ShopReference.buyItem(player, shopName, event);
                    } else if ((event.isLeftClick()) && (!event.isRightClick()) && (event.isShiftClick())) {
                        for (int i=0; i < 64; i++) {
                            ShopReference.buyItem(player, shopName, event);
                        }
                    }
                } else {
                    ShopReference.shopPageChangeButtonClicked(event, player, shopName);
                }
            } else {
                if ((event.isLeftClick()) && (!event.isRightClick()) && (!event.isShiftClick()) && ShopReference.isPlayerInvItemEqualSellItem(shopName, event, player)) {
                    ShopGui.playerShopMode.put(player.getUniqueId(), "Sell");
                    ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                    ShopGui.playerShopMode.put(player.getUniqueId(), "Buy");
                } else if ((event.isLeftClick()) && (!event.isRightClick()) && (event.isShiftClick()) && ShopReference.isPlayerInvItemEqualSellItem(shopName, event, player)) {
                    ShopGui.playerShopMode.put(player.getUniqueId(), "Sell");
                    ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                    ShopGui.playerShopMode.put(player.getUniqueId(), "Buy");
                }
            }
        } else if ((event.getView().getTitle().contains(Reference.prefix_shop)) && ((event.getView().getTitle().contains("판매"))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR)) {
            event.setCancelled(true);
            String shopName = event.getView().getTitle().substring(Reference.prefix_shop.length(), event.getView().getTitle().length() - 9);
            if (hasClickedTop(event)) {
                if (event.getSlot() < 36) {
                    if ((event.isLeftClick()) && (!event.isRightClick()) && (!event.isShiftClick())) {
                        ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                    } else if ((event.isLeftClick()) && (!event.isRightClick()) && (event.isShiftClick())) {
                        ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                    }
                } else {
                    ShopReference.shopPageChangeButtonClicked(event, player, shopName);
                }
            } else {
                if ((event.isLeftClick()) && (!event.isRightClick()) && (!event.isShiftClick()) && ShopReference.isPlayerInvItemEqualSellItem(shopName, event, player)) {
                    ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                } else if ((event.isLeftClick()) && (!event.isRightClick()) && (event.isShiftClick()) && ShopReference.isPlayerInvItemEqualSellItem(shopName, event, player)) {
                    ShopReference.sellItem(player, shopName, event, hasClickedTop(event));
                }
            }
        }
    }

    @EventHandler
    public void playerClickMinigameChest(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }
        Player player = event.getPlayer();
        String clickRoomName = null;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location block = event.getClickedBlock().getLocation();
            for (int i = 0; i < DoorArea.doorList.size(); i++) {
                String chestName = (String) DoorArea.doorList.get(i);

                if (DoorArea.isPlayerClickChest(chestName, block, "X") && DoorArea.isPlayerClickChest(chestName, block, "Y") && DoorArea.isPlayerClickChest(chestName, block, "Z")) {
                    clickRoomName = chestName;
                    event.setCancelled(true);
                    DoorOpen.getDoorOpenGui(player, clickRoomName);
                    return;
                }
            }
        }
    }


    @EventHandler
    public void ChestOpenGui(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String guiName = event.getView().getTitle();

        if (((guiName.contains(DoorOpen.doorGuiName))) &&
                (event.getCurrentItem() != null) && (event.getCurrentItem().getType() != Material.AIR) && hasClickedTop(event)) {
            event.setCancelled(true);

            int clickedNumber = RoomReference.numberKeypad.get(player.getUniqueId());
            String chestName = guiName.substring(guiName.lastIndexOf("-" ) + 2);

            if (event.getSlot() == 32) {
                RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                event.getInventory().clear(14);
                event.getInventory().clear(15);
                event.getInventory().clear(16);
            } else if (event.getSlot() == 34) {
                if (event.getInventory().getItem(14) == null || event.getInventory().getItem(15) == null || event.getInventory().getItem(16) == null) {
                    player.closeInventory();
                    RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "비밀번호을 정확하게 입력해주세요.")));
                } else {
                    int playerPassword = RoomReference.getCouponPriceFromItem(event.getInventory().getItem(14), event.getInventory().getItem(15), event.getInventory().getItem(16));

                    if (playerPassword == Integer.parseInt(DoorArea.doorArea.get(chestName + ".Password"))) {
                        player.closeInventory();
                        RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                        DoorOpen.openDoor(player, chestName);
                    } else {
                        player.closeInventory();
                        RoomReference.numberKeypad.put(player.getUniqueId(), 0);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "비밀번호가 다릅니다")));
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
