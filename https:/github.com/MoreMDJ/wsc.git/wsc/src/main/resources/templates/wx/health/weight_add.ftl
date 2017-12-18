<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>新增体重</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
    <!-- css -->
    <link href="/wx/css/health.css" rel="stylesheet" type="text/css" />
    <link href="/wx/css/common_health.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="/wx/js/common.js"></script>
<script type="text/javascript">
function check()
{
	var value1 = $("#value1").val();
	var value2 = $("#value2").val();
	var value3 = $("#value3").val();
	var addDate = $("#addDate").val();
	if(value1==null || value1=="" || value1==undefined)
	{
		warning("请输入身高");
		document.getElementById("value1").focus()
		return false;
	}
	if(value2==null || value2=="" || value2==undefined)
	{
		warning("请输入体重");
		document.getElementById("value2").focus()
		return false;
	}
	
		
	if(addDate=null || addDate=="" || addDate==undefined)
	{
		warning("请输入日期");
		document.getElementById("addDate").focus()
		return false;
	}
}
</script>

</head>
<body>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<div class="top_size"></div>

<div class="clear"></div> 

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">新增体重记录</div>
    <a href="#" title="" class="hright headright header">帮助</a>
</section>
<!-- header end -->

<!-- 主要参数 -->
<form action="/wx/health/add/3" onsubmit="return check();" method="POST" >
<#if huid??><input type="hidden" name="huid" value="${huid?c}" /></#if>
<section class="h_a_info mt20">
	<dl>
		<dt>身高(厘米)</dt>
		<dd><input type="text" id="value1" name="value1" placeholder="请输入"  />cm</dd>
	</dl>
	<dl>
		<dt>体重(公斤)</dt>
		<dd><input type="text" id="value2" name="value2" placeholder="请输入" />kg</dd>
	</dl>
</section>
<!-- 主要参数 end -->  

<!-- 次要参数 -->
<section class="h_a_info2 mt20">
	<dl>
		<dt>记录时间</dt>
		<dd><input id="addDate" style="height:.38rem;" value="${date?string("yyyy-MM-dd")}" name="addDate" type="date"/></dd>
	</dl>
	
</section>
<!-- 次要参数 end -->   



<input type="submit" class="h_a_btn" value="保存" />
</form>

</body>
</html>
