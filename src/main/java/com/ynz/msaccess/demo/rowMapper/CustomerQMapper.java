package com.ynz.msaccess.demo.rowMapper;

import com.ynz.msaccess.demo.model.CustomerQ;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQMapper implements RowMapper<CustomerQ> {

    @Override
    public CustomerQ mapRow(ResultSet resultSet, int i) throws SQLException {
        CustomerQ customerQ = new CustomerQ();
        customerQ.setFirstName(resultSet.getString("FirstName"));
        customerQ.setLastName(resultSet.getString("LastName"));
        customerQ.setPhone(resultSet.getString("Phone"));
        return customerQ;
    }
}
