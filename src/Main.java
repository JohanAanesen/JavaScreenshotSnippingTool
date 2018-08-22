import javax.swing.*;
import java.awt.*;
import java.awt.AWTException;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author johan
 * todo: Make the program run as background process and listen to a set keystroke to trigger the snipping event :)
 * todo: https://kra.lc/blog/2016/02/java-global-system-hook/
 *
 */
public class Main extends JPanel {

    public static JFrame f;

    public static void main(String[] args) {
        f = new JFrame("Draw Box Mouse 2");
        f.setUndecorated(true);
        f.setBackground(new Color(0, 0, 0, 0));
        f.setOpacity(0.2f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new DrawRect());
        f.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        f.setVisible(true);
    }

    public Main(int x, int y, int width, int height) {
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(x, y, width, height));
            TransferableImage trans = new TransferableImage(image);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(trans, null);
        }
        catch ( AWTException ex ) {
            ex.printStackTrace();
            System.exit( 1 );
        }
    }

    /**
     * Class taken from https://stackoverflow.com/a/4552081
     */
    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage( Image i ) {
            this.i = i;
        }

        public Object getTransferData( DataFlavor flavor )
                throws UnsupportedFlavorException, IOException {
            if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
                return i;
            }
            else {
                throw new UnsupportedFlavorException( flavor );
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported( DataFlavor flavor ) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for ( int i = 0; i < flavors.length; i++ ) {
                if ( flavor.equals( flavors[ i ] ) ) {
                    return true;
                }
            }

            return false;
        }
    }
}
