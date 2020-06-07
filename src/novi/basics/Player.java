package novi.basics;

public class Player {
    //attributen: informatie verzamelen
    private int playerid;
    private String name;
    private char token;
    private int score;
    private int wins;

    //methoden: acties die de speler uit kan voeren
    //constructor
    public Player(int playerid, String name, char token) {
        this.playerid = playerid;
        this.name = name;
        this.token = token;
        score = 0;
        wins = 0;
    }

    //get methoden
    public int getId() {
        return playerid;
    }

    public String getName() {
        return name;
    }

    public char getToken() {
        return token;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    //set methoden
    /*public void setScore(int score) {
        this.score = score;
    }*/
    public void addScore() {
        score++;
    }
    public void addWin() {
        //parameter voor welke speler gewonnen heeft???
    }
}