package com.springbootcassandra.springbootcassandratest.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.TransportException;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.BeforeClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@ContextConfiguration(classes = {TestCassandraConfig.class})
@ComponentScan(basePackages = {"com.springbootcassandra.springbootcassandratest.repo"})
@Slf4j
public class TestBase {

    private static final String CONTACT_POINTS = "127.0.0.1";
    private static int portNumber = 9042;
    private static Session session;

    @BeforeClass
    public static void startCassandraEmbeddedLocal() throws IOException, TTransportException , TransportException {
        if (session != null && !session.isClosed())
            return;
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(EmbeddedCassandraServerHelper.CASSANDRA_RNDPORT_YML_FILE,20000L);
       // portNumber = EmbeddedCassandraServerHelper.getRpcPort();
        log.info("Cassandra starting at port {} ", portNumber);
        portNumber = Integer.parseInt (EmbeddedCassandraServerHelper.getCluster().
                getMetadata().getAllHosts().iterator().next().toString().split(":")[1]);
        Cluster cluster = Cluster.builder().addContactPoints(CONTACT_POINTS).withPort(portNumber).build();
        session = cluster.connect();
        File file = ResourceUtils.getFile("classpath:cassandra/test-cassandra.cql");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String query = new String(bytes);
        String[] split = query.split(";");
        Arrays.stream(split).forEach(session::execute);
        log.info("Cassandra stated at port {} ", portNumber);

    }
}
