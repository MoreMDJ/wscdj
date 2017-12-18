<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑内容</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/PCASClass.js"></script>
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();

    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '100%',
        height: '350px',
        filterMode: false, //默认不过滤HTML
        resizeType: 1,
        uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
        fileManagerJson: '/management/tools/upload?action=ManagerFile',
        allowFileManager: true
    });
    var editorMini = KindEditor.create('.editor-mini', {
        width: '100%',
        height: '250px',
        filterMode: false, //默认不过滤HTML
        resizeType: 1,
        uploadJson: '/management/tools/upload?action=EditorFile&IsWater=1',
        items: [
		'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
		'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
		'insertunorderedlist', '|', 'emoticons', 'image', 'link']
    });

    // blur事件
    $("input.upload-path").blur(function(){
      if (null != $(this).val())
      {
        var newLi = "";
        if (""!=$(this).val())
        { 
          newLi = $('<ul><li>'
                    + '<div class="img-box">'
                    + '<img src="' + $(this).val() + '" />'
                    + '</div>'
                    + '</li></ul>');
        }
       
                      
        $(this).siblings(".photo-list").html(newLi);
      }
    });
    
    // 触发blur事件
    $('input.upload-path').trigger("blur");
    
    //初始化上传控件
    $(".upload-img").InitUploader({ filesize: "10240", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "gif,jpg,png,bmp,rar,zip,doc,xls,txt" });
    $(".upload-video").InitUploader({ filesize: "102400", sendurl: "/management/tools/upload", swf: "/management/js/uploader.swf", filetypes: "flv,mp4,avi" });
    $(".upload-album").InitUploader({ btntext: "批量上传", multiple: true, water: true, thumbnail: true, filesize: "10240", sendurl: "/management/tools/upload", swf: "../../scripts/webuploader/uploader.swf" });
    
});
    
function showChargeDialog() {
    var winDialog = top.dialog({
            title: '提示',
            content: '请输入充值金额：<br /><input id="amount" type="text" value="" placeholder="充值金额" style="height: 26px; margin-top: 10px;"/>',
            okValue: '充值',
            ok: function () {
                var amount = $("#amount", window.parent.document).val();
                
                if (null == amount || "" == amount)
                {
                    alert("请输入充值金额");
                    return false;
                }
                
                var amountFloat = parseFloat(amount);
                
                if (amountFloat <= 0)
                {
                    alert("充值金额必须大于0");
                    return false;
                }
                
                window.location.href="/management/shop/charge?amount=" + amountFloat;
                
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
}

function showWithdrawDialog() {
    var winDialog = top.dialog({
            title: '提示',
            content: '请输入提现金额：<br /><input id="amount" type="text" value="" placeholder="提现金额" style="height: 26px; margin-top: 10px;"/>',
            okValue: '申请提现',
            ok: function () {
                var amount = $("#amount", window.parent.document).val();
                
                if (null == amount || "" == amount)
                {
                    alert("请输入提现金额");
                    return false;
                }
                
                var amountFloat = parseFloat(amount);
                
                if (amountFloat <= 0)
                {
                    alert("提现金额必须大于0");
                    return false;
                }
                
                if (amountFloat > <#if total_deposit??>${total_deposit?string("0.00")}<#else>0</#if>)
                {
                    alert("提现金额必须小于余额");
                    return false;
                }
                
                window.location.href="/management/shop/withdraw?amount=" + amountFloat;
                
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
}
</script>
<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>

<body class="mainbody">
<form method="post" action="/management/pay/shop/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="">
<input type="hidden" name="id" value="<#if shop??><#if !action?? || action!="Copy">${shop.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/pay/shop/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/shop/list"><span>内容管理</span></a>
  <i class="arrow"></i>
  <span>编辑内容</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap" style="height: 42px;">
  <div class="content-tab">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>基本信息</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">基本信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
    <dt>门店名称</dt>
    <dd>
      <input name="title" type="text" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> value="<#if shop??>${shop.title!''}</#if>" class="input normal" datatype="*2-8" sucmsg=" ">
      <span class="Validform_checktip">*标题最多8个字符</span>
    </dd>
  </dl>
  <dl>
    <dt>状态</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="statusId" value="0" <#if !shop?? || !shop.statusId?? || shop.statusId==0>checked="checked"</#if>><label>开启</label>
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="statusId" value="1" <#if shop?? && shop.statusId?? && shop.statusId==1>checked="checked"</#if>><label>关闭</label>
        </span>
      </div>
      <span class="Validform_checktip">*关闭后该账户不能登录</span>
    </dd>
  </dl>
  <dl class="serviceOnly">
    <dt>星级</dt>
    <dd>
      <div class="rule-multi-radio">
        <span style="display: none;">
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="stars" value="1" <#if !shop?? || !shop.stars?? || shop.stars==1>checked="checked"</#if>><label>一星</label>
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="stars" value="2" <#if shop?? && shop.stars?? && shop.stars==2>checked="checked"</#if>><label>两星</label>
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="stars" value="3" <#if shop?? && shop.stars?? && shop.stars==3>checked="checked"</#if>><label>三星</label>
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="stars" value="4" <#if shop?? && shop.stars?? && shop.stars==4>checked="checked"</#if>><label>四星</label>
          <input type="radio" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> name="stars" value="5" <#if shop?? && shop.stars?? && shop.stars==5>checked="checked"</#if>><label>五星</label>
        </span>
      </div>
      <span class="Validform_checktip">*仅服务商选择</span>
    </dd>
  </dl>
  <dl>
    <dt>省市区</dt>
    <dd>
      <div>
        <select id="s_province" name="province"></select>
        <select id="s_city" name="city" ></select>
        <select id="s_county" name="district"></select>
        <span class="Validform_checktip"></span>
      </div>
<script type="text/javascript">
	var mypcas = new PCAS("s_province,所属省份", "s_city,所属城市", "s_county,所属地区");
	<#if shop??>mypcas.SetValue('${shop.province!''}','${shop.city!''}','${shop.district!''}');</#if>
</script>
    </dd>
  <dl>
    <dt>地址</dt>
    <dd>
      <input name="address" type="text" value="<#if shop??>${shop.address!''}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
      <span class="Validform_checktip">请填写地址</span>
    </dd>
  </dl>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" <#if sessionShopId?? || sessionSupplierId??>disabled="disabled"</#if> value="<#if shop??>${shop.sortId!'99'}<#else>99</#if>" class="input small" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>客服电话</dt>
    <dd>
      <input name="serviceTele" type="text" value="<#if shop??>${shop.serviceTele!''}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>营业时间</dt>
    <dd>
      <input name="openTimeSpan" type="text" value="<#if shop??>${shop.openTimeSpan!''}</#if>" class="input normal" datatype="*0-100" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: static;">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>

</body></html>