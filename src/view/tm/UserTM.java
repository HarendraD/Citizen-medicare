package view.tm;

public class UserTM {
    private String id;
    private String userName;
    private String password;
    private String userRole;

    public UserTM() {
    }

    public UserTM(String id, String userName, String password, String userRole) {
        this.setId(id);
        this.setUserName(userName);
        this.setPassword(password);
        this.setUserRole(userRole);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
