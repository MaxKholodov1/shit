package com.idk.shit;

public class Player extends Object { 
    protected float speed_y=-0.0f;
    protected float max_speed_y=0.06f;
    protected float accel_y=-0.003f;
    Player(float startX, float startY, float width, float height, float speed, float[] colour) {
        super(startX, startY, width, height, speed, colour);
    }

    @Override
    public void update_object(float speed) {
        speed_y+=accel_y;
        this.y+=speed_y;
        if(this.x<-1){
            this.x=2+this.x;
        }
        this.x+=speed;
        if (this.y<-1){
            this.y=0.5f;
        }
        if (this.x>1){
            this.x=-2+this.x;
        }
    }
    public void change_speed(){
        speed_y=max_speed_y;
    }
    public float speed_y(){
        return speed_y;
    }
    public float accel_y(){
        return accel_y ;
    }
    public float height(){
        return height ;
    }
}
