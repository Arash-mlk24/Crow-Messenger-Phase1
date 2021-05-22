package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Comparator;

public class HomePage {

    static Logger logger = LogManager.getLogger(HomePage.class.getName());

    public static void myPage(){
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* My Page *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Make a statement (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My statements (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My blacklist (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My followers (4)      " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My followings (5)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Edit info (6)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My info (7)           " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Notifications (8)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String myPageDestination = MyScanner.getSc().next().toLowerCase();

            if (myPageDestination.equals("-")){
                break;
            }

            if (myPageDestination.equals("1")){
                MyPage.makeStatement();
                continue;
            }

            if (myPageDestination.equals("2")){
                MyPage.myStatements();
                continue;
            }

            if (myPageDestination.equals("3")){
                MyPage.myBlackList();
                continue;
            }

            if (myPageDestination.equals("4")){
                MyPage.myFollowers();
                continue;
            }

            if (myPageDestination.equals("5")){
                MyPage.myFollowings();
                continue;
            }

            if (myPageDestination.equals("6")){
                MyPage.editInfo();
                continue;
            }

            if (myPageDestination.equals("7")){
                MyPage.myInfo();
                continue;
            }

            if (myPageDestination.equals("8")){
                MyPage.notifications();
                continue;
            }

            if (myPageDestination.equals("exit")){
                System.exit(0);
            }
            else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    public static void timeLine(){
        System.out.println(ConsoleColors.PURPLE + "\n* Timeline *" + ConsoleColors.RESET);
        ArrayList<Statement> timelineStatements = new ArrayList<>();
        for (long myFollowingId : Client.client.myUserByUsername().getFollowings()){
            if ( !(Client.client.myUserByUsername().hasMuted(myFollowingId)) ) {
                if (User.getUserById(myFollowingId).getStatements().size() > 0) {
                    timelineStatements.addAll(User.getUserById(myFollowingId).getStatements());
                }
            }
        }
        timelineStatements.sort(Comparator.comparing(Statement::getDateTime).reversed());

        int i = 0;
        out: while (true){
            if (timelineStatements.size() > 0) {

                if (timelineStatements.size() == 1){
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + timelineStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + timelineStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + timelineStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(timelineStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + timelineStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                            if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                timelineStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                timelineStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            timelineStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            timelineStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            timelineStatements.get(i).showComments();
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
                else if ( i == (timelineStatements.size()-1) ){
                    while (true) {
                        System.out.println("\n" + ConsoleColors.YELLOW + timelineStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + timelineStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + timelineStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(timelineStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + timelineStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                            if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                timelineStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                timelineStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            timelineStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            timelineStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            timelineStatements.get(i).showComments();
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
                        System.out.println("\n" + ConsoleColors.YELLOW + timelineStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + timelineStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + timelineStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(timelineStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + timelineStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                            if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                timelineStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                timelineStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            timelineStatements.get(i).addComment();
                        }
                        else if (nextOrPrev.equals("3")) {
                            timelineStatements.get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            timelineStatements.get(i).showComments();
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
                        System.out.println("\n" + ConsoleColors.YELLOW + timelineStatements.get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + timelineStatements.get(i).numberOfLikes() + ConsoleColors.BLUE
                                + " Likes  " + ConsoleColors.RED + timelineStatements.get(i).numberOfComments() + ConsoleColors.BLUE
                                + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(timelineStatements.get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + timelineStatements.size() + ConsoleColors.RESET + "\n");

                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                            if (timelineStatements.get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                timelineStatements.get(i).dislike();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "disliked a message");
                            } else {
                                timelineStatements.get(i).like();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "liked a message");
                            }
                            SaveAndLoad.saveUsers();
                        }
                        if (nextOrPrev.equals("2")) {
                            timelineStatements.get(i).addComment();
                        }
                        if (nextOrPrev.equals("3")) {
                            timelineStatements.get(i).showLikes();
                        }
                        if (nextOrPrev.equals("4")) {
                            timelineStatements.get(i).showComments();
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

    public static void explorer(){
        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Explorer *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Find Users (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Worldwide Statements (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String explorerDestination = MyScanner.getSc().next().toLowerCase();

            if (explorerDestination.equals("-")){
                break;
            } else if (explorerDestination.equals("1")){
                while (true) {
                    System.out.println(ConsoleColors.PURPLE + "\n* User Search *" + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter the username you are looking for: ");
                    String tmpSearched = MyScanner.getSc().next().toLowerCase();
                    if (tmpSearched.equals("-")) {
                        break;
                    }
                    boolean selfSearched = false;
                    for (User x : Index.getUsers()) {
                        if (x.getUsername().equals(tmpSearched)) {
                            if (x.getUsername().equals(Client.client.myUserByUsername().getUsername())) {
                                selfSearched = true;
                            }
                        }
                    }
                    if (selfSearched){
                        System.out.println(ConsoleColors.BLUE + "This is your own account. it is accessible from \"My Page\"!" + ConsoleColors.RESET);
                    }
                    else if (Explorer.isUserByUsername(tmpSearched)) {
                        Explorer.showUser(tmpSearched);
                    } else {
                        System.out.println(ConsoleColors.RED + "No user found!" + ConsoleColors.RESET);
                    }
                }
            } else if (explorerDestination.equals("2")){
                Explorer.worldWideStatements();
                break;
            } else if (explorerDestination.equals("exit")){
                SaveAndLoad.saveAll();
                logger.info("user" + Client.client.myUserByUsername().getId() + "exited program");
                System.exit(0);
            } else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    public static void chats(){
        out:
        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Chats *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Start new chat (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My chats (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Saved Messages (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String chatsDestination = MyScanner.getSc().next().toLowerCase();
            if (chatsDestination.equals("-")){
                break;
            } else if (chatsDestination.equals("1")){
                while (true){
                    System.out.println(ConsoleColors.PURPLE + "\n* New Chat *" + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "New Category (1)  " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "My Categories (2)  " +
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + "Uncategorized Messaging (3)" + ConsoleColors.RESET);
                    String newMessDestination = MyScanner.getSc().next().toLowerCase();

                    if (newMessDestination.equals("-")){
                        break;
                    } else if (newMessDestination.equals("1")){
                        while (true) {
                            System.out.println(ConsoleColors.PURPLE + "\n* New Category *" + ConsoleColors.RESET);
                            System.out.println(ConsoleColors.BLUE + "Enter the name of the category: (with no spaces)" + ConsoleColors.RESET);
                            String tmpCategoryName = MyScanner.getSc().next().toLowerCase();
                            if (tmpCategoryName.equals("-")) {
                                break;
                            } else if (Client.client.myUserByUsername().containCategoryByName(tmpCategoryName)){
                                System.out.println(ConsoleColors.RED + "Category already exist!" + ConsoleColors.RESET);
                            } else {
                                Client.client.myUserByUsername().createCategory(tmpCategoryName);
                                SaveAndLoad.saveUsers();
                                logger.info("user" + Client.client.myUserByUsername().getId() + "created a category named " + tmpCategoryName);
                                System.out.println(ConsoleColors.GREEN + "Category created!" + ConsoleColors.RESET);
                                break;
                            }
                        }
                    } else if (newMessDestination.equals("2")){
                        if (Client.client.myUserByUsername().getCategories().size() > 0) {
                            while (true) {
                                System.out.println(ConsoleColors.PURPLE + "\n* My Categories *" + ConsoleColors.RESET);
                                for (UserCategory category : Client.client.myUserByUsername().getCategories()) {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + category.getName());
                                }
                                System.out.println("Enter the category you want:" + ConsoleColors.RESET);
                                String tmpCategory = MyScanner.getSc().next().toLowerCase();
                                if (tmpCategory.equals("-")){
                                    break;
                                } else if (Client.client.myUserByUsername().containCategoryByName(tmpCategory)) {
                                    if (Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId().isEmpty()){
                                        in:
                                        while (true) {
                                            System.out.println(ConsoleColors.BLUE + "\nUsers in \"" + tmpCategory + "\" category: " + ConsoleColors.RESET);
                                            for (long memberId : Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId()) {
                                                System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + User.getUserById(memberId));
                                            }
                                            System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do: (\"-\" to go back)\n" +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Add members (1)  " +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Delete category (2)" + ConsoleColors.RESET);
                                            String categoryDestination = MyScanner.getSc().next();
                                            if (categoryDestination.equals("-")) {
                                                break;
                                            } else if (categoryDestination.equals("1")) {
                                                ArrayList<Long> usersIdToSend = new ArrayList<>();
                                                while (true) {
                                                    System.out.println(ConsoleColors.BLUE + "\nEnter one of your followings username:" + ConsoleColors.RESET);
                                                    String tmpUsername = MyScanner.getSc().next();
                                                    if (tmpUsername.equals("-")){
                                                        continue in;
                                                    } else if (tmpUsername.equals("+")){
                                                        break;
                                                    } else if (Client.client.myUserByUsername().hasFollowed(User.findUserByUsername(tmpUsername).getId())){
                                                        if (Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId().contains(User.findUserByUsername(tmpUsername).getId())){
                                                            System.out.println(ConsoleColors.RED + "This account is already in this category!" + ConsoleColors.RESET);
                                                        } else if (usersIdToSend.contains(User.findUserByUsername(tmpUsername).getId())){
                                                            System.out.println(ConsoleColors.RED + "You have already chosen this account to add!" + ConsoleColors.RESET);
                                                        } else {
                                                            usersIdToSend.add(User.findUserByUsername(tmpUsername).getId());
                                                        }
                                                    } else {
                                                        System.out.println(ConsoleColors.RED + "This username doesn't belong to any of your followings!" + ConsoleColors.RESET);
                                                    }
                                                }
                                                if (usersIdToSend.isEmpty()){
                                                    System.out.println(ConsoleColors.RED + "You haven't chosen any user to add!" + ConsoleColors.RESET);
                                                } else {
                                                    Client.client.myUserByUsername().findCategory(tmpCategory).addToCategory(usersIdToSend);
                                                    SaveAndLoad.saveUsers();
                                                    System.out.println(ConsoleColors.GREEN + "Users are successfully added to this category!" + ConsoleColors.RESET);
                                                    break;
                                                }
                                            }  else if (categoryDestination.equals("2")) {
                                                Client.client.myUserByUsername().removeCategory(tmpCategory);
                                                break;
                                            } else {
                                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                            }
                                        }
                                    } else {
                                        in:
                                        while (true) {
                                            System.out.println(ConsoleColors.BLUE + "\nUsers in \"" + tmpCategory + "\" category: " + ConsoleColors.RESET);
                                            for (long memberId : Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId()) {
                                                System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + User.getUserById(memberId));
                                            }
                                            System.out.println(ConsoleColors.BLUE + "\nEnter what you want to do: (\"-\" to go back)\n" +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Add members (1)  " +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Remove members (2)  " +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Send message (3)\n" +
                                                    ConsoleColors.RED + ">" +
                                                    ConsoleColors.BLUE + "Delete category (4)" + ConsoleColors.RESET);
                                            String categoryDestination = MyScanner.getSc().next();
                                            if (categoryDestination.equals("-")) {
                                                break;
                                            } else if (categoryDestination.equals("1")) {
                                                ArrayList<Long> usersIdToSend = new ArrayList<>();
                                                while (true) {
                                                    System.out.println(ConsoleColors.BLUE + "\nEnter one of your followings username:" + ConsoleColors.RESET);
                                                    String tmpUsername = MyScanner.getSc().next();
                                                    if (tmpUsername.equals("-")){
                                                        continue in;
                                                    } else if (tmpUsername.equals("+")){
                                                        break;
                                                    } else if (Client.client.myUserByUsername().hasFollowed(User.findUserByUsername(tmpUsername).getId())){
                                                        if (Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId().contains(User.findUserByUsername(tmpUsername).getId())){
                                                            System.out.println(ConsoleColors.RED + "This account is already in this category!" + ConsoleColors.RESET);
                                                        } else if (usersIdToSend.contains(User.findUserByUsername(tmpUsername).getId())){
                                                            System.out.println(ConsoleColors.RED + "You have already chosen this account to add!" + ConsoleColors.RESET);
                                                        } else {
                                                            usersIdToSend.add(User.findUserByUsername(tmpUsername).getId());
                                                        }
                                                    } else {
                                                        System.out.println(ConsoleColors.RED + "This username doesn't belong to any of your followings!" + ConsoleColors.RESET);
                                                    }
                                                }
                                                if (usersIdToSend.isEmpty()){
                                                    System.out.println(ConsoleColors.RED + "You haven't chosen any user to add!" + ConsoleColors.RESET);
                                                } else {
                                                    Client.client.myUserByUsername().findCategory(tmpCategory).addToCategory(usersIdToSend);
                                                    SaveAndLoad.saveUsers();
                                                    System.out.println(ConsoleColors.GREEN + "Users are successfully added to this category!" + ConsoleColors.RESET);
                                                }
                                            } else if (categoryDestination.equals("2")) {
                                                ArrayList<Long> usersIdToRemove = new ArrayList<>();
                                                while (true) {
                                                    System.out.println(ConsoleColors.BLUE + "\nEnter one of this category members username:" + ConsoleColors.RESET);
                                                    String tmpUsername = MyScanner.getSc().next();
                                                    if (tmpUsername.equals("-")){
                                                        continue in;
                                                    } else if (tmpUsername.equals("+")){
                                                        break;
                                                    } else if (usersIdToRemove.contains(User.findUserByUsername(tmpUsername).getId())){
                                                        System.out.println(ConsoleColors.RED + "You have already chosen this account to add!" + ConsoleColors.RESET);
                                                    } else if (Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId().contains(User.findUserByUsername(tmpUsername).getId())){
                                                        usersIdToRemove.add(User.findUserByUsername(tmpUsername).getId());
                                                    } else {
                                                        System.out.println(ConsoleColors.RED + "This account is not in this category!" + ConsoleColors.RESET);
                                                    }
                                                }
                                                if (usersIdToRemove.isEmpty()){
                                                    System.out.println(ConsoleColors.RED + "You haven't chosen any user to remove!" + ConsoleColors.RESET);
                                                } else {
                                                    Client.client.myUserByUsername().findCategory(tmpCategory).removeFromCategory(usersIdToRemove);
                                                    SaveAndLoad.saveUsers();
                                                    System.out.println(ConsoleColors.GREEN + "Users are successfully removed this category!" + ConsoleColors.RESET);
                                                }
                                            } else if (categoryDestination.equals("3")) {
                                                System.out.println(ConsoleColors.BLUE + "\nEnter your message:" + ConsoleColors.RESET);
                                                MyScanner.getSc().nextLine();
                                                String tmpMessage = MyScanner.getSc().nextLine();
                                                if ( !tmpMessage.equals("-") ){
                                                    for (long userId : Client.client.myUserByUsername().findCategory(tmpCategory).getMembersId()){
                                                        if (Client.client.myUserByUsername().containChatWith(User.getUserById(userId))) {
                                                            Client.client.myUserByUsername().sendMessage(User.getUserById(userId), tmpMessage);
                                                            SaveAndLoad.saveUsers();
                                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + userId);
                                                        } else {
                                                            Client.client.myUserByUsername().newChat(User.getUserById(userId));
                                                            Client.client.myUserByUsername().sendMessage(User.getUserById(userId), tmpMessage);
                                                            SaveAndLoad.saveUsers();
                                                            logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + userId);
                                                        }
                                                    }
                                                    System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                                    SaveAndLoad.saveUsers();
                                                    break;
                                                }
                                            } else if (categoryDestination.equals("4")) {
                                                Client.client.myUserByUsername().removeCategory(tmpCategory);
                                                SaveAndLoad.saveUsers();
                                                break;
                                            } else {
                                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println(ConsoleColors.RED + "There is no category with this name!" + ConsoleColors.RESET);
                                }
                            }
                        } else {
                            System.out.println(ConsoleColors.RED + "You have no categories!" + ConsoleColors.RESET);
                        }
                    } else if (newMessDestination.equals("3")){

                        out1:
                        while (true) {
                            ArrayList<User> usersToSend = new ArrayList<>();
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter a username: (if done of adding, enter \"+\")" + ConsoleColors.RESET);
                                String tmpUsername = MyScanner.getSc().next().toLowerCase();
                                if (tmpUsername.equals("-")) {
                                    break out1;
                                } else if (tmpUsername.equals("+")){
                                    break;
                                } else if (User.isUserByUsername(tmpUsername)) {
                                    if (Client.client.myUserByUsername().getUsername().equals(tmpUsername)){
                                        if (usersToSend.contains(Client.client.myUserByUsername())){
                                            System.out.println(ConsoleColors.RED + "User already selected!" + ConsoleColors.RESET);
                                        } else {
                                            usersToSend.add(Client.client.myUserByUsername());
                                        }
                                    } else if (Client.client.myUserByUsername().hasFollowed(User.findUserByUsername(tmpUsername).getId())) {
                                        if (usersToSend.contains(User.findUserByUsername(tmpUsername))){
                                            System.out.println(ConsoleColors.RED + "User already selected!" + ConsoleColors.RESET);
                                        } else {
                                            usersToSend.add(User.findUserByUsername(tmpUsername));
                                        }
                                    } else {
                                        System.out.println(ConsoleColors.RED + "You haven't followed this user!" + ConsoleColors.RESET);
                                    }
                                } else {
                                    System.out.println(ConsoleColors.RED + "The username you've entered doesn't exist!" + ConsoleColors.RESET);
                                }
                            }
                            if (usersToSend.size() > 0) {
                                System.out.println(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                                MyScanner.getSc().nextLine();
                                String tmpMessageText = MyScanner.getSc().nextLine();
                                for (User receiver : usersToSend){
                                    if (Client.client.myUserByUsername().containChatWith(receiver)) {
                                        Client.client.myUserByUsername().sendMessage(receiver, tmpMessageText);
                                        SaveAndLoad.saveUsers();
                                        logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + receiver.getId());
                                    } else {
                                        Client.client.myUserByUsername().newChat(receiver);
                                        Client.client.myUserByUsername().sendMessage(receiver, tmpMessageText);
                                        SaveAndLoad.saveUsers();
                                        logger.info("user " + Client.client.myUserByUsername().getId() + " sent a message to user " + receiver.getId());
                                    }
                                }
                                System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                break;
                            } else {
                                System.out.println(ConsoleColors.RED + "You haven't chosen any user!");
                            }
                        }

                    } else {
                        System.out.println(ConsoleColors.RED + "Your input is invalid!");
                    }
                }
            }
            else if (chatsDestination.equals("2")) {
                if (Client.client.myUserByUsername().getChats().size() > 0) {
                    while (true) {
                        System.out.println(ConsoleColors.PURPLE + "\n* My Chats *" + ConsoleColors.RESET);
                        ArrayList<Chat> sortedChat = Client.client.myUserByUsername().getChats();
                        sortedChat.sort(Comparator.comparing(Chat::countUnseenMessages).reversed());

                        for (Chat chat : sortedChat){
                            if ( !(chat.getOtherId() == Client.client.getId()) )
                            System.out.println(
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + User.getUserById(chat.getOtherId()).getUsername() + ConsoleColors.RESET +
                                    "  " + ConsoleColors.RED + chat.countUnseenMessages()
                            );
                        }
                        System.out.println(ConsoleColors.BLUE + "Enter a username to select its chat: (Your username to save a message!)");
                        String whichChat = MyScanner.getSc().next().toLowerCase();
                        if (whichChat.equals("-")) {
                            break;
                        } else if (whichChat.equals(Client.client.myUserByUsername().getUsername())) {
                            System.out.println(ConsoleColors.RED + "This is your username. access your saved messages in \"saved messages\" page!" + ConsoleColors.RESET);;
                        } else if (Client.client.myUserByUsername().getChats().contains(Client.client.myUserByUsername().findChatByUsername(whichChat))) {
                            Client.client.myUserByUsername().showChat(Client.client.myUserByUsername().findChatByUsername(whichChat));
                        } else {
                            System.out.println(ConsoleColors.RED + "The username is not in your chats list!" + ConsoleColors.RESET);
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED + "You Have no Chats!" + ConsoleColors.RESET);
                }
            }
            else if (chatsDestination.equals("3")){
                Chat.savedMessages();
            } else if (chatsDestination.equals("exit")){
                System.exit(0);
            } else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    public static void setting() {
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* Setting *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Privacy Setting (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "LogOut (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "deleteAccount (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String settingDestination = MyScanner.getSc().next().toLowerCase();

            if (settingDestination.equals("-")){
                break;
            }
            if (settingDestination.equals("1")){
                Setting.privacy();
                if (Client.client.isActive()){
                    continue;
                }
                break;
            }
            if (settingDestination.equals("2")){
                Setting.logOut();
                break;
            }
            if (settingDestination.equals("3")){
                boolean deleted;
                System.out.println(ConsoleColors.BLUE + "This will delete your account permanently." + ConsoleColors.RED + " Are you sure? (y/n)" + ConsoleColors.RESET);
                while (true) {
                    String is = MyScanner.getSc().next();
                    if (is.length() > 1) {
                        System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                    } else {
                        if (Character.toLowerCase(is.charAt(0)) == 'y') {
                            Setting.deleteAccount();
                            SaveAndLoad.saveUsers();
                            deleted = true;
                            System.out.println(ConsoleColors.GREEN + "Your account " + ConsoleColors.RED + "deleted" + ConsoleColors.GREEN + " successfully" + ConsoleColors.RESET);
                            break;
                        } else if (Character.toLowerCase(is.charAt(0)) == 'n') {
                            System.out.println(ConsoleColors.BLUE + "It is nice not to lose you!" + ConsoleColors.RESET);
                            deleted = false;
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                }
                if (deleted){
                    break;
                } else {
                    continue;
                }
            }
            if (settingDestination.equals("exit")){
                SaveAndLoad.saveAll();
                logger.info("user " + Client.client.myUserByUsername().getId() + " exited program");
                System.exit(0);
            }
            else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

}
