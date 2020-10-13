package org.acme.bean.cdi;

import org.acme.bean.spring.AnotherSpringBean;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;

@ApplicationScoped
@Named("customBeanFinder")
public class CDIBeanManagerDemo {

    @Inject
    BeanManager beanManager;


    public AnotherSpringBean getBean(String beanName) {
        AnotherSpringBean aSpringOrCdiBean = null;
        Bean namedCdiBean = null;
        HashSet<Bean<?>> beans = (HashSet<Bean<?>>) beanManager.
                getBeans(beanName);
        if (!beans.isEmpty()) {
            namedCdiBean = beans.iterator().next();
            CreationalContext creationalContext = beanManager.
                    createCreationalContext(namedCdiBean);
             aSpringOrCdiBean = (AnotherSpringBean)
                    beanManager.getReference(namedCdiBean, namedCdiBean.
                            getBeanClass(), creationalContext);
        }
        return aSpringOrCdiBean;
    }
}


