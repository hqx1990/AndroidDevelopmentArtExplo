package com.example.scott.exception;

import com.example.beaselibrary.util.Logger;

public class ScottException extends Exception {
    public ScottException(String e){
        super(e);
        Logger.e("ScottException",e);
    }
}
