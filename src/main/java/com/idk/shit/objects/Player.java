package com.idk.shit.objects;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

import com.idk.shit.graphics.Texture;
// import com.idk.shit.graphics.Shader;
public class Player extends Object { 
    protected float speed_y=-0.0f;
    protected float max_speed_y=0.07f;
    protected float accel_y=-0.003f;
    private float screen_width=650;
    private float screen_height=1000;
    private float RATIO=screen_width/screen_height;

    public Player(float startX, float startY, float width, float height, float speed, float[] colour, Texture texture) {
        super(startX, startY, width, height, speed, colour, texture);
    }
    public boolean fall_down(){
        if(this.y<-1){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void update_object(float speed) {
        speed_y+=accel_y;
        this.y+=speed_y;
        if(this.x<-RATIO){
            this.x=2*RATIO+this.x;
        }
        this.x+=speed;
        // if (this.y<-1){
        //     this.y=0.5f;
        // }
        if (this.x>RATIO){
            this.x=-2*RATIO+this.x;
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
    // @Override
    // public void draw() {
    //     glColor3f(0,0,0);
    //     glBegin(GL_QUADS);

    //     glVertex2f(x + width / 2f, y + height / 2f);
    //     glVertex2f(x + width / 2f, y - height / 2f);
    //     glVertex2f(x - width / 2f, y - height / 2f);
    //     glVertex2f(x - width / 2f, y + height / 2f);

    //     glEnd();

    // }
}
