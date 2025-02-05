package buildings.industrial;

import buildings.Building;
import game.City;
import utill.Constants;

public abstract class IndustrialBuilding extends Building {
    protected int pollution;
    protected int employees;


    public IndustrialBuilding(String name, int cost) {
        super(name, cost, Constants.INDUSTRIAL_MAINTENANCE);
        this.pollution = 10;
        this.employees = 50;
    }

    @Override
    public int calculateRevenue() {
        return employees * 10 ;
    }

    @Override
    public void calculateEffect(City city) {
        city.addPollution(10);
        city.addEmployment(employees);

        if (city.getPollution() > 50) {
            city.addHappiness(-10);
        }
    }

}
