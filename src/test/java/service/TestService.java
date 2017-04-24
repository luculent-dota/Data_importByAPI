package service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import com.luculent.data.constant.BasisKey;

import base.BaseTestSpringTestCase;
import mapper.BaseKeyMapper;

public class TestService extends BaseTestSpringTestCase{

    @Autowired
    private BaseKeyMapper baseKeyMapper;
    
    @Autowired
    private RedisTemplate<String, String> redisTempalte;  
    
    @Test
    public void insertXz(){
	List<String> xzs = baseKeyMapper.queryXZ();
	SetOperations<String,String> set =redisTempalte.opsForSet();
	set.add(BasisKey.XZ.name(), xzs.toArray(new String[xzs.size()]));
	
    }
    
    @Test
    public void insertXian(){
	List<String> xzs = baseKeyMapper.queryXian();
	SetOperations<String,String> set =redisTempalte.opsForSet();
	set.add(BasisKey.XIAN.name(), xzs.toArray(new String[xzs.size()]));
	
    }
    
    @Test
    public void insertCun(){
	List<String> xzs = baseKeyMapper.queryCun();
	SetOperations<String,String> set =redisTempalte.opsForSet();
	set.add(BasisKey.CUN.name(), xzs.toArray(new String[xzs.size()]));
	
    }
}
