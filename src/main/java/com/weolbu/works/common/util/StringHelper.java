package com.weolbu.works.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;

import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringHelper {
    private static final Logger LOG = LoggerFactory.getLogger(StringHelper.class);


    /**
     * null 이나 빈값일경우 s1 으로 치환해서 리턴
     * @param s 검증할 문자
     * @param s1 null 일경우 치환할문자
     * @return null 이 아닐경우 S ,null 일경우 치환문자(s1)
     */
    public static String nvl(String s, String s1) {
        if (isBlank(s)) {
            return s1;
        }
        return s;
    }

    /**
     * @param param 검증할문자열
     * @return 검증결과 (입력받은 문자열이 null 이거나 길이가 0 이면 True 아니면 false)
     * value = '';
     * StringHelper.isEmpty(value) : true
     * StringHelper.isBlank(value) : true
     * value = ' '; // 공백
     * StringHelper.isEmpty(value) : false
     * StringHelper.isBlank(value) : true
     */
    public static boolean isEmpty(String param) {
        return StringUtils.isEmpty(param);
    }

    /**
     * 설명	 :입력받은 문자열이 null 이거나 길이가 0 이면 True 를 리턴한다 (주의:공백값으로 인정안함).
     * @param param 검증할문자열
     * @return 공백포함 빈값일경우 true 아니면 false
     * value = '';
     * StringHelper.isEmpty(value) : true
     * StringHelper.isBlank(value) : true
     * value = ' '; // 공백
     * StringHelper.isEmpty(value) : false
     * StringHelper.isBlank(value) : true
     */
    public static boolean isBlank(String param) {
        return StringUtils.isBlank(param);
    }

    /**
     * 숫자포맷출력
     * @param val
     * @return
     */
    public static String addComma(long val) {
        DecimalFormat df = new DecimalFormat("###,##0");
        return df.format(val);
    }

    /**
     * 숫자포맷출력
     * @param val
     * @return
     */
    public static String addComma(String val) {
        long rslt = 0;

        try {
            rslt = Long.parseLong(val);
        } catch (Exception e) {
            return "";
        }

        return StringHelper.addComma(rslt);
    }

    /**
     * null 이나 빈값일경우 "" 으로 치환해서 리턴
     * @param s 검증할 문자
     * @param s 치환할문자 공백
     * @return null 이 아닐경우 S ,null 일경우 치환문자 공백
     */
    public static String nvl(String s) {
        if (isBlank(s)) {
            return "";
        }
        return s;
    }

    /**
     * 입력받은 List가 null 이거나 길이가 0 이면 True 를 리턴한다
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 현재(한국기준) 시간정보를 얻는다.<BR>
     * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 셋팅하면 1998121011과 같이 Return.<BR>
     * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이 0~23시간 타입으로
     * Return. <BR>
     * String CurrentDate = CtosUtil2.getKST("yyyyMMddHH");<BR>
     *
     * @param format
     *			얻고자하는 현재시간의 Type
     * @return str 현재 한국 시간.
     */
    public static String getKST(String format)
    {
        int millisPerHour = 60 * 60 * 1000;
        SimpleDateFormat fmt = new SimpleDateFormat(format,Locale.KOREA);

        SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");
        fmt.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        String str = fmt.format(new java.util.Date(time));
        return str;
    }

    /**
     * 현재날자로부터 N일후의 날자를 계산한다
     * flag 년도(year), 월(mon), 일(day) 구분자
     * addAmount 더하려는 일
     * return 형식(YYYYMMDD)
     */
    public static String getAddDate(String format, String flag, int addAmount)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if("year".equals(flag)) cal.add(Calendar.YEAR, addAmount);
        if("mon".equals(flag)) cal.add(Calendar.MONTH, addAmount);
        if("day".equals(flag)) cal.add(Calendar.DAY_OF_YEAR, addAmount);
        if("hour".equals(flag)) cal.add(Calendar.HOUR, addAmount);

        String str = getStringDate(cal.getTime(), format);
        return str;
    }

    /**
     * String 형태의 숫자값을 int 로 파싱하고, 잘못된 입력에 대해서는 i값으로 리턴 하는 함수
     *
     * @param s
     *			String
     * @param i
     *			int
     * @return int
     */
    public static int parseInt(String s, int i) {
        try {
            if (s != null && !s.equals("")) {
                if (s.indexOf(".") > -1) {
                    return Integer.parseInt(s.substring(0, s.indexOf(".")));
                } else {
                    return Integer.parseInt(s);
                }
            } else {
                return i;
            }
        } catch (NumberFormatException numberformatexception) {
            return i;
        }
    }

    /**
     * 설명	 : Date 형태를 String 형식으로 리턴
     * @param pDate
     *		  Date
     * @param pFormat
     *		 String format 예) yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static  String getStringDate(Date pDate , String pFormat ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pFormat,Locale.KOREA);

        String rtnDate = dateFormat.format(pDate);
        return rtnDate;
    }

    /**
     *
     * 입력받은 String 형의 데이타값을 s1형태로 변환하여 리턴
     * @param  s   String
     * @param  s1  String
     * @return String
     *
     */
    public static String format(String s, String s1) {
        return format(Double.parseDouble(s), s1);
    }

    /**
     *
     * 입력받은 int 형의 데이타값을 s형태로 변환하여 리턴
     *
     * @param  i   int
     * @param  s   String
     * @return String
     *
     */
    public static String format(int i, String s) {
        DecimalFormat decimalformat = new DecimalFormat(s);
        return decimalformat.format(i);
    }

    /**
     *
     * 입력받은 float형의 데이타값을 s형태로 변환하여 리턴
     *
     * @param  f   float
     * @param  s   String
     * @return String
     *
     */
    public static String format(float f, String s) {
        DecimalFormat decimalformat = new DecimalFormat(s);
        return decimalformat.format(f);
    }

    /**
     *
     * 입력받은 long형의 데이타값을 s형태로 변환하여 리턴
     *
     * @param  l   long
     * @param  s   String
     * @return String
     *
     */
    public static String format(long l, String s) {
        DecimalFormat decimalformat = new DecimalFormat(s);
        return decimalformat.format(l);
    }

    /**
     *
     * 입력받은 double형의 데이타값을 s형태로 변환하여 리턴
     *
     * @param  d   double
     * @param  s   String
     * @return String
     *
     */
    public static String format(double d, String s) {
        DecimalFormat decimalformat = new DecimalFormat(s);
        return decimalformat.format(d);
    }



}

