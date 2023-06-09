package models;

import java.io.Serializable;

public abstract class ModelsSuper implements Serializable {

    private static int counter;

    private final Integer id;

    public <E> ModelsSuper() {
        ++counter;
        this.id = counter;
    }

    public static int getCounter() {
        return counter;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "id=" + id +
                '}';
    }
}
