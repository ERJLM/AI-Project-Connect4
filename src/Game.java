import java.util.Scanner;

public class Game {
    private Node node;
    private char pcToken;

    Game(Node n){
        node = n;
        if(node.getPlayer() == 'O') pcToken = 'X';
        else pcToken = 'O';
    }

   

    //MiniMax
   void useMiniMax(){
    Scanner sc = new Scanner(System.in);
    
    while(!node.terminated()){
      if(node.getTurn() == pcToken){
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
  System.out.println(node.utility());
  sc.close();
  if (node.getDepth() >= 42) System.out.println("Draw");
  else if(node.utility() >= 512){
   System.out.println("Player " + "X" + " won");
   
  }
  else if(node.utility() <= -512){
    System.out.println("Player " + "O" + " won");
   
  }
}


    //Minimax
    int miniMax(){
      int v = Integer.MIN_VALUE;
      Node best = node.copy();
      if('X' == pcToken){ 
        for(Node n : node.expand()){
          int t = maxValue(n);
          if(t > v){
            v = t;
            best = n; 
          }
        }
      }
      else {
        v = Integer.MAX_VALUE;
        for(Node n : node.expand()){
          int t = minValue(n);
          if(t < v){
            v = t;
            best = n; 
          }
        }
      }
    
      node.move(best.getLastMove());
      return best.getLastMove();
    }
    
    int maxValue(Node node){
      if(node.terminated() || node.getDepth() >= 6){
        return node.utility();
      }
    
      int v = Integer.MIN_VALUE;
    
      for(Node n : node.expand()){
        int minValue = minValue(n);
        v = Math.max(minValue, v);
      }
    
      return v;
    }
    
    int minValue(Node node){
      if(node.terminated() || node.getDepth() >= 6){
        return node.utility();
      }
    
      int v = Integer.MAX_VALUE;
    
      for(Node n : node.expand()){
        int maxValue = maxValue(n);
        v = Math.min(maxValue, v);
      }
    
      return v;
    }
    

  





}
