package com.idk.shit;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextBounds;


public class TextRenderer {
    protected float screen_height=800;
    protected float screen_width=500;
    private float x;
    private float y;
    private String label;
    private float[] color; 
    private long vg; 
    private float max_height;
    private float max_width;

    public TextRenderer(float x, float y, String label,float[] color,  long vg, float max_height,float  max_width ) {
        this.x = (x+1)/2*screen_width;
        this.y =(1-(y+1)/2) *screen_height;
        this.label = label;
        this.color = color;
        this.vg=vg;
        this.max_height=max_height/2*screen_height;
        this.max_width=max_width/2*screen_width;
    }
    private float  calc( float max_width,float  max_height){
        float fontSize = 60.0f; 
        float[] bounds = new float[4];

        while (true) {
            nvgFontSize(vg, fontSize);
            nvgTextBounds(vg, 0, 0, label, bounds);

            float textWidth = bounds[2] - bounds[0]; // Ширина текста
            float textHeight = bounds[3] - bounds[1]; // Высота текста

            if (textWidth <= max_width && textHeight <= max_height) {
                break; // Текст помещается в заданные размеры
            }

            fontSize -= 1.0f; // Уменьшаем размер шрифта

            if (fontSize <= 0) {
                throw new RuntimeException("Не удалось подобрать размер шрифта для текста: " + label);
            }
        }
        return fontSize;
    }
    public void drawText() {
        nvgBeginFrame(vg, screen_width, screen_height, 1); // Начинаем фрейм
        float fontSize = calc( max_width, max_height);
        nvgFontSize(vg, fontSize);
        // String fontPath = "C:/Users/maksh/Desktop/python/shit/src/main/java/com/idk/shit/Roboto_Condensed-MediumItalic.ttf";
        String fontPath="";
        try {
            URL fontUrl = Main.class.getResource("/com/idk/shit/Roboto_Condensed-MediumItalic.ttf");

            if (fontUrl == null) {
                throw new RuntimeException("Файл шрифта не найден в ресурсах!");
            }

             fontPath = Paths.get(fontUrl.toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace(); 
        }
        int font = NanoVG.nvgCreateFont(vg, "roboto", fontPath);
        if (font == -1) {
            throw new RuntimeException("Не удалось загрузить шрифт: " + fontPath);
        }
        nvgFontFace(vg, "roboto");    

        NVGColor color1 = NVGColor.create();
        color1.r(0.0f);  // Красный
        color1.g(0.0f);  // Зеленый
        color1.b(0.0f);  // Синий
        color1.a(1.0f);  // Альфа (непрозрачность)
        nvgFillColor(vg, color1);

        float[] bounds = new float[4];
        nvgTextBounds(vg, 0, 0, label, bounds);
        float textWidth = bounds[2] - bounds[0]; // Ширина текста
        float textHeight = bounds[3] - bounds[1]; // Высота текста


        nvgText(vg, this.x-textWidth/2, this.y+textHeight/2, label);
    
        nvgEndFrame(vg);
    }
}