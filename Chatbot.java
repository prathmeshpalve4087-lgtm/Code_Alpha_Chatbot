
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Chatbot extends JFrame {

    // ============== COLOR THEME ==============

    private final Color BG = new Color(8, 13, 27);
    private final Color PANEL = new Color(15, 23, 42);
    private final Color PANEL2 = new Color(25, 38, 62);

    private final Color CYAN = new Color(0, 220, 230);

    private final Color WHITE = new Color(240, 247, 255);
    private final Color MUTED = new Color(155, 175, 205);

    private final Color USER = new Color(28, 76, 135);
    private final Color BOT = new Color(25, 39, 62);

    private JPanel chatPanel;
    private JTextField inputField;
    private JScrollPane scrollPane;

    private JLabel messageCountLabel;
    private JLabel statusLabel;

    private int messageCount = 0;

    private final DateTimeFormatter timeFormat =
            DateTimeFormatter.ofPattern("hh:mm a");

    // ============== CONSTRUCTOR ==============

    public Chatbot() {

        setTitle("NEXUS AI | Intelligent Assistant");
        setSize(1150, 780);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);
        add(createMainPanel(), BorderLayout.CENTER);

        addBotMessage(
                "Hello! I am NEXUS, your intelligent virtual assistant.\n\n" +
                "I can help you with Java, programming, AI, machine learning, " +
                "college, career guidance, internships and technology.\n\n" +
                "What would you like to explore today?"
        );

        setVisible(true);
    }

    // ============== SIDEBAR ==============

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(PANEL);

        sidebar.setBorder(new MatteBorder(
                0, 0, 0, 1, new Color(40, 65, 100)));

        sidebar.setLayout(new BoxLayout(
                sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("  NEXUS AI");

        logo.setFont(new Font("Arial", Font.BOLD, 25));
        logo.setForeground(CYAN);
        logo.setBorder(new EmptyBorder(30, 10, 25, 5));

        sidebar.add(logo);

        JLabel menuTitle = new JLabel("  WORKSPACE");

        menuTitle.setForeground(MUTED);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 11));
        menuTitle.setBorder(new EmptyBorder(15, 10, 10, 5));

        sidebar.add(menuTitle);

        sidebar.add(sidebarButton(
                "  ✦  New Conversation", this::clearChat));

        sidebar.add(sidebarButton(
                "  ◈  Java Assistant",
                () -> sendQuickMessage("Tell me about Java")));

        sidebar.add(sidebarButton(
                "  ◈  AI & Technology",
                () -> sendQuickMessage(
                        "What is Artificial Intelligence?")));

        sidebar.add(sidebarButton(
                "  ◈  Career Guidance",
                () -> sendQuickMessage(
                        "How can I become a software developer?")));

        JLabel toolsTitle = new JLabel("  TOOLS");

        toolsTitle.setForeground(MUTED);
        toolsTitle.setFont(new Font("Arial", Font.BOLD, 11));
        toolsTitle.setBorder(new EmptyBorder(25, 10, 10, 5));

        sidebar.add(toolsTitle);

        sidebar.add(sidebarButton(
                "  ◉  Help Center",
                () -> sendQuickMessage("What can you do?")));

        sidebar.add(sidebarButton(
                "  ◉  About Assistant",
                () -> sendQuickMessage("Who are you?")));

        sidebar.add(Box.createVerticalGlue());

        JPanel profile = new JPanel();

        profile.setBackground(PANEL2);
        profile.setLayout(new BoxLayout(
                profile, BoxLayout.Y_AXIS));

        profile.setBorder(new EmptyBorder(15, 15, 15, 15));
        profile.setMaximumSize(new Dimension(200, 105));

        JLabel profileName = new JLabel("NEXUS ASSISTANT");

        profileName.setForeground(WHITE);
        profileName.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel profileStatus = new JLabel("●  System Active");

        profileStatus.setForeground(CYAN);
        profileStatus.setFont(new Font("Arial", Font.PLAIN, 11));

        profile.add(profileName);
        profile.add(Box.createVerticalStrut(8));
        profile.add(profileStatus);

        JPanel profileWrapper = new JPanel(
                new FlowLayout(FlowLayout.CENTER));

        profileWrapper.setBackground(PANEL);
        profileWrapper.add(profile);

        sidebar.add(profileWrapper);
        sidebar.add(Box.createVerticalStrut(15));

        return sidebar;
    }

    private JButton sidebarButton(String text, Runnable action) {

        JButton button = new JButton(text);

        button.setMaximumSize(new Dimension(220, 48));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setForeground(MUTED);
        button.setBackground(PANEL);

        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFont(new Font("Arial", Font.PLAIN, 13));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 12, 12, 5));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> action.run());

        button.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                button.setBackground(PANEL2);
                button.setForeground(CYAN);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(PANEL);
                button.setForeground(MUTED);
            }
        });

        return button;
    }

    // ============== MAIN PANEL ==============

    private JPanel createMainPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        // Header

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(PANEL);
        header.setBorder(new MatteBorder(
                0, 0, 1, 0, new Color(40, 65, 100)));

        JPanel heading = new JPanel();

        heading.setLayout(new BoxLayout(
                heading, BoxLayout.Y_AXIS));

        heading.setBackground(PANEL);
        heading.setBorder(new EmptyBorder(18, 25, 18, 10));

        JLabel title = new JLabel("AI CONVERSATION");

        title.setForeground(WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 19));

        statusLabel = new JLabel(
                "●  ONLINE  |  RULE-BASED INTELLIGENCE");

        statusLabel.setForeground(CYAN);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 10));

        heading.add(title);
        heading.add(Box.createVerticalStrut(6));
        heading.add(statusLabel);

        messageCountLabel = new JLabel("Messages: 0");

        messageCountLabel.setForeground(MUTED);
        messageCountLabel.setFont(
                new Font("Arial", Font.PLAIN, 12));

        messageCountLabel.setBorder(
                new EmptyBorder(15, 10, 15, 25));

        header.add(heading, BorderLayout.WEST);
        header.add(messageCountLabel, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        // Chat area

        chatPanel = new JPanel();

        chatPanel.setLayout(new BoxLayout(
                chatPanel, BoxLayout.Y_AXIS));

        chatPanel.setBackground(BG);
        chatPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        scrollPane = new JScrollPane(chatPanel);

        scrollPane.setBorder(null);
        scrollPane.setBackground(BG);
        scrollPane.getViewport().setBackground(BG);

        scrollPane.getVerticalScrollBar().setUnitIncrement(18);

        main.add(scrollPane, BorderLayout.CENTER);

        // Bottom area

        JPanel bottom = new JPanel(new BorderLayout());

        bottom.setBackground(PANEL);
        bottom.setBorder(new MatteBorder(
                1, 0, 0, 0, new Color(40, 65, 100)));

        // Quick prompts

        JPanel suggestions = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 10));

        suggestions.setBackground(PANEL);
        suggestions.setBorder(new EmptyBorder(3, 15, 0, 10));

        suggestions.add(createQuickButton(
                "Java Basics", "What is Java?"));

        suggestions.add(createQuickButton(
                "Explain OOP", "Explain OOP concepts"));

        suggestions.add(createQuickButton(
                "Artificial Intelligence",
                "What is Artificial Intelligence?"));

        suggestions.add(createQuickButton(
                "Career", "How can I become a software developer?"));

        // Input area

        JPanel inputArea = new JPanel(
                new BorderLayout(10, 0));

        inputArea.setBackground(PANEL);
        inputArea.setBorder(
                new EmptyBorder(10, 15, 15, 15));

        inputField = new JTextField();

        inputField.setBackground(PANEL2);
        inputField.setForeground(WHITE);
        inputField.setCaretColor(CYAN);

        inputField.setSelectionColor(
                new Color(45, 110, 185));

        inputField.setSelectedTextColor(Color.WHITE);

        inputField.setFont(
                new Font("Arial", Font.PLAIN, 15));

        inputField.setBorder(new CompoundBorder(
                new LineBorder(
                        new Color(55, 85, 125), 1, true),
                new EmptyBorder(13, 15, 13, 15)));

        inputField.setToolTipText(
                "Type your message here...");

        // Send button

        JButton sendButton = new JButton("SEND  ➤");

        sendButton.setBackground(CYAN);
        sendButton.setForeground(new Color(8, 20, 35));

        sendButton.setOpaque(true);
        sendButton.setContentAreaFilled(true);

        sendButton.setFont(
                new Font("Arial", Font.BOLD, 13));

        sendButton.setFocusPainted(false);

        sendButton.setBorder(
                new EmptyBorder(12, 22, 12, 22));

        sendButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        sendButton.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent e) {
                sendButton.setBackground(
                        new Color(80, 245, 245));
            }

            public void mouseExited(MouseEvent e) {
                sendButton.setBackground(CYAN);
            }
        });

        sendButton.addActionListener(
                e -> sendMessage());

        inputField.addActionListener(
                e -> sendMessage());

        inputArea.add(inputField, BorderLayout.CENTER);
        inputArea.add(sendButton, BorderLayout.EAST);

        JLabel footer = new JLabel(
                "NEXUS AI  •  Powered by Java Swing & Rule-Based NLP",
                SwingConstants.CENTER);

        footer.setForeground(MUTED);
        footer.setFont(
                new Font("Arial", Font.PLAIN, 10));

        footer.setBorder(
                new EmptyBorder(0, 0, 10, 0));

        bottom.add(suggestions, BorderLayout.NORTH);
        bottom.add(inputArea, BorderLayout.CENTER);
        bottom.add(footer, BorderLayout.SOUTH);

        main.add(bottom, BorderLayout.SOUTH);

        return main;
    }

    // ============== QUICK BUTTONS ==============

    private JButton createQuickButton(
            String label, String prompt) {

        JButton button = new JButton(label);

        button.setBackground(PANEL2);
        button.setForeground(CYAN);

        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setFont(
                new Font("Arial", Font.PLAIN, 11));

        button.setFocusPainted(false);

        button.setBorder(new CompoundBorder(
                new LineBorder(
                        new Color(50, 85, 130), 1, true),
                new EmptyBorder(8, 10, 8, 10)));

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(
                e -> sendQuickMessage(prompt));

        return button;
    }

    // ============== CHAT MESSAGE DISPLAY ==============

    private void addUserMessage(String message) {
        addMessage(message, true);
    }

    private void addBotMessage(String message) {
        addMessage(message, false);
    }

    private void addMessage(
            String message, boolean isUser) {

        JPanel row = new JPanel(new BorderLayout());

        row.setOpaque(false);
        row.setBorder(new EmptyBorder(8, 0, 8, 0));

        JPanel bubble = new JPanel(new BorderLayout(0, 8));

        bubble.setBackground(isUser ? USER : BOT);

        bubble.setOpaque(true);

        bubble.setBorder(new CompoundBorder(
                new LineBorder(
                        isUser
                                ? new Color(55, 125, 205)
                                : new Color(50, 75, 110),
                        1, true),
                new EmptyBorder(13, 16, 13, 16)));

        bubble.setMaximumSize(
                new Dimension(650, Integer.MAX_VALUE));

        JLabel sender = new JLabel(
                (isUser ? "YOU" : "✦ NEXUS AI") +
                "     " +
                LocalTime.now().format(timeFormat));

        sender.setForeground(
                isUser ? CYAN : new Color(90, 195, 255));

        sender.setFont(
                new Font("Arial", Font.BOLD, 10));

        // Dark transparent message area

        JTextArea text = new JTextArea(message);

        text.setFont(
                new Font("Arial", Font.PLAIN, 14));

        text.setForeground(WHITE);

        text.setBackground(isUser ? USER : BOT);

        text.setCaretColor(CYAN);
        text.setSelectionColor(new Color(45, 110, 185));
        text.setSelectedTextColor(Color.WHITE);

        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        text.setEditable(false);
        text.setFocusable(false);

        text.setOpaque(false);
        text.setColumns(38);
        text.setBorder(null);

        JPanel content = new JPanel(
                new BorderLayout(0, 8));

        content.setOpaque(false);

        content.add(sender, BorderLayout.NORTH);
        content.add(text, BorderLayout.CENTER);

        bubble.add(content, BorderLayout.CENTER);

        row.add(bubble, isUser
                ? BorderLayout.EAST
                : BorderLayout.WEST);

        chatPanel.add(row);
        chatPanel.add(Box.createVerticalStrut(5));

        messageCount++;

        messageCountLabel.setText(
                "Messages: " + messageCount);

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(() -> {

            JScrollBar bar =
                    scrollPane.getVerticalScrollBar();

            bar.setValue(bar.getMaximum());
        });
    }

    // ============== SEND MESSAGE ==============

    private void sendMessage() {

        String message = inputField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        addUserMessage(message);

        inputField.setText("");

        String response = getBotResponse(message);

        statusLabel.setText(
                "●  PROCESSING YOUR MESSAGE...");

        Timer timer = new Timer(450, e -> {

            addBotMessage(response);

            statusLabel.setText(
                    "●  ONLINE  |  RULE-BASED INTELLIGENCE");
        });

        timer.setRepeats(false);
        timer.start();
    }

    private void sendQuickMessage(String message) {

        inputField.setText(message);
        sendMessage();
    }

    // ============== NLP PROCESSING ==============

    private String normalize(String message) {

        return message.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private boolean has(
            String text, String... keywords) {

        for (String keyword : keywords) {

            String key = normalize(keyword);

            if ((" " + text + " ").contains(
                    " " + key + " ")) {

                return true;
            }
        }

        return false;
    }

    // ============== KNOWLEDGE BASE ==============

    private String getBotResponse(String message) {

        String text = normalize(message);

        if (text.isEmpty()) {
            return "Please enter a valid question.";
        }

        // Greetings

        if (has(text, "hello", "hi", "hey",
                "good morning", "good evening",
                "good afternoon", "greetings")) {

            return "Hello! Welcome to NEXUS AI. " +
                    "How can I help you today?";
        }

        // Identity

        if (has(text, "who are you", "your name",
                "what are you", "introduce yourself")) {

            return "I am NEXUS AI, a Java-based virtual assistant. " +
                    "I use natural language text processing and " +
                    "a rule-based knowledge system to answer questions.";
        }

        // Capabilities

        if (has(text, "what can you do", "your features",
                "your capabilities", "help me", "help")) {

            return "Here is what I can help you with:\n\n" +
                    "1. Java programming and OOP\n" +
                    "2. Data structures and algorithms\n" +
                    "3. Artificial Intelligence and ML\n" +
                    "4. Computer science concepts\n" +
                    "5. College and examination guidance\n" +
                    "6. Internship and career guidance\n" +
                    "7. Technology and computers\n\n" +
                    "Choose a quick prompt or ask me a question!";
        }

        // Thanks

        if (has(text, "thank you", "thanks", "thank")) {

            return "You're very welcome! I am always happy to help.";
        }

        // Feelings

        if (has(text, "how are you", "how are u")) {

            return "My systems are running perfectly! " +
                    "Thanks for asking. What would you like to learn?";
        }

        // Java

        if (has(text, "what is java", "java language",
                "java programming", "java")) {

            return "Java is a high-level, class-based, " +
                    "object-oriented programming language.\n\n" +
                    "Key features:\n" +
                    "• Platform independent\n" +
                    "• Object-oriented\n" +
                    "• Secure and robust\n" +
                    "• Automatic memory management\n" +
                    "• Multithreading support";
        }

        // OOP

        if (has(text, "oops", "oop",
                "object oriented programming", "oop concepts")) {

            return "OOP stands for Object-Oriented Programming.\n\n" +
                    "Its four main principles are:\n\n" +
                    "1. Encapsulation – Binding data and methods together.\n" +
                    "2. Inheritance – Reusing properties of another class.\n" +
                    "3. Polymorphism – One interface with multiple forms.\n" +
                    "4. Abstraction – Hiding implementation details.";
        }

        // Class and object

        if (has(text, "class", "object")) {

            return "A class is a blueprint used to create objects.\n\n" +
                    "An object is an instance of a class that has " +
                    "state and behavior.\n\n" +
                    "Example:\nStudent s1 = new Student();";
        }

        // Constructor

        if (has(text, "constructor", "constructors")) {

            return "A constructor is a special member of a Java " +
                    "class used to initialize objects. It has the " +
                    "same name as the class and no return type.";
        }

        // Inheritance

        if (has(text, "inheritance", "extends keyword")) {

            return "Inheritance allows one Java class to acquire " +
                    "the properties and methods of another class. " +
                    "It is implemented using the extends keyword.";
        }

        // Polymorphism

        if (has(text, "polymorphism", "method overloading",
                "method overriding")) {

            return "Polymorphism means many forms.\n\n" +
                    "1. Compile-time polymorphism – Method overloading.\n" +
                    "2. Runtime polymorphism – Method overriding.";
        }

        // Encapsulation

        if (has(text, "encapsulation", "getter", "setter")) {

            return "Encapsulation wraps data and methods into " +
                    "a single unit. It is commonly achieved using " +
                    "private variables and public getter and setter methods.";
        }

        // Abstraction

        if (has(text, "abstraction", "abstract class", "interface")) {

            return "Abstraction hides internal implementation " +
                    "details and exposes only essential features. " +
                    "Java supports abstraction using abstract classes " +
                    "and interfaces.";
        }

        // Loops

        if (has(text, "loop", "loops", "for loop",
                "while loop", "do while")) {

            return "Java provides three main types of loops:\n\n" +
                    "1. For loop – Used when iterations are known.\n" +
                    "2. While loop – Repeats while a condition is true.\n" +
                    "3. Do-while loop – Executes at least once.";
        }

        // Arrays

        if (has(text, "array", "arrays", "arraylist")) {

            return "An array stores multiple values of the same " +
                    "data type in a single variable. Java also " +
                    "provides ArrayList for dynamically sized collections.";
        }

        // Methods

        if (has(text, "method", "methods",
                "function", "functions")) {

            return "A method is a reusable block of code that " +
                    "performs a specific task. Methods improve " +
                    "code reusability and organization.";
        }

        // Exception handling

        if (has(text, "exception", "try catch",
                "error handling", "finally", "throw keyword")) {

            return "Exception handling manages runtime errors.\n\n" +
                    "• try – Contains risky code.\n" +
                    "• catch – Handles exceptions.\n" +
                    "• finally – Executes cleanup code.\n" +
                    "• throw – Explicitly throws an exception.\n" +
                    "• throws – Declares possible exceptions.";
        }

        // Collections

        if (has(text, "collection", "collections",
                "hashmap", "hashset", "linkedlist")) {

            return "Java Collections Framework provides data structures.\n\n" +
                    "• ArrayList – Dynamic list.\n" +
                    "• LinkedList – Linked data structure.\n" +
                    "• HashSet – Stores unique elements.\n" +
                    "• HashMap – Stores key-value pairs.";
        }

        // DSA

        if (has(text, "data structure",
                "algorithm", "dsa")) {

            return "Data Structures and Algorithms help organize " +
                    "and process data efficiently.\n\n" +
                    "Important topics:\n" +
                    "Arrays, Linked Lists, Stacks, Queues, Trees, " +
                    "Graphs, Searching and Sorting.";
        }

        // Database

        if (has(text, "database", "sql", "mysql", "dbms")) {

            return "A database stores and organizes information.\n\n" +
                    "DBMS manages databases. SQL is used to create, " +
                    "read, update and delete data.\n\n" +
                    "Common operations: SELECT, INSERT, UPDATE and DELETE.";
        }

        // Operating system

        if (has(text, "operating system", "process",
                "deadlock", "multitasking")) {

            return "An Operating System manages computer hardware " +
                    "and software resources.\n\n" +
                    "Examples: Windows, Linux, macOS and Android.\n\n" +
                    "Functions include process management, memory " +
                    "management and file management.";
        }

        // Computer networks

        if (has(text, "computer network", "networking",
                "tcp", "udp", "http", "ip address")) {

            return "Computer networking connects devices to exchange data.\n\n" +
                    "• TCP – Reliable transport protocol.\n" +
                    "• UDP – Connectionless transport protocol.\n" +
                    "• HTTP – Web communication protocol.\n" +
                    "• IP – Logical addressing.";
        }

        // Artificial Intelligence

        if (has(text, "artificial intelligence",
                "what is ai", "ai")) {

            return "Artificial Intelligence (AI) is a branch of " +
                    "computer science that enables machines to perform " +
                    "tasks that normally require human intelligence.\n\n" +
                    "Applications include virtual assistants, robotics, " +
                    "recommendation systems and image recognition.";
        }

        // Machine learning

        if (has(text, "machine learning", "what is ml")) {

            return "Machine Learning is a branch of AI that enables " +
                    "computers to learn patterns from data.\n\n" +
                    "Main types:\n" +
                    "1. Supervised Learning\n" +
                    "2. Unsupervised Learning\n" +
                    "3. Reinforcement Learning";
        }

        // Deep learning

        if (has(text, "deep learning",
                "neural network", "neural networks")) {

            return "Deep Learning is a subset of Machine Learning " +
                    "that uses multi-layered artificial neural networks " +
                    "to learn complex patterns from data.";
        }

        // NLP

        if (has(text, "natural language processing", "nlp")) {

            return "Natural Language Processing (NLP) is a field " +
                    "of AI that helps computers understand and " +
                    "process human language.\n\n" +
                    "Applications include chatbots, translation, " +
                    "sentiment analysis and voice assistants.";
        }

        // Chatbot

        if (has(text, "chatbot", "how do you work",
                "how chatbot works")) {

            return "I work using a rule-based chatbot architecture.\n\n" +
                    "1. Receive your message.\n" +
                    "2. Normalize the text.\n" +
                    "3. Identify matching keywords and phrases.\n" +
                    "4. Select a predefined response.\n" +
                    "5. Display the response in the chat interface.";
        }

        // College

        if (has(text, "college", "university",
                "student", "semester", "exam", "examination")) {

            return "College is a great opportunity to build " +
                    "technical knowledge and practical skills.\n\n" +
                    "My suggestion: focus on programming, DSA, projects, " +
                    "communication and internship experience.";
        }

        // Internship

        if (has(text, "internship", "intern", "training")) {

            return "An internship provides practical industry experience.\n\n" +
                    "1. Understand every assigned task.\n" +
                    "2. Write clean and readable code.\n" +
                    "3. Test your application.\n" +
                    "4. Maintain project documentation.\n" +
                    "5. Submit your work before the deadline.";
        }

        // Career

        if (has(text, "career", "software developer",
                "job", "placement")) {

            return "To build a software development career:\n\n" +
                    "1. Learn Java fundamentals.\n" +
                    "2. Practice Data Structures and Algorithms.\n" +
                    "3. Learn SQL and Git.\n" +
                    "4. Build practical projects.\n" +
                    "5. Prepare your resume.\n" +
                    "6. Apply for internships and placements.";
        }

        // Git and GitHub

        if (has(text, "git", "github", "version control")) {

            return "Git is a version control system used to track " +
                    "code changes. GitHub is a platform for hosting " +
                    "and collaborating on Git repositories.";
        }

        // HTML and CSS

        if (has(text, "html", "css", "web development")) {

            return "HTML structures web pages, while CSS controls " +
                    "their visual appearance. JavaScript adds " +
                    "interactivity to web applications.";
        }

        // Python

        if (has(text, "python", "python programming")) {

            return "Python is a high-level programming language " +
                    "known for its simple syntax. It is widely used " +
                    "in AI, machine learning, automation and web development.";
        }

        // Motivation

        if (has(text, "motivate me", "motivation",
                "feeling lazy", "give me advice")) {

            return "Every expert was once a beginner. Focus on " +
                    "small improvements every day. Consistency and " +
                    "practice are more important than perfection!";
        }

        // Goodbye

        if (has(text, "bye", "goodbye", "see you")) {

            return "Goodbye! Thank you for using NEXUS AI. " +
                    "Have a productive day!";
        }

        // Default response

        return "I understand that you are asking: \"" +
                message + "\"\n\n" +
                "However, I don't have a predefined answer " +
                "for this question yet.\n\n" +
                "Try asking about:\n" +
                "• Java and OOP\n" +
                "• Data Structures and Algorithms\n" +
                "• Artificial Intelligence and NLP\n" +
                "• Database and Networking\n" +
                "• College, internships and careers";
    }

    // ============== CLEAR CHAT ==============

    private void clearChat() {

        chatPanel.removeAll();

        messageCount = 0;

        messageCountLabel.setText("Messages: 0");

        chatPanel.revalidate();
        chatPanel.repaint();

        addBotMessage(
                "New conversation started!\n\n" +
                "Hello! I am NEXUS AI. " +
                "What can I help you with?"
        );
    }

    // ============== MAIN METHOD ==============

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());

            } catch (Exception e) {

                System.out.println(
                        "Using default interface.");
            }

            new Chatbot();
        });
    }
}
