package org.semester_average.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import org.semester_average.data.Course;
import org.semester_average.data.Grade;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseRepositoryImplDBTest {
    private static final Logger log = LogManager.getLogger(CourseRepositoryImplDBTest.class);

    private final Course course = new Course(
            3,
            "math",
            List.of(
                    new Grade("taller 1", 5.0, 25.0),
                    new Grade("partial 1", 4.8, 25.0)
            )
    );
    private final CourseRepositoryImplDB courseRepository = new CourseRepositoryImplDB(course);


    @Test
    void findAll() {
        var grades = courseRepository.findAll();
        grades.forEach(log::info);
        assertNotEquals(grades, List.of());
    }

    @Test
    void save() {
        var grade = new Grade("pracial 4", 5, 20);
        var result = courseRepository.save(grade);
        log.info(result);
        assertEquals(grade, result);
    }

    @Test
    void saving_invalid_qualification() {
        var grade = new Grade("taller 1", 5.6, 20);
        var result = courseRepository.save(grade);
        assertNull(result);

    }

    @Test
    void saving_invalid_percentage(){
        var grade = new Grade("INVALID", 0, 90);
        var result = courseRepository.save(grade);
        assertNull(result);
    }

    @Test
    void get() {
        var grade = courseRepository.get(1);
        log.info(grade);
        var str = "parcial 2: qualification: 4.6, percentage: 25";
        assertEquals(grade.toString(), str);
    }

    @Test
    void getPercentage(){
        var totalPercentage = courseRepository.getPartialPercentage();
        log.info("total percentage: {}", totalPercentage);
    }

    @Test
    void remove() {
        var grade = courseRepository.remove(1);
        log.info(grade);
        assertNotNull(grade);
    }

    @Test
    void getAverage(){
        var average  = courseRepository.getAverage();
        log.info("average: {}", average);
        assertNotEquals(average, 0);
    }

    @Test
    void getAdvise(){
        log.info(courseRepository.getAdvice(4.5));
    }
}