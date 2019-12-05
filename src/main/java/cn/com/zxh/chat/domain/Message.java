package cn.com.zxh.chat.domain;



import java.io.Serializable;

//传递即时聊天
public class Message implements Serializable {
    private String sender;          //消息发送者
    private String receiver;        //消息接收者
    private String time;            //消息发送时间
    private String msg;             //聊天内容

    public Message() {
    }

    public Message(String sender, String receiver, String time, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", time='" + time + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
