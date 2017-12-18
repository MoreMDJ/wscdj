package com.zxsm.wsc.common.UtilsTools;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;

import com.zxsm.wsc.common.DjKeys;

/**
 * 常用字符串工具类
 * 
 * StringTools<BR>
 * 
 * 创建人:maeing <BR>
 * 时间：2016年11月23日11:23:29 <BR>
 * @version 1.0.0
 *
 */	
public class StringTools
{
	/**
	 *
	 * @param plainText
	 *            明文
	 * @return 32位密文
	 */
	public static String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	/**
	 * 凯德加密
	 * 方法名：encryption<BR>
	 * 创建人：xiaowei <BR>
	 * 时间：2014年10月25日-下午9:48:19 <BR>
	 * @param str
	 * @param k
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String encryption(String str,int k){
		String string = "";
		for (int i = 0; i < str.length(); i++) {
			char c= str.charAt(i);
			if(c>='a' && c<='z'){
				c += k%26;
				if(c<'a'){
					c+=26;
				}
				if(c>'z'){
					c-=26;
				}
			}else if(c>='A' && c<='Z'){
				c+=k%26;
				if(c<'A'){
					c+=26;
				}
				if(c>'Z'){
					c-=26;
				}
			}
			string+=c;
		}
		return string;
	}

	/**
	 * 凯德解密
	 * 方法名：dencryption<BR>
	 * 创建人：xiaowei <BR>
	 * 时间：2014年10月25日-下午9:48:35 <BR>
	 * @param str
	 * @param n
	 * @return String<BR>
	 * @exception <BR>
	 * @since  1.0.0
	 */
	public static String dencryption(String str,int n){
		String string = "";
		int k = Integer.parseInt("-"+n);
		for (int i = 0; i < str.length(); i++) {
			char c= str.charAt(i);
			if(c>='a' && c<='z'){
				c += k%26;
				if(c<'a'){
					c+=26;
				}
				if(c>'z'){
					c-=26;
				}
			}else if(c>='A' && c<='Z'){
				c+=k%26;
				if(c<'A'){
					c+=26;
				}
				if(c>'Z'){
					c-=26;
				}
			}
			string+=c;
		}
		return string;
	}
//	public static void main(String[] args) {
//		String str="1";
//		System.out.println(encryption(str));
//	}

	/**
	 * 字符串转换时间默认格式yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static Date stringToDate(String time, String dateFormat) {
		if (null == dateFormat || "".equals(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = null;
		if (null != time && !time.equals("")) {
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
	
	/**
	 * 过滤特殊字符 
	 * 清除掉所有特殊字符  [`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException { 
		if (str == null)
		{
			return null;
		}
		String regEx="[`~!@$%^&*+=|{}':;'\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。、？]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	} 
	
	/**
	 * 过滤特殊字符 
	 * 只允许字母和数字 [^a-zA-Z0-9]
	 * 清除掉所有特殊字符 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringNumberFilter(String str) throws PatternSyntaxException { 
		if (str == null)
		{
			return null;
		}
		String regEx ="[^a-zA-Z0-9]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}
	
	public static String encryptionPassword(String username,String password)
	{
		return encryption(password + DjKeys.DefaultPasswordKey + username);
	}
	
	/**
	 * 生成订单号
	 * @param headStr
	 * @return
	 */
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
		String orderNum = headStr + sDate + suiji;
		return orderNum;
	}
	
	
	public static String converter(Map<String, Object> dataMap)
    {
        synchronized (StringTools.class)
        {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("<xml>\n");
            Set<String> objSet = dataMap.keySet();
            for (Object key : objSet)
            {
                if (key == null)
                {
                    continue;
                }
//                strBuilder.append("\n");
                strBuilder.append("<").append(key.toString()).append(">");
                Object value = dataMap.get(key);
                if(value != null)
                	strBuilder.append(coverter(value));
                strBuilder.append("</").append(key.toString()).append(">\n");
            }
            strBuilder.append("</xml>");
            return strBuilder.toString();
        }
    }
	
    public static String coverter(Object[] objects) {
        StringBuilder strBuilder = new StringBuilder();
        for(Object obj:objects) {
            strBuilder.append("<item className=").append(obj.getClass().getName()).append(">\n");
            strBuilder.append(coverter(obj));
            strBuilder.append("</item>\n");
        }
        return strBuilder.toString();
    }

    public static String coverter(Collection<?> objects)
    {
        StringBuilder strBuilder = new StringBuilder();
        for(Object obj:objects) {
            strBuilder.append("<item className=").append(obj.getClass().getName()).append(">\n");
            strBuilder.append(coverter(obj));
            strBuilder.append("</item>\n");
        }
        return strBuilder.toString();
    }

    /**
     * Coverter.
     *
     * @param object the object
     * @return the string
     */
    public static String coverter(Object object)
    {
        if (object instanceof Object[])
        {
            return coverter((Object[]) object);
        }
        if (object instanceof Collection)
        {
            return coverter((Collection<?>) object);
        }
        StringBuilder strBuilder = new StringBuilder();
        if (isObject(object))
        {
            Class<? extends Object> clz = object.getClass();
            Field[] fields = clz.getDeclaredFields();

            for (Field field : fields)
            {
                field.setAccessible(true);
                if (field == null)
                {
                    continue;
                }
                String fieldName = field.getName();
                Object value = null;
                try
                {
                    value = field.get(object);
                }
                catch (IllegalArgumentException e)
                {
                    continue;
                }
                catch (IllegalAccessException e)
                {
                    continue;
                }
                strBuilder.append("<").append(fieldName)
                        .append(" className=\"").append(
                                value.getClass().getName()).append("\">\n");
                if (isObject(value))
                {
                    strBuilder.append(coverter(value));
                }
                else if (value == null)
                {
                    strBuilder.append("null\n");
                }
                else
                {
                    strBuilder.append(value.toString() + "\n");
                }
                strBuilder.append("</").append(fieldName).append(">\n");
            }
        }
        else if (object == null)
        {
            strBuilder.append("null\n");
        }
        else
        {
            strBuilder.append(object.toString() + "");
        }
        return strBuilder.toString();
    }

    /**
     * Checks if is object.
     *
     * @param obj the obj
     *
     * @return true, if is object
     */
    private static boolean isObject(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (obj instanceof String)
        {
            return false;
        }
        if (obj instanceof Integer)
        {
            return false;
        }
        if (obj instanceof Double)
        {
            return false;
        }
        if (obj instanceof Float)
        {
            return false;
        }
        if (obj instanceof Byte)
        {
            return false;
        }
        if (obj instanceof Long)
        {
            return false;
        }
        if (obj instanceof Character)
        {
            return false;
        }
        if (obj instanceof Short)
        {
            return false;
        }
        if (obj instanceof Boolean)
        {
            return false;
        }
        return true;
    }
    
    /** 
     * 判断Emoji表情字符
     * @param codePoint
     * @return
     */
    private static boolean isNotEmojiCharacter(char codePoint) 
    { 
    	return (codePoint == 0x0) || 
    			(codePoint == 0x9) || 
    			(codePoint == 0xA) || 
    			(codePoint == 0xD) || 
    			((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || 
    			((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || 
    			((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
    }
    
    /** 
     * 过滤emoji 或者 其他非文字类型的字符 
     * @param source 目标字符串
     * @param repStr 替换字符串
     * @return 
     */ 
    public static String filterEmoji(String source, String repStr) 
    {
    	if(StringUtils.isBlank(repStr))
    		repStr = "*";
    	int len = source.length();
    	StringBuilder buf = new StringBuilder(len); 
    	for (int i = 0; i < len; i++) 
    	{ 
    		char codePoint = source.charAt(i); 
    		if (isNotEmojiCharacter(codePoint)) 
    		{ 
    			buf.append(codePoint); 
    		} else{

    			buf.append(repStr);

    		}
    	} 
    	return buf.toString(); 
    }
} 


