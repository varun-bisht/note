package com.speer.note.payload.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRes {

    private String token;

}
