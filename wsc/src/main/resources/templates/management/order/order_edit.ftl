<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,initial-scale=1.0,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<title>查看订单</title>
<link href="/management/css/ui-dialog.css" rel="stylesheet" type="text/css">
<link href="/management/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/management/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/dialog-plus-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/laymain.js"></script>
<script type="text/javascript" charset="utf-8" src="/management/js/common.js"></script>
<script type="text/javascript">
    $(function () {
        $("#btnConfirm").click(function () { OrderConfirm(); });   //确认订单
        $("#btnPayment").click(function () { OrderPayment(); });   //确认付款
        $("#btnExpress").click(function () { OrderExpress(); });   //确认发货
        $("#btnReceive").click(function () { OrderReceive(); });   //确认收货
        $("#btnComplete").click(function () { OrderComplete(); }); //完成订单
        $("#btnCancel").click(function () { OrderCancel(); });     //取消订单
        $("#btnInvalid").click(function () { OrderInvalid(); });   //作废订单
        $("#btnPrint").click(function () { OrderPrint(); });       //打印订单

        $("#btnEditAcceptInfo").click(function () { EditAcceptInfo(); }); //修改收货信息
        $("#btnEditRemark").click(function () { EditOrderRemark(); });    //修改订单备注
        $("#btnEditRealAmount").click(function () { EditRealAmount(); }); //修改商品总金额
        $("#btnEditExpressFee").click(function () { EditExpressFee(); }); //修改配送费用
        $("#btnEditPaymentFee").click(function () { EditPaymentFee(); }); //修改支付手续费
        $("#btnEditInvoiceTaxes").click(function () { EditInvoiceTaxes(); }); //修改发票税金
    });
    //确认订单
    function OrderConfirm() {
        var winDialog = top.dialog({
            title: '提示',
            content: '确认订单后将无法修改金额，确认要继续吗？',
            okValue: '确定',
            ok: function () {
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderConfirm" };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "/management/order/update");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //确认付款
    function OrderPayment() {
        var winDialog = top.dialog({
            title: '提示',
            content: '操作提示信息：<br />1、该订单使用在线支付方式，付款成功后自动确认；<br />2、如客户确实已打款而没有自动确认可使用该功能；<br />3、确认付款后无法修改金额，确认要继续吗？',
            okValue: '确定',
            ok: function () {
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderPayment" };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "/management/order/update");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //确认收货
    function OrderReceive() {
        var winDialog = top.dialog({
            title: '提示',
            content: '操作提示信息：<br />确认收货由用户完成，用户不确认收货时可使用该功能，确认要继续吗？',
            okValue: '确定',
            ok: function () {
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderReceive" };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "/management/order/update");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //确认发货
    function OrderExpress() {
        var winDialog = top.dialog({
            title: '提示',
            url: '/management/order/edit/express?orderNumber=' + $("#spanOrderNo").text(),
            width: 450,
            data: window //传入当前窗口
        }).showModal();
    }
    //完成订单
    function OrderComplete() {
        var winDialog = top.dialog({
            title: '完成订单',
            content: '订单完成后，订单处理完毕，确认要继续吗？',
            button: [{
                value: '确定',
                callback: function () {
                    var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderComplete" };
                    //发送AJAX请求
                    sendAjaxUrl(winDialog, postData, "/management/order/update");
                    return false;
                },
                autofocus: true
            }, {
                value: '取消',
                callback: function () { }
            }]
        }).showModal();
    }
    //取消订单
    function OrderCancel() {
        var winDialog = top.dialog({
            title: '取消订单',
            content: '操作提示信息：<br />1、仅可取消未付款订单；<br />3、请单击相应按钮继续下一步操作！',
            button: [{
                value: '确认取消',
                callback: function () {
                    var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderCancel", "checkRevert": 1 };
                    //发送AJAX请求
                    sendAjaxUrl(winDialog, postData, "/management/order/update");
                    return false;
                },
                autofocus: true
            }, <#--{
                value: '直接取消',
                callback: function () {
                    var postData = { "orderNo": $("#spanOrderNo").text(), "editType": "cancel", "checkRevert": 0 };
                    //发送AJAX请求
                    sendAjaxUrl(winDialog, postData, "/management/order/update?action=editStatusId");
                    return false;
                }
            }, -->{
                value: '关闭',
                callback: function () { }
            }]
        }).showModal();
    }
    //作废订单
    function OrderInvalid() {
        var winDialog = top.dialog({
            title: '取消订单',
            content: '操作提示信息：<br />1、匿名用户，请线下与客户沟通；<br />2、会员用户，自动检测退还金额或积分到账户；<br />3、请单击相应按钮继续下一步操作！',
            button: [{
                value: '检测退还',
                callback: function () {
                    var postData = { "orderNo": $("#spanOrderNo").text(), "action": "order_invalid", "check_revert": 1 };
                    //发送AJAX请求
                    sendAjaxUrl(winDialog, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
                    return false;
                },
                autofocus: true
            }, {
                value: '直接作废',
                callback: function () {
                    var postData = { "orderNo": $("#spanOrderNo").text(), "action": "order_invalid", "check_revert": 0 };
                    //发送AJAX请求
                    sendAjaxUrl(winDialog, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
                    return false;
                }
            }, {
                value: '关闭',
                callback: function () { }
            }]
        }).showModal();
    }
    //打印订单
    function OrderPrint() {
        var winDialog = top.dialog({
            title: '打印订单',
            url: '/management/dialog/order/print?orderNumber=' + $("#spanOrderNo").text(),
            width: 850
        }).showModal();
    }
    //修改收货信息
    function EditAcceptInfo() {
        var winDialog = top.dialog({
            title: '修改收货信息',
            url: '/management/order/edit/uInfo?orderNo=' + $("#spanOrderNo").text(),
            width: 550,
            height: 320,
            data: window //传入当前窗口
        }).showModal();
    }
    //修改订单备注
    function EditOrderRemark() {
        var winDialog = top.dialog({
            title: '订单备注',
            content: '<textarea id="txtOrderRemark" name="txtOrderRemark" rows="2" cols="20" class="input">' + $("#divRemark").html() + '</textarea>',
            okValue: '确定',
            ok: function () {
                var remark = $("#txtOrderRemark", parent.document).val();
                if (remark == "") {
                    top.dialog({
                        title: '提示',
                        content: '对不起，请输入订单备注内容！',
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winDialog);
                    return false;
                }
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderRemark", "remark": remark };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "/management/order/update");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }

    //修改商品总金额
    function EditRealAmount() {
        var winDialog = top.dialog({
            title: '请修改商品总金额',
            content: '<input id="txtDialogAmount" type="text" value="' + $("#spanRealAmountValue").text() + '" class="input" />',
            okValue: '确定',
            ok: function () {
                var amount = $("#txtDialogAmount", parent.document).val();
                if (!checkIsMoney(amount)) {
                    top.dialog({
                        title: '提示',
                        content: '对不起，请输入正确的商品金额！',
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winDialog);
                    return false;
                }
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "edit_real_amount", "real_amount": amount };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //修改配送费用
    function EditExpressFee() {
        var winDialog = top.dialog({
            title: '请修改配送费用',
            content: '<input id="txtDialogAmount" type="text" value="' + $("#spanExpressFeeValue").text() + '" class="input" />',
            okValue: '确定',
            ok: function () {
                var amount = $("#txtDialogAmount", parent.document).val();
                if (!checkIsMoney(amount)) {
                    top.dialog({
                        title: '提示',
                        content: '对不起，请输入正确的配送金额！',
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winDialog);
                    return false;
                }
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "orderExpressFee", "express": amount };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "/management/order/update");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //修改手续费用
    function EditPaymentFee() {
        var winDialog = top.dialog({
            title: '请修改支付手续费用',
            content: '<input id="txtDialogAmount" type="text" value="' + $("#spanPaymentFeeValue").text() + '" class="input" />',
            okValue: '确定',
            ok: function () {
                var amount = $("#txtDialogAmount", parent.document).val();
                if (!checkIsMoney(amount)) {
                    top.dialog({
                        title: '提示',
                        content: '对不起，请输入正确的手续费用！',
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winDialog);
                    return false;
                }
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "edit_payment_fee", "payment_fee": amount };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }
    //修改税金费用
    function EditInvoiceTaxes() {
        var winDialog = top.dialog({
            title: '请修改发票税金费用',
            content: '<input id="txtDialogAmount" type="text" value="' + $("#spanInvoiceTaxesValue").text() + '" class="input" />',
            okValue: '确定',
            ok: function () {
                var amount = $("#txtDialogAmount", parent.document).val();
                if (!checkIsMoney(amount)) {
                    top.dialog({
                        title: '提示',
                        content: '对不起，请输入正确的税金费用！',
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winDialog);
                    return false;
                }
                var postData = { "orderNo": $("#spanOrderNo").text(), "action": "edit_invoice_taxes", "invoice_taxes": amount };
                //发送AJAX请求
                sendAjaxUrl(winDialog, postData, "../../tools/admin_ajax.ashx?action=edit_order_status");
                return false;
            },
            cancelValue: '取消',
            cancel: function () { }
        }).showModal();
    }

    //=================================工具类的JS函数====================================
    //检查是否货币格式
    function checkIsMoney(val) {
        var regtxt = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
        if (!regtxt.test(val)) {
            return false;
        }
        return true;
    }
    //发送AJAX请求
    function sendAjaxUrl(winObj, postData, sendUrl) {
        $.ajax({
            type: "post",
            url: sendUrl,
            data: postData,
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                top.dialog({
                    title: '提示',
                    content: '尝试发送失败，错误信息：' + errorThrown,
                    okValue: '确定',
                    ok: function () { }
                }).showModal(winObj);
            },
            success: function (data, textStatus) {
                if (data.status == 1) {
                    winObj.close().remove();
                    var d = dialog({ content: data.msg }).show();
                    setTimeout(function () {
                        d.close().remove();
                        location.reload(); //刷新页面
                    }, 2000);
                } else {
                    top.dialog({
                        title: '提示',
                        content: '错误提示：' + data.msg,
                        okValue: '确定',
                        ok: function () { }
                    }).showModal(winObj);
                }
            }
        });
    }
</script>
</head>

<body class="mainbody">
<form method="post" action="/management/order/save" id="form1">
<div class="aspNetHidden">
<input type="hidden" name="VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!}" >
</div>
<!--导航栏-->
<div class="location">
  <a href="/management/order/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/management/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/management/order/list"><span>订单管理</span></a>
  <i class="arrow"></i>
  <span>订单详细</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div id="floatHead" class="content-tab-wrap" style="height: 42px;">
  <div class="content-tab" style="position: fixed; top: 0px; width: 1373px;">
    <div class="content-tab-ul-wrap">
      <div class="tab-title"><span>订单详细信息</span><i></i></div><ul>
        <li><a class="selected" href="javascript:;">订单详细信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dd style="margin-left:50px;text-align:center;">
      <div class="order-flow" style="width:<#if order.status?? &&order.status==4>720<#else>700</#if>px">
        <div class="order-flow-left">
          <a class="order-flow-input">生成</a>
          <span><p class="name">订单已生成</p><p>${order.orderTime!}|${order.statusCode!}</p></span>
        </div>
        <#if order.statusCode==7>
          <div class="order-flow-wait">
            <a class="order-flow-input">付款</a>
            <span><p class="name">等待付款</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">发货</a>
            <span><p class="name">等待发货</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">收货</a>
            <span><p class="name">等待收货</p></span>
          </div>
                  
          <div class="order-flow-right-wait">
            <a class="order-flow-input">完成</a>
            <span><p class="name">等待完成</p></span>
          </div>
          <#elseif order.statusCode==3>
          <div class="order-flow-wait">
            <a class="order-flow-input">发货</a>
            <span><p class="name">等待发货</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">收货</a>
            <span><p class="name">等待收货</p></span>
          </div>
          
           <div class="order-flow-right-wait">
             <a class="order-flow-input">完成</a>
             <span><p class="name">等待完成</p></span>
           </div>
           <#elseif order.statusCode==4>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已发货</a>
            <span><p class="name">已发货</p><p>${order.expressTime!}</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">收货</a>
            <span><p class="name">等待收货</p></span>
          </div>
          
           <div class="order-flow-right-wait">
             <a class="order-flow-input">完成</a>
             <span><p class="name">等待完成</p></span>
           </div>
           <#elseif order.statusCode==5>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已发货</a>
            <span><p class="name">已发货</p><p>${order.expressTime!}</p></span>
          </div>
          
          <div class="order-flow-arrive">
            <a class="order-flow-input">收货</a>
            <span><p class="name">已收货</p><p>${order.receiveTime!}</p></span>
          </div>
          
           <div class="order-flow-right-arrive">
             <a class="order-flow-input">完成</a>
             <span><p class="name">完成</p></span>
           </div>
        <#elseif order.statusCode==8>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已付款</a>
            <span><p class="name">已付款</p><p>${order.paymentTime!}</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">发货</a>
            <span><p class="name">等待发货</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">收货</a>
            <span><p class="name">等待收货</p></span>
          </div>
          
           <div class="order-flow-right-wait">
             <a class="order-flow-input">完成</a>
             <span><p class="name">等待完成</p></span>
           </div>
        <#elseif order.statusCode==9>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已付款</a>
            <span><p class="name">已付款</p><p>${order.paymentTime!}</p></span>
          </div>
          
          <div class="order-flow-arrive">
            <a class="order-flow-input">已发货</a>
            <span><p class="name">已发货</p><p>${order.expressTime!}</p></span>
          </div>
          
          <div class="order-flow-wait">
            <a class="order-flow-input">收货</a>
            <span><p class="name">等待收货</p></span>
          </div>
          
           <div class="order-flow-right-wait">
             <a class="order-flow-input">完成</a>
             <span><p class="name">等待完成</p></span>
           </div>
        <#elseif order.statusCode gte 9 && order.statusCode != 14 && order.statusCode != 12>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已付款</a>
            <span><p class="name">已付款</p><p>${order.paymentTime!}</p></span>
          </div>
          
          <div class="order-flow-arrive">
            <a class="order-flow-input">已发货</a>
            <span><p class="name">已发货</p><p>${order.expressTime!}</p></span>
          </div>
          
          <div class="order-flow-arrive">
            <a class="order-flow-input">已收货</a>
            <span><p class="name">已收货</p><p>${order.receiveTime!}</p></span>
          </div>
          
           <div class="order-flow-right-arrive">
             <a class="order-flow-input">完成</a>
             <span><p class="name">已完成</p></span>
           </div>
        <#elseif order.statusCode==14>
          <div class="order-flow-arrive">
            <a class="order-flow-input">已确认</a>
            <span><p class="name">已确认</p><p>${order.confirmTime!}</p></span>
          </div>
          <div class="order-flow-wait">
            <a class="order-flow-input">待取货</a>
            <span><p class="name">等待取货</p><p></p></span>
          </div>
           <div class="order-flow-right-wait">
             <a class="order-flow-input">未完成</a>
             <span><p class="name">等待完成</p><p></p></span>
           </div>
        <#elseif order.statusCode==12>
           <div class="order-flow-right-arrive">
             <a class="order-flow-input">已取消</a>
             <span><p class="name">已取消</p><p>${order.cancelTime!}</p></span>
           </div>
        </#if>
         
      </div>
    </dd>
  </dl>
  <dl>
    <dt>订单号</dt>
    <dd><span id="spanOrderNo">${order.orderNo!}</span></dd>
  </dl>
  
  <dl>
    <dt>商品列表</dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <thead>
          <tr>
            <th style="text-align:left;">商品信息</th>
            <th width="8%">销售价</th>
            <th width="8%">提成比例</th>
            <th width="8%">数量</th>
            <th width="12%">金额合计</th>
            <th width="8%">提成金额</th>
          </tr>
        </thead>
        <tbody>
          <#if order.orderGoods??>
            <#list order.orderGoods as og>
              <tr class="td_c">
                <td style="text-align:left;white-space:inherit;word-break:break-all;line-height:20px;">
                  ${og.goodsTitle!''}
                  <br>
                  ${og.selectOneValue!''} ${og.selectTwoValue!''} ${og.selectThreeValue!''}
                  </br>
                </td>
                <td><#if og.salePrice??>${og.salePrice?string('0.00')}</#if></td>
                <td>${og.costRate!'0'}</td>
                <td>${og.quantity!}</td>
                <td><#if og.salePrice??>${(og.salePrice*og.quantity!0)?string('0.00')}</#if></td>
                <td><#if og.costFee??>${og.costFee?string('0.00')}<#else>0</#if></td>
              </tr>
            </#list>
          </#if>
          
        </tbody>
        </table>
      </div>
    </dd>
  </dl>
  
  <dl>
    <dt>收货信息</dt>
    <dd>
    	<#if !order.shopId??>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">收件人</th>
          <td>
            <div class="position">
              <span id="acceptNameId">${order.acceptName!''}</span>
              <input name="btnEditAcceptInfo" type="button" id="btnEditAcceptInfo" class="ibtn" value="修改">
            </div>
          </td>
        </tr>
        <tr>
          <th>发货地址</th>
          <td>
          	<span id="spanArea">${order.fullAddress!''}</span>
          	<input id="spanAreaId" type="hidden" value="${order.province!''},${order.city!''},${order.disctrict!''}">
          	<input id="addressId" type="hidden" value="${order.address!''}">
          </td>
        </tr>
        <tr>
          <th>电话</th>
          <td><span id="spanMobile">${order.telphone!''}</span></td>
        </tr>
        </tbody></table>
      </div>
      <#else>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">自提门店</th>
          <td>
            <div class="position">
              <span id="acceptNameId">${order.shopTitle!''}</span>
              <#--<input name="btnEditAcceptInfo" type="button" id="btnEditAcceptInfo" class="ibtn" value="修改">-->
            </div>
          </td>
        </tr>
        <tr>
          <th>门店地址</th>
          <td>
          	<span id="spanArea">${order.shopAddress!''}</span>
          	<input id="spanAreaId" type="hidden" value="${order.province!''},${order.city!''},${order.disctrict!''}">
          	<input id="addressId" type="hidden" value="${order.address!''}">
          </td>
        </tr>
        <#--<tr>
          <th>门店电话</th>
          <td><span id="spanMobile">${order.telphone!''}</span></td>
        </tr>-->
        </tbody></table>
      </div>
      </#if>
    </dd>
  </dl>
  <dl id="dlUserInfo">
    <dt>会员信息</dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">会员账户</th>
          <td><span id="lbUserName">${order.username!}</span></td>
        </tr>
        <#--
        <tr>
          <th>会员组别</th>
          <td><span id="lbUserGroup">注册会员</span></td>
        </tr>
        <tr>
          <th>购物折扣</th>
          <td><span id="lbUserDiscount">100 %</span></td>
        </tr>
        <tr>
          <th>账户余额</th>
          <td><span id="lbUserAmount">0.00</span> 元</td>
        </tr>
        <tr>
          <th>账户积分</th>
          <td><span id="lbUserPoint">10</span> 分</td>
        </tr>
        -->
        </tbody></table>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>支付配送</dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">支付方式</th>
          <td>${order.paymentTitle!''}</td>
        </tr>
        <tr>
          <th>配送方式</th>
          <td>${order.expressTitle!''}</td>
        </tr>
        <#if !order.shopId??>
        <tr>
          <th>物流公司</th>
          <td>${order.logisticTitle!''}</td>
        </tr>
        <tr>
          <th>物流单号</th>
          <td>${order.logisticNo!''}</td>
        </tr>
        </#if>
        <tr>
          <th>用户留言</th>
          <td>${order.message!''}</td>
        </tr>
        <#--
        <tr>
          <th>是否开具发票</th>
          <td>是</td>
        </tr>
        
        <tr>
          <th>发票抬头</th>
          <td></td>
        </tr>
        -->
        <tr>
          <th valign="top">订单备注</th>
          <td>
            <div class="position">
              <div id="divRemark">${order.remark!''}</div>
              <input name="btnEditRemark" type="button" class="ibtn" id="btnEditRemark" value="修改">
            </div>
          </td>
        </tr>
        </tbody></table>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>订单统计</dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">商品总金额</th>
          <td>
            <div class="position">
              <span id="spanRealAmountValue"><#if order.totalGoodsPrice??>${order.totalGoodsPrice?string("0.00")}</#if></span> 元
              <#--
              <input name="btnEditRealAmount" type="button" class="ibtn" value="调价">
              -->
            </div>
          </td>
        </tr>
        <tr>
          <th>配送费用</th>
          <td>
            <div class="position">
              <span id="spanExpressFeeValue"><#if order.expressFee??>${order.expressFee?string('0.00')}<#else>0.00</#if></span> 元
              <input name="btnEditExpressFee" type="button" id="btnEditExpressFee" class="ibtn" value="调价">
            </div>
          </td>
        </tr>
        <#--
        <tr>
          <th>支付手续费</th>
          <td>
            <div class="position">
              <span id="spanPaymentFeeValue">0.00</span> 元
              <input name="btnEditPaymentFee" type="button" id="btnEditPaymentFee" class="ibtn" value="调价">
            </div>
          </td>
        </tr>
        <tr>
          <th>发票税金</th>
          <td>
            <div class="position">
              <span id="spanInvoiceTaxesValue">0.00</span> 元
              <input name="btnEditInvoiceTaxes" type="button" id="btnEditInvoiceTaxes" class="ibtn" value="调价">
            </div>
          </td>
        </tr>
        <tr>
          <th>积分抵扣</th>
          <td>
            <div class="position">
              ${order.pointUse!0} 分
            </div>
          </td>
        </tr>
        -->
        <tr>
          <th>订单总金额</th>
          <td><#if order.orderAmount??>${order.orderAmount?string("0.00")}</#if> 元</td>
        </tr>
        </tbody></table>
      </div>
    </dd>
  </dl>
  <#if cash??>
  <dl>
    <dt>分销信息</dt>
    <dd>
      <div class="table-container">
        <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="100%">
        <tbody><tr>
          <th width="20%">分销金额</th>
          <td>
            <div class="position">
              <span><#if cash.cash??>${cash.cash?string("0.00")}</#if></span> 元
            </div>
          </td>
        </tr>
        <tr>
          <th>分销人</th>
          <td>
            <div class="position">
              <span id="spanExpressFeeValue"><#if distr_name??>${distr_name!''}<#if superior??>---${superior!''}</#if><#else>无</#if></span> 
            </div>
          </td>
        </tr>
        </tbody></table>
      </div>
    </dd>
  </dl>
  </#if>
</div>
<!--/内容-->


<!--工具栏-->
<div class="page-footer">
  <div class="btn-wrap" style="position: fixed;">
    <#if order.statusCode==7>
    <input name="btnCancel" type="button" id="btnCancel" value="取消订单" class="btn green">
    <input name="btnPayment" type="button" id="btnPayment" value="确认付款" class="btn">
    <#elseif order.statusCode==8 || order.statusCode==3>
    <input name="btnExpress" type="button" id="btnExpress" value="确认发货" class="btn">
    <#elseif order.statusCode==14 || order.statusCode==9 || order.statusCode==4>
    <input name="btnReceive" type="button" id="btnReceive" value="确认收货" class="btn">
    <#elseif order.statusCode==51>
    <input name="btnComplete" type="button" id="btnComplete" value="完成订单" class="btn">
    </#if>
    
    <#if order.statusCode==1>
    <input name="btnCancel" type="button" id="btnCancel" value="取消订单" class="btn green">
    <input name="btnCancel" type="button" id="btnConfirm" value="确认订单" class="btn violet">
    </#if>
    <#if order.statusCode==2>
    <input name="btnCancel" type="button" id="btnCancel" value="取消订单" class="btn green">
    </#if>
    <input id="btnPrint" type="button" value="打印订单" class="btn violet">
    <input id="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
</div>
<!--/工具栏-->

</form>


</body></html>