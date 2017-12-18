<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>浏览记录</title>
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
</head>
<script>
function scanDel(gid,obj)
{
	$.ajax({
        url:"/wx/u/scan/del",
        data: {msgId:gid},
        success:function(res){
          if (0==res.error)
          {
            warning(res.msg);
            obj.parentNode.remove();
          }
          else if(res.error == 2)
          {
        	  
        	  warning(res.msg);
        	  setTimeout(function(){
        		  wait();
            	  },1000);
        	  setTimeout(function(){
        	  window.location.href="/wx/login?returnTo=/wx/u";
        	  },1200);
          }
          else(res.error == 1)
          {
        	  warning(res.msg);
          }
        }
    });
}
function left_del1(val){
	var delh=getClassName(document,val);
	for(var i=0;i<delh.length;i++){
		delgo(delh[i]);
	}
	function delgo(obj){
		var childTag=obj.children[0].tagName;
		var oLi=obj.getElementsByTagName(childTag);
		var left0;
		for(var j=0;j<oLi.length;j++){
			var delClick=oLi[j].getElementsByTagName('menu')[0];
			var bW=delClick.offsetWidth;
			if(delClick){
				oLi[j].addEventListener('touchstart',function(event){
					this.style.transition='1s';
					var touch=event.changedTouches[0];
					left0=touch.pageX;
				},false)
				oLi[j].addEventListener('touchmove',function(event){
					var touch=event.changedTouches[0];
					nowleft=touch.pageX;
					if(nowleft-left0<-30){
						this.style.transform='translateX('+(-bW)+'px)';
					}
					if(nowleft-left0>30){
						this.style.transform='translateX('+0+'px)';
					}
				},false)
				delClick.onclick=function(){
					var gid = $(this).attr("value");
					scanDel(gid,this);
				}
			}
		}
	}
}
    setRem();
    window.onload=function(){
        left_del1('left_del_box');
    }
</script>

<body>
<div class="top_size"></div>
<div class="clear"></div>
<#-- 引入警告提示样式 -->
<#include "/common/common_warn.ftl">
<#-- 引入等待提示样式 -->
<#include "/common/common_wait.ftl">

<!-- header -->
<section class="white_header">
    <a href="/wx/u" title="" class="hleft headleft header"><img src="/wx/images/leftback.png"> </a>
    <div class="htitle header">浏览记录</div>
    <a href="#" title="" class="hright headright header"><img src="/wx/images/rightmore.png"></a>
</section>
<!-- header end -->

<section class="collection_box">
    <ul class="left_del_box" style="width:100%;overflow: hidden;">
    <#if goods??>
    <#list goods as goods>
    <li>
        <a href="javascript:;">
            <img src="${goods.imgUrl!''}" alt="" class="fl" />
            <section class="sec1 fl">
                <p class="p1">${goods.title!''}</p>
                <p class="p2">
                    <span class="colred">${goods.salePrice?string('currency') }</span>
                    <#if goods.marketPrice??><label class="col99918">${goods.marketPrice?string('currency')}</label></#if>
                </p>
            </section>
        </a>
        <menu value="${goods.id?c }">删除</menu>
    </li>
    </#list>
    </#if>
</ul>
    <script>
        $(function(){
            var oWidth=$(document).width();

            var iw=$('.left_del_box menu:eq(0)').outerWidth();
            $('.left_del_box a').css('width',oWidth);
            $('.left_del_box li').css('width',oWidth+iw+1);
        })
    </script>
</section>

</body>
</html>
