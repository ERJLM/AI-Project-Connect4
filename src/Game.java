import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        System.out.println(node.utility());
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
            double bestUCB1 = Double.MIN_VALUE;

            for(Node child : node.getChildren()){
                double ucb1 = UCB1(child);
                if(ucb1 > bestUCB1){
                    bestUCB1 = ucb1;
                    bestChild = child;
                }
            }

            node = bestChild;
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
            node.updateScore(result);
            node = node.getParent();
        }
    }

    double UCB1(Node node){
        double C = 1.0; // exploration parameter
        double exploitation = (double)node.getScore() / (double)node.getVisits();
        double exploration = C * Math.sqrt(Math.log((double)node.getParent().getVisits()) / (double)node.getVisits());
        return exploitation + exploration;
    }
}


//AlphaBeta
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
