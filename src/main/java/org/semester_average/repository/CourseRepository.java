package org.semester_average.repository;

import java.util.List;
import org.semester_average.data.Grade;

public interface CourseRepository {
    List<Grade> findAll();
    Grade save(Grade grade);
    Grade remove(int index);
    double getAverage();
    String getAdvice();
    double getPercentage(); // returns actual percentage expensed
}
