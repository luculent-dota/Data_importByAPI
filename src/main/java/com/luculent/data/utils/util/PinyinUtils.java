package com.luculent.data.utils.util;

import org.apache.commons.lang3.StringUtils;

import com.luculent.data.constant.DataConstant;
import com.luculent.data.exception.ClassMakeNameException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {

    private PinyinUtils() {
	// TODO Auto-generated constructor stub
    }
    
    
    public static String classNameByProjectNameWithApiName(String projectName,String apiName){
	if(StringUtils.isEmpty(projectName) || StringUtils.isEmpty(apiName)){
	    throw new ClassMakeNameException("生成动态类的项目名或接口名为空");
	}
	StringBuilder bl = new StringBuilder();
	bl.append(getFirstSpell(projectName));
	String str1 = apiName.substring(0, 1);
	bl.append(ConventionUtils.firstSpellToUp(getPingYin(str1)));
	if(apiName.length()>1){
	    String str2 = apiName.substring(1);
	    bl.append(getFirstSpell(str2));
	}
	bl.append(DataConstant.SCHEDULER_JOB_SUFFIX);
	//System.out.println(ConventionUtils.firstSpellToUp(bl.toString()));
	return ConventionUtils.firstSpellToUp(bl.toString());
    }
    
    public static String getFirstSpell(String chinese) {   
        StringBuffer pybf = new StringBuffer();   
        char[] arr = chinese.toCharArray();   
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();   
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);   
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);   
        for (int i = 0; i < arr.length; i++) {   
                if (arr[i] > 128) {   
                        try {   
                                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);   
                                if (temp != null) {   
                                        pybf.append(temp[0].charAt(0));   
                                }   
                        } catch (BadHanyuPinyinOutputFormatCombination e) {   
                                e.printStackTrace();   
                        }   
                } else {   
                        pybf.append(arr[i]);   
                }   
        }   
        return pybf.toString().replaceAll("\\W", "").trim();   
    } 
    
    public static String getPingYin(String src) {  
	  
        char[] t1 = null;  
        t1 = src.toCharArray();  
        String[] t2 = new String[t1.length];  
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();  
          
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);  
        String t4 = "";  
        int t0 = t1.length;  
        try {  
            for (int i = 0; i < t0; i++) {  
                // 判断是否为汉字字符  
                if (java.lang.Character.toString(t1[i]).matches(  
                        "[\\u4E00-\\u9FA5]+")) {  
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);  
                    t4 += t2[0];  
                } else  
                    t4 += java.lang.Character.toString(t1[i]);  
            }  
            // System.out.println(t4);
            return t4;  
        } catch (BadHanyuPinyinOutputFormatCombination e1) {  
            e1.printStackTrace();  
        }  
        return t4;  
    }  
    
    public static void main(String[] args) {
	
    }
}
