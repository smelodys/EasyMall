package cn.tedu.bean;

/**
 * 用户实体类
 * @author wangyyyy
 * @date 2020/6/19 11:46
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private String email;

    /**
     * modified by wangyyyy 2020-07-02
     */
/*    public int getId() {
        return id;
    }*/

    public void setId(int id) {
        this.id = id;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
