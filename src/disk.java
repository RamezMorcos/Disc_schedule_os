import java.util.ArrayList;

public class disk {
    public int n;
  public   ArrayList<Integer>extend=new ArrayList<>();
   public boolean check;

   disk(int siz){
       n=siz;
       for(int i=0;i<n;i++){
           extend.add(i);
       }
       check=false;
   }


}
