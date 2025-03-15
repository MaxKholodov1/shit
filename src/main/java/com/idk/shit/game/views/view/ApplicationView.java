package com.idk.shit.game.views.view;
import com.idk.shit.game.state.State;
import com.idk.shit.ui.TextRenderer;
import com.idk.shit.utils.InputManager;

public abstract  class ApplicationView {
    protected long window;
    protected InputManager inputManager;
    protected State state;
    protected long vg;
    protected TextRenderer textRenderer;
    public ApplicationView(State state, long window,InputManager inputManager, long vg, TextRenderer textRenderer){
        this.state = state;
        this.window=window;
        this.inputManager=inputManager;
        this.vg = vg;
        this.textRenderer=textRenderer;
    }
    public abstract void update() throws Exception;
    public abstract void render();
    // public abstract void cleanup();

    public void cleanup() {

    }
}