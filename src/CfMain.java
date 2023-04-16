import java.util.Scanner;

public class CfMain {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Node node = new Node();
       
        Game C4 = new Game(node);
        //MiniMax
        C4.useMiniMax();
        
        
     
     sc.close();
    }
}
