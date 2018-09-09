import java.awt.*;

/**
 *  A class that will be the data for a node, and which holds location
 *  and color information as well as the actual node data.
 *
 */
public class MazeData {
  /** the node data */
  private MazeContents data;

  /** location x */
  private int x;

  /** location y */
  private int y;

  public MazeData(MazeContents data, int x, int y) {
    this.data = data;
    this.x = x;
    this.y = y;
  }

  public int getX() { return x; }
  public int getY() { return y; }
  public void setData(MazeContents data) { this.data = data;}
  public void setX(int x) { this.x = x; }
  public void setY(int y) { this.y = y; }
  public MazeContents getData() { return this.data; }

  public String toString() { return data.toString() + "@(" + x + "," + y + ")"; }
}
