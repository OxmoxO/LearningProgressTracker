package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Scanner;
public class CommandsManager implements Command {

    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        String data = sc.nextLine().trim();
        switch (data) {

            case "add students":
                Main.command = new AddStudents();
                break;
            case "add points":
                Main.command = new AddPoints();
                break;
            case "find":
                Main.command = new Find();
                break;
            case "list":
                Main.command = new LiSt();
                break;
            case "statistics":
                Main.command = new Statistics();
                break;
            case "notify":
                Main.command = new Notify();
                break;
            case "back":
                System.out.println("Enter 'exit' to exit the program.");
                break;
            case "exit":
                Main.command = new Exit();
                break;
            case "":
                System.out.println("No input.");
                break;
            default:
                System.out.println("Unknown command!");
        }
    }
}
