package com.ynz.msaccess.demo;

import com.ynz.msaccess.demo.model.CustomerQ;
import com.ynz.msaccess.demo.rowMapper.CustomerQMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private JdbcTemplate template;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("##### starting running the commandline runner");

        Integer r = template.queryForObject("select Count(*) from CustomerQ", Integer.class);
        System.out.println("number of customer : " + r.toString());

        List<CustomerQ> customers = template.query("select * from CustomerQ", new CustomerQMapper());
        customers.forEach(c -> System.out.println(c.toString()));
    }


}
