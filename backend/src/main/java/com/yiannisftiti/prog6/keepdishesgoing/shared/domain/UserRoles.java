package com.yiannisftiti.prog6.keepdishesgoing.shared.domain;

public enum UserRoles {
    OWNER("owner"),CUSTOMER("customer");

    private String keycloakName;

    UserRoles(String name){
        this.keycloakName=name;
    }

    public String getKeycloakName(){
        return this.keycloakName;
    }

}
