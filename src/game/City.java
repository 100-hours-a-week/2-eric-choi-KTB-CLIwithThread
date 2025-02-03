package game;
import java.util.ArrayList;
import java.util.List;

import buildings.Building;
import buildings.residential.ResidentialBuilding;
import buildings.commercial.CommercialBuilding;
import buildings.industrial.IndustrialBuilding;

public class City {
    private String name;
    private int money;
    private int population;
    private int happiness;
    private int pollution;
    private int employment;

    private List<ResidentialBuilding> residentialBuildings;
    private List<CommercialBuilding> commercialBuildings;
    private List<IndustrialBuilding> industrialBuildings;

    public City(String name, int initialMoney) {
        this.name = name;
        this.money = initialMoney;
        this.population = 10;
        this.happiness = 50;
        this.pollution = 0;
        this.employment = 0;


        this.residentialBuildings = new ArrayList<>();
        this.commercialBuildings = new ArrayList<>();
        this.industrialBuildings = new ArrayList<>();
    }

    //메소드들
    public String getName() {
        return name;
    }
    public int getMoney() {
        return money;
    }
    public int getPopulation() {
        return population;
    }
    public int getHappiness() {
        return happiness;
    }
    public int getPollution() {
        return pollution;
    }
    public int getEmployment() {
        return employment;
    }
    public List<ResidentialBuilding> getResidentialBuildings() {
        return residentialBuildings;
    }
    public List<CommercialBuilding> getCommercialBuildings() {
        return commercialBuildings;
    }
    public List<IndustrialBuilding> getIndustrialBuildings() {
        return industrialBuildings;
    }

    public void addPopulation(int amount) {
        this.population += Math.max(0, this.population + amount);
    }
    public void addPollution(int amount) {
        this.pollution += Math.max(0, this.pollution + amount);;
    }
    public void addHappiness(int amount) {
        this.happiness += Math.max(0, Math.min(100, this.happiness + amount));;
    }
    public void addEmployment(int amount) {
        this.employment += Math.max(0, this.employment + amount);;
    }
    public void addMoney(int amount) {
        this.money += amount;
    }

    public boolean buildResidential(ResidentialBuilding building) {
        if (money >= building.getCost()) {
            residentialBuildings.add(building);
            money -= building.getCost();
            building.calculateEffect(this);
            updateCityStatus();
            return true;
        }
        return false;
    }

    public boolean buildCommercial(CommercialBuilding building) {
        if (money >= building.getCost()) {
            commercialBuildings.add(building);
            money -= building.getCost();
            building.calculateEffect(this);
            updateCityStatus();
            return true;
        }
        return false;
    }

    public boolean buildIndustrial(IndustrialBuilding building) {
        if (money >= building.getCost()) {
            industrialBuildings.add(building);
            money -= building.getCost();
            building.calculateEffect(this);
            updateCityStatus();
            return true;
        }
        return false;
    }


    public void collectTaxes() {
        int residentialRevenue = residentialBuildings.stream()
                .mapToInt(Building::calculateRevenue)
                .sum();
        int commercialRevenue = commercialBuildings.stream()
                .mapToInt(Building::calculateRevenue)
                .sum();
        int industrialRevenue = industrialBuildings.stream()
                .mapToInt(Building::calculateRevenue)
                .sum();

        money += (residentialRevenue + commercialRevenue + industrialRevenue);
    }

    public void maintenanceCost() {
        int residentialCost = residentialBuildings.stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();
        int commercialCost = commercialBuildings.stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();
        int industrialCost = industrialBuildings.stream()
                .mapToInt(Building::getMaintenanceCost)
                .sum();
        money -= (residentialCost+commercialCost+industrialCost);

        if(money< 0) {
            happiness -= 10;
        }
    }

    private void updateCityStatus() {
        int totalCapacity = residentialBuildings.stream()
                .mapToInt(Building::getCapacity)
                .sum();

        if(population > totalCapacity) {
            happiness -= 10;
        }

        int employmentRate = population > 0 ? (employment * 100) / population : 0;

        if (employmentRate < 50) {
            happiness -= 5;
        } else if (employmentRate > 80) {
            happiness += 5;
        }

        happiness = Math.max(0, Math.min(100, happiness));
    }

    public boolean isGameOver() {
        if (happiness <= 0 || money < -5000) return true;

        if (pollution >= 100) {
            System.out.println("환경 파괴로 인한 게임 오버!");
            return true;
        }

        if (population <= 0) {
            System.out.println("인구 부족으로 인한 게임 오버!");
            return true;
        }

        return false;
    }


}
