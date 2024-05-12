package org.semester_average;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.semester_average.data.Grade;
import org.semester_average.data.Course;
import org.semester_average.repository.CourseRepository;
import org.semester_average.repository.impl.CourseRepositoryImplDB;

import java.util.List;

public class Main {
    private static final Logger log = LogManager.getLogger(Main.class);
    private static final Course course = new Course(
            3,
            "math",
            List.of(
                    new Grade("parcial 1", 4.5, 25.0),
                    new Grade("parcial 2", 4.6, 25.0)
            )
    );
    private static final CourseRepository courseRepository = new CourseRepositoryImplDB(course);

    public static void main(String[] args) {

        for (Grade grade : course.getGrades()) {
            var result = courseRepository.save(grade);
            log.info(result);
        }
    }
}