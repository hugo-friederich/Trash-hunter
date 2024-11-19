package org.trash_hunter.util;
import java.util.Scanner;

public class Clavier {
    static int lireEntier(Scanner clavier, String message) {
        System.out.print(message);
        return clavier.nextInt();
    }
    static double lireReel(Scanner clavier, String message) {
        System.out.print(message);
        return clavier.nextDouble();
    }
}