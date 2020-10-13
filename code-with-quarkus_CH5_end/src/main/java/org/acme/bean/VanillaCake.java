package org.acme.bean;

import io.quarkus.runtime.StartupEvent;
import org.acme.aop.interceptor.Audited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("vanillaCake")
public class VanillaCake {

    Logger logger = LoggerFactory.getLogger(VanillaCake.class);

    @Inject
    @Named("deliciousVanilla")
    VanillaBean vanillaBean;

    @Inject
    @ApplicationScoped
    VanillaBean boldVanilla;

    @Inject
    FlavorTown aFlavor;

    public void init(@Observes StartupEvent startupEvent){
        logger.info("vanillaBean is aFlavor: "+(aFlavor==vanillaBean));
    }

    @Inject
    @Named("bold")
    @Audited
    public void gimmeSomeFlavor(FlavorTown flavor){
        logger.info("Got some flavor: "+(flavor==flavor));
    }
}
