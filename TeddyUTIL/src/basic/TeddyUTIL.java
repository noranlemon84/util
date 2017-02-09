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
     * ���ڿ��� ���̸� �˾Ƴ���.
     * ���ڿ��� null �� ��쿡�� 0�� �����ϵ��� �Ѵ�.
     * 
     * @param String str : ���ڿ�
     * @return int : ���ڿ� ����
     */
    public static int length( String str )
    {
        return length( str, 0 );
    }

    /**
     * ���ڿ��� ���̸� �˾Ƴ���.
     * �ι�° ���ڷ� �־��� ���̿� ���Ͽ�,
     * �־��� ���ڿ��� ���̿��� �� ������ ���� �����Ѵ�.
     * ���ڿ��� null �� ���� 0���� �����Ͽ�, -length ���� �����Ѵ�.
     * 
     * @param String str : �񱳴�� ���ڿ�
     * @param int : �񱳴�� ����
     * @return int : ���ڿ� ���� - �񱳴�� ����
     */
    public static int length( String str, int length )
    {
        if ( str == null ) { return -length; }
        return str.trim().length() - length;
    }


    /**
     * pattern�� ����Ͽ� ���ڿ��� �ش� ���Ͽ� ��Ī�Ǵ����� �˾Ƴ���.
     * 
     * @param 	String patternStr : ��� ���� ���ڿ� 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ treu, ���н� false ��ȯ
     */
    private static boolean basicMatch( String patternStr, String dataStr )
    {
        if ( dataStr == null || dataStr.length() <= 0 ) return false;
        Pattern pattern = Pattern.compile( patternStr );
        Matcher matcher = pattern.matcher( dataStr );
        return matcher.matches();
    }

    /**
     * pattern�� ����Ͽ� Ư�� ���ڿ��� �ش� �������� ��ȯ��.
     * 
     * @param 	String patternStr : ����� ���ڿ� 
     * @param 	String dataStr : ��� ���ڿ�
     * @param 	String dataStr : ����� ���ڿ�
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
     * pattern�� ����Ͽ� ���ڿ��� ������ ���ڷθ� ���յǾ� �ִ����� Ȯ��.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ true, ���н� false ��ȯ
     */
    public static boolean isNumber( String dataStr )
    {
        return basicMatch( PATTERN_NUMBER, dataStr );
    }

    /**
     * pattern�� ����Ͽ� ���ڿ��� ������ (+|-)�� ������ ���ڷθ� ���յǾ� �ִ����� Ȯ��.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ true, ���н� false ��ȯ
     */
    public static boolean isSignedNumber( String dataStr )
    {
        return basicMatch( PATTERN_SIGNEDNUMBER, dataStr );
    }

    /**
     * pattern�� ����Ͽ� ���ڿ��� ������ �������ڷθ� ���յǾ� �ִ����� Ȯ��.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ true, ���н� false ��ȯ
     */
    public static boolean isAlpha( String dataStr )
    {
        return basicMatch( PATTERN_ALPHA, dataStr );
    }

    /**
     * pattern�� ����Ͽ� ���ڿ��� ������ ���ڿ� �������ڷθ� ���յǾ� �ִ����� Ȯ��.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ true, ���н� false ��ȯ
     */
    public static boolean isNumberAlpha( String dataStr )
    {
        return basicMatch( PATTERN_NUMBERALPHA, dataStr );
    }

    /**
     * pattern�� ����Ͽ� ���ڿ��� ������ email ���信 �´����� �˾ƺ��� �Լ�.<br>
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	boolean : ������ true, ���н� false ��ȯ
     */
    public static boolean isEmail( String email )
    {
        if ( email == null ) { return false; }
        if ( email.trim().length() <= 0 ) { return false; }
        if ( email.indexOf( " " ) > 0 ) { return false; }

        return ( !email.matches( PATTERN_EMAIL1 ) && email.matches( PATTERN_EMAIL2 ) );
    }

    
    //---------------------------------------------
    // type ��ȯ �Լ� ����
    //---------------------------------------------

    /**
     * ���ڿ��� integer �� �ٲ۴�.
     * ��ȯ�� ������ ���, 0�� �����Ѵ�.
     * 
     * @param String val : ���ڿ�
     * @return int
     */
    public static int strToInt( String val )
    {
        return strToInt( val, 0 );
    }

    /**
     * ���ڿ��� ���ڷ� ��ȯ�Ͽ� integer ���� �����Ѵ�.<br>
     * Oracle �� ivl() �Լ��� ����ߴ�. ���ڿ��� �Ľ��Ͽ� integer �� �����Ѵ�.
     * ��ȯ�� �����ϸ�, rep ������ �����Ѵ�.
     * 
     * @param String val ���ڿ�
     * @param int rep ��ȯ���н� ��ü��
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
     * ���ڿ��� long �� �ٲ۴�.<br>
     * ��ȯ�� ������ ���, 0L�� �����Ѵ�.
     * 
     * @param String val ���ڿ�
     * @return long
     */
    public static long strToLong( String val )
    {
        return strToLong( val, 0L );
    }

    /**
     * ���ڿ��� ���ڷ� ��ȯ�Ͽ� long ���� �����Ѵ�.<br> 
     * ���н� 0L�� �����Ѵ�.
     * 
     * @param String val ���ڿ�
     * @param long rep ��ȯ���н� ��ü��
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
     * ���ڿ��� �Ǽ�(float)�� ��ȯ�Ѵ�.<br>
     * ��ȯ ���н� 0.0F �� �����Ѵ�.
     * 
     * @param String val ��ȯ��� ���ڿ�
     * @return float
     */
    public static float strToFloat( String val )
    {
        return strToFloat( val, 0.0F );
    }

    /**
     * ���ڿ��� �Ǽ�(float)�� ��ȯ�Ѵ�.<br>
     * ��ȯ ���� �� rep ���� �����Ѵ�.
     * 
     * @param String val ��ȯ��� ���ڿ�
     * @param float rep ��ȯ���н� ���ϰ� 
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
     * ���ڿ��� �Ǽ�(double)�� ��ȯ�Ѵ�.<br>
     * ��ȯ ���н� 0.0 �� �����Ѵ�.
     * 
     * @param String val ��ȯ��� ���ڿ�
     * @return double
     */
    public static double strToDouble( String val )
    {
        return strToDouble( val, 0.0 );
    }

    /**
     * ���ڿ��� �Ǽ�(double)�� ��ȯ�Ѵ�.<br>
     * ��ȯ ���н� rep �� �����Ѵ�.
     * 
     * @param String val ��ȯ��� ���ڿ�
     * @param float rep ��ȯ���н� ���ϰ� 
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
     * ���ڿ��� �ִ��� Ȯ���Ѵ�.<br>
     * Oracle nvl() �� ����� ����� �Ѵ�.
     * �ڹٿ��� "null" �̶�� ������ ���� �����ϱ� ���� �Լ�.
     * null �� ���, "" (��Ʈ��)�� �����Ѵ�.
     * 
     * @param String val �񱳴�� ���ڿ�
     * @return String
     */
    public static String nvl( String val )
    {
        return nvl( val, "" );
    }

    /**
     * ���ڿ��� �ִ��� Ȯ���Ѵ�.<br>
     * Oracle nvl() �� ����� ����� �Ѵ�.
     * �ڹٿ��� "null" �̶�� ������ ���� �����ϱ� ���� �Լ�.
     * null �� ���, rep�� �����Ѵ�.
     * 
     * @param String val �񱳴�� ���ڿ�
     * @param String rep null �� ���, ��ü��
     * @return String
     */
    public static String nvl( String val, String rep )
    {
        return ( val == null ) ? rep : val;
    }

    /**
     * ���ڿ��� �ִ��� Ȯ���Ѵ�.<br>
     * Oracle nvl() �� ����� ����� �Ѵ�.
     * �ڹٿ��� "null" �̶�� ������ ���� �����ϱ� ���� �Լ�.<br>
     * 
     * null �� ���, rep�� �����Ѵ�. �߰������� ����ڿ��� trim() �� ��,
     * 0 ���� �۰ų� ���� ��쿡�� rep �� �����Ѵ�.
     * 
     * @param String val �񱳴�� ���ڿ�
     * @param String rep null �� ���, ��ü��
     * @return String
     */
    public static String nvl2( String val, String rep )
    {
        if ( val == null ) { return rep; }
        return ( val.trim().length() <= 0 ) ? rep : val.trim();
    }

    /**
     * int�� String���� ��ȯ
     * 
     * @param int num ��ȯ���
     * @return String
     */
    public static String intToStr( int num )
    {
        return Integer.toString( num );
    }

    /**
     * float�� String���� ��ȯ
     * 
     * @param float num ��ȯ���
     * @return String
     */
    public static String floatToStr( float num )
    {
        return Float.toString( num );
    }

    /**
     * long�� String���� ��ȯ
     * 
     * @param long num ��ȯ���
     * @return String
     */
    public static String longToStr( long num )
    {
        return Long.toString( num );
    }

    /**
     * double�� String���� ��ȯ
     * 
     * @param double num ��ȯ���
     * @return String
     */
    public static String doubleToStr( double num )
    {
        return Double.toString( num );
    }

    // ��ü ��ȯ�� ����� �־߰���....(�� �Ⱦ��� �ҵ�...)
    /**
     * Integer ��ü�� String���� ��ȯ
     * null�� ��� �⺻������ ��ȯ
     * 
     * @param Integer num ��ȯ���
     * @return String
     */
    public static String intToStr( Integer num )
    {
        int defaultVal = 0;
        if ( num != null ) defaultVal = num.intValue();
        return intToStr( defaultVal );
    }

    /**
     * Float ��ü�� String���� ��ȯ
     * null�� ��� �⺻������ ��ȯ
     * 
     * @param Float num ��ȯ���
     * @return String
     */
    public static String floatToStr( Float num )
    {
        float defaultVal = 0.0F;
        if ( num != null ) defaultVal = num.floatValue();
        return floatToStr( defaultVal );
    }

    /**
     * Long ��ü�� String���� ��ȯ
     * null�� ��� �⺻������ ��ȯ
     * 
     * @param Long num ��ȯ���
     * @return String
     */
    public static String longToStr( Long num )
    {
        long defaultVal = 0L;
        if ( num != null ) defaultVal = num.longValue();
        return longToStr( defaultVal );
    }

    /**
     * Double ��ü�� String���� ��ȯ
     * null�� ��� �⺻������ ��ȯ
     * 
     * @param Double num ��ȯ���
     * @return String
     */
    public static String doubleToStr( Double num )
    {
        double defaultVal = 0.0D;
        if ( num != null ) defaultVal = num.doubleValue();
        return doubleToStr( defaultVal );
    }

    
    //---------------------------------------------
    // ���ڿ� ���� �Լ�1
    //---------------------------------------------

    /**
     * ���� ���ڿ��� �����ϴ� �Լ�.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	String
     */
    public static String deleteWhitespace( String dataStr )
    {
        return basicReplaceAll( PATTERN_WHITESPACE, dataStr, "" );
    }

    /**
     * Ư�� ���ڿ��� �����ϴ� �Լ�.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param 	String ch : ���� ��� ���ڿ�
     * @return 	String
     */
    public static String deleteChar( String dataStr, String ch )
    {
        return basicReplaceAll( ch, dataStr, "" );
    }

    /**
     * Ư�� ���ڿ����� �����ϴ� �Լ�.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param 	String[] chs : ���� ��� ���ڿ���
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
     * ��� ���ڿ����� Ư�� ���ڿ��� �ٸ� ���ڿ��� ��ü�ϴ� �Լ�.
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param 	String org : Ư�� ���ڿ�
     * @param	String des : ��ü ���ڿ�
     * @return 	String
     */
    public static String replace( String dataStr, String org, String des )
    {
        String patternStr = org;
        return basicReplaceAll( patternStr, dataStr, des );
    }

    /**
     * ��� ���ڿ����� Ư�� ���ڿ��� �ٸ� ���ڿ��� ��ü�ϴ� �Լ�.
     * ����Ʈ�� ��ü ���ڿ��� ""�� ����
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param 	String org : Ư�� ���ڿ�
     * @return 	String
     */
    public static String replace( String dataStr, String org )
    {
        return replace( dataStr, org, "" );
    }

    /**
     * ����ڿ����� ���鹮�� �����ϴ� �Լ�.
     * ����Ʈ�� ��ü ���ڿ��� ""�� ����
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	String
     */
    public static String replaceBlank( String dataStr )
    {
        return replace( trim( dataStr ), " ", "" );
    }

    /**
     * ���� ������� �����ϴ� �Լ�
     * 
     * @param 	String dataStr : ��� ���ڿ�
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
     * ������ ������� �����ϴ� �Լ�
     * 
     * @param 	String dataStr : ��� ���ڿ�
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
     * ���� ������� �����ϴ� �Լ�
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @return 	String
     */
    public static String trim( String dataStr )
    {
        return ltrim( rtrim( dataStr ) );
    }

    /**
     * ������ ���ڿ��� ���ڿ��� ���̸�ŭ�� ������ ���̸�ŭ Ư�� ���ڷ� ���ʺ��� ä��� �Լ�<br>
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param	int len	: Ư�����ڷ� ä�� �� ��ü ���ڿ��� length
     * @param 	char ch : Ư�� ����
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
     * ������ ���ڿ��� ���ڿ��� ���̸�ŭ�� ������ ���̸�ŭ Ư�� ���ڷ� �����ʺ��� ä��� �Լ�<br>
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param	int len	: Ư�����ڷ� ä�� �� ��ü ���ڿ��� length
     * @param 	char ch : Ư�� ����
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
     * ���ڿ��� �Ϻθ� �߶� �����Ѵ�.<br>
     * null �� ���� ���ϴ� ���̺��� ���� ��쵵 ó���Ѵ�.
     * 
     * @param String s ���ڿ�
     * @param int len ���ϴ� ����
     * @return String
     */
    public static String substring( String s, int len )
    {
        if ( s == null ) { return ""; }
        if ( s.length() < len ) { return s; }
        return s.substring( 0, len );
    }

    /**
     * ���ڿ��� �Ϻθ� �߶� �����Ѵ�.<br>
     * null �� ���� ���ϴ� ���̺��� ���� ��쵵 ó���Ѵ�.
     * bool�� true�̸� len���� ������ �ڸ���.
     * 
     * @param String s ���ڿ�
     * @param int len ���ϴ� ����
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
     * ���ڿ��� �Ϻθ� �߶� �����Ѵ�.<br>
     * null �� ���� ���ϴ� ���̺��� ���� ��쵵 ó���Ѵ�.
     * 
     * @param String s ���ڿ�
     * @param int len ���ϴ� ����
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
     * ���ڿ��� �Ϻθ� �߶� �����Ѵ�.<br>
     * null �� ���� ���ϴ� ���̺��� ���� ��쵵 ó���Ѵ�.
     * �κ� substring �߰�
     * 
     * @param String s ���ڿ�
     * @param int beginIndex ���۱���
     * @param int endIndex   �������
     * @return String
     */
    public static String substring( String s, int beginIndex, int endIndex )
    {
        if ( s == null ) { return ""; }

        if ( s.length() < endIndex ) { return s; }

        return s.substring( beginIndex, endIndex );
    }

    /**
     * ���ڿ� �߶� �����Ѵ�.<br>
     * �����ڿ� ���Ͽ� ���ϴ� ������ ���ڿ��� �����Ѵ�.
     * 
     * @param String s ���ڿ�
     * @param String g ������
     * @param int b ������ ������ 
     * @return String
     */
    public static String substring( String s, String g, int b )
    {
        if ( s == null ) { return ""; }

        String[] values = s.split( g );
        String rtlValue = "";
        for ( int x = 0; x < values.length; x++ ) {
            //System.out.println( "����(��) " + (x+1) + " : " + values[x] );
            if ( x == b ) {

                rtlValue = values[x];
                break;
            }
        }
        //return s.substring(beginIndex, endIndex);
        return rtlValue;
    }

    /**
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� ǥ���� ��� �ַ� ���ȴ�.(3�ڸ��� ���� ǥ��)<br>
     * 
     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param String str : ��� ���ڿ�
     * @return String
     */
    public static String makeDefaultMoneyFormat( String str )
    {
        return makeNumberFormat( str, FORMAT_MONEY_DEFAULT_FULL_LEN, FORMAT_MONEY_DEFAULT_DECI_LEN, true );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
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
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
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
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
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
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
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
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * ����ڰ� ���ڸ����� ������ ��� �����<br>
     * 
     * �ݾ״����� ǥ�õ�(3�ڸ��� ���� ǥ��)<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int total_len : ����ڰ� ������ �� �ڸ���(�����ڸ�)
     * @return String
     */
    public static String makeMoneyFormat( String str, int total_len )
    {
        return makeNumberFormat( str, total_len, 0, true );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ȭ����� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * ����ڰ� �Ѽ��ڸ��� �� �Ҽ��ڸ��� ������ ��� �����<br>
     * 
     * ���ڸ������� �Ҽ��ڸ����� �� �κ��� �����ڸ��� �ȴ�.<br>
     * 
     * �ݾ״����� ǥ�õ�(3�ڸ��� ���� ǥ��)<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int total_len : ����ڰ� ������ �� �ڸ���(�����ڸ�+�Ҽ��ڸ�)
     * @param int deci_len : ����ڰ� ������ �Ҽ� �ڸ���
     * @return String
     */
    public static String makeMoneyFormat( String str, int total_len, int deci_len )
    {
        return makeNumberFormat( str, total_len, deci_len, true );
    }

    /**
     * ��� ���ڿ��� ����Ʈ Decimal���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * ����Ʈ�� 6�ڸ�(����4+�Ҽ�2)�� ��� ����Ѵ�<br>
     * ����Ʈ�� �Ҽ��κ��� ����Ʈ �ڸ����� 0�� ǥ���Ѵ�
     * 
     * �Ķ������ string ���� Decimal ������ �������̾�� �Ѵ�<br>
     * ��, �Ҽ��κ��� ���Ե� �������̾�� �Ѵ�.<br>
     * (��)0012.05<br>
     * <br>
     * �ַ� host ������ ��ȯ�� ����Ѵ�.
     * 
     * @param String str : ��� ���ڿ�
     * @return String
     */
    public static String makeDefaultRateFormat( String str )
    {
        return makeDefaultRateFormat( str, FORMAT_RATE_DEFAULT_DECI_LEN );
    }

    /**
     * ��� ���ڿ��� ����Ʈ Decimal���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �Ҽ��κ� ���̸� ����ڰ� �����Ͽ� �����Ѵ�.<br>
     * ����Ʈ�� �Ҽ��κ��� ����Ʈ �ڸ����� 0�� ǥ���Ѵ�
     * 
     * �Ķ������ string ���� Decimal ������ �������̾�� �Ѵ�<br>
     * ��, �Ҽ��κ��� ���Ե� �������̾�� �Ѵ�.<br>
     * (��)0012.05<br>
     * <br>
     * �ַ� host ������ ��ȯ�� ����Ѵ�.
     * 
     * @param String str : ��� ���ڿ�
     * @param int deci_len : �Ҽ��κ� ����
     * @return String
     */
    public static String makeDefaultRateFormat( String str, int deci_len )
    {
        return makeDefaultRateFormat( str, deci_len != 0 ? deci_len : FORMAT_RATE_DEFAULT_DECI_LEN, true );
    }

    /**
     * ��� ���ڿ��� ����Ʈ Decimal���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �Ҽ��κ� ���̸� ����ڰ� �����Ͽ� �����Ѵ�.<br>
     * �Ҽ��к��� ��� 0�� ���� ������� ����ڰ� �������� ���θ� �Ǵ��Ѵ�.<br>
     * 
     * �Ķ������ string ���� Decimal ������ �������̾�� �Ѵ�<br>
     * ��, �Ҽ��κ��� ���Ե� �������̾�� �Ѵ�.<br>
     * (��)0012.05<br>
     * <br>
     * �ַ� host ������ ��ȯ�� ����Ѵ�.<br>
     * 
     * isShowDeciZero : true : 0012.00 -> 0012<br>
     * isShowDeciZero : false : 0012.00 -> 0012.00<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int deci_len : �Ҽ��κ� ����
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
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� �ƴ����� �������·� ǥ���� ��� �ַ� ����Ѵ�.<br>

     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param String str : ��� ���ڿ�
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
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� �ƴ����� �������·� ǥ���� ��� �ַ� ����Ѵ�.<br>

     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param int num : ��� int value
     * @return String
     */
    public static String makeDefaultNumberFormat( int num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.intToStr( num ) );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� �ƴ����� �������·� ǥ���� ��� �ַ� ����Ѵ�.<br>

     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param long num : ��� long value
     * @return String
     */
    public static String makeDefaultNumberFormat( long num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.longToStr( num ) );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� �ƴ����� �������·� ǥ���� ��� �ַ� ����Ѵ�.<br>

     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param float num : ��� float value
     * @return String
     */
    public static String makeDefaultNumberFormat( float num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.floatToStr( num ) );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * �޸��� �����Ͽ� ����13�ڸ�, �Ҽ�3�ڸ� �������� ǥ���Ѵ�.<br>
     * 
     * �ݾ״����� �ƴ����� �������·� ǥ���� ��� �ַ� ����Ѵ�.<br>

     * ���� �ڸ����� ������ �ʿ��� ��� ������ �Լ����� overroad�� �Լ��� ����ؼ�<br>
     * ���� ������ ���¸� ������ �־�� �Ѵ�.<br>
     * 
     * @param double num : ��� double value
     * @return String
     */
    public static String makeDefaultNumberFormat( double num )
    {
        return makeDefaultNumberFormat( TeddyUTIL.doubleToStr( num ) );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * 
     * ����ڰ� ���ڸ����� ������ ��� �����<br>
     * 
     * �ݾ״����� ǥ�õ����� ����<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int total_len : ����ڰ� ������ �� �ڸ���(�����ڸ�+�Ҽ��ڸ�)
     * @return String
     */
    public static String makeNumberFormat( String str, int total_len )
    {
        return makeNumberFormat( str, total_len, 0, false );
    }

    /**
     * ��� ���ڿ��� ����Ʈ ���� ǥ�÷� ��ȯ�ϴ� �Լ�<br>
     * 
     * ����ڰ� �Ѽ��ڸ��� �� �Ҽ��ڸ��� ������ ��� �����<br>
     * 
     * ���ڸ������� �Ҽ��ڸ����� �� �κ��� �����ڸ��� �ȴ�.<br>
     * �ݾ״����� ǥ�õ����� ����<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int total_len : ����ڰ� ������ �� �ڸ���(�����ڸ�+�Ҽ��ڸ�)
     * @param int deci_len : ����ڰ� ������ �Ҽ��ڸ���
     * @return String
     */
    public static String makeNumberFormat( String str, int total_len, int deci_len )
    {
        return makeNumberFormat( str, total_len, deci_len, false );
    }

    /**
     * ��� ���ڿ��� ȭ����� �Ǵ� ���ڴ����� ǥ���ϴ� ���� �Լ�<br>
     * 
     * ����ǥ�� ������ �־��� param�� �°� �����Ѵ�.<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param int total_len : ����ڰ� ������ �� �ڸ���(�����ڸ�+�Ҽ��ڸ�)
     * @param int deci_len : ����ڰ� ������ �Ҽ��ڸ���
     * @param boolean moneyType : true(ȭ�����ǥ��), false(���ڴ���ǥ��)
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
     * ��� ���ڿ��� ȭ����� �Ǵ� ���ڴ����� ǥ���ϴ� �Լ�<br>
     * 
     * Ư�� ���˿� �°� ���ڿ��� ǥ���ϴ� ���� �Լ�<br>
     * 
     * @param String str : ��� ���ڿ�
     * @param String format : ��� ���� ����
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
     * ��� ���ڿ��� �Ҽ��� 2�ڸ� �����ϴ� ȭ������� ����<br>
     * @param dVal
     * @return
     */
    public static String getDollarFormat( double dVal )
    {
        String displayForm = new DecimalFormat( "#,###,###,##0.00" ).format( dVal );
        return displayForm;
    }

    /**
     * ����ڹ�ȣ�� '-'�� ÷���Ѵ�.<br>
     * 
     * @param 	String str : ��� ���ڿ�
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
    // ���ڿ� ���� �Լ�2(with WEB)
    //---------------------------------------------

    /** 
     * ���๮�ڸ� <br/> �� �ٲ۴�.<br>
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
     * ������ String�߿��� "<", " ", "\n" �� <br> 
     * html �±׷� ��ȯ �ϴ� �Լ�
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
     * ������ String�߿��� ��ũ��Ʈ �� ���ڿ��� 
     * ��� ������ ���ڿ��� ��ȯ �ϴ� �Լ�
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
     * ������ String�߿��� Ư�� ���ڸ� �ڵ�� ��ȯ 
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
     * ������ ���ڿ��� Integer�� ��ȯ�ϴ� �Լ� <br>
     * 
     * @param 	String dataStr : ��� ���ڿ�
     * @param	int len	: Ư�����ڷ� ä�� �� ��ü ���ڿ��� length
     * @param 	char ch : Ư�� ����
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
     * �Էµ� ���ڿ��� ���ĺ��� ��� �ҹ��ڷ� ��ȯ
     * @param str
     * @return �ҹ���
     */
    public static String strToLowerCase( String str )
    {
        if ( isAlpha( PATTERN_ALPHA ) ) {
            str = str.toLowerCase();
        }
        return str;
    }

    /**
     * �Էµ� ���ڿ��� ���ĺ��� ��� �빮�ڷ� ��ȯ
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
     * �Է¹��� String�� �տ� ��û�� length��ŭ 0�� ä�� �����Ѵ�.
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
     * �������ڸ� �ݰ����ڷ� ��ȯ
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

                // Ư�������� ���
                if ( (int)c >= 65281 && (int)c <= 65374 ) {
                    c -= 0xfee0; // 0xfee0 = -65248
                }
                else if ( c == '��' ) { // space�ϰ��
                    c = 0x20; // 0x20 = 32
                }
                strBuf.append( c );
            }
        }

        return strBuf.toString();
    }

    /**
     * �ݰ����ڸ� �������ڷ� ��ȯ
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
                //�����̰ų� Ư�� ���� �ϰ��.
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
        //    ��ȭ��ȣ ������ ��츸 2�ڸ� �̴�.
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
     * �� ���ڿ��� �������� �Ǵ�<br>
     * 
     * @param	String org		�� ���ڿ� 1
     * @param	String des		�� ���ڿ� 1
     * @param	boolean ignoreCase ��ҹ��ڱ��п���(true:���о���, false:������)
     * @return 	boolean
     */
    public static boolean compare( String org, String des, boolean ignoreCase )
    {
        boolean defaultVal = false;

        String org_str = TeddyUTIL.trim( org );
        String des_str = TeddyUTIL.trim( des );

        if ( ignoreCase ) {
            //�ҹ��ڷ� ���Ѵ�.
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
            //    ��ȭ��ȣ ������ ��츸 2�ڸ� �̴�.
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
     * �ڵ���/��ȭ ��ȣ ���<br>
     * 
     * @param 	String source :
     * @return 	String
     */
    public static String getMobileNo( String source )
    {

        //String telNum = "0123456789 ����������������������";

        //String before="0123456789 ";
        //String next="����������������������";

        String telNum = "0123456789��������������������";
        String before = "0123456789";
        String next = "��������������������";
        String returnValue = "";

        for ( int i = 0; i < source.length(); i++ ) {
            //���ݰ� ���ڸ� �ν��Ѵ�.
            if ( telNum.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                // ���� ���ڰ� ����´ٸ� �ݰ����ڷ� �ٲپ��ش�.
                if ( next.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                    returnValue += before.substring( next.indexOf( source.substring( i, i + 1 ) ), next.indexOf( source.substring( i, i + 1 ) ) + 1 );
                }
                else {
                    //�ݰ��϶��� �� ������ ���� ���Ѵ�.
                    returnValue += source.substring( i, i + 1 );
                }
            }
        }
        return returnValue;
    }

    /**
     * ���ڿ��� ������ ���� ��ŭ �ڸ��� ������ ���� ������ ��ȣ�� �����Ѵ�.
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
     * ���� �����̽��� trim�ؼ� ��ȯ�Ѵ�.<br>
     * 
     * @param 	String str :
     * @return 	String
     */
    public static String sealTrim( String str )
    {
        //boolean isStart = false;
        //StringBuffer sb = new StringBuffer();

        // while ( "��".startsWith( str ) ) {
        //     str = str.substring( 2 );
        // }
        /*
         while ( "��".endsWith( str ) ) {
         str = str.substring( 0, str.length() - 2 );
         }
         */

        str = str.replace( '��', ' ' );
        str = str.trim();

        return str;
    }

    /**
     * ���(YYYYMM), �����(YYYYMMDD) ���̿� '/'�� ÷���Ѵ�.<br>
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
     * ���(YYYYMM), �����(YYYYMMDD) ���̿� '.'�� ÷���Ѵ�.<br>
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
     * ���(YYMM), �����(YYMMDD) ���̿� '.'�� ÷���Ѵ�.<br>
     * YY.MM �Ǵ� YY.MM.DD �������� ��ȯ
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
            return time.substring( 0, 2 ) + "��" + time.substring( 2, 4 ) + "��";
        }
        else if ( len <= 6 ) {
            return time.substring( 0, 2 ) + "��" + time.substring( 2, 4 ) + "��" + time.substring( 4 ) + "��";
        }
        else {
            return time;
        }
    }

    /**
     * ���(YYYYMM), �����(YYYYMMDD) ���̿� delimeter�� ÷���Ѵ�.<br>
     * 
     * @param   String str :  YYYYMMDD or YYYYMM
     * @param   delimiter   ������ ���̿� �� delimter
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
     * ���(YYYYMM), �����(YYYYMMDD) ���̿� '�� �� ��'�� ÷���Ѵ�.<br>
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
            temp = str.substring( 0, 4 ) + "�� " + str.substring( 4, 6 ) + "��";
            return temp;
        }
        else if ( len == 8 ) {
            return str.substring( 0, 4 ) + "�� " + str.substring( 4, 6 ) + "�� " + str.substring( 6, 8 ) + "��";
        }
        else {
            return "";
        }
    }

    /**
     * flash���� ���� ������ �� undefined �� ���� ��찡 �߻��ϹǷ�
     * �����ܿ��� �����Ѵ�.
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
     * Title �������� ���̸� �Է±��� ��ŭ �ڸ���.
     * @param 	String str : Title String
     * @param 	int    len : Title�� ������ length  
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
     * ��й�ȣã�� ��Ʈ �κ� ���� ���ڸ� �ݰ����ڷ� �ٲ۴�<br>
     * 
     * @param 	String str :
     * @return 	String
     */
    public static String getPwdhintans( String source )
    {

        String before = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~!@#$%^&*-_=+/?., ";
        String next = "��������������������������������������������������������£ãģţƣǣȣɣʣˣ̣ͣΣϣУѣңӣԣգ֣ףأ٣ڢ������������ޣ������ߣ�������������";

        String returnValue = "";

        for ( int i = 0; i < source.length(); i++ ) {

            // ���� ���ڰ� ����´ٸ�    �ݰ����ڷ� �ٲپ��ش�.
            if ( next.indexOf( source.substring( i, i + 1 ) ) > -1 ) {
                returnValue += before.substring( next.indexOf( source.substring( i, i + 1 ) ), next.indexOf( source.substring( i, i + 1 ) ) + 1 );
            }
            else {
                //�ݰ��϶��� �� ������ ���� ���Ѵ�.
                returnValue += source.substring( i, i + 1 );
            }

        }
        return returnValue;
    }

    /**
     * �Էµ� ���ڿ��� HTML tag�� ���ԵǾ� �ִ��� ���θ� �ѱ��.
     * ���� HTML tag �ΰ�쿡�� �����Ѵ�.
     *
     * ���HTML Tag : <br>, <br/>, <b>, </b>
     * 
     * true : HTML tag ����
     * false : HTML tag ������ �Ǵ� ���� HTML tag ����
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
     * �Էµ� ���ڿ�����  HTML tag�� �����Ѵ�.
     * 
     * @param str
     * @return str
     */
    public static String removeHtml( String str )
    {
    	if(str != null && !str.equals("")){    	 		
	    	str = str.replaceAll("[<][a-z|A-Z|/](.|\n)*?[>]", "");
	    	
	    	//���� �������� ������ ���� �±׵� �����ϱ� ���� �߰�(OCB ������)
	    	if(str.lastIndexOf("<")>-1)
	    	{
	    		str = str.substring(0,str.lastIndexOf("<"));
	    	}
    	}
    	 
    	return str;
    }

    /**
     * �Էµ� ���ڿ��� email������ ����ִ��� ���θ� Ȯ���Ѵ�.
     *  '@' �� ������ ��� true ó��
     * 
     * true : '@' ����
     * false : '@' ������
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
     * �Էµ� ���ڿ��� URL �� ���ԵǾ� �ִ��� ���θ� �ѱ��.
     * '.' ���� �յ� ���ڸ� �˻��Ͽ� ������ ���� ��츸 ���ܷ� ó���Ѵ�.
     * �� �� '.' �յڿ� ���ڰ� �����ϸ� URL�� ���Ե� ������ ó���Ѵ�.
     *     ��)
     *         �ȳ��ϼ���.�ݰ����ϴ�  => true
     *         �ȳ��ϼ���. �ݰ����ϴ�  => false
     *         
     * true : URL ����
     * false : URL ������
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
     * �Էµ� ���ڿ�(str)�� ��뵵���� ���(permitDomain)�� ������ �������� �ִ��� �˻��Ѵ�.
     * ���� Protocol�� �˻��Ͽ� ������ ��� Protocol ������ �ִ� ��������
     * �Էµ� ��뵵���� ��ϰ� ���Ͽ� ����� Return �Ѵ�.
     * 
     * true : ������ Domain ����
     * false : Domain ������ �Ǵ� ���� Doamin ����
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
     * �Էµ� ���ڿ��� ���ѵ� Protocol ���ڿ��� ���ԵǾ� �ִ��� ���θ� �ѱ��.
     * 
     * ���ѵ� Protocol : 'http:', 'https:', 'telnet:','ftp:','mms:','mailto'
     * 
     * true : protocol ����
     * false : protocol ������
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
     * val���忡 str���ڰ� �ִ��� ���翩��Ȯ��
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
     * val���忡 ������ ���ڿ� str �� �ִ��� Ȯ��, ��ҹ��� ��� üũ
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
     * ���ڿ��� "bar"�� �������� �и��Ͽ� ���ڿ� �迭�� �Ҵ�
     * @param bar : �и��� ���ڿ�
     * @param sRow : ���� ���ڿ�
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
     * ORACLE CLOB Ÿ���� �����͸� String���� ��ȯ
     * 
     * @param inp clob ������
     * @return string ������
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
	 * Description : ���� ������ ���� �б�
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
