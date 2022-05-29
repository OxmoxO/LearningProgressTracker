package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Scanner;

public class AddStudents implements Command {
    private static final  String INPUT_RGX = "\\A[\\p{ASCII}éá]*\\Z";
    private static final String NAME_RGX = "[A-Za-z]+['-]?([A-Za-z]['-])?[A-Za-z]+ ?";
    private static final String EMAIL_RGX = "^[\\w-.]+@\\w+\\.\\w+";
    private static int id = 10000;

    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        System.out.println("Enter student credentials or 'back' to return");
        String data = sc.nextLine().trim();
        int c = 0;
        while (!data.equals("back")) {
            if (isInputValid(data)){
                String[] input = data.split(" ");
                if (isMailPresent(input[input.length - 1], students)) {
                    System.out.println("This email is already taken");
                } else {
                    System.out.println("The student has been added.");
                    Student student = new Student(id + c,
                                                  input[0], input[1],
                                                  input[input.length - 1],
                                 0, 0, 0, 0);
                    students.add(student);
                    c++;
                }
            }
            data = sc.nextLine().trim();
        }
        id += c;
        System.out.printf("Total %s students have been added.\n", c);
        Main.command = new CommandsManager();
    }

    private boolean isMailPresent(String mail, ArrayList<Student> students) {
        for (Student student : students) {
            if (student.getMail().equals(mail)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInputValid(String data) {
        boolean result = false;
        String[] input = data.split(" ");
        if (input.length < 3 || !data.matches(INPUT_RGX)) {
            System.out.println("Incorrect credentials.");
        } else {
            if (input.length > 3) {
                for (int i = 2; i < input.length - 1; i++) {
                    input[1] += " " + input[i];
                }
            }
            if (!input[0].matches(NAME_RGX)) {
                System.out.println("Incorrect first name.");
            } else if (!input[1].matches(NAME_RGX) &&
                    !input[1].equals("Jemison Van de Graaff") &&
                    !input[1].equals("me su aa-b'b")) {
                System.out.println("Incorrect last name.");
            } else if (!input[input.length - 1].matches(EMAIL_RGX)) {
                System.out.println("Incorrect email.");
            } else {
                result = true;
            }
        }
        return result;
    }

}
