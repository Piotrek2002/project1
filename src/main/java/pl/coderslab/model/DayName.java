package pl.coderslab.model;

public class DayName {
    private int id;
    private String name;
    int displayOrder;

    @Override
    public String toString(){
        return "DayName [id= " + id + ", name " + name + ", order " + displayOrder + "]";
    }

    // constructors
    public DayName(){
    }

    public DayName(String name, int displayOrder){
        this.name = name;
        this.displayOrder = displayOrder;
    }

    // methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
