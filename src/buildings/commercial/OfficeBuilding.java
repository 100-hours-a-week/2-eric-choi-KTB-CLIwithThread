package buildings.commercial;

public class OfficeBuilding extends CommercialBuilding{
    private int companies;

    public OfficeBuilding (String name, int cost, int companies){
        super(name, cost);
        this.companies = companies;
        this.revenue = companies * 100;
        this.employees = companies * 10;
    }
}
