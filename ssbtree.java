import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class ssbtree extends JFrame {
    class Node {
        int val;
        Node left;
        Node right;
        int h;

        public Node(int val) {
            this.val = val;
            this.h = 1;
        }
    }

    private Node root;
    private final int DIAMETER = 30;
    private final int NODE_SIZE = 75; // Size of the node image
    private final float circleAlpha = 1.0f; // Transparency value

    private JTextField enterDepthNodeField;
    private JLabel depthLabel;

    private JTextField enterNodeField;
    private JTextField enterValueField;
    private JPanel treePanel;

    private Image backgroundImage;
    private Image nodeImage; // Image for nodes
    private Font boldFont;

    public ssbtree() {
        root = null;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size
        Dimension screenSize = toolkit.getScreenSize();

        // Display the screen width and height
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        initUI();

        // Load the background image
        backgroundImage = Toolkit.getDefaultToolkit().createImage("G:\\Java Projects\\DSA\\dsa project(manav)\\dsa project\\dsa project 6.jpg");

        // Load the image for nodes (replace with your image path)
        nodeImage = Toolkit.getDefaultToolkit().createImage("G:\\Java Projects\\DSA\\dsa project(manav)\\dsa project\\dsa project 5.png");
        boldFont = new Font("Cooper Black", Font.BOLD, 18);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public Node inpRoot(int val) {
        Node root = new Node(val);
        return root;
    }
    //creates a root
    public Node inpNode(int val) {
        Node side = new Node(val);
        return side;
    }
    //creates and returns a new node
    public int height(Node node) {
        if (node == null) return 0;
        return node.h;
    }
    //
    public void updateHeight(Node node) {
        node.h = Math.max(height(node.left), height(node.right)) + 1;
    }

    public Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    public Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }
    
    public Node balance(Node root, int val) {
        updateHeight(root);
        int balance = getBalance(root);

        if (balance > 1) {
            if (val < root.left.val) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }

        if (balance < -1) {
            if (val > root.right.val) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }

        return root;
    }

    public Node insert(Node root, int val) {
        if (root == null) {
            return new Node(val);
        }

        if (val < root.val) {
            root.left = insert(root.left, val);
        } else if (val > root.val) {
            root.right = insert(root.right, val);
        }

        return balance(root, val);
    }

    public void buildTree(List<Integer> values) {
        for (int val : values) {
            root = insert(root, val);
        }
        treePanel.repaint();
    }

    public int getBalance(Node node) 
    {
        if (node == null) 
        {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    public void drawTree(Graphics2D g, Node node, int x, int y, int xOffset) 
    {
        if (node == null) 
        {
            return;
        }
    
        int radius = DIAMETER / 2;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, circleAlpha)); // Set transparency
    
        // Draw the image at the node position (centered)
        g.drawImage(nodeImage, x - NODE_SIZE / 2, y - NODE_SIZE / 2, NODE_SIZE, NODE_SIZE, this);
    
        // Set the font to bold
        g.setFont(boldFont);
    
        g.setColor(Color.white);
        g.setFont(boldFont);
    
        // Center the value text within the node image
        g.drawString(String.valueOf(node.val), x - NODE_SIZE / 4, y + NODE_SIZE / 4);
    
        if (node.left != null) {
            Point leftPos = new Point(x - xOffset, y + 100);
            g.drawLine(x, y + radius, leftPos.x + radius, leftPos.y - radius);
            drawTree(g, node.left, leftPos.x, leftPos.y, xOffset / 2);
        }
    
        if (node.right != null) {
            Point rightPos = new Point(x + xOffset, y + 100);
            g.drawLine(x, y + radius, rightPos.x + radius, rightPos.y - radius);
            drawTree(g, node.right, rightPos.x, rightPos.y, xOffset / 2);
        }
    }
    
    private void onEnterNode() {
        String nodeValue = enterNodeField.getText();
        int value = Integer.parseInt(nodeValue);

        if (root == null) {
            root = inpRoot(value);
        } else {
            root = insert(root, value);
        }

        treePanel.repaint();

        // Clear the text field
        enterNodeField.setText("");
    }

    private void onBuildTree() {
        String values = enterValueField.getText();
        String[] valArray = values.split(" ");

        List<Integer> enteredValues = new ArrayList<>();
        for (String val : valArray) {
            enteredValues.add(Integer.parseInt(val));
        }

        buildTree(enteredValues);
        treePanel.repaint();

        // Clear the text field
        enterValueField.setText("");
    }

    private void onGetDepth() {
        String nodeValue = enterDepthNodeField.getText();
        int value = Integer.parseInt(nodeValue);

        int depth = findDepth(root, value, 1) - 1; // Start with depth 1
        depthLabel.setText("Depth of the selected node: " + depth);

        // Clear the text field
        enterDepthNodeField.setText("");
    }

    private int findDepth(Node node, int value, int depth) {
        if (node == null) {
            return 0;
        }

        if (node.val == value) {
            return depth;
        }

        int leftDepth = findDepth(node.left, value, depth + 1);
        if (leftDepth != 0) {
            return leftDepth;
        }

        int rightDepth = findDepth(node.right, value, depth + 1);
        return rightDepth;
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel enterNodePanel = new JPanel();
        JLabel enterNodeLabel = new JLabel("Enter Node Value:");
        enterNodeField = new JTextField(5);

        enterNodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onEnterNode();
                }
            }
        });
        JLabel enterNodeNote = new JLabel("Press Enter to Enter the Value");
        enterNodePanel.add(enterNodeLabel);
        enterNodePanel.add(enterNodeField);
        enterNodePanel.add(enterNodeNote);

        JPanel enterValuePanel = new JPanel();
        JLabel enterValueLabel = new JLabel("Enter values separated by space:");
        enterValueField = new JTextField(20);

        enterValueField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onBuildTree();
                }
            }
        });

        enterValuePanel.add(enterValueLabel);
        enterValuePanel.add(enterValueField);

        JPanel enterDepthNodePanel = new JPanel();
        JLabel enterDepthNodeLabel = new JLabel("Enter Node Value for Depth:");
        enterDepthNodeField = new JTextField(5);

        enterDepthNodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onGetDepth();
                }
            }
        });

        enterDepthNodePanel.add(enterDepthNodeLabel);
        enterDepthNodePanel.add(enterDepthNodeField);

        depthLabel = new JLabel("Depth of the selected node: ");
        enterDepthNodePanel.add(depthLabel);

        treePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                if (root != null) {
                    drawTree((Graphics2D) g, root, getWidth() / 2, 30, 300);
                }
            }
        };
        treePanel.setPreferredSize(new Dimension(800, 600));

        add(treePanel, BorderLayout.CENTER);
        add(enterNodePanel, BorderLayout.NORTH);
        add(enterValuePanel, BorderLayout.SOUTH);
        add(enterDepthNodePanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ssbtree();
        });
    }
}
