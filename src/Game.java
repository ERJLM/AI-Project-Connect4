import java.util.Scanner;

public class Game {
    private Node node;
    private char pcToken;

    Game(Node n){
        node = n;
        if(node.getPlayer() == 'O') pcToken = 'X';
        else pcToken = 'O';
    }

    void useAlfaBeta(){
      Scanner sc = new Scanner(System.in);
      
      while(!node.terminated()){
        if(node.getTurn() == pcToken){
        alphaBeta();
        node.print();
        }
        else{
        System.out.println("Choose a move: ");
        int m = sc.nextInt();
        node.move(m);
        //alphaBeta();
        node.print();
        }
    
    }
    System.out.println(node.utility());
    sc.close();
    if (node.getDepth() >= 42) System.out.println("Draw");
    else if(node.utility() >= 512) System.out.println("Player X won");
    else if(node.utility() <= -512) System.out.println("Player O won");
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
      //miniMax();
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
        node.print(); 
        for(Node n : node.expand()){
          int t = maxValue(n);
          n.print();
          System.out.println(n.utility());
          if(t > v){
            v = t;
            best = n; 
          }
        }
      }
      else {
        v = Integer.MAX_VALUE;
        node.print();
        for(Node n : node.expand()){
          int t = minValue(n);
          n.print();
          System.out.println(n.utility());
          if(t < v){
            v = t;
            best = n; 
          }
        }
      }
    
      node.move(best.getLastMove());
      System.out.println(v);
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
    

  //AlphaBeta
  int alphaBeta(){
    int v = Integer.MIN_VALUE;
    Node best = node.copy();
  
    if('X' == pcToken){ 
      node.print(); 
      for(Node n : node.expand()){
        int t = maxValue(n, Integer.MAX_VALUE, Integer.MIN_VALUE);
        n.print();
        System.out.println(n.utility());
        if(t > v){
          v = t;
          best = n; 
        }
      }
    }
    else {
      v = Integer.MAX_VALUE;
      node.print();
      for(Node n : node.expand()){
        int t = minValue(n, Integer.MAX_VALUE, Integer.MIN_VALUE);
        n.print();
        System.out.println(n.utility());
        if(t < v){
          v = t;
          best = n; 
        }
      }
    }
  
    node.move(best.getLastMove());
    System.out.println(v);
    return best.getLastMove();
   }
 
 int maxValue(Node node, int alfa, int beta){
    if(node.terminated() || node.getDepth() >= 12) return node.utility();
    
    int v = Integer.MIN_VALUE;
    
    for(Node n : node.expand()){
     
       int minValue = minValue(n, alfa, beta);
       v = Math.max(minValue, v);
      if (v >= beta) return v;
      alfa = Integer.max(alfa, v);
    }
    return v;
 }
 
 int minValue(Node node, int alfa, int beta){
     if(node.terminated() || node.getDepth() >= 12) return node.utility();
     
     int v = Integer.MAX_VALUE;
     
     for(Node n : node.expand()){
         int maxValue = maxValue(n, alfa, beta);
         v = Math.min(maxValue, v);
       if(v <= alfa) return v;
       beta = Integer.min(beta, v);
     }

    return v;
 }





}
