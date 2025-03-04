package com.idk.shit.objects;

import com.idk.shit.graphics.Texture;
import com.idk.shit.graphics.TextureCache;

public class Object  {
    protected float x,y;
    protected  float width, height; // Размеры игрока
    protected float speed;   // Скорость перемещения игрока
    protected float[] colour;
    private int textureId; // Идентификатор текстуры
    private Texture texture;
    public Object(float startX, float startY, float width, float height, float speed, float[] colour, String texturePath) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.colour = colour;
        this.texture = TextureCache.getTexture(texturePath); // Используем кэш текстур и используем из Map 
        this.textureId = texture.getTextureID();

    }
    public float getLeft() {
        return x - width / 2;
    }
    
    public float getRight() {
        return x + width / 2;
    }
    
    public float getBottom() {
        return y - height / 2;
    }
    
    public float getTop() {
        return y + height / 2;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public float getSpeed_x(){
        return this.speed;
    }
    public void SetY(float y){
        this.y=y;
    }
    public void update_object(float speed) {
        // Обновление позиции игрока на основе нажатых клавиш
        this.x+=speed;
        if (this.x+width/2>=1 || this.x-width/2<=-1){
            this.speed=-speed;
        }
    }
    public enum Direction {
        LEFT, 
        RIGHT, 
        UP, 
        DOWN,
        NONE
    }
    public void collision(Player a) {
        Direction dir = Direction.NONE;
        float dist=1f;
        float difx=(float)(-this.getSpeed_x()+a.getSpeed_x());
        float dify=(float)(a.speed_y()+a.accel_y());
        if (this.getRight()<a.getLeft() || this.getLeft()>a.getRight() ||  this.getBottom()>a.getTop()|| this.getTop()<a.getBottom()){
            if(!(a.getBottom()>this.getTop()||a.getTop()<this.getBottom())){
                if ((this.getRight()-a.getLeft())/difx>0f && (this.getRight()-a.getLeft())/difx<=dist){
                    dir = Direction.RIGHT; 
                    dist=(this.getRight()-a.getLeft())/difx;
                }
                if ((this.getLeft()-a.getRight()/difx)>0f && (this.getLeft()-a.getRight())/difx<=dist){
                    dir = Direction.LEFT; 
                    dist=(this.getLeft()-a.getRight())/difx;
                }
            }
            if (!(a.getRight()<this.getLeft()||a.getLeft()>this.getRight())) {
                if ((this.getTop()-a.getBottom())/dify>0f && (this.getTop()-a.getBottom())/dify<=dist){
                    dir = Direction.UP; 
                    dist=(this.getTop()-a.getBottom())/dify;
                }
                if ((this.getBottom()-a.getTop())/dify>0f && (this.getBottom()-a.getTop())/dify<=dist){
                    dir = Direction.DOWN; 
                    dist=(this.getBottom()-a.getTop())/dify;
                }
            }
        }
        if (dir == Direction.UP) {
            a.change_speed();
            a.y=this.getTop()+a.height/2;
        }
        dir = Direction.NONE;
    }
    
    
    public void draw(){
        texture.draw(this.x, this.y,this.width, this.height ); // рисуем текстуру используя draw от класса текстур
    }
}
