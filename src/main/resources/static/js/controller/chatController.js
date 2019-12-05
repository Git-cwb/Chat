var app = angular.module("chat", []);

app.controller("chatController", function ($scope, $http ) {

    //进入
    $http.get("/chat/findUser").then(function (response) {
        $scope.userMap = response.data;
        console.log("成功进入登录界面！")
    }).catch(function (error) {
            console.log("进入失败，异常为："+error)
        }
    )

});