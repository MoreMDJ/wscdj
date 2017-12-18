<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>个人中心</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" >
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<!-- css -->
<link href="/wx/css/common.css" rel="stylesheet" type="text/css" />
<link href="/wx/css/center.css" rel="stylesheet" type="text/css" />
    <!-- js -->
    <script type="text/javascript" src="/wx/js/jquery-2.1.0.min.js"></script>
    <script type="text/javascript" src="/wx/js/common.js"></script>
    <script type="text/javascript">
    $(function(){
    	$("#signId").click(function(){
    		$.ajax({
                data:$('#form1').serialize(),
                url : '/wx/u/point/add',
                method : 'POST',
                success: function(res)
                {
                        warning(res.msg);
                        if(res.status==0)
                        	$("#signId").html("已签到");
                }
            });
    	});
    });
    </script>
</head>
<body>

<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!--center_top-->
<section class="center_top wrap100">
    <div class="center_top_news"><a href="/wx/msg"></a><#if msg?? && msg gt 0><i></i></#if></div>
    <div class="center_top_box">
        <dl>
            <dt><img src="${user.headImage!''}" alt="" style="height: 100%;width: 100%;"></dt>
            <dd>
                <p>${user.nickname!'快去设置自己的昵称吧'}</p>
                <span <#if isSignPoint?? && isSignPoint><#else>id="signId"</#if> ><#if isSignPoint?? && isSignPoint>您已签到<#else>签到领积分</#if></span>
            </dd>
            <dd class="center_top_box_dd2"><a href="/wx/u/profile" title="">账户管理></a> </dd>
        </dl>
    </div>
</section>
<!--center_top end-->

<!--center_order-->
<section class="center_order wrap100">
    <div class="center_order1">
        <a href="/wx/u/order/2" title=""> <dl>
            <dt><img src="/wx/images/order1.png"></dt>
            <dd>待付款</dd>
            <dd class="center_order1_hint"></dd>
        </dl></a>
        <a href="/wx/u/order/4" title=""> <dl>
            <dt><img src="/wx/images/order2.png"></dt>
            <dd>待收货</dd>
        </dl></a>
            <a href="#" title=""> <dl>
            <dt><img src="/wx/images/order3.png"></dt>
            <dd>退换货</dd>
            </dl></a>
        <span></span>
            <a href="/wx/u/order/1" title=""> <dl>
            <dt><img src="/wx/images/order4.png"></dt>
            <dd>全部订单</dd>
            </dl></a>
    </div>
</section>
<!--center_order end-->

<!--center_order2-->
<section class="center_order wrap100 mt20">
    <div class="center_order1">
        <a href="/wx/doctor/cate" title=""> <dl>
            <dt><img src="/wx/images/2doctor.png"></dt>
            <dd>我的医生</dd>
        </dl></a>
        <a href="#" title=""> <dl>
            <dt><img src="/wx/images/2pill.png"></dt>
            <dd>我的药方</dd>
        </dl></a>
        <a href="/wx/health/u" title=""> <dl>
            <dt><img src="/wx/images/2health.png"></dt>
            <dd>我的健康</dd>
        </dl></a>
        <span></span>
        <a href="/wx/u/distribution" title=""> <dl>
            <dt><img src="/wx/images/2purse.png"></dt>
            <dd>我的分销</dd>
        </dl></a>
    </div>
</section>
<!--center_order2 end-->

<!--center_order3-->
<section class="center_order wrap100 mt20">
    <div class="center_order1 center_order3">
        <a href="#" title=""> <dl class="center_order3_dl3">
            <dt>${commentNo!'0'}</dt>
            <dd class="center_order3_dd3">商品评价</dd>
        </dl></a>
        <a href="/wx/u/collect" title=""><dl class="center_order3_dl3">
            <dt>${collectNo!'0'}</dt>
            <dd class="center_order3_dd3">商品关注</dd>
        </dl></a>
        <a href="/wx/u/scan" title=""><dl class="center_order3_dl3">
            <dt>${scanNo!'0'}</dt>
            <dd class="center_order3_dd3">浏览记录</dd>
        </dl></a>
        <span></span>
        <a href="#" title=""> <dl>
            <dt><img src="/wx/images/3service.png"></dt>
            <dd>在线客服</dd>
        </dl></a>
    </div>
</section>
<!--center_order3 end-->

<!--center_order4-->
<section class="center_order wrap100">
    <div class="center_order1 center_order4">
        <a href="/wx/u/profile" title=""> <dl>
            <dt><img src="/wx/images/4datum.png"></dt>
            <dd>基本资料</dd>
        </dl></a>
        <a href="/wx/u/address" title=""> <dl>
            <dt><img src="/wx/images/4address.png"></dt>
            <dd>地址管理</dd>
        </dl></a>
        <a href="#" title=""> <dl>
            <dt><img src="/wx/images/4help.png"></dt>
            <dd>使用帮助</dd>
        </dl></a>
        <span></span>
        <a href="/wx/u/feedback" title=""> <dl>
            <dt><img src="/wx/images/4suggestion.png"></dt>
            <dd>意见反馈</dd>
        </dl></a>
    </div>
</section>
<!--center_order4 end-->

<section class="center_ad mt20">
    <#if ads??><img src="${ads.imgUri!''}"></#if>
</section>

<!--center_esc-->
<section class="center_esc mt20">
   <a href="/wx/login/out">退出登录</a> 
</section>
<!--center_esc end-->

<div class="footer_size"></div>

<!-- footer -->
<section class="footer">
    <nav>
        <a href="/wx" title="">
			<span>
				<img alt="" src="/wx/images/footer_icon01.png"/>
				<img alt="" src="/wx/images/footer_icon11.png"/>
			</span>
            <label>首页</label>
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
				<img class="active_img" alt="" src="/wx/images/footer_icon05.png"/>
				<img class="disable_img" alt="" src="/wx/images/footer_icon55.png"/>
			</span>
            <label class="active_label">我的</label>
        </a>
    </nav>
</section>
<!-- footer end -->
</body>
</html>
