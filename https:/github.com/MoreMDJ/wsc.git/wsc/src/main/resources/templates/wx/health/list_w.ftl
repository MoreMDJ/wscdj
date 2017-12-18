<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>体重详情</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
    <!-- css -->
    <link href="/wx/css/swiper.min.css" rel="stylesheet" type="text/css" />
    <link href="/wx/css/health.css" rel="stylesheet" type="text/css" />
    <link href="/wx/css/common_health.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="/wx/js/common.js"></script>
    <script type="text/javascript" src="/wx/js/swiper.min.js"></script>
    



</head>
<body ontouchstart> 

<div class="top_size"></div>

<div class="clear"></div> 

<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1);" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">体重详情</div>
    <a href="#" title="" class="hright headright header"></a>
</section>
<!-- header end -->



<div class="swiper-container">
	<div class="my-pagination"><ul class="my-pagination-ul"></ul></div>
	<div class="swiper-wrapper">
        <!--体重-->
        <div class="swiper-slide">
	        <!-- 时间选择 -->
			<form action="/wx/health/list/3">
			<section class="h_l_time mt20">
				<input class="h_l_timebg" name="beginDate" <#if beginDate??>value="${beginDate}"  </#if> type="date" placeholder="请选择时间" />-
				<input class="h_l_timebg" name="endDate" <#if endDate??>value="${endDate}"  </#if>type="date" placeholder="请选择时间" />	
				<#if huid??><input type="hidden" name="huid" value="${huid?c}"/></#if>
				<input class="h_l_time_btn" type="submit" value="查询" />
			</section>
			</form>
			<!-- 时间选择 end -->  
			
			<!-- 列表 -->
			<#list list as item>
			<section class="h_l_list mt20">
				<dl>
					<dt>${item.addDate?string("yyyy-MM-dd")}</dt>
					<dd>
						<ul>
							<li><span>${(item.value1)!'0'}</span> cm/<span>${(item.value2)!'0'} </span>kg</li>
							<li>BMI: <span>${item.value3!'无'}</span></li>
							<li><span>${item.attribute1!'无'}</span></li>
						</ul>
					</dd>
				</dl>
			</section>
			</#list>
			<!-- 列表 end -->  
        </div>

	</div>
</div>
<#-- 
<script> 
var mySwiper = new Swiper('.swiper-container',{
pagination: '.my-pagination-ul',
paginationClickable: true,
paginationBulletRender: function (index, className) {
switch (index) {
  case 0: name='血糖';break;
  case 1: name='血压';break;
  case 2: name='体重';break;
  default: name='';
}
      return '<li class="' + className + '">' + name + '</li>';
  }
})
</script>
 -->
</body>
</html>
