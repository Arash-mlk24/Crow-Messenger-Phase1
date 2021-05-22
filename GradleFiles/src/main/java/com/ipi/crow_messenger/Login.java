package com.ipi.crow_messenger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Login {

    static Logger logger = LogManager.getLogger(Login.class.getName());

    public static void signUp(){

        String now = "name";
        User tmpUser = new User();

        out: while (true) {

            // set name
            while (now.equals("name")){
                    System.out.println(ConsoleColors.BLUE + "Name : " + ConsoleColors.RESET);
                    tmpUser.setName(MyScanner.getSc().next());
                    if (tmpUser.getName().equals("-")) {
                        break out;
                    }
                    if (haveNum(tmpUser.getName())) {
                        System.out.println(ConsoleColors.RED + "Name must only have alphabetic characters." + ConsoleColors.RESET);
                        continue;
                    }
                    now = now.replace("name", "lastName");
                    break;
            }

            // set lastName
            while (now.equals("lastName")){
                System.out.println(ConsoleColors.BLUE + "Last Name : " + ConsoleColors.RESET);
                tmpUser.setLastName(MyScanner.getSc().next());
                if (tmpUser.getLastName().equals("-")) {
                    now = now.replace("lastName", "name");
                    break;
                }
                if (haveNum(tmpUser.getLastName())) {
                    System.out.println(ConsoleColors.RED + "Last Name must only have alphabetic characters." + ConsoleColors.RESET);
                    continue;
                }
                now = now.replace("lastName", "email");
                break;
            }

            // set email
            while (now.equals("email")){
                System.out.println(ConsoleColors.BLUE + "email : " + ConsoleColors.RESET);
                tmpUser.setEmail(MyScanner.getSc().next().toLowerCase());
                if (tmpUser.getEmail().equals("-")) {
                    now = now.replace("email", "lastName");
                    break;
                }
//                if ((!isValidEmail(tmpUser.getEmail()))) {
//                    System.out.println(ConsoleColors.RED + "Your email is not valid." + ConsoleColors.RESET);
//                    continue;
//                }
                if (tmpUser.isEmailed()) {
                    System.out.println(ConsoleColors.RED + "There is an account existing with this email." + ConsoleColors.RESET);
                    continue;
                }
                now = now.replace("email", "username");
                break;
            }

            // set username
            while (now.equals("username")) {
                System.out.println(ConsoleColors.BLUE + "User Name : " + ConsoleColors.RESET);        //username input
                tmpUser.setUsername(MyScanner.getSc().next().toLowerCase());
                if (tmpUser.getUsername().equals("-")) {
                    now = now.replace("username" ,"email");
                    break;
                }
                if (!tmpUser.isUsernameValid()){
                    System.out.println(ConsoleColors.RED + "Your username is not valid!" + ConsoleColors.RESET);
                    continue;
                }
                if (tmpUser.isUser()){
                    System.out.println(ConsoleColors.RED + "This username is already taken!" + ConsoleColors.RESET);
                    continue;
                }
                now = now.replace("username" ,"password");
                break;
            }

            // set password
            while (now.equals("password")) {
                System.out.println(ConsoleColors.BLUE + "Password : " + ConsoleColors.RESET);
                tmpUser.setPassword(MyScanner.getSc().next());
                if (tmpUser.getPassword().equals("-")) {
                    now = now.replace("password" ,"username");
                    break;
                }
                if (tmpUser.getPassword().length() < 8) {
                    System.out.println(ConsoleColors.RED + "Password must include at least 8 characters." + ConsoleColors.RESET);
                    continue;
                }
                if (isValidPass(tmpUser.getPassword())) {
                    now = now.replace("password" ,"signUp");
                    break out;
                }
                System.out.println(ConsoleColors.RED + "Password must include alphabetic and numerical characters." + ConsoleColors.RESET);
            }
        }

        if (now.equals("signUp")){
            tmpUser.assignId();
            tmpUser.makeActive();
            Index.addUser(tmpUser);
            SaveAndLoad.saveAll();
            logger.info("user " + tmpUser.getId() + " signed up!");
            System.out.println(ConsoleColors.GREEN + "You Signed Up Successfully!" + ConsoleColors.RESET);
        }

    }

    public static void signIn() {

        String now = "username";
        User tmpUser = new User();

        out: while (true){
            while (true) {
                System.out.println(ConsoleColors.BLUE + "User Name : " + ConsoleColors.RESET);        //username input
                tmpUser.setUsername(MyScanner.getSc().next().toLowerCase());
                if (tmpUser.getUsername().equals("-")){
                    break out;
                }
                if ( !tmpUser.isUser() ){     //check existence of username
                    System.out.println(ConsoleColors.RED + "Username you've entered doesn't exist." + ConsoleColors.RESET);
                    continue;
                }
                now = now.replace("username", "password");
                break;
            }
            while (true) {
                System.out.println(ConsoleColors.BLUE + "Password : " + ConsoleColors.RESET);
                tmpUser.setPassword(MyScanner.getSc().next().toLowerCase());
                if (tmpUser.getPassword().equals("-")){
                    now = now.replace("password", "username");
                    break;
                }
                if (tmpUser.getPassword().length() < 8){
                    System.out.println(ConsoleColors.RED + "Password must include at least 8 characters." + ConsoleColors.RESET);
                    continue;
                }
                if (tmpUser.checkPass()){
                    now = now.replace("password", "logIn");
                    break out;
                } else {
                    System.out.println(ConsoleColors.RED + "The Password is incorrect!" + ConsoleColors.RESET);
                }
            }
        }
        if (now.equals("logIn")) {
            Client.client.setUsername(tmpUser.getUsername());
            Client.client.setId(Client.client.myUserByUsername().getId());
            Client.client.makeLoggedIn();
            Client.client.makeActive();
            logger.info("user " + Client.client.getId() + " logged in!");
            System.out.println(ConsoleColors.GREEN + "You Signed In Successfully!" + ConsoleColors.RESET);
        }
    }

    //    private static boolean isValidEmail(String email) {
//        for (int i = 0; i < email.length(); i++){
//            if (i == 0){
//                if (email.charAt(i) == '@' || email.charAt(i) == '.' || Character.isDigit(email.charAt(i))){
//                    return false;
//                }
//            }
//            if ()
//        }
//        return true;
//    }

    public static boolean haveNum(@org.jetbrains.annotations.NotNull String string) {
        for (int i = 0; i < string.length(); i++){
            if (Character.isDigit(string.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPass(String pass) {
        int alphas = 0;
        int numerics = 0;
        for (int i = 0; i < pass.length(); i++){
            if ( Character.isAlphabetic(pass.charAt(i)) ){
                alphas++;
                continue;
            }
            if ( Character.isDigit(pass.charAt(i)) ){
                numerics++;
            }
            else {
                return false;
            }
        }
        return ( numerics != 0 && alphas != 0 );
    }
}


