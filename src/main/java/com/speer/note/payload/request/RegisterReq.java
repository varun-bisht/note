package com.speer.note.payload.request;

import lombok.Data;

@Data
public class RegisterReq {

    private String name;
    private String email;
    private String password;
    private String roles;
}
