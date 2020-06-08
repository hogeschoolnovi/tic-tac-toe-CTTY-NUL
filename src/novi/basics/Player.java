package novi.basics;

import java.io.*;

public class Player implements Serializable {

    //TODO toString() leren https://www.javatpoint.com/understanding-toString()-method
    //TODO heb nu deze gedaan, maar moet ook lezen https://attacomsian.com/blog/java-write-object-to-file#
    //https://attacomsian.com/blog/java-read-object-from-file

    static final long serialVersionUID = 1L; //hm rare problemen zonder serialVersionUID https://stackoverflow.com/a/27489906 java.io.InvalidClassException:

    //attributen: informatie verzamelen
    private int playerid;
    private String name;
    private char token;
    private int score;
    //private int wins;

    //methoden: acties die de speler uit kan voeren
    //constructor
    public Player(int playerid, String name, char token) {
        this.playerid = playerid;
        this.name = name;
        this.token = token;
        score = 0;
        //wins = 0;
    }

    public static int write_to_file() {

        try {
            FileOutputStream fos = new FileOutputStream("object.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // create a new user object

            Player playertmp = new Player(1, "onzinnaam", 'X');
//            User user = new User("John Doe", "john.doe@example.com",
//                    new String[]{"Member", "Admin"}, true);

            // write object to file
            oos.writeObject(playertmp);

            // close writer
            oos.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int i = 0;
        return i;
    }

    public static int read_from_file() {

        try {
            FileInputStream fis = new FileInputStream("object.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            // read object from file
            //User user = (User) ois.readObject();
            Player playertmp = (Player) ois.readObject();

            // print object
            //System.out.println(user);
            System.out.println(playertmp);

            // close reader
            ois.close();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        int i = 0;
        return i;
    }

    //get methoden
    public int getId() {
        return playerid;
    }

    //public int getScore() {
    //    return score;
    // }

    public String getName() {
        return name;
    }

    //public int getWins() {
    //    return wins;
    //}

    public char getToken() {
        return token;
    }
    //public void addWin() {
    //parameter voor welke speler gewonnen heeft???
    //}

    public void show_score() {
        System.out.println("Player " + this.getName() + " scored " + this.score + " points"); //this.getScore()

    }

    //set methoden
    /*public void setScore(int score) {
        this.score = score;
    }*/
    public void addScore() {
        score++;
    }


}