<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>首页</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
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
    window.onload=function(){
        //two2();
    }
    
 $(document).ready(function(){
	var url = '/wx/tools/more?type=0'; 
	$('.recommend_box').refresh(url,".recommend_box",-1);
	/*var cc=document.getElementById('two2');
	var aA=cc.getElementsByTagName('a');
	if(aA[0].className=='active'){
		$('#kill_list').refresh(url+"?kind=3","#kill_list",0);
	}
	if(aA[1].className=='active'){
		$('#persell_list').refresh(url+"?kind=4","#persell_list",0);
	}*/
})
function submitSearch()
{
	var key = $('#keywordsId').val();
	if(key=="" || key=="undefine")
		return;
	document.getElementById('form1').submit();
}
  
</script>
</head>
<body style="background: #f2f2f2;">
<!-- search -->
<div style="width: 100%;background:#5e439b;float: left;height: .88rem;">
<section class="search">
<form id="form1" action="/wx/goods/search" method="POST">
	<input type="" name="keywords" id="keywordsId" value="" placeholder="商品关键字" />
</form>
</section>
<!-- search end -->
<!-- head -->
<div class="myhe headleft">
	<a href="javascript:submitSearch();" title=""><img src="/wx/images/typesearch.png"> </a>
</div>
<div class="myhe headright">
	<a href="/wx/msg" title=""><img src="/wx/images/newsearch.png"> </a>
</div>
</div>
<!-- head end -->
<!-- swiper////////////////////////////////////////////////////////////////////// -->			
<div class="index_banner">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
		    <#if ads??>
		    <#list ads as ad>
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
<!-- swiper////////////////////////////////////////////////////////////////////// -->
<!-- index_nav -->
<section class="index_nav">
	<ul class="box">
		<#if naviItems??>
		<#list naviItems as naviItem>
		<li>
			<a href="${naviItem.linkUrl!''}" <#if naviItem.isBlank>target="__blank"</#if> title="">
				<img alt="" src="${naviItem.imgUrl!''}"/>
			</a>
			<p>${naviItem.title!''}</p>
		</li>
		</#list>
		</#if>
		</li>
	</ul>
</section>
<!-- index_nav end -->

<!-- dynamic  -->
<section class="dynamic bgwhite">
	<label class="bgpurple">9号动态</label>
	<div>
		<#if articles??>
		<#list articles as article>
		<span>${article.title!''}</span>
		</#list>
		</#if>
	</div>
</section>
<!-- dynamic  end -->

<!-- seckill -->
<#--<section class="seckill mt20 bgwhite">
	<div class="seckill_title bgwhite">
			<label></label>
			<ul>
				<li class="seckill_bg">12</li>
				<li>:</li>
				<li class="seckill_bg">12</li>
				<li>:</li>
				<li class="seckill_bg">12</li>
			</ul>
			<a href="#" title="">更多心跳<span>></span></a>
		</div>

	<div class="seckill_box">
		<#if goods??>
		<#list goods.content as goods>
		<a href="/wx/goods/${goods.id?c}" title=""><dl>
			<dt><img src="${goods.imgUrl!''}"> </dt>
			<dd>￥${goods.specialPrice!''}</dd>
			<dd class="seckill_box_cost">￥${goods.marketPrice!''}</dd>
			<dd class="seckill_box_discount bgpurple">#{(goods.salePrice/goods.marketPrice);m1M2}OFF</dd>
		</dl></a>
		</#list>
		</#if>
	</div>
</section>-->
<!-- seckill end -->


<!-- floor -->
<#if cateKey??>
<#list cateKey as cate>
<section class="floor mt20">
	<a href="/wx/goods?cId=${(cate +'cId')?eval?c}" title="查看更多" class="floor_more"><div class="floor_pic"><img src="${(cate +'ImgUrl')?eval!''}"> </div></a>
	<div class="floor_box mt20">
		<div class="swiper-container">
	        <div class="swiper-wrapper" style="padding-left: .2rem;">
	        <#if cate?eval??>
	        <#list cate?eval as goods>
	        <#if goods_index gte 10>
	        <#break>
	        </#if>
	            <div class="swiper-slide">
	            	<a href="/wx/goods/${goods.id?c}" title="">
	            		<dl>
						<dt><img src="${goods.imgUrl!''}"> </dt>
						<dd>${goods.title!''}</dd>
						<dd class="colpurple mtf5">${goods.salePrice?string('currency')}</dd>
						<#-- <#if goods.marketPrice??><dd class="col99918 mtf5">￥${goods.marketPrice?string('#.00') }</dd></#if> -->
						</dl>
	            	</a>
	            </div>
	         </#list>
	            <div class="swiper-slide">
	            	<a href="/wx/goods?cId=${(cate +'cId')?eval?c}" title="查看更多" class="floor_more">查看更多</a>
	            </div>
	         </#if>
	        </div>				     
	    </div>
	    <!-- Swiper JS -->
	
	    <!-- Initialize Swiper -->
	    <script>
	    var swiper = new Swiper('.floor_box .swiper-container', {
	        pagination: '.floor_box .swiper-pagination',
	        slidesPerView:3,
	        paginationClickable: true,
	        spaceBetween: 30

	    });
	    </script>
	</div>
</section>
</#list>
</#if>
<!-- floor end -->



<!-- adv -->
<#if midAds??>
<section class="adv mt20">
<a href="${midAds.linkUri!''}" title=""><img src="${midAds.imgUri!''}"></a>
</section>
</#if>
<!-- adv end -->

<!-- recommend -->
<section class="recommend">
	<div class="recommend_title colpurple">好货推荐</div>
	<div class="recommend_box">
		<#include "/wx/index_goods.ftl" >
	</div>
	<div id="loading"></div>
</section>
<!-- recommend end -->





<div class="footer_size"></div>
<!-- footer -->
<section class="footer">
	<nav>
		<a href="/wx" title="">
			<span>
				<img class="active_img" alt="" src="/wx/images/footer_icon01.png"/>
				<img class="disable_img" alt="" src="/wx/images/footer_icon11.png"/>
			</span>
			<label class="active_label">首页</label>
		</a>
		<a href="/wx/goods/category" title="">
			<span>
				<img alt="" src="/wx/images/footer_icon02.png"/>
				<img alt="" src="/wx/images/footer_icon22.png"/>
			</span>
			<label>分类</label>
		</a>
		<a href="/wx/cart" title="">
			<span>
				<img alt="" src="/wx/images/footer_icon03.png"/>
				<img alt="" src="/wx/images/footer_icon33.png"/>
			</span>
			<label>购物车</label>
		</a>
		<a href="/wx/u" title="">
			<span>
				<img alt="" src="/wx/images/footer_icon05.png"/>
				<img alt="" src="/wx/images/footer_icon55.png"/>
			</span>
			<label>我的</label>
		</a>
	</nav>
</section>
<!-- footer end -->
</body>
</html>
