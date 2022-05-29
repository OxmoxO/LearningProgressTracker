package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Student> students = new ArrayList<>();
    public static boolean isGo = true;
    public static Command command;

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        Scanner sc = new Scanner(System.in);
        command = new CommandsManager();
        while (isGo) {
            command.go(sc, students);
        }
    }
}
