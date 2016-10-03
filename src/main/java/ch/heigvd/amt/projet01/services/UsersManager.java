package ch.heigvd.amt.projet01.services;

import ch.heigvd.amt.projet01.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sebbos on 28.09.2016.
 */
public class UsersManager {
    private List<User> listUsers = null;

    public UsersManager() {
        listUsers = new LinkedList<User>();
    }

    public boolean createUser(String userName, String userPassword) {
        if (isEmptyUserNameOrPassword(userName, userPassword)) {
            return false;
        }

        if (isUserExisting(userName) == -1) {
            listUsers.add(new User(userName, userPassword));

            return true;
        }

        return false;
    }

    public boolean checkLoginOK(String userName, String userPassword) {
        if (isEmptyUserNameOrPassword(userName, userPassword)) {
            return false;
        }

        int posListUsers = isUserExisting(userName);

        if (posListUsers != -1) {
            if (listUsers.get(posListUsers).getUserPassword().contentEquals(userPassword)) {
                return true;
            }
        }

        return false;
    }

    private boolean isEmptyUserNameOrPassword(String userName, String userPassword) {
        if (userName.equals("") || userPassword.equals("")) {
            return true;
        }

        return false;
    }

    private int isUserExisting(String userName) {
        for (int i = 0; i < listUsers.size(); i++) {
            if (listUsers.get(i).getUserName().contentEquals(userName)) {
                return i;
            }
        }

        return -1;
    }
}
