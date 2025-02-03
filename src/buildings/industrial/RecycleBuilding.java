package buildings.industrial;

import game.City;

public class RecycleBuilding extends IndustrialBuilding{

    public RecycleBuilding(String name, int cost){
        super(name, cost);
        this.employees = 30;
        this.pollution = -10;
    }

    @Override
    public void calculateEffect(City city) {
        city.addPollution(pollution);
        city.addEmployment(employees);
    }
}
