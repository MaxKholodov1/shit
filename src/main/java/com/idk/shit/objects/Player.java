package com.idk.shit.objects;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

// import com.idk.shit.graphics.Texture;

public class Player extends Object { 
    protected float speed_y=-0.0f;
    protected float max_speed_y=0.06f;
    protected float accel_y=-0.003f;
    // private int textureId; // Идентификатор текстуры
    // private Texture texture;
    public Player(float startX, float startY, float width, float height, float speed, float[] colour) {
        super(startX, startY, width, height, speed, colour);
        // this.texture = new Texture(texturePath); // Создаем объект текстуры
        // this.textureId = texture.getTextureID();
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
        // texture.draw(this.x, this.y, this.width, this.height);
         GL11.glColor4f(0, 0, 0, 1);

        // Начинаем рисовать квадрат с текстурой
        glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0); glVertex2f(x - width / 2, y - height / 2);
            GL11.glTexCoord2f(1, 0); glVertex2f(x + width / 2, y - height / 2);
            GL11.glTexCoord2f(1, 1); glVertex2f(x + width / 2, y + height / 2);
            GL11.glTexCoord2f(0, 1); glVertex2f(x - width / 2, y + height / 2);
        }
        glEnd();
    }
}
