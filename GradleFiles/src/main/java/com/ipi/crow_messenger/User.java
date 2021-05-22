package com.ipi.crow_messenger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;

@JsonIgnoreProperties({ "idByUser", "user", "emailed", "usernameValid", "phoned" })
public class User {

    Logger logger = LogManager.getLogger(User.class.getName());

    private String username;
    private String password;
    private long id;
    private String name;
    private String lastName;
    private String email;
    private String birthDate = null;
    private String phoneNumber = null;
    private String lastSeenType = "anyone";
    private String bio = "Hi, I am a crow member!" ;
    private boolean online = false;
    private boolean loggedIn = false;
    private boolean privateAccount = false;
    private boolean active = true;
    LocalDateTime lastSeen = LocalDateTime.now();
    private ArrayList<Long> followers = new ArrayList<>();
    private ArrayList<Long> followings = new ArrayList<>();
    private ArrayList<Long> blackList = new ArrayList<>();
    private ArrayList<Long> mutedList = new ArrayList<>();
    private ArrayList<Statement> statements = new ArrayList<>();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private ArrayList<Chat> chats = new ArrayList<>();
    private ArrayList<Long> followReqs = new  ArrayList<>();
    private ArrayList<Long> pendingReqs = new ArrayList<>();
    private ArrayList<UserCategory> categories = new ArrayList<UserCategory>();

    public long averageLike(){
        if (this.statements.size() > 0) {
            long sumOfLikes = 0;
            for (Statement statement : statements) {
                sumOfLikes += statement.getLikes().size();
            }
            return (sumOfLikes / (long) statements.size());
        } else {
            return 0;
        }
    }

    public UserCategory findCategory(String categoryName){
        for (UserCategory category : categories){
            if (category.getName().equals(categoryName)){
                return category;
            }
        }
        return new UserCategory();
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void addToChats(Chat chat){
        chats.add(chat);
    }

    public void addToNotifications(Notification notification){
        notifications.add(notification);
    }

    public void addToStatements(Statement statement){
        statements.add(statement);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void addToReqFromThis(User user){
        this.pendingReqs.add(user.getId());
        user.followReqs.add(this.getId());
    }

    public void makeActive() {
        this.active = true;
    }

    public void makeInactive() {
        this.active = false;
        this.makeLoggedOut();
    }

    public void makePrivate(){
        this.privateAccount = true;
    }

    public void makePublic(){
        this.privateAccount = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isUser(){
        for (User x : Index.getUsers()){
            if (x.username.equals(this.username)){
                return true;
            }
        }
        return false;
    }

    public static boolean isUser(String username){
        for (User user : Index.getUsers()){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public static boolean isPhoned(String phoneNumber){
        for (User user : Index.getUsers()){
            if (user.getPhoneNumber().equals(phoneNumber)){
                return true;
            }
        }
        return false;
    }

    public boolean isEmailed() {
        for (User x : Index.getUsers()){
            if (x.getEmail().equals(this.email)){
                return true;
            }
        }
        return false;
    }

    public static boolean isEmailed(String email){
        for (User user : Index.getUsers()){
            if (user.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    public boolean checkPass(){
        for (User x : Index.getUsers()){
            if ( this.username.equals(x.username) ){
                if (this.password.equals(Index.getUsers().get(Index.getUsers().indexOf(x)).password))
                    return true;
            }
        }
        return false;
    }

    public void makeLoggedIn(){
        this.loggedIn = true;
        this.makeActive();
    }

    public void makeLoggedOut(){
        this.loggedIn = false;
    }

    public boolean isUsernameValid(){
        return true;
    }

    // setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLastSeenType(String lastSeenType) {
        this.lastSeenType = lastSeenType;
    }

    // getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public boolean isOnline() {
        return online;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public ArrayList<Long> getFollowers() {
        return followers;
    }

    public ArrayList<Long> getFollowings() {
        return this.followings;
    }

    public ArrayList<Long> getBlackList() {
        return blackList;
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public boolean isPrivateAccount() {
        return privateAccount;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public String getLastSeenType() {
        return lastSeenType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public void assignId() {
        this.id = Index.getIdCounter();
        Index.idPlus();
    }

    public boolean hasBlocked(long userId) {
        return this.blackList.contains(userId);
    }

    public boolean hasFollowed(long userId){
        return this.followings.contains(userId);
    }

    public boolean hasMuted(long userId){
        return this.mutedList.contains(userId);
    }

    public User myUserByUsername(){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(Client.client.getUsername())){
                return x;
            }
        }
        return Client.client;
    }

    public static User findUserByUsername(String username){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(username)){
                return x;
            }
        }
        return Client.client;
    }

    public Chat findChatByUser(User user){
        for (Chat x : this.getChats()){
            if (x.getOtherId() == user.getId()){
                return x;
            }
        }
        return new Chat();
    }

    public void block(long userId){
        this.blackList.add(userId);
        if (User.getUserById(userId).hasFollowed(this.id)){
            User.getUserById(userId).unfollow(this.getId());
        }
    }

    public void removeFromBlackList(long userId){
        blackList.remove(userId);
    }

    public void unblock(long userId) {
        this.blackList.remove(userId);
    }

    public void follow(User user) {
        if (user.isPrivateAccount()) {
            this.addToReqFromThis(user);
        }
        else {
            user.followers.add(this.id);
            this.followings.add(user.id);
            // add to notifies
            Notification tmpNotification = new Notification();
            tmpNotification.setText("Started following you!");
            tmpNotification.setFrom(this.id);
            tmpNotification.setDateTime(LocalDateTime.now());
            user.addToNotifications(tmpNotification);
        }
    }

    public void mute(long userId){
        this.mutedList.add(userId);
    }

    public void unfollow(long userId) {
        User.getUserById(userId).followers.remove(this.getId());
        this.followings.remove(userId);
        // add to notifies
        Notification tmpNotification = new Notification();
        tmpNotification.setText("Unfollowed you!");
        tmpNotification.setFrom(this.getId());
        tmpNotification.setDateTime(LocalDateTime.now());
        User.getUserById(userId).addToNotifications(tmpNotification);
    }

    public void unmute(User user) {
        this.mutedList.remove(user.id);
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void deleteFromAll() {
        for (long targetId : this.followers) {
            getUserById(targetId).followings.remove(this.id);
        }
        for (long targetId : this.followings){
            getUserById(targetId).followers.remove(this.id);
        }
        for (User user : Index.getUsers()) {
            if ( !(user.getStatements().isEmpty()) ){
                for (Statement statement : user.getStatements()){
                    ArrayList<Statement> willRemove = new ArrayList<>();
                    if (statement.getLikes().contains(this.id)){
                        statement.removeFromLikes(this.id);
                    }
                    for (Statement comment : statement.getComments()){
                        if (comment.getUserId() == this.id){
                            willRemove.add(comment);
                        } else {
                            if (comment.getLikes().contains(this.id)){
                                comment.removeFromLikes(this.id);
                            }
                            deleteFromComments(comment, this);
                        }
                    }
                    statement.removeFromComments(willRemove);
                }
            }
        }
        for (User user : Index.getUsers()){
            if (user.hasBlocked(this.id)){
                user.blackList.remove(this.id);
            }
            if (user.hasMuted(this.id)){
                user.mutedList.remove(this.id);
            }
            user.followReqs.remove(this.id);
            user.pendingReqs.remove(this.id);
            for (UserCategory category : user.categories){
                if (category.getMembersId().contains(this.id)){
                    category.removeFromCategory(this.id);
                }
            }
            if (user.containChatWith(this)){
                user.chats.remove(user.findChatWith(this));
            }
        }
    }

    public static void deleteFromComments(Statement fatherComment, User user){
        if ( !(fatherComment.getComments().isEmpty()) ){
            ArrayList<Statement> willRemove = new ArrayList<>();
            for (Statement childComment : fatherComment.getComments()){
                if (childComment.getUserId() == user.id){
                    willRemove.add(childComment);
                } else {
                    if (childComment.getLikes().contains(user.id)){
                        childComment.removeFromLikes(user.id);
                    }
                    deleteFromComments(childComment, user);
                }
            }
            fatherComment.removeFromComments(willRemove);
        }
    }

    public void showChat(Chat chat) {
        int i = chat.getMessages().size() - 1;
        out: while (true){
            if (chat.getMessages().size() > 0) {
                System.out.println("\n" + ConsoleColors.RED + getUserById(chat.getMessages().get(i).getSenderId()).getUsername() + ": " + ConsoleColors.YELLOW + chat.getMessages().get(i).getText() + ConsoleColors.RESET);

                System.out.println(ConsoleColors.BLUE + "Sent at " + chat.getMessages().get(i).getLocalDateTime().toLocalDate() + ", " + chat.getMessages().get(i).getLocalDateTime().toLocalTime());
                System.out.println(ConsoleColors.BLUE + ( (chat.getMessages().size() - i) ) + " of " + chat.getMessages().size() + ConsoleColors.RESET + "\n");

                if ( (!chat.getMessages().get(i).isSeen()) || (!(chat.getMessages().get(i).getSenderId() == Client.client.getId())) ){
                    chat.getMessages().get(i).makeSeen();
                }

                if (chat.getMessages().size() == 1){
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        System.out.println(
                                ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Send Message (1)  " +
                                        ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "exit" + ConsoleColors.RESET
                        );
                        String nextOrPrev = MyScanner.getSc().next().toLowerCase();
                        if (nextOrPrev.equals("-")) {
                            break out;
                        }
                        else if (nextOrPrev.equals("1")) {

                            System.out.print(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                            MyScanner.getSc().nextLine();
                            String tmpMessageText = MyScanner.getSc().nextLine();
                            if ( !(tmpMessageText.equals("-")) ) {
                                Client.client.myUserByUsername().sendMessage(getUserById(chat.getOtherId()), tmpMessageText);
                                SaveAndLoad.saveUsers();
                                logger.info("user " + this.getId() + " sent a message to user " + chat.getOtherId());
                                System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                i = chat.getMessages().size() - 1;
                            }
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
                else if ( i == (chat.getMessages().size() - 1) ){
                    while (true) {
                        System.out.println(ConsoleColors.BLUE + "Enter what you want to do:");
                        System.out.println(
                                ConsoleColors.RED + ">" +
                                        ConsoleColors.BLUE + "Send Message (1)  " +
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
                            System.out.print(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                            MyScanner.getSc().nextLine();
                            String tmpMessageText = MyScanner.getSc().nextLine();
                            if ( !(tmpMessageText.equals("-")) ) {
                                Client.client.myUserByUsername().sendMessage(getUserById(chat.getOtherId()), tmpMessageText);
                                SaveAndLoad.saveUsers();
                                logger.info("user " + this.getId() + " sent a message to user " + chat.getOtherId());
                                System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                i = chat.getMessages().size() - 1;
                            }
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
                                        ConsoleColors.BLUE + "Send Message (1)  " +
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
                            System.out.print(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                            MyScanner.getSc().nextLine();
                            String tmpMessageText = MyScanner.getSc().nextLine();
                            if ( !(tmpMessageText.equals("-")) ) {
                                Client.client.myUserByUsername().sendMessage(getUserById(chat.getOtherId()), tmpMessageText);
                                SaveAndLoad.saveUsers();
                                logger.info("user " + this.getId() + " sent a message to user " + chat.getOtherId());
                                System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                i = chat.getMessages().size() - 1;
                            }
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
                                        ConsoleColors.BLUE + "Send Message (1)  " +
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
                            System.out.print(ConsoleColors.BLUE + "Enter the message you want to send: " + ConsoleColors.RESET);
                            MyScanner.getSc().nextLine();
                            String tmpMessageText = MyScanner.getSc().nextLine();
                            if ( !(tmpMessageText.equals("-")) ) {
                                Client.client.myUserByUsername().sendMessage(getUserById(chat.getOtherId()), tmpMessageText);
                                SaveAndLoad.saveUsers();
                                logger.info("user " + this.getId() + " sent a message to user " + chat.getOtherId());
                                System.out.println(ConsoleColors.GREEN + "Message Sent Successfully!" + ConsoleColors.RESET);
                                i = chat.getMessages().size() - 1;
                            }
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
    }

    public static boolean isUserByUsername(String username){
        for (User x : Index.getUsers()){
            if (x.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean containChatWith(User receiver) {
        for (Chat chat : chats){
            if (chat.getOtherId() == receiver.getId()){
                return true;
            }
        }
        return false;
    }

    public Chat findChatWith(User user){
        for (Chat chat : chats){
            if (chat.getOtherId() == user.getId()){
                return chat;
            }
        }
        return new Chat();
    }

    public Chat findChatByUsername(String username) {
        for (Chat x : this.getChats()){
            if (getUserById(x.getOtherId()).getUsername().equals(username)){
                return x;
            }
        }
        return new Chat();
    }

    public void deleteFromSaved(Message message) {
        this.findChatWith(this).getMessages().remove(message);
    }

    public ArrayList<Long> getFollowReqs() {
        return followReqs;
    }

    public ArrayList<Long> getPendingReqs() {
        return pendingReqs;
    }

    public void acceptRequest(User user) {
        this.getFollowReqs().remove(user.id);
        user.getPendingReqs().remove(this.id);
        this.followers.add(user.id);
        user.followings.add(this.id);
        // add to notifies
        Notification tmpNotification = new Notification();
        tmpNotification.setText(" accepted your follow request!");
        tmpNotification.setFrom(this.id);
        tmpNotification.setDateTime(LocalDateTime.now().withNano(0).withSecond(0));
        user.addToNotifications(tmpNotification);
        Notification tmpNotification2 = new Notification();
        tmpNotification2.setText(" started following you!");
        tmpNotification2.setFrom(user.id);
        tmpNotification2.setDateTime(LocalDateTime.now());
        this.addToNotifications(tmpNotification2);
    }

    public void rejectRequest(User user) {
        this.getFollowReqs().remove(user.id);
        user.getPendingReqs().remove(this.id);
        Notification tmpNotification = new Notification();
        tmpNotification.setText(" rejected your follow request!");
        tmpNotification.setFrom(this.id);
        tmpNotification.setDateTime(LocalDateTime.now().withNano(0).withSecond(0));
        user.addToNotifications(tmpNotification);
    }

    public void rejectRequestNoNotify(User user) {
        this.getFollowReqs().remove(user.id);
        user.getPendingReqs().remove(this.id);
    }

    public void deleteRequest(User user) {
        this.pendingReqs.remove(user.id);
        user.followReqs.remove(this.id);
    }

    // for save

    public void setOnline(boolean online) {
        this.online = online;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setPrivateAccount(boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setFollowers(ArrayList<Long> followers) {
        this.followers = followers;
    }

    public void setFollowings(ArrayList<Long> followings) {
        this.followings = followings;
    }

    public void setBlackList(ArrayList<Long> blackList) {
        this.blackList = blackList;
    }

    public ArrayList<Long> getMutedList() {
        return mutedList;
    }

    public void setMutedList(ArrayList<Long> mutedList) {
        this.mutedList = mutedList;
    }

    public void setStatements(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public void setFollowReqs(ArrayList<Long> followReqs) {
        this.followReqs = followReqs;
    }

    public void setPendingReqs(ArrayList<Long> pendingReqs) {
        this.pendingReqs = pendingReqs;
    }

    public User() {

    }

    public void deleteStatement(Statement statement) {
        this.getStatements().remove(statement);
    }

    public static User getUserById(long id){
        for (User user : Index.getUsers()){
            if (user.getId() == id){
                return user;
            }
        }
        return new User();
    }

    public boolean hasRequested(long id) {
        for (long userId : this.pendingReqs){
            if (userId == id){
                return true;
            }
        }
        return false;
    }

    public void newChat(User user) {

        ArrayList<Message> tmpMyMessageList = new ArrayList<>();
        Chat myChat = new Chat(this.getId(), user.getId(), tmpMyMessageList);
        this.addToChats(myChat);
        if ( !(this.id == user.id) ){
            ArrayList<Message> tmpOtherMessageList = new ArrayList<>();
            Chat otherChat = new Chat(user.getId(), this.getId(), tmpOtherMessageList);
            user.addToChats(otherChat);
        }

    }

    public void sendMessage(User user, String messageText) {
        this.findChatWith(user).addToMessages(new Message(this.id, user.id, messageText, LocalDateTime.now()));
        if ( !(user.id == this.id) ) {
            user.findChatWith(this).addToMessages(new Message(this.id, user.id, messageText, LocalDateTime.now()));
        }
    }

    public void setCategories(ArrayList<UserCategory> categories) {
        this.categories = categories;
    }

    public ArrayList<UserCategory> getCategories() {
        return categories;
    }

    public boolean containCategoryByName(String categoryName) {
        for (UserCategory category : categories){
            if (category.getName().equals(categoryName)){
                return true;
            }
        }
        return false;
    }

    public void createCategory(String categoryName) {
        UserCategory tmpCategory = new UserCategory();
        tmpCategory.setName(categoryName);
        this.categories.add(tmpCategory);
    }

    public void removeCategory(String tmpCategory) {
        for (UserCategory category : this.categories) {
            if (category.getName().equals(tmpCategory)) {
                this.categories.remove(category);
                break;
            }
        }
    }
}
