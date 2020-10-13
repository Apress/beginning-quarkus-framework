package org.acme.bean;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("deliciousVanilla")
public class VanillaBean implements FlavorTown {
    long flavorStrength;
    String countryOfOrigin;

    @Override
    public void setFlavorStrength(int flavorStrength) {
        this.flavorStrength = flavorStrength;
    }

    @Override
    public void setCountryOfOrigin(String origin) {
        this.countryOfOrigin = origin;
    }
}
