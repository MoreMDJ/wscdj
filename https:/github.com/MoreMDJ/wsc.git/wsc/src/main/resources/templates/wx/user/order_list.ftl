 <!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>我的订单</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
    <!-- css -->
    <link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
    <link href="/wx/css/center.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="/wx/js/common.js"></script>
	<script type="text/javascript">
	function submitOrderPay(orderid)
	{
		wait();
		$.post("/wx/order/user/pay?orderId=" + orderid, orderPayBack);
	}
	function orderPayBack(data) {
		if (data.status > 0) {
			close(200);
			warning(data.msg);
			isDoPay = true;
			return;
		}
		$("#payScript").append(data);

	}
	</script>
</head>
<body>
<div id="payScript"></div>

<div class="top_size"></div>

<div class="clear"></div>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">
<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

<!-- header -->
<section class="white_header">
    <a href="/wx/u" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">我的订单</div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<!-- order_tab -->
<section class="orderall_tab">
    <a href="/wx/u/order/1" title="" <#if !orderStatus?? || orderStatus == 1>class="orderall_tab_choiced"</#if> >全部</a>
    <a href="/wx/u/order/2" title="" <#if orderStatus?? && orderStatus == 2>class="orderall_tab_choiced"</#if> >待付款</a>
    <a href="/wx/u/order/3" title="" <#if orderStatus?? && orderStatus == 3>class="orderall_tab_choiced"</#if> >待发货</a>
    <a href="/wx/u/order/4" title="" <#if orderStatus?? && orderStatus == 4>class="orderall_tab_choiced"</#if>>待收货</a>
    <a href="/wx/u/order/6" title="" <#if orderStatus?? && orderStatus == 6>class="orderall_tab_choiced"</#if>>待取货</a>
</section>
<!-- order_tab end -->

<!--orderall_list-->
<section class="orderall_box">
    <#include "/wx/user/order_list_body.ftl">
</section>
<!--orderall_list end-->

</body>
</html>
