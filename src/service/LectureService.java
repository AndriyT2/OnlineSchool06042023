package service;

import models.Course;
import models.Lecture;
import models.Person;
import models.Role;
import repository.*;
import utility.ScannerThis;
import utility.utilityLog.LogFactory;

public class LectureService {



    public void createLecture() {
        LectureRepository.getInstance().getLecturesList().add(new Lecture());
        LogFactory.debug(this.getClass().getName(),"Create new lecture");

    }


    public Lecture createLecture(int idCourse) {
        return new Lecture();

    }

    public Lecture createLectureWithTeacher(int idPerson) {
        return new Lecture().lectureWithTeacher(idPerson);

    }

    public Lecture createLecture(int idCourse, int idTeacher) {
        return new Lecture(idCourse, idTeacher);

    }
    public void lectureScanner(int idCourse) {
        int totalLectures = 0;
        System.out.println("Чи бажаєте створити нову лекцію? [Так/Ні]");

        String lectureScannerAsk1 = ScannerThis.getInstance().nextLine();

        if (lectureScannerAsk1.equals("Ні") | lectureScannerAsk1.equals("ні") | lectureScannerAsk1.equals("No") | lectureScannerAsk1.equals("no")) {

            System.out.println("Ви відмовилися створювати нову лекцію!");

        } else if (lectureScannerAsk1.equals("Так") | lectureScannerAsk1.equals("так") | lectureScannerAsk1.equals("Yes") | lectureScannerAsk1.equals("yes")) {

            Lecture lecture1H7 = new Lecture(idCourse);
            String lecture1H7Id = String.valueOf(lecture1H7.getIdCourse());
            totalLectures++;

            System.out.println("Створена нова лекція. Значення Id курсу для даної лекції становить: " + lecture1H7Id + ".");

            System.out.println("Чи бажаєте створити додаткову лекцію? [Так/Ні]");
            String lectureScannerAsk2 = ScannerThis.getInstance().nextLine();

            if (lectureScannerAsk2.equals("Ні") | lectureScannerAsk2.equals("ні") | lectureScannerAsk2.equals("No") | lectureScannerAsk2.equals("no")) {

                System.out.println("Ви відмовилися створювати додаткову лекцію!");

            } else if (lectureScannerAsk2.equals("Так") | lectureScannerAsk2.equals("так") | lectureScannerAsk2.equals("Yes") | lectureScannerAsk2.equals("yes")) {

                Lecture lecture2H7 = new Lecture(idCourse);
                int lecture2H7Id = lecture2H7.getIdCourse();
                totalLectures++;

                System.out.println("Створена додаткова лекція. Значення Id курсу для даної лекції становить: " + lecture2H7Id + ".");
            } else {

                System.out.println("Ви ввели некоректну відповідь. Почніть з самого початку!");
            }

        } else {
            throw new IllegalArgumentException();

        }

        System.out.println("Загальна кількість створених лекцій складає - " + totalLectures + " штук(и).");
    }

    public void lectureScannerLoop() {

        int totalLecturesLoop = 0;
        int IdCourseLoop = -1;
        int maxValueLectures = 8;
        int maxValueIdCourse = 3;
        int minValueIdCourse = 1;

        while (true){

            System.out.println("Чи бажаєте створити нову лекцію? [Y/N]");
            String lectureScannerLoopAsk1 = ScannerThis.getInstance().nextLine();

            if (lectureScannerLoopAsk1.equalsIgnoreCase("n")) {
                System.out.println("Ви відмовилися створювати нову лекцію!");
                break;

            } else if (lectureScannerLoopAsk1.equalsIgnoreCase("y")) {

                do {
                    System.out.println("Введіть значення Id курсу для майбутньої лекції тільки в наступних межах від " + minValueIdCourse + " до " + maxValueIdCourse + "!");
                    IdCourseLoop = ScannerThis.getInstance().nextInt();
                    ScannerThis.getInstance().nextLine();

                } while (IdCourseLoop < minValueIdCourse || IdCourseLoop > maxValueIdCourse);

                totalLecturesLoop++;

                if (totalLecturesLoop > maxValueLectures) {

                    System.out.println("Ви перевищили ліміт на максимальну кількість створених лекцій, який становить - " + maxValueLectures + " штук!");
                    break;
                }

                System.out.println("Ви створили " + totalLecturesLoop +"-у лекцію з Id курсу = " + IdCourseLoop);

            } else {
                throw new IllegalArgumentException();
            }

        }

    }

    public void lecturesArrayShove(Lecture[] lectures) {
        for (Lecture lecture : lectures) {
            if (lecture == null) continue;
            System.out.println("Значення ID для лекції " + lecture + " буде приймати значення " + lecture.getId());
        }
    }

    public void lecturesArrayCreator () {

        CourseRepository courseRepository2 = CourseRepository.getInstance();
        System.out.println(CourseRepository.getInstance().getCourseList());

        LectureRepository lectureRepository2 = LectureRepository.getInstance();
        System.out.println(lectureRepository2.getLecturesList());

        CourseService course = new CourseService();
        Course course1H9 = course.createCourse();
        courseRepository2.getCourseList().add(course1H9);
        courseRepository2.getAll();

        int idCourse1H9 = course1H9.getId();
        System.out.println("Значення ID для курсу - " + idCourse1H9);

        Lecture lecture1H9 = createLecture(idCourse1H9);

        Lecture lecture2H9 = createLecture(idCourse1H9);

        lectureRepository2.getLecturesList().add(lecture1H9);
        lectureRepository2.getAll();

        lectureRepository2.getLecturesList().add(lecture2H9);
        lectureRepository2.getAll();

        while (true) {

                System.out.println("Чи бажаєте створити новий елемент? [Y/N]");
                String modelSuperAsk2 = ScannerThis.getInstance().nextLine();

                if (modelSuperAsk2.equalsIgnoreCase("n")) {
                    System.out.println("Ви відмовилися створювати новий елемент!");
                    break;

                } else if (modelSuperAsk2.equalsIgnoreCase("y")) {

                    lectureRepository2.getLecturesList().add(new Lecture(idCourse1H9));
                    lectureRepository2.getAll();
                } else {
                    throw new IllegalArgumentException();
                }

            }
        lectureRepository2.getAll();
    }

    public void lecturesCreatorWithTeacher () {


        while (true) {

            System.out.println("Чи бажаєте створити нову лекцію? [Y/N]");
            String modelSuperAsk2 = ScannerThis.getInstance().nextLine();

            if (modelSuperAsk2.equalsIgnoreCase("n")) {
                System.out.println("Ви відмовилися створювати нову лекцію!");
                break;

            } else if (modelSuperAsk2.equalsIgnoreCase("y")) {

                    System.out.println("Чи бажаєте додати вчителя з наступного списку? [Y/N]");

                    int maxTeacherId = 0;
                    int totalTeacher = 0;
                    for (Person person: PersonRepository.getInstance().getPersonList()) {
                        if (person == null) { break;
                        } else if (person.getRole().equals(Role.TEACHER)) {
                            ++totalTeacher;
                            System.out.print(person);
                            System.out.println(" - маэ значкння ID = " + person.getId());
                            if (maxTeacherId < person.getId()) {maxTeacherId = person.getId();}
                        }
                    }

                if (totalTeacher == 0) {
                    System.out.println("Жодний вчитель ще не був доданий до списку!\nВставити код з пропозицією створення нового вчителя чи лекції без вчителя!");
                }

                    String modelSuperAsk3 = ScannerThis.getInstance().nextLine();

                    if (modelSuperAsk3.equalsIgnoreCase("n")) {

                        System.out.println("Чи бажаєте додати нового вчителя? [Y/N]");

                        String modelSuperAsk4 = ScannerThis.getInstance().nextLine();

                        if (modelSuperAsk4.equalsIgnoreCase("n")) {
                            System.out.println("Ви створили лекцію без вчителя!");
                            LectureRepository.getInstance().getAll();
                            LectureRepository.getInstance().getLecturesList().add(new Lecture());
                            LectureRepository.getInstance().getAll();

                        } else if (modelSuperAsk4.equalsIgnoreCase("y")) {
                            System.out.println("Створена лекція з новим вчителем!");
                            PersonRepository.getInstance().getAll();
                            Person teacherH11 = new Person(Role.TEACHER);
                            PersonRepository.getInstance().getPersonList().add(teacherH11);
                            PersonRepository.getInstance().getAll();
                            LectureRepository.getInstance().getAll();
                            Lecture lectureH11 = new LectureService().createLectureWithTeacher(teacherH11.getId());
                            LectureRepository.getInstance().getLecturesList().add(lectureH11);
                            LectureRepository.getInstance().getAll();




                        } else {
                            throw new RuntimeException();
                        }


                     } else if (modelSuperAsk3.equalsIgnoreCase("y")) {

                       int ask5;
                        do {
                            System.out.println("Введіть значення ID з представленного списку для вибраного вчителя!");
                            int modelSuperAsk5 = ScannerThis.getInstance().nextInt();
                            ScannerThis.getInstance().nextLine();
                            ask5 = modelSuperAsk5;
                        } while (ask5<0 || ask5>maxTeacherId);

                        LectureRepository.getInstance().getAll();
                        Lecture lectureH11 = new LectureService().createLectureWithTeacher(ask5);
                        LectureRepository.getInstance().getLecturesList().add(lectureH11);
                        LectureRepository.getInstance().getAll();
                        System.out.println("ID створенної лекції - " + lectureH11.getId());

                    } else {
                        throw new IllegalArgumentException();
                    }


            } else {
                throw new IllegalArgumentException();
            }

        }
    }

    public void getAllInfoLecture () {

        int search = -1;
        Lecture lectureFromTask = null;
        do {
            System.out.println("Введіть існуюче значення ID лекції");
            int idLectureFromTask = ScannerThis.getInstance().nextInt();
            ScannerThis.getInstance().nextLine();
            for (Lecture lecture :
                    LectureRepository.getInstance().getLecturesList()) {
                if (lecture == null) {
                    continue;
                }
                if (lecture.getId() == idLectureFromTask) {
                    search = 1;
                    lectureFromTask = lecture;
                }
            }
        } while (search<0);

        System.out.println("Значення ID для заданної лекції - " + lectureFromTask.getId());
        System.out.println("Значення IDCourse для заданної лекції - " + lectureFromTask.getIdCourse());
        System.out.println("Значення PersonId для заданної лекції - " + lectureFromTask.getPersonId());
        System.out.println("Значення Name для заданної лекції - " + lectureFromTask.getName());
    }

    public void lectureWithHomeworkAndAMById(int id) {
        LogFactory.debug(this.getClass().getName(), "Display homework and additional materials for by lecture id");
        System.out.println("Lecture with Id = " + id + ": " + LectureRepository.getInstance().getById(id));
        System.out.println("Homework for this lecture: " + HomeworkRepository.getInstance().homeworkTreeMap().get(id));
        System.out.println("AdditionalMaterials for this lecture: " + AdditionalMaterialsRepository.getInstance().additionalMaterialsTreeMap().get(id));
    }


}
