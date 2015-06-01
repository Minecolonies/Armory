package com.Orion.Armory.Client.GUI.Components.Core;
/*
/  AbstractGUIMultiComponent
/  Created by : Orion
/  Created on : 27-4-2015
*/

import com.Orion.Armory.Client.GUI.ArmoryBaseGui;
import com.Orion.Armory.Util.Core.Rectangle;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public abstract class AbstractGUIMultiComponent implements IComponentManager, IGUIComponent
{
    StandardComponentManager iSubComponents;

    protected ArmoryBaseGui iGui;

    public int iHeight = 0;
    public int iWidth = 0;
    public int iLeft = 0;
    public int iTop = 0;

    String iInternalName;

    public AbstractGUIMultiComponent(ArmoryBaseGui pGui, String pInternalName, int pLeft, int pTop, int pWidth, int pHeigth)
    {
        iSubComponents = new StandardComponentManager(pGui);

        iGui = pGui;

        iLeft = pLeft;
        iTop = pTop;

        iWidth = pWidth;
        iHeight = pHeigth;

        iInternalName = pInternalName;
    }


    public String getInternalName() {
        return iInternalName;
    }


    @Override
    public ArrayList<IGUIComponent> getComponents() {
        return iSubComponents.getComponents();
    }

    @Override
    public void addComponent(IGUIComponent pNewComponent) {
        iSubComponents.addComponent(pNewComponent);
    }

    @Override
    public IGUIComponent getComponentAt(int pTargetX, int pTargetY) {
        pTargetX -= iLeft;
        pTargetY -=iTop;

        return iSubComponents.getComponentAt(pTargetX, pTargetY);
    }

    @Override
    public void drawComponents() {
        iSubComponents.drawComponents();
    }

    @Override
    public void onUpdate() {
        for(int i = 0; i < iSubComponents.getComponents().size(); i++) {
            IGUIComponent tComponent = iSubComponents.getComponents().get(i);
            tComponent.onUpdate();
        }
    }

    @Override
    public int getHeight() {
        return iHeight;
    }

    @Override
    public int getWidth() {
        return iWidth;
    }

    @Override
    public void draw(int pX, int pY) {
        GL11.glPushMatrix();
        GL11.glTranslatef(iLeft, iTop, 0F);

        iSubComponents.drawComponents();

        GL11.glPopMatrix();
    }

    @Override
    public void drawForeGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawBackGround(int pX, int pY) {
        //NOOP
    }

    @Override
    public void drawToolTips(int pMouseX, int pMouseY) {
        //NOOP
    }

    @Override
    public boolean checkIfPointIsInComponent(int pTargetX, int pTargetY) {
        Rectangle tComponentBounds = new Rectangle(iLeft, iTop, iWidth, iHeight);

        return tComponentBounds.contains(pTargetX, pTargetY);
    }

    @Override
    public abstract boolean handleMouseClicked(int pMouseX, int pMouseY, int pMouseButton);

    public boolean handleKeyTyped(char pKey, int pPara)
    {
        return false;
    }

    public boolean requiresForcedInput()
    {
        return false;
    }
}