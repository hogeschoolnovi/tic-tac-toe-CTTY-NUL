package novi.basics;

import java.util.Scanner;

import static novi.basics.Player.write_to_file;

public class Main {

    //declare stuff here so it can be used in different methods
    public static char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static String activePlayerName = "";
    //public static char player1token = 'X'; //mogelijk maken om andere tokens te kiezen!
    //public static char player2token = 'O';

    public static void main(String[] args) {
        //TODO make it work for all board dimensions?
        //TODO add ability to cheat; chosing field 13 automagically wins the game
        //TODO support non-standard tokens
        //TODO make alternative version wherein board is a multidimensional array
        //TODO player 1 mag nu, nadat ie gewonnnen heeft en je van player bent geswitched, 2 keer op een rij een zet doen!
        //TODO Meer OO maken
        //TODO opslaan en laden met https://www.tutorialspoint.com/java/java_serialization.htm


        write_to_file();


        boolean does_user_want_to_quit = false; //a bit presumptuous
        boolean should_we_switch_players = false;
        boolean did_someone_win = false;
        boolean should_we_ask_player_names = true;

        int drawCount = 0;
        int chosenField;
        char activePlayerToken;
        int activePlayerId = 1;
        int answer_continue_switchplayers_exit;

        while (does_user_want_to_quit == false) {
            Scanner playerInput = new Scanner(System.in);

            //give players default names to speed up testing but allow overriding them later
            String player1Name = "Alice";
            String player2Name = "Bob";

            if (should_we_ask_player_names) {
                System.out.println("Player 1, what is your name?");
                player1Name = playerInput.next();

                System.out.println("Player 2, what is your name?");
                player2Name = playerInput.next();
            }
            Player player1 = new Player(1, player1Name, 'X');
            Player player2 = new Player(2, player2Name, 'O');
            activePlayerName = player1Name;
            //activePlayerId = player1
            activePlayerToken = player1.getToken();

            should_we_switch_players = false; //we just did


            while (!should_we_switch_players && does_user_want_to_quit == false) {

                char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

                //TODO hier while loop van maken? zolang spel niet gewonnen is en we nog niet op beurt 10 zitten
                for (int turn = 1; turn < 10; turn++) { // the game has a max 9 turns, but the 9th turn is kinda pointless cause there is only 1 option left!

                    System.out.println("Turn number: " + turn);

                    printBoard(board);
                    chosenField = ask_player_for_move(activePlayerId); //TODO chosenfield is ambigous

                    board[chosenField - 1] = activePlayerToken;
                    if (turn > 2) {
                        did_someone_win = check_if_someone_won(board);
                    }

                    if (did_someone_win) {
                        did_someone_win = false;
                        printBoard(board);

                        if (activePlayerId == 1) {
                            player1.addScore();
                        } else {
                            player2.addScore();
                        }

                        turn = 9; //TODO I could change the for loop to a while loop and use turn++; the game may take less than 9 turns
                    } else if (!did_someone_win && turn == 9) {
                        drawCount++;
                        System.out.println("Its a draw... so technically you both lost!");
                    }

                    if (activePlayerId == 1) {
                        activePlayerId = player2.getId();
                        activePlayerToken = player2.getToken();
                        activePlayerName = player2.getName();
                    } else {
                        activePlayerId = player1.getId();
                        activePlayerToken = player1.getToken();
                        activePlayerName = player1.getName();
                    }

                }
                System.out.println();
                System.out.println("Amount of draws: " + drawCount);
                //System.out.println("Player 1 score: " + player1.getScore());
                //System.out.println("Player 2 score: " + player2.getScore());
                player1.show_score();
                player2.show_score();
                System.out.println();
                System.out.println();

                answer_continue_switchplayers_exit = ask_continue_switchplayers_exit();
                if (answer_continue_switchplayers_exit == 2) {
                    System.out.println("Switching players...");
                    should_we_ask_player_names = true;
                    should_we_switch_players = true;
                } else if (answer_continue_switchplayers_exit == 3) {
                    System.out.println("Quitting...");
                    does_user_want_to_quit = true;
                }

            }
        }
    }


    public static void printBoard(char[] board) {
        //TODO write something more elegant
        for (int fieldIndex = 0; fieldIndex < board.length; fieldIndex++) {
            System.out.print(board[fieldIndex] + " ");
            // als we het tweede veld geprint hebben of het vijfde veld geprint hebben
            // dan gaan we naar de volgende regel
//            if (fieldIndex == 2 || fieldIndex == 5) {
//                System.out.println();
//            }
            if ((fieldIndex - 2) % 3 == 0) { // if divisible by 3 -- waarom makkelijk doen als het ook moeilijk kan
                System.out.println();
            }

        }
        System.out.println();
    }


    public static boolean check_if_someone_won(char[] board) {

        for (int i = 1; i < 4; i++) {
            //horizontal check
            if (board[i * 3 - 3] == board[i * 3 - 2] && board[i * 3 - 2] == board[i * 3 - 1]) {
                //System.out.println("-----------------horizontal win");
                return true;
            }
            //vertical check
            if (board[i - 1] == board[i + 2] && board[i + 2] == board[i + 5]) {
                //System.out.println("-----------------vertical win");
                return true;
            }
        }
        //TODO find more elegant way to check diagonals
        if (board[0] == board[4] && board[4] == board[8]) { // 1 5 9 & 7 5 3 (-1 due to zero based array)
            //System.out.println("-----------------diagonal win");
            return true;
        }
        if (board[6] == board[4] && board[4] == board[2]) {
            //System.out.println("-----------------diagonal win");
            return true;
        }
        return false;
    }

    public static int ask_player_for_move(int player_number) {  //Player 1 or player 2

        Scanner scan = new Scanner(System.in);  // Create a Scanner object

        Boolean validInput = false;
        int inputInt = 0;
        do {

            System.out.println(activePlayerName + ", please enter a whole number (1-9): ");
            if (scan.hasNextInt()) { // I tried getting rid of this If statement but no bueno
                inputInt = scan.nextInt();

                try {
                    if (board[inputInt - 1] != 'O' && board[inputInt - 1] != 'X') { //TODO this should use player.gettoken in order to support non-standard tokens
                        validInput = true;
                    } else {
                        System.out.println("This space is already occupied.");
                    }
                } catch (Exception e) {
                    //do nothing
                }

            } else {
                scan.next(); //discard scanner input
            }
        }
        while (validInput == false); //a while loop has a weird syntax!

        return inputInt;
    }

    public static int ask_continue_switchplayers_exit() {
        int inputInt = 0;
        boolean validInput = false;

        Scanner scanner_continue_switchplayers_exit = new Scanner(System.in);

        do {
            System.out.println("Press 1 to continue playing");
            System.out.println("Press 2 to switch players");
            System.out.println("Press 3 to exit");

            if (scanner_continue_switchplayers_exit.hasNextInt()) {
                inputInt = scanner_continue_switchplayers_exit.nextInt();
                if (inputInt > 0 && inputInt < 4) {
                    validInput = true;
                } else {
                    System.out.println("Incorrect number");
                }
            } else {
                System.out.println("Not a number");
                scanner_continue_switchplayers_exit.next(); //discard scanner input, if not then it keeps looping
            }

        }
        while (validInput == false);

        return inputInt;
    }

}