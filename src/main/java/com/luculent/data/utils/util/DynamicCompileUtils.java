package com.luculent.data.utils.util;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.luculent.data.base.ServiceLocator;
import com.luculent.data.constant.DataConstant;
import com.luculent.data.exception.ClassMakeNameException;

/**
 * 
 *@Description:动态编译class文件 需要web容器动态加载
 *@Author:zhangy
 *@Since:2017年4月14日上午11:14:27
 */
public class DynamicCompileUtils {
    
    private final static  Logger logger = LogManager.getLogger(DynamicCompileUtils.class);
    
    private DynamicCompileUtils() {
	// TODO Auto-generated constructor stub
	throw new AssertionError();
    }
    /**
     * 
     *@param classPath 目标编译地址
     *@param javaPath java源文件地址
     *@return
     */
    private static boolean compileJavaFileByPath(String classPath,String javaPath){
	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	int compilationResult = compiler.run(null, null, null, "-d", classPath, javaPath);
	return 0==compilationResult?true:false;
    }
    @Deprecated
    public static String createClassByClassName(String projectName,String apiName){
	String className =TemplateUtils.createClass(projectName,apiName);
	String classPath = DynamicCompileUtils.class.getClassLoader().getResource("").getFile();
	String javaPath =DataConstant.SCHEDULER_PATH+className+".java";
	boolean res =compileJavaFileByPath(classPath,javaPath);
	if(!res){
	    logger.error("动态编译"+className+"任务类成功!");
	    throw new ClassMakeNameException("动态编译"+className+"任务类失败!");
	}
	
	logger.info("动态编译"+className+"任务类成功!");
	BeanDefinition bean = new GenericBeanDefinition();
	bean.setBeanClassName(DataConstant.SCHEDULER_PACKAGE_PATH+className);
	DefaultListableBeanFactory fty = (DefaultListableBeanFactory) ServiceLocator.context.getAutowireCapableBeanFactory();
	fty.registerBeanDefinition(ConventionUtils.firstSpellToLow(className), bean);
	logger.info("动态加入Spring上下文"+className+"任务类成功!");
	return className;
    }
    
    public static boolean existClass(String fullpath){
	try {
	    Class.forName(fullpath);
	} catch (ClassNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
	return true;
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
	createClassByClassName("扶贫办","项目张洋");
	
    }
}
