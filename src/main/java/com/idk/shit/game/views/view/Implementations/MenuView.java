package com.idk.shit.game.views.view.Implementations;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;


public class MenuView extends ApplicationView {
    private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.RED);

    public MenuView(State state, long window, InputManager inputManager) {
        super(state, window, inputManager);
        initMenu();
    }
    private void initMenu(){
        startButton.draw();
    }
    @Override
    public void update() throws Exception{
        startButton.update(window);
        if (startButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            state.Play();;
            inputManager.cleanup();;
        }
    }
    @Override
    public void render(){
        startButton.draw();
    }
}