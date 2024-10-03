package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.DatabaseException;
import com.capstone.integration.ClientHoldingDaoImpl;
import com.capstone.integration.DbTestUtils.*;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import com.capstone.models.Holding;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class ClientHoldingsMybatisTest {

    @Autowired
    private ClientMybatisHoldingsDaoImpl dao;

    

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //success test
    @Test
    void testGetClients() throws SQLException {
        List<Holding> queriedClients = dao.getClientHoldings("C002");
        assertEquals(2, queriedClients.size());
    }

    //Null client id
    @Test
    void testGetClientPortfolio_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> dao.getClientHoldings(null));
    }

    //Non existent clientid
    @Test
    void testGetClientPortfolio_ClientNotFound() throws SQLException {
        List<Holding> portfolios = dao.getClientHoldings("NON_EXISTENT_CLIENT");
        assertTrue(portfolios.isEmpty());
    }

    //Client with no holdings
    @Test
    void testGetClientPortfolio_PortfolioNotFound() throws SQLException {
        List<Holding> portfolios = dao.getClientHoldings("C010");
        assertTrue(portfolios.isEmpty());
    }

    //obtain holdings of a particular stock of a client
    @Test
    void getClientHoldingToSucceed() {
        Holding expectedHolding = new Holding("Stock A", "INST001", "C001", 100, new BigDecimal("150.5"), 15050, new BigDecimal("155"), 3.00, 450.00, 2.50);
        Holding actualHolding = dao.getClientHolding("C001", "INST001");
        assertEquals(expectedHolding, actualHolding);
    }

    
    //illegal argument exception
    @Test
    void getClientHoldingToThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dao.getClientHolding(null, null));
    }

    //insert successfully
    @Test
    void addClientHoldingToSucceed() throws SQLException {
        Holding newHolding = new Holding("Stock A", "INST003", "C001", 100, new BigDecimal("150.50"), 15050.00, new BigDecimal("155.00"), 3.00, 450.00, 2.50);
        dao.addClientHolding(newHolding);
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='C001' AND instrumentid='INST003'"));
    }

    //insert failed
    @Test
    void addClientHoldingToThrowDatabaseException() {
        Holding newHolding = new Holding("Stock A", "INST002", "CLIENT001", 100, new BigDecimal("150.50"), 15050.00, new BigDecimal("155.00"), 3.00, 450.00, 2.50);
        assertThrows(DataIntegrityViolationException.class, () -> dao.addClientHolding(newHolding));
    }

    @Test
    void addClientHoldingToThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dao.addClientHolding(null));
    }

    @Test
    void updateClientHoldingToSucceed() throws SQLException {
        Holding holdingToUpdate = dao.getClientHolding("C001", "INST002");
        holdingToUpdate.setAveragePrice(BigDecimal.valueOf(22.2).setScale(2));
        dao.updateClientHolding(holdingToUpdate);
        assertEquals(1, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='C001' AND instrumentid='INST002'"));
    }

    @Test
    void updateClientHoldingToThrowDatabaseException() {
        Holding newHolding = new Holding("Stock A", "INST003", "C001", 100, new BigDecimal("150.50"), 15050.00, new BigDecimal("155.00"), 3.00, 450.00, 2.50);
        newHolding.setAveragePrice(BigDecimal.valueOf(22.2).setScale(2));
        assertThrows(DatabaseException.class, () -> dao.updateClientHolding(newHolding));
    }

    @Test
    void updateClientHoldingToThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dao.updateClientHolding(null));
    }

    @Test
    void removeClientHoldingToSucceed() throws SQLException {
        dao.removeClientHolding("C001", "INST002");
        assertEquals(0, countRowsInTableWhere(jdbcTemplate, "holdings", "clientid='C001' AND instrumentid='INST002'"));
    }

    @Test
    void removeClientHoldingToThrowDatabaseException() {
        assertThrows(DatabaseException.class, () -> dao.removeClientHolding("CLIENT001", "INST003"));
    }

    @Test
    void removeClientHoldingToThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> dao.removeClientHolding(null, null));
    }

    private int countRowsInTableWhere(JdbcTemplate jdbcTemplate, String tableName, String whereClause) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause, Integer.class);
    }
}

