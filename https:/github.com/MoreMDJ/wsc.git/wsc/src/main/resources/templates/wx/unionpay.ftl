<#if !html??>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  <body>
      <form id="pay_form" action="https://101.231.204.80:5000/gateway/api/frontTransReq.do" method="post">
          <input type="hidden" name="txnType" id="txnType" value="01" />
          <input type="hidden" name="frontUrl" id="frontUrl" value="http://127.0.0.1:8080/ACPSample_B2C/frontRcvResponse" />
          <input type="hidden" name="channelType" id="channelType" value="07" />
          <input type="hidden" name="currencyCode" id="currencyCode" value="156" />
          <input type="hidden" name="merId" id="merId" value="777290058110048" />
          <input type="hidden" name="txnSubType" id="txnSubType" value="01" />
          <input type="hidden" name="txnAmt" id="txnAmt" value="10000" />
          <input type="hidden" name="version" id="version" value="5.0.0" />
          <input type="hidden" name="signMethod" id="signMethod" value="01" />
          <input type="hidden" name="backUrl" id="backUrl" value="http://222.222.222.222:8080/ACPSample_B2C/BackRcvResponse" />
          <input type="hidden" name="certId" id="certId" value="68759663125" />
          <input type="hidden" name="encoding" id="encoding" value="UTF-8" />
          <input type="hidden" name="bizType" id="bizType" value="000201" />
          <input type="hidden" name="signature" id="signature" value="q75cUw1E90Z/3zoPLoaWwOsHoiLmw4PvD1xgUIapsxKY3tcQpHmI/Y/4oKsG3lli4DpU63EoZScTEZNjdOvorAd5+DTSmKNLECVSBxy7mRTfTVISX/jYuVuc87ogdro8GpT4sHaY0jwVjp1dWalOSQ/jfoYniAggUuhSgQtz/0dSH//R4GVa3sP22jJjHWeWUVFJi5bMNeYe57qqcdZ5Ga04rnKnGuIpIQC3I3GosKziRtRGjdo+OYFmbl28W3QwB5qohG1QIqPvwpDM6WUlVbStuEVBf/FwpZ8yuai8WXOU+GQ9kZYuRSoSDNrRR9/jmYqkwyJDEMtWsl9pehQ4Og==" />
          <input type="hidden" name="orderId" id="orderId" value="20160303100902" />
          <input type="hidden" name="txnTime" id="txnTime" value="20160303100902" />
          <input type="hidden" name="accessType" id="accessType" value="0" />
      </form>
      <script type="text/javascript">document.all.pay_form.submit();</script>
  </body>
</html>
<#else>
${html!''}
</#if>