package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chat {

    static Logger logger = LogManager.getLogger(Chat.class.getName());

    private long myId;
    private long otherId;
    private ArrayList<Message> messages = new ArrayList<>();

    public Chat() {

    }

    public Chat(long myId, long otherId, ArrayList<Message> messages) {
        this.myId = myId;
        this.otherId = otherId;
        this.messages = messages;
    }

    public int countUnseenMessages(){
        int count = 0;
        for (Message message : messages) {
            if ( !(User.getUserById(message.getSenderId()).equals(Client.client.myUserByUsername())) ) {
                if (!(message.isSeen())) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void savedMessages() {
        while (true){
            System.out.println(ConsoleColors.PURPLE + "\n* Saved Messages *");
            System.out.println(ConsoleColors.BLUE + "Enter what you want to do:\n" +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Save a message (1)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "Manage and see your saved messages (2)  " +
                    ConsoleColors.RED + ">" +
                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
            String savedDestination = MyScanner.getSc().next().toLowerCase();
            if (savedDestination.equals("-")){
                break;
            }
            else if (savedDestination.equals("1")){
                System.out.print(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                MyScanner.getSc().nextLine();
                String tmpMessageText = MyScanner.getSc().nextLine();
                if ( !(tmpMessageText.equals("-")) ) {
                    if (Client.client.myUserByUsername().containChatWith(Client.client.myUserByUsername())) {
                        Client.client.myUserByUsername().sendMessage(Client.client.myUserByUsername(), tmpMessageText);
                        SaveAndLoad.saveUsers();
                        logger.info("user " + Client.client.myUserByUsername().getId() + " saved a message ");
                        System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                    } else {
                        Client.client.myUserByUsername().newChat(Client.client.myUserByUsername());
                        Client.client.myUserByUsername().sendMessage(Client.client.myUserByUsername(), tmpMessageText);
                        SaveAndLoad.saveUsers();
                        logger.info("user " + Client.client.myUserByUsername().getId() + " saved a message");
                        System.out.println(ConsoleColors.GREEN + "Message Saved Successfully!" + ConsoleColors.RESET);
                    }
                }
            }
            else if (savedDestination.equals("2")){
                User x = Client.client.myUserByUsername();
                int i = x.findChatWith(x).getMessages().size() - 1;
                out: while (true){
                    if (x.findChatWith(x).getMessages().size() > 0) {
                        System.out.println("\n" + ConsoleColors.YELLOW + x.findChatWith(x).getMessages().get(i).getText() + ConsoleColors.RESET);

                        System.out.println(ConsoleColors.BLUE + "Message from " +
                                ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(x.findChatWith(x).getMessages().get(i).getSenderId()).getUsername() +
                                ConsoleColors.YELLOW + " (" + ConsoleColors.BLUE + User.getUserById(x.findChatWith(x).getMessages().get(i).getSenderId()).getName() + " " + ConsoleColors.BLUE + User.getUserById(x.findChatWith(x).getMessages().get(i).getSenderId()).getLastName() + ConsoleColors.YELLOW + ")" + ConsoleColors.RESET);
                        System.out.println(ConsoleColors.BLUE + "Saved at " + x.findChatWith(x).getMessages().get(i).getLocalDateTime().toLocalDate() + ", " + x.findChatWith(x).getMessages().get(i).getLocalDateTime().toLocalTime());
                        System.out.println(ConsoleColors.BLUE + ( (x.findChatWith(x).getMessages().size() - i) ) + " of " + x.findChatWith(x).getMessages().size() + ConsoleColors.RESET + "\n");

                        if (x.findChatWith(x).getMessages().size() == 1){
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                System.out.println(
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Delete Message (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET
                                );
                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    x.deleteFromSaved( x.findChatWith(x).getMessages().get(i) );
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                        else if ( i == (x.findChatWith(x).getMessages().size() - 1) ){
                            while (true) {
                                System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                                System.out.println(
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Delete Message (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Next (2)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET
                                );
                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    x.deleteFromSaved( x.findChatWith(x).getMessages().get(i) );
                                    break;
                                }
                                else if (nextOrPrev.equals("2")) {
                                    i--;
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
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
                                System.out.println(
                                        ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "Delete Message (1)  " +
                                                ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "Previous (2)  " +
                                                ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "exit" + ConsoleColors.RESET
                                );
                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    x.deleteFromSaved( x.findChatWith(x).getMessages().get(i) );
                                    break;
                                }
                                else if (nextOrPrev.equals("2")) {
                                    i++;
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
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
                                System.out.println(
                                        ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "Delete Message (1)  " +
                                                ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "Previous (2)  " +
                                                ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "Next (3)\n" +
                                                ConsoleColors.RED + ">" +
                                                ConsoleColors.BLUE + "exit" + ConsoleColors.RESET
                                );
                                String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                                if (nextOrPrev.equals("-")) {
                                    break out;
                                }
                                else if (nextOrPrev.equals("1")) {
                                    x.deleteFromSaved( x.findChatWith(x).getMessages().get(i) );
                                    break;
                                }
                                else if (nextOrPrev.equals("2")) {
                                    i++;
                                    break;
                                }
                                else if (nextOrPrev.equals("3")){
                                    i--;
                                    break;
                                }
                                else if (nextOrPrev.equals("exit")) {
                                    System.exit(0);
                                }
                                else {
                                    System.out.println(ConsoleColors.RED + "Your input is invalid" + ConsoleColors.RESET);
                                }
                            }
                        }
                    } else {
                        System.out.println(ConsoleColors.RED + "\nYou have no saved message" + ConsoleColors.RESET);
                        break;
                    }
                }
                break;
            }
            else if (savedDestination.equals("exit")){
                System.exit(0);
            }
            else {
                System.out.println(ConsoleColors.RED + "Your input is invalid!" + ConsoleColors.RESET);
            }
        }
    }

    public void addToMessages(Message message) {
        this.messages.add(message);
    }

    public void setMe(long myId) {
        this.myId = myId;
    }

    public void setOther(long otherId) {
        this.otherId = otherId;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public long getMyId() {
        return myId;
    }

    public long getOtherId() {
        return otherId;
    }

}
