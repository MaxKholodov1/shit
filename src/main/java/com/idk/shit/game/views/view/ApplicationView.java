package com.idk.shit.game.views.view;
import com.idk.shit.game.state.State;
import com.idk.shit.utils.InputManager;

public abstract  class ApplicationView {
    protected long window;
    protected InputManager inputManager;
    protected State state;
    protected long vg;
    public ApplicationView(State state, long window,InputManager inputManager, long vg){
        this.state = state;
        this.window=window;
        this.inputManager=inputManager;
        this.vg = vg;
    }
    public abstract void update() throws Exception;
    public abstract void render();
    // public abstract void cleanup();

    public void cleanup() {

    }
}