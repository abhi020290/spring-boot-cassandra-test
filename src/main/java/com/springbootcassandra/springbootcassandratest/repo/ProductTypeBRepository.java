package com.springbootcassandra.springbootcassandratest.repo;

import com.springbootcassandra.springbootcassandratest.entity.ProductTypeB;
import com.springbootcassandra.springbootcassandratest.entity.ProductTypeBKey;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeBRepository extends CassandraRepository<ProductTypeB, ProductTypeBKey> {

}
