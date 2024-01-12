import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

class Task extends JPanel {
    JLabel index;
    JTextField taskName;
    JButton done;

    Color darkPurple = new Color(75, 0, 130);
    Color doneColor = new Color(0, 0, 255);
    Color blueColor = new Color(0, 0, 255);
    Color whiteColor = Color.WHITE;

    private boolean checked;

    Task() {
        this.setPreferredSize(new Dimension(400, 20));
        this.setBackground(darkPurple);

        this.setLayout(new BorderLayout());

        checked = false;

        index = new JLabel("");
        index.setPreferredSize(new Dimension(20, 20));
        index.setHorizontalAlignment(JLabel.CENTER);
        this.add(index, BorderLayout.WEST);

        taskName = new JTextField("Write something..");
        taskName.setBorder(BorderFactory.createEmptyBorder());
        taskName.setBackground(darkPurple);

        this.add(taskName, BorderLayout.CENTER);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(80, 20));
        done.setBorder(BorderFactory.createEmptyBorder());
        done.setBackground(doneColor);
        done.setFocusPainted(false);

        this.add(done, BorderLayout.EAST);
    }

    public void changeIndex(int num) {
        this.index.setText(num + "");
        this.revalidate();
    }

    public JButton getDone() {
        return done;
    }

    public boolean getState() {
        return checked;
    }

    public void changeState() {
        this.setBackground(doneColor);
        taskName.setBackground(doneColor);
        checked = true;
        revalidate();
    }

    public void setWhiteBackground() {
        this.setBackground(whiteColor);
        taskName.setBackground(whiteColor);
    }
}

class List extends JPanel {
    Color darkPurple = new Color(75, 0, 130);

    List() {
        GridLayout layout = new GridLayout(10, 1);
        layout.setVgap(5);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(400, 560));
        this.setBackground(darkPurple);
    }

    public void updateNumbers() {
        Component[] listItems = this.getComponents();

        for (int i = 0; i < listItems.length; i++) {
            if (listItems[i] instanceof Task) {
                ((Task) listItems[i]).changeIndex(i + 1);
            }
        }
    }

    public void removeCompletedTasks() {
        for (Component c : getComponents()) {
            if (c instanceof Task) {
                if (((Task) c).getState()) {
                    remove(c);
                    updateNumbers();
                }
            }
        }
    }

    public void addTask(Task task) {
        add(task);
        task.setWhiteBackground();
        updateNumbers();
    }
}

class Footer extends JPanel {
    JButton addTask;
    JButton clear;
    JLabel developedBy;

    Color darkPurple = new Color(75, 0, 130);
    Color blueColor = new Color(0, 0, 255);
    Color whiteColor = Color.WHITE;
    Border emptyBorder = BorderFactory.createEmptyBorder();

    Footer() {
        this.setPreferredSize(new Dimension(400, 60));
        this.setBackground(darkPurple);

        addTask = new JButton("Add Task");
        addTask.setBorder(emptyBorder);
        addTask.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        addTask.setVerticalAlignment(JButton.BOTTOM);
        addTask.setBackground(blueColor);
        addTask.setForeground(whiteColor);
        this.add(addTask);

        this.add(Box.createHorizontalStrut(20));

        clear = new JButton("Clear Finished Tasks");
        clear.setFont(new Font("Sans-serif", Font.ITALIC, 20));
        clear.setBorder(emptyBorder);
        clear.setBackground(blueColor);
        clear.setForeground(whiteColor);
        this.add(clear);

        this.add(Box.createHorizontalStrut(20));

        developedBy = new JLabel("Developed by Pratik Tikhe");
        developedBy.setFont(new Font("Sans-serif", Font.PLAIN, 10));
        developedBy.setForeground(whiteColor);
        this.add(developedBy);
    }

    public JButton getAddTask() {
        return addTask;
    }

    public JButton getClear() {
        return clear;
    }
}

class TitleBar extends JPanel {
    Color darkPurple = new Color(75, 0, 130);
    Color whiteColor = Color.WHITE;

    TitleBar() {
        this.setPreferredSize(new Dimension(400, 80));
        this.setBackground(darkPurple);
        JLabel titleText = new JLabel("To Do List");
        titleText.setPreferredSize(new Dimension(200, 60));
        titleText.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setForeground(whiteColor); // Set text color to white
        this.add(titleText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 255, 255, 120), 0, getHeight(), new Color(255, 255, 255, 0));
        g2d.setPaint(gradient);

        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.dispose();
    }
}

class AppFrame extends JFrame {
    private TitleBar title;
    private Footer footer;
    private List list;
    private JButton addTask;
    private JButton clear;

    AppFrame() {
        this.setSize(400, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        title = new TitleBar();
        footer = new Footer();
        list = new List();

        this.add(title, BorderLayout.NORTH);
        this.add(footer, BorderLayout.SOUTH);
        this.add(list, BorderLayout.CENTER);

        addTask = footer.getAddTask();
        clear = footer.getClear();

        addListeners();
    }

    public void addListeners() {
        addTask.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Task task = new Task();
                list.addTask(task);

                task.getDone().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        task.changeState();
                        list.updateNumbers();
                        revalidate();
                    }
                });
            }
        });

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                list.removeCompletedTasks();
                repaint();
            }
        });
    }
}

public class ToDoList {
    public static void main(String args[]) {
        new AppFrame();
    }
}
