package tracker.Commands;
import java.util.Scanner;
import tracker.Student;
import java.util.ArrayList;

public interface Command {
    void go(Scanner sc, ArrayList<Student> students);

}
