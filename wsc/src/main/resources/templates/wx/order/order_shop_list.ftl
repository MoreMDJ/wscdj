<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>门店选择</title>
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
    <script type="text/javascript">
    $(function(){
    	var mypcas = new PCAS("txtProvince,所属省份", "txtCity,所属城市", "txtArea,所属地区");
    	mypcas.SetValue('${province!''}','${city!''}','${district!''}');
    
	    $(".address_top").change(function(){
	    	 $("#formId").attr("action","/wx/order/delivery/shop/search");
	    	 $("input[name='action']").val("search");
	    	 $("#formId").submit();
	    });
    });
    function submitCheck()
    {
    	var ckInput = $("input[name='shopId']:checked");
    	if(!ckInput||ckInput.length < 1)
    		{
    		warning("请选择门店");
    		return false;
    		}
    	
    	return true;
    	
    	
    }
    </script>

</head>
<body>
<div class="top_size"></div>

<div class="clear"></div>

	<#-- 引入警告提示样式 -->
	<#include "/common/common_warn.ftl">
	<#-- 引入等待提示样式 -->
	<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="/wx/order/delivery" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">门店选择</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->
<form action="/wx/order/delivery/shop/action" onsubmit="return submitCheck()" method="post" id="formId">
<input type="hidden" name="action">
<section class="address_top">
    <select id="txtProvince" name="province"></select>
    <select id="txtCity" name="city" ></select>
    <select id="txtArea" name="district"></select>
</section>

<#if shops??>
<#list shops as shop>
<!--一条信息-->
<section class="car44_box mt20">
    <input type="radio" name="shopId" value="${shop.id?c}" <#if shop_choose?? && shop.id == shop_choose.id>checked=checked</#if> class="car44_radio">
    <a href="javascript:;" title="">
        <b>${shop.title!''}</b>
        <p><i>${shop.province!''}</i>,<i><#if shop.province?? && !shop.province?contains("市")> ${shop.city!''},</#if>${shop.district!''},${shop.address!'' }</i></p>
    </a>
</section>
<!--一条信息 end-->
</#list>
</#if>

<section class="footer_size"></section>

<input type="submit" class="footer_sub" value="确定">
</form>

</body>
</html>
