package org.example.Clients;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class StudentClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            int operationChoice;

            System.out.println("Choose operation:");
            System.out.println("1. View Grades");
            System.out.println("2. Calculate Average Grade");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            operationChoice = scanner.nextInt();

            oos.writeInt(3);
            oos.flush();

            oos.writeInt(operationChoice);
            oos.flush();

            switch (operationChoice) {
                case 1:
                case 2:
                    System.out.print("Enter your studentId: ");
                    String studentId = scanner.next();
                    oos.writeObject(studentId);
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
