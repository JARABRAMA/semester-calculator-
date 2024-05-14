package org.semester_average.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.semester_average.data.Course;
import org.semester_average.repository.SemesterRepository;


class SemesterRepositoryImplDBTest {
    private static final SemesterRepository semesterRepository = new SemesterRepositoryImplDB();
    private static final Logger log = LogManager.getLogger(SemesterRepositoryImplDBTest.class);

    @Test
    void findAll() {
        var courses = semesterRepository.findAll();
        courses.forEach(log::info);
    }

    @Test
    void save() {
        var name = "matematicas discretas 2";
        var credits = 3;
        var course = semesterRepository.save(name, credits);
    }

    @Test
    void remove() {
    }

    @Test
    void getAverage() {
    }
}