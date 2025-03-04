package com.idk.shit.game;

public class StateManager {
    private GameState currState;
    public void setState(GameState newState){
        if(currState!=null){
            currState=newState;
        }
        else{
            currState = null;
            currState=newState;
            newState = null;
            System.gc();
        }
    }
    public void update(){
        if(currState!=null){
            currState.update();
        }
    }
    public void render(){
        if(currState!=null){
            currState.render();
        }
    }
}
