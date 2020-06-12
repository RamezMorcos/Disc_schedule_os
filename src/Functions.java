import java.util.ArrayList;
import java.util.Scanner;

public class Functions {
    int disc_size;
    int extend_size;
    directory root;
    ArrayList<disk>blocks=new ArrayList<>();

Functions(int disk_siz,int ex_siz){
    extend_size=ex_siz;
    disc_size=disk_siz;
    for (int i = 0; i <disc_size/extend_size ; i++) {
        disk d=new disk(extend_size);
        blocks.add(d);
    }
    root=new directory();

}

    public boolean CreateFile( String f, directory d,int file_siz) {
        String[] parts = f.split("/");
        directory cur=new directory();
        String path1 = "";
        for (int i = 0; i < parts.length - 1; i++) {
            path1 += parts[i];
            if(i<parts.length - 2){
            path1+="/";}
        }
        boolean x = search(d,path1);
        if (x == false) {
            System.out.println("This path is wrong");
            return false;
        }
        int counter = 0;
        for (int i = 0; i < disc_size/extend_size; i++) {
            if (blocks.get(i).check == false) {
                counter++;
            }
        }
        if (file_siz > counter*extend_size) {
            System.out.println("there is no space enough");
            return false;
        } else {
            cur=d;
            for (int j = 0; j < parts.length - 2; j++) {
                for (int i = 0; i < cur.dir.size(); i++) {
                    if (cur.dir.get(i).directoryPath.equals(parts[j + 1]) && cur.dir.get(i).deleted == false) {
                        cur = cur.dir.get(i);
                        break;
                    } else {
                        return false;

                    }
                }
            }
//            System.out.println("size = " +cur.files.size());

                for (int i = 0; i < cur.files.size(); i++) {
                System.out.println(" = "+parts[parts.length-1]);

                if (cur.files.get(i).filePath.equals(parts[parts.length-1])) {
                    System.out.println("there is a directory has the same name");

                    return false;
                }

            }
            file fil = new file();
            int count =0;
            int s=file_siz;
            while(count<blocks.size()&&s>0){
                if(blocks.get(count).check==false){
                    blocks.get(count).check=true;
                    fil.allocatedBlocks.add(count);
                }

                count++;
                s-=extend_size;
            }
            fil.filePath = parts[parts.length - 1];
            fil.n = file_siz;
            cur.files.add(fil);
        }
            return true;
    }

    public boolean createfolder(String path,directory d)
    {
        String[] parts = path.split("/");
        directory cur=new directory();
        String path1="";
        for(int i=0;i<parts.length-1;i++){
            path1+=parts[i];
            path1+="/";
        }
        boolean x=search(d,path1);
        if(x==false){
            System.out.println("This path is wrong");
            return false;
        }
        else{

            cur = d;
            for (int j = 0; j < parts.length-2; j++) {
                for (int i = 0; i < cur.dir.size(); i++) {
                    if (cur.dir.get(i).directoryPath.equals(parts[j + 1]) && cur.dir.get(i).deleted == false) {
                        cur = cur.dir.get(i);
                        break;
                    } else {
                        return false;
                    }
                }
            }

            for(int i=0;i<cur.dir.size();i++){
                    if(cur.dir.get(i).directoryPath.equals(parts[parts.length-1])){
                        System.out.println("there is a directory has the same name");
                        return false;
                    }
            }
            directory dir=new directory();
                dir.directoryPath=parts[parts.length-1];
            cur.dir.add(dir);

        }
        return true;
    }

    public boolean deletefolder(String path,directory d){
        String[] parts = path.split("/");
        directory cur=new directory();
        boolean x=search(d,path);
        if(x==false){
            System.out.println("This path is wrong");
            return false;
        }
        else {
             cur = d;
            for (int j = 0; j < parts.length-1; j++) {
                for (int i = 0; i < cur.dir.size(); i++) {
                    if (cur.dir.get(i).directoryPath.equals(parts[j + 1]) && cur.dir.get(i).deleted == false) {
                        cur = cur.dir.get(i);
                        break;
                    } else {
                        return false;
                    }
                }}
                cur.deleted=true;

        }


        return true;
    }

   public boolean deletefile(String path,directory d){
       String[] parts = path.split("/");
       directory cur=new directory();

       boolean x = search(d, path);
       if (x == false) {
           System.out.println("This path is wrong");
           return false;
       }
       else {
           cur=d;
           for (int j = 0; j < parts.length - 1; j++) {
               for (int i = 0; i < cur.dir.size(); i++) {
                   if (cur.dir.get(i).directoryPath.equals(parts[j + 1]) && cur.dir.get(i).deleted == false) {
                       cur = cur.dir.get(i);
                       break;
                   } else {
                       return false;
                   }
               }
           }
           for (int i = 0; i < cur.files.size(); i++) {
               if (cur.files.get(i).equals(parts[parts.length - 1])) {
                   cur.files.get(i).deleted=true;
                   for(int j=0;j<cur.files.get(i).allocatedBlocks.size();j++){
                       blocks.get(cur.files.get(i).allocatedBlocks.get(j)).check=false;
                       cur.files.get(i).allocatedBlocks.clear();

                   }
                   cur.files.get(i).allocatedBlocks.clear();
                   break;
               }

           }
       }

   return true;
    }

    public boolean search(directory d,String path){
        String[] split=path.split("/");
        if(!split[0].equals("root")){return false;};
        directory cur = d;
        for (int j = 0; j < split.length-1; j++) {
            for(int i=0;i<cur.dir.size();i++){
                if(cur.dir.get(i).directoryPath.equals(split[j+1])){
                    cur = cur.dir.get(i);
                    break;
                }
                else {return false;}
            }
        }


        return true;


    }

    public void DisplayDiskStructure(directory d){

        directory cur=new directory();
        cur = d;
        System.out.println(cur.directoryPath);
        if(cur.files.size()!=0) {
            for (int i=0;i<cur.files.size();i++){
                System.out.println("-"+cur.files.get(i).filePath);
            }
        }
        if(cur.dir.size()==0){
            return;
        }
        else {
            for (int i = 0; i < cur.dir.size(); i++) {
                DisplayDiskStructure(cur.dir.get(i));
            }
        }
    }

    public void DisplayDiskStatus(){
        int count =0;
        for(int i=0;i<blocks.size();i++){
            if(blocks.get(i).check==true){
                System.out.println("the blocks allocated "+i);
                count++;
            }
            if(blocks.get(i).check==false){
                System.out.println("the blocks which deallocated is  "+i);
            }
        }
        System.out.println("The total allocated space used is  "+count*extend_size);
        System.out.println("The free space is  "+(disc_size-count*extend_size));
    }

    public static void main(String[] args) {

        int disk_size, extend_size;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter disk size");
        disk_size = s.nextInt();
        System.out.println("Enter extend size");
        extend_size = s.nextInt();
        Functions fun = new Functions(disk_size, extend_size);

        while (true) {
        System.out.println("Enter the path");
        String path = s.next();

            String[] arr = path.split("/");
            if (arr[0].equals("CreateFile")) {

                String path_of_file = "";

                for (int i = 1; i < arr.length; i++) {
                    path_of_file += arr[i];
                    path_of_file += "/";
                }
                System.out.println("Enter file size ");
                int SizeOfFile = s.nextInt();

                boolean x = fun.CreateFile(path_of_file, fun.root, SizeOfFile);
                if (x == true) {
                    System.out.println("file is created successfully");
                } else {
                    System.out.println("failed to create file");
                }
            }
            else if (arr[0].equals("CreateFolder")) {

                String path_of_file = "";

                for (int i = 1; i < arr.length; i++) {
                    path_of_file += arr[i];
                    path_of_file += "/";
                }

                boolean x = fun.createfolder(path_of_file, fun.root);
                if (x == true) {
                    System.out.println("folder is created successfully");
                } else {
                    System.out.println("failed to create folder");
                }

            } else if (arr[0].equals("DeleteFolder")) {

                String path_of_file = "";

                for (int i = 1; i < arr.length; i++) {
                    path_of_file += arr[i];
                    path_of_file += "/";
                }

                boolean x = fun.deletefile(path_of_file, fun.root);
                if (x == true) {
                    System.out.println("folder is deleted successfully");
                } else {
                    System.out.println("failed to delete folder");
                }

            } else if (arr[0].equals("DeleteFile")) {

                String path_of_file = "";

                for (int i = 1; i < arr.length; i++) {
                    path_of_file += arr[i];
                    path_of_file += "/";
                }

                boolean x = fun.deletefile(path_of_file, fun.root);
                if (x == true) {
                    System.out.println("file is deleted successfully");
                } else {
                    System.out.println("failed to delete file");
                }
            }
             else if (path.equals("DisplayDiskStatus")) {
                fun.DisplayDiskStatus();
            }
            else if (path.equals("DisplayDiskStructure")) {
                fun.DisplayDiskStructure(fun.root);
            } else {
                System.out.println("WRONGGGGGG");
            }
            System.out.println("1- if you want to exit enter zero ");
            System.out.println("2- if you want to continue enter your cmd ");
            int n=s.nextInt();
            if(n==0){
                break;
            }

        }


    }

}
