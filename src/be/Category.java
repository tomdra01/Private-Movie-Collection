package be;

public class Category {
    private int id;
    private String name;

    //Constructor
    public Category(String name) {
        this.name = name;
    }

    //Constructor
    public Category(int id, String name) {
        this(name);
        this.id = id;
    }

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

    @Override
    public String toString() {
        return name;
    }
}