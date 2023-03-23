public class AmbiousPersonException extends Exception{

    public String conflictPath1;
    public String conflictPath2;

    public AmbiousPersonException(String personName, String path1, String path2){
        super(personName);
        this.conflictPath1 = path1;
        this.conflictPath2 = path2;
    }
}
