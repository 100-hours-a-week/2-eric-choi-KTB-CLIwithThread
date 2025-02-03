package buildings.residential;
import game.City;

public class Apartment extends ResidentialBuilding{
    private int floors;

    public Apartment(String name, int cost, int floors){
        super(name, cost);
        this.floors = floors;
        this.capacity = floors * 10;
    }

    @Override
    public int calculateRevenue() {
        return capacity * 5;
    }

    @Override
    public void calculateEffect(City city) {
        city.addPopulation(capacity);
        city.addHappiness(happiness - (floors > 20 ? 10 : 0)); // 층수가 높으면 행복도 감소
    }
}
