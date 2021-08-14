package bullscows;
import java.util.*;

public class Main {

    private static final Scanner cin = new Scanner(System.in);
    private static char[] secretCode;
    private static int bull;
    private static int cows;
    private static int num; //length of secret code

    //generating code using nano time (changed to random in current iteration of code)
    /*
    private static void secretCode() { //making secret code which is to be predicted
        boolean flg = false;
        do {
            System.out.println("Please, enter the secret code's length:");
            num = cin.nextInt();

            if (num > 10) {
                flg = true;
                System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            }
        } while (flg);

        StringBuilder secretCodeMain;
        while (true) {
            StringBuilder secretCode = new StringBuilder(num);
            long pseudoRandomNumber = System.nanoTime();
            char[] tempCode = Long.toString(pseudoRandomNumber).toCharArray();
            for (int i = tempCode.length - 1; i >= 0; i--) {
                boolean flag = true;
                for (int j = 0; j < secretCode.length(); j++) {
                    if (tempCode[i] == secretCode.charAt(j)) {
                        flag = false;
                    }
                }
                if (flag) {
                    if (secretCode.length() == 0 && tempCode[i] == '0') {
                        continue;
                    }
                    secretCode.append(tempCode[i]);
                }
                if (secretCode.length() == num) {
                    break;
                }
            }
            if (secretCode.length() == secretCode.capacity()) {
                secretCodeMain = secretCode;
                break;
            }
        }
        secretCode = new char[secretCodeMain.length()];
        secretCodeMain.getChars(0, num, secretCode, 0);

        System.out.println("Okay, let's start a game!");
    }
    */

    private static void secretCode() { // generating code using Random
        boolean flg = false;
        do {
            System.out.println("Please, enter the secret code's length:");
            num = cin.nextInt();

            if (num > 10) {
                flg = true;
                System.out.println("Error: can't generate a secret number with a length of 11 because there aren't enough unique digits.");
            }
        } while (flg);

        Random rand = new Random();
        StringBuilder secNum = new StringBuilder(num);
        for (int i = 0; i < num; i++) {
            boolean flag = false;
            int intnum;
            do {
                intnum = rand.nextInt(10);
                for (int j = 0; j < secNum.length(); j++) {
                    if ((intnum + '0')  == secNum.charAt(j)) {
                        flag =  true;
                    }
                }
            } while (flag);
            secNum.append(intnum);
        }
        secretCode = new char[num];
        secNum.getChars(0, num, secretCode, 0);

        System.out.println("Okay, let's start a game!");
    }

    protected static void initiateCode() { // to initiate the secret Code
        secretCode();
        bull = 0;
        cows = 0;
    }

    public static void countBC(String secret, String guess) { // to count bulls and cows

        int []secDigitCount = new int[10];
        int []guessDigitCount = new int[10];
        for (int i = 0; i < Math.min(secret.length(), guess.length()); i++) {
            char secretChar = secret.charAt(i);
            char guessChar = guess.charAt(i);

            if (secretChar == guessChar) {
                bull += 1;
            } else {
                secDigitCount[secretChar - '0'] += 1;
                guessDigitCount[guessChar - '0'] += 1;
            }
        }

        if (secret.length() > guess.length()){
            for (int i = guess.length(); i < secret.length(); i++) {
                secDigitCount[secret.charAt(i) - '0'] += 1;
            }
        } else if (secret.length() < guess.length()) {
            for (int i = secret.length(); i < guess.length(); i++) {
                guessDigitCount[guess.charAt(i) - '0'] += 1;
            }
        }
        for (int i = 0; i < 10; i++) {
            cows += Math.min(secDigitCount[i], guessDigitCount[i]);
        }
    }

    public static void printRes() { // print output according to guess
        System.out.print("Grade: ");
        if (bull > 0) {
            if (cows > 0) {
                System.out.println(bull + " bull(s) and " + cows + " cow(s).");
            } else {
                System.out.println(bull + " bull(s).");
            }
        } else if (cows > 0) {
            System.out.println(cows + " cows(s).");
        } else {
            System.out.println("None.");
        }
    }

    protected static void playGame() { // method to play game till answer is found
        boolean flag = true;
        int turn = 1;
        do {
            System.out.println("Turn " + turn + ":");
            if (turn == 1) {
                cin.nextLine();
            }
            String number = cin.nextLine();
            countBC(String.valueOf(secretCode), number);
            printRes();
            turn++;
            if (bull == num) {
                flag = false;
            }
            bull = 0;
            cows = 0;
        } while (flag);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public static void main(String[] args) {
        initiateCode();
        playGame();
    }
}

