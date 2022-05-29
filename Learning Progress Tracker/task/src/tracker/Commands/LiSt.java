package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Scanner;

public class LiSt implements Command {
    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students found");

        } else {
            System.out.println("Students:");
            students
                    .forEach((student) ->
                            System.out.println(student.getId()));
        }
        Main.command = new CommandsManager();
    }
}