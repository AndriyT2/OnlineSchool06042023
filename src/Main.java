import models.Course;
import models.Lecture;
import models.Teacher;

public class Main {
    public static void main(String[] args) {
        Lecture lecture1 = new Lecture();
        lecture1.printId();
        Lecture lecture2 = new Lecture();
        lecture2.printId();
        Lecture lecture3 = new Lecture();
        lecture1.printId();

        System.out.println("Лічильник прийме знаачення = " + Lecture.counter);
    }
}