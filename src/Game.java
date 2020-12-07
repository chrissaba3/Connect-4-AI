import java.util.Scanner;

public class Game {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to go first? (y/n): ");
        String inputResponse = sc.nextLine();
        if (inputResponse.equalsIgnoreCase("y")) {
            System.out.println("\nHow long should the computer think about its moves (in secs)?: ");
            long seconds = sc.nextInt();
            Board b = new Board(seconds);
            gameLoop(b);

        }
        else if (inputResponse.equalsIgnoreCase("n")) {
            System.out.print("\nHow long should the computer think about its moves (in secs)?: ");
            int seconds = sc.nextInt();
            Board b = new Board(seconds);
            b.makeMove(); //Computer makes first move, then user can make move as shown in gameLoop
            gameLoop(b);

        }else{
            System.err.println("invalid response, please use y or n");
            System.exit(-1);
        }
    }

    //main game loop, parses input string into x and y position for move placement
    private static void gameLoop(Board b) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            while (true) { //error checking
                System.out.println("Choose your move, enter format <letter><number>: ");
                String move = sc.nextLine();
                String[] split = move.split("");
                int x=1,y= 1;
                try {
                    x = charPosition(split[0]);     //A-H
                    y = Integer.parseInt(split[1]); //1-8
                }catch(ArrayIndexOutOfBoundsException e){
                    System.err.println("Incorrect input, Format must be <letter><number> AND letter must be A-H, number must be 1-8");
                    System.exit(1);
                }
                if (b.getPlayerMove(x, y)) {
                    b.gameOver(x, y - 1, 2);
                    break;
                }
            }
            b.makeMove(); //Cpu move

        }
    }

    //returns the position based on the character
    private static int charPosition(String s) {
        String str = "ABCDEFGH";
        String[] c = str.split("");
        for (int i = 0; i < c.length; i++) {
            if (s.toUpperCase().equals(c[i]))
                return i;
        }
        return -1;
    }

}