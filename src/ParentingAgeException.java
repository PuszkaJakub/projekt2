public class ParentingAgeException extends Exception {
    public int age;

    public ParentingAgeException(){
        super("Dziwny wiek rodzica");
    }
}
