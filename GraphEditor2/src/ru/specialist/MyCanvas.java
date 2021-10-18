/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.specialist;

import java.awt.*;

/**
 *
 * @author user
 */
public class MyCanvas extends Canvas
{
    private Scene scene;
    
    public MyCanvas(Scene scene)
    {
        this.scene = scene;
    }
    
    @Override
    public void paint(Graphics g) {
        if (scene != null)
            scene.paint(g);
    }

    /**
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @param scene the scene to set
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
