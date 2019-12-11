//控制层
app.controller("chatController", function ($scope, $interval, $filter, $timeout, $rootScope, chatService) {


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
        var userName = decodeURI(location.search.substring(1).split(/[=&]/)[1]);//访问用户
        var role = decodeURI(location.search.substring(1).split(/[=&]/)[3]);//用户权限

        //更新当前用户名
        $scope.userName = userName;
        //更新可聊天对象列表
        chatService.findUser().then(
            function (response) {
                //获取其他角色用户
                var userMap = response.data;
                delete userMap[role];//当前用户，不展示
                $scope.userMap = userMap;
            }).catch(
            function (error) {
                console.log("[" + userName + "]访问失败，异常为：" + error)
            });

        //更新历史聊天对象列表
        $scope.chatSet = this.findChatByUser(userName);
        console.log("[" + userName + "]成功进入聊天界面！")
    };

    //获取历史聊天对象
    $scope.findChatByUser = function (user) {
        chatService.inChat(user).then(
            function (response) {
                $scope.chatSet = response.data;
            }).catch(
            function (error) {
                console.log("[" + user + "]访问失败，异常为：" + error)
            });
    };

    //点击聊天对象
    $scope.addChatUser = function (sender, receiver) {
        //置顶聊天对象
        if ($.inArray(receiver, $scope.chatSet) !== -1) {
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
                // console.log("获取[" + sender + "]与[" + receiver + "]的聊天记录.");
            }).catch(function (error) {
            console.log("[" + sender + "]获取记录失败.原因：" + error)
        });

        this.newChat(100);
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

        //保存数据 并更新当前页面
        chatService.sendMsg(sender, receiver, time, msg).then(
            function (response) {
                $scope.chatList.push(response.data);
                console.log("[" + sender + "]向[" + receiver + "]发言：" + msg);
            }).catch(
            function (error) {
                console.log("[" + sender + "]消息发送失败：" + error)
            });
        //清空输入框
        $scope.msg = null;
        //显示最新信息
        this.newChat(10);
    };


    //实时更新聊天页面
    $scope.upChat = function () {
        //初始化this 防止找不到相关函数
        var thar = this;

        //轮询 实时更新聊天内容
        var t = $interval(function () {
            console.log("更新");
            var receiver = $scope.receiver;

            //获取取当前用户与当前聊天对象的聊天记录数
            chatService.findChatCount($scope.userName, receiver).then(
                function (response) {
                    if (receiver !== undefined) {
                        // console.log(response.data[0]);
                        // console.log($scope.chatList.length);
                        if (response.data[0] !== $scope.chatList.length) {
                            //当数据库的聊天记录与页面上的聊天记录不符合时，更新页面数据
                            thar.findMsg($scope.userName, receiver);
                            console.log("[" + receiver + "]发来消息.")
                        }
                    }

                    // response.data[1]
                    //     if ($scope.chatSet.toString() === response.data[1].toString()){
                    //         $scope.chatSet=response.data[1]
                    // $scope.chatSet.unshift(receiver);
                    //     }
                    angular.forEach(response.data[1],function(user,index){
                        if (!$scope.chatSet.toString().indexOf(user) === -1){
                            // $scope.chatSet=response.data[1]
                            $scope.chatSet.unshift(response.data[1][index]);
                        }
                    });
                    // console.log($scope.chatSet.toString());
                    // console.log(response.data[1].toString());
                    // console.log(response.data[1].toString()===$scope.chatSet.toString())
                }).catch(function (error) {
                console.log("接收[" + $scope.receiver + "]的消息失败,原因：" + error);
            });


        }, 1000);

        //停止轮询
        $scope.stop = function () {
            $interval.cancel(t);
        };

    };
    //继续轮询
    $scope.start = function () {
        this.upChat();
    };


    //清屏
    $scope.clear = function (t) {
        $scope.chatList = null;
        $scope.msg = null;
    };

    //实时显示当前时间
    $scope.Timer = $interval(function () {
        $scope.time = $filter('date')(new Date(), 'yyyy-MM-dd HH:mm:ss');
    }, 1000);

    //滚动条置低（延时加载） t:延迟时间
    $scope.newChat = function (t) {
        $timeout(function () {
            var chatContent = document.getElementById('chatContent');
            chatContent.scrollTop = chatContent.scrollHeight;
        }, t);

    };


    $scope.getQueryVariable = function (variable) {
        var query = window.location.search.substring(1);
        console.log("url:" + query);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] === variable) {
                return decodeURI(pair[1]);
            }
        }
        return false;
    };


});
