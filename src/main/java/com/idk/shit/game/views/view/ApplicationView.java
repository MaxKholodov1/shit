package com.idk.shit.game;
import com.idk.shit.utils.InputManager;
import com.idk.shit.graphics.Texture;

public abstract  class GameState {
    protected long window;
    protected StateManager stateManager;
    protected InputManager inputManager;
    public GameState(long window,InputManager inputManager, StateManager stateManager){
        this.window=window;
        this.stateManager=stateManager;
        this.inputManager=inputManager;
    }
    public abstract void update();
    public abstract void render();
    // public abstract void cleanup();

    public void cleanup() {

    }
}