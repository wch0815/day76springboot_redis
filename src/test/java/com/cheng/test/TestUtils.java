package com.cheng.test;

import com.cheng.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUtils {

    @Resource
    private RedisUtil util;

    @Test
    public void testString(){
        boolean set = util.set("hello", "你好");
        System.out.println(set);
        Object hello = util.get("hello");
        System.out.println(hello);
    }

    @Test
    public void testHash(){
        Map<String, Object> map = new HashMap<>();
        map.put("haha","heihei");
        map.put("ha1ha","heihei1");
        boolean hehe = util.hmset("hehe", map);
        System.out.println(hehe);
        System.out.println(util.hmget("hehe"));
        System.out.println(util.hget("hehe","haha"));
    }

    @Test
    public void testSet(){

        System.out.println(util.lpush("ksy","heheheh"));
        System.out.println(util.lrange("ksy",0,10));
    }
}
