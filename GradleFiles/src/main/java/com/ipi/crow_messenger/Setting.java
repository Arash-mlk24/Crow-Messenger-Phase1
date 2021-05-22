package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Setting {

    static Logger logger = LogManager.getLogger(Setting.class.getName());

    public static void deleteAccount() {
        Client.client.myUserByUsername().deleteFromAll();
        Index.deleteUser(Client.client.myUserByUsername());
        Client.client.setId(-1);
        Client.client.setUsername("");
        Client.client.makeLoggedOut();
        SaveAndLoad.saveUsers();
        logger.info("user " + Client.client.myUserByUsername().getId() + " deleted account");
    }

    public static void logOut() {
        Client.client.setId(-1);
        Client.client.setUsername("");
        Client.client.makeLoggedOut();
        Client.client.myUserByUsername().makeLoggedOut();
        SaveAndLoad.saveUsers();
        logger.info("user " + Client.client.myUserByUsername().getId() + " logged out");
    }

    public static void privacy() {

        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Privacy Setting *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Public and Private setting (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Last Seen setting (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Temporarily Disable Account (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Change Password (4)             " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String privacyDestination = MyScanner.getSc().next().toLowerCase();
            if (privacyDestination.equals("-")) {
                break;
            }
            else if (privacyDestination.equals("1")) {
                while (true) {
                    if (Client.client.myUserByUsername().isPrivateAccount()) {
                        System.out.println(ConsoleColors.BLUE + "Your account is private. do you want to change it? (y/n)" + ConsoleColors.RESET);
                        String pubOrPv = MyScanner.getSc().next().toLowerCase();
                        if (pubOrPv.equals("y")) {
                            Client.client.myUserByUsername().makePublic();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.myUserByUsername().getId() + "changed privacy to public");
                            System.out.println(ConsoleColors.GREEN + "Your account is public now!" + ConsoleColors.RESET);
                            break;
                        } else if (pubOrPv.equals("n")) {
                            System.out.println(ConsoleColors.GREEN + "The process canceled. Your account remains private!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    } else{
                        System.out.println(ConsoleColors.BLUE + "Your account is public. do you want to change it? (y/n)" + ConsoleColors.RESET);
                        String pubOrPv = MyScanner.getSc().next().toLowerCase();
                        if (pubOrPv.equals("y")) {
                            Client.client.myUserByUsername().makePrivate();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.myUserByUsername().getId() + "changed privacy to private");
                            System.out.println(ConsoleColors.GREEN + "Your account is private now!" + ConsoleColors.RESET);
                            break;
                        } else if (pubOrPv.equals("n")) {
                            System.out.println(ConsoleColors.GREEN + "The process canceled. Your account remains public!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                }
            }
            else if (privacyDestination.equals("2")) {
                System.out.println(ConsoleColors.PURPLE + "\n* Last Seen Option *" + ConsoleColors.RESET);
                if (Client.client.myUserByUsername().getLastSeenType().equals("anyone")){
                    out: while (true){
                        System.out.println(ConsoleColors.BLUE + "Your last seen option is set as \"Anyone\" " + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + "Do you want to change it? (y/n)" + ConsoleColors.RESET);
                        String is = MyScanner.getSc().next().toLowerCase();
                        if (is.equals("y")){
                            System.out.println(ConsoleColors.BLUE + "Change to:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Just my followings (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Nobody (2)  " + ConsoleColors.RESET);
                            String changeOfLastS = MyScanner.getSc().next().toLowerCase();
                            while (true){
                                if (changeOfLastS.equals("-")){
                                    break out;
                                } else if (changeOfLastS.equals("1")){
                                    Client.client.myUserByUsername().setLastSeenType("justmyf");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Just My Followings\"!");
                                    break out;
                                } else if (changeOfLastS.equals("2")){
                                    Client.client.myUserByUsername().setLastSeenType("nobody");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Nobody\"!");
                                    break out;
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        } else if (is.equals("n")){
                            System.out.println(ConsoleColors.GREEN + "The process canceled. Your account LastSeen option remains \"Anyone\"!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                } else if (Client.client.myUserByUsername().getLastSeenType().equals("justmyf")){
                    out: while (true){
                        System.out.println(ConsoleColors.BLUE + "Your last seen option is set as \"Just My Followings\" " + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + "Do you want to change it? " + ConsoleColors.RESET);
                        String is = MyScanner.getSc().next().toLowerCase();
                        if (is.equals("y")){
                            System.out.println(ConsoleColors.BLUE + "Change to:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Anyone (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Nobody (2)  " + ConsoleColors.RESET);
                            String changeOfLastS = MyScanner.getSc().next().toLowerCase();
                            while (true){
                                if (changeOfLastS.equals("-")){
                                    break out;
                                } else if (changeOfLastS.equals("1")){
                                    Client.client.myUserByUsername().setLastSeenType("anyone");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Anyone\"!");
                                    break out;
                                } else if (changeOfLastS.equals("2")){
                                    Client.client.myUserByUsername().setLastSeenType("nobody");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Nobody\"!");
                                    break out;
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        } else if (is.equals("n")){
                            System.out.println(ConsoleColors.GREEN + "The process canceled. Your account LastSeen option remains \"Just My Followings\"!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                } else if (Client.client.myUserByUsername().getLastSeenType().equals("nobody")){
                    out: while (true){
                        System.out.println(ConsoleColors.BLUE + "Your last seen option is set as \"Nobody\" " + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + "Do you want to change it? " + ConsoleColors.RESET);
                        String is = MyScanner.getSc().next().toLowerCase();
                        if (is.equals("y")){
                            System.out.println(ConsoleColors.BLUE + "Change to:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Anyone (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Just My Followings (2)  " + ConsoleColors.RESET);
                            String changeOfLastS = MyScanner.getSc().next().toLowerCase();
                            while (true){
                                if (changeOfLastS.equals("-")){
                                    break out;
                                } else if (changeOfLastS.equals("1")){
                                    Client.client.myUserByUsername().setLastSeenType("anyone");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Anyone\"!");
                                    break out;
                                } else if (changeOfLastS.equals("2")){
                                    Client.client.myUserByUsername().setLastSeenType("justmyf");
                                    SaveAndLoad.saveUsers();
                                    System.out.println(ConsoleColors.GREEN + "Your account LastSeen option changed to \"Just My Followings\"!");
                                    break out;
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        } else if (is.equals("n")){
                            System.out.println(ConsoleColors.GREEN + "The process canceled. Your account LastSeen option remains \"Nobody\"!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                }
            }
            else if (privacyDestination.equals("3")) {
                Client.client.myUserByUsername().makeInactive();
                Client.client.makeInactive();
                SaveAndLoad.saveUsers();
                logger.info("user " + Client.client.myUserByUsername().getId() + " made him/her account inactive");
                break;
            }
            else if (privacyDestination.equals("4")) {
                Setting.changePassword();
            }
            else if (privacyDestination.equals("exit")){
                SaveAndLoad.saveAll();
                logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                System.exit(0);
            }
            else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    private static void changePassword(){
        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Change Password *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter your current password: " + ConsoleColors.RESET);
            String currentPass = MyScanner.getSc().next();
            if (currentPass.equals("-")){
                break;
            } else {
                out:
                for (User x : Index.getUsers()) {
                    if (Client.client.getId() == (x.getId())) {
                        if (currentPass.equals(x.getPassword())) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter your new password: " + ConsoleColors.RESET);
                                String newPass = MyScanner.getSc().next();
                                if (newPass.equals("-")) {
                                    break out;
                                }
                                if (newPass.equalsIgnoreCase("exit")) {
                                    System.out.println(ConsoleColors.RED + "You can't choose \"exit\" as password." + ConsoleColors.RESET);
                                    continue;
                                }
                                if (newPass.length() < 8) {
                                    System.out.println(ConsoleColors.RED + "Password must include at least 8 characters." + ConsoleColors.RESET);
                                    continue;
                                }
                                if (!Login.isValidPass(newPass)) {
                                    System.out.println(ConsoleColors.RED + "Password must only include alphabetic and numerical characters and at least one of each!" + ConsoleColors.RESET);
                                    continue;
                                }
                                x.setPassword(newPass);
                                logger.info("user " + Client.client.myUserByUsername().getId() + " changed password");
                                SaveAndLoad.saveUsers();
                                System.out.println(ConsoleColors.GREEN + "Your Password Changed successfully!" + ConsoleColors.RESET);
                                break out;
                            }
                        } else {
                            System.out.println(ConsoleColors.RED + "Your password is wrong!" + ConsoleColors.RESET);
                        }
                    }
                }
            }
        }
    }
}
