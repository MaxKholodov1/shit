package com.idk.shit.game.views.view.Implementations;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;


public class MenuView extends ApplicationView {
    private button Buttonlevel1 = new button(0.f, 0.5f, 1f, 0.8f, "start",Colours.RED);
    private button Buttonlevel2 = new button(0.f, -0.5f, 1f, 0.8f, "start",Colours.RED);
    public MenuView(State state, long window, InputManager inputManager) {
        super(state, window, inputManager);
        initMenu();
    }
    private void initMenu(){
        Buttonlevel1.draw();
        Buttonlevel2.draw();

    }
    @Override
    public void update() throws Exception{
        Buttonlevel1.update(window);
        Buttonlevel2.update(window);
        if (Buttonlevel1.isClicked() || inputManager.isKeyPressed(GLFW_KEY_1)) {
            state.Play();
            inputManager.cleanup();
        }
        if (Buttonlevel2.isClicked() || inputManager.isKeyPressed(GLFW_KEY_2)) {
            state.Play();
            inputManager.cleanup();
        }
    }
    @Override
    public void render(){
        Buttonlevel1.draw();
        Buttonlevel2.draw();

    }
}