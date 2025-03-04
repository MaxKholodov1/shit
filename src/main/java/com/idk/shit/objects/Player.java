package com.idk.shit.objects;

import com.idk.shit.graphics.Texture;

public class Player extends Object { 
    protected float speed_y=-0.0f;
    protected float max_speed_y=0.06f;
    protected float accel_y=-0.003f;
    private int textureId; // Идентификатор текстуры
    private Texture texture;
    public Player(float startX, float startY, float width, float height, float speed, float[] colour, String texturePath) {
        super(startX, startY, width, height, speed, colour, texturePath);
        this.texture = new Texture(texturePath); // Создаем объект текстуры
        this.textureId = texture.getTextureID();
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
        if(this.x<-1){
            this.x=2+this.x;
        }
        this.x+=speed;
        // if (this.y<-1){
        //     this.y=0.5f;
        // }
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
    @Override
    public void draw() {
            texture.draw(this.x, this.y, this.width, this.height);
    }
}
