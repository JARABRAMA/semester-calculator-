package org.semester_average.data;

import java.text.MessageFormat;

public class Grade {
    private String name;
    private double qualification;
    private double percentage;

    public Grade(String name, double qualification, double percentage){
        this.name = name;
        this.percentage = percentage;
        this.qualification = qualification;
    }

    @Override
    public String toString(){
        return MessageFormat.format(
                "{0}: qualification: {1}, percentage: {2}",
                this.name,
                this.qualification,
                this.percentage
        );
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
