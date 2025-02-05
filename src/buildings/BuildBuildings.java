package buildings;

import buildings.residential.*;
import buildings.commercial.*;
import buildings.industrial.*;
import game.InputManager;
import game.City;
import utill.Constants;

public class BuildBuildings {
    private final City city;

    public BuildBuildings(City city) {
        this.city = city;
    }

    public void buildResidential() {
        System.out.println("\n=== 주거용 건물 건설 ===");
        System.out.println("1. 아파트 (비용: 층수 × 1,000원)");
        System.out.println("2. 타운하우스 (기본 1,000원, 정원 추가 500원)");
        System.out.println("3. 건설 취소");

        ResidentialBuilding building = null;
        int choice = InputManager.getIntInputInRange("건물 유형을 선택하세요: ", 1, 3);

        switch (choice) {
            case 1:
                int floors = InputManager.getIntInputInRange("층수를 입력하세요 (1~30층): ", 1, 30);
                building = new Apartment("아파트", floors * Constants.APARTMENT_BASE_COST, floors);
                break;

            case 2:
                int gardenChoice = InputManager.getIntInputInRange("정원을 포함하겠습니까? (1: 예, 0: 아니오): ", 0, 1);
                boolean garden = gardenChoice == 1;
                building = new Townhouse("타운하우스", garden ? Constants.TOWNHOUSE_BASE_COST + Constants.TOWNHOUSE_GARDEN_COST : Constants.TOWNHOUSE_BASE_COST, garden);
                break;

            case 3:
                return;
        }

        constructBuilding(building, BuildingType.RESIDENTIAL);
    }

    public void buildCommercial() {
        System.out.println("\n=== 상업용 건물 건설 ===");
        System.out.println("1. 쇼핑몰 (비용: 3,000원)");
        System.out.println("2. 오피스 빌딩 (비용: 5,000원)");
        System.out.println("3. 건설 취소");

        CommercialBuilding building = null;
        int choice = InputManager.getIntInputInRange("건물 유형을 선택하세요: ", 1, 3);

        switch (choice) {
            case 1:
                int visitors = InputManager.getVisitorInput("예상 일일 방문객 수를 입력하세요: ", city.getPopulation());
                building = new Shoppingmall("쇼핑몰", Constants.SHOPPING_MALL_COST, visitors);
                break;

            case 2:
                int companies = InputManager.getIntInputInRange("입주 예정 회사 수를 입력하세요 (1~10): ", 1, 10);
                building = new OfficeBuilding("오피스빌딩", Constants.OFFICE_BUILDING_COST, companies);
                break;

            case 3:
                return;
        }

        constructBuilding(building, BuildingType.COMMERCIAL);
    }

    public void buildIndustrial() {
        System.out.println("\n=== 공업용 건물 건설 ===");
        System.out.println("1. 공장 (비용: 4,000원)");
        System.out.println("2. 재활용처리장 (비용: 5,000원)");
        System.out.println("3. 건설 취소");

        IndustrialBuilding building = null;
        int choice = InputManager.getIntInputInRange("건물 유형을 선택하세요: ", 1, 3);

        switch (choice) {
            case 1:
                int size = InputManager.getFactorySizeInput("공장 크기를 입력하세요(크기와 같은 값으로 오염도가 증가합니다): ", city.getPollution());
                building = new Factory("공장", Constants.FACTORY_COST, size);
                break;

            case 2:
                building = new RecycleBuilding("재활용처리장", Constants.RECYCLE_BUILDING_COST);
                break;

            case 3:
                return;
        }

        constructBuilding(building, BuildingType.INDUSTRIAL);
    }

    private void constructBuilding(Building building, BuildingType type) {
        if (building != null) {
            boolean success = false;
            switch (type) {
                case RESIDENTIAL:
                    success = city.buildResidential((ResidentialBuilding) building);
                    break;
                case COMMERCIAL:
                    success = city.buildCommercial((CommercialBuilding) building);
                    break;
                case INDUSTRIAL:
                    success = city.buildIndustrial((IndustrialBuilding) building);
                    break;
            }

            if (success) {
                System.out.println(building.getName() + " 건설 완료!");
            } else {
                System.out.println("자금 부족으로 건설 불가!");
            }
        }
    }

    private enum BuildingType {
        RESIDENTIAL, COMMERCIAL, INDUSTRIAL
    }
}