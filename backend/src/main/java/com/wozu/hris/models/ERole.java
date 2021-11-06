package com.wozu.hris.models;

public enum ERole {
    ROLE_CANDIDATE(0),
    ROLE_EMPLOYEE(1),
    ROLE_MANAGER(2),
    ROLE_HR(3);

    final private int id;

    ERole(int id){
        this.id = id;
    }
    public int getID(){
        return this.id;
    }
}