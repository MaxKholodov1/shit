package com.idk.shit.ui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;



public class button {
    protected float screen_height=800;
    protected float screen_width=500;

    private float x, y, width, height;
    private String label;
    private boolean isHovered = false;
    private boolean isClicked = false;
    private float[] color; 
    private long vg; 

    // TextRenderer textRenderer;


    public button(float x, float y, float width, float height, String label,float[] color,  long vg ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.color = color;
        this.vg=vg;
        // this.textRenderer = new TextRenderer(x, y, label, Colours.BROWN, vg, height, width); // Создаем один раз

    }

    public float left() {
        return x - width / 2;
    }
    
    public float right() {
        return x + width / 2;
    }
    
    public float bottom() {
        return y - height / 2;
    }
    
    public float top() {
        return y + height / 2;
    }
    public float x() {
        return x;
    }

    public float y() {
        return y;
    }
    public void draw() {
        if (isHovered) {
            glColor3f(color[0] * 0.8f, color[1] * 0.8f, color[2] * 0.8f); 
        } else {
            glColor3f(color[0], color[1], color[2]); 
        }
    
        glBegin(GL11.GL_QUADS);
            glVertex2f(x - width / 2, y - height / 2); 
            glVertex2f(x + width / 2, y - height / 2); 
            glVertex2f(x + width / 2, y + height / 2); 
            glVertex2f(x - width / 2, y + height / 2); 
        glEnd();
        // this.textRenderer.drawText( );
    }
    
   
        
    public void update(long window) {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];
        glfwGetCursorPos(window, mouseX, mouseY);
        
        float normX = (float) ((mouseX[0] / screen_width) * 2 - 1);  
        float normY = (float) (1 - (mouseY[0] / screen_height) * 2);  

        isHovered = (normX >= x - width/2  && normX <= x + width/2&&
                    normY >= y - height / 2 && normY <= y + height / 2);
        isClicked = isHovered && glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_LEFT) == GLFW_PRESS;
    }

    public boolean isClicked() {
        return isClicked;
    }
   
}
