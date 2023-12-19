package com.EShop.EShop.model.binding;

public class UserLoginBindingModel {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public UserLoginBindingModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
