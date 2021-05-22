package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Statement {

    static Logger logger = LogManager.getLogger(Statement.class.getName());

    private long userId;
    private String text;
    private ArrayList<Long> likes = new ArrayList<>();
    private ArrayList<Statement> comments = new ArrayList<>();
    private LocalDateTime dateTime;

    public Statement(long userId, String text, LocalDateTime dateTime){
        this.userId = userId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public void addToComments(Statement statement){
        comments.add(statement);
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public long getUserId() {
        return userId;
    }

    public long numberOfLikes(){
        return likes.size();
    }

    public long numberOfComments(){
        return comments.size();
    }

    public ArrayList<Long> getLikes() {
        return likes;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void dislike() {
        this.likes.remove(Client.client.myUserByUsername().getId());
    }

    public void like() {
        this.likes.add(Client.client.myUserByUsername().getId());
    }

    public void addComment() {
            System.out.println(ConsoleColors.BLUE + "Enter your Comment: " + ConsoleColors.RESET);
            MyScanner.getSc().nextLine();
            String tmpCommentText = MyScanner.getSc().nextLine();
            if (tmpCommentText.equals("-")) {
                System.out.println(ConsoleColors.BLUE + "Your commenting process canceled!" + ConsoleColors.RESET);
            } else {
                Statement tmpComment = new Statement();
                tmpComment.setText(tmpCommentText);
                tmpComment.setUserId(Client.client.myUserByUsername().getId());
                tmpComment.setDateTime(LocalDateTime.now());
                this.addToComments(tmpComment);
                SaveAndLoad.saveUsers();
                SaveAndLoad.saveUsers();
                logger.info(Client.client.myUserByUsername().getId() + "liked a message");
                SaveAndLoad.saveUsers();
                logger.info("user" + Client.client.myUserByUsername().getId() + "commented on a message");
                System.out.println(ConsoleColors.GREEN + "Your comment submitted successfully!" + ConsoleColors.RESET);
            }

    }

    public void editStatement() {
        while (true) {
            System.out.println(ConsoleColors.BLUE + "\nYour current statement is: " + ConsoleColors.YELLOW + this.text + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "Enter a new text for your statement:" + ConsoleColors.RESET);
            MyScanner.getSc().nextLine();
            String editStateDestination = MyScanner.getSc().nextLine();
            if (editStateDestination.equals("-")){
                break;
            } else {
                this.text = editStateDestination;
                System.out.println(ConsoleColors.GREEN + "Statement changed successfully!" + ConsoleColors.RESET);
            }
        }
    }

    public void showLikes() {
        if (this.likes.size() > 0) {
            System.out.println(ConsoleColors.PURPLE + "\n* Likes *" + ConsoleColors.RESET);
            for (long userId : this.likes) {
                System.out.println(ConsoleColors.RED + ">" + ConsoleColors.BLUE + User.getUserById(userId).getUsername() +
                        ConsoleColors.RED + "  (" +
                        ConsoleColors.BLUE + User.getUserById(userId).getName() + " " + User.getUserById(userId).getLastName() +
                        ConsoleColors.RED + ")" + ConsoleColors.RESET);
            }
        } else {
            System.out.println(ConsoleColors.RED + "There is no comments!" + ConsoleColors.RESET);
        }
    }

    public void showComments() {
        System.out.println(ConsoleColors.PURPLE + "\n* Comments *" + ConsoleColors.RESET);
        int i = 0;
        if (this.getComments().size() > 0) {
            out: while (true){
                System.out.println("\n" + ConsoleColors.YELLOW + this.getComments().get(i).getText() + ConsoleColors.RESET);
                System.out.println(ConsoleColors.RED + this.getComments().get(i).numberOfLikes() + ConsoleColors.BLUE
                        + " Likes  " + ConsoleColors.RED + this.getComments().get(i).numberOfComments() + ConsoleColors.BLUE
                        + " Comments  " + ConsoleColors.RED + "@" + ConsoleColors.BLUE + User.getUserById(this.getComments().get(i).getUserId()).getUsername() + ConsoleColors.RESET);
                System.out.println(ConsoleColors.BLUE + (i+1) + " of " + this.getComments().size() + ConsoleColors.RESET + "\n");

                if (this.getComments().size() == 1){
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(
                                    ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (4)  " +
                                             ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        } else {
                            System.out.println(
                                    ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "like (1)        " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
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
                            if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                this.getComments().get(i).dislike();
                                logger.info("user " + Client.client.getId() + " disliked a comment by user " + this.userId);
                            } else {
                                this.getComments().get(i).like();
                                logger.info("user " + Client.client.getId() + " liked a comment by user " + this.userId);
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            this.getComments().get(i).addComment();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.getId() + " commented on user " + this.userId);
                        }
                        else if (nextOrPrev.equals("3")) {
                            this.getComments().get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            this.getComments().get(i).showComments();
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
                else if ( i == (this.getComments().size()-1) ){
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                                    ConsoleColors.BLUE + "Previous (5)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {
                            if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                this.getComments().get(i).dislike();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " disliked a comment by user " + this.userId);
                            } else {
                                this.getComments().get(i).like();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " liked a comment by user " + this.userId);
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            this.getComments().get(i).addComment();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.getId() + " commented on user " + this.userId);
                        }
                        else if (nextOrPrev.equals("3")) {
                            this.getComments().get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            this.getComments().get(i).showComments();
                        }
                        else if (nextOrPrev.equals("5")) {
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
                        if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
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
                                    ConsoleColors.BLUE + "Show Likes (3)\n" +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Show Comments (4)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "Next (5)  " +
                                    ConsoleColors.RED + ">" +
                                    ConsoleColors.BLUE + "exit" + ConsoleColors.RESET);
                        }

                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {
                            if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                this.getComments().get(i).dislike();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " disliked a comment by user " + this.userId);
                            } else {
                                this.getComments().get(i).like();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " liked a comment by user " + this.userId);
                            }
                            SaveAndLoad.saveUsers();
                        }
                        else if (nextOrPrev.equals("2")) {
                            this.getComments().get(i).addComment();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.getId() + " commented on user " + this.userId);
                        }
                        else if (nextOrPrev.equals("3")) {
                            this.getComments().get(i).showLikes();
                        }
                        else if (nextOrPrev.equals("4")) {
                            this.getComments().get(i).showComments();
                        }
                        else if (nextOrPrev.equals("5")) {
                            i++;
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
                        if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                            System.out.println(
                                    ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "dislike (1)     " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Add Comment (2)    " +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Likes (3)\n" +
                                            ConsoleColors.RED + ">" +
                                            ConsoleColors.BLUE + "Show Comments (4)   " +
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
                            if (this.getComments().get(i).getLikes().contains(Client.client.myUserByUsername().getId())) {
                                this.getComments().get(i).dislike();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " disliked a comment by user " + this.userId);
                            } else {
                                this.getComments().get(i).like();
                                SaveAndLoad.saveUsers();
                                logger.info("user " + Client.client.getId() + " liked a comment by user " + this.userId);
                            }
                            SaveAndLoad.saveUsers();
                        }
                        if (nextOrPrev.equals("2")) {
                            this.getComments().get(i).addComment();
                            SaveAndLoad.saveUsers();
                            logger.info("user " + Client.client.getId() + " commented on user " + this.userId);
                        }
                        if (nextOrPrev.equals("4")) {
                            this.getComments().get(i).showLikes();
                        }
                        if (nextOrPrev.equals("5")) {
                            this.getComments().get(i).showComments();
                        }
                        if (nextOrPrev.equals("6")) {
                            i--;
                            break;
                        }
                        if (nextOrPrev.equals("7")) {
                            i++;
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
            }
        } else {
            System.out.println(ConsoleColors.RED + "There is no comments!" + ConsoleColors.RESET);
        }
    }

     //for save

    public Statement() {

    }

    public void setLikes(ArrayList<Long> likes) {
        this.likes = likes;
    }

    public ArrayList<Statement> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Statement> comments) {
        this.comments = comments;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Statement findStatement() {
        for (Statement statement : User.getUserById(this.userId).getStatements()){
            if (statement.equals(this)){
                return statement;
            }
        }
        return new Statement();
    }

    public boolean containsCommentById(long id) {
        for (Statement comment : this.getComments()){
            if (comment.userId == id){
                return true;
            }
        }
        return false;
    }

    public void removeFromComments(ArrayList<Statement> willRemove) {
        this.comments.removeAll(willRemove);
    }

    public void removeFromLikes(long id) {
        this.likes.remove(id);
    }
}
