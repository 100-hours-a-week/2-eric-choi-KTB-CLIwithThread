package buildings.commercial;

import buildings.Building;
import game.City;
import utill.Constants;

public abstract class CommercialBuilding extends Building {
    protected int revenue;
    protected int employees;

    public CommercialBuilding(String name, int cost) {
        super(name, cost, Constants.COMMERCIAL_MAINTENANCE);
        this.revenue = 100;
    }

    @Override
    public int calculateRevenue() {
        return revenue + (employees * 10) ;
    }

    @Override
    public void calculateEffect(City city) {
        city.addHappiness(5);
        city.addEmployment(employees);
    }

}
