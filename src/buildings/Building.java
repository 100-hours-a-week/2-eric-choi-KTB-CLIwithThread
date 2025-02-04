package buildings;

import game.City;

public abstract class Building {
    protected String name;
    protected int cost;
    protected int maintenanceCost;

    public Building(String name, int cost, int maintenanceCost) {
        this.name = name;
        this.cost = cost;
        this.maintenanceCost = maintenanceCost;
    }
    //메서드들
    public int calculateRevenue(){
        return 0;
    };
    public void calculateEffect(City city){

    };

    public int getCapacity() {
        return 0;
    }

    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public int getMaintenanceCost() {
        return maintenanceCost;
    }
}
