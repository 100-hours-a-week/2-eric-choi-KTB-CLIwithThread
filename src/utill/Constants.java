package utill;

public class Constants {
    private Constants() {};

    // 초기 설정 관련 상수
    public static final int INITIAL_MONEY = 10000;
    public static final int INITIAL_POPULATION = 10;
    public static final int INITIAL_HAPPINESS = 50;

    // 건물 비용 관련 상수
    public static final int SHOPPING_MALL_COST = 3000;
    public static final int OFFICE_BUILDING_COST = 5000;
    public static final int FACTORY_COST = 4000;
    public static final int RECYCLE_BUILDING_COST = 5000;
    public static final int APARTMENT_BASE_COST = 1000;
    public static final int TOWNHOUSE_BASE_COST = 1000;
    public static final int TOWNHOUSE_GARDEN_COST = 500;

    // 유지비 관련 상수
    public static final int RESIDENTIAL_MAINTENANCE = 50;
    public static final int COMMERCIAL_MAINTENANCE = 50;
    public static final int INDUSTRIAL_MAINTENANCE = 100;

    // 게임 규칙 관련 상수
    public static final int MAX_POLLUTION = 100;
    public static final int MAX_HAPPINESS = 100;
    public static final int BANKRUPTCY_Trigger = -5000;
    public static final int GAME_OVER_POLLUTION = 100;

    // 이벤트 관련 상수
    public static final int EVENT_TRIGGER_CHANCE = 10; // 10%
    public static final int FESTIVAL_HAPPINESS = 5;
    public static final int ENVIRONMENT_CAMPAIGN = -5;
    public static final int DISASTER_HAPPINESS_PENALTY = -10;
    public static final int DISASTER_MONEY_PENALTY = -3000;
    public static final int CORPORATE_INVESTMENT_EMPLOYMENT = 50;
    public static final int CORPORATE_INVESTMENT_MONEY = 2000;
    public static final int ECONOMIC_RECESSION_MONEY = -2500;
    public static final int ECONOMIC_RECESSION_EMPLOYMENT = -20;
}
