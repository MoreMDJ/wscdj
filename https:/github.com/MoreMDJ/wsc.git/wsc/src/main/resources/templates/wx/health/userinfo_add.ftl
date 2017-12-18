<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>会员详情</title>
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

</head>
<body>

<div class="top_size"></div>

<div class="clear"></div> 

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">会员详情</div>
    <a href="#" title="" class="hright headright header"> </a>
</section>
<!-- header end -->

<section class="h_searchlist">
	<dl>
		<dt>姓名</dt>
		<dd>${user.nickname!''}</dd>
	</dl>
	<dl>
		<dt>性别</dt>
		<dd><#if user.sex??><#if user.sex=1>男<#elseif user.sex=2>女<#elseif user.sex=3>保密</#if><#else>保密</#if></dd>
	</dl>
	<#-- <dl>
		<dt>年龄</dt>
		<dd>18</dd>
	</dl> -->
	<dl>
		<dt>电话</dt>
		<dd>${user.username!''}</dd>
	</dl>
</section>


<!-- 列表 已记录 -->
<section class="h_i_list">
	<span>血压记录</span>
	<a href="/wx/health/add/1?huid=${user.id?c}" class="look">新增></a>
	
</section>
<!-- 列表 end -->   

<!-- 列表 未记录 -->
<section class="h_i_list">
	<span>血糖记录</span>
	<a href="/wx/health/add/2?huid=${user.id?c}" class="look">新增></a>
</section>
<!-- 列表 end -->  

<!-- 列表 未记录 -->
<section class="h_i_list">
	<span>体重记录</span>
	<a href="/wx/health/add/3?huid=${user.id?c}" class="look">新增></a>
</section>
<!-- 列表 end -->  
</body>
</html>
