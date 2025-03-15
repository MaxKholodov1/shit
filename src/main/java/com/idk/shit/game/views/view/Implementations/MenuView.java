package com.idk.shit.game.views.view.Implementations;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_0;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_1;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_2;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import javax.swing.JButton;

import com.idk.shit.ui.Button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;


public class MenuView extends ApplicationView {
    private Button Buttonlevel1 = new Button(0.f, 0.5f, 1f, 0.6f, "LEVEL 1",Colours.BROWN, vg);
    private Button Buttonlevel2 = new Button(0.f, -0.5f, 1f, 0.6f, "LEVEL 2",Colours.BROWN, vg);
    Button[] buttons = new Button[]{Buttonlevel1, Buttonlevel2};

    public MenuView(State state, long window, InputManager inputManager, long vg) {
        super(state, window, inputManager, vg);
        initMenu();
    }
    private void initMenu(){
        for (Button button : buttons) {
            button.draw();
        }
    }
    @Override
    public void update() throws Exception{
        for (Button button : buttons) {
            button.update(window);
        }
        for (int i = 0; i < buttons.length; i++) {
            int key = GLFW_KEY_1 + i; // i = 1 -> GLFW_KEY_1, i = 2 -> GLFW_KEY_2
            if(buttons[i].isClicked() || inputManager.isKeyPressed(key)){
                
                state.Play(i+1);
            }
        }
    }
    @Override
    public void render(){
        for (Button button : buttons) {
            button.draw();
        }
    }
}