import java.util.Scanner;

public class CfMain {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Node node = new Node();
       
        Game C4 = new Game(node);
        //MiniMax
        C4.useMiniMax();
        
        
        //Node A = node.copy();
       /*  node.move(4);
        node.print();
        System.out.println(node.utility());
        node.move(3);
        node.print();
        System.out.println(node.utility());
        node.move(5);
        node.print();
        System.out.println(node.utility());
        node.move(3);
        node.print();
        System.out.println(node.utility());
        node.move(6);
        node.print();
        System.out.println(node.utility());
        node.move();
        node.print();
        System.out.println(node.utility());
        */
     sc.close();
    }
}
