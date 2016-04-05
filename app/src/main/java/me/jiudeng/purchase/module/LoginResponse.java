package me.jiudeng.purchase.module;

/**
 * Created by Yin on 2016/4/1.
 * 登录请求返回结果
 */
public class LoginResponse {
    /**
     * Code : 320000        （ Code == 0 为登录验证成功，否则不成功）
     * Data : null
     * Message : 账号格式错误
     */

    private int Code;
    private Object Data;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
