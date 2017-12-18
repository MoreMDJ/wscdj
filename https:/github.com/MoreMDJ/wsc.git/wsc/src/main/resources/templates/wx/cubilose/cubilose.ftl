<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>首页</title>
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
<!--<link href="/wx/css/my_swiper.css" rel="stylesheet" type="text/css" />-->
<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<script type="text/javascript" src="/wx/js/refresh.js"></script>
<script>
 $(document).ready(function(){
	var url = '/wx/tools/more?type=1'; 
	$('.recommend_box').refresh(url,".recommend_box",-1);
})
  
</script>
</head>
<body style="background: #f2f2f2;">
	
<div class="top_size"></div>
<div class="clear"></div>
<!-- header -->
<section class="color_header" >
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftbackwhite.png"> </a>
    <div class="htitle header">燕窝馆</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmorewhite.png"></a>
</section>
<!-- header end -->
	
<!-- banner////////////////////////////////////////////////////////////////////// -->			
<div class="index_banner">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
		    <#if ads_top??>
		    <#list ads_top as ad>
		    <div class="swiper-slide blue-slide">
		    	<a href="${ad.linkUri!''}">	    		
			    	<img src="${ad.imgUri!''}"/>
		    	</a>
		    </div>
		    </#list>
		    </#if>
	  	</div>
	  	<div class="swiper-pagination"></div>
	</div>
	
	<script type="text/javascript">
	  var mySwiper = new Swiper('.index_banner .swiper-container',{
	    loop: true,
		autoplay: 3000,
		pagination : '.index_banner .swiper-pagination'
	  });	
	</script>
</div>		
<!-- banner////////////////////////////////////////////////////////////////////// -->

<!-- cubilose_type -->
<section class="cubilose_type">
	<#if articles_cat??>
	<#list articles_cat as cat>
		<#if cat_index gte 4>
		<#break>
		</#if>
		<#if cat.type?? && cat.type=1 && cat.linkUri?? && cat.linkUri != "">
		<section class="type1"><a href="${cat.linkUri!''}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		<#else>
		<section class="type1"><a href="/wx/cubilose/article/${cat.id?c}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		</#if>
	</#list>
	</#if>
</section>
<!-- cubilose_type end -->

<!-- 活动广告 可增减////////////////////////////////////////////////////////////////////// -->			
<div class="index_banner" style="float: left;>
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
		    <#if ads_mid??>
		    <#list ads_mid as ad>
		    <div class="swiper-slide blue-slide">
		    	<a href="${ad.linkUri!''}">	    		
			    	<img src="${ad.imgUri!''}"/>
		    	</a>
		    </div>
		    </#list>
		    </#if>
		    
	  	</div>
	  	<div class="swiper-pagination"></div>
	</div>
	
	<script type="text/javascript">
	  var mySwiper = new Swiper('.index_banner .swiper-container',{
	    loop: true,
		autoplay: 3000,
		pagination : '.index_banner .swiper-pagination'
	  });	
	</script>
</div>		
<!-- 活动广告 可增减end////////////////////////////////////////////////////////////////////// -->

<!-- 产品广告 可增减////////////////////////////////////////////////////////////////////// -->
<!-- cubilose_adv -->
<#if articles??>
	<#list articles as cat>
		<#if cat_index gte 5>
		<#break>
		</#if>
		<#if cat.type?? && cat.type=1 && cat.linkUri?? && cat.linkUri != "">
		<section class="cubilose_adv mt20">
		<section class="type1"><a href="${cat.linkUri!''}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		</section>
		<#else>
		<section class="cubilose_adv mt20">
		<section class="type1"><a href="/wx/cubilose/article/${cat.id?c}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		</section>
		</#if>
	</#list>
	</#if>

<!-- cubilose_adv end -->
<!-- 产品广告 可增减 end////////////////////////////////////////////////////////////////////// -->

<#-- cubilose_cards会员卡 
<section class="cubilose_cards mt20">
	<a href="#" title=""><img src="/wx/images/ywg/周期定制.png"></a>
	<span class="mt15 fl">
		<!--a的数量可增减
		<a href="#" title=""><img src="/wx/images/ywg/卡1.png"></a>
		<a href="#" title=""><img src="/wx/images/ywg/卡2.png"></a>
		<a href="#" title=""><img src="/wx/images/ywg/卡3.png"></a>
		<a href="#" title=""><img src="/wx/images/ywg/卡4.png"></a>
	</span>
</section>
 cubilose_cards end -->

<!-- cubilose_cards店铺环境 -->
<section class="cubilose_cards mt20">
	<#if shop_cate??>
	<a href="" title=""><img src="${shop_cate.imgUri!''}"></a>
	</#if>
	<span class="mt15 fl">
		<!--a的数量可增减-->
		<#if shop_article??>
		<#list shop_article as cat>
		<#if cat.type?? && cat.type=1 && cat.linkUri?? && cat.linkUri != "">
		<section class="type1"><a href="${cat.linkUri!''}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		<#else>
		<section class="type1"><a href="/wx/cubilose/article/${cat.id?c}" title=""><img src="${cat.coverImgUri!''}"></a></section>
		</#if>
		</#list>
		</#if>
	</span>
</section>
<!-- cubilose_cards end -->



<!-- recommend好货推荐 -->
<section class="recommend">
	<div class="recommend_title colpurple">好货推荐</div>
	<div class="recommend_box">
		<#include "/wx/index_goods.ftl" >
	</div>
	<div id="loading"></div>
</section>
<!-- recommend end -->


<#include "/wx/rightmore.ftl">

</body>
</html>
