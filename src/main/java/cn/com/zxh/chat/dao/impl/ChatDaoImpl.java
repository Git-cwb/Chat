package cn.com.zxh.chat.dao.impl;

import cn.com.zxh.chat.dao.ChatDao;
import cn.com.zxh.chat.utils.FileUtils;
import cn.com.zxh.chat.utils.StringUtils;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

//聊天持久层

@Repository
public class ChatDaoImpl implements ChatDao {
    @Resource
    private FileUtils fileUtils;

    @Resource
    private StringUtils stringUtils;

    /**
     * 存储
     *
     * @param map 聊天信息
     */
    @Override
    public void addMsg(Map<String, String> map) {
        //将map转为字符串后 写入本地文件中
        fileUtils.write(stringUtils.mapToString(map), "ChattingRecords", 1);
    }

    /**
     * 查询所有聊天记录
     *
     * @return 聊天记录
     */
    @Override
    public List<Map> findMsg() {
        //从本地文件中 获取所有聊天记录 并返回
        return stringUtils.stringToMapList(fileUtils.read("ChattingRecords"));
    }

    /**
     * 添加新用户
     *
     * @param map 新用户信息
     */
    @Override
    public void addUser(Map<String, String> map) {
        fileUtils.write(stringUtils.mapToString(map), "User", 1);
    }

    /**
     * 获取所有用户
     *
     * @return 用户信息集合
     */
    @Override
    public List<Map> findUser() {
        return stringUtils.stringToMapList(fileUtils.read("User"));
    }


}
