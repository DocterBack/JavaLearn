/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.specialist;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author user
 */
public class Scene implements Serializable
{
    
    private List<List<Point>> lines = new ArrayList<>();
    
    public void clear()
    {
        lines.clear();
    }
    public void addLine(Point p)
    {
        List<Point> l = new ArrayList<>();
        lines.add(l);
        l.add(p);
    }
    public void addPoint(Point p)
    {
        if (lines.size() > 0) 
            lines.get(lines.size()-1).add(p);
        else
            addLine(p);
    }
    
    public void paint(Graphics g)
    {
        for(List<Point> line : lines)
        {
            Point p1 = line.get(0);
            for(int i =1; i < line.size(); i++)
            {
                Point p2 = line.get(i);
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
                p1 = p2;
            }
        }
    }
    
}
