package cn.com.zxh.chat.dao;

import java.util.List;
import java.util.Map;

/**
 * @author xmzxh
 */
public interface ChatDao {
    /**
     * 存储聊天信息
     *
     * @param map 聊天信息
     */
    void addMsg(Map<String, String> map);

    /**
     * 查询所有聊天记录
     *
     * @return 聊天记录
     */
    List<Map> findMsg();

    /**
     * 添加新用户
     *
     * @param map 新用户信息
     */
    void addUser(Map<String, String> map);

    /**
     * 获取所有用户
     *
     * @return 用户信息集合
     */
    List<Map> findUser();
}
