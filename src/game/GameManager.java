package game;

import buildings.Building;
import buildings.BuildBuildings;
import utill.ThreadedText;
import game.EndTurnManager;

import java.util.Random;

public class GameManager {
    private City city;
    private EconomyManager economyManager;
    private EndTurnManager endTurnManager;
    private int turn;
    private final Random random;
    private BuildBuildings BuildBuildings;


    public GameManager() {
        random = new Random();
        turn = 1;
    }

    public void startGame() {
        String cityName;
        while (true) {
            try {
                ThreadedText.printTextSync("========도시 건설 시뮬레이터========");
                ThreadedText.printTextSync("새로운 도시의 시장이 되었습니다.");
                cityName = InputManager.getStringInput("도시의 이름을 입력해주세요 (한글 또는 영문만 가능): ");

                // 빈 문자열 체크
                if (cityName.isEmpty()) {
                    ThreadedText.printTextSync("도시 이름을 입력해주세요!");
                    continue;
                }

                // 한글 또는 영문자만 있는지 정규식으로 체크
                if (!cityName.matches("^[가-힣a-zA-Z]+$")) {
                    ThreadedText.printTextSync("도시 이름은 한글 또는 영문자만 입력 가능합니다!");
                    continue;
                }
                break;
            } catch (Exception e) {
                ThreadedText.printTextSync("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        city = new City(cityName);
        BuildBuildings = new BuildBuildings(city);
        economyManager = new EconomyManager(city);
        endTurnManager = new EndTurnManager(city, economyManager);
        ThreadedText.printTextSync(cityName + " 시의 시장이 되신 걸 축하드립니다!");
        ThreadedText.printTextSync("초기 자금: 10,000원");
    }


    private boolean processCommand() {
        ThreadedText.printTextSync("\n=== 행동 선택 ===");
        ThreadedText.printTextSync("1. 주거용 건물 건설");
        ThreadedText.printTextSync("2. 상업용 건물 건설");
        ThreadedText.printTextSync("3. 공업용 건물 건설");
        ThreadedText.printTextSync("4. 건물 목록 보기");
        ThreadedText.printTextSync("5. 도시 상세 정보");
        ThreadedText.printTextSync("6. 턴 종료");
        ThreadedText.printTextSync("7. 게임 종료");

        int result = InputManager.getIntInputInRange("\n명령을 선택하세요: ", 1, 7);

        switch (result) {
            case 1:
                BuildBuildings.buildResidential();
                break;
            case 2:
                BuildBuildings.buildCommercial();
                break;
            case 3:
                BuildBuildings.buildIndustrial();
                break;
            case 4:
                displayBuildings();
                return processCommand();
            case 5:
                displayDetails();
                return processCommand();
            case 6:
                return true;
            case 7:
                return false;
        }
        return true;
    }


    private void processTurnEnd() {
        endTurnManager.processEndTurn();
    }



    private void displayBuildings() {
        ThreadedText.printText("\n=== 건물 목록 ===");
        int totalMaintenance = 0;

        // 주거용 건물
        ThreadedText.printTextSync("\n[ 주거용 건물 ]");
        for (int i = 0; i < city.getResidentialBuildings().size(); i++) {
            Building b = city.getResidentialBuildings().get(i);
            ThreadedText.printText(String.format("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost()));
            totalMaintenance += b.getMaintenanceCost();
        }

        // 상업용 건물
        ThreadedText.printTextSync("\n[ 상업용 건물 ]");
        for (int i = 0; i < city.getCommercialBuildings().size(); i++) {
            Building b = city.getCommercialBuildings().get(i);
            ThreadedText.printText(String.format("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost()));
            totalMaintenance += b.getMaintenanceCost();
        }

        // 공업용 건물
        ThreadedText.printTextSync("\n[ 공업용 건물 ]");
        for (int i = 0; i < city.getIndustrialBuildings().size(); i++) {
            Building b = city.getIndustrialBuildings().get(i);
            ThreadedText.printText(String.format("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost()));
            totalMaintenance += b.getMaintenanceCost();
        }

        ThreadedText.printTextSync(String.format("\n총 유지비: %,d원/턴\n", totalMaintenance));
    }

    private void displayDetails() {
        ThreadedText.printTextSync("\n=== 도시 상세 정보 ===");
        ThreadedText.printTextSync(String.format("도시 이름: %s", city.getName()));
        ThreadedText.printTextSync(String.format("현재 턴: %d", turn));
        ThreadedText.printTextSync(String.format("자금: %,d원", city.getMoney()));
        ThreadedText.printTextSync(String.format("인구: %,d명", city.getPopulation()));
        ThreadedText.printTextSync(String.format("행복도: %d%%", city.getHappiness()));
        ThreadedText.printTextSync(String.format("고용: %,d명", city.getEmployment()));
        ThreadedText.printTextSync(String.format("공해도: %d", city.getPollution()));

        // 건물 통계
        int totalBuildings = city.getResidentialBuildings().size() +
                city.getCommercialBuildings().size() + city.getIndustrialBuildings().size();
        ThreadedText.printTextSync(String.format("\n총 건물 수: %d개", totalBuildings));
        ThreadedText.printTextSync(String.format("- 주거용 건물: %d개", city.getResidentialBuildings().size()));
        ThreadedText.printTextSync(String.format("- 상업용 건물: %d개", city.getCommercialBuildings().size()));
        ThreadedText.printTextSync(String.format("- 공업용 건물: %d개", city.getIndustrialBuildings().size()));
    }

    private void displayStatus() {
        ThreadedText.printTextSync("=== " + city.getName() + " 현황" + " ( " + turn + " 턴)" + " ===");
        ThreadedText.printTextSync(String.format("현재 자금: %d원", city.getMoney()));
        ThreadedText.printTextSync(String.format("현재 인구: %d명", city.getPopulation()));
        ThreadedText.printTextSync(String.format("현재 행복도: %d", city.getHappiness()));
        ThreadedText.printTextSync(String.format("현재 고용: %d명", city.getEmployment()));
        ThreadedText.printTextSync(String.format("현재 오염도: %d", city.getPollution()));

        if (city.getMoney() < 1000 ) {
            ThreadedText.printText("자금 관리가 필요합니다.");
        }
        if (city.getHappiness() < 20 ) {
            ThreadedText.printText("시민들의 원성이 자자합니다.");
        }
        if (city.getPollution() > 50 ) {
            ThreadedText.printText("도시가 더럽습니다.");
        }
    }

    private void displayFinalStatus() {
        System.out.println("\n================================");
        System.out.println("        게임 종료 통계          ");
        System.out.println("================================");
        ThreadedText.printText(String.format("도시 이름: %s\n", city.getName()));
        ThreadedText.printText(String.format("생존 턴 수: %d\n", turn));
        ThreadedText.printText(String.format("최종 인구: %,d명\n", city.getPopulation()));
        ThreadedText.printText(String.format("최종 자금: %,d원\n", city.getMoney()));
        ThreadedText.printText(String.format("최종 행복도: %d%%\n", city.getHappiness()));
        ThreadedText.printText(String.format("최종 고용: %,d명\n", city.getEmployment()));
        ThreadedText.printText(String.format("건설한 건물 수: %d개\n",
                city.getResidentialBuildings().size() + city.getCommercialBuildings().size()));
    }




    public static void main(String[] args) {
        GameManager game = new GameManager();
        game.startGame();
        game.runGameLoop();

    }

    public void runGameLoop() {
        boolean running = true;

        while (running) {
            displayStatus();
            running = processCommand();

            if (!running) {
                break;
            }

            processTurnEnd();
            turn++;

            if (city.isGameOver()) {
                running = false;
            }
        }

        displayFinalStatus();
    }
}

