package game;

import buildings.Building;
import buildings.BuildBuildings;

import java.util.Random;

public class GameManager {
    private City city;
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
                System.out.println("========도시 건설 시뮬레이터========");
                System.out.println("새로운 도시의 시장이 되었습니다.");
                cityName = InputManager.getStringInput("도시의 이름을 입력해주세요 (한글 또는 영문만 가능): ");

                // 빈 문자열 체크
                if (cityName.isEmpty()) {
                    System.out.println("도시 이름을 입력해주세요!");
                    continue;
                }

                // 한글 또는 영문자만 있는지 정규식으로 체크
                if (!cityName.matches("^[가-힣a-zA-Z]+$")) {
                    System.out.println("도시 이름은 한글 또는 영문자만 입력 가능합니다!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
            }
        }

        city = new City(cityName, 10000);
        BuildBuildings = new BuildBuildings(city);
        System.out.println(cityName + " 시의 시장이 되신 걸 축하드립니다!");
        System.out.println("초기 자금: 10,000원");
    }

    private void displayStatus() {
        System.out.println("=== " + city.getName() + " 현황" + " ( " + turn + " 턴)" + " ===");
        System.out.printf("현재 자금: %d원\n", city.getMoney());
        System.out.printf("현재 인구: %d명\n", city.getPopulation());
        System.out.printf("현재 행복도: %d\n", city.getHappiness());
        System.out.printf("현재 고용: %d명\n", city.getEmployment());
        System.out.printf("현재 오염도: %d\n", city.getPollution());

        if (city.getMoney() < 1000 ) {
            System.out.println("자금 관리가 필요합니다.");
        }
        if (city.getHappiness() < 20 ) {
            System.out.println("시민들의 원성이 자자합니다.");
        }
        if (city.getPollution() > 50 ) {
            System.out.println("도시가 더럽습니다.");
        }


    }

    private boolean processCommand() {
        System.out.println("\n=== 행동 선택 ===");
        System.out.println("1. 주거용 건물 건설");
        System.out.println("2. 상업용 건물 건설");
        System.out.println("3. 공업용 건물 건설");
        System.out.println("4. 건물 목록 보기");
        System.out.println("5. 도시 상세 정보");
        System.out.println("6. 턴 종료");
        System.out.println("7. 게임 종료");

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


    private void displayBuildings() {
        System.out.println("\n=== 건물 목록 ===");
        int totalMaintenance = 0;

        // 주거용 건물
        System.out.println("\n[ 주거용 건물 ]");
        for (int i = 0; i < city.getResidentialBuildings().size(); i++) {
            Building b = city.getResidentialBuildings().get(i);
            System.out.printf("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost());
            totalMaintenance += b.getMaintenanceCost();
        }

        // 상업용 건물
        System.out.println("\n[ 상업용 건물 ]");
        for (int i = 0; i < city.getCommercialBuildings().size(); i++) {
            Building b = city.getCommercialBuildings().get(i);
            System.out.printf("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost());
            totalMaintenance += b.getMaintenanceCost();
        }

        // 공업용 건물
        System.out.println("\n[ 공업용 건물 ]");
        for (int i = 0; i < city.getIndustrialBuildings().size(); i++) {
            Building b = city.getIndustrialBuildings().get(i);
            System.out.printf("%d. %s (유지비: %,d원/턴)\n",
                    i + 1, b.getName(), b.getMaintenanceCost());
            totalMaintenance += b.getMaintenanceCost();
        }

        System.out.printf("\n총 유지비: %,d원/턴\n", totalMaintenance);
    }

    private void displayDetails() {
        System.out.println("\n=== 도시 상세 정보 ===");
        System.out.printf("도시 이름: %s\n", city.getName());
        System.out.printf("현재 턴: %d\n", turn);
        System.out.printf("자금: %,d원\n", city.getMoney());
        System.out.printf("인구: %,d명\n", city.getPopulation());
        System.out.printf("행복도: %d%%\n", city.getHappiness());
        System.out.printf("고용: %,d명\n", city.getEmployment());
        System.out.printf("공해도: %d\n", city.getPollution());

        // 건물 통계
        int totalBuildings = city.getResidentialBuildings().size() +
                city.getCommercialBuildings().size() + city.getIndustrialBuildings().size();
        System.out.printf("\n총 건물 수: %d개\n", totalBuildings);
        System.out.printf("- 주거용 건물: %d개\n", city.getResidentialBuildings().size());
        System.out.printf("- 상업용 건물: %d개\n", city.getCommercialBuildings().size());
        System.out.printf("- 공업용 건물: %d개\n", city.getIndustrialBuildings().size());
    }

    private void processTurnEnd() {
        // 세금 수입
        city.collectTaxes();

        // 유지비 지출
        city.maintenanceCost();

        // 인구 자연 증가
        int growth = (int)(city.getPopulation() * 0.05);
        if (growth > 0) {
            city.addPopulation(growth);
            System.out.printf("\n인구가 %d명 증가했습니다.\n", growth);
        }

        // 랜덤 이벤트
        processRandomEvent();
    }

    private void processRandomEvent() {
        if (random.nextInt(100) < 10) {
            int eventType = random.nextInt(6);
            switch (eventType) {
                case 0:
                    System.out.println("\n[이벤트] 축제가 열렸습니다! 행복도가 증가합니다.");
                    city.addHappiness(5);
                    break;
                case 1:
                    System.out.println("\n[이벤트] 환경 캠페인으로 공해가 감소했습니다!");
                    city.addPollution(-5);
                    break;
                case 2:
                    int bonus = random.nextInt(1000) + 500;
                    System.out.printf("\n[이벤트] 국가 보조금 %,d원을 받았습니다!\n", bonus);
                    city.addMoney(bonus);
                    break;
                case 3:
                    System.out.println("\n[이벤트] 자연재해가 발생했습니다! 행복도와 자금이 감소합니다.");
                    city.addHappiness(-10);
                    city.addMoney(-3000);
                    break;
                case 4:
                    System.out.println("\n[이벤트] 기업 투자 유치! 고용이 증가합니다.");
                    city.addEmployment(50);
                    city.addMoney(2000);
                    break;
                case 5:
                    System.out.println("\n[이벤트] 경제 불황! 세수가 감소합니다.");
                    city.addMoney(-2500);
                    city.addEmployment(-20);
                    break;
            }
        }
    }

    private void displayFinalStatus() {
        System.out.println("\n================================");
        System.out.println("        게임 종료 통계          ");
        System.out.println("================================");
        System.out.printf("도시 이름: %s\n", city.getName());
        System.out.printf("생존 턴 수: %d\n", turn);
        System.out.printf("최종 인구: %,d명\n", city.getPopulation());
        System.out.printf("최종 자금: %,d원\n", city.getMoney());
        System.out.printf("최종 행복도: %d%%\n", city.getHappiness());
        System.out.printf("최종 고용: %,d명\n", city.getEmployment());
        System.out.printf("건설한 건물 수: %d개\n",
                city.getResidentialBuildings().size() + city.getCommercialBuildings().size());
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
                System.out.println("\n게임 오버!");
                running = false;
            }
        }

        displayFinalStatus();
    }
}

