<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>产品列表</title>
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
<script type="text/javascript" src="/wx/js/refresh.js"></script>
<script>
 $(document).ready(function(){
	//var url = '/wx/goods/list?<#if cId??>cId=${cId?c}</#if>';
	//$('.goods_list_box').refresh(url,".goods_list_box",1);
	var url = '/wx/goods/list';
	$('.goods_list_box').refreshWithDate(url,".goods_list_box",$("#subFormId").serialize(),1);
})
  
</script>
</head>
<body class="bgwhite">
<div class="top_size"></div>
<div class="clear"></div>
<form id="subFormId">
<#if keywords??><input type="hidden" name="keywords" value=${keywords!''}></#if>
<#if cId??><input type="hidden" name="cId" value="${cId?c}"></#if>
</form>

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="hsearch">
        <form action="/wx/goods/search" method="post">
            <input type="" name="keywords" id="" value="<#if keywords??>${keywords!''}</#if>" placeholder="搜索药名、品牌、症状" />
            <#if cId??><input type="hidden" name="cId" value="${cId?c}"></#if>
        </form>
    </div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->



<!-- goods_lis_type -->
<section class="goods_lis_type wrap100">
    <a title="">全部<span> </span> </a><label></label>
    <a title="">综合排序<span> </span> </a>
</section>
<!-- goods_lis_type end -->

<!-- goods_list_box -->
<section class="goods_list_box wrap100">
	<#if goods??>
	<#list goods.content as goods>
	<a href="/wx/goods/${goods.id?c}" title="">
		<dl>
			<dt>
				<img src="${goods.imgUrl!''}">
			</dt>
			<dd class="goods_list_box_ddtitle">${goods.title!''}</dd>
			<dd>
				<span class="goods_list_box_activity colpurple">满减</span>
				<span class="goods_list_box_activity colpurple">秒杀</span>
			</dd>
			<dd>
				<b class="colred fs26">${goods.salePrice?string('currency') }</b> <#-- <#if goods.marketPrice??><label class="col99918">${goods.marketPrice?string('currency')}</label></#if> -->
			</dd>
		</dl>
	</a>
	</#list>
    </#if>
</section>
<!-- goods_list_box end -->

<!-- goods_lis_type1 大分类选择pop -->
<section class="goods_lis_type1">
    <ul class="type1_ul1">
    		<#if parentCate??>
    		<#list parentCate as cate>
	            <li>${cate.title!''}</li>
	        </#list>
	        </#if>
    </ul>
    <#if parentCate??>
    		<#list parentCate as pcate>
    	<div class="type1_ul2">
	    		<#if (pcate.title + pcate.id?c)?eval??>
	    		<#list (pcate.title + pcate.id?c)?eval as cateKey>
			        <ul>
	    			<#if cateKey?eval??>
	    			<#list cateKey?eval as cate>
			        	<#if cate_index == 0>
			            <a href="/wx/goods?cId=${cate.id?c}" title=""><li>${cate.title!""}</li></a>
			            <#else>
			            <a href="/wx/goods?cId=${cate.id?c}" title=""><li>${cate.title!""}</li></a>
			            </#if>
			        </#list>
			        </#if>
			        </ul>
		        </#list>
		        </#if>
    	</div>
	        </#list>
        </#if>
</section>
<!-- goods_lis_type1 end -->

<!-- goods_lis_type2 排序选择pop -->
<section class="goods_lis_type2">
    <ul class="type1_ul3">
        <li class="type1_ul2_choiced">默认排序</li>
        <li>价格升序</li>
        <li>价格降序</li>
        <li>销量排序</li>
    </ul>
</section>
<!-- goods_lis_type1 end -->

<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
