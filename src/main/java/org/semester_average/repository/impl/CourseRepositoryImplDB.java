package org.semester_average.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.semester_average.data.Grade;
import org.semester_average.repository.CourseRepository;

import java.sql.*;
import java.text.MessageFormat;
import java.util.List;

import org.semester_average.data.Course;
import org.semester_average.utlis.DataBaseConstants;

import static org.semester_average.utlis.DataBaseConstants.DB;
import static org.semester_average.utlis.SemesterConstant.*;


public class CourseRepositoryImplDB implements CourseRepository {
    private final String TABLE;
    private Statement statement;
    private Connection connection;
    private static final Logger log = LogManager.getLogger(CourseRepositoryImplDB.class);
    private double restPercentage = maxPercentage;

    public CourseRepositoryImplDB(Course course) {
        this.TABLE = course.getName().replace(" ", "_");
        try {
            Class.forName(DataBaseConstants.DRIVER);
            connection = DriverManager.getConnection(
                    DataBaseConstants.URL + DB,
                    DataBaseConstants.USER,
                    DataBaseConstants.PASSWORD
            );
            statement = connection.createStatement();
            log.info(MessageFormat.format("database {0} connected", DB));
        } catch (SQLException | ClassNotFoundException e) {
            log.error("constructor: " + e.getMessage());
        }
    }

    @Override
    public double getPercentage() {
        var query = MessageFormat.format(
                "SELECT SUM(percentage) AS total_percentage FROM {0}.{1}",
                DB,
                TABLE
        );
        try {
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getDouble("total_percentage");
            }
        } catch (SQLException e) {
            log.error("getPercentage: {}", e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Grade> findAll() {
        List<Grade> result = new java.util.ArrayList<>(List.of());
        var query = MessageFormat.format("SELECT * FROM {0}.{1}", DB, TABLE);
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var qualification = resultSet.getDouble("qualification");
                var percentage = resultSet.getDouble("percentage");
                result.add(
                        new Grade(name, qualification, percentage)
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return result;
        }
        return result;
    }

    @Override
    public Grade save(Grade grade) {
        if (grade.getQualification() > maxQualification || grade.getQualification() < minQualification) {
            log.error("the qualification is out of the let limit");
            return null;
        }
        if (grade.getPercentage() > maxPercentage || grade.getPercentage() < minPercentage) {
            log.error("the percentage is out of the let limit");
            return null;
        }
        var query = MessageFormat.format(
                "INSERT INTO {0}.{1} (name, qualification, percentage) VALUES (\"{2}\", {3}, {4})",
                DB,
                TABLE,
                grade.getName(),
                grade.getQualification(),
                grade.getPercentage()
        );
        log.info("the query is: {}", query);
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
        return grade;

    }

    public Grade get(int index) {
        var query = MessageFormat.format(
                "SELECT * FROM {0}.{1} WHERE idx = {2};",
                DB,
                TABLE,
                index
        );
        log.info(MessageFormat.format("the query is: {0}", query));
        try {
            var resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return new Grade(
                        resultSet.getString("name"),
                        resultSet.getDouble("qualification"),
                        resultSet.getDouble("percentage")
                );
            }
        } catch (SQLException e) {
            log.error("get: {}", e.getMessage());
        }
        return null;
    }


    @Override
    public Grade remove(int index) {
        var query = MessageFormat.format(
                "DELETE FROM {0}.{1} WHERE idx = {2};",
                DB,
                TABLE,
                index
        );
        try {
            var grade = get(index);
            if (grade == null) {
                log.error(MessageFormat.format("Grade at table {0} at index {1} does not exist",
                        TABLE, index));
                return null;
            }
            statement.executeUpdate(query);

            var orderQuery = MessageFormat.format(
                    "SET @idx = 0;\n" +
                            "UPDATE {0}.{1} SET idx = (@idx:=@idx+1) ORDER BY idx;",
                    DB,
                    TABLE
            );
            statement.executeUpdate(orderQuery);

            log.info(MessageFormat.format("grade {0} deleted from {1} at index {2}", grade, TABLE, index));
            return grade;
        } catch (SQLException e) {
            log.error("remove: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public double getAverage() {
        return 0;
    }

    @Override
    public String getAdvice() {
        return "";
    }
}
