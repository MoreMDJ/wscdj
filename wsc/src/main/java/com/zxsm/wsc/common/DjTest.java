package com.zxsm.wsc.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.zxsm.wsc.common.UtilsTools.DjArrayUtils;
import com.zxsm.wsc.common.UtilsTools.DjSMSUtils;

public class DjTest
{
	public static void main(String[] args)
	{
		try
		{
	       System.out.println(DjSMSUtils.getVerifyCode());
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		Long[] z = {1l,23l,34l,56l,7l,3l,41l,8l};
		Long[] n = {1l,2l,3l,4l,5l,6l,45l,76l,8l,7l,41l,};
		Long[] m = {1l};
		
		ArrayList<Long> arrayList = DjArrayUtils.complement(n, m);
		ArrayList<Long> list = DjArrayUtils.Intersection(arrayList.toArray(new Long[arrayList.size()]), z);
		DjArrayUtils.OutPut(list);
	}


	public static String getUniqueNoWithHeader(String headStr)
	{
		if (headStr == null)
		{
			headStr = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		sDate = sDate.substring(1);
		String orderNum = sDate + suiji;
		return orderNum;
	}
	
	public static String XMLEncNA(String s) {
        int ln = s.length();
        for (int i = 0; i < ln; i++) {
            char c = s.charAt(i);
            if (c == '<' || c == '>' || c == '&' || c == '"') {
                StringBuffer b =
                        new StringBuffer(s.substring(0, i));
                switch (c) {
                    case '<': b.append("&lt;"); break;
                    case '>': b.append("&gt;"); break;
                    case '&': b.append("&amp;"); break;
                    case '"': b.append("&quot;"); break;
                }
                i++;
                int next = i;
                while (i < ln) {
                    c = s.charAt(i);
                    if (c == '<' || c == '>' || c == '&' || c == '"') {
                        b.append(s.substring(next, i));
                        switch (c) {
                            case '<': b.append("&lt;"); break;
                            case '>': b.append("&gt;"); break;
                            case '&': b.append("&amp;"); break;
                            case '"': b.append("&quot;"); break;
                        }
                        next = i + 1;
                    }
                    i++;
                }
                if (next < ln) b.append(s.substring(next));
                s = b.toString();
                break;
            } // if c ==
        } // for
        return s;
    }
	public static String getNumberStr(String headStr)
	{
		if (headStr == null)
		{
			headStr = "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
		String sDate = sdf.format(now);
		Random random = new Random();
		Integer suiji = random.nextInt(900) + 100;
		String orderNum = headStr + sDate + suiji; 
		return orderNum;
	}
	
	public static String generateRandomNo(Integer number)
	{
		Random random = new Random();
		int[] array = {0,1,2,3,4,5,6,7,8,9};
		for (int i = 9 ; i >0 ;i--)
		{
			int index = random.nextInt(i);
			int temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		String result = "";
		for (int i = 0;i < number ;i++)
		{
			result = result + array[i];
			if(i >9)
			{
				i = 0;
			}
		}
		
		return result;
	}
}
