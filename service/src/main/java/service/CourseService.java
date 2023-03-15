package service;

import models.Course;
import models.Lecture;
import models.Person;
import models.Role;
import repository.CourseRepository;
import repository.LectureRepository;
import repository.PersonRepository;
import utility.ScannerThis;
import utility.utilityLog.LogFactory;

import java.io.Serializable;


public class CourseService implements Serializable {
    public Course createCourse() {
        return new Course();
    }

    public Course createCourse(String courseName) {
       Course course = new Course(courseName);
     CourseRepository.getInstance().getCourseList().add(course);
     int index = CourseRepository.getInstance().getCourseList().indexOf(course);
        LogFactory.debug(this.getClass().getName(),"Create course with name");
        return CourseRepository.getInstance().getCourseList().get(index);
    }

    public void createCourse(String courseName, String teacher, String student, String lecture) {
        Course course = new Course(courseName);
        int  courseId = course.getId();
        CourseRepository.getInstance().getCourseList().add(course);
        if(teacher != null) {
            PersonRepository.getInstance().getPersonList().add(new Person(teacher, Role.TEACHER, courseId));
        }
        if (student != null) {
            PersonRepository.getInstance().getPersonList().add(new Person(student, Role.STUDENT, courseId));
        }
        if (lecture != null) {
            LectureRepository.getInstance().getLecturesList().add(new Lecture(lecture, courseId));
        }

    }



    public void courseScanner(String courseName) {
        String answer = askCourseScanner(courseName);

        if (answer.contains(String.valueOf('#'))) {
            System.out.println(answer);
            int pointOfFirstDelimiter = answer.indexOf('#');
            int pointOfSecondDelimiter = answer.lastIndexOf('#');


            String courseName1 = answer.substring(0, pointOfFirstDelimiter);
            System.out.println(courseName1);
            String courseParameterNumber1 = answer.substring(pointOfFirstDelimiter + 1, pointOfSecondDelimiter);
            System.out.println(courseParameterNumber1);
            String courseParameter1 = answer.substring(pointOfSecondDelimiter + 1);
            System.out.println(courseParameter1);


            System.out.println("Ви створили курс:" + courseName1);

            if (courseParameterNumber1.equals("1")) {
                System.out.println(courseParameter1);
                 createCourse(courseName1, courseParameter1, null, null);

            } else if (courseParameterNumber1.equals("2")) {
                 createCourse(courseName1, null, courseParameter1, null);
            } else {
                 createCourse(courseName1, null, null, courseParameter1);
            }

        } else {

            System.out.println("Ви створили курс:" + answer);
            createCourse(answer);
        }
    }

    private String askCourseScanner(String courseName) {


        System.out.println("Ви впевнені, що хочете назвати курсу наступним чином: " + courseName + " ?");
        System.out.println("Якщо назва правильна, то введіть \"Так\" або \"Yes\", " +
                "в противному випадку введіть \"Ні\" або \"No\"!");

        String courseNameAnswer = ScannerThis.getInstance().nextLine();

        if (courseNameAnswer.equals("Ні") | courseNameAnswer.equals("ні") |
                courseNameAnswer.equals("No") | courseNameAnswer.equals("no")) {

            System.out.println("Введіть нову назву курсу:");
            courseName = ScannerThis.getInstance().nextLine();

        } else if (courseNameAnswer.equals("Так") | courseNameAnswer.equals("так") |
                courseNameAnswer.equals("Yes") | courseNameAnswer.equals("yes")) {

//            Course name stay without changes (courseName = courseName;)

        } else {
            throw new RuntimeException("Ви ввели некоректну відповідь. Почніть з самого початку!");
        }

        System.out.println("Чи бажаєте додати додаткові параметри для курсу?");
        System.out.println("Якщо бажаєте, то введіть \"Так\" або \"Yes\", " +
                "в противному випадку введіть \"Ні\" або \"No\"!");

        String courseParametersAnswer = ScannerThis.getInstance().nextLine();

        if (courseParametersAnswer.equals("Ні") | courseParametersAnswer.equals("ні") |
                courseParametersAnswer.equals("No") | courseParametersAnswer.equals("no")) {

            return courseName;

        } else if (courseParametersAnswer.equals("Так") | courseParametersAnswer.equals("так") |
                courseParametersAnswer.equals("Yes") | courseParametersAnswer.equals("yes")) {

            System.out.println("""
                    Введіть порядковий номер одного з наступних додаткових параметрів, що бажаєте додати:
                    1 Вчитель;
                    2 Студент;
                    3 Лекція.""");

            int courseParameterNumber = ScannerThis.getInstance().nextInt();
            ScannerThis.getInstance().nextLine();

            System.out.println("Тепер введіть значення для вибраного параметру:");
            String courseParameterValue = ScannerThis.getInstance().nextLine();

            String courseParameter = switch (courseParameterNumber) {
                case 1:
                    System.out.println("Ви вибрали параметр: вчитель. " +
                            "\nЗначення для цього параметру:" + courseParameterValue);
                    yield courseParameterValue;
                case 2:
                    System.out.println("Ви вибрали параметр: студент." +
                            "\nЗначення для цього параметру:" + courseParameterValue);
                    yield courseParameterValue;
                case 3:
                    System.out.println("Ви вибрали параметр: лекція." +
                            "\nЗначення для цього параметру:" + courseParameterValue);
                    yield courseParameterValue;
                default:
                    yield "Ви ввели некоректну відповідь. Почніть з самого спочатку!";
            };

            return courseName + "#" + courseParameterNumber + "#" + courseParameter;

        } else {
            throw new IllegalArgumentException();
        }
    }




}



