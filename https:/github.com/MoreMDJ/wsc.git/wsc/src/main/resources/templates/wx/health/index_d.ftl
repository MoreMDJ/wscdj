<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>会员列表</title>
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
    <div class="htitle header">会员列表</div>
    <a href="#" title="" class="hright headright header"> </a>
</section>
<!-- header end -->

<section class="h_search">
	<form action="/wx/health/search" method="POST">
		<input type="text" name="keywords" placeholder="搜索电话号码" />
		<input type="hidden" name="code" value="1"/>
	</form>
</section>

<section class="h_searchlist">
	<#if user??>
	<#list user as item>
	<a href="/wx/health/infoadd?huid=${item.id?c}"><span>${item.username!''}</span>${item.nickname!''}<i></i></a>
	</#list>
	</#if>
	
</section>

<div class="clear"></div>
<div class="top_size"></div>


<a class="h_register" href="/wx/join?msgId=12" title="">新用户注册</a>


</body>
</html>
