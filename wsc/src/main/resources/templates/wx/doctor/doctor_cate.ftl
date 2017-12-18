<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>分类列表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />

<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="/wx/css/doctor.css"/>
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<style type="text/css">
	html,body{
		height: 100%;
	}
</style>
</head>
<body class="bgwhite">

<div class="top_size"></div>

<div class="clear"></div>

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="hsearch">
        <form action="/wx/doctor/cate" method="POST">
            <input type="" name="keywords" id="" value="" placeholder="搜索医生、症状、疾病" />
        </form>
    </div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="doctor_type">
	<a href="/wx/doctor/list?keywords=全科"><dl>
		<dt><img src="/wx/images/doctor/quanke.png"></dt>
		<dd>全科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=妇科"><dl>
		<dt><img src="/wx/images/doctor/fuchanke.png"></dt>
		<dd>妇科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=儿科"><dl>
		<dt><img src="/wx/images/doctor/erke.png"></dt>
		<dd>儿科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=皮肤科"><dl>
		<dt><img src="/wx/images/doctor/pifuke.png"></dt>
		<dd>皮肤科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=内科"><dl>
		<dt><img src="/wx/images/doctor/neike.png"></dt>
		<dd>内科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=外科"><dl>
		<dt><img src="/wx/images/doctor/waike.png"></dt>
		<dd>外科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=消化科"><dl>
		<dt><img src="/wx/images/doctor/xiaohuaneike.png"></dt>
		<dd>消化科</dd>
	</dl></a>
	
	<#-- <a href="/wx/doctor/list?keywords=牙科"><dl>
		<dt><img src="/wx/images/doctor/yake.png"></dt>
		<dd>牙科</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=眼科"><dl>
		<dt><img src="/wx/images/doctor/yanke.png"></dt>
		<dd>眼科</dd>
	</dl></a>  -->
	
	<a href="/wx/doctor/list?keywords=中医科"><dl>
		<dt><img src="/wx/images/doctor/erbihouke.png"></dt>
		<dd>中医科</dd>
	</dl></a> 
	
	<a href="/wx/doctor/list?keywords=中西医结合"><dl>
		<dt><img src="/wx/images/doctor/zhongxiyijiehe.png"></dt>
		<dd>中西医结合</dd>
	</dl></a>
	
	<a href="/wx/doctor/list?keywords=男科"><dl>
		<dt><img src="/wx/images/doctor/nanke.png"></dt>
		<dd>男科</dd>
	</dl></a>
</section>




<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
