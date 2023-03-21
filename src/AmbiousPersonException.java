public class AmbiousPersonException extends Exception{

    public String conflictName1;
    public String conflictName2;

    public AmbiousPersonException(String personName, String path1, String path2){
        super(personName);
        this.conflictName1 = path1;
        this.conflictName2 = path2;
    }
}
