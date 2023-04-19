import java.io.IOException;
import java.util.Scanner;

public class CfMain {
    public static void main(String[] args){
        String choice = null;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Type a command (help for 'help'):");
            choice = scan.nextLine();
            if(choice.equals("quit")) break;
            switch (choice) {
                case "info":
                    System.out.println("Realizado por:\n-> 202203859 - Joao Manuel Cardoso Guedes\n-> 202203858 - Eliandro Ricardo Joao Luis de Melo\n-> 202105587 - Antonio Maria Araujo Pinto dos Santos\n");
                    break;
                case "create":
                    System.out.println("Creating new connect 4 game...");
                    String choiceGame = null;
                    Scanner scanGame = new Scanner(System.in);
                    do {
                        System.out.println("\nChoose a algorithm (help for 'help'):");
                        choiceGame = scan.nextLine();
                        if(choiceGame.equals("quit")) break;
                        switch (choiceGame) {
                            case "minmax":
                                Scanner sc = new Scanner(System.in);
                                Node node = new Node();
                                Game C4 = new Game(node);
                                //MiniMax
                                C4.useMiniMax();
                                sc.close();
                                break;
                            case "mcts":
                                Scanner scmcts = new Scanner(System.in);
                                Node nodemcts = new Node();
                                Game C4mcts = new Game(nodemcts);
                                //MCTS
                                C4mcts.useMCTS();
                                scmcts.close();
                                break;
                            case "alphabeta":
                                // totas
                                break;
                            case "help":
                                System.out.println("minmax - minmax algorithm\nmcts - monte carlo tree search algorithm\nalphabeta - alphabeta algorithm");
                            default:
                                System.out.println("Invalid game Command!");
                                break;
                        }
                    } while (!choiceGame.equals("quit"));
                    scan.close();
                    System.out.println("Exiting current game...");
                    break;
                case "help":
                    System.out.println("info - group information\ncreate - create a new Board\nquit - exit the program");
                default:
                    System.out.println("Invalid project Command!");
                    break;
            }
        } while (!choice.equals("quit"));
        scan.close();
    }
}
