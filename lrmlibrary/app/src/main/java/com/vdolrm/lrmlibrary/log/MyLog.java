package com.vdolrm.lrmlibrary.log;

import android.os.StrictMode;
import android.util.Log;

import com.vdolrm.lrmlibrary.BaseActivity;

public class MyLog {
    private MyLog() {

    }

    public static final boolean DEBUG = true;

    public static void d(String msg) {
        if (DEBUG) {
            Log.d("vdolibrary", msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    /**
     * 打印当前类、方法、行数、文件名等信息
     */
    public static String printBaseInfo( ){
        if( DEBUG){
            StringBuffer strBuffer = new StringBuffer( );
            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );

            strBuffer.append( "; class:" ).append( stackTrace[ 1 ].getClassName( ) )
                    .append( "; method:" ).append( stackTrace[ 1 ].getMethodName( ) )
                    .append("; number:").append( stackTrace[ 1 ].getLineNumber() )
                    .append( "; fileName:" ).append(stackTrace[1].getFileName());

            //println( strBuffer.toString( ) );
            return strBuffer.toString();
        }
        return "";
    }

    /**
     * 打印当前行数、文件名等信息
     */
    public static String printFileNameAndLinerNumber( ){
        if( DEBUG ){
            StringBuffer strBuffer = new StringBuffer( );
            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );

            strBuffer.append( "; fileName:" ).append( stackTrace[ 1 ].getFileName( ) )
                    .append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) );

           // println( strBuffer.toString( ) );
            return strBuffer.toString();
        }
        return "";
    }

    /**
     * 打印当前行数
     */
    public static int printLineNumber( ){
        if( DEBUG ){
            StringBuffer strBuffer = new StringBuffer( );
            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );

            strBuffer.append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) );

            //println( strBuffer.toString( ) );
            return stackTrace[ 1 ].getLineNumber( );
        }else{
            return 0;
        }
    }

    /**
     * 打印当前方法名
     */
    public static String printMethodName( ){
        if( DEBUG ){
            StringBuffer strBuffer = new StringBuffer( );
            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );

            strBuffer.append( "; number:" ).append( stackTrace[ 1 ].getMethodName( ) );

            //println( strBuffer.toString( ) );
            return strBuffer.toString();
        }
        return "";
    }

    /**
     * 打印当前行数、文件名等信息
     */
    public static String printFileNameAndLinerNumber( String printInfo ){
        if( null == printInfo || !DEBUG ){
            return "";
        }
        StringBuffer strBuffer = new StringBuffer( );
        StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );

        strBuffer.append( "; fileName:" ).append( stackTrace[ 1 ].getFileName( ) )
                .append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) ).append( "\n" )
                .append( ( null != printInfo ) ? printInfo : "" );

        //println( strBuffer.toString( ) );
        return strBuffer.toString();
    }



}

