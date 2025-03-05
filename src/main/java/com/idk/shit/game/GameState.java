package com.idk.shit.game;

public abstract  class GameState {
    protected long window;
    protected StateManager stateManager;
    public GameState(long window, StateManager stateManager ){
        this.window=window;
        this.stateManager=stateManager;
    }
    public abstract void update();
    public abstract void render();
    // public abstract void cleanup();

    public void cleanup() {

    }
}
