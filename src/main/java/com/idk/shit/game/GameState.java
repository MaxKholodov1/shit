package com.idk.shit.game;

import com.idk.shit.utils.InputManager;

public abstract  class GameState {
    public enum State{
        _menu_,
        _game_,
        _overgame_
    }
    protected long window;
    protected State curState;
    protected InputManager inputManager;
<<<<<<< Updated upstream
    public GameState(State state, InputManager input){
=======
    public GameState(long window, State state, InputManager input){
>>>>>>> Stashed changes
        this.curState = state;
        this.inputManager = input;
    }
    public State update(){
        return this.curState;
    }
    public abstract void render();
    public void cleanup() {

    }

    
}