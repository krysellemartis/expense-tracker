package com.finance.expense.tracker.util;

public class ExpenseTrackerSqls {

    public static final String GET_USER_NAME = "select full_name from person where upper(email)=upper('%s') and password='%s'";
    public static final String CHECK_USER_EXISTS = "SELECT 1 FROM person WHERE upper(email)=upper('%s')";
    public static final String INSERT_USER = "INSERT INTO person(full_name, email, password) VALUES ('%s', '%s', '%s')";
}
