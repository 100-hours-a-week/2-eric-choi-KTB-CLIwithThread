package buildings.commercial;

import game.City;

public class Shoppingmall extends CommercialBuilding{
    private int visitors;

    public Shoppingmall(String name, int cost, int visitors){
        super(name,cost);
        this.visitors = visitors;
        this.revenue = visitors * 10;
        this.employees = 50;
    }

    @Override
    public void calculateEffect(City city){
        city.addHappiness(10);
        city.addEmployment(employees);
    }
}
