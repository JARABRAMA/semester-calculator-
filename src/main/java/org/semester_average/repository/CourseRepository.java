package org.semester_average.repository;

import java.util.List;
import org.semester_average.data.Grade;

public interface CourseRepository {
    List<Grade> findAll();
    Grade save(Grade grade);
    Grade remove(int index);
    double getAverage();
    String getAdvice(double goal);
    double getPartialPercentage(); // returns actual percentage expensed
}
