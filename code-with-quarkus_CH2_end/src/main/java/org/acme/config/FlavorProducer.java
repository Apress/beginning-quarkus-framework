package org.acme.config;

import io.quarkus.arc.profile.IfBuildProfile;
import org.acme.bean.VanillaBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class FlavorProducer {

    @IfBuildProfile("test-aws-east1")
    @Produces
    @Named("boldVanilla")
    @RequestScoped
    public VanillaBean vanillaBean(){
        VanillaBean vanillaBean = new VanillaBean();
        vanillaBean.setFlavorStrength(30);
        vanillaBean.setCountryOfOrigin("Madagascar");
        return vanillaBean;
    }

}
