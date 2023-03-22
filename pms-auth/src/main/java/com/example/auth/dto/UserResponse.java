package com.example.auth.dto;

import lombok.Data;

@Data
public class UserResponse {

    private String msg;

    private String token;

    private CommonState state;

    public UserResponse() {
    }

    public UserResponse(String msg, CommonState state) {
        this.msg = msg;
        this.state = state;
    }

    public UserResponse(String msg, String token, CommonState state) {
        this.msg = msg;
        this.token = token;
        this.state = state;
    }

    public static UserResponse loginSuccess(String token){
        UserResponse ur = new UserResponse();
        ur.setToken(token);
        ur.setMsg("success");
        ur.setState(CommonState.LOGIN_SUCCESS);
        return ur;
    }

    public static UserResponse registerSuccess(){
        return new UserResponse("success", CommonState.REGISTER_SUCCESS);
    }

    public static UserResponse loginFail(){
        UserResponse ur = new UserResponse();
        ur.setToken(null);
        ur.setMsg("failed");
        ur.setState(CommonState.LOGIN_FAILED);
        return ur;
    }

    public static UserResponse registerFail(){
        return new UserResponse("fail", CommonState.REGISTER_FAILED);
    }
}
