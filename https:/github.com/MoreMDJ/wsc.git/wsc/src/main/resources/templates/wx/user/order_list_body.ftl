<#if order_list??>
<#list order_list as order>
<div class="orderall_list mt20">
        <p><span>订单号：<i>${order.orderNo!'' }</i></span><label>${order.statusTitle!''}</label></p>
        <#if order.orderGoods?? && order.orderGoods?size gt 0>
        <#list order.orderGoods as goods>
        <dl>
            <dt><img src="${goods.imgUrl!''}"></dt>
            <dd class="dd1">${goods.goodsTitle!''}</dd>
            <dd class="dd2">套餐类型：<span>无</span></dd>
            <dd class="dd3"><span>${goods.salePrice?string("0.00")}</span> <#if goods.marketPrice??><s>￥${goods.marketPrice?string("0.00")}</s></#if></dd>
        </dl>
        </#list>
	        <i>共<span>${order.orderGoods?size}</span>件商品  合计￥<label>${order.orderAmount?string('0.00')}</label>(含运费￥<span>${order.expressFee?string('0.00') }</span>)</i>
        </#if>
        <span>
        	<#if orderStatus?? &&orderStatus ==1 >
        	<a href="/wx/u/order/detail/${order.id?c}" title="">订单详情</a>
        	<#elseif orderStatus?? &&orderStatus ==2 >
            <a href="javascript:submitOrderPay(${order.id?c})" title="" class="orderall_list_colored">去付款</a>
            <a href="/wx/u/order/detail/${order.id?c}" title="">订单详情</a>
        	<#elseif orderStatus?? &&orderStatus ==3 >
        	<a href="/wx/u/order/detail/${order.id?c}" title="">订单详情</a>
        	<#elseif orderStatus?? &&orderStatus == 4>
        	<a href="/wx/u/order/receive?orderId=${order.id?c}" title="" class="orderall_list_colored">确认收货</a>
        	<a href="/wx/u/order/detail/${order.id?c}" title="">订单详情</a>
        	<#elseif orderStatus?? &&orderStatus == 6>
        	<a href="#" title="" class="orderall_list_colored">确认取货</a>
        	<a href="/wx/u/order/detail/${order.id?c}" title="">订单详情</a>
        	</#if>
        </span>
    </div>
</#list>
</#if>