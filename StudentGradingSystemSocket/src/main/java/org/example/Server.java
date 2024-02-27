package org.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/studentgradingsystem2";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";
    private static final String COURSE_TABLE = "course";
    private static final String INSTRUCTOR_TABLE = "instructor";
    private static final String STUDENT_TABLE = "student";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server waiting for client connection...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

                    int clientType = ois.readInt();

                    switch (clientType) {
                        case 1:
                            handleAdminOperations(ois, oos);
                            break;
                        case 2:
                            handleInstructorOperations(ois, oos);
                            break;
                        case 3:
                            handleStudentOperations(ois,oos);
                            break;
                        default:
                            oos.writeObject("Invalid client type");
                            oos.flush();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleStudentOperations(ObjectInputStream ois, ObjectOutputStream oos) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int requestType = ois.readInt();

            switch (requestType) {
                case 1:
                    viewStudentGrades(ois, oos, connection);
                    break;
                case 2:
                    calculateGradeAverage(ois, oos, connection);
                    break;
                default:
                    oos.writeObject("Invalid student request type");
                    oos.flush();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void viewStudentGrades(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String studentId = (String) ois.readObject();

        if (!recordExists(connection, STUDENT_TABLE, "studentId", studentId)) {
            oos.writeObject("Student not found.");
            oos.flush();
            return;
        }

        String viewGradesSql = "SELECT sg.courseId, c.courseName, sg.gradeValue " +
                "FROM student_grades sg " +
                "JOIN course c ON sg.courseId = c.courseId " +
                "WHERE sg.studentId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(viewGradesSql)) {
            preparedStatement.setString(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder response = new StringBuilder("Your Grades:\n");
                while (resultSet.next()) {
                    String courseId = resultSet.getString("courseId");
                    String courseName = resultSet.getString("courseName");
                    double gradeValue = resultSet.getDouble("gradeValue");
                    response.append("Course: ").append(courseName).append(" (").append(courseId).append("), Grade: ").append(gradeValue).append("\n");
                }
                oos.writeObject(response.toString());
            }
        }

        oos.flush();
    }



    private static void calculateGradeAverage(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String studentId = (String) ois.readObject();

        if (!recordExists(connection, STUDENT_TABLE, "studentId", studentId)) {
            oos.writeObject("Student not found.");
            oos.flush();
            return;
        }

        String calculateAvgSql = "SELECT AVG(gradeValue) AS avgGrade FROM student_grades WHERE studentId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(calculateAvgSql)) {
            preparedStatement.setString(1, studentId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    double avgGrade = resultSet.getDouble("avgGrade");
                    oos.writeObject("Your Average Grade: " + avgGrade);
                } else {
                    oos.writeObject("No grades found for the student.");
                }
            }
        }
        oos.flush();
    }
    private static void handleInstructorOperations(ObjectInputStream ois, ObjectOutputStream oos) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int operationChoice = ois.readInt();

            switch (operationChoice) {
                case 1:
                    handleAssignCourse(ois, oos, connection);
                    break;
                case 2:
                    handleAssignGrade(ois, oos, connection);
                    break;
                default:
                    oos.writeObject("Invalid operation choice");
                    oos.flush();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void handleAdminOperations(ObjectInputStream ois, ObjectOutputStream oos) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            int operationChoice = ois.readInt();

            switch (operationChoice) {
                case 1:
                    addStudent(ois, oos, connection);
                    break;
                case 2:
                    addInstructor(ois, oos, connection);
                    break;
                default:
                    oos.writeObject("Invalid admin operation choice");
                    oos.flush();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void addStudent(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String studentName = (String) ois.readObject();

        String sql = "INSERT INTO student (studentName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, studentName);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                oos.writeObject("Student added successfully! Student Name: " + studentName);
            } else {
                oos.writeObject("Failed to add student.");
            }
        }
        oos.flush();
    }

    private static void addInstructor(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String instructorName = (String) ois.readObject();

        String sql = "INSERT INTO instructor (instructorName) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, instructorName);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                oos.writeObject("Instructor added successfully! Instructor Name: " + instructorName);
            } else {
                oos.writeObject("Failed to add instructor.");
            }
        }
        oos.flush();
    }

    private static void handleAssignCourse(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String instructorId = (String) ois.readObject();
        String courseId = (String) ois.readObject();

        if (!recordExists(connection, INSTRUCTOR_TABLE, "instructorId", instructorId) ||
                !recordExists(connection, COURSE_TABLE, "courseId", courseId)) {
            oos.writeObject("Instructor or course not found.");
            oos.flush();
            return;
        }

        String assignCourseSql = "INSERT INTO instructor_courses (instructorId, courseId) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(assignCourseSql)) {
            preparedStatement.setString(1, instructorId);
            preparedStatement.setString(2, courseId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                oos.writeObject("Course assigned successfully! Instructor ID: " + instructorId + ", Course ID: " + courseId);
            } else {
                oos.writeObject("Failed to assign course.");
            }
        }
        oos.flush();
    }

    private static void handleAssignGrade(ObjectInputStream ois, ObjectOutputStream oos, Connection connection)
            throws SQLException, IOException, ClassNotFoundException {
        String instructorId = (String) ois.readObject();
        String studentId = (String) ois.readObject();
        String courseId = (String) ois.readObject();
        double gradeValue = (double) ois.readObject();
        String gradeId = (String) ois.readObject();

        if (!recordExists(connection, INSTRUCTOR_TABLE, "instructorId", instructorId) ||
                !recordExists(connection, STUDENT_TABLE, "studentId", studentId) ||
                !recordExists(connection, COURSE_TABLE, "courseId", courseId)) {
            oos.writeObject("Instructor, student, or course not found.");
            oos.flush();
            return;
        }

        String assignGradeSql = "INSERT INTO student_grades (instructorId, studentId, courseId, gradeValue, gradeId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(assignGradeSql)) {
            preparedStatement.setString(1, instructorId);
            preparedStatement.setString(2, studentId);
            preparedStatement.setString(3, courseId);
            preparedStatement.setDouble(4, gradeValue);
            preparedStatement.setString(5, gradeId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                oos.writeObject("Grade assigned successfully! Student ID: " + studentId +
                        ", Course ID: " + courseId + ", Instructor ID: " + instructorId + ", Grade Value: " + gradeValue);
            } else {
                oos.writeObject("Failed to assign grade.");
            }
        }
        oos.flush();
    }
    private static boolean recordExists(Connection connection, String tableName, String columnName, String value)
            throws SQLException {
        String checkRecordSql = "SELECT * FROM " + tableName + " WHERE " + columnName + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkRecordSql)) {
            preparedStatement.setString(1, value);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
