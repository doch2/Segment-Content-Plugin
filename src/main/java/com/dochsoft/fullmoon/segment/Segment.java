package com.dochsoft.fullmoon.segment;

import com.dochsoft.fullmoon.segment.beforeLocation.PlayerBeforeLocation;
import com.dochsoft.fullmoon.segment.item.InformationTabletItem;
import com.dochsoft.fullmoon.segment.item.StopItem;
import com.dochsoft.fullmoon.segment.minigame.DoorArea;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomCommand;
import com.dochsoft.fullmoon.segment.room.RoomConfig;
import com.dochsoft.fullmoon.segment.room.RoomBukkitEvent;
import com.dochsoft.fullmoon.segment.room.door.DoorChooseBuyOrOpen;
import com.dochsoft.fullmoon.segment.room.door.OpenDoorCommand;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import com.dochsoft.fullmoon.segment.room.setRoomArea.SetRoomCommand;
import com.dochsoft.fullmoon.segment.shop.ShopCommand;
import com.dochsoft.fullmoon.segment.addon.GetAddonItemCommand;
import com.dochsoft.fullmoon.segment.addon.useAddonCommand;
import com.dochsoft.fullmoon.segment.timer.TimerCommand;
import com.dochsoft.fullmoon.segment.timer.TimerReference;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Segment extends JavaPlugin {
    private static Segment Instance;

    public static Economy econ = null;

    RoomBukkitEvent roomEvent = new RoomBukkitEvent();
    BukkitEvent event = new BukkitEvent();

    @Override
    public void onEnable() {
        Instance = this;
        getServer().getMessenger().registerOutgoingPluginChannel(this, "SEGMENT");
        getCommand("useAddon").setExecutor(new useAddonCommand());
        getCommand("roomArea").setExecutor(new SetRoomCommand());
        getCommand("buyRoom").setExecutor(new BuyRoomCommand());
        getCommand("openDoor").setExecutor(new OpenDoorCommand());
        getCommand("getAddon").setExecutor(new GetAddonItemCommand());
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("tppreviousloc").setExecutor(new PlayerBeforeLocation());
        getCommand("openorbuy").setExecutor(new DoorChooseBuyOrOpen());
        getCommand("getInfoTablet").setExecutor(new InformationTabletItem());
        getCommand("segment").setExecutor(new SegmentCommand());
        getServer().getPluginManager().registerEvents(this.roomEvent, this);
        getServer().getPluginManager().registerEvents(this.event, this);

        try {
            RoomConfig.loadRoomArea();
            RoomConfig.loadDoorArea();
            RoomConfig.loadHallwayArea();
            DoorArea.loadChestArea();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
        if (!setupEconomy()) {
            System.out.println(Reference.prefix_error + "Vault 플러그인이 감지되지 않았습니다. 플러그인을 비활성화합니다.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                String temp;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    temp = "MONEY||" + (int) econ.getBalance(onlinePlayer);
                    onlinePlayer.sendPluginMessage(Segment.getInstance(), "SEGMENT", temp.getBytes());
                }
            }
        }, 0L, 2L); //2틱당 돌아가게, 20틱 = 1초


        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                String temp;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    temp = "TIMER||" + Boolean.toString(TimerReference.showTimer) + "||" + TimerReference.getTimerTimeHour() + "||" + TimerReference.getTimerTimeMinute() + "||" + TimerReference.getTimerTimeSecond();
                    onlinePlayer.sendPluginMessage(Segment.getInstance(), "SEGMENT", temp.getBytes());
                }
                if (TimerReference.timerTime == 0) {
                    TimerReference.showTimer = false;
                } else {
                    TimerReference.timerTime = TimerReference.timerTime - 1;
                }
            }
        }, 0L, 20L); //10틱당 돌아가게, 20틱 = 1초

        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                String temp;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (onlinePlayer.getWorld() != Reference.playerBeforeWorld.get(onlinePlayer.getUniqueId()).getWorld()) {
                        PlayerConfig.setPlayerBeforeLocation(onlinePlayer, Reference.playerBeforeWorld.get(onlinePlayer.getUniqueId()));
                    }

                    Reference.playerBeforeWorld.put(onlinePlayer.getUniqueId(), onlinePlayer.getLocation());
                }
            }
        }, 0L, 20L); //10틱당 돌아가게, 20틱 = 1초

        /* Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            public void run() {
                int roomNumber1 = 0;
                int roomNumber2 = 0;
                int roomNumber3 = 0;
                int roomNumber4 = 0;
                int roomNumber5 = 0;
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    roomNumber1 = 0;
                    roomNumber2 = 0;
                    roomNumber3 = 0;
                    roomNumber4 = 0;
                    roomNumber5 = 0;
                    for (int i=0; i < RoomArea.roomAreaList.size(); i++) {
                        if (RoomArea.roomArea.get(RoomArea.roomAreaList.get(i) + ".Owner").equalsIgnoreCase(onlinePlayer.getName())) {
                            if (RoomArea.roomAreaList.get(i).toString().contains("1층")) {
                                roomNumber1 = roomNumber1 + 1;
                            } else if (RoomArea.roomAreaList.get(i).toString().contains("2층")) {
                                roomNumber2 = roomNumber2 + 1;
                            } else if (RoomArea.roomAreaList.get(i).toString().contains("3층")) {
                                roomNumber3 = roomNumber3 + 1;
                            } else if (RoomArea.roomAreaList.get(i).toString().contains("4층")) {
                                roomNumber4 = roomNumber4 + 1;
                            } else if (RoomArea.roomAreaList.get(i).toString().contains("5층")) {
                                roomNumber5 = roomNumber5 + 1;
                            }
                        }
                    }

                    if (roomNumber1 >= 15) {
                        if (roomNumber1 >= 20) {
                            Reference.sendOpMessage("§6" + onlinePlayer.getName() + "§f님이 1층에서 소유한 방 개수가 20개를 넘었습니다.");
                        } else {
                            Reference.sendMessageAllPlayer("§6" + onlinePlayer.getName() + "§f님이 1층에서 소유하고 있는 방 개수가 15개를 넘었습니다.");
                        }
                    } else if (roomNumber2 >= 15) {
                        if (roomNumber2 >= 20) {
                            Reference.sendOpMessage("§6" + onlinePlayer.getName() + "§f님이 2층에서 소유한 방 개수가 20개를 넘었습니다.");
                        } else {
                            Reference.sendMessageAllPlayer("§6" + onlinePlayer.getName() + "§f님이 2층에서 소유하고 있는 방 개수가 15개를 넘었습니다.");
                        }
                    } else if (roomNumber3 >= 15) {
                        if (roomNumber3 >= 20) {
                            Reference.sendOpMessage("§6" + onlinePlayer.getName() + "§f님이 3층에서 소유한 방 개수가 20개를 넘었습니다.");
                        } else {
                            Reference.sendMessageAllPlayer("§6" + onlinePlayer.getName() + "§f님이 3층에서 소유하고 있는 방 개수가 15개를 넘었습니다.");
                        }
                    } else if (roomNumber4 >= 15) {
                        if (roomNumber4 >= 20) {
                            Reference.sendOpMessage("§6" + onlinePlayer.getName() + "§f님이 4층에서 소유한 방 개수가 20개를 넘었습니다.");
                        } else {
                            Reference.sendMessageAllPlayer("§6" + onlinePlayer.getName() + "§f님이 4층에서 소유하고 있는 방 개수가 15개를 넘었습니다.");
                        }
                    } else if (roomNumber5 >= 15) {
                        if (roomNumber5 >= 20) {
                            Reference.sendOpMessage("§6" + onlinePlayer.getName() + "§f님이 5층에서 소유한 방 개수가 20개를 넘었습니다.");
                        } else {
                            Reference.sendMessageAllPlayer("§6" + onlinePlayer.getName() + "§f님이 5층에서 소유하고 있는 방 개수가 15개를 넘었습니다.");
                        }
                    }
                }
            }
        }, 0L, 200L); //10틱당 돌아가게, 20틱 = 1초 */

        StopItem.runBukkitScheduler();

        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.ENABLE_MESSAGE);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Reference.prefix_normal + Reference.DISABLE_MESSAGE);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Segment getInstance() {
        return Instance;
    }
}
