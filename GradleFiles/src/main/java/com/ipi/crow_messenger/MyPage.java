package com.ipi.crow_messenger;

import java.time.LocalDateTime;

public class MyPage {

    public static void makeStatement(){
        System.out.println(ConsoleColors.PURPLE + "\n* Make Statement *" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE + "Type Your Statement:" + ConsoleColors.RESET);
        MyScanner.getSc().nextLine();
        String tmpStatementText = MyScanner.getSc().nextLine();
        if (!tmpStatementText.equals("-")){
            Statement tmpStatement = new Statement(Client.client.getId(), tmpStatementText, LocalDateTime.now());
            Client.client.myUserByUsername().addToStatements(tmpStatement);
            SaveAndLoad.saveUsers();
            System.out.println(ConsoleColors.GREEN + "Statement successfully made!" + ConsoleColors.RESET);
        }
    }

    public static void myStatements(){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(Client.client.getUsername())){
                int i = 0;
                out: while (true){
                    if (x.getStatements().size() > 0) {
                        System.out.println("\n" + ConsoleColors.YELLOW + x.getStatements().get(i).getText() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.RED + x.getStatements().get(i).numberOfLikes() + ConsoleColors.BLUE
                        + " Likes  " + ConsoleColors.RED + x.getStatements().get(i).numberOfComments() + ConsoleColors.BLUE
                        + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + x.getUsername() + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + (i+1) + " of " + x.getStatements().size() + ConsoleColors.RESET + "\n");

                        if (x.getStatements().size() == 1){
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                    System.out.println(
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                } else {
                                    System.out.println(
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "like (1)        " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                }

                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                        x.getStatements().get(i).dislike();
                                    } else {
                                        x.getStatements().get(i).like();
                                    }
                                    SaveAndLoad.saveUsers();
                                }
                                else if (nextOrPrev.equals("2")) {
                                    x.getStatements().get(i).addComment();
                                }
                                else if (nextOrPrev.equals("3")) {
                                    x.getStatements().get(i).editStatement();
                                }
                                else if (nextOrPrev.equals("4")) {
                                    x.getStatements().get(i).showLikes();
                                }
                                else if (nextOrPrev.equals("5")) {
                                    x.getStatements().get(i).showComments();
                                }
                                else if (nextOrPrev.equals("6")) {
                                    x.deleteStatement(x.getStatements().get(i));
                                    SaveAndLoad.saveUsers();
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if ( i == (x.getStatements().size()-1) ){
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "dislike (1)           " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Previous (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (7)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                } else {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)              " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Previous (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (7)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                }

                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                        x.getStatements().get(i).dislike();
                                    } else {
                                        x.getStatements().get(i).like();
                                    }
                                    SaveAndLoad.saveUsers();
                                }
                                else if (nextOrPrev.equals("2")) {
                                    x.getStatements().get(i).addComment();
                                }
                                else if (nextOrPrev.equals("3")) {
                                    x.getStatements().get(i).editStatement();
                                }
                                else if (nextOrPrev.equals("4")) {
                                    x.getStatements().get(i).showLikes();
                                }
                                else if (nextOrPrev.equals("5")) {
                                    x.getStatements().get(i).showComments();
                                }
                                else if (nextOrPrev.equals("6")) {
                                    i--;
                                    break;
                                }
                                else if (nextOrPrev.equals("7")) {
                                    x.deleteStatement(x.getStatements().get(i));
                                    SaveAndLoad.saveUsers();
                                    i--;
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if ( i == 0 ){
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Next (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (7)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                } else {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)        " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Next (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (7)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                }

                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                        x.getStatements().get(i).dislike();
                                    } else {
                                        x.getStatements().get(i).like();
                                    }
                                    SaveAndLoad.saveUsers();
                                }
                                else if (nextOrPrev.equals("2")) {
                                    x.getStatements().get(i).addComment();
                                }
                                else if (nextOrPrev.equals("3")) {
                                    x.getStatements().get(i).editStatement();
                                }
                                else if (nextOrPrev.equals("4")) {
                                    x.getStatements().get(i).showLikes();
                                }
                                else if (nextOrPrev.equals("5")) {
                                    x.getStatements().get(i).showComments();
                                }
                                else if (nextOrPrev.equals("6")) {
                                    i++;
                                    break;
                                }
                                else if (nextOrPrev.equals("7")) {
                                    x.deleteStatement(x.getStatements().get(i));
                                    SaveAndLoad.saveUsers();
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else {
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                    System.out.println(
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Previous (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Next (7)        " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (8)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                } else {
                                    System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + "like (1)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Edit Statement (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (4)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (5)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Previous (6)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Next (7)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Delete Statement (8)  " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                                }

                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                if (nextOrPrev.equals("1")) {
                                    if (x.getStatements().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                        x.getStatements().get(i).dislike();
                                    } else {
                                        x.getStatements().get(i).like();
                                    }
                                    SaveAndLoad.saveUsers();
                                }
                                if (nextOrPrev.equals("2")) {
                                    x.getStatements().get(i).addComment();
                                }
                                if (nextOrPrev.equals("3")) {
                                    x.getStatements().get(i).editStatement();
                                }
                                if (nextOrPrev.equals("4")) {
                                    x.getStatements().get(i).showLikes();
                                }
                                if (nextOrPrev.equals("5")) {
                                    x.getStatements().get(i).showComments();
                                }
                                if (nextOrPrev.equals("6")) {
                                    i--;
                                    break;
                                }
                                if (nextOrPrev.equals("7")) {
                                    i++;
                                    break;
                                }
                                if (nextOrPrev.equals("8")) {
                                    x.deleteStatement(x.getStatements().get(i));
                                    SaveAndLoad.saveUsers();
                                    i--;
                                    break;
                                }
                                if (nextOrPrev.equals("exit")) {
                                    SaveAndLoad.saveAll();
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                    } else {
                        System.out.println(ConsoleColors.RED + "\nYou have no statement" + ConsoleColors.RESET);
                        break;
                    }
                }
                break;
            }
        }
    }

    public static void myBlackList(){
        out:
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* Black List *" + ConsoleColors.RESET);
            User x = Client.client.myUserByUsername();
            if (x.getBlackList().size() > 0) {
                for (long userId : x.getBlackList()){
                    System.out.println(
                            ConsoleColors.RED + ">" +
                            ConsoleColors.BLUE + User.getUserById(userId).getUsername() +
                            ConsoleColors.YELLOW + "  (" +
                            ConsoleColors.BLUE + User.getUserById(userId).getName() + " " + User.getUserById(userId).getLastName() +
                            ConsoleColors.YELLOW + ")" + ConsoleColors.RESET);
                }
                while (true){
                System.out.println(ConsoleColors.BLUE + "\nEnter a username to unblock: " + ConsoleColors.RESET);
                String blackListDestination = MyScanner.getSc().next().toLowerCase();

                    if (blackListDestination.equals("-")){
                        break out;
                    } else if (x.getBlackList().contains(User.findUserByUsername(blackListDestination).getId())){
                        x.removeFromBlackList(User.findUserByUsername(blackListDestination).getId());
                        SaveAndLoad.saveUsers();
                        System.out.println(ConsoleColors.GREEN + "User removed from Blacklist successfully!" + ConsoleColors.RESET);
                        break;
                    } else {
                        System.out.println(ConsoleColors.RED + "username isn't in your Blacklist!" + ConsoleColors.RESET);
                    }
                }

            } else {
                System.out.println(ConsoleColors.RED + "Your BlackList is empty!" + ConsoleColors.RESET);
                break;
            }
        }
    }

    public static void myFollowers(){
        out:
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* My Followers *" + ConsoleColors.RESET);
            User x = Client.client.myUserByUsername();
            if (x.getFollowers().size() > 0) {
                for (long userId : x.getFollowers()){
                    System.out.println(
                            ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + User.getUserById(userId).getUsername() +
                                    ConsoleColors.YELLOW + "  (" +
                                    ConsoleColors.BLUE + User.getUserById(userId).getName() + " " + User.getUserById(userId).getLastName() +
                                    ConsoleColors.YELLOW + ")" + ConsoleColors.RESET);
                }
                while (true){
                    System.out.println(ConsoleColors.BLUE + "\nEnter \"-\" to go back: " + ConsoleColors.RESET);
                    String isBack = MyScanner.getSc().next().toLowerCase();

                    if (isBack.equals("-")){
                        break out;
                    } else {
                        System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED + "Your have no followers " + ConsoleColors.YELLOW + ":(" + ConsoleColors.RESET);
                break;
            }
        }
    }

    public static void myFollowings(){
        out:
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* My Followings *" + ConsoleColors.RESET);
            User x = Client.client.myUserByUsername();
            if (x.getFollowings().size() > 0) {
                for (long userId : x.getFollowings()){
                    System.out.println(
                            ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + User.getUserById(userId).getUsername() +
                                    ConsoleColors.YELLOW + "  (" +
                                    ConsoleColors.BLUE + User.getUserById(userId).getName() + " " + User.getUserById(userId).getLastName() +
                                    ConsoleColors.YELLOW + ")" + ConsoleColors.RESET);
                }
                while (true){
                    System.out.println(ConsoleColors.BLUE + "\nEnter a username to unfollow: " + ConsoleColors.RESET);
                    String followingsDestination = MyScanner.getSc().next().toLowerCase();

                    if (followingsDestination.equals("-")){
                        break out;
                    } else if (x.getFollowings().contains(User.findUserByUsername(followingsDestination).getId())){
                        x.unfollow(User.findUserByUsername(followingsDestination).getId());
                        SaveAndLoad.saveUsers();
                        System.out.println(ConsoleColors.GREEN + "User unfollowed successfully!" + ConsoleColors.RESET);
                        break;
                    } else {
                        System.out.println(ConsoleColors.RED + "username isn't in your followings!" + ConsoleColors.RESET);
                    }
                }

            } else {
                System.out.println(ConsoleColors.RED + "Your haven't followed anyone!" + ConsoleColors.RESET);
                break;
            }
        }
    }

    public static void editInfo() {
        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Edit Info *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
            System.out.println(
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Name (1)          " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Last Name (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Username (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Phone Number (4)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Email (5)    " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Bio (6)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Birth Date (7)    " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String editDestination = MyScanner.getSc().next().toLowerCase();

            if (editDestination.equals("-")){
                break;
            }
            else if (editDestination.equals("1")){
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Your current name is: " + Client.client.myUserByUsername().getName() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter new name: " + ConsoleColors.RESET);
                    String tmpName = MyScanner.getSc().next();
                    if (tmpName.equals("-")) {
                        break;
                    }
                    if (Login.haveNum(tmpName)) {
                        System.out.println(ConsoleColors.RED + "Name must only have alphabetic characters!" + ConsoleColors.RESET);
                        continue;
                    }
                    Client.client.myUserByUsername().setName(tmpName);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your name changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("2")){
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Your current last name is: " + Client.client.myUserByUsername().getName() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter new last name: " + ConsoleColors.RESET);
                    String tmpLast = MyScanner.getSc().next();
                    if (tmpLast.equals("-")) {
                        break;
                    }
                    if (Login.haveNum(tmpLast)) {
                        System.out.println(ConsoleColors.RED + "Last name must only have alphabetic characters!" + ConsoleColors.RESET);
                        continue;
                    }
                    Client.client.myUserByUsername().setLastName(tmpLast);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your last name changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("3")){
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Your current username is: " + Client.client.myUserByUsername().getName() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter new username: " + ConsoleColors.RESET);
                    String tmpUsername = MyScanner.getSc().next().toLowerCase();
                    if (tmpUsername.equals("-")) {
                        break;
                    }
                    if (User.isUser(tmpUsername)) {
                        System.out.println(ConsoleColors.RED + "This username is already chosen!" + ConsoleColors.RESET);
                        continue;
                    }
                    Client.client.myUserByUsername().setUsername(tmpUsername);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your username changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("4")){
                while (true) {
                    if (Client.client.myUserByUsername().getPhoneNumber() == null) {
                        System.out.println(ConsoleColors.BLUE + "Your current phone number is: " + ConsoleColors.RED + "None" + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.BLUE + "Your current phone number is: " + Client.client.myUserByUsername().getPhoneNumber() + ConsoleColors.RESET);
                    }
                    System.out.println(ConsoleColors.BLUE + "Enter new phone number: " + ConsoleColors.RESET);
                    String tmpPhoneNumber = MyScanner.getSc().next().toLowerCase();
                    if (tmpPhoneNumber.equals("-")) {
                        break;
                    }
                    if (User.isPhoned(tmpPhoneNumber)) {
                        System.out.println(ConsoleColors.RED + "This phone number is already used!" + ConsoleColors.RESET);
                        continue;
                    }
                    Client.client.myUserByUsername().setPhoneNumber(tmpPhoneNumber);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your phone number changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("5")){
                while (true) {
                    if (Client.client.myUserByUsername().getEmail() == null) {
                        System.out.println(ConsoleColors.BLUE + "Your current email is: " + ConsoleColors.RED + "None" + ConsoleColors.RESET);
                    } else {
                        System.out.println(ConsoleColors.BLUE + "Your current email is: " + Client.client.myUserByUsername().getEmail() + ConsoleColors.RESET);
                    }
                    System.out.println(ConsoleColors.BLUE + "Enter new email: " + ConsoleColors.RESET);
                    String tmpEmail = MyScanner.getSc().next().toLowerCase();
                    if (tmpEmail.equals("-")) {
                        break;
                    }
                    if (User.isEmailed(tmpEmail)) {
                        System.out.println(ConsoleColors.RED + "This email is already used!" + ConsoleColors.RESET);
                        continue;
                    }
                    Client.client.myUserByUsername().setEmail(tmpEmail);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your email changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("6")){
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Your current bio is:\n" + Client.client.myUserByUsername().getBio() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter new bio: (\"-\" to go cancel)" + ConsoleColors.RESET);
                    MyScanner.getSc().nextLine();
                    String tmpBio = MyScanner.getSc().nextLine();
                    if (tmpBio.equals("-")) {
                        break;
                    }
                    Client.client.myUserByUsername().setBio(tmpBio);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your bio changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("7")){
                while (true) {
                    System.out.println(ConsoleColors.BLUE + "Your current birthday is:\n" + Client.client.myUserByUsername().getBio() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + "Enter new birthday: " + ConsoleColors.RESET);
                    String tmpBirth = MyScanner.getSc().next();
                    if (tmpBirth.equals("-")) {
                        break;
                    }
                    Client.client.myUserByUsername().setBirthDate(tmpBirth);
                    SaveAndLoad.saveUsers();
                    System.out.println(ConsoleColors.GREEN + "Your birthday changed successfully!" + ConsoleColors.RESET);
                    break;
                }
            }
            else if (editDestination.equals("exit")){
                SaveAndLoad.saveAll();
                System.exit(0);
            }
            else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    public static void myInfo(){
        System.out.println(ConsoleColors.PURPLE + "\n* My Info *" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "@" + ConsoleColors.YELLOW + Client.client.myUserByUsername().getUsername() + ConsoleColors.RED + "  (" + ConsoleColors.YELLOW + Client.client.myUserByUsername().getName() + " " + Client.client.myUserByUsername().getLastName() + ConsoleColors.RED + ")" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "Bio: " + ConsoleColors.YELLOW + Client.client.myUserByUsername().getBio() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + Client.client.myUserByUsername().getFollowers().size() + ConsoleColors.YELLOW
                + " Followers  " + ConsoleColors.RED + Client.client.myUserByUsername().getFollowings().size() + ConsoleColors.YELLOW + " Followings" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + Client.client.myUserByUsername().getStatements().size() + ConsoleColors.YELLOW
                + " Statements  " + ConsoleColors.RED + Client.client.myUserByUsername().averageLike() + ConsoleColors.YELLOW + " Average Likes" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.RED + "Email: " + ConsoleColors.YELLOW + Client.client.myUserByUsername().getEmail() + ConsoleColors.RESET);
        if (Client.client.myUserByUsername().getPhoneNumber() == null){
            System.out.println(ConsoleColors.RED + "Phone Number: " + ConsoleColors.YELLOW + "None" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Phone Number: " + ConsoleColors.YELLOW + Client.client.myUserByUsername().getPhoneNumber() + ConsoleColors.RESET);
        }
        if (Client.client.myUserByUsername().getBirthDate() == null){
            System.out.println(ConsoleColors.RED + "Birth Date: " + ConsoleColors.YELLOW + "None" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Birth Date: " + ConsoleColors.YELLOW + Client.client.myUserByUsername().getBirthDate() + ConsoleColors.RESET);
        }
    }

    public static void notifications(){
        while (true) {
            System.out.println(ConsoleColors.PURPLE + "\n* Notifications *" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Follow Requests (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "My Pending Requests (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "System Notifications (3)\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String notificationDestination = MyScanner.getSc().next();
            if (notificationDestination.equals("-")){
                break;
            } else if (notificationDestination.equals("1")){
                NotificationPage.showRequests();
            } else if (notificationDestination.equals("2")){
                NotificationPage.showMyRequests();
            } else if (notificationDestination.equals("3")){
                NotificationPage.showSysNotification();
            } else if (notificationDestination.equalsIgnoreCase("exit")){
                SaveAndLoad.saveAll();
                System.exit(0);
            } else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

}
