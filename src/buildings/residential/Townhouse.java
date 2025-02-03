package buildings.residential;
import game.City;

public class Townhouse extends ResidentialBuilding{
    private boolean garden;

    public Townhouse (String name, int cost, boolean garden) {
        super(name, cost);
        this.garden = garden;
        this.capacity = garden ? 10 : 5;
    }

    @Override
    public void calculateEffect(City city){
        city.addPopulation(capacity);
        city.addHappiness(happiness + (garden ? 5 : 0));
    }
}
