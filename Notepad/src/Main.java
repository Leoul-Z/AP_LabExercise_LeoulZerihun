import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main {

    JFrame myframe;
    JTextArea textArea;
    JScrollPane scrollPanel;
    JMenuBar MenuBar;
    JMenu fileMenu, editMenu, formatMenu, colorMenu;
    JMenuItem iNew, iOpen, iSave, iSaveas,iExit, iRedo, iUndo, iFont, iSize, iBlack, iWhite;
    public static void main(String[] args) throws Exception {

        new Main();
    }


    public Main(){
        createPanel();
        createTextarea();
        createMenuBar();
        createFileMenuItem();
        createEditMenuItem();
        createFormatMenuItem();
        createColorMenuItem();

        myframe.setVisible(true);
    }

    public void createPanel(){
        myframe = new JFrame("Notepad");
        myframe.setSize(800, 600);
        myframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void createTextarea(){
        textArea = new JTextArea();
        scrollPanel= new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        myframe.add(scrollPanel);
    }

    public void createMenuBar(){
        MenuBar = new JMenuBar();
        myframe.setJMenuBar(MenuBar);

        fileMenu = new JMenu("File");
        MenuBar.add(fileMenu);
        editMenu = new JMenu("Edit");
        MenuBar.add(editMenu);
        formatMenu = new JMenu("Format");
        MenuBar.add(formatMenu);
        colorMenu = new JMenu("Color");
        MenuBar.add(colorMenu);
    }

    public void createFileMenuItem(){
        iNew = new JMenuItem("New");
        fileMenu.add(iNew);
        iOpen = new JMenuItem("Open");
        fileMenu.add(iOpen);
        iSave = new JMenuItem("Save");
        fileMenu.add(iSave);
        iSaveas = new JMenuItem("Saveas");
        fileMenu.add(iSaveas);
        iExit = new JMenuItem("Exit");
        fileMenu.add(iExit);
    }
    public void createEditMenuItem(){
        iUndo= new JMenuItem("Undo");
        editMenu.add(iUndo);
        iRedo= new JMenuItem("Redo");
        editMenu.add(iRedo);
    }

    public void createFormatMenuItem(){
        iFont = new JMenuItem("Font Family");
        formatMenu.add(iFont);
        iSize = new JMenuItem("Font Size");
        formatMenu.add(iSize);
    }

    public void createColorMenuItem(){
        iBlack = new JMenuItem("Black");
        colorMenu.add(iBlack);
        iWhite = new JMenuItem("White");
        colorMenu.add(iWhite);
    }



}

