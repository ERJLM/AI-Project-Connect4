import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Node{
 private Node parent;
 private int depth;
 private int lastMove;
 private char[][] state = new char[6][7];
 private char turn;


    Node(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                state[i][j] = '-';
            }
        }
        turn = 'X';
        parent = null;
        depth = 0;
        lastMove = 0;
    }

 char[][] getState(){
    return state;
 }

 char getTurn(){
    return turn;
 }

 int getDepth(){
     return depth;
 }

 Node getParent(){
    return parent;
 }

 int getLastMove(){
    return lastMove;
 }

  //This method calculates the evaluation of the state
 int evaluator(){
     return 0;
 }

 Node copy(){
    Node res = new Node();
    res.setParent(parent);
    res.setTurn(turn);
    res.copyState(state);
    res.setDepth(depth);
    return res;
 }

 ArrayDeque<Node> expand(){
    ArrayDeque<Node> q = new ArrayDeque<>();
    Node n = new Node();
    for(int i = 1; i <= 7; i++){
        n = copy();
        //n.print();
        if(n.move(i)) q.add(n);
    }
    return q;
 }

 private void copyState(char[][] s){
    for (int i = 0; i < state.length; i++) {
        for(int j = 0; j < state[0].length; j++){
        setState(i, j, s[i][j]);
        }
    }
    
 }

 private void setParent(Node p){
    parent = p;
 }

 private void setTurn(char p){
    turn = p;
 }

 private void setDepth(int p){
    depth = p;
 }

 private void setState(int x, int y, char n){
   state[x][y] = n;
 }

 private void setLastMove(int m){
    lastMove = m;
 }


 /*This method is responsible for doing a move on the Node
  * if it returns false it is because the move was not possible.
 */

 /*  Node move(int m){
    Node n = copy();
    if(!(m >= 1 && m <= 7) || terminated()) return null;
    for(int i = 5; i >= 0; i--){
        if(state[i][m-1] == '-'){
            n.setState(i, m-1, turn);
            //if(terminated()) System.out.println("Player " + turn + " won");
            if(turn == 'X'){ 
             n.setTurn('O');
            }
            else{
              n.setTurn('X');
            }
            n.setLastMove(m);
            n.setDepth(depth + 1);;
            return n;
        }
    }
    return null;
 } */

 boolean move(int m){
    if(!(m >= 1 && m <= 7) || (terminated())) return false;
    for(int i = 5; i >= 0; i--){
        if(state[i][m-1] == '-'){
            state[i][m-1] = turn;
            //if(terminated()) System.out.println("Player " + turn + " won");
            if(turn == 'X'){ 
             turn = 'O';
            }
            else{
              turn = 'X';
            }
            lastMove = m;
            depth++;
            return true;
        }
    }
    return false;
 }

boolean equals(Node n){
    return (Arrays.equals(getState(), n.getState()));
}

boolean terminated(){
    int utility = utility();
    if(utility >= 512) {
        
        return true; 
    } else if (utility <= -512) {
       
        return true;
    } else if (getDepth() >= 42) {
        
        return true;
    }
    return false;
}

int utility(){
    int countO = 0;
    int countX = 0;
    int consecutiveO = 0;
    int consecutiveX = 0;
    int value = 0;

    //check horizontals
    for(int rows = 0; rows < 6; rows++){
      countO = 0; countX = 0; consecutiveO = 0; consecutiveX = 0;
      for(int cols = 0; cols < 7; cols++){
        if(state[rows][cols] == 'O'){
             countO++;
             consecutiveO++;
             consecutiveX = 0;
             if(consecutiveO == 4) return -512;
        }
        else if(state[rows][cols] == 'X'){
            countX++;
            consecutiveX++;
            consecutiveO = 0;
            if(consecutiveX == 4) return 512;
        }
        else{
            consecutiveO = 0;
            consecutiveX = 0;
        }
      }
      value += checkUtility(countX, countO, consecutiveO, consecutiveX);
      if( Math.abs(value) >= 512) return value;
    }
  
    //check verticals
    for(int cols = 0; cols < 7; cols++){
      countO = 0; countX = 0; consecutiveO = 0; consecutiveX = 0;
      for(int rows = 0; rows < 6; rows++){
        if(state[rows][cols] == 'O'){
             countO++;
             consecutiveO++;
             consecutiveX = 0;
             if(consecutiveO == 4) return -512;
        }
        else if(state[rows][cols] == 'X'){
            countX++;
            consecutiveX++;
            consecutiveO = 0;
            if(consecutiveX == 4) return 512;
        }
        else{
            consecutiveO = 0;
            consecutiveX = 0;
        }
      }
      value += checkUtility(countX, countO, consecutiveO, consecutiveX);
      if( Math.abs(value) >= 512) return value;
    }

    //check normal diagonals
    for (int i = 0; i <= 2; i++) {
        for (int j = 0; j <= 3; j++) {
            countO = 0; countX = 0; consecutiveO = 0; consecutiveX = 0;
            for (int k = 0; k <= 3; k++) {
                if (state[i+k][j+k] == 'O'){
                     countO++;
                     consecutiveO++;
                     consecutiveX = 0;
                     if(consecutiveO == 4) return -512;
                }
                else if (state[i+k][j+k] == 'X'){
                     countX++;
                     consecutiveX++;
                     consecutiveO = 0;
                     if(consecutiveX == 4) return 512;
                }
                else{
                    consecutiveO = 0;
                    consecutiveX = 0;
                }
            }
            value += checkUtility(countX, countO, consecutiveO, consecutiveX);
            if( Math.abs(value) >= 512) return value;
        }
    }

    //check reverse diagonals
    for (int i = 0; i <= 2; i++) {
        for (int j = 6; j >= 3; j--) {
            countO = 0; countX = 0; consecutiveO = 0; consecutiveX = 0;
            for (int k = 0; k <= 3; k++) {
                if (state[i+k][j-k] == 'O'){
                     countO++;
                     consecutiveO++;
                     consecutiveX = 0;
                     if(consecutiveO == 4) return -512;
                }
                else if (state[i+k][j-k] == 'X'){
                     countX++;
                     consecutiveX++;
                     consecutiveO = 0;
                     if(consecutiveX == 4) return 512;
                }
                else{
                    consecutiveO = 0;
                    consecutiveX = 0;
                }
                
            }
            value += checkUtility(countX, countO, consecutiveO, consecutiveX);
            if( Math.abs(value) >= 512) return value;
        }
    }



    return value;
}



private int checkUtility(int countX, int countO, int consecutiveO, int consecutiveX){
    int value = 0;
    if(countO == 3 && countX == 0) value -= 50;
    else if(countO == 2 && countX == 0) value -= 10;
    else if(countO == 1 && countX == 0) value -= 1;
    else if(countO == 0 && countX == 1) value += 1;
    else if(countO == 0 && countX == 2) value += 10;
    else if(countO == 0 && countX == 3) value += 50;
    else if(consecutiveO >= 4) return -512;
    else if(consecutiveX >= 4) return 512;

    return value;
  }


void print(){
    for(int i = 0; i < 6; i++){
        for(int j = 0; j < 7; j++){
            System.out.print(state[i][j]);
        }
        System.out.println("");
    }
    for(int i = 1; i < 8; i++)
       System.out.print(i);
    System.out.println("");
    System.out.println("");
    System.out.println("It's now " + turn + "'s turn.");
    System.out.println("Make a move by choosing your coordinates to play (1 to 7).");

}

@Override
public String toString(){
    String s = "";
    for(int i = 0; i < 6; i++){
        for(int j = 0; j < 7; j++){
            s += state[i][j];
        }
    }
    return s;
}

}
