package com.luculent.data.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luculent.data.constant.BasisKey;
import com.luculent.data.exception.BasisKeyNotFoundException;


@Service
@Transactional
public class BasisKeyService {
    @Autowired
    private RedisTemplate<String, String> redisTempalte;
    
    
    private Set<String> getCacheSet(String key){
   	SetOperations<String,String> set =redisTempalte.opsForSet();
   	return set.members(key);
    }
    
    public Set<String> getCacheBykey(String key){
	BasisKey keystr = null;
	try{
	    keystr = BasisKey.valueOf(key.toUpperCase());
	    
	}catch(IllegalArgumentException e){
	    throw new BasisKeyNotFoundException();
	}
	return getCacheSet(keystr.name());
	
    }
}
