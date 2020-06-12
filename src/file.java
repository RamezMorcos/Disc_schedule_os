import com.sun.java.accessibility.util.AccessibilityListenerList;

import java.util.ArrayList;

public class file
{
    public int n;
    public String filePath;
    public ArrayList <Integer>allocatedBlocks;
   public boolean deleted;

    file(){
        allocatedBlocks=new ArrayList<>();
        n=0;
        filePath="";
        deleted=false;
    }

    file(int siz, String s){
        n=siz;
        filePath=s;
        deleted=false;
        allocatedBlocks=new ArrayList<>();

    }


}







/*
 public ArrayList<Integer> delete_file(directory path, String s){
        ArrayList<Integer>located=new ArrayList<>();
        for(int i=0;i<path.file.size();i++){
            if(file.get(i).filePath.equals(s)){
                for(int j=0;j<file.get(i).allocatedBlocks.size();j++){
                    located.add(file.get(i).allocatedBlocks.get(j));
                }
                path.file.remove(i);
            }
        }
        return located;
    }
 */