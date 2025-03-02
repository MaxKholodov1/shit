package com.idk.shit;

public class StateManager {
    private GameState currState;
    public void setState(GameState newState){
        if(currState!=null){
            //очистка
            currState=newState;
        }
        else{
            currState=newState;
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
