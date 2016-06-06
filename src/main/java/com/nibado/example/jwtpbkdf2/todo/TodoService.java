package com.nibado.example.jwtpbkdf2.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

public class TodoService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS todo(id VARCHAR(255), created TIMESTAMP, PRIMARY KEY(id));");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS todo_item(id VARCHAR(255), todo_id VARCHAR(255), text VARCHAR(1000), PRIMARY KEY(id));");
    }
}
