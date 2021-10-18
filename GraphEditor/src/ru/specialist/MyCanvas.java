/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.specialist;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Student
 */
public class MyCanvas extends Canvas
{
    
    private List<Point> scene;
    public MyCanvas(List<Point> scene)
    {
        this.scene = scene;
    }

    @Override
    public void paint(Graphics g) {
        if (scene != null && scene.size()>1)
        {
            for(int i=0; i < scene.size()-1; i++)
            {
                Point pp = scene.get(i);
                Point p = scene.get(i+1);
                g.drawLine(pp.x, pp.y, p.x, p.y);
            }
        
        }
        
        
        
    }
    
    
    
}
