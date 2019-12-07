//控制层
app.controller("chatController", function ($scope, $interval, $filter, chatService) {

    //访问登录界面
    $scope.login = function () {
        chatService.findUser().then(
            function (response) {
                //获取所有用户
                $scope.userMap = response.data;
                console.log("成功进入登录界面！")
            }).catch(function (error) {
            console.log("进入失败，异常为：" + error)
        })
    };

    //访问聊天界面
    $scope.inChat = function () {
        //获取访问参数
        var userName = decodeURI(location.search.substring(1).split(/[=&]/)[1]);
        var role = decodeURI(location.search.substring(1).split(/[=&]/)[3]);
        //获取当前用户名
        $scope.userName = userName;
        //获取可聊天对象
        chatService.findUser().then(
            function (response) {
                //获取其他角色用户
                var userMap = response.data;
                delete userMap[role];
                $scope.userMap = userMap;
            });

        //获取历史聊天对象
        chatService.inChat(userName).then(
            function (response) {
                $scope.chatSet = response.data;
                console.log("[" + userName + "]成功进入聊天界面！")
            }).catch(
            function (error) {
                console.log("[" + userName + "]访问失败，异常为：" + error)
            })

    };

    //点击聊天对象
    $scope.addChatUser = function (sender, receiver) {
        //置顶聊天对象
        if ($.inArray(receiver, $scope.chatSet) != -1) {
            $scope.chatSet.splice($.inArray(receiver, $scope.chatSet), 1);
        }
        $scope.chatSet.unshift(receiver);
        //更改当前聊天对象
        $scope.receiver = receiver;
        //清空聊天内容和输入框
        this.clear();
        //获取聊天记录
        this.findMsg(sender, receiver);
        console.log("[" + sender + "]成功与[" + receiver + "]建立聊天.");
    };

    //获取聊天记录
    $scope.findMsg = function (sender, receiver) {
        chatService.findMsg(sender, receiver).then(
            function (response) {
                $scope.chatList = response.data;
                console.log("获取[" + sender + "]与[" + receiver + "]的聊天记录.");
            }).catch(function (error) {
            console.log("[" + sender + "]获取记录失败.")
        })
    };

    //清屏
    $scope.clear = function () {
        $scope.chatList = null;
        $scope.msg = null;
        console.log("清屏")
    };

    //发言
    $scope.sendMsg = function (sender, receiver, time, msg) {
        //非空判断
        if (receiver == null || receiver === "") {
            alert("请选择聊天对象！");
            $scope.msg = null;
            return
        }
        if (msg == null || msg === "") {
            alert("输入框不能为空！");
            $scope.msg = null;
            return
        }

        //保存数据
        alert("保存数据");
        var map = null;
        chatService.sendMsg(sender, receiver, time, msg).then(
            function (response) {
                 map = response.data;
            }).catch(
                function (reason) {
                    console.log("000")
                });
        //展示数据
        //发送者
        if ($scope.userName === sender) {
            $scope.chatList.unshift(map);
        }
        //接收者
        if ($scope.userName === receiver) {
            //发送者是当前聊天对象
            if ($scope.receiver === sender) {
                $scope.chatList.unshift(map);
            }
            //发送者不是当前聊天对象
            if ($scope.receiver !== sender) {

            }
        }

        //清空输入框
        $scope.msg = null;
    };

    //实时显示当前时间
    $scope.Timer = $interval(function () {
        $scope.time = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
    }, 1000);


});