<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>我的购物车</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/layer/layer.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/zshop.css">
    <script>
        function shopping() {
            location.href='${pageContext.request.contextPath}/front/product/main';
        }

        //删除商品明细
        function deleteItem() {
            //alert(1);
            $.post('${pageContext.request.contextPath}/front/product/removeItemByIds',
                {"ids":$('#delItemId').val()},
                function (result) {
                    if(result.status==1) {
                        layer.msg(result.message, {
                            time: 2000,
                            skin: 'successMsg'
                        },function () {
                            //刷新整个界面
                            //location.href='${pageContext.request.contextPath}/cart';
                            //局部刷新页面
                            var count=$('#count').val();
                            $('#id_'+count).remove();
                            $('#delItemModel').modal("hide");
                            //重新设置总价
                            $('#totalMoney').html(result.data);

                        });
                    }
                    else{
                        layer.msg(result.message,{
                            time:2000,
                            skin:'errorMsg'
                        },function () {
                            //刷新整个界面
                            //location.href='${pageContext.request.contextPath}/cart'
                            //局部刷新页面
                            var count=$('#count').val();
                            $('#id_'+count).remove();
                            $('#delItemModel').modal("hide");
                            //重新设置总价
                            $('#totalMoney').html(0);

                        });
                    }
                });
        }

        function showDelModel(id,count) {
            //alert(id);
            //将该id赋值到隐藏表单域
            $('#delItemId').val(id);
            $('#count').val(count);
            //显示该删除确认对话框
            $('#delItemModel').modal("show");


        }

        function clearShoppingCart(){
            $.post('${pageContext.request.contextPath}/front/product/clear',
                    function (result) {
                        if(result.status==1){
                            layer.msg(result.message,{
                                time:2000,
                                skin:'successMsg'
                            },function () {
                                //刷新整个页面
                                //location.href='${pageContext.request.contextPath}/cart';
                                //局部刷新
                                //将购物车中的所有行删除
                                $('tr[id]').remove();
                                //将总价置为0
                                $('#totalMoney').html(0);

                            });
                        }
                    });
        }

        function showOrder(){
            //alert(1);
            location.href='${pageContext.request.contextPath}/showOrder';
        }
        
        $(function () {
            $('#all').click(function () {
                //alert(1);
                $('tr[id] input').prop('checked',$('#all').prop('checked'));
            });

            //批量删除
            $('#del').bind('click',function () {
                //alert(1);
                if($('tr[id] input:checked').length==0){
                    layer.msg('请选择需要删除的商品',{
                        time:2000,
                        skin:'errorMsg'
                    });
                }

                else{
                    //组装ids
                    var ids=0;
                    $('table tr[id] input:checked').each(function () {
                        ids+=this.id+',';
                    });
                    ids=ids.substr(0,ids.length-1);
                    //console.log("ids--->"+ids);
                    $.post('${pageContext.request.contextPath}/front/product/removeItemByIds',
                        {'ids':ids},function (result) {
                           if(result.status==1){
                               layer.msg('删除成功',{
                                   time:2000,
                                   skin:'successMsg'
                               },function () {
                                   //将被选中的行删除
                                   $('table tr[id] input:checked').parent().parent().remove();
                                   //重新设置商品总价
                                   $('#totalMoney').html(result.data);
                               });
                           }
                           //如果购物车为空
                           else{
                               layer.msg(result.message,{
                                   time:2000,
                                   skin:'errorMsg'
                               },function () {
                                   //将被选中的行删除
                                   $('table tr[id] input:checked').parent().parent().remove();
                                   //重新设置商品总价
                                   $('#totalMoney').html(0);
                               });
                           }
                        
                        });
                }
            });

            //使用ajax技术修改单个商品的数量
            //文本框内容发生改变时触发
            $(":text").change(function () {
                //alert(1);
                var quantityVal=$.trim(this.value);
                var reg=/^\d+$/g;
                var quantity=-1;
                var flag=false;
                if(reg.test(quantityVal)){
                   quantity= parseInt(quantityVal);
                   if(quantity>0){
                       flag=true;
                   }
                }
                //alert(flag);
                //如果输入的数字<=0,提示用户，将原值赋予文本框
                if(!flag){
                    alert('输入的商品数量必须大于0');
                    $(this).val($(this).prop('class'));
                    return;
                }

                var $tr = $(this).parent().parent();
                var title = $.trim($tr.find('td:eq(2)').text());
                //单机取消按钮
                if(!confirm('确定要修改【'+title+'】的数量吗？')){
                    //将该值之前最近一次正确的值写回文本框
                    $(this).val($(this).prop('class'));
                }
                //单机确定按钮
                else{
                    $(this).prop('class',$(this).val());
                }

                //使用ajax引擎访问服务器
                var url='${pageContext.request.contextPath}/front/product/updateItemQuantity';
                var idVal=$.trim(this.name);
                var args={"id":idVal,"quantity":quantityVal,"time":new Date()};
                //var itemMoneyId=$(this).parent().parent().find("td:eq(6)").prop('id');
                var item_count=$(this).parent().parent().find("td:eq(1)").text();
                $.post(url,args,function (data) {
                    //console.log(data);
                    var itemMoney=data.itemMoney;
                    var totalMoney=data.totalMoney;

                    //console.log(itemMoneyId);
                    $('#itemMoney_'+item_count).html(itemMoney);
                    $('#totalMoney').html(totalMoney);


                })




            });


        });
        
    </script>
</head>

<body>
<% request.setAttribute("index",2);%>
<jsp:include page="top.jsp"/>
<!-- content start -->
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="page-header" style="margin-bottom: 0px;">
                <h3>我的购物车</h3>
            </div>
        </div>
    </div>
    <table class="table table-hover table-striped table-bordered">
        <tr>
            <th>
                <input type="checkbox" id="all">
            </th>
            <th>序号</th>
            <th>商品名称</th>
            <th>商品图片</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>商品总价</th>
            <th>操作</th>
        </tr>
        <c:forEach items="${shoppingcart.items}" var="item" varStatus="s">
        <tr id="id_${s.count}">
            <td>
                <input type="checkbox" id="${item.product.id}">
            </td>
            <td>${s.count}</td>
            <td>${item.product.name}</td>
            <td> <img src="${pageContext.request.contextPath}/front/product/showPic?image=${item.product.image}" alt="" width="60" height="60"></td>
            <td>
                <input type="text" name="${item.product.id}" value="${item.quantity}" size="5" class="${item.quantity}">
            </td>
            <td>${item.product.price}</td>
            <td id="itemMoney_${s.count}"><fmt:formatNumber value="${item.itemMoney}" pattern="#.00"/></td>
            <td>
                <button class="btn btn-success" type="button"> <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>修改</button>
                <button class="btn btn-danger" type="button" onclick="showDelModel(${item.product.id},${s.count})">
                    <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
                </button>
            </td>
        </tr>
        </c:forEach>

        <tr>
            <td colspan="8" align="right">
                <button class="btn btn-warning margin-right-15" type="button" id="del">删除选中项</button>
                <button class="btn btn-warning  margin-right-15" type="button" onclick="clearShoppingCart()"> 清空购物车</button>
                <button class="btn btn-warning margin-right-15" type="button" onclick="shopping()"> 继续购物</button>
                <a>
                    <c:if test="${!empty customer}">
                    <button class="btn btn-warning " type="button" onclick="showOrder()"> 结算</button>
                    </c:if>
                </a>
            </td>
        </tr>
        <tr>
            <td colspan="8" align="right" class="foot-msg">
                总计： <b><span id="totalMoney"><fmt:formatNumber value="${shoppingcart.totalMoney}" pattern="#.00"/></span> </b>元
            </td>
        </tr>
    </table>
</div>
<!-- content end-->

<!-- 确认删除商品明细 start -->
<div class="modal fade" tabindex="-1" id="delItemModel">
    <input type="hidden" id="delItemId"/>
    <input type="hidden" id="count"/>
    <!-- 窗口声明 -->
    <div class="modal-dialog modal-sm">
        <!-- 内容声明 -->
        <div class="modal-content">
            <!-- 头部、主体、脚注 -->
            <div class="modal-header">
                <button class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">提示消息</h4>
            </div>
            <input type="hidden" id="productTypeId">
            <div class="modal-body text-left">
                <h4>确认要删除该商品明细吗？</h4>
            </div>
            <div class="modal-footer">
                <button class="btn btn-warning delProType" onclick="deleteItem()">确定</button>
                <button class="btn btn-primary cancel" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<!-- 确认删除商品明细 end -->

<!-- footers start -->
<div class="footers">
    版权所有：中兴软件技术
</div>
<!-- footers end -->

</body>

</html>