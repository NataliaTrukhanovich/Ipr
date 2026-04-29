package api.models;

import lombok.Getter;

@Getter
public class Auth {

    private String username;
    private String password;

    public Auth(String username, String password){
        this.username = username;
        this.password = password;
    }
}
