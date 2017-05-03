package com.luculent.data.utils.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.model.BackBean;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @Description:http连接工具（okHttp3）
 * @Author:zhangy
 * @Since:2017年4月14日下午4:49:16
 */
public class OkHttpUtils {

    private final static Logger logger = LogManager.getLogger("run_long");

    private OkHttpUtils() {
	// TODO Auto-generated constructor stub
	throw new AssertionError();
    }

    private final static OkHttpClient client;


    static {
	client = new OkHttpClient.Builder()
		.connectTimeout(DataConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
		.writeTimeout(DataConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
		.readTimeout(DataConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
		.addInterceptor(new Interceptor() {
		    @Override
		    public Response intercept(Chain chain) throws IOException {
			// TODO Auto-generated method stub
			Request request = chain.request().newBuilder()
				.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 7.0; Windows NT 5.1) ")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("Connection", "close").build();
			return chain.proceed(request);
		    }
		}).build();
    }
    
    /**
     * 以get方式获取返回值
     * 
     * @param url
     * @return BackBean
     */
    public static BackBean getBeanContent(String url){
	return ConventionUtils.jsonToBackBean(getContent(url));
    }
    
    /**
     * 以get方式获取返回值
     * 
     * @param url
     * @param params 参数值
     * @return BackBean
     */
    public static BackBean getBeanContent(String url,String... params){
	return ConventionUtils.jsonToBackBean(getContent(url,params));
    }
    
    /**
     * 以get方式获取返回值
     * 
     * @param url
     * @param params 线程安全map
     * @return BackBean
     */
    public static BackBean getBeanContent(String url,ConcurrentHashMap<String,String> params){
	return ConventionUtils.jsonToBackBean(getStrContent(url,params));
    }

    /**
     * 以get方式获取返回值
     * 
     * @param url
     * @return
     */
    public static String getContent(String url) {
	return getStrContent(url, null);
    }

    /**
     * 以get方式获取返回值
     * 
     * @param url
     * @param params
     *            字符串
     * @return
     */
    public static String getContent(String url, String... params) {
	return getStrContent(url, ConventionUtils.toMap(params));
    }

    /**
     * 以get方式获取图片 返回图片uuid文件名
     * 
     * @param url
     * @return
     */
    public static String getImageDownLoad(String url) {
	if (logger.isDebugEnabled()) {
	    logger.debug("开始获取图片：" + url);
	}
	return getImageDownLoadContent(url);
    }

    /**
     * 以get方式获取验证码返回值
     * 
     * @param url
     * @return
     */
    public static String getCodeResult(String url) {
	if (logger.isDebugEnabled()) {
	    logger.debug("开始获取图片：" + url);
	}
	return getCodeCheckedStr(url);
    }

    public static String getStrContent(String url, Map<String, String> params) {
	if (logger.isDebugEnabled()) {
	    logger.debug("开始获取URL：" + url);
	}
	Request request = getRequest(url, params);
	Response response = null;
	try {
	    response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
		logger.error("获取URL：" + url+ "出错,错误代码为:"+response.code() );
		response.close();
		return null;
	    }
	    logger.debug("获取URL：" + url+" 成功");
	    return response.body().string();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    logger.error("开始获取URL：" + url+ "出错", e);
	    if(params == null){
		 return e.getMessage();
	    }
	    return null;
	   
	}finally {
	    try {
		response.close();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	    }
	}

    }

    private static String getImageDownLoadContent(String url) {
	Request request = getRequest(url, null);
	Response response = null;
	String uuid = UUIDBuilder.getUUID();
	try {
	    response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
		logger.error("获取图片：" + url+ "出错,错误代码为:"+response.code() );
		response.close();
		return null;
	    }
	    logger.debug("获取URL：" + url+" 成功");
	    InputStream input = response.body().byteStream();
	    OutputStream output = new FileOutputStream(new File(DataConstant.TEMP_PATH + uuid + DataConstant.IMG_TYPE));
	    IOUtils.copy(input, output);
	    output.flush();
	    return uuid;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    logger.error("获取图片：" + url+ "出错",e );
	    return e.getMessage();
	} catch (NullPointerException e) {
	    // TODO Auto-generated catch block
	    logger.error("获取图片：" + url+ "出错",e );
	    return e.getMessage();
	   
	}finally {
	    try {
		response.close();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    private static String getCodeCheckedStr(String url) {
	Request request = getRequest(url, null);
	BufferedImage bi = null;
	Response response = null;
	String res = "";
	try {
	    response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
		logger.error("获取图片：" + url+ "出错,错误代码为:"+response.code() );
		response.close();
		return null;
	    }
	    logger.debug("获取URL：" + url+" 成功");
	    InputStream input = response.body().byteStream();
	    bi = ImageIO.read(input);
	    Tesseract tessreact = new Tesseract();
	    tessreact.setDatapath(DataConstant.OCR_PATH);
	    tessreact.setLanguage("eng");
	    res = tessreact.doOCR(bi).replace("\n", "").replaceAll("\\s*", "");
	    return res;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    logger.error("获取图片：" + url+ "出错",e );
	    return null;
	} catch (TesseractException e) {
	    // TODO Auto-generated catch block
	    logger.error("解析图片：" + url+ "出错",e );
	    return null;
	} catch (NullPointerException e) {
	    // TODO Auto-generated catch block
	    logger.error("获取图片：" + url+ "出错",e );
	    return null;
	   
	}finally {
	    try {
		response.close();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
    }

    private static Request getRequest(String url, Map<String, String> params) {
	StringBuffer urlStr = new StringBuffer(url);
	if (params != null && params.size() != 0) {
	    for (Entry<String, String> param : params.entrySet()) {
		if(StringUtils.isNotEmpty(param.getValue())){
		    urlStr.append(DataConstant.URL_AND).append(param.getKey()).append(DataConstant.URL_EQUAL)
		    .append(param.getValue());
		}
		
	    }
	}
	okhttp3.Request.Builder builder = new Request.Builder();
	builder.url(urlStr.toString());
	Request request = builder.build();
	return request;
    }

    public static String postContent(String url, Map<String, String> headers, Map<String, String> params,
	    String jsonStr) {
	okhttp3.Request.Builder builder = new Request.Builder();
	builder.url(url);
	if (headers != null && headers.size() != 0) {
	    for (Entry<String, String> param : headers.entrySet()) {
		builder.addHeader(param.getKey(), param.getValue());
	    }
	}
	FormBody.Builder formBuilder = new FormBody.Builder();
	if (params != null && params.size() != 0) {
	    for (Entry<String, String> param : params.entrySet()) {
		formBuilder.add(param.getKey(), param.getValue());
	    }

	}
	if (StringUtils.isNotEmpty(jsonStr)) {
	    Map<String, String> jsons = JSON.parseObject(jsonStr, Map.class);
	    if (jsons != null && jsons.size() != 0) {
		for (Entry<String, String> param : jsons.entrySet()) {
		    formBuilder.add(param.getKey(), param.getValue());
		}
	    }
	}
	builder.post(formBuilder.build());
	Request request = builder.build();
	Response response = null;
	try {
	    response = client.newCall(request).execute();
	    if (!response.isSuccessful()) {
		response.close();
		return null;
	    }
	    return response.body().string();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    throw new RuntimeException("POST " + url + "出错", e);
	} finally {
	    try {
		response.close();
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

    public static void main(String[] args) throws IOException {
	String res = getContent("http://219.159.44.169:32201/cf_fpb/?iw-apikey=123&iw-cmd=xiangmuxiangqing","XMBH","4301100648419650");

	System.out.println(res);

    }
}
