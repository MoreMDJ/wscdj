<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>确认订单</title>
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
    <script type="text/javascript" charset="utf-8" src="/management/js/PCASClass.js"></script>

</head>
<body>
<div id="payScript"></div>
<div class="top_size"></div>

<div class="clear"></div>

	<#-- 引入警告提示样式 -->
	<#include "/common/common_warn.ftl">
	<#-- 引入等待提示样式 -->
	<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">确认订单</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->
<form action="" method="post" id="formId">
<#if deliveryType??>
<input type="hidden" name="deliveryType" id="deliveryTypeId" value="${deliveryType}">
</#if>
<#if shop_choose??>
<input type="hidden" name="shopId" id="shopId" value="${shop_choose.id?c}">
</#if>
<input type="hidden" name="typeId" id="typeId" value="${typeId?c}">
<section class="order_address" id="addressChoose">
	<#if !shop_choose??>
	<#if address??>
    <a href="/wx/order/address?addressId=${address.id?c}" title="">
        <p>${address.acceptName!''} <span>${address.telphone!''}</span></p>
        <label>
            <img src="/wx/images/address_icon.png">
            <span>收货地址：${address.fullAddress!''}</span>
        </label>
    </a>
    <input type="hidden" name="addressId" id="addressId" value="${address.id?c}">
    <#else>
    <a href="/wx/order/address/edit" title="">
    <label></label>
     <p><span>点击我添加地址吧</span></p>
    </a>
    </#if>
	<#else>
	<p>自提门店:${shop_choose.title!''}</p>
	<label>
	<span>门店地址：${shop_choose.fullAddress!''}</span>
	</label>    
    </#if>
</section>


<section class="address_bg" style="float:left;"></section>

<!--car2_list 已选列表-->
<a href="/wx/order/goods" title="" class="car2_list mt20">
    <ul>
        <!--这里面只显示四个，麻烦马老师程序控制一下下-->
        <#if goods??>
        <#list goods as goods>
        <#if goods_index gte 4>
        <#break>
        </#if>
        <li><img src="${goods.gImgUrl!''}"></li>
        </#list>
        </#if>
    </ul>
    <p>共<#if goods??>${goods?size}<#else>0</#if>个</p>
</a>
<!--car2_list end-->

<!--car2_deliver 配送方式选择-->
<a href="/wx/order/delivery" title="" class="car2_deliver mt20">
    <span>配送方式</span>
    <p><#if deliveryType?? && deliveryType?length gt 0>${deliveryType!''}<#else>请选择配送方式</#if></p>
</a>
<!--car2_deliver end-->


<!--car2_deliver 支付方式-->
<section class="car2_deliver mt20">
    <span>支付方式</span>
    <p>
        <select name="payCode" id="paytypeId">
            <#if paytype??>
            <#list paytype as type>
            <option value="${type.code!''}">${type.title!''}</option>
            </#list>
            </#if>
        </select>
    </p>
</section>
<!--car2_deliver end-->

<!-- car2_message 留言 -->
<section class="car2_message mt20"> 
    <input type="text" name="message" placeholder="给商家留言">
</section>
<!-- car2_message end -->

<!-- car2_words 积分 -->
<section class="car2_words mt20">
    <span>积分</span>
    <p>共<span><#if totalPoint??>${totalPoint?c}</#if><span>积分，本单可用<span class="colred">0</span>积分</p>
    <!--<i class="car2_words_choiced"></i>-->
    <input type="checkbox" class="car2_words_checkbox"></input>
</section>
<!-- car2_message end -->

<!-- car2_words 商品金额 -->
<section class="car2_money mt20">
    <p>
        <span>商品金额</span>
        <label>${totalPrice?string('0.00')}</label>
    </p>
    <p>
        <span>运费</span>
        <label>+￥${expressFee?string('0.00')}</label>
    </p>
</section>
<!-- car2_message end -->

<!--car2_deliver 发票选择-->
<#--<a href="#" title="" class="car2_deliver mt20">
    <span>发票信息</span>
    <p>个人</p>
</a>-->

</form>
<!--car2_deliver end-->


<div class="footer_size"></div>

<section class="carfooter">
    <div class="car1_left">
        <i class="car1_left_words">实付款：${(totalPrice + expressFee)?string('0.00')}</i>
    </div>
    <a href="javascript:goPay();" title="" class="car1_right"><#if typeId?? && typeId==1>提交需求<#else>去支付</#if></a>
</section>
	<script type="text/javascript">
		var isDoPay = true;
		function goPay() {
			if (!isDoPay)
				return;
			isDoPay = false;
			wait();
			var postData = $("#formId").serialize()

			var url = "/wx/order/submit";
			var postData = $("#formId").serializeArray();
			if (orderSubmitCheck())
				$.post(url, postData, orderSubmitBack);
		}
		
		function orderSubmitBack(data) {

			if (data.code > 0) {
				if (data.code != 4) {
					close(200);
					warning(data.msg);
					isDoPay = true;
				} else
					window.location.href = "/wx/u/order/1";
				return;
			} else {
				//window.location.href="/wx/u/order/1";
				//window.location.href = "/wx/order/pay";
				$.post("/wx/order/pay", orderPayBack);
			}
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

		function orderSubmitCheck() {
			var addressId = $("#addressId").val();
			var deliveryType = $("#deliveryTypeId").val();
			var shopId = $("#shopId").val();
			var paytype = $("#paytypeId").val();
			if (isNull(deliveryType)) {
				warning("请选择配送方式");
				close(1);
				return false;
			}
			if (deliveryType.indexOf("自提") >= 0) {
				if (isNull(shopId)) {
					warning("请选择自提门店");
					close(1);
					return false;
				}
			} else {
				if (isNull(addressId)) {
					warning("请选择地址");
					close(1);
					return false;
				}
			}
			if (isNull(paytype)) {
				warning("请选择支付方式");
				close(1);
				return false;
			}
			return true;
		}
	</script>



</body>
</html>
