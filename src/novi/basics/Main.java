package novi.basics;

import java.util.Scanner;

public class Main {

    //ik moet dingen hier declareren als ik ze ook in andere methods wil gebruiken!
    public static char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static void main(String[] args) {
        //TODO zou leuker zijn als dit voor alle board sizes werkte
        //TODO welke andere classes kan ik aan tic tac toe toevoegen
        //TODO playerid ipv playertoken gebruiken
        //TODO Engels en Nederlands door elkaar


        int user_wants_to_exit = 0;
        int switch_players = 0;
        int drawCount = 0; // opslaan hoeveel keer er gelijk spel is geweest
        String activePlayerName;
        int keuze;
        int did_someone_win;

        int ask_for_player_names = 0; //TODO should be a boolean

        while (user_wants_to_exit == 0) {

            Scanner playerInput = new Scanner(System.in);

            String player1Name;
            String player2Name;

            if (ask_for_player_names == 0) {
              //give players default names to speed up testing
              player1Name = "Alice";
              player2Name = "Bob";
            } else {
                System.out.println("Player 1, what is your name?");
                player1Name = playerInput.next();

                System.out.println("Player 2, what is your name?");
                player2Name = playerInput.next();
            }
            Player player1 = new Player(1, player1Name, 'X');
            Player player2 = new Player(2, player2Name, 'O');


            switch_players = 0;

            char activePlayerToken = 'X';
            int activePlayerId = 1;

            while (switch_players == 0) {

                char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};

                for (int turn = 1; turn < 10; turn++) { // the game has a max 9 turns, but the 9th turn is kinda pointless cause there is only 1 option left!
                    System.out.println("Turn number: " + turn);

                    printBoard(board);
                    keuze = ask_player_for_move(activePlayerId);

                    board[keuze - 1] = activePlayerToken;
                    //printBoard(board);
                    //if (turn > 2) { //TODO checking if someone won after only 2 turns is pointless
                    did_someone_win = check_if_someone_won(board);
                    //}

                    if (did_someone_win == 1) { //TODO I could change the for loop to a while loop and use turn++; the game may take less than 9 turns
                        printBoard(board);

                        if (activePlayerId == 1) {
                            player1.addScore();
                        } else {
                            player2.addScore();
                        }

                        turn = 9;
                    } else if (did_someone_win == 0 && turn == 9) {
                        drawCount++;
                        System.out.println("Its a draw... so you both lost!");
                    }


                    if (activePlayerId == 1) {
                        activePlayerId = 2;
                        activePlayerToken = 'O';
                    } else {
                        activePlayerId = 1;
                        activePlayerToken = 'X';
                    }

                }
                System.out.println();
                System.out.println("Amount of draws: " + drawCount );
                System.out.println("Player 1 score: " + player1.getScore());
                System.out.println("Player 2 score: " + player2.getScore());
                System.out.println();
                System.out.println();

                Scanner are_you_done_scanner = new Scanner(System.in);
                System.out.println("Press 1 to continue playing");
                System.out.println("Press 2 to switch players");
                System.out.println("Press 3 to exit");

                int are_you_done = are_you_done_scanner.nextInt();
                //TODO this should detect incorrect input, maybe turn this into a method?
                if (are_you_done == 3) {
                    System.exit(0);
                    //user_wants_to_exit = 1;
                } else if (are_you_done == 2) {
                    switch_players = 1;
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


    public static int check_if_someone_won(char[] board) { //TODO this should return a boolean

        for (int i = 1; i < 4; i++) {
            //horizontal check
            if (board[i * 3 - 3] == board[i * 3 - 2] && board[i * 3 - 2] == board[i * 3 - 1]) {
                System.out.println("-----------------horizontal win");
                return 1;
            }
            //vertical check
            if (board[i - 1] == board[i + 2] && board[i + 2] == board[i + 5]) {
                System.out.println("-----------------vertical win");
                return 1;
            }
        }
        //TODO find more elegant way to check diagonals
        if (board[0] == board[4] && board[4] == board[8]) { // 1 5 9 & 7 5 3 (-1 due to zero based array)
            System.out.println("-----------------diagonal win");
            return 1;
        }
        if (board[6] == board[4] && board[4] == board[2]) {
            System.out.println("-----------------diagonal win");
            return 1;
        }
        return 0;
    }


    public static int ask_player_for_move(int player_number) {  //Player 1 or player 2

        Scanner scan = new Scanner(System.in);  // Create a Scanner object

        Boolean validInput = false;
        int inputInt = 0;
        do {

            System.out.print("Player " + player_number + " please enter a whole number (1-9): ");
            if (scan.hasNextInt()) { // I tried getting rid of this If statement but no bueno
                inputInt = scan.nextInt();

                try {
                    if (board[inputInt - 1] != 'O' && board[inputInt - 1] != 'X') {
                        validInput = true;
                    } else {
                        System.out.println("This space is already occupied.");
                        validInput = false;
                    }
                } catch (Exception e) {
                    System.out.println("Player " + player_number + " please enter a whole number (1-9): ");
                    validInput = false;
                }

            } else
                System.out.println("Player " + player_number + " please enter a whole number (1-9): ");
                scan.nextLine();
            }
        while (validInput == false); //a while loop has a weird syntax!

        return inputInt;
    }

}