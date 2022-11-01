package ku.cs.models;

import ku.cs.models.User;

import java.util.ArrayList;

public class UserList {

    private static ArrayList<User> users; //ตัวแปร users เอาไว้เพิ่มข้อมูลลงไป

    public void UserArrayList(ArrayList<User> users) {
        this.users = users;
    }

    public UserList() { this.users = new ArrayList<>(); }

    public void addUser(User user){ users.add(user); }

    public static ArrayList<User> getAllUsers() { return users; }

}
