package cn.com.zxh.chat.controller;

import cn.com.zxh.chat.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

//聊天控制层
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    private ChatService chatService;

    //开启日志
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    /**
     * 直接进入聊天界面
     *
     * @param user 当前用户
     * @return 与该用户聊过天 的其他用户
     */
    @RequestMapping("/inChat")
    public Set inChat(String user) {
        log.info("[" + user + "]进入聊天界面");
        return chatService.findUserByUser(user);
    }

    /**
     * 发送消息
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @param time     时间
     * @param msg      消息内容
     * @return msg
     */
    @RequestMapping("/sendMsg")
    public Map sendMsg(String sender, String receiver, String time, String msg) {
        log.info("[" + sender + "]给[" + receiver + "]发送消息：" + msg);
        return chatService.sendMsg(sender, receiver, time, msg);
    }

    /**
     * 查询聊天记录
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @return 聊天记录
     */
    @RequestMapping("/findMsg")
    public List<Map> findMsg(String sender, String receiver) {
        log.info("获取[" + sender + "]与[" + receiver + "]的聊天记录。");
        return chatService.findMsg(sender, receiver);
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param role     用户角色
     */
    @RequestMapping("/addUser")
    public void addUser(String userName, String role) {
        log.info("新用户[" + userName + "]注册成功!");
        System.out.println(userName);
        chatService.addUser(userName, role);
    }

    /**
     * 获取所有用户
     *
     * @return 用户集合
     */
    @RequestMapping("/findUser")
    public Map<String, List> findUser() {
        log.info("检索所有用户...");
        return chatService.findUser();
    }

}
