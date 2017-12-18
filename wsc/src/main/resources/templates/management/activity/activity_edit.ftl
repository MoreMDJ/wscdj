<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑信息</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" src="/management/js/WdatePicker.js"></script>
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript" src="/management/js/lhgdialog.js?skin=idialog"></script>
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
    
    
    // 添加组合商品
    $("#addGoods").click(function(){
        showDialogCombination();
    });
    
    //创建商品组合窗口
    function showDialogCombination(obj) {
        var objNum = arguments.length;
        
        var combinationDialog = $.dialog({
            id: 'combinationDialogId',
            lock: true,
            max: false,
            min: false,
            title: "商品组合",
            content: 'url:/management/activity/dialog/goods?statusId='  + $("#var_box_comb").children("tr").length,
            width: 800,
            height: 550
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            combinationDialog.data = obj;
        }
    }
    
    //删除商品组合节点
    function delCombinationNode(obj) {
        $(obj).parent().parent().remove();
    }
    //清空已选商品好赠品
    function removeGoodsAndGifts(){
    	//删除商品及赠品
    	$('tr[class=td_c]').remove();
    	//修改 商品总算
    	$('#totalComb').val('0');
    	//修改赠品总算
    	$('#totalGift').val('0');
    }
});



//创建赠品窗口
function show_goods_comb_dialog(obj) {
    var objNum = arguments.length;
    var zengpinDialog = $.dialog({
        id: 'zengpinhDialogId',
        lock: true,
        max: false,
        min: false,
        title: "活动赠品",
        content: 'url:/management/activity/dialog/goods?statusId='  + $("#var_box_comb").children("tr").length,
        width: 800,
        height: 550
    });
    //如果是修改状态，将对象传进去
    if (objNum == 1) {
        zengpinDialog.data = obj;
    }
}
    
//删除促销商品节点
function del_goods_gift(obj) {
    $(obj).parent().parent().remove();
    $("#totalGift").val(parseInt($("#totalGift").val())-1);
}

//删除商品组合节点
function del_goods_comb(obj) {
    $(obj).parent().parent().remove();
    $("#totalComb").val(parseInt($("#totalComb").val())-1);
}
//判断活动结束时间不能小于开始时间
function checkDate(){
	var beginDate= $('#beginDate').val();
	var finishDate=$('#finishDate').val();
	if(finishDate<=beginDate){
		alert("亲,活动结束时间不能小于开始时间");
		return false;
	}
	
}
  <#if fns??>
  	$(document).ready(function(){
  		alert("保存成功");
  		location.href='/Verwalter/activity/list';
  	});
  <#else>
  	console.log("aaa");	
  </#if>
</script>
</head>
<body class="mainbody">
<form method="post" action="/management/activity/save" id="form1" onsubmit="return checkDate();">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if activity??>${activity.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <#--<a href="/Verwalter/goods/list" class="back"><i></i><span>
        返回列表页</span></a> -->
    <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>活动管理</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>活动名称</dt>
            <dd>
                <#-- <input name="name" type="text" value="<#if activity??>${activity.name!""}</#if>" class="input normal" datatype="*" <#if !activity??> ajaxurl="/management/activity/checkname"</#if> sucmsg=" ">-->
                <input name="title" type="text" value="<#if activity??>${activity.title!""}</#if>" class="input normal" datatype="*" sucmsg=" ">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>活动图片</dt>
            <dd>
                <input id="txtImgUrl" name="imgUrl" type="text" value="<#if activity??>${activity.imgUrl!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div id="thumb_ImgUrl_show1" class="photo-list ">
                </div>
            </dd>
        </dl>
        <dl>
            <dt>活动开始时间</dt>
            <dd>
                <div class="input-date">
                    <input name="beginTime" id="beginDate" type="text" value="<#if activity??>${activity.beginTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>开始日期</i>
                	<span class="Validform_checktip"></span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>结束到期时间</dt>
            <dd>
                <div class="input-date">
                    <input name="endTime" id="finishDate" type="text" value="<#if activity??>${activity.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>结束日期</i>
                <span class="Validform_checktip"></span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>最多购买不同品类的量</dt>
            <dd>
                <input name="number" type="text" value="<#if activity??&&activity.number??>${activity.number!'0'}<#else>0</#if>" class="input normal" datatype="n" sucmsg=" " errormsg="请输入一个正确的整数">
                <i>0表示不限购买品类数量</i>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>活动商品</dt>
            <dd>
                <a id="addGoods" class="icon-btn add"><i></i><span>添加活动商品</span></a>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt></dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="10%">
                                商品ID
                            </th>
                            <th width="38%">
                                活动商品
                            </th>
                            <th width="10%">
                               数量
                            </th>
                            <th width="10%">
                                价格
                            </th>
                            <th width="6%">
                                操作
                            </th>
                        </tr>
                    </thead>
                    <tbody id="var_box_comb">
                        <input type="hidden" id="totalComb" name="totalGoods" value="<#if activity??>${activity.totalGoods!'0'}<#else>0</#if>" />
                        <#if activity?? && activity.limitGoods??>
                            <#list activity.limitGoods as comb>
                                <tr class="td_c">
                                    <td>
                                    <input name="limitGoods[${comb_index}].id" type="hidden" value="${comb.id?c}">
                                    <input name="limitGoods[${comb_index}].postFee" type="hidden" value="${comb.id?c}">
                                        <input name="limitGoods[${comb_index}].coverImageUri" type="hidden" value="${comb.coverImageUri!''}">
                                        <input name="limitGoods[${comb_index}].costPrice" type="hidden" value="${0.00!''}">
                                    <input type="text" id="id" name="limitGoods[${comb_index}].goodsId" class="td-input" value="<#if comb.goodsId??> ${comb.goodsId?c}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="title" name="limitGoods[${comb_index}].goodsTitle" class="td-input" value="${comb.goodsTitle!''}" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="number" name="limitGoods[${comb_index}].quantity" class="td-input" value="<#if comb.quantity??>${comb.quantity?c}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <input type="text" id="price" name="limitGoods[${comb_index}].salePrice" class="td-input" value="<#if comb.salePrice??>${comb.salePrice?string("0.00")}</#if>" style="width:90%;">
                                    </td>
                                    <td>
                                        <i class="icon"></i>
                                        <a title="编辑" class="img-btn edit operator" onclick="show_goods_comb_dialog(this);">编辑</a>
                                        <a title="删除" class="img-btn del operator" onclick="del_goods_comb(this);">删除</a>
                                    </td>
                                </tr>
                            </#list>
                        </#if>
                    </tbody>
                </table>
            </dd>
        </dl>
    </div>
    
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn" >
            <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>
</body>
</html>