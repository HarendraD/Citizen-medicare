package model;

public class Login {
    private String UserName;
    private String password;
    private String userRole;

    public Login() {
    }

    public Login(String userName, String password, String userRole) {
        setUserName(userName);
        this.setPassword(password);
        this.setUserRole(userRole);
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
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
