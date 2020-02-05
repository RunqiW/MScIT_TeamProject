package model;

public class Card {
    String name;
    Description description;
    private int[] values;
    public Card(String name, Description description, int[] values){
        this.name = name;
        this.description = description;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public void print(){
        System.out.println(name);
    }
}
