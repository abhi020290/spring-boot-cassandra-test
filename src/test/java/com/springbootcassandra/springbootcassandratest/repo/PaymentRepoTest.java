package com.springbootcassandra.springbootcassandratest.repo;

import com.springbootcassandra.springbootcassandratest.config.TestBase;
import com.springbootcassandra.springbootcassandratest.entity.PaymentDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class PaymentRepoTest extends TestBase {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void TestSave(){
        PaymentDetails paymentDetails = new PaymentDetails("126721621","1221214343","failure");
        PaymentDetails save = paymentRepository.save(paymentDetails);
        Assert.assertNotNull(save);
    }
}
