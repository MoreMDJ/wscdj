<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>产品详情</title>
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
<script type="text/javascript" src="/wx/js/goods.js"></script>
<script type="text/javascript">
$(function(){

	// 加入购物车
    $("#cartId").click(function(){
        $.ajax({
            url:"/wx/cart/add",
            type: 'POST',
            data: {id:<#if goods??>${goods.id?c}</#if>,quantity:$("#quantity"+<#if goods??>${goods.id?c}</#if>).val(),type:$(".gsct_info input[name='type']").val()},
            success:function(res){
              if (0==res.error)
              {
                warning(res.msg);
                $("#cart_number").html('<font>' + res.number + '</font>');
              }
              else if(res.error == 2)
              {
            	  
            	  warning(res.msg);
            	  setTimeout(function(){
            		  wait();
                	  },1000);
            	  setTimeout(function(){
            	  window.location.href="/wx/login?returnTo=/wx/goods/${goods.id?c}?type=cartType";
            	  },1200);
              }
              else if(res.error == 1)
              {
            	  warning(res.msg);
              }
              else if(res.error == 3)
              {
            	  warning(res.msg);
              }
            }
        });
    });
	
 	// 立即购买
    $("#buyNowId").click(function(){
    	//window.location.href="/wx/cart";
    	$.ajax({
            url:"/wx/cart/add",
            type: 'POST',
            data: {id:<#if goods??>${goods.id?c}</#if>,quantity:$("#quantity"+<#if goods??>${goods.id?c}</#if>).val()},
            success:function(res){
              if (0==res.error)
              {
            	  var formVar = $("#formId");
            	  $("#hiddenIdsId").val(res.cartGoodsId);
            	  formVar.submit();
              }
              else if(res.error == 2)
              {
            	  warning(res.msg);
            	  setTimeout(function(){
            		  wait();
                	  },1000);
            	  setTimeout(function(){
            	  window.location.href="/wx/login?returnTo=/wx/goods/${goods.id?c}";
            	  },1200);
              }
              else if(res.error == 1)
              {
            	  warning("参数错误");
              }
              else if(res.error == 3)
              {
            	  warning(res.msg);
              }
            }
        });
    });
	
	// 收藏商品
    $("#collectDivId").click(function(){
    	$.ajax({
            url:"/wx/goods/collect/toggle",
            data: {gid:<#if goods??>${goods.id?c}</#if>},
            success:function(res)
            {
              if (1==res.error)
              {
                 window.location.href="/wx/login?returnTo=${param.reqUrl!''}";
              }
              else if(res.error==2)
              {
            	  warning(res.msg);
            	  setTimeout(function(){
            		  wait();
                	  },1000);
            	  setTimeout(function(){
            		  window.location.href="/wx/login?returnTo=${param.reqUrl!''}?type=collectType";
            	  },1200);
            	  
              }
              else
              {
                warning(res.msg);
                if(res.msg == "收藏成功")
                	{
                	$("#collectDivId").addClass("gsct_f2_choiced")
                	$("#collectId").attr("src","/wx/images/f22.png");
                	}
                else
                	{
                	$("#collectDivId").removeClass("gsct_f2_choiced")
                	$("#collectId").attr("src","/wx/images/f2.png");
                	}
              }
            }
        });
	});
	<#if param.type??>
	<#if param.type=="cartType">
	setTimeout($("#cartId").click(),1000);
	<#elseif param.type=="collectType">
	setTimeout($("#collectDivId").click(),1000);
	</#if>
	</#if>
	$("#navigationId a").click(function(){
		$(this).addClass("gsct_choiced");
		$(this).siblings().removeClass("gsct_choiced");
		swichType($(this).index());
	});
});
function swichType(type){
	if(type==0)
	{
		$("#goodsType").show();
		$("#detailType").hide();
		$("#commentType").hide();
	}
	else if(type==1)
		{
		$("#goodsType").hide();
		$("#detailType").show();
		$("#commentType").hide();
		}
	else if(type==2)
		{
		$("#goodsType").hide();
		$("#detailType").hide();
		$("#commentType").show();
		}
}
</script>
</head>
<body>
<div class="top_size"></div>
<div class="clear"></div>

<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">
<!-- header -->
<section class="white_header">
    <a href="javascript:history.go(-1)" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div id="navigationId" class="htitle header gsct">
        <a href="javascript:;" title="" class="gsct_choiced">商品</a>
        <a href="javascript:;" title="">详情</a>
        <a href="javascript:;" title="">评价</a>
    </div>
    <a title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<div id="goodsType" style="display:block">
<!-- swiper////////////////////////////////////////////////////////////////////// -->			
<div class="gsct_pic">
	<div class="swiper-container" style="width: 100%;height: 100%;">
	 	<div class="swiper-wrapper">
	 	<#if goods?? && goods.showPictures??>
            <#list goods.showPictures?split(",") as imgUri>
              <#if imgUri!="">
                <div class="swiper-slide blue-slide">
                  <a href="javascript:;">          
                    <img src="${imgUri!}"/>
                  </a>
                </div>
              </#if>
            </#list>
          </#if>
	  	</div>
	  	<div class="swiper-pagination"></div>
	</div>
	
	<script type="text/javascript">
	  var mySwiper = new Swiper('.gsct_pic .swiper-container',{
	    loop: true,
		autoplay: 3000,
		pagination : '.gsct_pic .swiper-pagination'
	  });	
	</script>
</div>		
<!-- swiper////////////////////////////////////////////////////////////////////// -->

<!-- gsct_info -->
<form id="formId" action="/wx/order" method="post">
<section class="gsct_info mt10">
	<input type="hidden" id="inventory${goods.id?c}" value="<#if limitNumber??>${limitNumber?c}<#else>${goods.leftNumber?c}</#if>">
	<input type="hidden" name="gid" value="${goods.id?c}">
	<input type="hidden" name="ckIds" value="0">
	<input type="hidden" name="type" value="${goods.type!'2'}">
	<input id="hiddenIdsId" type="hidden" name="ids" value="">
    <div class="gsct_info_title">${goods.title!''}</div>
    <div class="gsct_info_price"><b>${goods.salePrice?string("currency")}</b> <#-- <#if goods.marketPrice??><s>${goods.marketPrice?string("currency")}</s>  #{(goods.salePrice/goods.marketPrice * 10);m1M1}折</div></#if>-->
    <h style="float: right; color: #999;">已售出${goods.soldNumber!'0'}件</h>
    <!-- gsct_opt -->
    <div class="gsct_opt">
        <!-- 文字 -->
        <#--<div class="gsct_opt_words mt20">
            <span>已选</span><label>两盒疗程装 1件</label>
        </div>-->
        <!-- 文字 end-->
        <!-- 规格 -->
        <#--<div class="gsct_opt_norms mt20">
            <span>规格</span>
            <ul>
                <li>一盒疗程装</li>
                <li class="gsct_opt_norms_choiced">一盒疗程装</li>
                <li class="gsct_opt_norms_no">一盒疗程装</li>
                <li>一盒疗程装</li>
            </ul>
        </div>-->
        <!-- 规格 end-->
		<!-- 数量 -->
		<div class="gsct_opt_nums mt10">
			<span>数量</span>
			<label>
				<a onclick="changeQuantity(${goods.id?c},'sub')">-</a>
				<input type="tel" name="quantitys" onkeyup="onlyInt(this)" id="quantity${goods.id?c}" value="1" class="text">
				<a onclick="changeQuantity(${goods.id?c},'add')">+</a>
			</label>
		</div>
		<!-- 数量 end-->
		<!-- 文字 -->
		<div class="gsct_opt_words mt20">
		<#if limitNumber??><p>此商品限购${limitNumber?c}个</p></#if>
			<span>运费</span><label><#if goods.postFee?? && goods.postFee gt 0>${goods.postFee?string('0.00')}元<#else>免邮</#if></label>
		</div>
		<!-- 文字 end-->
    </div>
    <!-- gsct_opt end -->
</section>
</form>
<!-- gsct_info end -->

<!-- gsct_comment -->
<section class="gsct_comment mt20">
	<h3>商品评价</h3>
	<hr/>
	<#if comment_list?? && comment_list?size gt 0>
	<#list comment_list as comment>
	<dl>
		<dt>${comment.content!''}</dt>
		<dd>${comment.nickname!''} | </dd>
		<dd>${comment.initDate}</dd>
		<dd style="float: right;">
		<#list 1..5 as start>
		<img src="/wx/images/start<#if comment.stars lt start>_empty</#if>.png">
		</#list>
		</dd>
	</dl>
	</#list>
	<a href="#" title="">查看全部评价></a>
	<#else>
	<h6 style="al">暂无评论！</h6>
	</#if>
</section>
<!-- gsct_comment end -->

<!-- gsct_upglide 上滑加载 -->
<#--<section class="gsct_upglide">继续上滑，加载图文详情</section>-->
<!-- gsct_upglide 上滑加载 end-->

<div class="footer_size"></div>

<section class="footer">
	<div class="gsct_f_left">
		<div class="gsct_f">
			<dl>
				<dt><img src="/wx/images/f1.png"> </dt>
				<a href="http://wpa.qq.com/msgrd?V=3&uin=3205583032&site=qq&menu=yes"><dd>客服</dd></a>
			</dl>
		</div>
		<div id="collectDivId" class="gsct_f">
			<dl>
				<dt><img id="collectId" src="/wx/images/f2<#if collect?? && collect>2</#if>.png"> </dt>
				<dd>收藏</dd>
			</dl>
		</div>
		<!-- 选择收藏时用这个div
		<div class="gsct_f gsct_f2_choiced">
			<dl>
				<dt><img src="/wx/images/f22.png"> </dt>
				<dd>收藏</dd>
			</dl>
		</div>
		-->
		<a href="/wx/cart?ctype=<#if goods.type?? && goods.type==1>1<#else>2</#if>"><div class="gsct_f gsct_f1">
			<dl>
				<dt><img src="/wx/images/f3.png"> </dt>
				<dd><#if goods.type?? && goods.type==1>需求清单<#else>购物车</#if></dd>
			</dl>
		</div></a>

	</div>
	<label id="cartId" class="gsct_f4"><#if goods.type?? && goods.type==1>加入需求清单<#else>加入购物车</#if></label>
	<label id="buyNowId" class="gsct_f5"><#if goods.type?? && goods.type==1>直接提交需求<#else>立即购买</#if></label>
</section>
</div>
<div id="detailType" style="display:none">
<section class="gcc_box">
	${goods.contentDetail!''}
</section>
</div>
<div id="commentType" style="display:none">
<section class="gsct_comment mt20">
	<h3>商品评价</h3>
	<hr/>
	<#if comment_list?? && comment_list?size gt 0>
	<#list comment_list as comment>
	<dl>
		<dt>${comment.content!''}</dt>
		<dd>${comment.nickname!''} | </dd>
		<dd>${comment.initDate}</dd>
		<dd style="float: right;">
		<#list 1..5 as start>
		<img src="/wx/images/start<#if comment.stars lt start>_empty</#if>.png">
		</#list>
		</dd>
	</dl>
	</#list>
	<a href="#" title="">查看全部评价></a>
	<#else>
	<h6 style="al">暂无评论！</h6>
	</#if>
</section>
</div>

<!-- rightmore 右上角更多pop -->
<#include "/wx/rightmore.ftl">
<!-- rightmore end -->
</body>
</html>
