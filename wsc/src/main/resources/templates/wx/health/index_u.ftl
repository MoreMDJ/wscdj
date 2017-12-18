<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>我的健康</title>
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
    <div class="htitle header">我的健康</div>
    <a href="#" title="" class="hright headright header">帮助</a>
</section>
<!-- header end -->

<section class="h_search">
	<form action="/wx/health/search" method="POST">
		<input type="text" name="keywords" placeholder="输入电话查看家人健康档案" />
	</form>
</section>

<!-- 列表 已记录 -->
<section class="h_i_list">
	<span>血压记录</span>
	<a href="/wx/health/add/1" class="add">新增+</a>
	<a href="/wx/health/list/1" class="look">查看></a>
	
</section>
<!-- 列表 end -->   

<!-- 列表 未记录 -->
<section class="h_i_list">
	<span>血糖记录</span>
	<a href="/wx/health/add/2" class="add">新增+</a>
	<a href="/wx/health/list/2" class="look">查看></a>
</section>
<!-- 列表 end -->  

<!-- 列表 未记录 -->
<section class="h_i_list">
	<span>体重记录</span>
	<a href="/wx/health/add/3" class="add">新增+</a>
	<a href="/wx/health/list/3" class="look">查看></a>
</section>
<!-- 列表 end -->  






    






</body>
</html>
