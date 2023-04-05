import java.util.Scanner;

public class Game {
    private Node node;
    private char myToken;

    Game(Node n, char playerT){
        node = n;
        if(playerT == 'X') myToken = 'O';
        else myToken = 'X';
    }

    //MiniMax
   void useMiniMax(){
    Scanner sc = new Scanner(System.in);
    
    while(!node.terminated()){
      if(node.getTurn() == myToken){
      miniMax();
      node.print();
      }
      else{
      System.out.println("Choose a move: ");
      int m = sc.nextInt();
      node.move(m);
      node.print();
      }
  
  }
  sc.close();
  if (node.getDepth() >= 42) System.out.println("Draw");
  else if(node.utility() >= 512) System.out.println("Player X won");
  else if(node.utility() <= -512) System.out.println("Player O won");
}


    //Minimax
    int miniMax(){
     int v = Integer.MIN_VALUE;
     Node best = node.copy();
     if(myToken == 'O'){ 
      v = Integer.MAX_VALUE;
    
     for(Node n : node.expand()){
      int t;
     t = minValue(n);
     if(t < v){
       v = t;
       best = n; 
     }
    }
  }
  else{
    for(Node n : node.expand()){
      int t = maxValue(n);;
     if(t > v){
       v = t;
       best = n; 
     }
    }
  }
     node.move(best.getLastMove());
     return best.getLastMove();
    }
  
  int maxValue(Node node){
     if(node.terminated() || node.getDepth() >= 7) return node.utility();
     
     int v = Integer.MIN_VALUE;
     
     for(Node n : node.expand()){
      if (n.getDepth() <= 7) {
        int minValue = minValue(n);
        v = Math.max(minValue, v);
    }
         
     }
     
     return v;
  }
  
  int minValue(Node node){
      if(node.terminated() || node.getDepth() >= 7) return node.utility();
      
      int v = Integer.MAX_VALUE;
      
      for(Node n : node.expand()){
        if (n.getDepth() <= 7) {
          int maxValue = maxValue(n);
          v = Math.min(maxValue, v);
      }
      }
      
      return v;
  }





}
