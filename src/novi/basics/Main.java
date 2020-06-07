package novi.basics;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TODO zou leuker zijn als dit voor alle board sizes werkte
        //TODO speler objecten zoveel mogelijk gebruiken in tic tac toe, ik gebruik nu nog player1name en player2name
        //TODO welke andere classes kan ik aan tic tac toe toevoegen

        Scanner playerInput = new Scanner(System.in);

        // -- vanaf hier gaan we het spel opnieuw spelen met andere spelers (nadat op het eind keuze 2 gekozen is)
        // de 1e speler om zijn naam vragen

//voor testen makkelijker als ze defaultnamen hebben

        String player1Name = "Alice";
        String player2Name = "Bob";
//        System.out.println("Player 1, what is your name?");
        // de naam van de 1e speler opslaan
//        String player1Name = playerInput.next();
        Player player1 = new Player(player1Name, 'X');
        // de 2e speler om zijn naam vragen
//        System.out.println("Player 2, what is your name?");
        // de naam van de 2e speler opslaan
//        String player2Name = playerInput.next();
        Player player2 = new Player(player2Name, 'O');


        // -- vanaf hier gaan we het spel opnieuw spelen met dezelfde spelers (nadat op het eind keuze 1 gekozen is)
        // speelbord opslaan (1 - 9)
        // uitleg: omdat we altijd met een bord starten met 9 getallen, kunnen we het Tic Tac Toe bord ook direct een waarde geven
        // zie demo project code voor de andere manier met de for loop
        char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        // speelbord tonen
        printBoard(board);




        int user_wants_to_exit = 0;
        int user_input_is_valid = 0;

        while (user_wants_to_exit == 0) {

            for (int turn = 1; turn < 10; turn++) {
                System.out.println("Het is beurt nummer " + turn);

                while (user_input_is_valid == 0){

                }

            }

        }












        int maxRounds = board.length; // maximale aantal rondes opslaan
        char activePlayerToken = 'X'; // token van de actieve speler opslaan
        int drawCount = 0; // opslaan hoeveel keer er gelijk spel is geweest
        String activePlayerName;


        // starten met de beurt (maximaal 9 beurten)
        for (int round = 0; round < maxRounds; round++) {

            // naam van de actieve speler opslaan
            if (activePlayerToken == player1.getToken()) {
                activePlayerName = player1Name;
            } else {
                activePlayerName = player2Name;
            }


            // actieve speler vragen om een veld te kiezen (1 - 9)
            System.out.println(activePlayerName + ", please choose a field");
            // gekozen veld van de actieve speler opslaan
            int chosenField = playerInput.nextInt();
            int chosenIndex = chosenField - 1;
            // als het veld bestaat
            if (chosenIndex < 9 && chosenIndex >= 0) {
                //als het veld leeg is, wanneer er geen token staat
                if (board[chosenIndex] != player1.getToken() && board[chosenIndex] != player2.getToken()) {
                    // wanneer de speler een token op het bord kan plaatsen
                    // de token van de actieve speler op het gekozen veld plaatsen
                    board[chosenIndex] = activePlayerToken;
                    //player.score += 10;
                    // het nieuwe speelbord tonen
                    printBoard(board);
                        // als het spel gewonnen is
                        int heefteriemandgewonnen;
                        heefteriemandgewonnen = check_if_someone_won(board);
                        if (heefteriemandgewonnen == 1) {
                            // tonen wie er gewonnen heeft (de actieve speler)
                            System.out.println(activePlayerName + " heeft gewonnen!");
                            // de actieve speler krijgt een punt
                            if (activePlayerToken == player1.getToken()) {
                                player1.addScore();
                            } else {
                                player2.addScore();
                            }


                            // de scores van de spelers tonen
                            System.out.println();
                            System.out.println("Scorebord");
                            System.out.println("---------");
                            System.out.println(player1.getName() + ": " + player1.getScore());
                            System.out.println(player2.getName() + ": " + player2.getScore());
                        }
                    //TODO wanneer we in de laatste beurt zijn en niemand gewonnen heeft
                    //TODO aantal keer gelijk spel ophogen
                    //TODO aantal keer gelijk spel tonen
                    //de beurt doorgeven aan de volgende speler (van speler wisselen)
                    //als de actieve speler, speler 1 is:
                    if (activePlayerToken == player1.getToken()) {
                        // maak de actieve speler, speler 2
                        activePlayerToken = player2.getToken();
                    }
                    // anders
                    else {
                        // maak de actieve speler weer speler 1
                        activePlayerToken = player1.getToken();
                    }
                } //of al bezet is
                else {
                    maxRounds++;
                    System.out.println("this field is not available, choose another");
                }
                //TODO versie 2: als het veld leeg is, wanneer de waarde gelijk is aan chosenField
                /*if(board[chosenIndex] != '1' + chosenIndex) {
                    board[chosenIndex] = activePlayerToken;
                }*/
            }
            // als het veld niet bestaat
            else {
                // het mamimale aantal beurten verhogen
                maxRounds++;
                // foutmelding tonen aan de speler
                System.out.println("the chosen field does not exist, try again");
            }

            // -- terug naar het begin van de volgende beurt
        }
        //TODO vragen of de spelers nog een keer willen spelen
        //1: nog een keer spelen
        //2: van spelers wisselen
        //3: afsluiten
        // speler keuze opslaan
        // bij keuze 1: terug naar het maken van het bord
        // bij keuze 2: terug naar de start van de applicatie en het vragen van spelernamen
        // bij keuze 3: het spel en de applicatie helemaal afsluiten
    }



    public static void printBoard(char[] board) {
        //TODO vervangen door mijn elegantere versie
        for (int fieldIndex = 0; fieldIndex < board.length; fieldIndex++) {
            System.out.print(board[fieldIndex] + " ");
            // als we het tweede veld geprint hebben of het vijfde veld geprint hebben
            // dan gaan we naar de volgende regel
            if(fieldIndex == 2 || fieldIndex == 5) {
                System.out.println();
            }
        }
        System.out.println();
    }



    public static int check_if_someone_won(char[] board) {

        for (int i = 1; i < 4; i++) {
            //horizontaal checken
            if(board[i * 3 - 3] == board[i * 3 - 2] && board[i * 3 - 2] == board[i * 3 - 1]){
                System.out.println("WHOOHOOOOOOO er heeft iemand horizontaal gewoonnen!!!");
                return 1;
            }
            //verticaal checken
            if(board[i - 1] == board[i+2] && board[i+2] == board[i+5]){
                System.out.println("fuck yeah verticale win");
                return 1;
            }
        }
        //TODO diagonalen checken, zowel linksonder naar rechtsboven als linksboven naar rechtsonder

        return 0;
    }

}