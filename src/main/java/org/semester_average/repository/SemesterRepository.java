package org.semester_average.repository;

import java.util.List;
import org.semester_average.data.Course;

public interface SemesterRepository {
    List<Course> findAll();
    Course save(Course course);
    Course remove(int index);
    double getAverage();
}
