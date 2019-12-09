//业务层
app.service("chatService", function ($http) {

    //获取所有用户
    this.findUser = function () {
        return $http.get("../chat/findUser");
    };

    //获取历史聊天对象
    this.inChat = function (userName) {
        return $http.get("../chat/inChat?user=" + userName);
    };

    //获取聊天记录
    this.findMsg = function (sender, receiver) {
        return $http.get("../chat/findMsg?sender=" + sender + "&receiver=" + receiver);;
    };

    //发送消息
    this.sendMsg = function (sender, receiver, time, msg) {
        return $http.get("../chat/sendMsg?sender=" + sender + "&receiver=" + receiver + "&time=" + time + "&msg=" + msg);;
    }

});