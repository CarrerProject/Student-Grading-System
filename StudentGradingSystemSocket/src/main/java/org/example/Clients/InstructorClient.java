package org.example.Clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InstructorClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            int operationChoice;

                System.out.println("Choose operation:");
                System.out.println("1. Assign Course");
                System.out.println("2. Assign Grade");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                operationChoice = scanner.nextInt();

                oos.writeInt(2);
                oos.flush();

                oos.writeInt(operationChoice);
                oos.flush();

                switch (operationChoice) {
                    case 1:
                        System.out.print("Enter your instructorId: ");
                        String instructorId = scanner.next();
                        oos.writeObject(instructorId);
                        oos.flush();

                        System.out.print("Enter courseId to assign: ");
                        String courseId = scanner.next();
                        oos.writeObject(courseId);
                        oos.flush();

                        break;
                    case 2:
                        System.out.print("Enter your instructorId: ");
                        instructorId = scanner.next();
                        oos.writeObject(instructorId);
                        oos.flush();

                        System.out.print("Enter studentId to assign grade: ");
                        String studentId = scanner.next();
                        oos.writeObject(studentId);
                        oos.flush();

                        System.out.print("Enter courseId to assign grade: ");
                        courseId = scanner.next();
                        oos.writeObject(courseId);
                        oos.flush();

                        System.out.print("Enter gradeValue to assign: ");
                        double gradeValue = scanner.nextDouble();
                        oos.writeObject(gradeValue);
                        oos.flush();

                        System.out.print("Enter gradeId: ");
                        String gradeId = scanner.next();
                        oos.writeObject(gradeId);
                        oos.flush();

                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid operation choice");
                        break;
                }

                String response = (String) ois.readObject();
                System.out.println(response);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
