package org.datn.app.config;

import lombok.extern.slf4j.Slf4j;
import org.datn.app.constant.config.HibernateConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
@Slf4j(topic = "DataLoader")
public class DataLoader {
    @Autowired
    public Environment env;

    @Bean
    public boolean loadData(@Value("${spring.jpa.hibernate.ddl-auto}") String value,
                            @Value("${spring.datasource.url}") String url,
                            @Value("${spring.datasource.username}") String username,
                            @Value("${spring.datasource.password}") String password,
                            @Value("${spring.datasource.driver-class-name}") String driverClassName)
            throws ClassNotFoundException, SQLException {
        String message = env.getProperty("success-load-data");
        if (value.equals(HibernateConstant.CREATE_DROP)) {
            Class.forName(driverClassName);
            Connection connection = DriverManager.getConnection(url, username, password);
            if (connection != null) {
                String sqlQuery = "ALTER TABLE products ADD FULLTEXT(product_name)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.execute();
                log.info(message, sqlQuery);
                preparedStatement.close();
            }
            connection.close();
        }
        return true;
    }

}
