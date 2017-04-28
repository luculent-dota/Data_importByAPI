package com.luculent.data.utils.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.luculent.data.constant.DataConstant;

/**
 * 
 *@Description:模板生成java文件
 *@Author:zhangy
 *@Since:2017年4月14日上午11:14:06
 */
public class TemplateUtils {
    
    private final static  Logger logger = LogManager.getLogger(TemplateUtils.class);
    
    private TemplateUtils(){
	// TODO Auto-generated constructor stub
	throw new AssertionError();
    }
    
    public static String createClass(String projectName,String apiName) {
	String className = PinyinUtils.classNameByProjectNameWithApiName(projectName,apiName);
	VelocityEngine ve = new VelocityEngine();
	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

	ve.init();
	Template t = ve.getTemplate("create.vm");
	VelocityContext ctx = new VelocityContext();
	
	ctx.put("projectName", projectName);
	ctx.put("apiName", apiName);
	ctx.put("note", "数据处理类");
	ctx.put("className", className);

	String rootPath = DataConstant.SCHEDULER_PATH + className + ".java";
	logger.info("动态生成"+className+"任务类成功!");
	merge(t, ctx, rootPath);
	return className;
    }

    private static void merge(Template template, VelocityContext ctx, String path) {
	PrintWriter writer = null;
	try {
	    writer = new PrintWriter(path);
	    template.merge(ctx, writer);
	    writer.flush();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} finally {
	    writer.close();
	}
    }
}
