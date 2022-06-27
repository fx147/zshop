1.搭建环境
a.创建多模块项目
zshop_parent
zshop_common
zshop_pojo
zshop_dao
zshop_service
zshop_front_web
zshop_background_web

各层间的数据封装模型
页面---->controller(vo)---->service(dto)---->dao(pojo)
页面表单中的值：request.getParameter("key")

vo:file对象
dto---file对象转换成流对象

前台购物车对象
购物车对象：ShoppingCart.java  (service提供)
购物车明细对象:ShoppingCartItem.java(service提供)
购物车工具类:ShoppingCartUtils.java(front-web提供)

