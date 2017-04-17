package com.luculent.data.utils.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

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
public class TemplateUtil {
    
    private TemplateUtil(){
	// TODO Auto-generated constructor stub
	throw new AssertionError();
    }

    public static void createClass(String name) {
	VelocityEngine ve = new VelocityEngine();
	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

	ve.init();
	Template t = ve.getTemplate("create.vm");
	VelocityContext ctx = new VelocityContext();

	ctx.put("className", name);

	String rootPath = DataConstant.SCHEDULER_PATH + name + ".java";
	System.out.println(rootPath);
	merge(t, ctx, rootPath);
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
