import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class directory {
    public String directoryPath;
    ArrayList<file> files;
    ArrayList<directory> dir;
    public boolean deleted = false;


directory(){
        directoryPath="root";
        dir= new ArrayList<>();
        files= new ArrayList<>();
}
}
