package com.sapient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import com.sapient.utils.DbUtil;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class DbConnTest {
    static Connection connection;

    @BeforeAll
    public static void before() {
        try {
            connection = DbUtil.createConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    @AfterAll
    public static void after() throws SQLException,ClassNotFoundException {
        if (connection != null){
            connection.close();
        }
        
    }

    @Test
    public void createConnection_connectionActive() throws SQLException,ClassNotFoundException {
        assertNotNull(connection);
    }
}