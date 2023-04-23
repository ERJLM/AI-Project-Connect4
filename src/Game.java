import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private Node node;
    private char pcToken;

    Game(Node n){
        node = n;
        pcToken = 'X';
        
    }
    
    void useAlphaBeta(){
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
      
      node.print();
      }
  
  }
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
      node.setDepth(0);
      int v = Integer.MIN_VALUE;
      int c = 0;
      
       
        for(Node n : node.expand()){
          int t = minValue(n);
         
          if(t > v){
            v = t;
            c = n.getLastMove(); 
          }
        }
      
      
        
      node.move(c);
      
      
      return c;
    }
    
    int maxValue(Node node){
      if(node.terminated() || node.getDepth() >= 6){
        
        
        int u = node.utility();
       
        
        return u;
      }
    
      int v = Integer.MIN_VALUE;
    
      for(Node n : node.expand()){
        
        v = Math.max(minValue(n), v);
      }
    
      return v;
    }
    
    int minValue(Node node){
     
      if(node.terminated() || node.getDepth() >= 6){
        int u = node.utility();
        
        
        return u;
      }
    
      int v = Integer.MAX_VALUE;
    
      for(Node n : node.expand()){
        
        v = Math.min(maxValue(n), v);
      }
    
      return v;
    }
    

  //AlphaBeta
  int alphaBeta(){
    node.setDepth(0);
    int v = Integer.MIN_VALUE;
      int c = 0;
      
      
        for(Node n : node.expand()){
          int t = minValue(n,Integer.MIN_VALUE, Integer.MAX_VALUE);
         
          if(t > v){
            v = t;
            c = n.getLastMove(); 
          }
        }
      
      
        
      node.move(c);
      //System.out.println(c);
      
      return c;
   }
 
 int maxValue(Node node, int alfa, int beta){
    if(node.terminated() || node.getDepth() >= 7) return node.utility();
    
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
     if(node.terminated() || node.getDepth() >= 7){
       return node.utility();
     }
     
     int v = Integer.MAX_VALUE;
     
     for(Node n : node.expand()){
         int maxValue = maxValue(n, alfa, beta);
         v = Math.min(maxValue, v);
       if(v <= alfa) return v;
       beta = Integer.min(beta, v);
     }

    return v;
 }

    void useMCTS(){
        Scanner sc = new Scanner(System.in);
        while(!node.terminated()){
            if(node.getTurn() == pcToken){
                int move = MCTS();
                System.out.println(move);
                node.move(move);
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
        else if(node.utility() >= 512){
            System.out.println("Player " + "X" + " won");
            System.exit(0);
        }
        else if(node.utility() <= -512){
            System.out.println("Player " + "O" + " won");
            System.exit(0);
        }
    }

    //MCTS
    int MCTS(){
        Node rootNode = node.copy();
        // chooses three possible solutions, I tried all but it keeps looping :(
        int times=3;
        while(times>0){
            times--;
            Node selectedNode = select(rootNode);
            Node expandedNode = expand(selectedNode);
            int result = simulate(expandedNode);
            backpropagate(expandedNode, result);
            if(rootNode.getChildren().size()<1) return result;
        }

        int bestMove = Integer.MIN_VALUE;
        double bestScore = Double.MIN_VALUE;
        for(Node child : rootNode.getChildren()){
            double score = (double)child.getScore() / (double)child.getVisits();
            if(score > bestScore){
                bestScore = score;
                bestMove = child.getLastMove();
            }
        }

        return bestMove;
    }

    Node select(Node node){
        while(!node.terminated() && !node.getChildren().isEmpty()){
            Node bestChild = null;
        }

        return node;
    }

    Node expand(Node node){
        List<Integer> moves = node.getLegalMoves();
        Random r = new Random();
        int index = r.nextInt(moves.size());
        int move = moves.get(index);
        Node child = node.childNode(move);
        return child;
    }

    int simulate(Node node){
        Node state = node.copy();
        while(!state.terminated()){
            List<Integer> moves = state.getLegalMoves();
            Random r = new Random();
            int index = r.nextInt(moves.size());
            int move = moves.get(index);
            state.move(move);
            break;
        }

        return state.utility();
    }

    void backpropagate(Node node, int result){
        while(node != null){
            node.setVisits(node.getVisits() + 1);
            node.setScore(node.getScore() + result);
            node = node.getParent();
        }
    }

}


  
