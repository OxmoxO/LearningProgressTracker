package tracker.Commands;

import tracker.Student;
import java.util.ArrayList;
import java.util.Scanner;

public class Exit implements Command {
    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        Main.isGo = false;
        System.out.println("Bye!");
    }
}
