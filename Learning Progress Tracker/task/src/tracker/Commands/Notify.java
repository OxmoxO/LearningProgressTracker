package tracker.Commands;


import tracker.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Notify implements Command {

    static Map<String, ArrayList<String>> notices = new HashMap<>();
    private static final String NOTIFY = "Total %d students have been notified";

    @Override
    public void go(Scanner scanner, ArrayList<Student> students) {
        for (ArrayList<String> note : notices.values()) {
            note.forEach(System.out::println);
        }
        System.out.printf((NOTIFY) + "%n", notices.size());
        notices.clear();
        Main.command = new CommandsManager();
    }
}