package com.luculent.data.utils.util;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luculent.data.DataConstant;

/**
 * 
 *@Description:动态编译class文件
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

    public static boolean createClassByClassName(String className){
	TemplateUtil.createClass(className);
	String classPath = DynamicCompileUtils.class.getClassLoader().getResource("").getFile();
	String javaPath =DataConstant.SCHEDULER_PATH+className+".java";
	boolean res =compileJavaFileByPath(classPath,javaPath);
	String message = res?"成功":"失败";
	logger.info("动态生成"+className+"类"+message+"!");
	return res;
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
	boolean ss = createClassByClassName("Teamsss");
	
    }
}
