package cn.com.zxh.chat.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ChatService {
    /**
     * 发送消息
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @param time     时间
     * @param msg      消息内容
     * @return msg
     */
    Map sendMsg(String sender, String receiver, String time, String msg);

    /**
     * 查询聊天记录
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @return 聊天记录
     */
    List<Map> findMsg(String sender, String receiver);

    /**
     * 查询用户聊天对象
     *
     * @param user 当前用户
     * @return 与该用户聊过天 的其他用户
     */
    Set findUserByUser(String user);

    /**
     * 注册
     *
     * @param userName 用户名
     * @param role     用户角色
     */
    void addUser(String userName, String role);

    /**
     * 获取所有用户
     *
     * @return 用户信息集合
     */
    Map<String, List> findUser();
}
