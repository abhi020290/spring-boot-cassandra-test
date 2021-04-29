package com.springbootcassandra.springbootcassandratest.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@EnableCassandraRepositories(basePackages = {"com.springbootcassandra.springbootcassandratest.repo"})
@EntityScan("com.springbootcassandra.springbootcassandratest.entity")
@Configuration
public class TestCassandraConfig {
    private static final String CONTACT_POINTS = "127.0.0.1";
    private static final String KEYSPACE = "product_keyspace";

    @Bean("testSession")
    public CqlSessionFactoryBean session() {

        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
        session.setContactPoints(CONTACT_POINTS);
        session.setKeyspaceName(KEYSPACE);
        session.setPort(Integer.parseInt (EmbeddedCassandraServerHelper.getCluster().
                getMetadata().getAllHosts().iterator().next().toString().split(":")[1]));
        session.setLocalDatacenter("datacenter1");
        return session;
    }

   /* @Bean
    public CassandraCqlTemplateFactoryBean cluster(CqlSession session, CassandraConverter converter) {
        CassandraCqlTemplateFactoryBean cluster = new CassandraCqlTemplateFactoryBean();
        cluster.setSessionFactory((SessionFactory) sessionFactory(session, converter));
        return cluster;
    }*/

    @Bean
    public SessionFactoryFactoryBean sessionFactory(CqlSession session, CassandraConverter converter) {
        SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
        sessionFactory.setSession(session);
        sessionFactory.setConverter(converter);
        sessionFactory.setSchemaAction(SchemaAction.NONE);
        return sessionFactory;
    }

    @Bean
    public CassandraMappingContext mappingContext(CqlSession cqlSession) {
        return new CassandraMappingContext();
    }

    @Bean
    public CassandraConverter converter(CassandraMappingContext mappingContext) {
        return new MappingCassandraConverter(mappingContext);
    }

    @Bean
    public CassandraOperations cassandraTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }

}
