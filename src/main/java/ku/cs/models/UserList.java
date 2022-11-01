package ku.cs.models;

import ku.cs.models.User;

import java.util.ArrayList;

public class UserList {

    private static ArrayList<User> users; //ตัวแปร users เอาไว้เพิ่มข้อมูลลงไป

    public static ArrayList<User> getAllUsers() { return users; }

    public static void setUsers(ArrayList<User> users) {
        UserList.users = users;
    }

}
