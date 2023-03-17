public class Game {
    private Node node;
    private char playerToken;
    private char myToken; 

    Game(Node n, char playerT){
        node = n;
        playerToken = playerT;
        if(playerT == 'X') myToken = 'O';
        else myToken = 'X';
    }
    



//This method verifies if the game ended.
boolean terminationTest(){
  
  return false;
}

/*This method verifies if you won, lost or if it was a draw.
 (Method to use after the game has ended)
*/
int utility(){
   
    return 0;
  }

}

