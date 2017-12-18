<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>${title!''}</title>
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
<script type="text/javascript" src="/wx/js/goods.js"></script>

</head>
<body>
	<#-- 引入警告提示样式 -->
	<#include "/common/common_warn.ftl">
	<#-- 引入等待提示样式 -->
	<#include "/common/common_wait.ftl">
	<!-- header -->
	<section class="white_header">
		<a href="javascript:history.go(-1)" title=""
			class="hleft headleft header"><img src="/wx/images/leftback.png">
		</a>
		<div class="htitle header">${title!''}</div>
		<a title="" class="hright headright header"><img
			src="/wx/images/rightmore.png"></a>
	</section>
	<!-- header end -->

	<!-- order_tab -->
	<section class="orderall_tab">
		<a href="#" title="" class="orderall_tab_choiced"
			style="margin: 0 20%;">OTC</a> <a href="#" title=""
			style="margin: 0 20%;">处方药</a>

	</section>
	<!-- order_tab end -->

	<!--car_box-->
	<form id="formId" action="/wx/order" method="post">
	<input type="hidden" name="totalPrice" id="inputTotalPrice" value="">
	<section class="car_box mt20">
		<#if goods ?? && goods?size gt 0>
		<#list goods as goods>
		<div class="car_list">
			<input type="checkbox" name="ckIds" value="${goods_index}" class="car1_checkbook"></input>
			<input type="hidden" name="ids" value="${goods.id?c}">
			<input type="hidden" name="gid" value="${goods.gid?c}">
			<input type="hidden" id="inventory${goods_index}" value="${goods.leftQuantity?c}">
			<input type="hidden" id="sprice${goods_index}" value="${goods.salePrice?string("0.00")}">
			
			<a href="/wx/goods/${goods.gid?c}" title="">
				<dl>
					<dt><img src="${goods.gImgUrl!''}"></dt>
					<dd class="dd1">${goods.gTitle!''}</dd>
					<dd class="dd2">套餐类型：<span>三盒装</span></dd>
					<dd class="dd3">
						<span id="price${goods_index}">￥${goods.salePrice?string("0.00") }</span>
						<i class="car_list_num">
						 <a onclick="changeQuantity(${goods_index},'sub')" title="">-</a>
						 <input type="tel" name="quantitys" onchange="updataPrice()" onkeyup="onlyInt(this)" id="quantity${goods_index}" value="${goods.quantity}" class="text">
						 <a onclick="changeQuantity(${goods_index},'add')" title="">+</a>
						</i>
					</dd>
				</dl>
			</a>
		</div>
		</#list>
		<#else>
		<div class="car_list">
		<a href="/wx/goods" style="color:red"><dl style="width:6.4rem;margin-top:50%;text-align:center;">      购物车空空如也，点击此处去抢购吧！</dl></a>
		</div>
		</#if>
	</section>
	</form>
	<!--car_box end-->

	<div class="footer_size"></div>

	<section class="carfooter">
		<div class="car1_left">
			<input type="checkbox" id="all_sel" class="car1_checkbook3"></input> <i>全选</i>
			<dl>
				<dd>合计：￥<span id="totalPrice">0.00</span></dd>
				<dt>不含运费</dt>
			</dl>
		</div>
		<#-- <a onclick="order()" title="" class="car1_right">${payTitle!''}(<span id="countId">0</span>)</a> -->
		<a onclick="checkLimitGoods()" title="" class="car1_right">${payTitle!''}(<span id="countId">0</span>)</a>
    </section>
<script  type="text/javascript">
$(function(){
	$('#all_sel').click(function()
	{
		$('.car_list input[name="ckIds"]').prop("checked",this.checked); 
		if(this.checked)
		{
			$('#countId').html($(".car_list input[name='ckIds']:checked").length);
			updataPrice();
		 }
		else
			{
			$('#countId').html(0);
			updataPrice();
			}
    });
    var $subBox = $(".car_list input[name='ckIds']");
    $subBox.click(function()
    {
        $("#all_sel").prop("checked",$subBox.length == $(".car_list input[name='ckIds']:checked").length ? true : false);
        $('#countId').html($(".car_list input[name='ckIds']:checked").length);
        updataPrice();
    });
});
</script>

<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->
</body>
</html>
