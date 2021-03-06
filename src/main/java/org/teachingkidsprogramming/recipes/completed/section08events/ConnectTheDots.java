package org.teachingkidsprogramming.recipes.completed.section08events;

import java.awt.Color;

import org.teachingextensions.logo.Tortoise;
import org.teachingextensions.logo.utils.ColorUtils.ColorWheel;
import org.teachingextensions.logo.utils.ColorUtils.PenColors;
import org.teachingextensions.logo.utils.EventUtils.MouseLeftClickListener;
import org.teachingextensions.logo.utils.EventUtils.MouseRightClickListener;
import org.teachingextensions.logo.utils.LineAndShapeUtils.Circle;
import org.teachingextensions.logo.utils.LineAndShapeUtils.Text;

public class ConnectTheDots implements MouseRightClickListener, MouseLeftClickListener
{
  public static void main(String[] args)
  {
    //Create new a 'Connect the Dots' object.
    new ConnectTheDots();
  }
  public ConnectTheDots()
  {
    Tortoise.show();
    // Listen for right clicks on the window for the tortoise 
    Tortoise.getBackgroundWindow().addMouseRightClickListener(this);
    // Listen for left clicks on the window for the tortoise 
    Tortoise.getBackgroundWindow().addMouseLeftClickListener(this);
    //Make the Tortoise go as fast as possible.
    Tortoise.setSpeed(10);
    //  clearTheScreen (recipe below)
    clearTheScreen();
    //  prepareColorPalette (recipe below)
    prepareColorPalette();
  }
  private static void prepareColorPalette()
  {
    //  ------------- Recipe for prepareColorPalette 
    //
    //   Add red to the color wheel
    ColorWheel.addColor(PenColors.Reds.Red);
    //   Add green to the color wheel
    ColorWheel.addColor(PenColors.Greens.Green);
    //   Add blue to the color wheel
    ColorWheel.addColor(PenColors.Blues.Blue);
    //   Add purple to the color wheel
    ColorWheel.addColor(PenColors.Purples.Purple);
    //   Add pink to the color wheel
    ColorWheel.addColor(PenColors.Pinks.Pink);
    //   Add teal to the color wheel
    ColorWheel.addColor(PenColors.Greens.Teal);
  }
  private void addDot(int x, int y)
  {
    //  ------------- Recipe for addDot
    //   addACircle (recipe below)
    addCircle(x, y);
    //   Move the tortoise to the current position of the mouse # 8
    Tortoise.moveTo(x, y);
  }
  private void addCircle(int x, int y)
  {
    //  ------------- Recipe for addACircle
    //   The width of the circle is 15
    int radius = 7;
    //   Change the color for the next shape to the next color from the color wheel
    Color color = ColorWheel.getNextColor();
    //   Create a circle
    Circle circle = new Circle(radius, color);
    //   Change the circle to be 40% opaque
    circle.setTransparency(60);
    //   Move the center of the circle to the current position of the mouse
    circle.setCenter(x, y);
    circle.addTo(Tortoise.getBackgroundWindow());
  }
  private static void clearTheScreen()
  {
    //  ------------- Recipe for clearTheScreen
    //   Clear the Program Window
    Tortoise.clear();
    //   Write "Right click to clear" on the screen at position 100, 100
    new Text("Right click to clear").setTopLeft(100, 100).addTo(Tortoise.getBackgroundWindow());
  }
  @Override
  public void onRightMouseClick(int x, int y)
  {
    clearTheScreen();
  }
  @Override
  public void onLeftMouseClick(int x, int y)
  {
    addDot(x, y);
  }
}
