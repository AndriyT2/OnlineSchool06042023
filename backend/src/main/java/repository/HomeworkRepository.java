package repository;

import models.Homework;
import utility.utilityLog.LogFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class HomeworkRepository implements Serializable {
    public List<Homework> getHomeworkList() {
        return homeworkList;
    }

    private List<Homework> homeworkList;

    private static HomeworkRepository instance;

    private HomeworkRepository() {
        this.homeworkList = new ArrayList<>();
    }

    public static HomeworkRepository getInstance() {
        if (instance == null) {
            instance = new HomeworkRepository();
        }
        return instance;
    }

    public Homework getById(int index) {
        for (Homework homework : homeworkList) {
            if (homework.getId() == index) {
                return homework;
            }
        }
        throw new NoSuchElementException();
    }


    public TreeMap<Integer, Homework> homeworkTreeMap() {
        TreeMap<Integer, Homework> homeworkTreeMap = new TreeMap<>();
        for (Homework homework : HomeworkRepository.getInstance().getHomeworkList()) {
            homeworkTreeMap.put(homework.getLectureId(), homework);
        }
        LogFactory.info(this.getClass().getName(), "Create TreeMapHomework from ListHomework");
        return homeworkTreeMap;

    }


}

