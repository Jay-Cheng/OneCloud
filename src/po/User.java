package po;

public class User {
    private Long id;
    private String account;
    private String password;
    private String nickname;
    private String photo_url;
    
    public User() {}
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}
