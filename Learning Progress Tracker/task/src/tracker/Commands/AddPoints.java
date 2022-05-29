package tracker.Commands;

import tracker.Student;
import java.util.*;

public class AddPoints implements Command {
    private static final String NOT_FOUND = "No student is found for id=%s\n";

    private static final String NOTIFY = "To: %s\n" + "Re: Your Learning Progress\n" +
            "Hello, %s %s! You have accomplished our %s course!";

    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        System.out.println("Enter an id and points or 'back' to return");
        String data = sc.nextLine().trim();
        while (!data.equals("back")) {
            add(data, students);
            data = sc.nextLine().trim();
        }
        Main.command = new CommandsManager();
    }

    void add(String data, ArrayList<Student> students) {

        if (data.matches("[\\da-zA-Z]* \\d* \\d* \\d* \\d*")) {
            String[] input = data.split(" ");

            if (input[0].matches("[A-Za-z]+") ||
                    students.stream().noneMatch(student ->
                            student.getId() == Integer.parseInt(input[0]))) {
                System.out.printf(NOT_FOUND, input[0]);

            } else {

                Student student = students.stream()
                        .filter(s -> s.getId() == Integer.parseInt(input[0]))
                        .findAny()
                        .orElse(null);
                assert student != null;
                student
                        .setJavaScore(Integer.parseInt(input[1]));
                student
                        .setDsaScore(Integer.parseInt(input[2]));
                student
                        .setDataBasesScore(Integer.parseInt(input[3]));
                student
                        .setSpringScore(Integer.parseInt(input[4]));
                System.out.println("Points updated.");
                addNote(student);
            }

        } else {
            System.out.println("Incorrect points format.");
        }
    }

    private void addNote(Student student) {
        if (!student.isJavaComp() &&
                student.getJavaScore() >= 600) {

            addNoteAssistant(student,"Java");
            student.setJavaComp(true);
        }
        if (!student.isDsaComp() &&
                student.getDsaScore() >= 400) {
            addNoteAssistant(student,"DSA");
            student.setDsaComp(true);
        }
        if (!student.isDbComp() && 
                student.getDataBasesScore() >= 480) {
            addNoteAssistant(student,"DataBase");
            student.setJavaComp(true);
        }
        if (!student.isSpringComp() &&
                student.getSpringScore() >= 550) {
            addNoteAssistant(student,"Spring");
            student.setJavaComp(true);
        }
    }

    private void addNoteAssistant(Student student, String course) {
        String mail = student.getMail();
        String note = String.format(NOTIFY, student.getMail(),
                                               student.getFirstName(),
                                               student.getLastName(),
                                               course);

        if (Notify.notices.containsKey(mail)) {
            Notify
                    .notices
                    .get(student.getMail())
                    .add(note);
        } else {
            Notify
                    .notices
                    .put(mail,  new ArrayList<String>(List.of(note)));
        }
    }

}


