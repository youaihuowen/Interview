package com.wuyixiong.interview.event;

/**
 * Created by WUYIXIONG on 2017-5-15.
 */

public class SendError {
    private String errorText;
    private int errorId;

    public SendError(String errorText, int errorId) {
        this.errorText = errorText;
        this.errorId = errorId;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }
}
