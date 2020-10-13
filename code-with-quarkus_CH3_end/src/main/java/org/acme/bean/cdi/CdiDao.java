package org.acme.bean.cdi;

import io.quarkus.runtime.StartupEvent;
import org.acme.bean.spring.AnotherSpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

@Named("aCdiBean")
@ApplicationScoped
public class CdiDao implements AnagramDao {

    final Logger logger = LoggerFactory.getLogger(CdiDao.class);

    @Autowired
    @Qualifier("anotherSpringBean")
    AnotherSpringBean springBean;

    public void init(@Observes StartupEvent startupEvent){
        logger.info("Starting CDI bean with spring component. Is it null? "+(springBean ==null));
    }
}
