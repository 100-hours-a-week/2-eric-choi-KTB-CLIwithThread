package buildings.industrial;

import game.City;

public class Factory extends IndustrialBuilding{
    private final int size;

    public Factory(String name, int cost, int size){
        super(name, cost);
        this.size = size;
        this.employees = 10 * size;
        this.pollution = size ;
    }

    @Override
    public void calculateEffect(City city) {
        city.addPollution(pollution);
        city.addEmployment(employees);
    }

}
