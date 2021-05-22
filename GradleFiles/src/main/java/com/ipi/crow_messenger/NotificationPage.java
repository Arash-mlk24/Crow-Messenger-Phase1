package com.ipi.crow_messenger;

import java.util.ArrayList;

public class NotificationPage {

    public static void showRequests(){
        if (Client.client.myUserByUsername().getFollowReqs().size() > 0) {
            while (true){
                int i = Client.client.myUserByUsername().getFollowReqs().size();
                while ( i > 0 ) {
                    System.out.println(
                            ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + User.getUserById(Client.client.myUserByUsername().getFollowReqs().get(i - 1)).getUsername() +
                                    ConsoleColors.YELLOW + "  (" +
                                    ConsoleColors.BLUE + User.getUserById(Client.client.myUserByUsername().getFollowReqs().get(i - 1)).getName() + " " + User.getUserById(Client.client.myUserByUsername().getFollowReqs().get(i - 1)).getLastName() +
                                    ConsoleColors.YELLOW + ")" + ConsoleColors.RESET
                    );
                    i--;
                }
                System.out.println(ConsoleColors.BLUE + "Select a username:" + ConsoleColors.RESET);
                String reqDestination = MyScanner.getSc().next().toLowerCase();
                if (reqDestination.equals("-")) {
                    break;
                } else if (reqDestination.equals("exit")) {
                    System.exit(0);
                } else if (Client.client.myUserByUsername().getFollowReqs().contains(User.findUserByUsername(reqDestination).getId())) {
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                                ConsoleColors.RED + ">" +
                                ConsoleColors.BLUE + "Accept (1)  " +
                                ConsoleColors.RED + ">" +
                                ConsoleColors.BLUE + "Reject (2)  " +
                                ConsoleColors.RED + ">" +
                                ConsoleColors.BLUE + "Reject( don't notify ) (3)  " + ConsoleColors.RESET);
                        String acceptOrReject = MyScanner.getSc().next().toLowerCase();
                        if (acceptOrReject.equals("-")) {
                            break;
                        } else if (acceptOrReject.equals("1")) {
                            Client.client.myUserByUsername().acceptRequest(User.findUserByUsername(reqDestination));
                            SaveAndLoad.saveUsers();
                            System.out.println(ConsoleColors.GREEN + "Request accepted!" + ConsoleColors.RESET);
                            break;
                        } else if (acceptOrReject.equals("2")) {
                            Client.client.myUserByUsername().rejectRequest(User.findUserByUsername(reqDestination));
                            SaveAndLoad.saveUsers();
                            System.out.println(ConsoleColors.GREEN + "Request rejected!" + ConsoleColors.RESET);                            break;
                        } else if (acceptOrReject.equals("3")) {
                            Client.client.myUserByUsername().rejectRequestNoNotify(User.findUserByUsername(reqDestination));
                            SaveAndLoad.saveUsers();
                            System.out.println(ConsoleColors.GREEN + "Request accepted!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED + "No user with this username in follow requests!" + ConsoleColors.RESET);
                }
            }
        } else {
            System.out.println(ConsoleColors.RED + "You have no incoming requests!" + ConsoleColors.RESET);
        }
    }

    public static void showMyRequests(){
        while (true){
            if (Client.client.myUserByUsername().getPendingReqs().size() > 0) {
                int i = Client.client.myUserByUsername().getPendingReqs().size();
                while ( i > 0 ) {
                    System.out.println(
                            ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + User.getUserById(Client.client.myUserByUsername().getPendingReqs().get(i - 1)).getUsername() +
                                    ConsoleColors.YELLOW + "  (" +
                                    ConsoleColors.BLUE + User.getUserById(Client.client.myUserByUsername().getPendingReqs().get(i - 1)).getName() + " " + User.getUserById(Client.client.myUserByUsername().getPendingReqs().get(i - 1)).getLastName() +
                                    ConsoleColors.YELLOW + ")" + ConsoleColors.RESET
                    );
                    i--;
                }
                System.out.println(ConsoleColors.BLUE + "Select a username:" + ConsoleColors.RESET);
                String reqDestination = MyScanner.getSc().next().toLowerCase();
                if (reqDestination.equals("-")) {
                    break;
                } else if (reqDestination.equals("exit")) {
                    System.exit(0);
                } else if (Client.client.myUserByUsername().getPendingReqs().contains(User.findUserByUsername(reqDestination).getId())) {
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter where you want to go:\n" +
                                ConsoleColors.RED + ">" +
                                ConsoleColors.BLUE + "Remove your request (1)  " + ConsoleColors.RESET);
                        String removeOrNot = MyScanner.getSc().next().toLowerCase();
                        if (removeOrNot.equals("-")) {
                            break;
                        } else if (removeOrNot.equals("1")) {
                            Client.client.myUserByUsername().deleteRequest(User.findUserByUsername(reqDestination));
                            SaveAndLoad.saveUsers();
                            System.out.println(ConsoleColors.GREEN + "Your request removed!" + ConsoleColors.RESET);
                            break;
                        } else {
                            System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                        }
                    }
                } else {
                    System.out.println(ConsoleColors.RED + "No user with this username in follow requests!" + ConsoleColors.RESET);
                }
            } else {
                System.out.println(ConsoleColors.RED + "You have no pending requests!" + ConsoleColors.RESET);
                break;
            }
        }
    }

    public static void showSysNotification(){
        out:
        while (true){

            ArrayList<Notification> unseenList = new ArrayList<>();
            for (int i = Client.client.myUserByUsername().getNotifications().size(); i > 0; i--){
                if ( !(Client.client.myUserByUsername().getNotifications().get(i-1).isSeen()) ){
                    unseenList.add(Client.client.myUserByUsername().getNotifications().get(i-1));
                }
            }

            if (unseenList.size() > 0){
                int i = 0;
                in:
                while (true){
                    System.out.println("\n" + ConsoleColors.YELLOW + User.getUserById(unseenList.get(i).getFromId()).getUsername() + unseenList.get(i).getText() + ConsoleColors.RESET);
                    System.out.println(ConsoleColors.BLUE + unseenList.get(i).getDateTime().toLocalDate() + ", " + unseenList.get(i).getDateTime().toLocalTime());
                    System.out.println( (i+1) + " of " + unseenList.size() + ConsoleColors.RESET + "\n");

                    if (unseenList.size() == 1){
                        while (true){
                            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Check as seen (1)" + ConsoleColors.RESET);
                            String notifyDestination = MyScanner.getSc().next().toLowerCase();
                            if (notifyDestination.equals("-")){
                                break out;
                            } else if (notifyDestination.equals("1")){
                                Client.client.myUserByUsername().getNotifications().remove(unseenList.get(i));
                                SaveAndLoad.saveUsers();
                                break in;
                            } else {
                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                            }
                        }
                    } else if (i == 0){
                        while (true) {
                            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Check as seen (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (2)" + ConsoleColors.RESET);
                            String notifyDestination = MyScanner.getSc().next().toLowerCase();
                            if (notifyDestination.equals("-")){
                                break out;
                            } else if (notifyDestination.equals("1")){
                                Client.client.myUserByUsername().getNotifications().remove(unseenList.get(i));
                                SaveAndLoad.saveUsers();
                                break in;
                            } else if (notifyDestination.equals("2")){
                                i++;
                                break;
                            } else {
                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                            }
                        }
                    } else if (i == unseenList.size()-1){
                        while (true) {
                            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Check as seen (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Previous (2)" + ConsoleColors.RESET);
                            String notifyDestination = MyScanner.getSc().next().toLowerCase();
                            if (notifyDestination.equals("-")){
                                break out;
                            } else if (notifyDestination.equals("1")){
                                Client.client.myUserByUsername().getNotifications().remove(unseenList.get(i));
                                SaveAndLoad.saveUsers();
                                break in;
                            } else if (notifyDestination.equals("2")){
                                i--;
                                break;
                            } else {
                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                            }
                        }
                    } else {
                        while (true) {
                            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Check as seen (1)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Previous (2)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (3)" + ConsoleColors.RESET);
                            String notifyDestination = MyScanner.getSc().next().toLowerCase();
                            if (notifyDestination.equals("-")){
                                break out;
                            } else if (notifyDestination.equals("1")){
                                Client.client.myUserByUsername().getNotifications().remove(unseenList.get(i));
                                SaveAndLoad.saveUsers();
                                break in;
                            } else if (notifyDestination.equals("2")){
                                i--;
                                break;
                            } else if (notifyDestination.equals("3")){
                                i++;
                                break;
                            } else {
                                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
                            }
                        }
                    }
                }
            } else {
                System.out.println(ConsoleColors.RED + "You have no notifications!" + ConsoleColors.RESET);
                break;
            }
        }
    }

}
