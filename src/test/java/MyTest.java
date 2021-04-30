import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertNotNull;
import com.sapient.utils.DbUtil;


public class MyTest {
    Connection connection;

    @Before
    public void before() {
        try {
            connection = DbUtil.createConnection();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    @After
    public void after() throws SQLException,ClassNotFoundException {
        connection.close();
    }

    @Test
    public void createConnection_connectionActive() throws SQLException,ClassNotFoundException {
        assertNotNull(connection);
    }
}