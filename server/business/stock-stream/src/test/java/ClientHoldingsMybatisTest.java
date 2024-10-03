import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.DatabaseException;
import com.capstone.integration.ClientMybatisHoldingsDaoImpl;
import com.capstone.models.Holding;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class ClientHoldingDaoImplTest {

    @Autowired
    private ClientMybatisHoldingsDaoImpl dao;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Autowired
	private JdbcTemplate jdbcTemplate;

    @Test
    void testGetClients() throws SQLException {
        List<Holding> querClients = dao.getClientHoldings("C002");
        assertEquals(2, querClients.size());
    }

    @Test
    void testGetClientPortfolio_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getClientHoldings(null);
        });
    }

    @Test
    void testGetClientPortfolio_ClientNotFound() throws SQLException {
        List<Holding> portfolios = dao.getClientHoldings("NON_EXISTENT_CLIENT");
        assertTrue(portfolios.isEmpty());
    }

    @Test
    void testGetClientPortfolio_PortfolioNotFound() throws SQLException {
        List<Holding> portfolios = dao.getClientHoldings("CLIENT_WITH_NO_PORTFOLIO");
        assertTrue(portfolios.isEmpty());
    }

    @Test
    void getClientHoldingToSucceed() {
        Holding holding1 = new Holding("Stock A", "INST001", "C001", 100, new BigDecimal(150.50).setScale(2), 15050.00, new BigDecimal(155.00).setScale(2), 3.00, 450.00, 2.50);
        Holding holding = dao.getClientHolding("C001", "INST001");
        assertEquals(holding, holding1);
    }

    @Test
    void getClientHoldingToThrowDatabaseException() {
        assertThrows(DatabaseException.class, () -> {
            dao.getClientHolding("CLIENT001", "INST003");
        });
    }

    @Test
    void getClientHoldingToThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.getClientHolding(null, null);
        });
    }

    @Test
    void addClientHoldingToSucceed() throws SQLException {
        Holding newHolding = new Holding("Stock A", "INST003", "C001", 100, new BigDecimal(150.50).setScale(2), 15050.00, new BigDecimal(155.00).setScale(2), 3.00, 450.00, 2.50);
        dao.addClientHolding(newHolding);
        sqlSessionTemplate.commit();
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='C001' AND instrumentid='INST003'"));
    }

    @Test
    void addClientHoldingToThrowDatabaseException() throws SQLException {
        Holding newHolding = new Holding("Stock A", "INST002", "CLIENT001", 100, new BigDecimal(150.50).setScale(2), 15050.00, new BigDecimal(155.00).setScale(2), 3.00, 450.00, 2.50);
        assertThrows(DatabaseException.class, () -> {
            dao.addClientHolding(newHolding);
        });
    }

    @Test
    void addClientHoldingToThrowIllegalArgumentException() throws SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.addClientHolding(null);
        });
    }

    @Test
    void updateClientHoldingToSucceed() throws SQLException {
        Holding newHolding = dao.getClientHolding("C001", "INST002");
        newHolding.setAveragePrice(BigDecimal.valueOf(22.2).setScale(2));
        dao.updateClientHolding(newHolding);
        sqlSessionTemplate.commit();
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='C001' AND instrumentid='INST002'"));
    }

    @Test
    void updateClientHoldingToThrowDatabaseException() throws SQLException {
        Holding newHolding = new Holding("Stock A", "INST003", "C001", 100, new BigDecimal(150.50).setScale(2), 15050.00, new BigDecimal(155.00).setScale(2), 3.00, 450.00, 2.50);
        newHolding.setAveragePrice(BigDecimal.valueOf(22.2).setScale(2));
        assertThrows(DatabaseException.class, () -> {
            dao.updateClientHolding(newHolding);
        });
    }

    @Test
    void updateClientHoldingToThrowIllegalArgumentException() throws SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.updateClientHolding(null);
        });
    }

    @Test
    void removeClientHoldingToSucceed() throws SQLException {
        Holding holdingToBeDeleted = dao.getClientHolding("C001", "INST002");
        dao.removeClientHolding("C001", "INST002");
        sqlSessionTemplate.commit();
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='CLIENT001' AND instrumentid='INST002'"));
    }

    @Test
    void removeClientHoldingToThrowDatabaseException() throws SQLException {
        assertThrows(DatabaseException.class, () -> {
            dao.removeClientHolding("CLIENT001", "INST003");
        });
    }

    @Test
    void removeClientHoldingToThrowIllegalArgumentException() throws SQLException {
        assertThrows(IllegalArgumentException.class, () -> {
            dao.removeClientHolding(null, null);
        });
    }
}
