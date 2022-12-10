package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.*;

public class Viewer extends JFrame implements ActionListener {
    JButton button = new JButton("submit");
    JTextArea textArea = new JTextArea("link here");
    JTextArea outArea = new JTextArea( 10, 40);

    public JTextArea getOutArea() {
        return outArea;
    }

    public void setOutArea(JTextArea outArea) {
        this.outArea = outArea;
    }



    // Create a new JComboBox with the options
    JComboBox comboBox;
    public Viewer(){
        ImageIcon img = new ImageIcon("icon.png");
        this.setIconImage(img.getImage());

        setTitle("Miventana");
        setMinimumSize(new Dimension(500,500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setVisible(true);
        iniciarComponentes();
        pack();

    }
    public void iniciarComponentes(){

        button.addActionListener(this);

        JPanel panel = new JPanel(new GridBagLayout());
        JScrollPane spane = new JScrollPane(panel);
        GridBagConstraints c = new GridBagConstraints();
        spane.createHorizontalScrollBar();
        c.gridx =0;
        c.gridy=0;
        panel.add(button,c);
        c.gridx =0;
        c.gridy=1;
        textArea.setColumns(10);
        textArea.setRows(5);
        panel.add(textArea,c);
        c.gridx =0;
        c.gridy=2;
        //Not Available:

        String[] options = {  "mp4","m4a",/*"3gp", "aac", "flv", "mp3", "ogg", "wav", "webm"*/};
        comboBox = new JComboBox<>(options);
        panel.add(comboBox,c);
        c.gridx =0;
        c.gridy=3;


        JScrollPane scrollPane = new JScrollPane(outArea);
        outArea.setEditable(true);
        panel.add(scrollPane,c);

        getContentPane().add(panel);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button) {

// The source file
            Path source = Paths.get("youtube-dl.exe");

// The target directory (in this case, the temp directory)
            Path target = Paths.get(System.getProperty("java.io.tmpdir")).resolve("youtube-dl.exe");

// Copy the file
            try {
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String[] argumentos = new String[] {"cmd.exe" ,"/c", target  +" -o \"./%(title)s-%(id)s.%(ext)s\" " +"-f "+comboBox.getSelectedItem()+" "+textArea.getText()};
            for (int i = 0; i < argumentos.length; i++) {
                System.out.println(argumentos[i]);
            }
            Controller controller = new Controller();
            controller.setViewer(this);
            controller.setPath(argumentos);
            Model model = new Model(controller);
            new Thread(model).start();

        }
    }
}
