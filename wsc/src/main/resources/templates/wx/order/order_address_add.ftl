<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if address??>修改地址<#else>新增地址</#if></title>
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
	$(function () {
	var mypcas = new PCAS("txtProvince,所属省份", "txtCity,所属城市", "txtArea,所属地区");
	<#if address??>mypcas.SetValue('${address.province!''}','${address.city!''}','${address.district!''}');</#if>
	});
		function check(){
			var mobile = $("#telphoneId").val();
			var acceptName = $("#acceptNameId").val();
			var province = $("#txtProvince").val();
			var city = $("#txtCity").val();
			var area = $("#txtArea").val();
			var address = $("#addressId").val();
			
			if (isNull(acceptName)) {
				document.getElementById("acceptNameId").focus()
				alert("请输入姓名");
				return false;
			}
			if (mobile == null || mobile == "" || mobile == undefined) {
				alert("请输入手机号");
				document.getElementById("telphoneId").focus()
				return false;
			}
			if (!(/^1\d{10}$/.test(mobile))) {
				alert("请输入正确的手机号");
				document.getElementById("telphoneId").focus()
				return false;
			}
			if (isNull(province)) {
				document.getElementById("txtProvince").focus()
				alert("请选择省份");
				return false;
			}
			if (isNull(area)) {
				document.getElementById("txtArea").focus()
				alert("请选择区");
				return false;
			}
			if (isNull(address)) {
				document.getElementById("addressId").focus()
				alert("请输入详细地址");
				return false;
			}
		}
	</script>
</head>
<body>
<div class="top_size"></div>

<div class="clear"></div>

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header"><#if address??>修改地址<#else>新增地址</#if></div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<!-- center_infobox-->
<section class="center_infobox">
    <form action="/wx/order/address/submit" method="post" onsubmit="return check();">
    <#if address??>
    <input type="hidden" value="${address.id?c}" name="id" />
    </#if>
    <dl>
        <dt>收货人</dt>
        <dd><input type="text" id="acceptNameId" onkeyup="onlyName(this)" onafterpaste="onlyName(this)" name="acceptName" value="<#if address??>${address.acceptName!""}</#if>"class="text center_infobox_text"></dd>
    </dl>
    <dl>
        <dt>联系方式</dt>
        <dd><input type="tel" id="telphoneId" name="telphone" maxlength="11" onkeyup="onlyInt(this)" onafterpaste="onlyInt(this)" value="<#if address??>${address.telphone!""}</#if>" class="text center_infobox_text"></dd>
    </dl>
    <dl>
        <dt>所在地区</dt>
        <dd>
            <select id="txtProvince" name="province" class="select"></select>
        	<select id="txtCity" name="city" class="select"></select>
        	<select id="txtArea" name="district" class="select"></select>
        </dd>
    </dl>
    <dl>
        <dt>详细地址</dt>
        <dd><input type="text" id="addressId" name="address" placeholder="街道 门牌号" value="<#if address??>${address.address!""}</#if>" class="text center_infobox_text"></dd>
    </dl>
    <dl>
        <dt>是否默认</dt>
        <dd class="center_infobox_nomore"><input name="isDefault" type="checkbox"<#if address?? &&address.isDefault?? && address.isDefault>checked="checked"</#if> value="1"style="width: .26rem;height: .26rem;"></dd>
    </dl>
    
        <input type="submit" class="footer_sub" value="保存并使用">
    </form>
</section>
<!-- center_infobox end-->


</body>
</html>
