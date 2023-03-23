import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Serializable {
    private String name;
    private LocalDate birth, death;
    private Person parents[] = new Person[2];

    public Person(String name, LocalDate birth) {
        this(name, birth, null);
    }

    public Person(String name, LocalDate birth, LocalDate death) {
        this.name = name;
        this.birth = birth;
        this.death = death;
        try {
            if (birth.isAfter(death)) {
                throw new NegativeLifespanException(birth, death, "Possible time-space loophole.");
            }
        } catch (NullPointerException e) {}
    }

    public Person(String name, LocalDate birth, LocalDate death, Person parent1, Person parent2) throws IncestException {
        this(name, birth, death);
        parents[0] = parent1;
        parents[1] = parent2;

        checkForIncest();
    }

    public Person(String name, LocalDate birth, Person parent1, Person parent2) throws IncestException {
        this(name, birth, null, parent1, parent2);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birth=" + birth +
                ", death=" + death +
                ", parents=" + Arrays.toString(parents) +
                '}';
    }

    void checkForIncest() throws IncestException {
        if(parents[0] == null || parents[1] == null)
            return;
        for(var leftSideParent : parents[0].parents) {
            if (leftSideParent == null) continue;
            for (var rightSideParent : parents[1].parents) {
                if (rightSideParent == null) continue;
                if (leftSideParent == rightSideParent)
                    throw new IncestException(leftSideParent, this);
            }
        }
    }

    static List<TemporaryPerson> people = new ArrayList<>();

    public static Person getPersonFromFile(String path) throws FileNotFoundException, AmbiousPersonException, IncestException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        String nameAndLastName = sc.nextLine();
        LocalDate birthdayDate = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate deathDate = null;
        Person firstPerson = null, secondPerson = null;

        if(sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!"Rodzice:".equals(line)) {
                deathDate = LocalDate.parse(line, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            }
            if(sc.hasNextLine()) {
                sc.nextLine();
            }
            if (sc.hasNextLine()) {
                String firstPersonName = sc.nextLine();
                firstPerson = findPerson(firstPersonName);
                if(sc.hasNextLine()) {
                    String secondPersonName = sc.nextLine();
                    if (secondPersonName != null) {
                        secondPerson = findPerson(secondPersonName);
                    }
                }
            }
        }
        for(var person : people){
            if(person.person.name.compareTo(nameAndLastName)==0){
                throw new AmbiousPersonException(person.person.name,path,person.path);
            }
        }
        Person person = new Person(nameAndLastName, birthdayDate, deathDate, firstPerson, secondPerson);
        people.add(new TemporaryPerson(person, path));
        return person;
    }

    private static Person findPerson(String name) {
        for (TemporaryPerson person : people) {
            if (person.person.name.equals(name)) {
                return person.person;
            }
        }
        return null;
    }

    public static List<Person> getPeople(List<String> paths) throws FileNotFoundException, AmbiousPersonException, IncestException {
        List<Person> people = new ArrayList<>();
        for (String path : paths) {
            people.add(getPersonFromFile(path));
        }
        return people;
    }
}