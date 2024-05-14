package org.semester_average.repository;

import java.util.List;
import org.semester_average.data.Course;

public interface SemesterRepository {
    List<Course> findAll();
    Course save(String name, int credits);
    Course get(int Index);
    Course remove(int index);
    double getAverage();
}
