<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>backend</title>
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/index.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/userSetting.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-paginator.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrapValidator.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrapValidator.min.js"></script>
    <script>
        $(function () {
            $('#pagination').bootstrapPaginator({

                bootstrapMajorVersion: 3,
                currentPage: ${data.pageNum},
                totalPages:${data.pages},
               /* pageUrl: function (type, page, current) {
                    return "${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum="+page;
                },*/
                onPageClicked: function (event, originalEvent, type, page) {

                    $("#pageNum").val(page);
                    $("#frmQuery").submit();

                },
                itemTexts: function (type, page, current) {
                    switch (type) {
                        case "first":
                            return "??????";
                        case "prev":
                            return "?????????";
                        case "next":
                            return "?????????";
                        case "last":
                            return "??????";
                        case "page":
                            return page;
                    }
                }
            });
            $('#frmAddSysuser').bootstrapValidator({
                feedbackIcons:{
                    valid:"glyphicon glyphicon-ok",
                    invalid:"glyphicon glyphicon-remove",
                    validating:"glyphicon glyphicon-refresh"
                },
                fields:{
                   name:{
                       validators:{
                           notEmpty:{
                               message:'?????????????????????'
                           }
                       }
                   },
                   loginName:{
                       validators: {
                           notEmpty: {
                               message:'????????????????????????'
                           },
                           remote:{
                               //?????????????????????????????????
                               url:'${pageContext.request.contextPath}/backend/sysuser/checkName'
                           }
                       }
                   },
                   password:{
                       validators:{
                           notEmpty:{
                               message:'??????????????????'
                           }
                       }
                   },
                   phone:{
                       validators:{
                           notEmpty:{
                               message:'????????????????????????'
                           }
                       }
                   },
                   email:{
                       validators:{
                           notEmpty:{
                               message:'??????????????????'
                           },
                           emailAddress:{
                               message:'?????????????????????'
                           }
                       }
                   },
                   roleId:{
                       validators:{
                           notEmpty:{
                               message:'???????????????'
                           }
                       }
                   }
                }
            });



        });
       function addSysuser() {

            //??????????????????
            var bv=$('#frmAddSysuser').data('bootstrapValidator');
            bv.validate();
            if(bv.isValid()){
                $.post('${pageContext.request.contextPath}/backend/sysuser/add',
                    $('#frmAddSysuser').serialize(),function (result) {
                        //console.log($('#frmAddSysuser').serialize());
                        if(result.status==1){
                            layer.msg(result.message,{
                                time:2000,
                                skin:"successMsg"
                            },function () {
                                location.href="${pageContext.request.contextPath}/backend/sysuser/findAll?pageNum="+${data.pageNum};
                            });
                        }
                        else{
                            layer.msg(result.message,{
                                time:2000,
                                skin:"errorMsg"
                            });
                        }
                    });
            }

        }

        //???????????????
        function  doQuery() {
            $('#pageNum').val(1);
            $('#frmQuery').submit();
        }

        //??????????????????
        function modifyStatus(id,btn) {
           $.post("${pageContext.request.contextPath}/backend/sysuser/modifyStatus",
               {"id":id},function (result) {
                   if(result.status==1){
                      var $td=$(btn).parent().parent().children().eq(5);
                      if($td.text().trim()=="??????"){
                          $td.text("??????");
                          $(btn).val("??????").removeClass("btn-danger").addClass("btn-success");
                      }
                      else{
                          $td.text("??????");
                          $(btn).val("??????").removeClass("btn-success").addClass("btn-danger");
                      }

                   }

               });
            
        }
    </script>
</head>

<body>
<!-- ?????????????????? -->
<div class="panel panel-default" id="adminSet">
    <div class="panel-heading">

        <h3 class="panel-title">??????????????????</h3>
    </div>
    <div class="panel-body">
        <div class="showmargersearch">
            <form id="frmQuery" class="form-inline" action="${pageContext.request.contextPath}/backend/sysuser/findByParams" method="post">
                <input type="hidden" name="pageNum" value="${data.pageNum}" id="pageNum">
                <div class="form-group">
                    <label for="userName">??????:</label>
                    <input type="text" class="form-control" id="userName" placeholder="???????????????" name="name" value="${sysuserParams.name}">
                </div>
                <div class="form-group">
                    <label for="loginName">??????:</label>
                    <input type="text" class="form-control" id="loginName" placeholder="???????????????" name="loginName" value="${sysuserParams.loginName}">
                </div>
                <div class="form-group">
                    <label for="phone">??????:</label>
                    <input type="text" class="form-control" id="phone" placeholder="???????????????" name="phone" value="${sysuserParams.phone}">
                </div>
                <div class="form-group">
                    <label for="role">??????</label>
                    <select class="form-control" name="roleId" id="role">
                        <option value="-1">??????</option>
                        <c:forEach items="${roles}" var="role">

                            <option value="${role.id}" <c:if test="${role.id==sysuserParams.roleId}"> selected</c:if>>${role.roleName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="status">??????</label>
                    <select class="form-control" name="isValid" id="status">
                        <option value="-1">??????</option>
                        <option value="1" <c:if test="${sysuserParams.isValid==1}"> selected</c:if>>---??????---</option>
                        <option value="0" <c:if test="${sysuserParams.isValid==0}"> selected</c:if>>---??????---</option>
                    </select>
                </div>
                <input type="button" value="??????" class="btn btn-primary" id="doSearch" onclick="doQuery()">
            </form>
        </div>
        <br>
        <input type="button" value="??????????????????" class="btn btn-primary" id="doAddManger">
        <div class="show-list text-center">
            <table class="table table-bordered table-hover" style='text-align: center;'>
                <thead>
                <tr class="text-danger">
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">????????????</th>
                    <th class="text-center">??????</th>
                    <th class="text-center">??????</th>
                </tr>
                </thead>
                <tbody id="tb">
                <c:forEach items="${data.list}" var="sysuser">
                    <tr>
                        <td>${sysuser.id}</td>
                        <td>${sysuser.name}</td>
                        <td>${sysuser.loginName}</td>
                        <td>${sysuser.phone}</td>
                        <td>${sysuser.email}</td>
                        <td>
                            <c:if test="${sysuser.isValid==1}">??????</c:if>
                            <c:if test="${sysuser.isValid==0}">??????</c:if>
                        </td>
                        <td><fmt:formatDate value="${sysuser.createDate}" pattern="yyyy-MM-dd"/></td>
                        <td>${sysuser.role.roleName}</td>
                        <td class="text-center">
                            <input type="button" class="btn btn-warning btn-sm doMangerModify" value="??????">
                            <c:if test="${sysuser.isValid==1}">
                            <input type="button" class="btn btn-danger btn-sm doMangerDisable" value="??????" onclick="modifyStatus(${sysuser.id},this)">
                            </c:if>
                            <c:if test="${sysuser.isValid==0}">
                            <input type="button" class="btn btn-success btn-sm doMangerDisable" value="??????" onclick="modifyStatus(${sysuser.id},this)">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <ul id="pagination"></ul>
        </div>
    </div>
</div>

<!-- ?????????????????? start -->
<div class="modal fade" tabindex="-1" id="myMangerUser">
    <!-- ???????????? -->
    <div class="modal-dialog">
        <form id="frmAddSysuser">
        <!-- ???????????? -->
        <div class="modal-content">
            <!-- ???????????????????????? -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">??????????????????</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="marger-username" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="marger-username" name="name">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-loginName" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="marger-loginName" name="loginName">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-password" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="password" class="form-control" id="marger-password" name="password">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-phone" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="marger-phone" name="phone">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="marger-adrees" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="marger-email" name="email">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="role" class="col-sm-4 control-label">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</label>
                    <div class=" col-sm-4">
                        <select class="form-control" name="roleId">
                            <option value="">?????????</option>
                            <c:forEach items="${roles}" var="role">
                                 <option value="${role.id}" >${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" type="button" onclick="addSysuser()">??????</button>
                <button class="btn btn-primary cancel" data-dismiss="modal" type="button">??????</button>
            </div>
        </div>
        </form>
    </div>
</div>
<!-- ?????????????????? end -->

<!-- ?????????????????? start -->
<div class="modal fade" tabindex="-1" id="myModal-Manger">
    <!-- ???????????? -->
    <div class="modal-dialog">
        <!-- ???????????? -->
        <div class="modal-content">
            <!-- ???????????????????????? -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">??????????????????</h4>
            </div>
            <div class="modal-body text-center">
                <div class="row text-right">
                    <label for="MargerUsername" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffId" readonly="readonly">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerUsername" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerStaffname">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerLoginName" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerLoginName" readonly="readonly">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerPhone" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="MargerPhone">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerAdrees" class="col-sm-4 control-label">???????????????</label>
                    <div class="col-sm-4">
                        <input type="email" class="form-control" id="MargerAdrees">
                    </div>
                </div>
                <br>
                <div class="row text-right">
                    <label for="MargerRole" class="col-sm-4 control-label">???&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;??????</label>
                    <div class=" col-sm-4">
                        <select class="form-control" id="MargerRole">
                            <option>?????????</option>
                            <option>??????????????????</option>
                            <option>??????????????????</option>
                        </select>
                    </div>
                </div>
                <br>
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary doMargerModal">??????</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">??????</button>
            </div>
        </div>
    </div>
</div>
<!-- ?????????????????? end -->

</body>

</html>