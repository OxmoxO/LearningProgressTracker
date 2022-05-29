package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Find implements Command {

    private static final String FIND = "%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n";
    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        System.out.println("Enter an id or 'back' to return");
        String data = sc.nextLine().trim();

        while (!data.equals("back")) {
            if (data.matches("\\d*")) {
                int id = Integer.parseInt(data);
                if (students
                        .stream()
                        .anyMatch(user ->
                                user.getId() == id)) {

                    Student student = students
                                      .stream()
                                      .filter(o -> o.getId() == id)
                                      .findAny()
                                      .orElse(null);

                    System.out.printf(FIND, id, Objects.requireNonNull(student).getJavaScore(),
                                                       student.getDsaScore(),
                                                       student.getDataBasesScore(),
                                                       student.getSpringScore());
                } else {
                    System.out.printf("No student is found for id=%d\n", id);
                }
            }
            data = sc.nextLine().trim();
        }
        Main.command = new CommandsManager();
    }
}
