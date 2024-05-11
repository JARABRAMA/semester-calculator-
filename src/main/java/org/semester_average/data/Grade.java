package org.semester_average.data;

public class Grade {
    private String name;
    private double qualification;
    private double percentage;

    public Grade(String name, double qualification, double percentage){
        this.name = name;
        this.percentage = percentage;
        this.qualification = qualification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQualification() {
        return qualification;
    }

    public void setQualification(double qualification) {
        this.qualification = qualification;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
