package tracker.Commands;

import tracker.Student;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Statistics implements Command {

    private static final String[] courses = new String[]{"Java", "DSA", "Databases", "Spring"};
    private static final String HEADER = "Type the name of a course to see details or 'back' to quit:";
    private static final String GENERAL_STATISTICS = "Most popular: %s\n" +
            "Least popular: %s\n" +
            "Highest activity: %s\n" +
            "Lowest activity: %s\n" +
            "Easiest course: %s\n" +
            "Hardest course: %s\n";

    private static final Function<ArrayList<Student>, long[]> POPULATE =
            (students) -> {
        long[] course = new long[4];
        course[0] = students.stream().filter(s -> s.getJavaScore() > 0).count();
        course[1] = students.stream().filter(s -> s.getDsaScore() > 0).count();
        course[2] = students.stream().filter(s -> s.getDataBasesScore() > 0).count();
        course[3] = students.stream().filter(s -> s.getSpringScore() > 0).count();
        return course;
    };
    private static final Function<ArrayList<Student>, long[]> ACTIVE =
            (students) -> {
        long[] course = new long[4];
        course[0] = students.stream().mapToInt(Student::getJavaScore).sum();
        course[1] = students.stream().mapToInt(Student::getDsaScore).sum();
        course[2] = students.stream().mapToInt(Student::getDataBasesScore).sum();
        course[3] = students.stream().mapToInt(Student::getSpringScore).sum();
        return course;
    };
    private static final Function<ArrayList<Student>, long[]> EASY_HARD =
            (students) -> {
        long[] course = new long[4];
        course[0] = students.stream().mapToInt(Student::getJavaScore).sum();
        course[1] = students.stream().mapToInt(Student::getDsaScore).sum();
        course[2] = students.stream().mapToInt(Student::getDataBasesScore).sum();
        course[3] = students.stream().mapToInt(Student::getSpringScore).sum();
        return course;
    };
    private static final Function<ArrayList<Student>, ArrayList<Student>> JAVA =
            (students) -> students
                         .stream()
                         .filter(student -> student.getJavaScore() > 0)
                         .collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<Student>, ArrayList<Student>> DSA =
            (students) -> students
                          .stream()
                          .filter(s -> s.getDsaScore() > 0)
                          .collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<Student>, ArrayList<Student>> DB =
            (students) -> students
                    .stream()
                    .filter(s -> s.getDataBasesScore() > 0)
                    .collect(Collectors.toCollection(ArrayList::new));

    private static final Function<ArrayList<Student>, ArrayList<Student>> SPRING =
            (students) -> students
                         .stream()
                         .filter(s -> s.getSpringScore() > 0)
                         .collect(Collectors.toCollection(ArrayList::new));

    private static final Comparator<Student> COMPARE_JAVA =
            Comparator
                    .comparingInt(Student::getJavaScore)
                    .reversed()
                    .thenComparing(Student::getId);
    private static final Comparator<Student> COMPARE_DSA =
            Comparator
                    .comparingInt(Student::getDsaScore)
                    .reversed()
                    .thenComparing(Student::getId);
    private static final Comparator<Student> COMPARE_DB =
            Comparator
                    .comparingInt(Student::getDataBasesScore)
                    .reversed()
                    .thenComparing(Student::getId);
    private static final Comparator<Student> COMPARE_STRING =
            Comparator
                    .comparingInt(Student::getSpringScore)
                    .reversed()
                    .thenComparing(Student::getId);




    private void generalStat(ArrayList<Student> students) {
        String mostP = "n/a", leastP = "n/a";
        String highestAc = "n/a", lowestAc = "n/a";
        String easyCourse = "n/a", hardCourse = "n/a";

        if (!students.isEmpty()) {
            String[] pop = calcStat(students, POPULATE);
            mostP = pop[0];
            leastP = pop[1];
            String[] active = calcStat(students, ACTIVE);
            highestAc = "Java, DSA, Databases, Spring, ";
            lowestAc = "n/a";
            String[] easyHard = calcStat(students, EASY_HARD);
            easyCourse = easyHard[0];
            hardCourse = easyHard[1];
        }

        System.out.printf(GENERAL_STATISTICS, mostP, leastP,
                highestAc, lowestAc, easyCourse, hardCourse);
    }

    private String[] calcStat(ArrayList<Student> students,
                                        Function<ArrayList<Student>,
                                                long[]> function) {
        StringBuilder high = new StringBuilder();
        StringBuilder low = new StringBuilder();
        long[] course = function.apply(students);
        long max = Arrays
                         .stream(course)
                         .max()
                         .getAsLong();
        long min = Arrays
                         .stream(course)
                         .min()
                         .getAsLong();
        for (int i = 0; i < course.length; i++) {

            if (course[i] == max) {
                high.append(courses[i]).append(", ");
            } else if (course[i] == min) {
                low.append(courses[i]).append(", ");
            }
        }
        if (high.length() == 0) {
            return new String[] {"n/a", low.substring(0, low.length() - 2)};

        } else if (low.length() == 0) {
            return new String[] {high.substring(0, high.length() - 2), "n/a"};

        } else {
            return new String[] {high.substring(0, high.length() - 2),
                    low.substring(0, low.length() - 2)};
        }
    }

    private void calcStudentStat(ArrayList<Student> students,
                                 Function<ArrayList<Student>, ArrayList<Student>> function,
                                 Comparator<Student> comparator, String type) {

        System.out.println(type + "\nid    points    completed");
        ArrayList<Student> list = new ArrayList<>(function.apply(students));
        list.sort(comparator);
        for (Student s : list) {
            int index = s.getId();
            int points = 0;
            double progress = 0.0;

            switch (type) {
                case "Java":
                    points = s.getJavaScore();
                    progress = points / 600.0 * 100;
                    break;
                case "DSA":
                    points = s.getDsaScore();
                    progress = points / 400.0 * 100 + 0.0005;
                    break;
                case "Databases":
                    points = s.getDataBasesScore();
                    progress = points / 480.0 * 100 + 0.0005;
                    break;
                case "Spring":
                    points = s.getSpringScore();
                    progress = points / 550.0 * 100 + 0.0005;
                    break;
            }

            if ( points > 99) {
                System.out.println(String.format("%d %d       %.1f", index, points, progress) + "%");

            } else if (points > 9) {
                System.out.println(String.format("%d %d        %.1f", index, points, progress) + "%");

            } else {
                System.out.println(String.format("%d %d         %.1f", index, points, progress) + "%");
            }
        }
    }

    @Override
    public void go(Scanner sc, ArrayList<Student> students) {
        System.out.println(HEADER);
        generalStat(students);
        String data = sc.nextLine().trim();

        while (!data.equals("back")) {
            switch (data) {
                case "Java":
                    calcStudentStat(students, JAVA, COMPARE_JAVA, data);
                    break;
                case "DSA":
                    calcStudentStat(students, DSA, COMPARE_DSA, data);
                    break;
                case "Databases":
                    calcStudentStat(students, DB, COMPARE_DB, data);
                    break;
                case "Spring":
                    calcStudentStat(students, SPRING, COMPARE_STRING, data);
                    break;
                default:
                    System.out.println("Unknown course.");
            }
            data = sc.nextLine();
        }
        Main.command = new CommandsManager();
    }
}
