<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>订单详情</title>
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

</head>
<body>

<div class="top_size"></div>

<div class="clear"></div>
<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">订单详情</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="order_address">
	<#if order.expressTitle?? && order.expressTitle?contains('自提')>
	<label>
	<span>自提地址：${order.shopAddress!''}</span>
	</label>
	<#else>
    <p>${order.acceptName!''} <span>${order.telphone!''}</span></p>
    <label>
        <img src="/wx/images/address_icon.png">
        <span>收货地址：${order.fullAddress!''}</span>
    </label>
	</#if>
</section>

<section class="address_bg" style="float:left;"></section>

<!--orderall_box-->
<section class="orderall_box">
    <div class="orderall_list mt20">
        <p><span>订单号：<i>${order.orderNo!''}</i></span><label>${order.statusTitle!''}</label></p>
        <#if order.orderGoods?? && order.orderGoods?size gt 0>
        <#list order.orderGoods as goods>
        <dl>
            <dt><img src="${goods.imgUrl!''}"></dt>
            <dd class="dd1">${goods.goodsTitle!''}</dd>
            <dd class="dd2">套餐类型：<span>无</span></dd>
            <dd class="dd3"><span>${goods.salePrice?string("0.00")}</span> <#if goods.marketPrice??><s>￥${goods.marketPrice?string("0.00")}</s></#if></dd>
        </dl>
        </#list>
	        <i>共<span>${order.orderGoods?size}</span>件商品  合计￥<label>${order.orderAmount?string(0.0)}</label>(含运费￥<span>${order.expressFee?string('0.00') }</span>)</i>
        </#if>
    </div>
</section>
<!--orderall_box end-->

<!--order_logistics-->
<section class="order_logistics mt20">
    <p>物流详情</p>
    <span><i>物流公司：${order.logisticTitle!''}</i><label>物流号：${order.logisticNo!''}</label></span>
    <#--<dl class="order_logistics_colored">
        <dt>重庆市渝中区派件员：河流（13883845552）正在派件</dt>
        <dd>2015-11-30  14:30:21</dd>
    </dl>
    <dl>
        <dt>重庆市渝中区派件员：河流（13883845552）正在派件</dt>
        <dd>2015-11-30  14:30:21</dd>
    </dl>
    <dl>
        <dt>重庆市渝中区派件员：河流（13883845552）正在派件</dt>
        <dd>2015-11-30  14:30:21</dd>
    </dl>-->
</section>
<!--order_logistics end-->
</body>
</html>
