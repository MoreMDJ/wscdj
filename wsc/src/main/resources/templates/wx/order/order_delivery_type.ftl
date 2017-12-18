<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>配送方式</title>
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

    <#-- 引入警告提示样式 -->
	<#include "/common/common_warn.ftl">
	<#-- 引入等待提示样式 -->
	<#include "/common/common_wait.ftl">

<!-- header -->
<form action="/wx/order/delivery/choose" method="post" id="formId" onsubmit="return submitDelivery()">
<section class="white_header">
    <a href="/wx/order" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">配送方式</div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->
<#if express??>
<#list express as express>
<label>
<section class="car4_box<#if express.title?? && express.title?contains("门店自提")> mt20</#if>">
    <dl>
        <dt >
        <input name="deliveryType"  value="${express.title!''}" <#if deliveryType?? && express.title== deliveryType>checked=checked</#if> type="radio">
        <input type="hidden" name="deliveryTypeId" value="${express.id?c}">
        </dt>
        <dd>${express.title!''}</dd>
    </dl>
</section>
</label>
<#if express.title?? && express.title?contains("门店自提")>
<#if shop_choose??>
<a href="/wx/order/delivery/shop" title="" class="car4_store">
    <span id="shopNameId"><#if shop_choose??>${shop_choose.title!'1'}</#if></span><#if shop_choose??>(点击重选)<#else>(点击选择)</#if>
</a>
</#if>
</#if>
</#list>
</#if>
<input type="submit" class="footer_sub" value="确定">
<script type="text/javascript">

	$(function() {
		var ckIn = $(".car4_box input[name='deliveryType']");

		ckIn.click(function() {
			var deliveryName = $(this).val();
			if (!isNull(deliveryName) && deliveryName.indexOf("自提") > 0)
				window.location.href = "/wx/order/delivery/shop";
		});
	});
	function submitDelivery() {
		var ckInput = $(".car4_box input[name='deliveryType']:checked");
		if (!ckInput || ckInput.length < 1) {
			warning("请选择配送方式");
			return false;
		}
		return true;
	}
</script>
</form>
</body>
</html>
