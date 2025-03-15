package com.idk.shit.game.views.view.Implementations;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.Button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;

public class GameOverView extends ApplicationView{
    private Button gameButton = new Button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.GREEN);

    public GameOverView(State state,long window, InputManager inputManager) {
        super(state, window, inputManager);
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){

    }
    @Override
    public void  update() throws Exception{
        gameButton.update(window);
        if (gameButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE))  {
            inputManager.cleanup();
            state.Play(2);
        }
    }
    @Override
    public void render(){
        gameButton.draw();
    }
}