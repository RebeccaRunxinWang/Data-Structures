import java.awt.*;

/**
 *  Maze Contents represents the status of a square in a maze
 *
 *  @author  Nicholas R. Howe, John Ridgway
 *  @version CSC 212, 18 April 2018
 */
public enum MazeContents {
  WALL (false, Color.black),
  OPEN (true, Color.white),
  VISITED (false, Color.white),
  DEAD_END (false, new Color(255, 200, 200)),
  START(true, Color.blue),
  FINISH(true, Color.red),
  PATH(false, Color.ORANGE);

  /** Can we visit this square? */
  private boolean traversable;

  /** How to display the square */
  private Color color;

  /** Constructor */
  private MazeContents(boolean traversable, Color color) {
    this.traversable = traversable;
    this.color = color;
  }

  /** Accessor for traversable 
   *  @return true if is traversable
   */
  public boolean isTraversable() {
    return traversable;
  }

  /** Accessor for color 
   * @return color
   */
  public Color getColor() {
    return color;
  }
}
