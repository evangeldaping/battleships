package com.company.BattleShips;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static Scanner input = new Scanner(System.in);
    public static Random random = new Random();

    public static String[][] oceanMap = new String[10][10]; // this is for the location of user's ships
    public static String[][] oceanMap2 = new String[10][10]; //this is for the location of computer's ships

    public static int userShips = 0;
    public  static int computerShips = 0;

    public static void main(String[] args) {
        intro();
        userShipsLocation(oceanMap);
        computerShipsLocation(oceanMap);
        computersTurn();
        battle();
    }

    public static void intro() {
        System.out.println("  **** Welcome to Battle Ships game ****\n ");
        System.out.println("Right now, the sea is empty. \n  ");
        printMap(oceanMap);
    }

    public static void printMap(String[][] oceanMap) {
        System.out.println("  0123456789  "); // this is the horizontal grid

        for (int row = 0; row < oceanMap.length; row++) {
            System.out.print(row + "|"); // this is the vertical grid

            for (int column = 0; column < oceanMap.length; column++) {
                if (oceanMap[row][column] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print(oceanMap[row][column]);
                }
            }
            System.out.println("|" + row); // this is the vertical grid
        }

        System.out.println("  0123456789  "); // this is the horizontal grid
    }

    public static void userShipsLocation(String[][] oceanMap) {
        System.out.println("\nDeploy your ships:");

        while (userShips < 5) {
            System.out.println("Enter X coordinate for your " +(userShips + 1) + ". ship: ");
            int row = input.nextInt();
            System.out.println("Enter Y coordinate for your " +(userShips + 1) + ". ship: ");
            int column= input.nextInt();

            if (row > 9 || column > 9) {
                System.out.println("you can't place ships outside the 10 by 10 grid");
            } else if (oceanMap[row][column]  != null) {
                System.out.println("you can NOT place two or more ships on the same location");
            } else {
                oceanMap [row][column] = "@";
                userShips++;
            }
        }
    }

    public static void computerShipsLocation(String[][] oceanMap) {
        System.out.println("\nComputer is deploying ships:");

        while(computerShips < 5) {

            int row = random.nextInt(10); // you can’t place ships outside the 10 by 10 grid
            int column = random.nextInt(10); // you can’t place ships outside the 10 by 10 grid

            if (oceanMap [row][column] == null && oceanMap2 [row][column] == null) { // you cannot place the ship on a location that is already taken by another ship (player’s or computer’s)
                System.out.println(""+(computerShips + 1 )+". ship DEPLOYED");
                oceanMap2 [row][column] = "@";
                computerShips++;
            }

        }

    }

    public static void playersTurn() {
        System.out.println("YOUR TURN");

        int turn = 0;

        while (turn == 0) {
            System.out.println("Enter X coordinate: ");
            int row = input.nextInt();
            System.out.println("Enter Y coordinate: ");
            int column = input.nextInt();

            if (row > 9 || column > 9) {
                System.out.println("out of range, please try again");
            }else if (oceanMap[row][column] == "!" || oceanMap[row][column] == "x" || oceanMap[row][column] == "-"  ) {
                System.out.println("coordinate was already used, please try again");
            }else if (oceanMap2[row][column] == "@") {
                System.out.println("Boom! You sunk the computer's ship!");
                oceanMap[row][column] = "!";
                oceanMap2[row][column] = "!";
                computerShips--;
                turn++;
            }else if (oceanMap[row][column] == "@") {
                System.out.println("Oh no, you sunk your own ship");
                oceanMap[row][column] = "x";
                userShips--;
                turn++;
            }else {
                System.out.println("Sorry, you missed");
                oceanMap[row][column] = "-";
                turn++;
            }
        }
    }

    public static void computersTurn() {
        System.out.println("\nCOMPUTER'S TURN");
        int turn = 0;

        while (turn == 0) {
            int row = random.nextInt(10);
            int column = random.nextInt(10);

            if (oceanMap[row][column] == "@") {
                System.out.println("The Computer sunk one of your ships!");
                oceanMap[row][column] = "x";
                oceanMap2[row][column] = "x";
                userShips--;
                turn++;
            }else if (oceanMap2[row][column] == "@") {
                System.out.println("The Computer sunk one of its own ships");
                oceanMap[row][column] = "!";
                oceanMap2[row][column] = "!";
                computerShips--;
                turn++;
            }else if (oceanMap[row][column] == "!" || oceanMap[row][column] == "x" || oceanMap2[row][column] == "-"  ) {

            }else {
                System.out.println("Computer missed");
                oceanMap2[row][column] = "-";
                turn++;
            }
        }
    }
    public static void battle() {
        printMap(oceanMap); // print updated map
        System.out.println("\nYour ships:" +userShips+ " | Computer ships:" +computerShips+ "\n----------------------------------");

        while (userShips > 0 && computerShips > 0) {
            playersTurn();
            computersTurn();
            printMap(oceanMap);
            System.out.println("\nYour ships:" +userShips+ " | Computer ships:" +computerShips+ "\n----------------------------------");
        }

        if (userShips == 0) {
            System.out.println("\nYour ships:" +userShips+ " | Computer ships:" +computerShips+ "\n----------------------------------");
            System.out.println("\n*** GAME OVER ***");
        }else if (computerShips == 0) {
            System.out.println("\nYour ships:" +userShips+ " | Computer ships:" +computerShips+ "\n----------------------------------");
            System.out.println("Hooray! You win the battle :) ");
        }
    }
}
