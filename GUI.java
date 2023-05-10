import edu.princeton.cs.algs4.StdRandom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class GUI {
    private final static Color bg = Color.DARK_GRAY;
    private final static Color fg = Color.WHITE;
    private static JFrame frame;
    private static JPanel contentPane;

    public static void setup() {
        frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 1000));
        frame.setSize(frame.getPreferredSize());
        frame.setResizable(false);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Error with cross platform look/feel in frameSetup().");
        }
        GraphFunctions.init();
        frame.add(createContentPane());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createContentPane() {
        contentPane = new JPanel(new BorderLayout());
            contentPane.add(new DisplayPanel(), BorderLayout.CENTER);
            contentPane.add(createButtonPane(), BorderLayout.NORTH);
            contentPane.setForeground(fg);
            contentPane.setBackground(bg);

        return contentPane;
    }

    private static JPanel createButtonPane() {
        JPanel pane = new JPanel();
            pane.setForeground(fg);
            pane.setBackground(bg);
            pane.setAlignmentX(0.5f);
            pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
            pane.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pane.add(createAddVPanel());

            JTextField vertex1 = new JTextField();
            JTextField vertex2 = new JTextField();
            JTextField eWeight = new JTextField();
                vertex1.setForeground(fg);
                vertex1.setBackground(bg);
                vertex2.setForeground(fg);
                vertex2.setBackground(bg);
                eWeight.setForeground(fg);
                eWeight.setBackground(bg);

                pane.add(vertex1);
                pane.add(vertex2);
                pane.add(eWeight);

        JButton b = new JButton("Add an edge");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(vertex1.getText().equals("") || vertex2.getText().equals("") || eWeight.getText().equals("")) {
                        int v = StdRandom.uniformInt(GraphFunctions.getGraph().V());
                        int w = StdRandom.uniformInt(GraphFunctions.getGraph().V());
                        double weight = StdRandom.uniformInt(0, 100);
                        GraphFunctions.addEdge(v, w, weight);
                    }
                    else {
                        GraphFunctions.addEdge(Integer.parseInt(vertex1.getText()), 
                            Integer.parseInt(vertex2.getText()), 
                            Double.parseDouble(eWeight.getText())); 
                    }
                    contentPane.removeAll();
                    frame.setContentPane(createContentPane());
                    contentPane.repaint();
                    contentPane.revalidate();
                }
            });
            b.setFocusPainted(false);
            b.setFocusable(false);
            b.setForeground(fg);
            b.setBackground(bg);
            pane.add(b);

        JPanel p = new JPanel(new GridLayout(1, 2));
        JPanel subPanel = new JPanel(new GridLayout(2, 1));
        b = new JButton("Reset graph");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    GraphFunctions.reset();
                    contentPane.removeAll();
                    frame.setContentPane(createContentPane());
                    contentPane.repaint();
                    contentPane.revalidate();
                }
            });
            b.setFocusPainted(false);
            b.setFocusable(false);
            b.setForeground(fg);
            b.setBackground(bg);
            subPanel.add(b);

        b = new JButton("Load graph");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    JFileChooser fc = new JFileChooser("~");
                    int returnVal = fc.showOpenDialog(frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        GraphFunctions.loadGraph(fc.getSelectedFile());
                        contentPane.removeAll();
                        frame.setContentPane(createContentPane());
                        contentPane.repaint();
                        contentPane.revalidate();
                    } 
                }
            });
            b.setFocusPainted(false);
            b.setFocusable(false);
            b.setForeground(fg);
            b.setBackground(bg);
            subPanel.add(b);

            p.add(subPanel);
            p.add(createRandomPanel());

            contentPane.add(p, BorderLayout.SOUTH);

        return pane;
    }

    private static JPanel createAddVPanel() {
            JPanel vertPnl = new JPanel(new GridLayout(1, 3));
                vertPnl.setForeground(fg);
                vertPnl.setBackground(bg);
                JLabel verticesLbl = new JLabel("Number of vertices to add: ");
                    verticesLbl.setForeground(fg);
                    verticesLbl.setBackground(bg);
                    vertPnl.add(verticesLbl);
                JTextField addVertices = new JTextField();
                    addVertices.setForeground(fg);
                    addVertices.setBackground(bg);
                    vertPnl.add(addVertices);
                JButton b = new JButton("Add vertices");
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent arg0) {
                            if(addVertices.getText().equals("")) GraphFunctions.addVertex();
                            else {
                                for(int i = 0; i < Integer.parseInt(addVertices.getText()); i++)
                                    GraphFunctions.addVertex();
                            }
                            contentPane.removeAll();
                            frame.setContentPane(createContentPane());
                            contentPane.repaint();
                            contentPane.revalidate();
                        }
                    });
                    b.setFocusPainted(false);
                    b.setFocusable(false);
                    b.setForeground(fg);
                    b.setBackground(bg);
                    vertPnl.add(b);
        return vertPnl;
    }

    private static JPanel createRandomPanel() {
        JPanel randomPanel = new JPanel(new GridLayout(2, 1));
            randomPanel.setForeground(fg);
            randomPanel.setBackground(bg);
            JPanel subPanel = new JPanel(new GridLayout(2, 2));
                    subPanel.setForeground(fg);
                    subPanel.setBackground(bg);
                JLabel veLabel = new JLabel("Number of Vertices:");
                    veLabel.setForeground(fg);
                JTextField vertexCount = new JTextField();
                    vertexCount.setForeground(fg);
                    vertexCount.setBackground(bg);
                JLabel edLabel = new JLabel("Number of edges:");
                    edLabel.setForeground(fg);
                JTextField edgesCount = new JTextField();
                    edgesCount.setForeground(fg);
                    edgesCount.setBackground(bg);
                subPanel.add(veLabel);
                subPanel.add(vertexCount);
                subPanel.add(edLabel);
                subPanel.add(edgesCount);
            randomPanel.add(subPanel);
        JButton b = new JButton("Random graph");
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if(vertexCount.getText().equals("") || edgesCount.getText().equals(""))GraphFunctions.random(10, 20);
                    else GraphFunctions.random(Integer.parseInt(vertexCount.getText()), Integer.parseInt(edgesCount.getText()));
                    contentPane.removeAll();
                    frame.setContentPane(createContentPane());
                    contentPane.repaint();
                    contentPane.revalidate();
                }
            });
            b.setFocusPainted(false);
            b.setFocusable(false);
            b.setForeground(fg);
            b.setBackground(bg);
            randomPanel.add(b);

        return randomPanel;
    }
}
