<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>编辑内容</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<link href="/management/js/skin/WdatePicker.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<link href="/management/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">

<link href="/management/js/themes/default/default.css" rel="stylesheet"></head>
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/WdatePicker.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/webuploader.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/uploader.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/bootstrap-select.min.js"></script>
<script type="text/javascript">
        
KindEditor.ready(function(K) {
	K.create('#txtContentDetail', {
		width: '100%',
        height: '350px',
		uploadJson : '/management/tools/upload?action=EditorFile&IsWater=1', 
        fileManagerJson : '/management/tools/upload?action=ManagerFile',
		allowFileManager : true
	});
});
KindEditor.ready(function(K) {
	K.create('#txtServeDetail', {
		width: '100%',
        height: '350px',
		uploadJson : '/management/tools/upload?action=EditorFile&IsWater=1', 
        fileManagerJson : '/management/tools/upload?action=ManagerFile',
		allowFileManager : true
	});
});
KindEditor.ready(function(K) {
	K.create('#txtPackDetail', {
		width: '100%',
        height: '350px',
		uploadJson : '/management/tools/upload?action=EditorFile&IsWater=1', 
        fileManagerJson : '/management/tools/upload?action=ManagerFile',
		allowFileManager: true
	});
});
        
        
$(function () {
	$('.selectpicker').selectpicker({
	  style: 'btn-info',
	  size: 5
	});
    //初始化表单验证
    $("#form1").initValidform();

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
    $(".upload-album").InitUploader({ btntext: "批量上传", multiple: true, water: true, thumbnail: false, filesize: "10240", sendurl: "/management/tools/upload", swf: "../../scripts/webuploader/uploader.swf" });

    // 根据类型载入参数
    $("#categoryId").change(function(){
        $.ajax({
            url : '/management/goods/parameter/'+$(this).val(),
            type : 'POST',
            success : function(res) {
                $("#id-param-sec").html(res);
            }
        });
    });
    
    var typeId=${typeId!0};
    
    $("input[name=typeId]").click(function(){
        if (typeId!=$(this).val())
        {
            typeId=$(this).val();
            
            $.ajax({
                url : '/management/goods/categoryGoods/type/'+$(this).val() <#if goods??>+'?goodsId='+${goods.id?c}</#if>,
                type : 'GET',
                success : function(data) {
                    $("#categoryDiv").html(data);
                }
            });
        }
    });
    
    //类别选择
    //添加按钮(点击绑定)
    $("#itemAddButton").click(function () {
        showSpecDialog();
    });
});
//创建窗口
function showSpecDialog(obj) {
    var objNum = arguments.length;
    var d = top.dialog({
        width: 500,
        height: 180,
        title: '商品类别',
        url: '/management/goods/dialog',
        onclose: function () {
            var trHtml = this.returnValue;
            if (trHtml.length > 0) {
                $("#item_box").append(trHtml);
            }
        }
    }).showModal();
    //检查是否修改状态
    if (objNum == 1) {
        d.data = obj;
    }
}
//删除节点
function delItemTr(obj) {
    $(obj).parent().parent().remove();
}

</script>

<body class="mainbody">
<form method="post" action="/management/goods/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="<#if param??>${param.VIEWSTATE!''}</#if>">
<input type="hidden" name="id" value="<#if goods??&& param??><#if !param.EVENTTARGET?? || param.EVENTTARGET!="Copy">${goods.id?c}</#if></#if>">
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/goods/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center.aspx" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/goods/list"><span>内容管理</span></a>
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
        <li><a href="javascript:;" class="">商品信息</a></li>
        <li><a href="javascript:;" class="">详细描述</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content" style="display: block;">
  <dl>
  <dt>所属类别</dt>
	  <dd>
	      <select id="categoryId" name="categoryId" datatype="*" sucmsg=" " nullmsg="请选择！" data-live-search="true" class="selectpicker Validform_error" style="">
	        <option value="">请选择类别...</option>
	        <#if category_list??>
	          <#list category_list as item>
	            <option value="${item.id?c}" <#if goods?? && goods.categoryId?? && goods.categoryId==item.id>selected="selected"</#if>><#if item.layerId!=1><#list 2..item.layerId as idx>　</#list>├ </#if>${item.title!''}</option>
	          </#list>
	        </#if>
	      </select>
	  </dd>
  </dl>
  <#-- <dl>
    <dt>类别选项</dt>
    <dd><a id="itemAddButton" class="icon-btn add"><i></i><span>添加选项</span></a></dd>
  </dl> 
  <dl>
    <dt></dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table">
          <thead>
              <tr>
                <th width="12%">文字</th>
                <th width="16%">图片</th>
                <th width="12%">排序</th>
                <th width="10%">操作</th>
              </tr>
            </thead>
            <tbody id="item_box">
              
              <tr class="td_c">
                <td>
                  <input type="hidden" name="item_id" value="9">
                  <input type="hidden" name="item_title" value="白色">
                  <span class="item_title">白色</span>
                </td>
                <td>
                  <input type="hidden" name="item_imgurl" value="/upload/201503/25/201503251553231051.jpg">
                  <span class="item_imgurl img-box"><img src="/upload/201503/25/201503251553231051.jpg"></span>
                </td>
                <td>
                  <input type="hidden" name="item_sortid" value="99">
                  <span class="item_sortid">99</span>
                </td>
                <td>
                  <a title="编辑" class="img-btn edit operator" onclick="showSpecDialog(this);">编辑</a>
                  <a title="删除" class="img-btn del operator" onclick="delItemTr(this);">删除</a>
                </td>
              </tr>
              
             
              
            </tbody>
        </table>
      </div>
    </dd>
  </dl>-->
  <dl>
      <dt>显示状态</dt>
      <dd>
          <div class="rule-multi-radio multi-radio">
              <span>
                  <input type="radio" name="status" value="1" <#if goods?? && goods.status?? && goods.status==1>checked="checked"</#if>>
                  <label>上架</label>
                  <input type="radio" name="status" value="2" <#if !goods?? ||(goods.status?? && goods.status==2 )>checked="checked"</#if>>
                  <label>待审核</label>
                  <input type="radio" name="status" value="3" <#if goods?? && goods.status?? && goods.status==3>checked="checked"</#if>>
                  <label>下架</label>
              </span>
          </div>
      </dd>
  </dl>
  <dl>
      <dt>药品类型</dt>
      <dd>
          <div class="rule-multi-radio multi-radio">
              <span>
                  <input type="radio" name="type" value="1" <#if goods?? && goods.type?? && goods.type==1>checked="checked"</#if>>
                  <label>处方药</label>
                  <input type="radio" name="type" value="2" <#if !goods?? || !goods.type?? || goods.type==2>checked="checked"</#if>>
                  <label>非处方药</label>
              </span>
          </div>
      </dd>
  </dl>
  <dl>
    <dt>销售方式</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span style="display: none;">
          <input type="radio" name="saleType" value="0" <#if !goods?? || !goods.saleType?? || goods.saleType==0>checked="checked"</#if>><label>正常销售</label>
          <input type="radio" name="saleType" value="1" <#if goods?? && goods.saleType?? && goods.saleType==1>checked="checked"</#if>><label>秒杀</label>
          <input type="radio" name="saleType" value="2" <#if goods?? && goods.saleType?? && goods.saleType==2>checked="checked"</#if>><label>好货推荐</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>商品名称</dt>
    <dd>
      <input name="title" type="text" value="<#if goods??>${goods.title!''}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
      <span class="Validform_checktip">*标题最多100个字符</span>
    </dd>
  </dl>
  <dl id="div_sub_title">
    <dt><span>副标题</span></dt>
    <dd>
      <input name="subTitle" type="text" value="<#if goods??>${goods.subTitle!''}</#if>" class="input normal" datatype="*0-255" sucmsg=" ">
      <span id="div_sub_title_tip" class="Validform_checktip">可为空，最多255个字符</span>
    </dd>
  </dl>
  <dl id="div_stock_quantity">
    <dt><span>库存数量</span></dt>
    <dd>
      <input name="leftNumber" type="text" class="input small" value="<#if goods?? && goods.leftNumber??>${goods.leftNumber!''}<#else>0</#if>" datatype="n" sucmsg=" ">
      <span id="div_stock_quantity_tip" class="Validform_checktip">库存数量为0时显示缺货状态</span>
    </dd>
  </dl>
  <dl>
    <dt><span>市场价</span></dt>
    <dd>
      <input name="marketPrice" type="text" value="<#if goods?? && goods.marketPrice??>${goods.marketPrice?string("0.00")}</#if>" class="input small" datatype="/^\s*$|(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 元
      <span class="Validform_checktip">市场的参考价格，不参与计算</span>
    </dd>
  </dl>
  <dl>
    <dt><span>销售价</span></dt>
    <dd>
      <input name="salePrice" type="text" value="<#if goods?? && goods.salePrice??>${goods.salePrice?string("0.00")}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 元
      <span class="Validform_checktip">*正常销售的交易价格</span>
    </dd>
  </dl>
  <dl>
    <dt><span>活动价</span></dt>
    <dd>
      <input name="specialPrice" type="text" value="<#if goods?? && goods.specialPrice??>${goods.specialPrice?string("0.00")}</#if>" class="input small" datatype="/^\s*$|(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 元
      <span class="Validform_checktip">*活动期间的交易价格</span>
    </dd>
  </dl>
  <dl>
    <dt><span>成本价</span></dt>
    <dd>
      <input name="costPrice" type="text" value="<#if goods?? && goods.costPrice??>${goods.costPrice?string("0.00")}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 元
      <span class="Validform_checktip">*商品成本价格</span>
    </dd>
  </dl>
  <dl>
    <dt><span>分销提成比例</span></dt>
    <dd>
      <input name="costRate" type="text" value="<#if goods?? && goods.costRate??>${goods.costRate}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*百分比 输入 整数</span>
    </dd>
  </dl>
  <dl>
    <dt><span>邮费</span></dt>
    <dd>
      <input name="postFee" type="text" value="<#if goods?? && goods.postFee??>${goods.postFee?string("0.00")}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" "> 元
      <span class="Validform_checktip">*商品成本价格</span>
    </dd>
  </dl>
  <dl>
    <dt>关键字</dt>
    <dd>
      <input name="tags" type="text" value="<#if goods??>${goods.tags!''}</#if>" class="input normal" datatype="*0-500" sucmsg=" ">
      <span class="Validform_checktip">多个可用英文逗号分隔开，如：a,b</span>
    </dd>
  </dl>
  <dl>
    <dt>浏览次数</dt>
    <dd>
      <input name="viewCount" type="text" value="<#if goods??>${goods.viewCount!'0'}<#else>0</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">点击浏览该信息自动+1</span>
    </dd>
  </dl>
  <dl>
    <dt>销售数量</dt>
    <dd>
      <input name="soldNumber" type="text" value="<#if goods??>${goods.soldNumber!'0'}<#else>0</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">销售数量</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if goods??>${goods.sortId!'99'}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>发布时间</dt>
    <dd>
      <input name="startTime" value="<#if goods?? && goods.startTime??>${goods.startTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" type="text" class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
      <span class="Validform_checktip">活动开始时间(仅在销售方式非正常销售下有效)</span>
    </dd>
  </dl>
 <dl>
    <dt>发布时间</dt>
    <dd>
      <input name="endTime" value="<#if goods?? && goods.endTime??>${goods.endTime?string("yyyy-MM-dd HH:mm:ss")}</#if>" type="text" class="input rule-date-input" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
      <span class="Validform_checktip">活动结束时间(仅在销售方式非正常销售下有效)</span>
    </dd>
  </dl>  
  <div id="id-param-sec">
      <#if goods??>
          <#include "/management/goods/goods_param_list.ftl" />
      </#if>
  </div>
</div>

<div class="tab-content" style="display: none;">
   <dl>
    <dt><span>商品货号</span></dt>
    <dd>
      <input name="sku" type="text" value="<#if goods??>${goods.sku!''}</#if>" ajaxurl="/management/tools/check?action=goodsSku_conform<#if goods??&&goods.sku??>&param1=${goods.sku!''}</#if>" class="input normal" datatype="*0-50" sucmsg=" ">
      <span class="Validform_checktip">可为空，最多50个字符</span>
    </dd>
  </dl>
  <dl>
    <dt><span>商品规格</span></dt>
    <dd>
      <input name="specification" type="text" value="<#if goods??>${goods.specification!''}</#if>"  class="input normal" datatype="*0-40" sucmsg=" ">
      <span class="Validform_checktip">可为空，最多40个字符</span>
    </dd>
  </dl>
  <dl>
    <dt><span>商品厂商</span></dt>
    <dd>
      <input name="manufacturer" type="text" value="<#if goods??>${goods.manufacturer!''}</#if>" class="input normal" datatype="*0-90" sucmsg=" ">
      <span class="Validform_checktip">可为空，最多90个字符</span>
    </dd>
  </dl>
  <dl>
    <dt><span>商品批准文号</span></dt>
    <dd>
      <input name="approvalNumber" type="text" value="<#if goods??>${goods.approvalNumber!''}</#if>" class="input normal" datatype="*0-160" sucmsg=" ">
      <span class="Validform_checktip">可为空，最多160个字符</span>
    </dd>
  </dl>
</div>

<div class="tab-content" style="display: none;">
  <dl>
    <dt>封面图片</dt>
    <dd>
      <input name="imgUrl" type="text" value="<#if goods??>${goods.imgUrl!''}</#if>" class="input normal upload-path">
      <div class="upload-box upload-img"></div>
      <div class="photo-list"></div>
    </dd>
  </dl>
  <dl>
    <dt>图片相册</dt>
    <dd>
      <div class="upload-box upload-album"></div>
      <div class="photo-list">
        <ul>
          <#if goods?? && goods.showPictures??>
            <#list goods.showPictures?split(",") as uri>
              <#if "" != uri>
                <li>
                  <input type="hidden" name="showPictures" value="${uri!}">
                  <div class="img-box" onclick="setFocusImg(this);">
                    <img src="${uri!}">
                  </div>
                  <a href="javascript:;" onclick="delImg(this);">删除</a>
                </li>
              </#if>
            </#list>
          </#if>
        </ul>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>图文详情</dt>
    <dd>
      <div class="ke-container ke-container-default" style="width: 100%;">
        <textarea name="contentDetail" id="txtContentDetail" class="editor"><#if goods??>${goods.contentDetail!''}</#if></textarea>
    </dd>
  </dl>
  <dl>
    <dt>包装与配送</dt>
    <dd>
      <div class="ke-container ke-container-default" style="width: 100%;">
        <textarea name="serveDetail" id="txtServeDetail" class="editor"><#if goods??>${goods.serveDetail!''}</#if></textarea>
    </dd>
  </dl>
  <dl>
    <dt>售后服务</dt>
    <dd>
      <div class="ke-container ke-container-default" style="width: 100%;">
        <textarea name="packDetail" id="txtPackDetail" class="editor"><#if goods??>${goods.packDetail!''}</#if></textarea>
    </dd>
  </dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: static;">
    <#if !sessionShopId?? && !sessionSupplierId??>
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    </#if>
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>

</body></html>