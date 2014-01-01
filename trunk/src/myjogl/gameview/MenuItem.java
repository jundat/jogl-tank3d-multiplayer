/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myjogl.gameview;

import com.sun.opengl.util.texture.Texture;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JOptionPane;
import myjogl.Global;
import myjogl.utils.Renderer;

/**
 *
 * @author Jundat
 */
public class MenuItem {

    public boolean isClicked = false;
    public boolean isOver = false;
    public Rectangle rect = new Rectangle();
    private Texture ttNormal = null;
    private Texture ttClick = null;

    public MenuItem(Texture ttNormal, Texture ttClick) {
        this.ttNormal = ttNormal;
        this.ttClick = ttClick;

        if (ttNormal == null && ttClick == null) {
            JOptionPane.showMessageDialog(null, "Can not create MenuItem");
        }
    }

    public void SetPosition(Point p) {
        rect.x = p.x;
        rect.y = p.y;
        if (ttNormal != null) {
            rect.width = this.ttNormal.getWidth();
            rect.height = this.ttNormal.getHeight();
        } else if (ttClick != null) {
            rect.width = this.ttClick.getWidth();
            rect.height = this.ttClick.getHeight();
        }
    }

    public void SetPosition(int x, int y) {
        SetPosition(new Point(x, y));
    }

    public void Render() {
        if ((isClicked == true || isOver == true) && ttClick != null) {
            Renderer.Render(this.ttClick, rect.x, rect.y);
        } else if (ttNormal != null) {
            Renderer.Render(this.ttNormal, rect.x, rect.y);
        }
    }

    public void setIsClick(boolean isClicked) {
        this.isClicked = isClicked;
    }
    
    public void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }

    //
    public boolean contains(int x, int y) {
        Rectangle newrect = new Rectangle((int)(rect.x * Global.scaleFactorX), 
                (int)(rect.y * Global.scaleFactorY), 
                (int)(rect.width * Global.scaleFactorX), 
                (int)(rect.height * Global.scaleFactorY));
        return newrect.contains(x, y);
    }
}
