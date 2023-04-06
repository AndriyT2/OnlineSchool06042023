import models.ResourceType;
import models.Role;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import service.*;

import java.io.IOException;
@Configuration
@ComponentScan
public class Main11 {


    public static void main(String[] args) throws IOException {


        CourseService courseH18T1 = new CourseService();
        courseH18T1.createCourse("CCCCCCCCC");
        CourseService courseH18T2 = new CourseService();
        courseH18T2.createCourse("AAAAAAAA");
        CourseService courseH18T3 = new CourseService();
        courseH18T3.createCourse("BBBBBBBBB");


        PersonService personH18T1 = new PersonService();
        personH18T1.addPerson("CCCCCCCC", "BBBBBB", Role.TEACHER);
        PersonService personH18T2 = new PersonService();
        personH18T2.addPerson("AAAAAAA", "BBBBB", Role.TEACHER);
        PersonService personH18T3 = new PersonService();
        personH18T3.addPerson("AAAA", "aaaaaa", Role.TEACHER);
        PersonService personH18T4 = new PersonService();
        personH18T4.addPerson("BBBBBBBBB", "BBBBBB", Role.TEACHER);
        personH18T4.addPerson("nnnnnnnn", "mmmmmmmm", Role.STUDENT);


        AdditionalMaterialsService amH18T1 = new AdditionalMaterialsService();
        amH18T1.createAdditionalMaterialsService("CCCCCCC", 12, ResourceType.VIDEO);
        AdditionalMaterialsService amH18T2 = new AdditionalMaterialsService();
        amH18T2.createAdditionalMaterialsService("AAAAAAA", 13, ResourceType.URL);
        AdditionalMaterialsService amH18T3 = new AdditionalMaterialsService();
        amH18T3.createAdditionalMaterialsService("BBBBBBB", 14, ResourceType.URL);
        AdditionalMaterialsService amH18T4 = new AdditionalMaterialsService();
        amH18T4.createAdditionalMaterialsService("AAAAAAA", 12, ResourceType.BOOK);


        LectureService lectureH19T1 = new LectureService();
        lectureH19T1.createLecture(1);
        lectureH19T1.createLecture(2);
        lectureH19T1.createLecture(3);
        lectureH19T1.createLectureWithTeacher(4);
        lectureH19T1.createLectureWithTeacher(5);
        lectureH19T1.createLectureWithTeacher(6);
        lectureH19T1.createLectureWithTeacher(7);


        HomeworkService homeworkH19T1 = new HomeworkService();
        homeworkH19T1.createHomeworkWithLectureId(15);
        homeworkH19T1.createHomeworkWithLectureId(13);
        homeworkH19T1.createHomeworkWithLectureId(14);

        personH18T1.addPerson("ffffffff", "hhhhhhhh", Role.STUDENT);


        System.out.println("\n=====================  Menu  ===========================================================\n");



        Menu menu = new Menu();
        menu.menu();


    }

}