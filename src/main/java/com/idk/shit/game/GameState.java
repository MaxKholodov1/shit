package com.idk.shit.game;
import com.idk.shit.utils.InputManager;
import com.idk.shit.graphics.Texture;

public abstract  class GameState {
    protected long window;
    protected StateManager stateManager;
    protected InputManager inputManager;
    protected Texture blockTexture;
    protected Texture playerTexture;
    public GameState(long window,InputManager inputManager, StateManager stateManager, Texture blockTexture, Texture playerTexture ){
        this.window=window;
        this.stateManager=stateManager;
        this.inputManager=inputManager;
        this.blockTexture=blockTexture;
        this.playerTexture=playerTexture;
    }
    public abstract void update();
    public abstract void render();
    // public abstract void cleanup();

    public void cleanup() {

    }
}