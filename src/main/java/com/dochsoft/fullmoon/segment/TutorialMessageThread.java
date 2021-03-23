package com.dochsoft.fullmoon.segment;

import org.bukkit.entity.Player;

public class TutorialMessageThread extends Thread {
    private Player player;
    private String tutorialKind;

    public TutorialMessageThread(Player player, String tutorialKind) {
        this.player = player;
        this.tutorialKind = tutorialKind;

    }

    public void run() {
        try {
            if (tutorialKind.equals("1")) {
                firstTuto(player);
            } else if (tutorialKind.equals("2")) {
                secondTuto(player);
            } else if (tutorialKind.equals("3")) {
                thirdTuto(player);
            } else if (tutorialKind.equals("3-1")) {
                useOpenCardKeyTuto(player);
            } else if (tutorialKind.equals("4")) {
                fourthTuto(player);
            } else if (tutorialKind.equals("5")) {
                fifthTuto(player);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void firstTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ[방 구매]ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f방에 배치되어 있는 시스템 블럭을 클릭하여 §6[방 구매]§f를 선택하십시오.");
        Thread.sleep(2000);
        player.sendMessage("§6[방 구매]§f에서 현재 해당방의 주인과, 가격, 적용되어 있는 애드온을 확인할 수 있습니다.");
        Thread.sleep(2000);
        player.sendMessage("§6[방 사기]§f버튼을 눌러 방을 구매하십시오.");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }

    private static void secondTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ[방 인수]ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f구매하려는 방에 주인이 이미 존재할 시, 해당 방을 인수할 수 있습니다. 인수 방법은 방을 구매하는 방법과 동일합니다.");
        Thread.sleep(2000);
        player.sendMessage("§f해당방에 대한 애드온 적용여부에 따라 인수할 때 내는 금액과 아이템이 달라집니다.");
        Thread.sleep(2000);
        player.sendMessage("§f방 구매는 오로지 해당방 안에 설치되어 있는 시스템 블록을 통해서만 가능합니다. 복도에 있는 시스템 블록을 통해서는 불가능합니다.");
        Thread.sleep(2000);
        player.sendMessage("§6[방 사기]§f버튼을 눌러 방을 인수하십시오.");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }

    private static void thirdTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ[카드키]ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f카드키는 방 또는 복도에 배치되어 있는 시스템 블럭에서 생성할 수 있습니다.");
        Thread.sleep(2000);
        player.sendMessage("§f방에 배치되어 있는 시스템 블럭을 클릭한 뒤 §6[방 문 열기]§f를 선택하십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f오른쪽 밑에 배치되어 있는 §6[카드키 만들러가기]§f 를 클릭해 카드키 제작창으로 들어가십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f키패드를 사용하여 원하는 액수의 카드키를 제작하십시오.");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }

    private static void useOpenCardKeyTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f제작한 카드키 중 §6[해제]§f용 카드키를 사용하여 해당 방의 문의 잠금을 해제하십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f방 문의 잠금이나 잠금해제는 방과 복도에 놓여있는 시스템 블럭 둘 다 가능합니다. ");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }

    private static void fourthTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ[애드온]ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f방에 배치되어 있는 시스템 블럭을 클릭해 §6[애드온 적용]§f 을 선택하십시오.");
        Thread.sleep(2000);
        player.sendMessage("§왼쪽 빈칸에 애드온을 올려놓은 후 적용하십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f아이템 애드온을 올려놓을 시, 오른쪽에 생성되는 새로운 빈칸에 등록하고 싶은 아이템을 올려놓으십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f가운데 적용 버튼을 눌러 애드온을 적용시키십시오.");
        Thread.sleep(2000);
        player.sendMessage("§f일반 애드온을 적용할 시 오른쪽에 생성되는 아이템 등록용 빈칸은 생성되지 않습니다. 오로지 아이템 애드온을 적용할때만 생성됩니다.");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }

    private static void fifthTuto(Player player) throws InterruptedException {
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ[상점, 아이템]ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
        player.sendMessage("§     ");
        player.sendMessage("§f세그먼트의 모든 상점거래는 시계를 통해 이루어집니다.");
        Thread.sleep(2000);
        player.sendMessage("§이 컨텐츠에는 다양한 아이템들이 있습니다. 한번씩 사용해보세요.");
        Thread.sleep(2000);
        player.sendMessage("§f야생과 세그먼트 모두, 살인이 금지됩니다. 만약 이를 어기고 다른 플레이어를 살해할 시, 게임에서 그 즉시 탈락됩니다. (야생내에서 자연사는 가능)");
        player.sendMessage("§     ");
        player.sendMessage("§ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
    }
}

