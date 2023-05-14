package com.common.exceptionHandle;



public class iException extends RuntimeException {
    int code = 1;

    public int getCode() {
        return code;
    }
    public iException(int code,String message){
        super(message);
        this.code=code;

    }
    public iException(String message){
        super(message);


    }
}
