import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class welcomepage extends JFrame {
    welcomepage() {
        // frame
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        Dimension screenSize = toolkit.getScreenSize();

        // Display the screen width and height
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setSize(screenWidth, screenHeight);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // font
        Font cooperBlackFont = new Font("Cooper Black", Font.BOLD, 50);
        Color smokeWhite = new Color(245, 245, 245, 100);

        // header
        JPanel header;
        header = new JPanel();
        header.setBackground(new Color(0, 0, 0, 100));
        header.setBounds(0, 300, screenWidth, 200);

        // Create a JLabel with HTML text for multiline display
        JLabel name = new JLabel("Self Balancing BST (AVL Trees) project");
//      JLabel name = new JLabel("Welcome to SBBST (AVL) Manav's project");
        name.setBounds(0, 25, 500, 70);
        name.setFont(cooperBlackFont);
        name.setForeground(smokeWhite);
        header.add(name);
        JLabel name1 = new JLabel("Click BELOW to continue");
        name1.setBounds(75, 50, 500, 70);
        name1.setFont(cooperBlackFont);
        name1.setForeground(smokeWhite);
        header.add(name1);

        // background
        ImageIcon background_image = new ImageIcon("G:\\Java Projects\\DSA\\dsa project(manav)\\dsa project\\dsa project 9.jpg"); // Replace with the image path
        JLabel background = new JLabel("", background_image, JLabel.CENTER);
        background.add(header);
        background.setBounds(0, 0, screenWidth, screenHeight);
        add(background);

        // Create a clickable image for "ENTER"
        ImageIcon enterImageIcon = new ImageIcon("G:\\Java Projects\\DSA\\dsa project(manav)\\dsa project\\dsa project 5.png"); // Replace with your image path
        JLabel enterImageLabel = new JLabel(enterImageIcon);
        int buttonWidth = enterImageIcon.getIconWidth();
        int buttonHeight = enterImageIcon.getIconHeight();
        int buttonX = (screenWidth - buttonWidth) / 2;
        int buttonY = header.getY() + header.getHeight();
        enterImageLabel.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        background.add(enterImageLabel);

        // Create a common function for redirection
        ActionListener redirectionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redirectToSsbTree();
            }
        };

        // Add KeyListener to the image
        enterImageLabel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    redirectToSsbTree();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        // Add ActionListener to the image
        enterImageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                redirectToSsbTree();
            }
        });

        setVisible(true);
    }

    private void redirectToSsbTree() {
        dispose();  // Close the WelcomePage
        new ssbtree(); // Call the SsbTree function
    }

    public static void main(String args[]) {
        new welcomepage();
    }
}
