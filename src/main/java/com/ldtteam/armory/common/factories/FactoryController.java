package com.ldtteam.armory.common.factories;

import com.ldtteam.armory.api.common.factories.IFactoryController;
import com.ldtteam.armory.api.common.factories.IHeatedItemFactory;
import com.ldtteam.armory.api.common.factories.IMLAFactory;

/**
 * Created by marcf on 1/12/2017.
 */
public final class FactoryController implements IFactoryController {

    private static final IFactoryController INSTANCE = new FactoryController();

    public static IFactoryController getInstance(){
        return INSTANCE;
    }

    private FactoryController () {}

    @Override
    public IHeatedItemFactory getHeatedItemFactory() {
        return HeatedItemFactory.getInstance();
    }

    @Override
    public IMLAFactory getMLAFactory() {
        return ArmorFactory.getInstance();
    }
}
