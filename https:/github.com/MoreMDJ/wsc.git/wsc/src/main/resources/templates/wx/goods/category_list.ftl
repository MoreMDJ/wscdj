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
<link href="/wx/css/main.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<style type="text/css">
	html,body{
		height: 100%;
		overflow: hidden;
	}
</style>
</head>
<body class="bgwhite">

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="hsearch">
        <form>
            <input type="" name="" id="" value="" placeholder="搜索药名、品牌、症状" />
        </form>
    </div>
    <a class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<!--type_box-->
<section class="type_box">
    <div class="type_box_left">
    	<div class="type-left-scroll">
    		<#if parentCate??>
    		<#list parentCate as cate>
	        <dl>
	            <dt><img src="${cate.imgUri!''}"> </dt>
	            <dd>${cate.title!''}</dd>
	        </dl>
	        </#list>
	        </#if>
    	</div>
    </div>

    <div class="type_box_right">
   		<#if parentCate??>
    		<#list parentCate as pcate>
    	<div class="type-right-scroll">
	    		<#if (pcate.title + pcate.id?c)?eval??>
	    		<#list (pcate.title + pcate.id?c)?eval as cateKey>
			        <dl>
	    			<#if cateKey?eval??>
	    			<#list cateKey?eval as cate>
			        	<#if cate_index == 0>
			            <a href="/wx/goods?cId=${cate.id?c}" title=""><dt>${cate.title!""}</dt></a>
			            <#else>
			            <a href="/wx/goods?cId=${cate.id?c}" title=""><dd>${cate.title!""}</dd></a>
			            </#if>
			        </#list>
			        </#if>
			        </dl>
		        </#list>
		        </#if>
    	</div>
	        </#list>
        </#if>
    </div>
</section>
<!--type_box end-->





<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
