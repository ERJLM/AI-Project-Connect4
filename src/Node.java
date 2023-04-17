import java.util.*;

public class Node{
 private Node parent;
 private int depth;
 private char starter;
 private char player;
 private int lastMove;
 private char[][] state = new char[6][7];
 private char turn;
 private ArrayList<Node> children;
 private double score;
 private int visits;

    Node(char[][] state, int depth, int lastMove) {
        this.state = state;
        this.depth = depth;
        this.lastMove = lastMove;
        this.score = 0;
        this.visits = 0;
        this.children = new ArrayList<>();
    }
    Node(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                state[i][j] = '-';
            }
        }
        Random rand = new Random();
        int upper = 2;
        int t = rand.nextInt(upper);
        if(t == 0)player = 'X';
        else player = 'O';
        turn = 'X';
        starter = turn;
        parent = null;
        depth = 0;
        lastMove = 0;
        score = 0;
        visits = 0;
        children = new ArrayList<>();
    }


 char[][] getState(){
    return state;
 }

 char getTurn(){
    return turn;
 }

 char getPlayer(){
    return player;
 }

 char getStarter(){
    return starter;
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

    public List<Integer> getLegalMoves() {
        List<Integer> legalMoves = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            if (state[0][i] == '-') {
                legalMoves.add(i);
            }
        }
        return legalMoves;
    }

    public List<Node> getChildren() {
        if (children == null) {
            children = new ArrayList<Node>();
            List<Integer> legalMoves = getLegalMoves();
            for (int move : legalMoves) {
                children.add(childNode(move));
            }
        }
        return children;
    }

    public Node childNode(int move) {
        char[][] newState = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                newState[i][j] = state[i][j];
            }
        }
        int row = 5;
        while (row >= 0) {
            if (newState[row][move] == ' ') {
                newState[row][move] = (player == 1) ? 'X' : 'O';
                break;
            }
            row--;
        }
        Node child = new Node(newState, 3 - player, move);
        return child;
    }

    public void updateScore(double result) {
        visits++;
        score += result;
    }

    public double getScore() {
        return score;
    }

    public int getVisits() {
        return visits;
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

 ArrayList<Node> expand(){
    ArrayList<Node> q = new ArrayList<>();
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
                       
int utility() {
    int countO = 0;
    int countX = 0;
    int value = 0;
    int check = 0;
    // check horizontals
    for (int rows = 0; rows < 6; rows++) {
        for (int cols = 0; cols <= 3; cols++) {
            countO = 0; countX = 0;
            for (int k = 0; k < 4; k++) {
                if (state[rows][cols + k] == 'O') {
                    countO++;
                } else if (state[rows][cols + k] == 'X') {
                    countX++;
                }
            }
            value += checkUtility(countX, countO);
            if (countX == 4) return 512;
            else if(countO == 4) return -512;
            if (Math.abs(value) >= 512) return value;
            check++;
        }
    }

    // check verticals
    for (int cols = 0; cols < 7; cols++) {
        for (int rows = 0; rows <= 2; rows++) {
            countO = 0; countX = 0;
            for (int k = 0; k < 4; k++) {
                if (state[rows + k][cols] == 'O') {
                    countO++;
                } else if (state[rows + k][cols] == 'X') {
                    countX++;
                }
            }
            value += checkUtility(countX, countO);
            if (countX == 4) return 512;
            else if(countO == 4) return -512;
            if (Math.abs(value) >= 512) return value;
            check++;
        }
    }

    // check normal diagonals
    for (int i = 0; i <= 2; i++) {
        for (int j = 0; j <= 3; j++) {
            countO = 0; countX = 0;
            for (int k = 0; k < 4; k++) {
                if (state[i + k][j + k] == 'O') {
                    countO++;
                } else if (state[i + k][j + k] == 'X') {
                    countX++;
                }
            }
            value += checkUtility(countX, countO);
            if (countX == 4) return 512;
            else if(countO == 4) return -512;
            if (Math.abs(value) >= 512) return value;
            check++;
        }
    }

    // check reverse diagonals
    for (int i = 0; i <= 2; i++) {
        for (int j = 3; j <= 6; j++) {
            countO = 0; countX = 0;
            for (int k = 0; k < 4; k++) {
                if (state[i + k][j - k] == 'O') {
                    countO++;
                } else if (state[i + k][j - k] == 'X') {
                    countX++;
                }
            }
            value += checkUtility(countX, countO);
            if (countX == 4) return 512;
            else if(countO == 4) return -512;
            check++;
        }
    }

    return value;
}



  private int checkUtility(int countX, int countO){
    int value = 0;
    if(countO == 3 && countX == 0) return -50;
    else if(countO == 2 && countX == 0) return -10;
    else if(countO == 1 && countX == 0) return -1;
    else if(countO == 0 && countX == 1) return 1;
    else if(countO == 0 && countX == 2) return 10;
    else if(countO == 0 && countX == 3) return 50;
    else if(countO == 0 && countX == 4) return 512;
    else if(countO == 4 && countX == 0) return -512;

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
