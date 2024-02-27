package org.example.Clients;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class AdminClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            int operationChoice;

            System.out.println("Choose operation:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Instructor");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            operationChoice = scanner.nextInt();

            oos.writeInt(1);
            oos.flush();

            oos.writeInt(operationChoice);
            oos.flush();

            switch (operationChoice) {
                case 1:
                    System.out.print("Enter student name: ");
                    String studentName = scanner.next();
                    oos.writeObject(studentName);
                    oos.flush();
                    break;
                case 2:
                    System.out.print("Enter instructor name: ");
                    String instructorName = scanner.next();
                    oos.writeObject(instructorName);
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}