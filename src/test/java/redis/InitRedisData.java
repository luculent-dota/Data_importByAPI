package redis;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import base.BaseRedisSpringTestCase;

public class InitRedisData extends BaseRedisSpringTestCase{

    @Autowired
    private RedisTemplate<String, String> redisTempalte;  
    
    private void addkey(String key,String value){
	SetOperations<String,String> set =redisTempalte.opsForSet();
	set.add(key, value);
	
    }
    
    private Set<String> getkey(String key){
	SetOperations<String,String> set =redisTempalte.opsForSet();
	return set.members(key);
    }
    
    private void pirnt(Set<String> set){
	for(String str:set){
	    
	}
    }
    
    @Test
    public void test(){
	//addkey();
    }
    
    
}
