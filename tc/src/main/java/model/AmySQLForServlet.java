package model;

import models.Lecture;
import models.Person;
import models.Role;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.IntTrue;
import utility.utilityLog.LogFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@Lazy
public class AmySQLForServlet {

    private static final String BD_URL = "jdbc:mysql://localhost/online_school";
    private static final String USER = "A";
    private static final String PASSWORD = "ssss";


    public Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(BD_URL, USER, PASSWORD);
    }

    public void addPersonSQLJSP(String lastname, String firstname, String phone, String email, String role) {
//        LogFactory.debug(this.getClass().getName(), "Create new person JSP");

        String addPerson = "INSERT INTO `online_school`.`person` (`lastname`, `firstname`,`phone`,`email`,`role`) VALUES (?, ?,?,?, ?)";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(addPerson);
            statement.setString(1, lastname);
            statement.setString(2, firstname);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setString(5, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

    }


    public void addPersonMenuSQL() {

        try (Connection connection = DriverManager.getConnection(BD_URL, USER, PASSWORD)) {
            int ask1;
            String addPerson = "INSERT INTO `online_school`.`person` (`role`) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(addPerson);
            do {
                System.out.println("Введіть числове значення для вибору необхідного критерія: \n1 Студент;\n2 Вчитель.");
                ask1 = new IntTrue().intTrue();
            } while (ask1 < 1 | ask1 > 2);

            if (ask1 == 1) {
                System.out.println("Ви створили нового студента.");
                statement.setString(1, "student");
                statement.executeUpdate();

            } else {
                System.out.println("Ви створили нового вчителя.");
                statement.setString(1, "teacher");
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            LogFactory.warning(this.getClass().getName(), "SQLPS", e.getStackTrace());
        }
    }

    public List<Person> personGetAll() {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String getAll = "{call get_data_from_table (?) }";
            CallableStatement statement = connection.prepareCall(getAll);
            statement.setString(1, "person");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int idSQL = result.getInt("person_id");
                String lastname = result.getString("lastname");
                String firstname = result.getString("firstname");
                String phone = result.getString("phone");
                String email = result.getString("email");
                String role = result.getString("role");
                Person person = new Person(firstname, lastname, phone, email, idSQL, Role.valueOf(role.toUpperCase()));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

        return personList;
    }


    public Person personById(int IdFromDB) {
        String person = "SELECT * FROM online_school.person where person_id = " + IdFromDB;
        Person person1 = null;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(person);
            ResultSet result = statement.executeQuery(person);
            while (result.next()) {
                int idSQL = result.getInt("person_id");
                String lastname = result.getString("lastname");
                String firstname = result.getString("firstname");
                String phone = result.getString("phone");
                String email = result.getString("email");
                String role = result.getString("role");
                person1 = new Person(firstname, lastname, phone, email, idSQL, Role.valueOf(role.toUpperCase()));
                System.out.println(person1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }
        return person1;
    }

    public List<Lecture> lectureBefore2023() {
        List<Lecture> lectureList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String lectureSQL = "SELECT l.name, COUNT(am.additional_materials_id) AS am_count FROM lecture l LEFT JOIN additional_materials am ON l.lecture_id = am.lecture_id WHERE l.lecture_date < '2023-01-01 00:00:00' GROUP BY l.lecture_id ORDER BY l.lecture_date";
            CallableStatement statement = connection.prepareCall(lectureSQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String name = result.getString("name");
                int am_count = result.getInt("am_count");
                Lecture lecture = new Lecture(name);
                lecture.am = am_count;
                lectureList.add(lecture);
            }
        } catch (SQLException e) {
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }
        return lectureList;
    }

    public Lecture firstLecture() {
        Lecture lecture = null;
        try (Connection connection = getConnection()) {
            String lectureSQL = "SELECT l.lecture_id, l.description, l.lecture_date, l.name, l.creation_date, COUNT(h.homework_id)  AS hw_count\nFROM lecture l\nLEFT JOIN homework h ON l.lecture_id = h.lecture_id\nWHERE l.creation_date = (SELECT MAX(creation_date) FROM lecture)\nGROUP BY l.lecture_id\nORDER BY hw_count DESC\nLIMIT 1".formatted();
            CallableStatement statement = connection.prepareCall(lectureSQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int IdSQL = result.getInt("lecture_id");
                String name = result.getString("name");
                String description = result.getString("description");
                LocalDateTime creationDate = result.getTimestamp("creation_date").toLocalDateTime();
                LocalDateTime lectureDate = result.getTimestamp("lecture_date").toLocalDateTime();
                lecture = new Lecture(IdSQL, name, description, creationDate, lectureDate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }
        return lecture;
    }

    public List<Person> studentByLastname() {
        List<Person> studentList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String student = "SELECT * FROM person where role = 'student' ORDER BY lastname";
            CallableStatement statement = connection.prepareCall(student);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int idSQL = result.getInt("person_id");
                String lastname = result.getString("lastname");
                String firstname = result.getString("firstname");
                String phone = result.getString("phone");
                String email = result.getString("email");
                String role = result.getString("role");
                Person student1 = new Person(firstname, lastname, phone, email, idSQL, Role.valueOf(role.toUpperCase()));
                studentList.add(student1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

        return studentList;
    }

    public Map<String, Integer> amType() {
        Map<String, Integer> type = new HashMap<>();
        try (Connection connection = getConnection()) {
            String amType = "SELECT resource_type, count(*) as type_total FROM online_school.additional_materials group by resource_type";
            CallableStatement statement = connection.prepareCall(amType);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String typeOfAM = result.getString("resource_type");
                Integer count = result.getInt("type_total");
                type.put(typeOfAM, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

        return type;
    }

    public List<Person> teacherByLetter() {
        List<Person> personList = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String teacherSQL = "SELECT * FROM online_school.person where role = 'teacher' AND lastname REGEXP '^[A-N]|^[А-Н]'";
            CallableStatement statement = connection.prepareCall(teacherSQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int idSQL = result.getInt("person_id");
                String lastname = result.getString("lastname");
                String firstname = result.getString("firstname");
                String phone = result.getString("phone");
                String email = result.getString("email");
                String role = result.getString("role");
                Person person = new Person(firstname, lastname, phone, email, idSQL, Role.valueOf(role.toUpperCase()));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

        return personList;
    }

    public List<Person> studentOnCourse(int countCourse) {
        List<Person> personList = new ArrayList<>();
        String studentSQL;
        try (Connection connection = getConnection()) {
            if (countCourse == 1) {
                studentSQL = """
                        SELECT  p.lastname, p.firstname,  COUNT(cws.course_with_student_id) AS course_total_count
                        FROM person p  LEFT  JOIN course_with_student cws ON p.person_id = cws.person_id
                        WHERE p.role = 'student'
                            GROUP BY p.person_id
                            HAVING course_total_count =1
                        ORDER BY lastname;
                        """;
            } else if (countCourse == 2) {
                studentSQL = """
                        SELECT  p.lastname, p.firstname,  COUNT(cws.course_with_student_id) AS course_total_count
                        FROM person p  LEFT  JOIN course_with_student cws ON p.person_id = cws.person_id
                        WHERE p.role = 'student'
                            GROUP BY p.person_id
                            HAVING course_total_count =2
                        ORDER BY lastname""";
            } else {
                studentSQL = """
                        SELECT  p.lastname, p.firstname,  COUNT(cws.course_with_student_id) AS course_total_count
                        FROM person p  LEFT  JOIN course_with_student cws ON p.person_id = cws.person_id
                        WHERE p.role = 'student'
                            GROUP BY p.person_id
                            HAVING course_total_count >=3
                        ORDER BY lastname""";
            }


            CallableStatement statement = connection.prepareCall(studentSQL);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String lastname = result.getString("lastname");
                String firstname = result.getString("firstname");
                Person person = new Person(firstname, lastname);
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogFactory.warning(this.getClass().getName(), "JSP", e.getStackTrace());
        }

        return personList;
    }


}
