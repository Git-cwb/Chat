package cn.com.zxh.chat.service.impl;

import cn.com.zxh.chat.dao.ChatDao;
import cn.com.zxh.chat.service.ChatService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
//聊天业务层

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private ChatDao chatDao;

    /**
     * 发送消息
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @param time     时间
     * @param msg      消息内容
     * @return
     */
    @Override
    public Map sendMsg(String sender, String receiver, String time, String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("sender", sender);
        map.put("receiver", receiver);
        map.put("time", time);
        map.put("message", msg);
        //将该条聊天信息 存入数据库中
        chatDao.addMsg(map);
        System.out.println(map);
        return map;
    }

    /**
     * 查询聊天记录
     *
     * @param sender   发送者
     * @param receiver 接收者
     * @return 聊天记录
     */

    @Override
    public List<Map> findMsg(String sender, String receiver) {
        //创建一个list 存放用户与当前对象的聊天记录
        List<Map> chats = new ArrayList<>();
        //获取所有聊天记录
        List<Map> maps = chatDao.findMsg();
        //遍历记录 筛选出符合的信息
        maps.forEach(map -> {
            //判断该记录是否与发送者有关
            if (map.get("sender").equals(sender) || map.get("receiver").equals(sender)) {
                //判断该条记录是否与接收者有关
                if (map.get("sender").equals(receiver) || map.get("receiver").equals(receiver)) {
                    //添加进 chatMaps中
                    chats.add(map);
                }
            }
        });
        return chats;
    }

    /**
     * 查询 用户聊天对象
     *
     * @param user 当前用户
     * @return 与该用户聊过天 的其他用户
     */
    @Override
    public Set findUserByUser(String user) {
        //创建一个list 存放该用户所有聊天记录
        List<Map> chats = new ArrayList<>();
        //获取所有聊天记录
        List<Map> maps = chatDao.findMsg();
        //遍历记录 筛选出符合的信息
        maps.forEach(map -> {
            //筛选出所有与该用户相关的聊天信息
            if (map.get("sender").equals(user) || map.get("receiver").equals(user)) {
                //判断该条记录是否与接收者有关
                chats.add(map);
            }
        });
        //倒序
        Collections.reverse(chats);
        //创建 set集合 存放聊天对象
        Set<String> userSet = new LinkedHashSet<>();
        chats.forEach(map -> {
            userSet.add(map.get("sender").toString());
            userSet.add(map.get("receiver").toString());
        });
        //删除当前角色
        userSet.remove(user);
        return userSet;
    }

    /**
     * 注册
     *
     * @param userName 用户名
     * @param role     用户角色
     */
    @Override
    public void addUser(String userName, String role) {
        //创建map集合 存放用户信息
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("role", role);
        //调用方法 将新用户信息存入本地文件中
        chatDao.addUser(map);
    }

    /**
     * 获取所有用户
     *
     * @return 用户信息集合
     */
    @Override
    public Map<String, List> findUser() {
        //创建list集合存放 客服用户信息
        List<String> list_S = new ArrayList<>();
        //创建list集合存放 企业用户信息
        List<String> list_E = new ArrayList<>();
        //创建list集合存放 个人用户信息
        List<String> list_T = new ArrayList<>();
        //获取所有用户信息
        List<Map> users = chatDao.findUser();
        users.forEach(map -> {
            if (map.get("role").toString().equals("0")) {
                list_S.add(map.get("userName").toString());
            }
            if (map.get("role").toString().equals("1")) {
                list_E.add(map.get("userName").toString());
            }
            if (map.get("role").toString().equals("2")) {
                list_T.add(map.get("userName").toString());
            }
        });
        //创建 map 存放所有用户信息
        Map<String, List> userMap = new HashMap<>();
        userMap.put("0", list_S);
        userMap.put("1", list_E);
        userMap.put("2", list_T);
        return userMap;
    }
}
