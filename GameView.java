import java.awt.Canvas;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;

import javax.imageio.ImageIO;

import java.io.File;

import java.util.TreeMap;

/** This class contains the window in which
 * the game is rendered. It sits inside the
 * main window and is called to render the
 * game whenever the frame timer expires.
 * @see Game
 * @see GameUi
 * */
public class GameView extends Canvas {

  /** Used to render the game. */
  private GameRenderer renderer;

  /** The tile image to tile ID map. */
  private TreeMap<Integer, BufferedImage> tileImageMap;

  /** This constructs a new instance of the game view.
   * Internally, this just makes sure that the window
   * is visible and 'repaint' events from the OS are
   * set to be ignored.
   * */
  public GameView() {
    setIgnoreRepaint(true);
    setVisible(true);
    renderer = new GameRenderer();
    tileImageMap = new TreeMap<Integer, BufferedImage>();
  }

  /** Renders the game onto the window.
   * @param game The game to be rendered.
   * */
  public void render(Game game) {

    BufferStrategy strategy = lazyInitBufferStrategy();

    // This loop is suggested in the Oracle docs,
    // although the exact reason for it is slightly unclear (to me.)
    //
    // Here's the explaination from: https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
    //
    // The following loop ensures that the contents of the drawing buffer
    // are consistent in case the underlying surface was recreated
    do {

      do {

        Graphics graphics = strategy.getDrawGraphics();

        int w = getWidth();
        int h = getHeight();

        GraphicsContextAwt gc = new GraphicsContextAwt(graphics, w, h);

        renderer.render(gc, game);

        graphics.dispose();

      } while (strategy.contentsRestored());

      strategy.show();

    } while (strategy.contentsLost());
  }

  /** Loads a tile image.
   * @param id The ID to assign the tile image.
   * @param imagePath The path to the image to load.
   * */
  public void loadTileImage(int id, String imagePath) {

    BufferedImage image = null;

    try {
      ImageIO.read(new File(imagePath));
    } catch (Exception e) { }

    tileImageMap.put(id, image);
  }

  /** This initializes the swap buffer when it is needed.
   * It should only be called right before the game is rendered.
   * @return The swap buffer instance that's either brand new or
   * already created in a previous call to this function.
   * */
  private BufferStrategy lazyInitBufferStrategy() {
    if (getBufferStrategy() == null) {
      createBufferStrategy(2);
    }
    return getBufferStrategy();
  }
}
