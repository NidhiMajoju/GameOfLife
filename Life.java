import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
    this is the Controller component
 */

class Life extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private LifeView view;
    private LifeModel model;
    private JButton runButton, pauseButton, resumeButton, resetButton, randomButton;
    private String fileName;
    private boolean color;
    /** construct a randomized starting grid */
    Life() throws IOException
    {
        this(null);
    }

    /** construct a grid using the info in text file */
    Life(String fileName) throws IOException
    {
        super("blinker.lif");
        this.fileName = fileName;
        color = true;

        // build the buttons
        JPanel controlPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));
        runButton = new JButton("Run");
        runButton.addActionListener(this);
        runButton.setEnabled(true);
        controlPanel.add(runButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        pauseButton.setEnabled(false);
        controlPanel.add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        resumeButton.setEnabled(false);
        controlPanel.add(resumeButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        resetButton.setEnabled(true);
        controlPanel.add(resetButton);

        randomButton = new JButton("Random Colors");
        randomButton.addActionListener(this);
        randomButton.setEnabled(true);
        controlPanel.add(randomButton);



        // build the view
        view = new LifeView();
        view.setBackground(Color.white);

        // put buttons, view together
        Container c = getContentPane();
        c.add(controlPanel, BorderLayout.NORTH);
        c.add(view, BorderLayout.CENTER);

        // build the model
        model = new LifeModel(view, fileName);

    }

    public void actionPerformed(ActionEvent e)
    {
        JButton b = (JButton)e.getSource();
        if ( b == runButton )
        {
            model.run();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
        }
        else if ( b == pauseButton )
        {
            model.pause();
            runButton.setEnabled(false);
            pauseButton.setEnabled(false);
            resumeButton.setEnabled(true);
        }
        else if ( b == resumeButton )
        {
            model.resume();
            runButton.setEnabled(false);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
        }
        else if (b==resetButton)
        {
            try {
                model.reset(view, fileName);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            runButton.setEnabled(true);
            pauseButton.setEnabled(true);
            resumeButton.setEnabled(false);
        }
        else if (b==randomButton)
        {
            color = !color;
            if (color == false)
            {
                randomButton.setText(" Mono Color");
            }
            else
            {
                randomButton.setText("Random Color");
            }
            view.isRandom (color);


        }



    }

    public static void main(String[] args) throws IOException
    {
        Life conway = new Life(); //parameterize to start w/ a particular file

        conway.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        conway.setSize(570, 640);
        //conway.show(); //deprecated, added call below
        conway.setVisible(true);
    }
}