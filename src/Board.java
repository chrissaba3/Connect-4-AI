import java.util.Arrays;
import java.util.Objects;

public class Board {
    private int[][] connect4Board;
    private static final int SIZE = 8;
    private static final int MAXDEPTH = 5;
    private long time;
    private long startTime;

    //initialize the starting board
    Board(long time) {
        this.time = time * 1000 - 500;
        connect4Board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                connect4Board[i][j] = 0;
            }
        }
        printBoard();
    }



    //checks if given move is legal
    private int checkLegalMove(int i, int j) {
        if (i < SIZE && i >= 0 && j < SIZE && j >= 0) {
            if (connect4Board[i][j] == 0) {
                return 0;
            }
            else {
                return 1;
            }
        }
        else
            return -1;
    }

    private int evaluate() {
        return 0;
    }
    //places the player move on the board if legal
    boolean getPlayerMove(int i, int j) {
        switch (checkLegalMove(i, j - 1)) {
            case 0:
                connect4Board[i][j - 1] = 2;
                printBoard();
                return true;
            case 1:
                System.out.println("Move already taken!");
                return false;
            default:
                System.out.println("Invalid move!");
                return false;
        }
    }
    //determine what move does the computer make
    void makeMove() {
        int best = -20000;
        int depth = MAXDEPTH;
        int score, mi = 4, mj = 4;
        startTimer();
        for (int i = 3; i !=2; i = (i+1)%SIZE) {
            for (int j = 3; j != 2; j = (j+1)%SIZE) {
                if (connect4Board[i][j] == 0) {
                    connect4Board[i][j] = 1; //make move on board
                    score = min(depth + 1, i, j);
                    if (score > best) {
                        mi = i;
                        mj = j;
                        best = score;
                    }
                    connect4Board[i][j] = 0; //undo move
                }
            }
            if (connect4Board[i][mj] == 0) {
                connect4Board[i][mj] = 1; //make move on board
                score = min(depth + 1, i, 2);
                if (score > best) {
                    mi = i;
                    mj = 2;
                    best = score;
                }
                connect4Board[i][mj] = 0; //undo move
            }
        }
        for (int j = 3; j != 2; j = (j + 1) % SIZE) {
            if (connect4Board[mi][j] == 0) {
                connect4Board[mi][j] = 1; //make move on board
                score = min(depth + 1, 2, j);
                if (score > best) {
                    mi = 2;
                    mj = j;
                    best = score;
                }
                connect4Board[mi][j] = 0; //undo move
            }
        }

        connect4Board[mi][mj] = 1;
        printBoard();
        System.out.println((System.currentTimeMillis() - startTime)/1000 + "\n");
        gameOver(mi,mj,1);
    }

    private int min(int depth, int x, int y) {
        int best = 20000;
        int beta = -20000;
        int score;

        if((System.currentTimeMillis()- startTime > time )){
            return -1;
        }

        if (check4Winner(x, y, 1) != 0) {
            return (check4Winner(x, y, 1));
        }
        if (depth == 0) {
            return (evaluate());
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (connect4Board[i][j] == 0) {
                    connect4Board[i][j] = 1; // make move on board
                    score = max(depth + 1, i, j);
                    if (score < best) {
                        best = score;
                    }
                    connect4Board[i][j] = 0; // undo move
                    if (score >= beta){
                        beta = score;
                    }else{
                        return best;
                    }

                }
            }
        }
        return (best);
    }

    private int max(int depth, int x, int y) {
        int best = -20000;
        int beta = 20000;
        int score;
        if (check4Winner(x, y, 2) != 0) {
            return check4Winner(x,y,2);
        }
        if (depth == 0) {
            return (evaluate());
        }

        if((System.currentTimeMillis()- startTime > time )){
            return 1;
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (connect4Board[i][j] == 0) {
                    connect4Board[i][j] = 1; // make move on board
                    score = min(depth + 1, i , j);
                    if (score > best) {
                        best = score;
                    }
                    connect4Board[i][j] = 0; // undo move

                    if (score <= beta) {
                        beta = score;
                    } else {
                        return score;
                    }

                }
            }
        }
        return (best);
    }

    private void startTimer(){
        startTime = System.currentTimeMillis();
    }


    //check if there is four in a line


    private int checkUp(int x, int y, int player) {
        if (player == connect4Board[x][y] && y == SIZE - 1)
            return 1;
        if (player == connect4Board[x][y])
            return 1 + checkUp(x, ++y, player);
        return 0;
    }

    private int checkDown(int x, int y, int player) {
        if (player == connect4Board[x][y] && y == 0)
            return 1;
        if (player == connect4Board[x][y])
            return 1 + checkDown(x, --y, player);
        return 0;
    }

    private int checkLeft(int x, int y, int player) {
        if (player == connect4Board[x][y] && x == 0)
            return 1;
        if (player == connect4Board[x][y])
            return 1 + checkLeft(--x, y, player);
        return 0;
    }

    private int checkRight(int x, int y, int player) {
        if (player == connect4Board[x][y] && x == SIZE - 1)
            return 1;
        if (player == connect4Board[x][y])
            return 1 + checkRight(++x, y, player);
        return 0;
    }
    private int check4Winner(int row, int col, int player) {
        if (player == 1) { //computer
            if (row == 0 && col == 0) {
                if (checkRight(++row, col, player) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return 5000;
                }
            }
            if (row == 0 && col == SIZE - 1) {
                if (checkRight(++row, col, player) >= 4) {
                    return 5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return 5000;
                }
            }
            if (row == SIZE - 1 && col == SIZE - 1) {
                if (checkLeft(--row, col, player) >= 4) {
                    return 5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return 5000;
                }
            }
            if (row == SIZE - 1 && col == 0) {
                if (checkLeft(--row, col, player) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return 5000;
                }
            }
            if (row == 0) {
                if (checkRight(++row, col, player) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                    return 5000;
                }
            }
            if (col == SIZE - 1) {
                if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                    return 5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return 5000;
                }
            }
            if (row == SIZE - 1) {
                if (checkLeft(--row, col, player) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                    return 5000;
                }
            }
            if (col == 0) {
                if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                    return 5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return 5000;
                }
            }
            if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                return 5000;
            }
            if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                return 5000;
            }
        }
        if (player == 2) { //player
            if (row == 0 && col == 0) {
                if (checkRight(++row, col, player) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return -5000;
                }
            }
            if (row == 0 && col == SIZE - 1) {
                if (checkRight(++row, col, player) >= 4) {
                    return -5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return -5000;
                }
            }
            if (row == SIZE - 1 && col == SIZE - 1) {
                if (checkLeft(--row, col, player) >= 4) {
                    return -5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return -5000;
                }
            }
            if (row == SIZE - 1 && col == 0) {
                if (checkLeft(--row, col, player) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return -5000;
                }
            }
            if (row == 0) {
                if (checkRight(++row, col, player) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                    return -5000;
                }
            }
            if (col == SIZE - 1) {
                if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                    return -5000;
                }
                if (checkDown(row, --col, player) >= 4) {
                    return -5000;
                }
            }
            if (row == SIZE - 1) {
                if (checkLeft(--row, col, player) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                    return -5000;
                }
            }
            if (col == 0) {
                if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                    return -5000;
                }
                if (checkUp(row, ++col, player) >= 4) {
                    return -5000;
                }
            }
            if (checkLeft(--row, col, player) + checkRight(++row, col, player) >= 4) {
                return -5000;
            }
            if (checkUp(row, ++col, player) + checkDown(row, --col, player) >= 4) {
                return -5000;
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (connect4Board[i][j] == 0)
                    return 0;
            }
        }
        return -1; //draw
    }


    //prints the current board status
    private void printBoard() {
        String str = "ABCDEFGH";
        char[] c = str.toCharArray();
        System.out.print("\n ");
        for (int i = 1; i <= 8; i++) {
            System.out.print("\u001B[35m " + i + "\u001B[0m");
        }
        System.out.print("\n");
        for (int j = 0; j < SIZE; j++) {
            System.out.print("\u001B[35m"+ c[j] + "\u001B[0m");
            for (int k = 0; k < SIZE; k++) {
                switch (connect4Board[j][k]) {
                    case 0:
                        System.out.print(" -");
                        break;
                    case 1:
                        System.out.print("\u001B[33m X\u001B[0m");
                        break;
                    default:
                        System.out.print("\u001B[34m O\u001B[0m");
                        break;
                }
            }
            System.out.print("\n");
        }
        System.out.println();
    }
    //determines the result of the game
    void gameOver(int i, int j, int player) {
        if (check4Winner(i,j, player) == 5000){
            System.out.println("Computer wins!");
            System.exit(0);
        }
        if (check4Winner(i,j, player) == -5000){
            System.out.println("You win!");
            System.exit(0);
        }
        if (check4Winner(i,j, player) == 1){
            System.out.println("Draw!");
            System.exit(0);
        }
    }
    @Override
    public String toString() {
        return "Board{" +
                "connect4Board=" + Arrays.toString(connect4Board) +
                ", time=" + time +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return time == board.time &&
                startTime == board.startTime &&
                Arrays.equals(connect4Board, board.connect4Board);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(time, startTime);
        result = 31 * result + Arrays.hashCode(connect4Board);
        return result;
    }
}