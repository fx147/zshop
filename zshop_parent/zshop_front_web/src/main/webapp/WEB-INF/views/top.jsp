<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="${pageContext.request.contextPath}/js/template.js"></script>
    <script>

        $(function () {
            //正确显示高亮导航栏
            var curIndex=${requestScope.index};
            $('#navInfo li').each(function (i) {
                //console.log(i);
                //将所有导航栏的背景清除
                $(this).removeClass("active");
                if(curIndex==i){
                    //如果就是当前页，其背景高亮显示
                    $(this).addClass("active");
                }
            });

        });

        //登录
        function loginByAccount() {
            //alert(1);
            $.post('${pageContext.request.contextPath}/front/customer/loginByAccount',$('#frmLoginByAccount').serialize(),
                function (result) {
                    console.log(result);
                    //登录成功
                    if(result.status==1){
                        //返回主界面
                        //location.href='${pageContext.request.contextPath}/front/product/main';
                        var contentA = template('linka');
                        var contentB = template('linkb',result.data);

                        $('#loginModal').modal('hide');
                        //将这段html内容插入到页面对应的位置
                        $('#navInfo').html(contentA);
                        $('#navInfo1').html(contentB);

                    }

                    //登录失败
                    else{
                        //给登录框一个提示信息
                        $('#loginInfo').html(result.message);
                    }
                });
            //console.log($('#frmLoginByAccount').serialize());

        }

        //退出
        function loginOut() {
            $.post('${pageContext.request.contextPath}/front/customer/loginOut',function (result) {
                if(result.status==1){
                    //刷新整个页面
                    location.href='${pageContext.request.contextPath}/front/product/main';
                    //局部刷新页面
                    //通过模板引擎获取整段html页面
                    //var contentC = template('linkc');
                    //var contentD = template('linkd',result.data);
                    //console.log(contentC);
                    //console.log(contentD);
                    //$('#loginModal').modal('hide');
                    //将这段html内容插入到页面对应的位置
                    //$('#navInfo').html(contentC);
                    //$('#navInfo1').html(contentD);


                }
            });
        }
    </script>
    <script id="linka" type="text/html">
        <li>
            <a href="${pageContext.request.contextPath}/front/product/main">商城主页</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/myOrders">我的订单</a>
        </li>
        <li class="active">
            <a href="${pageContext.request.contextPath}/cart">购物车</a>
        </li>
        <li class="dropdown">
            <a href="${pageContext.request.contextPath}/center">会员中心</a>
        </li>

    </script>
    <script id="linkb" type="text/html">
        <li class="userName">
            您好：{{name}}！
        </li>
        <li class="dropdown">
            <a href="#" class="dropdown-toggle user-active" data-toggle="dropdown" role="button">
                <img class="img-circle" src="${pageContext.request.contextPath}/images/user.jpeg" height="30" />
                <span class="caret"></span>
            </a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#" data-toggle="modal" data-target="#modifyPasswordModal">
                        <i class="glyphicon glyphicon-cog"></i>修改密码
                    </a>
                </li>
                <li>
                    <a href="#" onclick="loginOut()">
                        <i class="glyphicon glyphicon-off"></i> 退出
                    </a>
                </li>
            </ul>
        </li>
    </script>

    <script id="linkc" type="text/html">
        <li>
            <a href="${pageContext.request.contextPath}/front/product/main">商城主页</a>
        </li>


    </script>
    <script id="linkd" type="text/html">
        <li>
            <a href="#" data-toggle="modal" data-target="#loginModal">登陆</a>
        </li>
        <li>
            <a href="#" data-toggle="modal" data-target="#registModal">注册</a>
        </li>
    </script>

</head>
<body>

<div class="navbar navbar-default clear-bottom">
    <div class="container">

        <!-- navbar start -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav" id="navInfo">
                <li>
                    <a href="${pageContext.request.contextPath}/front/product/main">商城主页</a>
                </li>

                    <li>
                        <a href="${pageContext.request.contextPath}/myOrders">我的订单</a>
                    </li>
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/cart">购物车</a>
                    </li>
                    <li class="dropdown">
                        <a href="${pageContext.request.contextPath}/center">会员中心</a>
                    </li>

            </ul>
            <ul class="nav navbar-nav navbar-right" id="navInfo1">
                <c:choose>
                    <c:when test="${empty customer}">
                        <li>
                            <a href="#" data-toggle="modal" data-target="#loginModal">登陆</a>
                        </li>
                        <li>
                            <a href="#" data-toggle="modal" data-target="#registModal">注册</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="userName">
                            您好：${customer.name}！
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle user-active" data-toggle="dropdown" role="button">
                                <img class="img-circle" src="${pageContext.request.contextPath}/images/user.jpeg" height="30" />
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li>
                                    <a href="#" data-toggle="modal" data-target="#modifyPasswordModal">
                                        <i class="glyphicon glyphicon-cog"></i>修改密码
                                    </a>
                                </li>
                                <li>
                                    <a href="#" onclick="loginOut()">
                                        <i class="glyphicon glyphicon-off"></i> 退出
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
        <!-- navbar end -->
    </div>
</div>

<!-- 修改密码模态框 -->
<div class="modal fade" id="modifyPasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改密码</h4>
            </div>
            <form action="" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">原密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">新密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">重复密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-warning">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-warning">修&nbsp;&nbsp;改</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 登录模态框 -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">登&nbsp;陆&nbsp;&nbsp;<small class="text-danger" id="loginInfo"></small></h4>
            </div>
            <form action="" class="form-horizontal" method="post" id="frmLoginByAccount">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">登录帐号：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password" id="loginName" name="loginName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">密码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password" name="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">验证码：</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                        <div class="col-sm-2 input-height">
                            1234
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-warning">重&nbsp;&nbsp;置</button>
                    <button type="button" class="btn btn-warning" onclick="loginByAccount()">登&nbsp;&nbsp;陆</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!-- 注册模态框 -->
<div class="modal fade" id="registModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">会员注册</h4>
            </div>
            <form action="" class="form-horizontal" method="post">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户姓名:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">登陆账号:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">登录密码:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系电话:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">联系地址:</label>
                        <div class="col-sm-6">
                            <input class="form-control" type="text">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close">关&nbsp;&nbsp;闭</button>
                    <button type="reset" class="btn btn-warning">重&nbsp;&nbsp;置</button>
                    <button type="submit" class="btn btn-warning">注&nbsp;&nbsp;册</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
