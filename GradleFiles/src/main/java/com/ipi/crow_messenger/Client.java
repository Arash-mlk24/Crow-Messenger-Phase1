package com.ipi.crow_messenger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {

    static Logger logger = LogManager.getLogger(Client.class.getName());

    public static User client = new User();

    public static void main(String[] args) {

        SaveAndLoad.loadAll();

        System.out.println(ConsoleColors.YELLOW + "*********************************\n" +
                           ConsoleColors.YELLOW + "*" + ConsoleColors.RESET +
                           " Welcome to the Crow messenger " + ConsoleColors.YELLOW + "*\n" +
                           "*********************************" + ConsoleColors.RESET);
        logger.info("App Started!");

        mainLoop: while (true) {

            if (client.isLoggedIn()) {
                System.out.println(ConsoleColors.PURPLE + "\n* HomePage *" + ConsoleColors.RESET);
                while (true){
                    System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "My Page (1)  " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "TimeLine (2)  " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "Explorer (3)\n" +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "Chats (4)    " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "Setting (5)   " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "Exit" + ConsoleColors.RESET);
                    String homeDestination = MyScanner.getSc().next().toLowerCase();
                    if (homeDestination.equals("1")){
                        HomePage.myPage();
                        break;
                    }
                    if (homeDestination.equals("2")){
                        HomePage.timeLine();
                        break;
                    }
                    if (homeDestination.equals("3")){
                        HomePage.explorer();
                        break;
                    }
                    if (homeDestination.equals("4")){
                        HomePage.chats();
                        break;
                    }
                    if (homeDestination.equals("5")){
                        HomePage.setting();
                        break;
                    }
                    if (homeDestination.equals("exit")){
                        SaveAndLoad.saveAll();
                        System.exit(0);
                    }
                    else {
                        System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                    }
                }
            } else {
                System.out.println(ConsoleColors.PURPLE + "\n* Login Page *" + ConsoleColors.RESET);
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Are you a Crow member? (y/n)" + ConsoleColors.RESET);
                    String is = MyScanner.getSc().next().toLowerCase();
                    if (is.length() == 1) {
                        if (is.charAt(0) == 'y') {
                            Login.signIn();
                            break;
                        } else if (is.charAt(0) == 'n') {
                            Login.signUp();
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    } else {
                        if (is.equals("exit")) {
                            break mainLoop;
                        }
                        System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                    }
                }
            }

        }

    }

}

