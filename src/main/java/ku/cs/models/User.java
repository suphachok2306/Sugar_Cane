package ku.cs.models;

public class User {
    public String user_id;
    private String u_name;
    private String username;
    private String password;
    private String user_email;
    private String tel;
    private String role_id;

    public User(String user_id, String u_name, String username, String password, String user_email, String tel, String role_id) {
        this.user_id = user_id;
        this.u_name = u_name;
        this.username = username;
        this.password = password;
        this.user_email = user_email;
        this.tel = tel;
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }
}
