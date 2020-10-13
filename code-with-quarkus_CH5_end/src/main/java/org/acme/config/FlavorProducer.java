package org.acme.config;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.configuration.ProfileManager;
import org.acme.bean.VanillaBean;
import org.eclipse.microprofile.config.ConfigProvider;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class FlavorProducer {

    public void start(@Observes StartupEvent startupEvent){
        printConfig();
    }

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

    public void printConfig(){
        String currentProfile = ProfileManager.getActiveProfile();
        String appName = ConfigProvider.getConfig().getValue("quarkus.application.name", String.class);
    }

}
