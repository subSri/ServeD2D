package com.sapient.dao.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.sql.Connection;


import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.sapient.utils.DbUtil;


public class TestAddressDao {
    
    @BeforeAll
    public static void setup() {
    	
    	try( Connection conn = DbUtil.createConnection();){
    	RunScript.execute(conn, new FileReader("schema.sql"));
    	}catch(Exception e) {
    		
    	}
    }
    
    @Test
    public void assertSomething() {
        assertEquals("", "");
    }
}