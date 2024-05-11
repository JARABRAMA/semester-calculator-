package org.semester_average.data;

import java.util.List;

public class Course {
    private int credits;
    private String name;
    private List<Grade> grades;

    public Course(int credits, String name, List<Grade> grades) {
        this.credits = credits;
        this.name = name;
        this.grades = grades;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public int getCredits() {
        return credits;
    }

    public String getName() {
        return name;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}
