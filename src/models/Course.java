package models;

public class Course {
    static int counter;
    private int id;

    public void printId() {

        ++counter;
        id = counter;
        System.out.println(id);
    }
}
