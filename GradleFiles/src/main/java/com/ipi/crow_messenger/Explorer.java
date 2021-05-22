package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;

public class Explorer {

    static Logger logger = LogManager.getLogger(Explorer.class.getName());

    public static boolean isUserByUsername(String username){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(username)){
                if (x.getUsername().equals(Client.client.myUserByUsername().getUsername())){
                    return false;
                }
                if ( !(x.isActive()) ){
                    return false;
                }
                for (User y : Index.getUsers()){
                    if (y.getId() == Client.client.getId()){
                        if (!x.hasBlocked(y.getId())){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void showUser(String username){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(username)) {
                out: while (true) {
                    System.out.print("\n" + ConsoleColors.YELLOW + x.getName() + " " + x.getLastName());
                    if (Client.client.myUserByUsername().hasFollowed(x.getId())) {
                        if (x.getLastSeenType().equals("nobody")){
                            System.out.println( " ("+ ConsoleColors.BLUE +"last seen recently"+ ConsoleColors.YELLOW +")" +ConsoleColors.RESET );
                        } else {
                            System.out.println(" (" + ConsoleColors.BLUE + "last seen at " + x.getLastSeen().toLocalDate().toString() + " " + x.getLastSeen().toLocalTime().withNano(0).withSecond(0).toString() + ConsoleColors.YELLOW + ")" + ConsoleColors.RESET);
                        }
                    } else {
                        System.out.println( " ("+ ConsoleColors.BLUE +"last seen recently"+ ConsoleColors.YELLOW +")" +ConsoleColors.RESET );
                    }
                    System.out.println(ConsoleColors.RED + x.getFollowers().size() + ConsoleColors.BLUE
                            + " Followers  " + ConsoleColors.RED + x.getFollowings().size() + ConsoleColors.BLUE
                            + " Followings  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + x.getUsername() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.YELLOW + x.getBio() + ConsoleColors.RESET);
                    if (Client.client.myUserByUsername().hasMuted(x.getId())) {
                        if (!(Client.client.myUserByUsername().hasFollowed(x.getId())) && Client.client.myUserByUsername().hasBlocked(x.getId())) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Follow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unblock (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unmute (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    while (true) {
                                        System.out.println(ConsoleColors.RED + "The user will automatically be unblocked. are you sure you want to follow? (y/n)" + ConsoleColors.RESET);
                                        String is = MyScanner.getSc().next().toLowerCase();
                                        if (is.equals("y")) {
                                            Client.client.myUserByUsername().unblock(x.getId());
                                            System.out.println(ConsoleColors.GREEN + "User unblocked successfully" + ConsoleColors.RESET);
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " unblocked user " + x.getId());
                                            if (x.isPrivateAccount()) {
                                                if (Client.client.myUserByUsername().hasRequested(x.getId())){
                                                    System.out.println(ConsoleColors.RED + "You have already requested!" + ConsoleColors.RESET);
                                                } else {
                                                    Client.client.myUserByUsername().follow(x);
                                                    SaveAndLoad.saveUsers();
                                                    logger.info("user " + Client.client.myUserByUsername().getId() + " sent a follow request to user " + x.getId());
                                                    System.out.println(ConsoleColors.GREEN + "Follow request sent!" + ConsoleColors.RESET);
                                                }
                                            } else {
                                                Client.client.myUserByUsername().follow(x);
                                                System.out.println(ConsoleColors.GREEN + "You followed the user successfully!" + ConsoleColors.RESET);
                                                SaveAndLoad.saveUsers();
                                                logger.info("user " + Client.client.myUserByUsername().getId() + " followed user " + x.getId());
                                            }
                                            break;
                                        } else if (is.equals("n")) {
                                            System.out.println(ConsoleColors.BLUE + "Follow command canceled" + ConsoleColors.RESET);
                                            break;
                                        } else {
                                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    Client.client.myUserByUsername().unblock(x.getId());
                                    System.out.println(ConsoleColors.GREEN + "User unblocked successfully!" + ConsoleColors.RESET);
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unblocked user " + x.getId());
                                    break;
                                } else if (showUserDestination.equals("3")) {
                                    Client.client.myUserByUsername().unmute(x);
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unmuted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is unmuted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if (!(Client.client.myUserByUsername().hasFollowed(x.getId())) && !(Client.client.myUserByUsername().hasBlocked(x.getId())) ) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Follow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "block (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unmute (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    if (x.isPrivateAccount()) {
                                        if (Client.client.myUserByUsername().hasRequested(x.getId())){
                                            System.out.println(ConsoleColors.RED + "You have already requested!" + ConsoleColors.RESET);
                                        } else {
                                            Client.client.myUserByUsername().follow(x);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a follow request to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Follow request sent!" + ConsoleColors.RESET);
                                        }
                                    } else {
                                        Client.client.myUserByUsername().follow(x);
                                        SaveAndLoad.saveUsers();
                                        logger.info("user " + Client.client.myUserByUsername().getId() + " followed user " + x.getId());
                                        System.out.println(ConsoleColors.GREEN + "You followed the user successfully!" + ConsoleColors.RESET);
                                    }
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    Client.client.myUserByUsername().block(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " blocked user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "User is blocked successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("3")) {
                                    Client.client.myUserByUsername().unmute(x);
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unmuted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is muted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if (Client.client.myUserByUsername().hasFollowed(x.getId())) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unfollow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "block (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Send Message (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unmute (4)    " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    Client.client.myUserByUsername().unfollow(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unfollowed user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "You unfollowed user successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    while (true) {
                                        System.out.println(ConsoleColors.RED + "The user will automatically be unfollowed. are you sure you want to block? (y/n)" + ConsoleColors.RESET);
                                        String is = MyScanner.getSc().next().toLowerCase();
                                        if (is.equals("y")) {
                                            Client.client.myUserByUsername().unfollow(x.getId());
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " unfollowed user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "User unfollowed successfully" + ConsoleColors.RESET);
                                            Client.client.myUserByUsername().block(x.getId());
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " blocked user " + x.getId());
                                            SaveAndLoad.saveUsers();
                                            System.out.println(ConsoleColors.GREEN + "User blocked successfully!" + ConsoleColors.RESET);
                                            break;
                                        } else if (is.equals("n")) {
                                            System.out.println(ConsoleColors.BLUE + "Block command canceled" + ConsoleColors.RESET);
                                            break;
                                        } else {
                                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;
                                } else if (showUserDestination.equals("3")) {

                                    System.out.println(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                                    MyScanner.getSc().nextLine();
                                    String tmpMessageText = MyScanner.getSc().nextLine();
                                    if ( !(tmpMessageText.equals("-")) ) {
                                        if (Client.client.myUserByUsername().containChatWith(x)) {
                                            Client.client.myUserByUsername().sendMessage(x, tmpMessageText);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                        } else {
                                            Client.client.myUserByUsername().newChat(x);
                                            Client.client.myUserByUsername().sendMessage(x, tmpMessageText);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;

                                } else if (showUserDestination.equals("4")) {
                                    Client.client.myUserByUsername().unmute(x);
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unmuted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is unmuted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                    }
                    else {
                        if (!(Client.client.myUserByUsername().hasFollowed(x.getId())) && Client.client.myUserByUsername().hasBlocked(x.getId())) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Follow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unblock (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Mute (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    while (true) {
                                        System.out.println(ConsoleColors.RED + "The user will automatically be unblocked. are you sure you want to follow? (y/n)" + ConsoleColors.RESET);
                                        String is = MyScanner.getSc().next().toLowerCase();
                                        if (is.equals("y")) {
                                            Client.client.myUserByUsername().unblock(x.getId());
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " unblocked user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "User unblocked successfully" + ConsoleColors.RESET);
                                            if (x.isPrivateAccount()) {
                                                if (Client.client.myUserByUsername().hasRequested(x.getId())){
                                                    System.out.println(ConsoleColors.RED + "You have already requested!" + ConsoleColors.RESET);
                                                } else {
                                                    Client.client.myUserByUsername().follow(x);
                                                    SaveAndLoad.saveUsers();
                                                    logger.info("user " + Client.client.myUserByUsername().getId() + " sent a follow request to user " + x.getId());
                                                    System.out.println(ConsoleColors.GREEN + "Follow request sent!" + ConsoleColors.RESET);
                                                }
                                            } else {
                                                Client.client.myUserByUsername().follow(x);
                                                SaveAndLoad.saveUsers();
                                                logger.info("user " + Client.client.myUserByUsername().getId() + " followed user " + x.getId());
                                                System.out.println(ConsoleColors.GREEN + "You followed the user successfully!" + ConsoleColors.RESET);
                                            }
                                            break;
                                        } else if (is.equals("n")) {
                                            System.out.println(ConsoleColors.BLUE + "Follow command canceled" + ConsoleColors.RESET);
                                            break;
                                        } else {
                                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    Client.client.myUserByUsername().unblock(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unblocked user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "User unblocked successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("3")) {
                                    Client.client.myUserByUsername().mute(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " muted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is muted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if (!(Client.client.myUserByUsername().hasFollowed(x.getId())) && !(Client.client.myUserByUsername().hasBlocked(x.getId())) ) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Follow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "block (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Mute (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    if (x.isPrivateAccount()) {
                                        if (Client.client.myUserByUsername().hasRequested(x.getId())){
                                            System.out.println(ConsoleColors.RED + "You have already requested!" + ConsoleColors.RESET);
                                        } else {
                                            Client.client.myUserByUsername().follow(x);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a follow request to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Follow request sent!" + ConsoleColors.RESET);
                                        }
                                    } else {
                                        Client.client.myUserByUsername().follow(x);
                                        SaveAndLoad.saveUsers();
                                        logger.info("user " + Client.client.myUserByUsername().getId() + " followed user " + x.getId());
                                        System.out.println(ConsoleColors.GREEN + "You followed the user successfully!" + ConsoleColors.RESET);
                                    }
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    Client.client.myUserByUsername().block(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " blocked user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "User is blocked successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("3")) {
                                    Client.client.myUserByUsername().mute(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " muted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is muted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if (Client.client.myUserByUsername().hasFollowed(x.getId())) {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do:\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Unfollow (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "block (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Send Message (3)\n" +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Mute (4)      " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                String showUserDestination = MyScanner.getSc().next().toLowerCase();
                                if (showUserDestination.equals("-")) {
                                    break out;
                                } else if (showUserDestination.equals("1")) {
                                    Client.client.myUserByUsername().unfollow(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " unfollowed user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "You unfollowed user successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("2")) {
                                    while (true) {
                                        System.out.println(ConsoleColors.RED + "The user will automatically be unfollowed. are you sure you want to block? (y/n)" + ConsoleColors.RESET);
                                        String is = MyScanner.getSc().next().toLowerCase();
                                        if (is.equals("y")) {
                                            Client.client.myUserByUsername().unfollow(x.getId());
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " unfollowed user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "User unfollowed successfully" + ConsoleColors.RESET);
                                            Client.client.myUserByUsername().block(x.getId());
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " blocked user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "User blocked successfully!" + ConsoleColors.RESET);
                                            break;
                                        } else if (is.equals("n")) {
                                            System.out.println(ConsoleColors.BLUE + "Block command canceled" + ConsoleColors.RESET);
                                            break;
                                        } else {
                                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;
                                } else if (showUserDestination.equals("3")) {

                                    System.out.println(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                                    MyScanner.getSc().nextLine();
                                    String tmpMessageText = MyScanner.getSc().nextLine();
                                    if ( !(tmpMessageText.equals("-")) ) {
                                        if (Client.client.myUserByUsername().containChatWith(x)) {
                                            Client.client.myUserByUsername().sendMessage(x, tmpMessageText);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                        } else {
                                            Client.client.myUserByUsername().newChat(x);
                                            Client.client.myUserByUsername().sendMessage(x, tmpMessageText);
                                            SaveAndLoad.saveUsers();
                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + x.getId());
                                            System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                        }
                                    }
                                    break;

                                } else if (showUserDestination.equals("4")) {
                                    Client.client.myUserByUsername().mute(x.getId());
                                    SaveAndLoad.saveUsers();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " muted user " + x.getId());
                                    System.out.println(ConsoleColors.GREEN + "The user is muted successfully!" + ConsoleColors.RESET);
                                    break;
                                } else if (showUserDestination.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                                    System.exit(0);
                                } else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void worldWideStatements() {
        System.out.println(ConsoleColors.PURPLE + "\n* WorldWide Statements *" + ConsoleColors.RESET);
        ArrayList<Statement> worldWideStatements = new ArrayList<>();
        for (User user : Index.getUsers()){
            if (user.isActive()) {
                if (user.getStatements().size() > 1) {
                    if (!(Client.client.myUserByUsername().hasMuted(user.getId()))) {
                        ArrayList<Statement> tmpUserStatement = user.getStatements();
                        tmpUserStatement.sort(Comparator.comparing(Statement::numberOfLikes).reversed());
                        worldWideStatements.add(tmpUserStatement.get(0));
                        worldWideStatements.add(tmpUserStatement.get(1));
                    }
                }
            }
        }
        worldWideStatements.sort(Comparator.comparing(Statement::numberOfLikes).reversed());

        int i = 0;
        out: while (true){
            if (worldWideStatements.size() > 0) {

                if (worldWideStatements.size() == 1){
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + worldWideStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + worldWideStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + worldWideStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(worldWideStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + worldWideStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "dislike (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {
                            if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                worldWideStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                worldWideStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            worldWideStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            worldWideStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            worldWideStatements.get(i).showComments();
                        }
                        else if (nextOrPrev.equals("exit")) {
                            SaveAndLoad.saveAll();
                            logger.info("user" + Client.client.myUserByUsername().getId() + "exited program");
                            System.exit(0);
                        }
                        else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                        }
                    }
                }
                else if ( i == (worldWideStatements.size()-1) ){
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + worldWideStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + worldWideStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + worldWideStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(worldWideStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + worldWideStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "dislike (1)           " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)    " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Previous (5)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)              " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)    " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Previous (5)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {
                            if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                worldWideStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                worldWideStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            worldWideStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            worldWideStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            worldWideStatements.get(i).showComments();
                        }
                        else if (nextOrPrev.equals("5")) {
                            i--;
                            break;
                        }
                        else if (nextOrPrev.equals("exit")) {
                            SaveAndLoad.saveAll();
                            logger.info("user" + Client.client.myUserByUsername().getId() + "exited program");
                            System.exit(0);
                        }
                        else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                        }
                    }
                }
                else if ( i == 0 ){
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + worldWideStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + worldWideStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + worldWideStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(worldWideStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + worldWideStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "dislike (1)     " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (5)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)        " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (5)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {
                            if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                worldWideStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                worldWideStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            worldWideStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            worldWideStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            worldWideStatements.get(i).showComments();
                        }
                        else if (nextOrPrev.equals("5")) {
                            i++;
                            break;
                        }
                        else if (nextOrPrev.equals("exit")) {
                            SaveAndLoad.saveAll();
                            logger.info("user" + Client.client.myUserByUsername().getId() + "exited program");
                            System.exit(0);
                        }
                        else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                        }
                    }
                }
                else {
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + worldWideStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + worldWideStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + worldWideStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(worldWideStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + worldWideStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(
                                    ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (4)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Previous (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Next (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        } else {
                            System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Add Comment (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Previous (5)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (6)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        if (nextOrPrev.equals("1")) {
                            if (worldWideStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                worldWideStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                worldWideStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        if (nextOrPrev.equals("2")) {
                            worldWideStatements.get(i).addComment();
                        }
                        if (nextOrPrev.equals("3")) {
                            worldWideStatements.get(i).showLikes();
                        }
                        if (nextOrPrev.equals("4")) {
                            worldWideStatements.get(i).showComments();
                        }
                        if (nextOrPrev.equals("5")) {
                            i--;
                            break;
                        }
                        if (nextOrPrev.equals("6")) {
                            i++;
                            break;
                        }
                        if (nextOrPrev.equals("exit")) {
                            SaveAndLoad.saveAll();
                            logger.info("user" + Client.client.myUserByUsername().getId() + "exited program");
                            System.exit(0);
                        }
                        else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                        }
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED + "\nThere is no statement to show!" + ConsoleColors.RESET);
                break;
            }
        }
    }
}


