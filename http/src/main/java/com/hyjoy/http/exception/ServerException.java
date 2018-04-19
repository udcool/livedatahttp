package com.hyjoy.http.exception;

/**
 * 异常类
 * showMessage 显示给用户的信息
 * message debug时打印的信息
 * Created by hyjoy on 2018/4/19.
 */
public class ServerException extends Throwable {
    private String showMessage;

    public ServerException(Throwable e) {
        super(e.getMessage());
        showMessage = e.getMessage();
    }

    public ServerException(Throwable e, String showMessage) {
        this(e);
        this.showMessage = showMessage;
    }

    public ServerException(String message) {
        this(message, message);
    }

    public ServerException(String message, String showMessage) {
        super(message);
        this.showMessage = showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    public String getShowMessage() {
        return showMessage;
    }
}
