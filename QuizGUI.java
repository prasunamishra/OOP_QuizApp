package Coursework1;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


/**
 * QuizGUI is the main graphical user interface for the Quiz Competition System.
 * It contains panels for login, main menu, quiz questions, and the leaderboard.
 */
public class QuizGUI extends JFrame {

    private final Color OLIVE_DARK = new Color(85, 107, 47);
    private final Color OLIVE_LIGHT = new Color(196, 215, 155);
    private final Color OLIVE_BG = new Color(236, 240, 225);
    private final Color TEXT_DARK = new Color(45, 55, 30);
    private static final long serialVersionUID = 1L;

    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FIELD_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private CardLayout card = new CardLayout();
    private JPanel container = new JPanel(card);

    private QuizApp quizApp = new QuizApp();
    private QuizCompetitor currentCompetitor;

    private QuizPanel quizPanel = new QuizPanel();
    private LeaderboardPanel leaderboardPanel = new LeaderboardPanel();

    /**
     * Constructs the main Quiz GUI and initializes all panels.
     */
    public QuizGUI() {
        setTitle("Quiz Competition System");
        setSize(900,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Add all panels to the container
        container.add(new LoginPanel(), "login");
        container.add(new MenuPanel(), "menu");
        container.add(quizPanel, "quiz");
        container.add(leaderboardPanel, "leaderboard");

        add(container);
        card.show(container, "login"); // Start with login panel
        setVisible(true);
    }

    // LOGIN PANEL

    /**
     * LoginPanel allows users to enter their first name, last name,
     * country, and select a quiz level before starting the quiz.
     */
    class LoginPanel extends JPanel {

        JTextField first = new JTextField(15);
        JTextField last = new JTextField(15);
        JTextField country = new JTextField(15);
        private static final long serialVersionUID = 1L;

        JComboBox<String> level =
                new JComboBox<>(new String[]{"Beginner","Intermediate","Advanced"});

        /**
         * Constructs the login panel with input fields and login button.
         */
        LoginPanel(){
            setLayout(new BorderLayout());
            setBackground(OLIVE_BG);

            // Title
            JLabel title = new JLabel("Quiz Competition System", JLabel.CENTER);
            title.setFont(TITLE_FONT);
            title.setForeground(OLIVE_DARK);
            title.setBorder(BorderFactory.createEmptyBorder(40,0,20,0));
            add(title, BorderLayout.NORTH);

            // Form layout
            JPanel form = new JPanel(new GridLayout(5,2,15,15));
            form.setBackground(Color.WHITE);
            form.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(OLIVE_DARK,2),
                    BorderFactory.createEmptyBorder(30,40,30,40)
            ));

            JLabel l1=new JLabel("First Name");
            JLabel l2=new JLabel("Last Name");
            JLabel l3=new JLabel("Country");
            JLabel l4=new JLabel("Level");

            for(JLabel l:new JLabel[]{l1,l2,l3,l4}){
                l.setFont(LABEL_FONT);
                l.setForeground(TEXT_DARK);
            }

            first.setFont(FIELD_FONT);
            last.setFont(FIELD_FONT);
            country.setFont(FIELD_FONT);
            level.setFont(FIELD_FONT);

            JButton login = new JButton("Enter Quiz");
            login.setBackground(OLIVE_DARK);
            login.setForeground(Color.WHITE);
            login.setFont(new Font("Segoe UI", Font.BOLD, 15));
            login.setFocusPainted(false);

            form.add(l1); form.add(first);
            form.add(l2); form.add(last);
            form.add(l3); form.add(country);
            form.add(l4); form.add(level);
            form.add(new JLabel()); form.add(login);

            JPanel centerWrap = new JPanel(new GridBagLayout());
            centerWrap.setBackground(OLIVE_BG);
            centerWrap.add(form);

            add(centerWrap, BorderLayout.CENTER);

            // Login action
            login.addActionListener(e->{
                if(first.getText().isEmpty()||last.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this,"Enter name!");
                    return;
                }

                quizApp.login(
                        new Name(first.getText(),last.getText()),
                        level.getSelectedItem().toString(),
                        country.getText()
                );
                card.show(container,"menu");
            });
        }
    }

    // MENU PANEL 

    /**
     * MenuPanel displays the main menu options: start quiz,
     * view leaderboard, view user details, or quit the application.
     */
    class MenuPanel extends JPanel{
    	private static final long serialVersionUID = 1L;


        /**
         * Constructs the menu panel with buttons and welcome label.
         */
        MenuPanel(){
            setLayout(new BorderLayout());
            setBackground(OLIVE_BG);

            JLabel welcome = new JLabel("", JLabel.CENTER);
            welcome.setFont(new Font("Segoe UI", Font.BOLD, 25));
            welcome.setForeground(OLIVE_DARK);
            welcome.setBorder(BorderFactory.createEmptyBorder(40, 0, 10, 0));
            add(welcome, BorderLayout.NORTH);

            JPanel box = new JPanel(new GridLayout(4,1,15,15));
            box.setBorder(BorderFactory.createEmptyBorder(40,320,150,320));
            box.setBackground(OLIVE_BG);

            JButton start=new JButton("Start Quiz");
            JButton lead=new JButton("Leaderboard");
            JButton det=new JButton("My Details");
            JButton quit=new JButton("Quit");

            for(JButton b:new JButton[]{start,lead,det,quit}){
                b.setBackground(OLIVE_DARK);
                b.setForeground(Color.WHITE);
                b.setFont(new Font("Segoe UI", Font.BOLD, 15));
                b.setFocusPainted(false);
            }

            box.add(start); box.add(lead); box.add(det); box.add(quit);
            add(box,BorderLayout.CENTER);

            // Update welcome text when menu is shown
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentShown(ComponentEvent e) {
                    QuizCompetitor c = quizApp.getCurrentUser();
                    if (c != null) {
                        welcome.setText("Welcome " + c.getName().getFirstName());
                    }
                }
            });

            // Button actions
            start.addActionListener(e->{
                quizPanel.startQuiz();
                card.show(container,"quiz");
            });

            lead.addActionListener(e->{
                leaderboardPanel.refresh();
                card.show(container,"leaderboard");
            });

            det.addActionListener(e->{
                if(currentCompetitor!=null)
                    JOptionPane.showMessageDialog(null,
                            currentCompetitor.getFullDetails());
                else
                    JOptionPane.showMessageDialog(null,
                            "No quiz completed yet.");
            });

            quit.addActionListener(e->System.exit(0));
        }
    }

    //  QUIZ PANEL 

    /**
     * QuizPanel displays quiz questions, multiple-choice options,
     * and navigation buttons for answering and finishing the quiz.
     */
    class QuizPanel extends JPanel {
    	private static final long serialVersionUID = 1L;


        JLabel qNo = new JLabel();
        JTextArea qText = new JTextArea();
        JRadioButton[] opts = new JRadioButton[4];
        ButtonGroup group = new ButtonGroup();
        JButton next = new JButton("Next");
        JButton back = new JButton("Back to Menu");

        /**
         * Constructs the quiz panel with question text, options, and buttons.
         */
        QuizPanel() {
            setLayout(new BorderLayout(15, 15));
            setBorder(BorderFactory.createEmptyBorder(40, 120, 40, 120));
            setBackground(OLIVE_BG);

            qNo.setFont(new Font("Segoe UI", Font.BOLD, 16));

            qText.setLineWrap(true);
            qText.setWrapStyleWord(true);
            qText.setEditable(false);
            qText.setFont(new Font("Segoe UI", Font.BOLD, 20));
            qText.setMargin(new Insets(15, 15, 15, 15));

            JScrollPane qScroll = new JScrollPane(qText);
            qScroll.setPreferredSize(new Dimension(500, 120));
            qScroll.setBorder(BorderFactory.createEmptyBorder());
            qScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            JPanel top = new JPanel(new BorderLayout());
            top.setBackground(OLIVE_BG);
            top.add(qNo, BorderLayout.NORTH);
            top.add(qScroll, BorderLayout.CENTER);

            add(top, BorderLayout.NORTH);

            // Options and buttons
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
            centerPanel.setBackground(OLIVE_BG);

            JPanel optP = new JPanel(new GridLayout(4, 1, 10, 10));
            optP.setBackground(OLIVE_BG);
            for (int i = 0; i < 4; i++) {
                opts[i] = new JRadioButton();
                opts[i].setFont(new Font("Segoe UI", Font.PLAIN, 18));
                opts[i].setBackground(OLIVE_BG);
                group.add(opts[i]);
                optP.add(opts[i]);
            }
            centerPanel.add(optP);

            centerPanel.add(Box.createVerticalStrut(15));

            JPanel btn = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            btn.setBackground(OLIVE_BG);
            btn.add(back);
            btn.add(next);
            centerPanel.add(btn);

            add(centerPanel, BorderLayout.CENTER);

            next.setBackground(OLIVE_DARK);
            next.setForeground(Color.WHITE);
            back.setBackground(Color.WHITE);
            back.setForeground(OLIVE_DARK);
            next.setPreferredSize(new Dimension(160, 38));
            back.setPreferredSize(new Dimension(160, 38));

            next.addActionListener(e -> nextQ());
            back.addActionListener(e -> card.show(container, "menu"));
        }

        /**
         * Starts the quiz by loading the first question.
         */
        void startQuiz() {
            loadQ();
        }

        /**
         * Loads the current question and updates the UI.
         */
        void loadQ() {
            Question q = quizApp.getCurrentQuestion();
            qNo.setText("Question " + quizApp.getQuestionNumber() + " of " + quizApp.getTotalQuestions());
            qText.setText(q.getQuestionText());
            for (int i = 0; i < 4; i++)
                opts[i].setText(q.getOptions()[i]);
            group.clearSelection();
        }

        /**
         * Submits the selected answer and moves to the next question.
         * If it was the last question, finishes the quiz.
         */
        void nextQ() {
            int sel = -1;
            for (int i = 0; i < 4; i++)
                if (opts[i].isSelected()) sel = i;

            if (sel == -1) {
                JOptionPane.showMessageDialog(this, "Select an option!");
                return;
            }

            if (quizApp.submitAnswer(sel)) {
                loadQ();
            } else {
                quizApp.finishQuiz();
                currentCompetitor = quizApp.getCurrentUser();
                JOptionPane.showMessageDialog(this, "Quiz Finished!");
                card.show(container, "menu");
            }
        }
    }

    //  LEADERBOARD PANEL 

    /**
     * LeaderboardPanel displays all competitors' scores and
     * a frequency table of scores.
     */
    class LeaderboardPanel extends JPanel {
    	private static final long serialVersionUID = 1L;


        JTable table = new JTable();
        JTable freq = new JTable();
        CompetitorList listRef;

        /**
         * Constructs the leaderboard panel with tables and back button.
         */
        LeaderboardPanel() {

            setLayout(new BorderLayout(10, 10));
            setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
            setBackground(OLIVE_BG);

            table.setRowHeight(24);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            table.getTableHeader().setBackground(OLIVE_LIGHT);

            add(new JScrollPane(table), BorderLayout.CENTER);

            freq.setRowHeight(18);
            freq.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            freq.setEnabled(false);
            freq.setTableHeader(null);

            DefaultTableCellRenderer center = new DefaultTableCellRenderer();
            center.setHorizontalAlignment(JLabel.CENTER);
            freq.setDefaultRenderer(Object.class, center);

            JScrollPane freqScroll = new JScrollPane(freq);
            int freqHeight = freq.getRowHeight() * 2 + 8;
            freqScroll.setPreferredSize(new Dimension(300, freqHeight));
            freqScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            JLabel freqTitle = new JLabel("Frequency Table");
            freqTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));

            JPanel freqPanel = new JPanel();
            freqPanel.setLayout(new BoxLayout(freqPanel, BoxLayout.Y_AXIS));
            freqPanel.setBackground(OLIVE_BG);
            freqPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

            freqPanel.add(freqTitle);
            freqPanel.add(Box.createVerticalStrut(5));
            freqPanel.add(freqScroll);

            add(freqPanel, BorderLayout.SOUTH);

            JButton back = new JButton("Back");
            back.setBackground(OLIVE_DARK);
            back.setForeground(Color.WHITE);

            JPanel top = new JPanel();
            top.setBackground(OLIVE_BG);
            top.add(back);
            add(top, BorderLayout.NORTH);

            back.addActionListener(e -> card.show(container, "menu"));

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row != -1 && listRef != null) {
                        QuizCompetitor c = listRef.getCompetitors().get(row);
                        JOptionPane.showMessageDialog(null, c.getFullDetails());
                    }
                }
            });
        }

        /**
         * Refreshes the leaderboard and frequency table with latest data.
         */
        void refresh() {
            Manager m = new Manager();
            listRef = m.getCompetitorList();

            String[] cols = {
                    "ID", "Name", "Level", "Country",
                    "S1", "S2", "S3", "S4", "S5", "Overall"
            };

            Object[][] data = new Object[listRef.getCompetitors().size()][10];

            int i = 0;
            for (QuizCompetitor c : listRef.getCompetitors()) {
                int[] s = c.getScoreArray();
                data[i++] = new Object[]{
                        c.getCompetitorID(),
                        c.getName().getFullName(),
                        c.getLevel(),
                        c.getCountry(),
                        s[0], s[1], s[2], s[3], s[4],
                        c.getOverallScore()
                };
            }

            table.setModel(new DefaultTableModel(data, cols));

            int[] f = listRef.getScoreFrequencies();

            Object[][] fd = {
                    {"Score", "0", "1", "2", "3", "4", "5"},
                    {"Count", f[0], f[1], f[2], f[3], f[4], f[5]}
            };

            freq.setModel(new DefaultTableModel(
                    fd,
                    new String[]{"", "", "", "", "", "", ""}
            ));
        }
    }

    /**
     * Main method to launch the GUI.
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(QuizGUI::new);
    }
}
