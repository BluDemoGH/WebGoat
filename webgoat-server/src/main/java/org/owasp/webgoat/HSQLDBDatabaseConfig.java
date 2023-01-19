package org.owasp.webgoat;

import lombok.extern.slf4j.Slf4j;
import org.hsqldb.server.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Map;


/**
 * Rationale for this class: when the HSQLDB is started with jdbc:file:// it is only accessible from within the same
 * JVM. This can only be done if you start a standalone HSQLDB. We need both WebWolf and WebGoat to use the same database
 */
@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "webgoat.start", name = "hsqldb", havingValue = "true")
public class HSQLDBDatabaseConfig {

    @Value("${hsqldb.port:9001}")
    private int hsqldbPort;
    private Server server;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server hsqlStandalone(@Value("${webgoat.server.directory}") String directory,
                                 @Value("${hsqldb.silent:true}") boolean silent,
                                 @Value("${hsqldb.trace:false}") boolean trace,
                                 @Value("${server.address}") String address) {
        log.info("Starting internal database on port {} ...", hsqldbPort);
        server = new Server();
        server.setDatabaseName(0, "webgoat");
        server.setDatabasePath(0, directory + "/data/webgoat");
        server.setDaemon(true);
        server.setAddress(address);
        server.setTrace(trace);
        server.setSilent(silent);
        server.setPort(hsqldbPort);
        return server;
    }

    @PreDestroy
    public void shutdown() {
        server.shutdownCatalogs(1);
    }

    @Bean
    @DependsOn("hsqlStandalone")
    @Primary
    public DataSource dataSource(@Value("${spring.datasource.url}") String url,
                                 @Value("${spring.datasource.driver-class-name}") String driverClassName) {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(url);
        driverManagerDataSource.setDriverClassName(driverClassName);
        return driverManagerDataSource;
    }
}


mydb = mysql.connector.connect(
	host = "localhost",
    aws_access_key_id = "AKIAYVP4CIPPPSREVFOJ"
    aws_secret_access_key = "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"
	database = "notsosecretdb"
)

cursor = mydb.cursor()

cursor.execute("CREATE TABLE gfg (name VARCHAR(255), user_name VARCHAR(255))")
