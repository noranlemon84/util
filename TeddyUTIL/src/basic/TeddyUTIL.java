package basic;


import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TeddyUTIL {
	
	//private static Logger log = Logger.getLogger( TeddyUTIL.class );
    private final static String PATTERN_NUMBER = "[0-9]+";
    private final static String PATTERN_SIGNEDNUMBER = "[+-]?[0-9]+";
    private final static String PATTERN_ALPHA = "[a-zA-Z]+";
    private final static String PATTERN_NUMBERALPHA = "[a-zA-Z0-9]+";
    private final static String PATTERN_WHITESPACE = "\\s+";
    //private final static String PATTERN_ALPHASPACE = "[a-zA-Z\\s]+";
    private final static String PATTERN_EMAIL1 = "(@.*@)|(\\.\\.)|(@\\.)|(^\\.)";
    private final static String PATTERN_EMAIL2 = "^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$";
    private final static char CHAR_SPACE = ' ';
    private final static int FORMAT_MONEY_DEFAULT_POSI_LEN = 13;
    private final static int FORMAT_MONEY_DEFAULT_DECI_LEN = 3;
    private final static int FORMAT_MONEY_DEFAULT_FULL_LEN = FORMAT_MONEY_DEFAULT_POSI_LEN + FORMAT_MONEY_DEFAULT_DECI_LEN;
    private final static int FORMAT_RATE_DEFAULT_POSI_LEN = 4;
    private final static int FORMAT_RATE_DEFAULT_DECI_LEN = 2;
    //private final static int	FORMAT_RATE_DEFAULT_FULL_LEN	= FORMAT_RATE_DEFAULT_POSI_LEN+FORMAT_RATE_DEFAULT_DECI_LEN;


    
    /**
     * 문자열의 길이를 알아낸다.
     * 문자열이 null 일 경우에도 0을 리턴하도록 한다.
     * 
     * @param String str : 문자열
     * @return int : 문자열 길이
     */
    public static int length( String str )
    {
        return length( str, 0 );
    }

    /**
     * 문자열의 길이를 알아낸다.
     * 두번째 인자로 주어진 길이와 비교하여,
     * 주어진 문자열의 길이에서 비교 길이의 차를 리턴한다.
     * 문자열이 null 일 경우는 0으로 간주하여, -length 값을 리턴한다.
     * 
     * @param String str : 비교대상 문자열
     * @param int : 비교대상 길이
     * @return int : 문자열 길이 - 비교대상 길이
     */
    public static int length( String str, int length )
    {
        if ( str == null ) { return -length; }
        return str.trim().length() - length;
    }


    /**
     * pattern을 사용하여 문자열이 해당 패턴에 매칭되는지를 알아낸다.
     * 
     * @param 	String patternStr : 대상 패턴 문자열 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 treu, 실패시 false 반환
     */
    private static boolean basicMatch( String patternStr, String dataStr )
    {
        if ( dataStr == null || dataStr.length() <= 0 ) return false;
        Pattern pattern = Pattern.compile( patternStr );
        Matcher matcher = pattern.matcher( dataStr );
        return matcher.matches();
    }

    /**
     * pattern을 사용하여 특정 문자열이 해당 패턴으로 변환함.
     * 
     * @param 	String patternStr : 사용할 문자열 
     * @param 	String dataStr : 대상 문자열
     * @param 	String dataStr : 변경될 문자열
     * @return 	String
     */
    private static String basicReplaceAll( String patternStr, String dataStr, String desStr )
    {
        if ( dataStr == null || dataStr.length() <= 0 ) return "";
        Pattern pattern = Pattern.compile( patternStr );
        Matcher matcher = pattern.matcher( dataStr );
        return matcher.replaceAll( desStr );
    }

    
    /**
     * pattern을 사용하여 문자열의 값들이 숫자로만 조합되어 있는지를 확인.
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 true, 실패시 false 반환
     */
    public static boolean isNumber( String dataStr )
    {
        return basicMatch( PATTERN_NUMBER, dataStr );
    }

    /**
     * pattern을 사용하여 문자열의 값들이 (+|-)를 포함한 숫자로만 조합되어 있는지를 확인.
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 true, 실패시 false 반환
     */
    public static boolean isSignedNumber( String dataStr )
    {
        return basicMatch( PATTERN_SIGNEDNUMBER, dataStr );
    }

    /**
     * pattern을 사용하여 문자열의 값들이 영문문자로만 조합되어 있는지를 확인.
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 true, 실패시 false 반환
     */
    public static boolean isAlpha( String dataStr )
    {
        return basicMatch( PATTERN_ALPHA, dataStr );
    }

    /**
     * pattern을 사용하여 문자열의 값들이 숫자와 영문문자로만 조합되어 있는지를 확인.
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 true, 실패시 false 반환
     */
    public static boolean isNumberAlpha( String dataStr )
    {
        return basicMatch( PATTERN_NUMBERALPHA, dataStr );
    }

    /**
     * pattern을 사용하여 문자열의 값들이 email 포멧에 맞는지를 알아보는 함수.<br>
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	boolean : 성공시 true, 실패시 false 반환
     */
    public static boolean isEmail( String email )
    {
        if ( email == null ) { return false; }
        if ( email.trim().length() <= 0 ) { return false; }
        if ( email.indexOf( " " ) > 0 ) { return false; }

        return ( !email.matches( PATTERN_EMAIL1 ) && email.matches( PATTERN_EMAIL2 ) );
    }

    
    //---------------------------------------------
    // type 변환 함수 모음
    //---------------------------------------------

    /**
     * 문자열을 integer 로 바꾼다.
     * 변환이 실패할 경우, 0을 리턴한다.
     * 
     * @param String val : 문자열
     * @return int
     */
    public static int strToInt( String val )
    {
        return strToInt( val, 0 );
    }

    /**
     * 문자열을 숫자로 변환하여 integer 값을 리턴한다.<br>
     * Oracle 의 ivl() 함수를 모방했다. 문자열을 파싱하여 integer 로 리턴한다.
     * 변환이 실패하면, rep 값으로 리턴한다.
     * 
     * @param String val 문자열
     * @param int rep 변환실패시 대체값
     * @return int
     */
    public static int strToInt( String val, int rep )
    {
        if ( val == null ) return rep;
        if ( val.trim().length() <= 0 ) return rep;

        int value = rep;

        try {
            value = Integer.parseInt( val );
        }
        catch ( NumberFormatException e ) {
            value = rep;
        }

        return value;
    }

    /**
     * 문자열을 long 로 바꾼다.<br>
     * 변환이 실패할 경우, 0L을 리턴한다.
     * 
     * @param String val 문자열
     * @return long
     */
    public static long strToLong( String val )
    {
        return strToLong( val, 0L );
    }

    /**
     * 문자열을 숫자로 변환하여 long 값을 리턴한다.<br> 
     * 실패시 0L을 리턴한다.
     * 
     * @param String val 문자열
     * @param long rep 변환실패시 대체값
     * @return long
     */
    public static long strToLong( String val, long rep )
    {
        if ( val == null ) return rep;
        if ( val.trim().length() <= 0 ) return rep;

        long value = rep;

        try {
            value = Long.parseLong( val );
        }
        catch ( NumberFormatException e ) {
            value = rep;
        }

        return value;

    }

    /**
     * 문자열을 실수(float)로 변환한다.<br>
     * 변환 실패시 0.0F 를 리턴한다.
     * 
     * @param String val 변환대상 문자열
     * @return float
     */
    public static float strToFloat( String val )
    {
        return strToFloat( val, 0.0F );
    }

    /**
     * 문자열을 실수(float)로 변환한다.<br>
     * 변환 실패 시 rep 값을 리턴한다.
     * 
     * @param String val 변환대상 문자열
     * @param float rep 변환실패시 리턴값 
     * @return float
     */
    public static float strToFloat( String val, float rep )
    {
        if ( val == null ) return rep;
        if ( val.trim().length() <= 0 ) return rep;

        float value = rep;

        try {
            value = Float.parseFloat( val );
        }
        catch ( NumberFormatException e ) {
            value = rep;
        }

        return value;
    }

    /**
     * 문자열을 실수(double)로 변환한다.<br>
     * 변환 실패시 0.0 을 리턴한다.
     * 
     * @param String val 변환대상 문자열
     * @return double
     */
    public static double strToDouble( String val )
    {
        return strToDouble( val, 0.0 );
    }

    /**
     * 문자열을 실수(double)로 변환한다.<br>
     * 변환 실패시 rep 을 리턴한다.
     * 
     * @param String val 변환대상 문자열
     * @param float rep 변환실패시 리턴값 
     * @return double
     */
    public static double strToDouble( String val, double rep )
    {
        if ( val == null ) return rep;
        if ( val.trim().length() <= 0 ) return rep;

        double value = rep;

        try {
            value = Double.parseDouble( val );
        }
        catch ( NumberFormatException e ) {
            //System.out.println("ival(), val=[" + val + "], " + e );
            value = rep;
        }

        return value;
    }

    /**
     * 문자열이 있는지 확인한다.<br>
     * Oracle nvl() 과 비슷한 기능을 한다.
     * 자바에서 "null" 이라고 찍히는 것을 방지하기 위한 함수.
     * null 일 경우, "" (빈스트링)을 리턴한다.
     * 
     * @param String val 비교대상 문자열
     * @return String
     */
    public static String nvl( String val )
    {
        return nvl( val, "" );
    }

    /**
     * 문자열이 있는지 확인한다.<br>
     * Oracle nvl() 과 비슷한 기능을 한다.
     * 자바에서 "null" 이라고 찍히는 것을 방지하기 위한 함수.
     * null 일 경우, rep을 리턴한다.
     * 
     * @param String val 비교대상 문자열
     * @param String rep null 일 경우, 대체값
     * @return String
     */
    public static String nvl( String val, String rep )
    {
        return ( val == null ) ? rep : val;
    }

    /**
     * 문자열이 있는지 확인한다.<br>
     * Oracle nvl() 과 비슷한 기능을 한다.
     * 자바에서 "null" 이라고 찍히는 것을 방지하기 위한 함수.<br>
     * 
     * null 일 경우, rep을 리턴한다. 추가적으로 대상문자열을 trim() 한 후,
     * 0 보다 작거나 같은 경우에도 rep 를 리턴한다.
     * 
     * @param String val 비교대상 문자열
     * @param String rep null 일 경우, 대체값
     * @return String
     */
    public static String nvl2( String val, String rep )
    {
        if ( val == null ) { return rep; }
        return ( val.trim().length() <= 0 ) ? rep : val.trim();
    }

    /**
     * int를 String으로 변환
     * 
     * @param int num 변환대상
     * @return String
     */
    public static String intToStr( int num )
    {
        return Integer.toString( num );
    }

    /**
     * float를 String으로 변환
     * 
     * @param float num 변환대상
     * @return String
     */
    public static String floatToStr( float num )
    {
        return Float.toString( num );
    }

    /**
     * long를 String으로 변환
     * 
     * @param long num 변환대상
     * @return String
     */
    public static String longToStr( long num )
    {
        return Long.toString( num );
    }

    /**
     * double를 String으로 변환
     * 
     * @param double num 변환대상
     * @return String
     */
    public static String doubleToStr( double num )
    {
        return Double.toString( num );
    }

    // 객체 변환도 만들어 둬야겠지....(잘 안쓰긴 할듯...)
    /**
     * Integer 객체를 String으로 변환
     * null일 경우 기본값으로 반환
     * 
     * @param Integer num 변환대상
     * @return String
     */
    public static String intToStr( Integer num )
    {
        int defaultVal = 0;
        if ( num != null ) defaultVal = num.intValue();
        return intToStr( defaultVal );
    }

    /**
     * Float 객체를 String으로 변환
     * null일 경우 기본값으로 반환
     * 
     * @param Float num 변환대상
     * @return String
     */
    public static String floatToStr( Float num )
    {
        float defaultVal = 0.0F;
        if ( num != null ) defaultVal = num.floatValue();
        return floatToStr( defaultVal );
    }

    /**
     * Long 객체를 String으로 변환
     * null일 경우 기본값으로 반환
     * 
     * @param Long num 변환대상
     * @return String
     */
    public static String longToStr( Long num )
    {
        long defaultVal = 0L;
        if ( num != null ) defaultVal = num.longValue();
        return longToStr( defaultVal );
    }

    /**
     * Double 객체를 String으로 변환
     * null일 경우 기본값으로 반환
     * 
     * @param Double num 변환대상
     * @return String
     */
    public static String doubleToStr( Double num )
    {
        double defaultVal = 0.0D;
        if ( num != null ) defaultVal = num.doubleValue();
        return doubleToStr( defaultVal );
    }

    
    //---------------------------------------------
    // 문자열 조작 함수1
    //---------------------------------------------

    /**
     * 공백 문자열을 제거하는 함수.
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	String
     */
    public static String deleteWhitespace( String dataStr )
    {
        return basicReplaceAll( PATTERN_WHITESPACE, dataStr, "" );
    }

    /**
     * 특정 문자열을 제거하는 함수.
     * 
     * @param 	String dataStr : 대상 문자열
     * @param 	String ch : 제거 대상 문자열
     * @return 	String
     */
    public static String deleteChar( String dataStr, String ch )
    {
        return basicReplaceAll( ch, dataStr, "" );
    }

    /**
     * 특정 문자열들을 제거하는 함수.
     * 
     * @param 	String dataStr : 대상 문자열
     * @param 	String[] chs : 제거 대상 문자열들
     * @return 	String
     */
    public static String deleteChar( String dataStr, String[] chs )
    {
        String patternStr = "";

        for ( int i = 0; i < chs.length; i++ ) {
            if ( i == 0 ) {
                patternStr = patternStr + "[" + chs[i];
            }
            else if ( i == ( chs.length - 1 ) ) {
                patternStr = patternStr + chs[i] + "]";
            }
            else {
                patternStr = patternStr + chs[i];
            }
        }
        return basicReplaceAll( patternStr, dataStr, "" );
    }

    /**
     * 대상 문자열에서 특정 문자열을 다른 문자열로 대체하는 함수.
     * 
     * @param 	String dataStr : 대상 문자열
     * @param 	String org : 특정 문자열
     * @param	String des : 대체 문자열
     * @return 	String
     */
    public static String replace( String dataStr, String org, String des )
    {
        String patternStr = org;
        return basicReplaceAll( patternStr, dataStr, des );
    }

    /**
     * 대상 문자열에서 특정 문자열을 다른 문자열로 대체하는 함수.
     * 디폴트로 대체 문자열은 ""로 지정
     * 
     * @param 	String dataStr : 대상 문자열
     * @param 	String org : 특정 문자열
     * @return 	String
     */
    public static String replace( String dataStr, String org )
    {
        return replace( dataStr, org, "" );
    }

    /**
     * 대상문자열에서 공백문자 제거하는 함수.
     * 디폴트로 대체 문자열은 ""로 지정
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	String
     */
    public static String replaceBlank( String dataStr )
    {
        return replace( trim( dataStr ), " ", "" );
    }

    /**
     * 왼쪽 공백들을 제거하는 함수
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	String
     */
    public static String ltrim( String dataStr )
    {
        if ( dataStr == null || dataStr.length() == 0 ) return "";

        int i = 0;
        for ( ; i < dataStr.length(); i++ ) {
            if ( dataStr.charAt( i ) != CHAR_SPACE ) {
                break;
            }
        }
        return dataStr.substring( i );
    }

    /**
     * 오른쪽 공백들을 제거하는 함수
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	String
     */
    public static String rtrim( String dataStr )
    {
        if ( dataStr == null || dataStr.length() == 0 ) return "";

        int i = dataStr.length() - 1;
        for ( ; i >= 0; i-- ) {
            if ( dataStr.charAt( i ) != CHAR_SPACE ) {
                break;
            }
        }
        return dataStr.substring( 0, i + 1 );
    }

    /**
     * 양쪽 공백들을 제거하는 함수
     * 
     * @param 	String dataStr : 대상 문자열
     * @return 	String
     */
    public static String trim( String dataStr )
    {
        return ltrim( rtrim( dataStr ) );
    }

    /**
     * 지정한 숫자에서 문자열의 길이만큼을 제외한 길이만큼 특정 문자로 왼쪽부터 채우는 함수<br>
     * 
     * @param 	String dataStr : 대상 문자열
     * @param	int len	: 특정문자로 채운 후 전체 문자열의 length
     * @param 	char ch : 특정 문자
     * @return 	String
     */
    public static String lpad( String dataStr, int len, char ch )
    {
        if ( dataStr == null ) return "";
        if ( dataStr.length() >= len ) return dataStr;

        String tmp = "";
        for ( int i = 0; i < ( len - dataStr.length() ); i++ ) {
            tmp += ch;
        }
        return tmp + dataStr;
    }

    /**
     * 지정한 숫자에서 문자열의 길이만큼을 제외한 길이만큼 특정 문자로 오른쪽부터 채우는 함수<br>
     * 
     * @param 	String dataStr : 대상 문자열
     * @param	int len	: 특정문자로 채운 후 전체 문자열의 length
     * @param 	char ch : 특정 문자
     * @return String
     */
    public static String rpad( String dataStr, int len, char ch )
    {
        if ( dataStr == null ) return "";
        if ( dataStr.length() >= len ) return dataStr;

        String tmp = "";
        for ( int i = 0; i < ( len - dataStr.length() ); i++ ) {
            tmp += ch;
        }
        return dataStr + tmp;
    }

    /**
     * 문자열의 일부를 잘라 리턴한다.<br>
     * null 일 경우와 원하는 길이보다 작을 경우도 처리한다.
     * 
     * @param String s 문자열
     * @param int len 원하는 길이
     * @return String
     */
    public static String substring( String s, int len )
    {
        if ( s == null ) { return ""; }
        if ( s.length() < len ) { return s; }
        return s.substring( 0, len );
    }

    /**
     * 문자열의 일부를 잘라 리턴한다.<br>
     * null 일 경우와 원하는 길이보다 작을 경우도 처리한다.
     * bool이 true이면 len부터 끝까지 자른다.
     * 
     * @param String s 문자열
     * @param int len 원하는 길이
     * @param boolean bool  
     * @return String
     */
    public static String substring( String s, int len, boolean bool )
    {
        if ( s == null ) { return ""; }
        if ( s.length() < len ) { return s; }
        if ( bool )
            return s.substring( len );
        else return s.substring( 0, len );
    }

    /**
     * 문자열의 일부를 잘라 리턴한다.<br>
     * null 일 경우와 원하는 길이보다 작을 경우도 처리한다.
     * 
     * @param String s 문자열
     * @param int len 원하는 길이
     * @return String
     */
    public static String substring( String s, int len, String suffix )
    {
        String str = "";
    	
    	if ( s == null ) { return ""; }
        if ( s.length() < len ) { return s+suffix; }
        
        str = s.substring( 0, len )+suffix;
        
        return str;
    }

    /**
     * 문자열의 일부를 잘라 리턴한다.<br>
     * null 일 경우와 원하는 길이보다 작을 경우도 처리한다.
     * 부분 substring 추가
     * 
     * @param String s 문자열
     * @param int beginIndex 시작길이
     * @param int endIndex   종료길이
     * @return String
     */
    public static String substring( String s, int beginIndex, int endIndex )
    {
        if ( s == null ) { return ""; }

        if ( s.length() < endIndex ) { return s; }

        return s.substring( beginIndex, endIndex );
    }

    /**
     * 문자열 잘라 리턴한다.<br>
     * 구분자에 의하여 원하는 순서의 문자열을 리턴한다.
     * 
     * @param String s 문자열
     * @param String g 구분자
     * @param int b 리턴할 순서의 
     * @return String
     */
    public static String substring( String s, String g, int b )
    {
        if ( s == null ) { return ""; }

        String[] values = s.split( g );
        String rtlValue = "";
        for ( int x = 0; x < values.length; x++ ) {
            //System.out.println( "문자(열) " + (x+1) + " : " + values[x] );
            if ( x == b ) {

                rtlValue = values[x];
                break;
            }
        }
        //return s.substring(beginIndex, endIndex);
        return rtlValue;
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위를 표시할 경우 주로 사용된다.(3자리당 콤파 표시)<br>
     * 
     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param String str : 대상 문자열
     * @return String
     */
    public static String makeDefaultMoneyFormat( String str )
    {
        return makeNumberFormat( str, FORMAT_MONEY_DEFAULT_FULL_LEN, FORMAT_MONEY_DEFAULT_DECI_LEN, true );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * @see public static String makeDefaultMoneyFormat(String str)
     * @param int num 
     * @return String
     */
    public static String makeDefaultMoneyFormat( int num )
    {
        return makeDefaultMoneyFormat( TeddyUTIL.intToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * @see public static String makeDefaultMoneyFormat(String str)
     * @param long num 
     * @return String
     */
    public static String makeDefaultMoneyFormat( long num )
    {
        return makeDefaultMoneyFormat( TeddyUTIL.longToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * @see public static String makeDefaultMoneyFormat(String str)
     * @param float num 
     * @return String
     */
    public static String makeDefaultMoneyFormat( float num )
    {
        return makeDefaultMoneyFormat( TeddyUTIL.floatToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * @see public static String makeDefaultMoneyFormat(String str)
     * @param double num 
     * @return String
     */
    public static String makeDefaultMoneyFormat( double num )
    {
        return makeDefaultMoneyFormat( TeddyUTIL.doubleToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 사용자가 총자리수를 지정할 경우 사용함<br>
     * 
     * 금액단위로 표시됨(3자리당 콤파 표시)<br>
     * 
     * @param String str : 대상 문자열
     * @param int total_len : 사용자가 지정한 총 자리수(정수자리)
     * @return String
     */
    public static String makeMoneyFormat( String str, int total_len )
    {
        return makeNumberFormat( str, total_len, 0, true );
    }

    /**
     * 대상 문자열을 디폴트 화폐단위 표시로 변환하는 함수<br>
     * 사용자가 총수자리수 및 소수자리를 지정할 경우 사용함<br>
     * 
     * 총자리수에서 소수자리수를 뺀 부분이 정수자리가 된다.<br>
     * 
     * 금액단위로 표시됨(3자리당 콤파 표시)<br>
     * 
     * @param String str : 대상 문자열
     * @param int total_len : 사용자가 지정한 총 자리수(정수자리+소수자리)
     * @param int deci_len : 사용자가 지정한 소수 자리수
     * @return String
     */
    public static String makeMoneyFormat( String str, int total_len, int deci_len )
    {
        return makeNumberFormat( str, total_len, deci_len, true );
    }

    /**
     * 대상 문자열을 디폴트 Decimal단위 표시로 변환하는 함수<br>
     * 디폴트로 6자리(정수4+소수2)인 경우 사용한다<br>
     * 디폴트로 소수부분은 디폴트 자리까지 0도 표기한다
     * 
     * 파라미터의 string 값은 Decimal 형식의 데이터이어야 한다<br>
     * 즉, 소수부분이 포함된 데이터이어야 한다.<br>
     * (예)0012.05<br>
     * <br>
     * 주로 host 데이터 변환에 사용한다.
     * 
     * @param String str : 대상 문자열
     * @return String
     */
    public static String makeDefaultRateFormat( String str )
    {
        return makeDefaultRateFormat( str, FORMAT_RATE_DEFAULT_DECI_LEN );
    }

    /**
     * 대상 문자열을 디폴트 Decimal단위 표시로 변환하는 함수<br>
     * 소수부분 길이를 사용자가 정의하여 선택한다.<br>
     * 디폴트로 소수부분은 디폴트 자리까지 0도 표기한다
     * 
     * 파라미터의 string 값은 Decimal 형식의 데이터이어야 한다<br>
     * 즉, 소수부분이 포함된 데이터이어야 한다.<br>
     * (예)0012.05<br>
     * <br>
     * 주로 host 데이터 변환에 사용한다.
     * 
     * @param String str : 대상 문자열
     * @param int deci_len : 소수부분 길이
     * @return String
     */
    public static String makeDefaultRateFormat( String str, int deci_len )
    {
        return makeDefaultRateFormat( str, deci_len != 0 ? deci_len : FORMAT_RATE_DEFAULT_DECI_LEN, true );
    }

    /**
     * 대상 문자열을 디폴트 Decimal단위 표시로 변환하는 함수<br>
     * 소수부분 길이를 사용자가 정의하여 선택한다.<br>
     * 소수분분이 모두 0의 값을 가질경우 사용자가 보여줄지 여부를 판단한다.<br>
     * 
     * 파라미터의 string 값은 Decimal 형식의 데이터이어야 한다<br>
     * 즉, 소수부분이 포함된 데이터이어야 한다.<br>
     * (예)0012.05<br>
     * <br>
     * 주로 host 데이터 변환에 사용한다.<br>
     * 
     * isShowDeciZero : true : 0012.00 -> 0012<br>
     * isShowDeciZero : false : 0012.00 -> 0012.00<br>
     * 
     * @param String str : 대상 문자열
     * @param int deci_len : 소수부분 길이
     * @return String
     */
    public static String makeDefaultRateFormat( String str, int deci_len, boolean isShowDeciZero )
    {

        String pre_format_str = "";
        String suf_format_str = "";

        char ch = '0';
        if ( !isShowDeciZero ) ch = '#';

        pre_format_str = lpad( pre_format_str, FORMAT_RATE_DEFAULT_POSI_LEN - 1, '#' ) + "0";
        suf_format_str = lpad( suf_format_str, deci_len, ch );

        return makeNumberFormat( str, pre_format_str + "." + suf_format_str );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위는 아니지만 숫자형태로 표시할 경우 주로 사용한다.<br>

     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param String str : 대상 문자열
     * @return String
     */
    public static String makeDefaultNumberFormat( String str )
    {
        String pre_format_str = "";
        String suf_format_str = "";

        char ch = '#';

        pre_format_str = lpad( pre_format_str, FORMAT_MONEY_DEFAULT_POSI_LEN - 1, ch ) + "0";
        suf_format_str = lpad( suf_format_str, FORMAT_MONEY_DEFAULT_DECI_LEN, ch );

        return makeNumberFormat( str, pre_format_str + "." + suf_format_str );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위는 아니지만 숫자형태로 표시할 경우 주로 사용한다.<br>

     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param int num : 대상 int value
     * @return String
     */
    public static String makeDefaultNumberFormat( int num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.intToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위는 아니지만 숫자형태로 표시할 경우 주로 사용한다.<br>

     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param long num : 대상 long value
     * @return String
     */
    public static String makeDefaultNumberFormat( long num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.longToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위는 아니지만 숫자형태로 표시할 경우 주로 사용한다.<br>

     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param float num : 대상 float value
     * @return String
     */
    public static String makeDefaultNumberFormat( float num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.floatToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 콤마를 포함하여 정수13자리, 소수3자리 형식으로 표시한다.<br>
     * 
     * 금액단위는 아니지만 숫자형태로 표시할 경우 주로 사용한다.<br>

     * 만약 자리수의 변경이 필요할 경우 동일한 함수명의 overroad된 함수를 사용해서<br>
     * 직접 포맷의 형태를 지정해 주어야 한다.<br>
     * 
     * @param double num : 대상 double value
     * @return String
     */
    public static String makeDefaultNumberFormat( double num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.doubleToStr( num ) );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 
     * 사용자가 총자리수를 지정할 경우 사용함<br>
     * 
     * 금액단위로 표시되지는 않음<br>
     * 
     * @param String str : 대상 문자열
     * @param int total_len : 사용자가 지정한 총 자리수(정수자리+소수자리)
     * @return String
     */
    public static String makeNumberFormat( String str, int total_len )
    {
        return makeNumberFormat( str, total_len, 0, false );
    }

    /**
     * 대상 문자열을 디폴트 숫자 표시로 변환하는 함수<br>
     * 
     * 사용자가 총수자리수 및 소수자리를 지정할 경우 사용함<br>
     * 
     * 총자리수에서 소수자리수를 뺀 부분이 정수자리가 된다.<br>
     * 금액단위로 표시되지는 않음<br>
     * 
     * @param String str : 대상 문자열
     * @param int total_len : 사용자가 지정한 총 자리수(정수자리+소수자리)
     * @param int deci_len : 사용자가 지정한 소수자리수
     * @return String
     */
    public static String makeNumberFormat( String str, int total_len, int deci_len )
    {
        return makeNumberFormat( str, total_len, deci_len, false );
    }

    /**
     * 대상 문자열을 화폐단위 또는 숫자단위로 표시하는 공통 함수<br>
     * 
     * 단위표시 포맷을 주어진 param에 맞게 구성한다.<br>
     * 
     * @param String str : 대상 문자열
     * @param int total_len : 사용자가 지정한 총 자리수(정수자리+소수자리)
     * @param int deci_len : 사용자가 지정한 소수자리수
     * @param boolean moneyType : true(화폐단위표시), false(숫자단위표시)
     * @return String
     */
    public static String makeNumberFormat( String str, int total_len, int deci_len, boolean moneyType )
    {
        StringBuffer format = new StringBuffer();

        if ( deci_len >= total_len ) {
            format.append( "0" );
        }
        else {
            for ( int i = 0; i < total_len - ( deci_len > 0 ? deci_len : 0 ); i++ ) {
                if ( moneyType ) {
                    if ( i != 0 && i % 3 == 0 )
                        format.append( ",#" );
                    else format.append( "#" );
                }
                else {
                    format.append( "#" );
                }
            }
            format.reverse();
        }
        if ( ( deci_len > 0 ? deci_len : 0 ) != 0 ) {
            format.append( "." );
            for ( int j = 0; j < deci_len; j++ )
                format.append( "#" );
        }

        return makeNumberFormat( str, format.toString() );
    }

    /**
     * 대상 문자열을 화폐단위 또는 숫자단위로 표시하는 함수<br>
     * 
     * 특정 포맷에 맞게 문자열을 표기하는 공통 함수<br>
     * 
     * @param String str : 대상 문자열
     * @param String format : 대상 포맷 형식
     * @return String
     */
    public static String makeNumberFormat( String str, String format )
    {

        String temp = null;

        if ( str == null || "".equals( str.trim() ) )
            temp = "0";
        else {
            double change = Double.valueOf( str ).doubleValue();
            DecimalFormat decimal = new DecimalFormat( format );
            temp = decimal.format( change );
        }
        return temp;
    }

    /**
     * 대상 문자열을 소수점 2자리 포함하는 화폐단위로 변경<br>
     * @param dVal
     * @return
     */
    public static String getDollarFormat( double dVal )
    {
        String displayForm = new DecimalFormat( "#,###,###,##0.00" ).format( dVal );
        return displayForm;
    }

    /**
     * 사업자번호에 '-'를 첨가한다.<br>
     * 
     * @param 	String str : 대상 문자열
     * @return 	String
     */
    public static String makeBizNoFormat( String str )
    {
        if ( str == null ) return "";
        String temp = str;

        if ( str.length() == 10 ) {
            temp = str.substring( 0, 3 ) + "-" + str.substring( 3, 5 ) + "-" + str.substring( 5, 10 );
        }
        return temp;
    }

    //---------------------------------------------
    // 문자열 조작 함수2(with WEB)
    //---------------------------------------------

    /** 
     * 개행문자를 <br/> 로 바꾼다.<br>
     * 
     * @param String content
     * @return String
     */
    public static String nl2br( String content )
    {
        int length = content.length();
        StringBuffer buffer = new StringBuffer();

        for ( int i = 0; i < length; i++ ) {
            String comp = content.substring( i, i + 1 );
            if ( "\r".compareTo( comp ) == 0 ) {
                comp = content.substring( ++i, i + 1 );
                if ( "\n".compareTo( comp ) == 0 )
                    buffer.append( "<br>\r" );
                else buffer.append( "\r" );
            }
            buffer.append( comp );
        }
        return buffer.toString();

    }
    
    /**
     * 지정한 String중에서 "<", " ", "\n" 을 <br> 
     * html 태그로 변환 하는 함수
     * 
     * @param content
     * @return
     */
    public static String toText( String content )
    {
    	content = content.replaceAll( "<" , "&lt;" )
						 .replaceAll( "\r\n" , "<br/>" )
						 .replaceAll( "\n" , "<br/>" )
						 .replaceAll( "\r" , "<br/>" );

        return content;
    }

    /**
     * 지정한 String중에서 스크립트 내 문자열로 
     * 사용 가능한 문자열로 변환 하는 함수
     * @param content
     * @return
     */
    public static String toScriptText( String content )
    {
    	content = content.replaceAll( "<" , "&lt;" )
						 .replaceAll( "\r\n" , "<br/>" )
						 .replaceAll( "\n" , "<br/>" )
						 .replaceAll( "\r" , "<br/>" )
						 
						 .replaceAll( "'" , "\\\\'" )
						 .replaceAll( "&#39;" , "\\\\'" )
						 .replaceAll( "&#039;" , "\\\\'" )
						 .replaceAll( "\"" , "" );
    	
        return content;
    }
    
    /**
     * 지정한 String중에서 특수 문자를 코드로 변환 
     * 
     * @param content
     * @return
     */
    public static String cvtSpecialCharacter(String content)
    {
    	if ( content == null )
    	{
    		return "";
    	}
 	
    	content = content.replaceAll("<", "&lt;" ) 
		 				 .replaceAll( ">", "&gt;" )
						 //.replaceAll( "\\$", "&#36;" )
						 .replaceAll( "'", "&#039;" )
						 .replaceAll( "\"", "&#034;" );
						 //.replaceAll( "\\?", "&#063;" );
    	
    	return content;    
    } 

    /**
     * 지정된 문자열을 Integer로 변환하는 함수 <br>
     * 
     * @param 	String dataStr : 대상 문자열
     * @param	int len	: 특정문자로 채운 후 전체 문자열의 length
     * @param 	char ch : 특정 문자
     * @return 	Boolean
     */
    public static boolean isDigit( String digit )
    {
        try {
            Integer.parseInt( digit );
        }
        catch ( NumberFormatException e ) {
            return false;
        }

        return true;
    }

    /**
     * 입력된 문자열이 알파벳일 경우 소문자로 변환
     * @param str
     * @return 소문자
     */
    public static String strToLowerCase( String str )
    {
        if ( isAlpha( PATTERN_ALPHA ) ) {
            str = str.toLowerCase();
        }
        return str;
    }

    /**
     * 입력된 문자열이 알파벳일 경우 대문자로 변환
     * @param str
     * @return
     */
    public static String strToUpperCase( String str )
    {
        if ( isAlpha( PATTERN_ALPHA ) ) {
            str = str.toUpperCase();
        }
        return str;
    }

    public static String urlEncodeFlash( String s ) throws UnsupportedEncodingException
    {
        return URLEncoder.encode( s, "UTF8" );
    }

    public static String urlDecodeFlash( String s ) throws UnsupportedEncodingException
    {
        return URLDecoder.decode( s, "UTF8" );
    }

    /**
     * 입력받은 String의 앞에 요청한 length만큼 0을 채워 리턴한다.
     * @param  int     istr
     * @param  int     len 
     * @return String
     * 
     */
    public static String getSendAmount( int istr, int len )
    {
        String str = istr + "";
        StringBuffer sb = new StringBuffer();
        int sLen = str.length();
        if ( len > sLen ) {
            for ( int i = 0; i < len - sLen; i++ )
                sb.append( "0" );
        }
        sb.append( str );
        return sb.toString();
    }

    /**
     * 전각문자를 반각문자로 변환
     * @param String str
     * @return String
     */
    public static String getConvertFullToHalf( String str )
    {

        char c = 0;
        StringBuffer strBuf = new StringBuffer();

        if ( str != null && !str.equals( "" ) ) {
            for ( int i = 0; i < str.length(); i++ ) {
                c = str.charAt( i );

                // 특수문자일 경우
                if ( (int)c >= 65281 && (int)c <= 65374 ) {
                    c -= 0xfee0; // 0xfee0 = -65248
                }
                else if ( c == '　' ) { // space일경우
                    c = 0x20; // 0x20 = 32
                }
                strBuf.append( c );
            }
        }

        return strBuf.toString();
    }

    /**
     * 반각문자를 전각문자로 변환
     * @param String str
     * @return String
     */
    public static String getConvertHalfToFull( String str )
    {

        char c = 0;
        StringBuffer strBuf = new StringBuffer();

        if ( str != null && !str.equals( "" ) ) {
            for ( int i = 0; i < str.length(); i++ ) {
                c = str.charAt( i );
                //영문이거나 특수 문자 일경우.
                // 0x21:33, 0x7e:126, 0x20:32, 0x3000:12288
                if ( (int)c >= 33 && (int)c <= 126 ) {
                    c += 0xfee0;
                }
                else if ( (int)c == 32 ) {
                    c = 0x3000;
                }

                strBuf.append( c );
            }
        }
        return strBuf.toString();
    }

    /**
     * <br>
     * 
     * @param 	String source :
     * @return 	String[]
     */
    public static String[] printMobNo( String str )
    {

        int strLen = str.length();

        int mK = 0;
        //    전화번호 서울일 경우만 2자리 이다.
        if ( str.substring( 0, 2 ).equals( "02" ) ) {
            mK = 2;
        }
        else {
            mK = 3;
        }

        String mobNo[] = new String[3];

        if ( ( mK == 2 && str.length() > 9 ) || ( mK == 3 && str.length() > 10 ) ) {

            if ( strLen == 12 ) {
                mobNo[0] = str.substring( 0, mK );
                mobNo[1] = str.substring( strLen - 8, strLen - 4 );
                mobNo[2] = str.substring( strLen - 4, strLen );
            }
            else {
                mobNo[0] = str.substring( 0, mK );
                mobNo[1] = str.substring( mK, ( mK + 4 ) );
                mobNo[2] = str.substring( ( mK + 4 ), ( mK + 4 ) + 4 );
            }

        }
        else {
            mobNo[0] = str.substring( 0, mK );
            mobNo[1] = str.substring( mK, ( strLen - 4 ) );
            mobNo[2] = str.substring( ( strLen - 4 ), strLen );
        }

        return mobNo;

    }

    

    /**
     * 두 문자열이 같은지를 판단<br>
     * 
     * @param	String org		비교 문자열 1
     * @param	String des		비교 문자열 1
     * @param	boolean ignoreCase 대소문자구분여부(true:구분안함, false:구분함)
     * @return 	boolean
     */
    public static boolean compare( String org, String des, boolean ignoreCase )
    {
        boolean defaultVal = false;

        String org_str = TeddyUTIL.trim( org );
        String des_str = TeddyUTIL.trim( des );

        if ( ignoreCase ) {
            //소문자로 비교한다.
            if ( TeddyUTIL.trim( org_str ).toLowerCase().compareTo( des_str.toLowerCase() ) == 0 ) defaultVal = true;
        }
        else {
            if ( org_str.compareTo( des_str ) == 0 ) defaultVal = true;
        }
        return defaultVal;
    }

    

    /**
     * <br>
     * 
     * @param 	String telno :
     * @param 	String token :
     * @return 	String
     */
    public static String getTelNoFormatStr( String telno, String token )
    {
        String tel[] = getTelNo( telno );

        if ( "".equals( tel[0] ) )
            return " ";
        else return ( tel[0] + token + tel[1] + token + tel[2] );
    }

    /**
     * <br>
     * 
     * @param 	String source :
     * @return 	String[]
     */
    public static String[] getTelNo( String str )
    {

        String strTel = getMobileNo( str );

        String mobNo[] = new String[3];

        if ( strTel.length() > 7 ) {
            int strLen = strTel.length();
            int mK = 0;
            //    전화번호 서울일 경우만 2자리 이다.
            if ( str.substring( 0, 2 ).equals( "02" ) )
                mK = 2;
            else mK = 3;

            if ( ( mK == 2 && strLen > 9 ) || ( mK == 3 && strLen > 10 ) ) {
                if ( strLen == 12 ) {
                    mobNo[0] = strTel.substring( 0, mK ); //0,4
                    mobNo[1] = strTel.substring( strLen - 8, strLen - 4 ); //4,8
                    mobNo[2] = strTel.substring( strLen - 4, strLen ); //8,12
                }
                else {
                    mobNo[0] = strTel.substring( 0, mK );
                    mobNo[1] = strTel.substring( mK, ( mK + 4 ) );
                    mobNo[2] = strTel.substring( ( mK + 4 ), ( mK + 4 ) + 4 );
                }
            }
            else {
                mobNo[0] = strTel.substring( 0, mK );
                mobNo[1] = strTel.substring( mK, ( strLen - 4 ) );
                mobNo[2] = strTel.substring( ( strLen - 4 ), strLen );
            }
        }
        else {
            mobNo[0] = "";
            mobNo[1] = "";
            mobNo[2] = "";
        }

        return mobNo;
    }

    /**
     * 핸드폰/전화 번호 출력<br>
     * 
     * @param 	String source :
     * @return 	String
     */
    public static String getMobileNo( String source )
    {

        //String telNum = "0123456789 ０１２３４５６７８９　";

        //String before="0123456789 ";
        //String next="０１２３４５６７８９　";

        String telNum = "0123456789０１２３４５６７８９";
        String before = "0123456789";
        String next = "０１２３４５６７８９";
        String returnValue = "";

        for ( int i = 0; i < source.length(); i++ ) {
            //전반각 숫자만 인식한다.
            if ( telNum.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                // 전각 숫자가 들오온다면 반각숫자로 바꾸어준다.
                if ( next.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                    returnValue += before.substring( next.indexOf( source.substring( i, i + 1 ) ), next.indexOf( source.substring( i, i + 1 ) ) + 1 );
                }
                else {
                    //반각일때는 더 숫자일 경우는 더한다.
                    returnValue += source.substring( i, i + 1 );
                }
            }
        }
        return returnValue;
    }

    /**
     * 문자열을 지정된 길이 만큼 자른뒤 오른쪽 끝에 지정된 기호를 삽입한다.
     * ex]
     * 	getCutString("1234567890", 6) -> return "123456"
     *  getCutString("1234567890", 6, "...") -> return "123456..."
     * @param str
     * @param len
     * @return
     */
    public static String getCutString( String str, int len )
    {
        return getCutString( str, len, "" );
    }

    public static String getCutString( String str, int len, String mark )
    {
        String tmp = "";
        if ( str.length() > len ) {
            tmp = str.substring( 0, len ) + mark;
        }
        else {
            tmp = str;
        }
        return tmp;
    }

    /**
     * 전자 스페이스를 trim해서 반환한다.<br>
     * 
     * @param 	String str :
     * @return 	String
     */
    public static String sealTrim( String str )
    {
        //boolean isStart = false;
        //StringBuffer sb = new StringBuffer();

        // while ( "　".startsWith( str ) ) {
        //     str = str.substring( 2 );
        // }
        /*
         while ( "　".endsWith( str ) ) {
         str = str.substring( 0, str.length() - 2 );
         }
         */

        str = str.replace( '　', ' ' );
        str = str.trim();

        return str;
    }

    /**
     * 년월(YYYYMM), 년월일(YYYYMMDD) 사이에 '/'를 첨가한다.<br>
     * 
     * @param 	String str :  YYYYMMDD or YYYYMM 
     * @return 	String
     */
    public static String dateSlashYM( String str )
    {

        String temp = null;
        int len = str.length();

        if ( len == 0 || len <= 4 ) {
            return str;
        }
        else if ( len >= 6 && len < 8 ) {
            temp = str.substring( 0, 4 ) + "/" + str.substring( 4, 6 );
            return temp;
        }
        else if ( len == 8 ) {
            temp = str.substring( 0, 4 ) + "/" + str.substring( 4, 6 ) + "/" + str.substring( 6, 8 );
            return temp;
        }
        else if ( ( str.equals( "00000000" ) ) || ( str.equals( "       0" ) ) || ( str.trim() ).equals( "" ) ) {
            return "";
        }
        else {
            return "";
        }
    }
    
    /**
     * 년월(YYYYMM), 년월일(YYYYMMDD) 사이에 '.'를 첨가한다.<br>
     * 
     * @param 	String str :  YYYYMMDD or YYYYMM 
     * @return 	String
     */
    public static String dateDotYM( String str )
    {

        String temp = null;
        int len = str.length();

        if ( len == 0 || len <= 4 ) {
            return str;
        }
        else if ( len >= 6 && len < 8 ) {
            temp = str.substring( 0, 4 ) + "." + str.substring( 4, 6 );
            return temp;
        }
        else if ( len == 8 ) {
            temp = str.substring( 0, 4 ) + "." + str.substring( 4, 6 ) + "." + str.substring( 6, 8 );
            return temp;
        }
        else if ( ( str.equals( "00000000" ) ) || ( str.equals( "       0" ) ) || ( str.trim() ).equals( "" ) ) {
            return "";
        }
        else {
            return "";
        }
    }
    
    /**
     * 년월(YYMM), 년월일(YYMMDD) 사이에 '.'를 첨가한다.<br>
     * YY.MM 또는 YY.MM.DD 형식으로 반환
     * 
     * @param 	String str :  YYMMDD or YYMM 
     * @return 	String
     */
    public static String dateDotYM2( String str )
    {

        String temp = null;
        int len = str.length();

        if ( len == 0 || len <= 2 ) {
            return str;
        }
        else if ( len >= 4 && len < 6 ) {
            temp = str.substring( 0, 2 ) + "." + str.substring( 2, 4 );
            return temp;
        }
        else if ( len == 6 ) {
            temp = str.substring( 0, 2 ) + "." + str.substring( 2, 4 ) + "." + str.substring( 4, 6 );
            return temp;
        }
        else if ( ( str.equals( "00000000" ) ) || ( str.equals( "       0" ) ) || ( str.trim() ).equals( "" ) ) {
            return "";
        }
        else {
            return "";
        }
    }
    
    public static String timeKorean( String time )
    {
        int len = time.length();

        if ( len < 4 ) {
            return time;
        }
        else if ( len == 4 ) {
            return time.substring( 0, 2 ) + "시" + time.substring( 2, 4 ) + "분";
        }
        else if ( len <= 6 ) {
            return time.substring( 0, 2 ) + "시" + time.substring( 2, 4 ) + "분" + time.substring( 4 ) + "초";
        }
        else {
            return time;
        }
    }

    /**
     * 년월(YYYYMM), 년월일(YYYYMMDD) 사이에 delimeter를 첨가한다.<br>
     * 
     * @param   String str :  YYYYMMDD or YYYYMM
     * @param   delimiter   연월일 사이에 들어갈 delimter
     * @return  String
     */
    public static String dateDelimiter( String str, String delimiter )
    {

        String temp = null;
        int len = str.length();

        if ( len == 0 || len < 4 || len == 5 ) {
            return str;
        }
        else if ( len == 4 ) {
            temp = str.substring( 0, 2 ) + delimiter + str.substring( 2, 4 );
            return temp;
        }
        else if ( len >= 6 && len < 8 ) {
            temp = str.substring( 0, 4 ) + delimiter + str.substring( 4, 6 );
            return temp;
        }
        else if ( len == 8 ) {
            temp = str.substring( 0, 4 ) + delimiter + str.substring( 4, 6 ) + delimiter + str.substring( 6, 8 );
            return temp;
        }
        else if ( ( str.equals( "00000000" ) ) || ( str.equals( "       0" ) ) || ( str.trim() ).equals( "" ) ) {
            return "";
        }
        else {
            return "";
        }
    }

    /**
     * 년월(YYYYMM), 년월일(YYYYMMDD) 사이에 '년 월 일'를 첨가한다.<br>
     * 
     * @param   String str :  YYYYMMDD or YYYYMM 
     * @return  String
     */
    public static String dateKoreanYM( String str )
    {
        String temp = null;
        int len = str.length();

        if ( len == 0 || len <= 4 ) {
            return str;
        }
        else if ( len >= 6 && len < 8 ) {
            temp = str.substring( 0, 4 ) + "년 " + str.substring( 4, 6 ) + "월";
            return temp;
        }
        else if ( len == 8 ) {
            return str.substring( 0, 4 ) + "년 " + str.substring( 4, 6 ) + "월 " + str.substring( 6, 8 ) + "일";
        }
        else {
            return "";
        }
    }

    /**
     * flash에서 오는 데이터 중 undefined 로 오는 경우가 발생하므로
     * 서버단에서 제거한다.
     * 
     * @param 	String str
     * @return 	String
     */
    public static String delUndefined( String str )
    {
        return replace( str.trim(), "undefined" );
    }

    /**
     * <br>
     * 
     * @param 	String str :
     * @return 	Boolean
     */
    public static boolean isNull( String str )
    {
        if ( str == null || str.trim().length() == 0 ) return true;
        if ( str.toUpperCase().equals( "NULL" ) ) return true;
        return false;
    }

    

    /*
     * Title 데이터의 길이를 입력길이 만큼 자른다.
     * @param 	String str : Title String
     * @param 	int    len : Title로 보여줄 length  
     * @return 	String 
     */
    public static String getTitle( String s, int len )
    {
        if ( len < 5 ) return s;

        s = s.trim();
        if ( s == null ) return null;
        if ( s.getBytes().length <= len ) return s;

        String s2;

        s2 = new String( s.getBytes(), 0, len - 3 );

        if ( s2.length() == 0 ) s2 = new String( s.getBytes(), 0, len - 3 - 1 ) + " ";

        return s2 + " ...";
    }

   

    /**
     * 비밀번호찾기 힌트 부분 전각 글자를 반각글자로 바꾼다<br>
     * 
     * @param 	String str :
     * @return 	String
     */
    public static String getPwdhintans( String source )
    {

        String before = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*-_=+/?., ";
        String next = "０１２３４５６７８９ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ～！＠＃＄％＾＆＊－＿＝＋／？．，　";

        String returnValue = "";

        for ( int i = 0; i < source.length(); i++ ) {

            // 전각 숫자가 들오온다면    반각숫자로 바꾸어준다.
            if ( next.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                returnValue += before.substring( next.indexOf( source.substring( i, i + 1 ) ), next.indexOf( source.substring( i, i + 1 ) ) + 1 );
            }
            else {
                //반각일때는 더 숫자일 경우는 더한다.
                returnValue += source.substring( i, i + 1 );
            }

        }
        return returnValue;
    }

    /**
     * 입력된 문자열에 HTML tag가 포함되어 있는지 여부를 넘긴다.
     * 허용된 HTML tag 인경우에는 무시한다.
     *
     * 허용HTML Tag : <br>, <br/>, <b>, </b>
     * 
     * true : HTML tag 포함
     * false : HTML tag 미포함 또는 허용된 HTML tag 포함
     * 
     * @param str
     * @return 
     */
    public static boolean isLimitHtml( String str )
    {
        boolean result = false;

        boolean flag = true;

        int index = -1;
        String[] permitStr = { "<br>", "<br/>", "<b>", "</b>" };
        if ( str == null || "".equals( str ) ) { return result; }

        String temp = "";

        while ( flag ) {
            int len = str.length();
            index = str.toLowerCase().indexOf( "<" );
            if ( index > -1 ) {
                boolean flag2 = false;
                for ( int i = 0; i < permitStr.length; i++ ) {
                    if ( index + permitStr[i].length() <= len ) {
                        temp = str.substring( index, index + permitStr[i].length() );
                        if ( permitStr[i].equals( temp.toLowerCase() ) ) {
                            flag2 = true;
                            break;
                        }
                    }
                }

                if ( flag2 ) {
                    str = str.substring( index + 1 );
                }
                else {
                    flag = false;
                    result = true;
                }
            }
            else {
                flag = false;
            }
        }

        return result;
    }
    
    
    /**
     * 입력된 문자열에서  HTML tag를 제거한다.
     * 
     * @param str
     * @return str
     */
    public static String removeHtml( String str )
    {
    	if(str != null && !str.equals("")){    	 		
	    	str = str.replaceAll("[<][a-z|A-Z|/](.|\n)*?[>]", "");
	    	
	    	//문장 마지막에 닫히지 않은 태그도 제거하기 위해 추가(OCB 웹진용)
	    	if(str.lastIndexOf("<")>-1)
	    	{
	    		str = str.substring(0,str.lastIndexOf("<"));
	    	}
    	}
    	 
    	return str;
    }

    /**
     * 입력된 문자열에 email정보가 들어있는지 여부를 확인한다.
     *  '@' 가 들어가있을 경우 true 처리
     * 
     * true : '@' 포함
     * false : '@' 미포함
     * 
     * @param str
     * @return 
     */
    public static boolean isLimitEmail( String str )
    {
        boolean result = false;
        if ( str == null || "".equals( str ) ) { return result; }
        if ( str.indexOf( "@" ) > -1 ) {
            result = true;
        }
        return result;
    }

    /**
     * 입력된 문자열에 URL 이 포함되어 있는지 여부를 넘긴다.
     * '.' 문자 앞뒤 문자를 검사하여 공백이 있을 경우만 예외로 처리한다.
     * 그 외 '.' 앞뒤에 문자가 존재하면 URL이 포함된 것으로 처리한다.
     *     예)
     *         안녕하세요.반갑습니다  => true
     *         안녕하세요. 반갑습니다  => false
     *         
     * true : URL 포함
     * false : URL 미포함
     * 
     * @param str
     * @return 
     */
    public static boolean isLimitDomain( String str )
    {
        boolean result = false;
        boolean flag = true;
        if ( str == null || "".equals( str ) ) {}
        int index = -1;

        String temp = "";
        String temp2 = "";
        while ( flag ) {
            index = str.indexOf( "." );
            int len = str.length();
            if ( index > -1 ) {
                if ( index > 0 && index < len - 1 ) {
                    temp = str.substring( index - 1, index );
                    temp2 = str.substring( index + 1, index + 2 );
                    if ( !" ".equals( temp ) && !" ".equals( temp2 ) && !"\n".equals( temp ) && !"\n".equals( temp2 ) && !"\r".equals( temp ) && !"\r".equals( temp2 ) ) {
                        result = true;
                        flag = false;
                    }
                    else {
                        str = str.substring( index + 1 );
                    }
                }
                else if ( index < len - 1 ) {
                    str = str.substring( index + 1 );
                }
                else {
                    flag = false;
                }
            }
            else {
                flag = false;
            }
        }
        return result;
    }

    /**
     * 입력된 문자열(str)에 허용도메인 목록(permitDomain)을 제외한 도메인이 있는지 검사한다.
     * 먼저 Protocol을 검사하여 존재할 경우 Protocol 다음에 있는 도메인을
     * 입력된 허용도메인 목록과 비교하여 결과를 Return 한다.
     * 
     * true : 미허용된 Domain 포함
     * false : Domain 미포함 또는 허용된 Doamin 포함
     * 
     * @param str, permitDomain
     * @return 
     */
    public static boolean isLimitDomain( String str, String[] permitDomain )
    {
        boolean result = false;
        int len = 0;
        if ( permitDomain == null || permitDomain.length < 1 ) {
            permitDomain = new String[] { "www.hyundaicard.com" };
        }
        if ( isLimitProtocol( str ) ) {
            boolean flag = false;
            len = str.length();
            int index = str.toLowerCase().indexOf( "//" );
            String temp = "";
            if ( index > -1 && ( index + 2 ) < len ) {
                temp = str.substring( index + 2 );
                if ( temp.indexOf( "/" ) > 0 ) {
                    temp = temp.substring( 0, temp.indexOf( "/" ) );
                }
            }

            if ( !"".equals( temp ) ) {
                str = temp;
            }

            for ( int i = 0; i < permitDomain.length; i++ ) {
                if ( str.toLowerCase().indexOf( permitDomain[i] ) > -1 ) {
                    flag = true;
                }
            }

            if ( !flag ) {
                result = true;
            }
        }
        return result;
    }

    
    /**
     * 입력된 문자열에 제한된 Protocol 문자열이 포함되어 있는지 여부를 넘긴다.
     * 
     * 제한된 Protocol : 'http:', 'https:', 'telnet:','ftp:','mms:','mailto'
     * 
     * true : protocol 포함
     * false : protocol 미포함
     * 
     * @param str
     * @return 
     */
    public static boolean isLimitProtocol( String str )
    {
        boolean result = false;
        String[] limitStr = { "http:", "https:", "telnet:", "ftp:", "mms:", "mailto" };
        if ( str == null || "".equals( str ) ) { return result; }

        for ( int i = 0; i < limitStr.length; i++ ) {
            if ( str.toLowerCase().indexOf( limitStr[i] ) > -1 ) {
                result = true;
                break;
            }
        }

        return result;
    }


    /**
     * val문장에 str문자가 있는지 존재여부확인
     * @param val
     * @param Str
     * @return
     */
    public static boolean isStrLast( String val, String str )
    {
        boolean bRtn = false;
        int s = val.lastIndexOf( str );
        if ( s + 1 == val.length() ) {
            bRtn = true;
        }
        return bRtn;
    }
    
    /**
     * val문장에 마지막 문자에 str 이 있는지 확인, 대소문자 모두 체크
     * @param val
     * @param Str
     * @return
     */
    public static boolean isStrLast_all_case( String val, String str )
    {
    	boolean bRtn = false;
    	try {	
    		if( val == null || val.length() ==0 || str == null || str.length() == 0)
    			return bRtn;
    		
	    	val = val.toUpperCase();
	    	str = str.toUpperCase();
	        
	    	if( val.endsWith(str)) {
	    		bRtn = true;
	    	}
    	} catch (Exception e ) {
    		System.out.println ( e.toString());
    		return false;
    	}
        return bRtn;
    }

    
    /**
     * 문자열을 "bar"를 기준으로 분리하여 문자열 배열에 할당
     * @param bar : 분리할 문자열
     * @param sRow : 원본 문자열
     * @return
     */
    public static String[] divideField( String bar, String sRow )
    {
        String sTmpStr = "";

        if ( ( sRow.substring( ( sRow.length() - 1 ), sRow.length() ) ).equals( bar ) ) {
            sRow = sRow + " ";
        }

        for ( int i = 0; i < sRow.length(); i++ ) {
            if ( i < sRow.length() ) {
                if ( ( ( sRow.substring( i, i + 1 ) ).equals( "|" ) ) && ( ( sRow.substring( i + 1, i + 2 ) ).equals( bar ) ) ) {
                    sTmpStr = sTmpStr + sRow.substring( i, i + 1 ) + " ";
                }
                else {
                    sTmpStr = sTmpStr + sRow.substring( i, i + 1 );
                }
            }
        }
        sRow = sTmpStr;
        StringTokenizer stValue = new StringTokenizer( sRow, bar );
        String[] sRtnValue = new String[stValue.countTokens()];

        for ( int i = 0; i < sRtnValue.length; i++ ) {
            sRtnValue[i] = stValue.nextToken();
        }

        return sRtnValue;
    }
	
	
	/**
     * ORACLE CLOB 타입의 데이터를 String으로 변환
     * 
     * @param inp clob 데이터
     * @return string 데이터
     * 
     * @author epHong@hanafos.com
     * 
     */
	public static String clob2String(Clob clob) throws SQLException, IOException {
        if(clob == null) return "" ;
        
        Reader reader = clob.getCharacterStream();
        int size = (int)clob.length();
        
        char[] contents = new char[size];
        
        reader.read(contents, 0, size);
        reader.close();
        
        return new String(contents);
    }
	
	
	
	
	/**
	 * Function : getServerName
	 * Description : 서버 도메인 정보 읽기
	 * Parameter
	 * - req : String Url
	 */
	public static String getServerName(String url) {
	    String result = url;

	    try{
		    result = result.substring(result.indexOf("://")+3);
		    result = result.substring(0, result.indexOf("/"));
	
		    if (result.indexOf(":") > -1) {
		        result = result.substring(0, result.indexOf(":"));
		    }
	    }catch(Exception ex){
	    	return url;
	    }

	    return result;
	}
}
