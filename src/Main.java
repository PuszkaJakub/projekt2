import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void save(String path) {
        try {
            List<Person> people = new ArrayList<>();
            Person janusz = new Person("Janusz",
                    LocalDate.of(1975, 1, 1));
            people.add(janusz);

            Person grazyna = new Person("Grażyna",
                    LocalDate.of(1975, 1, 1));
            people.add(grazyna);

            Person krystyna = new Person("Krystyna",
                    LocalDate.of(1975, 1, 1));
            people.add(krystyna);

            Person seba = new Person("Seba",
                    LocalDate.of(1990, 1, 1),
                    janusz, grazyna);
            people.add(seba);

            Person edzio = new Person("Edzio",
                    LocalDate.of(1975, 1, 1));
            people.add(edzio);

            Person karyna = new Person("Karyna",
                    LocalDate.of(1995, 1, 1),
                    edzio, krystyna);
            people.add(karyna);

            Person brajan = new Person("Brajan",
                    LocalDate.of(2011, 1, 1),
                    seba, karyna);
            people.add(brajan);

            System.out.println(brajan);

            try {
                FileOutputStream stream = new FileOutputStream(path);
                ObjectOutputStream objectStream = new ObjectOutputStream(stream);
                objectStream.writeObject(people);
                objectStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IncestException e) {
            e.printStackTrace();
        }

    }

    public static void load(String path) {
        List<Person> people;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            people = (List<Person>) in.readObject();

            System.out.println(people);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> paths = new ArrayList<>();
            paths.add("test/test_rodzice/p1.txt");
            paths.add("test/test_rodzice/p2.txt");
            paths.add("test/test_rodzice/p3.txt");
            paths.add("test/test_rodzice/p4.txt");
            paths.add("test/test_rodzice/p5.txt");
            paths.add("test/test_rodzice/p6.txt");
            paths.add("test/test_rodzice/p7.txt");
            paths.add("test/test_rodzice/p8.txt");
            paths.add("test/test_rodzice/p9.txt");
            paths.add("test/test_rodzice/p10.txt");
            paths.add("test/test_rodzice/p11.txt");
            List<Person> people = Person.getPeople(paths);
            for (Person person : people) {
                System.out.println(person);
            }
        } catch (FileNotFoundException | IncestException e) {
            e.printStackTrace();
        } catch (AmbiousPersonException e) {
            System.out.println(e.conflictPath1);
            System.out.println(e.conflictPath2);
        }
    }
}