package com.finance.expense.tracker.repository;

import com.finance.expense.tracker.util.ExpenseTrackerSqls;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class ExpenseTrackerRepository {

    private static final Logger log = LogManager.getLogger(ExpenseTrackerRepository.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public String getUserName(String email, String password) {
        String query = String.format(ExpenseTrackerSqls.GET_USER_NAME, email, password);
        String userName;
        logQuery(query);
        try {
            userName = jdbcTemplate.queryForObject(query, String.class);
        }
        catch (EmptyResultDataAccessException e) {
            log.info("No users found for email: {}", email);
            return "";
        }
        return userName;
    }

    public boolean checkUserExists(String email) {
        String query = String.format(ExpenseTrackerSqls.CHECK_USER_EXISTS, email);
        logQuery(query);
        Integer count;
        try {
            count = jdbcTemplate.queryForObject(query, Integer.class);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
        return count>0;
    }

    public void createUser(String fullName, String email, String password) {
        String query = String.format(ExpenseTrackerSqls.INSERT_USER, fullName, email, password);
        logQuery(query);
        try {
            jdbcTemplate.execute(query);
        }
        catch (Exception e) {
            log.info("Exception while inserting user: {}, {}", fullName, e, e);
            throw e;
        }
    }

    private void logQuery(String query) {
        log.info("Executing query: {}", query);
    }

}