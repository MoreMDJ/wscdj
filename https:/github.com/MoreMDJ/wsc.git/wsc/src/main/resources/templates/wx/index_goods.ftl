<#if goodGoods??>
<#list goodGoods.content as goods>
<a href="/wx/goods/${goods.id?c}" title="${goods.title!""}">
	<dl class="bgwhite">
		<dt>
			<img src="${goods.imgUrl!''}">
		</dt>
		<dd>${goods.title!''}</dd>
		<dd>
			<span class="colpurple">￥${goods.salePrice?string('#.00') }</span>
			<#-- <#if goods.marketPrice??><span class="col99918">￥${goods.marketPrice?string('#.00') }</span></#if> -->
		</dd>
	</dl>
</a>
</#list>
</#if>