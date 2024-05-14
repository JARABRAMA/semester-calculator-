package org.semester_average.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.semester_average.data.Course;
import org.semester_average.data.Grade;
import org.semester_average.repository.SemesterRepository;
import org.semester_average.utils.DataBaseConstants;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SemesterRepositoryImplDB implements SemesterRepository {
    private static final Logger log = LogManager.getLogger(SemesterRepositoryImplDB.class);
    private Statement statement;
    private Connection connection;

    public SemesterRepositoryImplDB() {
        try {
            Class.forName(DataBaseConstants.DRIVER);
            connection = DriverManager.getConnection(
                    DataBaseConstants.URL + DataBaseConstants.DB,
                    DataBaseConstants.USER,
                    DataBaseConstants.PASSWORD
            );
            log.info("connected to data base {}{}", DataBaseConstants.URL, DataBaseConstants.DB);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            log.error("constructor: {}", e.getMessage());
            statement = null;
        }
    }

    @Override
    public List<Course> findAll() {
        try {
            var resultSet = statement.executeQuery(MessageFormat.format("SELECT * FROM {0}.{1}",
                    DataBaseConstants.DB, DataBaseConstants.COURSES_TABLE));
            List<Course> courses = new ArrayList<>();
            while (resultSet.next()) {
                var tableGradesName = resultSet.getString("table_name");
                var grades = new CourseRepositoryImplDB(tableGradesName).findAll();
                courses.add(new Course(
                        resultSet.getInt("credit"),
                        resultSet.getString("table_name").replace("_", " "),
                        grades
                ));
            }
            return courses;
        } catch (SQLException e) {
            log.info("findAll: {}", e.getMessage());
        }
        return List.of();
    }

    @Override
    public Course save(String name, int credits) {
        try {
            var tableName = name.replace(" ", "_");
            String query = MessageFormat.format("CREATE TABLE {0}.{1} (idx INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name CHAR(40) NOT NULL, qualification DOUBLE NOT NULL, percentage DOUBLE NOT NULL)",
                    DataBaseConstants.DB, tableName);
            statement.executeUpdate(query);
            var courseRepository = new CourseRepositoryImplDB(tableName);
            query = MessageFormat.format("INSERT INTO {0}.{1} (table_name, credit, average) VALUES (\"{2}\", {3}, {4});",
                    DataBaseConstants.DB, DataBaseConstants.COURSES_TABLE, tableName, credits, courseRepository.getAverage());
            statement.executeUpdate(query);
            return new Course(credits, name, new ArrayList<>());
        } catch (SQLException e) {
            log.error("save: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Course get(int index){
        var query = MessageFormat.format("SELECT * AS course FROM {0}.{1} WHERE idx = ?;",
                DataBaseConstants.DB, DataBaseConstants.COURSES_TABLE);
        try {
            var statement = connection.prepareStatement(query);
            statement.setInt(1, index);
            var resultSet = statement.executeQuery();
            if(resultSet.next()){
                var tableName = resultSet.getString("table_name");
                List<Grade> grades = new CourseRepositoryImplDB(tableName).findAll();
                return new Course(
                        resultSet.getInt("credits"),
                        tableName.replace("_", " "),
                        grades
                );
            }
        } catch (SQLException e) {
            log.error("get: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Course remove(int index) {
        var query = MessageFormat.format("DELETE FROM {0}.{1} WHERE idx = ?", DataBaseConstants.DB, DataBaseConstants.COURSES_TABLE);
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, index);
            statement.executeUpdate();
            return get(index);
        } catch (SQLException e) {
            log.error("remove: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public double getAverage() {
        return 0;
    }
}
