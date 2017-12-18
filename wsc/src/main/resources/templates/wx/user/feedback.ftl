<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>意见反馈</title>
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
    function check()
    {
    	var content = $("#contentId").val();
    	var mobile = $("#mobileId").val();
    	if(isNull(content))
    		{
    		warning("请输入反馈内容");
    			document.getElementById("contentId").focus()
    			return false;
    		}
    		
    		
    	if(!isNull(mobile))
    	{
    		if(!(/^1\d{10}$/.test(mobile)))
    		{
    			warning("请输入正确的手机号");
    			document.getElementById("mobileId").focus()
    			return false;
    		}
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
    <div class="htitle header">意见反馈</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="opinion_1 mt20">
    送货、退换货及咨询请联系<a href="#" title=""> 电话客服</a>
</section>

<form action="/wx/u/feedback/submit" method="post" onsubmit="return check();">
<section class="opinion_1 mt20">
    我们存在哪些不足
    <textarea name="content" id="contentId" placeholder="请您对我们服务不周处，留下您的宝贵意见，我们将竭诚为您服务"></textarea>
</section>

<section class="opinion_1 mt20">
    您的联系电话
    <input name="mobile" id="mobileId" placeholder="联系电话" type="tel">
</section>


<input type="submit"  class="footer_sub" value="提交">
</form>
</body>
</html>
