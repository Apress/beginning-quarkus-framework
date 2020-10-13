package org.acme.bean;


import org.acme.bean.cdi.qualifier.Rare;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Rare
public class BeaverVanilla implements FlavorTown {

    @Override
    public void setFlavorStrength(int i) {

    }

    @Override
    public void setCountryOfOrigin(String madagascar) {

    }
}
