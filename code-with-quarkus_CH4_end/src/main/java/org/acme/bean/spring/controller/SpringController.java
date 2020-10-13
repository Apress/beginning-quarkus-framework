package org.acme.bean.spring.controller;

import io.quarkus.runtime.StartupEvent;
import org.acme.bean.VanillaCake;
import org.acme.bean.cdi.AnagramDao;
import org.acme.bean.cdi.CDIBeanManagerDemo;
import org.acme.bean.cdi.event.InterestingEvent;
import org.acme.bean.cdi.qualifier.Gossip;
import org.acme.bean.spring.AnotherSpringBean;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.lang.annotation.Annotation;

@RestController
@RequestMapping("/quarkus-for-spring")
public class SpringController {
    final Logger logger = Logger.getLogger(SpringController.class);

    @Autowired
    @Qualifier("springBean")
    AnotherSpringBean linkedBean;

    @Autowired
    @Qualifier("aCdiBean")
    AnagramDao aCdiBean;

    @Inject
    BeanManager beanManager;


    @Inject
    CDIBeanManagerDemo cdiBeanManagerDemo;

    public void init(@Observes StartupEvent startupEvent){
        logger.info("Starting Spring framework RestController");
        logger.info("Searching for the spring bean via BeanManager. Is it missing? "+ (cdiBeanManagerDemo.getBean("springBean") == null));
        sendInterestingEvent("CDI bean started!");
    }

    public void sendInterestingEvent(String message){
        Annotation annotation = new Annotation() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return Gossip.class;
            }
        };
        InterestingEvent interestingEvent = new InterestingEvent(message);
        beanManager.fireEvent(interestingEvent);
        beanManager.fireEvent(interestingEvent,annotation);
        beanManager.getEvent().fireAsync(interestingEvent);
    }
}
