package model.builder;

import model.User;

public class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder setName(String name){
        user.setUsername(name);
        return this;
    }

    public UserBuilder setPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder setAdmin(Integer admin){
        user.setIsAdmin(admin);
        return this;
    }

    public User build(){
        return user;
    }
}
