import java.util.ArrayDeque;
import java.util.Arrays;

public class Node{
 private Node parent;
 private int depth;
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
    }

 char[][] getState(){
    return state;
 }

 //This method calculates the evaluation of the state
 int evaluator(){
     
 }
 
 Node copy(){
    Node res = new Node();
    res.setParent(parent);
    res.setTurn(turn);
    res.setState(state);
    return res;
 }

 ArrayDeque<Node> expand(){
    ArrayDeque<Node> q = new ArrayDeque<>();
    Node n;
    for(int i = 1; i <= 7; i++){
        n = copy();
        if(n.move(i)) q.add(n);
    }
    return q;
 }

 private void setParent(Node p){
    parent = p;
 }

 private void setTurn(char p){
    turn = p;
 }

 private void setState(char[][] m){
   state = m.clone();
 }

 /*This method is responsible for doing a move on the Node
  * if it returns false it is because the move was not possible.
 */

 boolean move(int m){
    if(!(m >= 1 && m <= 7)) return false;
    for(int i = 5; i >= 0; i--){
        if(state[i][m-1] == '-'){
            state[i][m-1] = turn;
            if(turn == 'X'){ 
             turn = 'O';
            }
            else{
              turn = 'X';
            }
            return true;
        };
    }
    return false;
 }
boolean equals(Node n){
    return (Arrays.equals(getState(), n.getState()));
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
