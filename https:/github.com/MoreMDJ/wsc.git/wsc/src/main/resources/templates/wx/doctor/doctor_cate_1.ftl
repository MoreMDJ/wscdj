<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>名医咨询</title>
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

<link rel="stylesheet" type="text/css" href="/wx/css/doctor_1.css"/>





<!-- js -->
<script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
<script type="text/javascript" src="/wx/js/swiper.min.js"></script>
<script type="text/javascript" src="/wx/js/common.js"></script>
<script type="text/javascript" src="/wx/js/refresh.js"></script>
<style type="text/css">
	html,body{
		height: 100%;
	}
</style>
<script  type="text/javascript">
$(document).ready(function(){
	var url = '/wx/tools/more?type=0'; 
	/*$('.doctor_list').refresh(url,".doctor_list",-1);
	var cc=document.getElementById('two2');
	var aA=cc.getElementsByTagName('a');
	if(aA[0].className=='active'){
		$('#kill_list').refresh(url+"?kind=3","#kill_list",0);
	}
	if(aA[1].className=='active'){
		$('#persell_list').refresh(url+"?kind=4","#persell_list",0);
	}*/
})
</script>
<#include "/common/wx_share.ftl">
</head>
<body>

<div class="top_size"></div>

<div class="clear"></div>

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="hsearch">
        <form action="/wx/doctor/list" method="POST">
            <input type="" name="keywords" id="" value="" placeholder="搜索医生、症状、疾病" />
        </form>
    </div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->



<!-- 20171031 -->
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


<!-- 20171031 end -->



<!-- 20171031 -->
<section class="top_link">
	<a href="/wx/doctor/list">
	<dl class="top_l_left">
		<dt><img src="/wx/images/doctor/i1.png"></dt>
		<dd>咨询问诊</dd>
		<dd>视频咨询医生、药师</dd>
	</dl>
	</a>
	<section class="top_l_right">
		<a href="http://www.jkwin.com.cn/ystm/mainPage.do?method=mainPage&regWayCode=ZSM_WX" >
		<dl>
			<dt><img src="/wx/images/doctor/i2.png"></dt>
			<dd>预约挂号</dd>
			<dd>三甲医院不用愁</dd>
		</dl>
		</a>
		<a href="/wx/health/u"><dl>
			<dt><img src="/wx/images/doctor/i3.png"></dt>
			<dd>我的健康</dd>
			<dd>健康信息握在手</dd>
		</dl>
		</a>
	</section>
</section>
<!-- 20171031 end -->


<section class="doctor_type">
	<#if docnavi??>
    <#list docnavi as navi>
    <a href="/wx/doctor/list?keywords=${navi.title!''}"><dl>
		<dt><img src="${navi.imgUrl!''}"></dt>
		<dd>${navi.title!''}</dd>
	</dl></a>
    </#list>
    </#if>
	<#-- <a href="/wx/doctor/list?keywords=全科"><dl>
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
	</dl></a>-->
</section>



<section id="doctor_list" class="doctor_list">
    <!--一条-->
    <#include "/wx/doctor/doctor_one.ftl">
    <!--一条 end-->
	
</section>





<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->

</body>
</html>
