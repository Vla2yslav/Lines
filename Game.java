import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private int [][] pole = new int[10][10];
    private Scanner sc = new Scanner(System.in);
    private int count1 = 1;
    private int count2 = 1;
    private int score = 0;
    private String diff = "Easy";
    private int dif = 3;
    private int a = 11, b = 11;
    private String TAG_COLOR = "ON", TAG_LANG = "English";

    private void start(){
        if (TAG_COLOR.equalsIgnoreCase("ON"))
        System.out.println(ANSI_GREEN);
        else System.out.println(ANSI_RESET);
        System.out.println("    -*- [----]   [------]     ---      ------    [------] -*-");
        System.out.println("  -*-*- ||          ||       (   )     |  O |       ||    -*-*-");
        System.out.println("-*-*-*- [----]      ||      (  O  )    | |---       ||    -*-*-*-");
        System.out.println("  -*-*-     ||      ||     (  | |  )   | | \\        ||    -*-*-");
        System.out.println("    -*- [----]      ||     ( |   | )   | |  \\       ||    -*-");
        System.out.println(ANSI_RESET);
    }

    private void finish(){
        if (TAG_COLOR.equalsIgnoreCase("ON"))
            System.out.println(ANSI_RED);
        else System.out.println(ANSI_RESET);

        System.out.println("    -*- [-----]      [-]      [-]\\  /[-]   [----]       [------]   [-]     [-]   [----]   ------  -*-");
        System.out.println("  -*-*- |           /   \\     | | \\/ | |   ||           |      |   | |     | |   ||       |  O |  -*-*-");
        System.out.println("-*-*-*- |          /  O  \\    | |  ^ | |   [----]       |  []  |    \\ \\    / /   [----]   | |---  -*-*-*-");
        System.out.println("  -*-*- |   [-]   |  | |  |   | |    | |   ||           |      |     \\ \\__/ /    ||       | | \\   -*-*-");
        System.out.println("    -*- [-----]   | |   | |   | |    | |   [----]       [------]      \\____/     [----]   | |  \\  -*-");
        System.out.println(ANSI_RESET);
        System.out.println("\nYour score: " + score+"\n");
    }
    private int isNull(){
        int count = 0;
        for(int i = 1; i < 10; i++){
            for (int j = 1; j < 10; j++){
                if (pole[i][j] == 0)
                    count++;
            }
        }
        return count;
    }

    private void fillPole(){
        for(int i = 1; i < 10; i++){
            for (int j = 1; j < 10; j++)
                pole[i][j] = 0;
        }
        for(int i = 1; i < 10; i++)
            pole[i][0] = i;

        for (int i = 1; i < 10; i++)
            pole[0][i] = i;

        randomFillPole();
    }

    private void randomFillPole() {
        int x,y;
        Random random = new Random();
        if (isNull() < dif)
            dif = isNull();
        for (int i = 0; i < dif;i++){
            {
                do {
                    x = random.nextInt(10);
                    y = random.nextInt(10);
                }
                while (pole[x][y] != 0);
                pole[x][y] = random.nextInt(4)+1;
            }
        }
    }

    private void printPole(){
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (TAG_COLOR.equalsIgnoreCase("ON")) {
                    if (i != 0 && j != 0 && pole[i][j] > 0) {
                        if (i != a || j != b)
                            System.out.print(ANSI_BLUE + pole[i][j] + ANSI_RESET);
                        else System.out.print(ANSI_RED + pole[i][j] + ANSI_RESET);
                    } else if (i == 0)
                        System.out.print(ANSI_RED + pole[i][j] + ANSI_RESET);
                    else if (j == 0)
                        System.out.print(ANSI_RED + pole[i][j] + ANSI_RESET);
                    else System.out.print(pole[i][j]);
                }
                else System.out.print(pole[i][j]);

                if (TAG_COLOR.equalsIgnoreCase("ON"))
                System.out.print(ANSI_GREEN+" | "+ANSI_RESET);
                else System.out.print(" | ");
            }
            System.out.println();
            if (TAG_COLOR.equalsIgnoreCase("ON"))
            System.out.println(ANSI_GREEN+"--|---|---|---|---|---|---|---|---|---|"+ANSI_RESET);
            else System.out.println("--|---|---|---|---|---|---|---|---|---|");
        }
        System.out.println("\nYour score: " + score+"\n");
    }

    private void destroyHoriz(int p, int n){
         for (int i = 1; i <= 5; i++) {
          pole[p][n] = 0;
          n--;
         }
         score += 20;
     }

    private void destroyVert(int p, int n){
        for (int i = 1; i <= 5; i++){
            pole[p][n] = 0;
            p--;
        }
         score += 20;
     }

    private void checkOnDestroyHorizontal(){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 9; j++) {
                if (pole[i][j] == pole[i][j + 1] && pole[i][j] != 0) {
                    count1++;
                    if (count1 == 5){
                        destroyHoriz(i, j + 1);
                    }
                }
                else count1 = 1;
            }
        }
    }

    private void checkOnDestroyVertical(){
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 9; j++) {
                if (pole[j][i] == pole[j+1][i] && pole[j][i] != 0) {
                    count2++;
                    if (count2 >= 5){
                        destroyVert(j + 1, i);
                    }
                }
                else count2 = 1;
            }
        }
    }


    private void move2(){
        String choi = "";
        try{
            do {
                System.out.println("Enter coordinate of element that you want to move\n");
                if (TAG_COLOR.equalsIgnoreCase("ON")){
                System.out.println("Input " + ANSI_YELLOW + "Y" + ANSI_RESET + " coordinate of element: ");
                a = sc.nextInt();
                System.out.println("\nInput " + ANSI_YELLOW + "X" + ANSI_RESET + " coordinate of element: ");
                b = sc.nextInt();
                }
                else {
                    System.out.println("Input Y coordinate of element: ");
                         a = sc.nextInt();
                      System.out.println("\nInput X coordinate of element: ");
                         b = sc.nextInt();
                }
            }while(a <= 0 || b <= 0);
        }catch(InputMismatchException e){
            System.out.println(e.getMessage());
        }
        while (!choi.equalsIgnoreCase("q")){
            System.out.println("Press 'w' to move up \nPress 's' to move down \nPress 'a' to move left \nPress 'd' to move right \nPress 'q' to enter position of element \nPress 'menu' to back in menu");
            choi = sc.next();
            switch (choi){
                case "w":
                    if (a-1 != 0) {
                        if ( pole[a-1][b] == 0 &&  pole[a][b] != 0){
                            pole[a - 1][b] = pole[a][b];
                            pole[a][b] = 0;
                            a--;
                        }
                    }
                    printPole();
                    break;
                case "s":
                    if (a+1 != 10) {
                        if (pole[a+1][b] == 0 &&  pole[a][b] != 0) {
                            pole[a + 1][b] = pole[a][b];
                            pole[a][b] = 0;
                            a++;
                        }
                    }
                    printPole();
                    break;
                case "a":
                    if (b-1 != 0) {
                        if (pole[a][b-1] == 0 &&  pole[a][b] != 0) {
                            pole[a][b - 1] = pole[a][b];
                            pole[a][b] = 0;
                            b--;
                        }
                    }
                    printPole();
                    break;
                case "d":
                    if (b+1 != 10) {
                        if (pole[a][b+1] == 0 &&  pole[a][b] != 0) {
                            pole[a][b + 1] = pole[a][b];
                            pole[a][b] = 0;
                            b++;
                        }
                    }
                    printPole();
                    break;
                case "menu":
                    menu();
                    break;
            }

        }
        randomFillPole();
        checkOnDestroyHorizontal();
        checkOnDestroyVertical();
    }



    private void rules(){
        String exit = " ";
        if (TAG_COLOR.equalsIgnoreCase("ON"))
        System.out.println(ANSI_RED + "\t\t\t\t\t\t\t\t\t\t\tR U L E S" + ANSI_RESET);
        else  System.out.println("\t\t\t\t\t\t\t\t\t\t\tR U L E S");
        System.out.println("\nMain task in game L I N E S is create one line of 5 same element on horizontal or vertical.\nYou can move element with usage game field. For move element you can use keyboard (W, A, S, D keys).\nWhen you collect in one line same element, line wil be destroyed and to your score count add 20 score.\nGame will be over when field doesn't have empty cell.\nGood luck!\n\nPress 'q' for back to main menu.");
        exit = sc.next();
        if (exit.equalsIgnoreCase("q"))
            menu();
    }

    private void settings(){
        String set = "";
        while (!set.equalsIgnoreCase("q")){
            if (TAG_COLOR.equalsIgnoreCase("ON"))
            System.out.println(ANSI_BLUE+"\t\tS E T T I N G S" + ANSI_RESET);
            else  System.out.println("\t\tS E T T I N G S\n");
            if (TAG_COLOR.equalsIgnoreCase("ON")){
                if (TAG_COLOR.equalsIgnoreCase("ON"))
                   System.out.println("\n\t\tMulticolor: "+ANSI_GREEN+TAG_COLOR+ANSI_RESET);
            }
            else System.out.println("\n\t\tMulticolor: "+TAG_COLOR);
            if (TAG_COLOR.equalsIgnoreCase("ON")) {
                if (diff.equalsIgnoreCase("Easy"))
                    System.out.println("\n\t\tDifficult: " + ANSI_YELLOW + diff + ANSI_RESET);
                else if (diff.equalsIgnoreCase("Medium"))
                    System.out.println("\n\t\tDifficulty: " + ANSI_BLUE + diff + ANSI_RESET);
                else System.out.println("\n\t\tDifficulty: " + ANSI_RED + diff + ANSI_RESET);
            }
            else {
                    System.out.println("\n\t\tDifficulty: " + diff);
            }
            System.out.println("\nType 'multicolor on' to activate multicolor mode \nType 'multicolor off' to deactivate multicolor mode \nType 'diff easy' to set EASY mode\nType 'diff medium' to set MEDIUM mode\nType 'diff hard' to set HARD mode\nType 'q' to back in menu.\n");
            set = sc.nextLine();
            switch (set){
                case "multicolor off":
                  TAG_COLOR = "OFF";
                    break;
                case "multicolor on":
                    TAG_COLOR = "ON";
                    break;
                case "diff easy":
                    diff = "Easy";
                    dif = 3;
                    break;
                case "diff medium":
                    diff = "Medium";
                    dif = 5;
                    break;
                case "diff hard":
                    diff = "Hard";
                    dif = 7;
                    break;
            }
        }
    }

    public void menu(){
        String choice = "";
        while (!choice.equalsIgnoreCase("q")){
            if (TAG_COLOR.equalsIgnoreCase("ON"))
            System.out.println("\t-*-*-*-*- " + ANSI_BLUE + "L I N E S" + ANSI_RESET + " -*-*-*-*-\n\n\t  -*-*-*- " + ANSI_YELLOW + "NEW GAME" + ANSI_RESET + "  -*-*-*-\n\n\t\t-*-*-  " + ANSI_RED + "RULES" + ANSI_RESET + "    -*-*-\n\n\t\t  -*- " + ANSI_CYAN + "SETTINGS" + ANSI_RESET + "  -*-");
            else System.out.println("\t-*-*-*-*- L I N E S -*-*-*-*-\n\n\t  -*-*-*- NEW GAME  -*-*-*-\n\n\t\t-*-*-  RULES    -*-*-\n\n\t\t  -*- SETTINGS  -*-");
            System.out.println("\nType 'game' to start new game \nType 'rules' to see a rules \nType 'settings' to check a settings \nType 'q' to exit.");
            choice = sc.next();
            switch (choice){
                case "game":
                    start();
                    fillPole();
                    printPole();
                    while(isNull() != 0){
                        move2();
                        printPole();
                    }
                    finish();
                    break;
                case "rules":
                    rules();
                    break;
                case "settings":
                    settings();
                    break;
            }

        }
    }

}
