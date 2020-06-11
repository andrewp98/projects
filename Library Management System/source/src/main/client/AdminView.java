package ro.utcluj.client;
import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


class AdminView extends JFrame implements IAdminView {

    private JTextField textUsername;
    private JTextField textPass;
    private JCheckBox textRights;

    private JTextField textBookTitle = new JTextField(10);
    private JTextField textBookGenre = new JTextField(10);
    private JTextField textBookAuthor = new JTextField(10);
    private JTextField textBookQuant = new JTextField(10);
    private JTextField textBookRating = new JTextField(10);

    private JTable table;

    private JTable table2;

    private List<Book> books;
    private List<Account> accounts;

    private JFileChooser reportLocation = new JFileChooser();

    private static int tempID;

    public void addRowToJTable(	) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[7];
        for(Book bk : books) {
            rowData[0] = bk.getId();
            rowData[1] = bk.getTitle();
            rowData[2] = bk.getAuthor();
            rowData[3] = bk.getGenre();
            rowData[4] = bk.getQuantity();
            rowData[5] = bk.getRating();
            model.addRow(rowData);
        }
    }

    public void addRowToAccountTable(	) {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[5];
        for(Account acc : accounts) {
            rowData[0] = acc.getId();
            rowData[1] = acc.getUserName();
            rowData[2] = acc.getPassword();
            rowData[3] = acc.getIsAdmin();
            rowData[4] = acc.getSubscription();
            model.addRow(rowData);
        }
    }

    public AdminView() {

        this.setTitle("Admin view");
        AdminVCRequest adq = new AdminVCRequest();
        AdminViewController adminViewController = new AdminViewController(this, adq);
        books = adminViewController.returnBooks();
        accounts = adminViewController.returnAccounts();
        this.setBounds(100, 100, 855, 457);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2 - 100;

        // Move the window
        this.setLocation(x, y);

        JTabbedPane tabPane = new JTabbedPane();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setLayout(null);
        panel2.setLayout(null);

        JLabel bookTitle = new JLabel("Book title: ");
        bookTitle.setBounds(10, 10, 70, 23);
        panel1.add(bookTitle);

        textBookTitle = new JTextField();
        textBookTitle.setBounds(80, 10, 120, 23);
        panel1.add(textBookTitle);
        textBookTitle.setColumns(10);

        JLabel bookAuthor = new JLabel("Book Author: ");
        bookAuthor.setBounds(210, 10, 75, 23);
        panel1.add(bookAuthor);

        textBookAuthor = new JTextField();
        textBookAuthor.setBounds(285, 10, 120, 23);
        panel1.add(textBookAuthor);
        textBookAuthor.setColumns(10);

        JLabel bookRating = new JLabel("Book Rating: ");
        bookRating.setBounds(210, 40, 75, 23);
        panel1.add(bookRating);

        textBookRating = new JTextField();
        textBookRating.setBounds(285, 40, 120, 23);
        panel1.add(textBookRating);
        textBookRating.setColumns(10);

        JLabel bookQuant = new JLabel("Book Quantity: ");
        bookQuant.setBounds(415, 10, 90, 23);
        panel1.add(bookQuant);

        textBookQuant = new JTextField();
        textBookQuant.setBounds(500, 10, 120, 23);
        panel1.add(textBookQuant);
        textBookQuant.setColumns(10);

        JLabel bookGenre = new JLabel("Book Genre: ");
        bookGenre.setBounds(10, 40, 75, 23);
        panel1.add(bookGenre);

        textBookGenre = new JTextField();
        textBookGenre.setBounds(80, 40, 120, 23);
        panel1.add(textBookGenre);
        textBookGenre.setColumns(10);

        JButton btnAddBook = new JButton("Add Book");
        btnAddBook.addActionListener(actionEvent -> {
            adminViewController.addBook();
            books = adminViewController.returnBooks();
            addRowToJTable();
        });
        btnAddBook.setBounds(10, 80, 100, 23);
        panel1.add(btnAddBook);

        JButton btnDeleteBook = new JButton("Delete Book");
        btnDeleteBook.addActionListener(actionEvent -> {
            adminViewController.deleteBook();
            books = adminViewController.returnBooks();
            addRowToJTable();
        });
        btnDeleteBook.setBounds(10, 110, 100, 23);
        panel1.add(btnDeleteBook);

        JButton btnUpdateBook = new JButton("Update Book");
        btnUpdateBook.addActionListener(actionEvent -> {
                adminViewController.updateBook();
                books = adminViewController.returnBooks();
                addRowToJTable();
                }
        );
        btnUpdateBook.setBounds(10, 140, 100, 23);
        panel1.add(btnUpdateBook);

        JButton btnRefreshTable = new JButton("Refresh");
        btnRefreshTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                books = adminViewController.returnBooks();
                addRowToJTable();
            }
        });
        btnRefreshTable.setBounds(10, 170, 100, 23);
        panel1.add(btnRefreshTable);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> this.setVisible(false));
        btnLogout.addActionListener(e -> ClientConnection.main(null));
        btnLogout.setBounds(720, 10, 100, 23);
        panel1.add(btnLogout);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(177, 80, 620, 293);
        panel1.add(scrollPane1);

        table = new JTable();
        scrollPane1.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Title", "Author", "Genre", "Quantity", "Rating"
                }
        ));
        this.setVisible(true);

        ListSelectionModel lsm = table.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.de
                if (e.getValueIsAdjusting()) {return;}

                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int selectedRow = lsm.getMinSelectionIndex();
                    tempID = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
                    textBookTitle.setText(table.getModel().getValueAt(selectedRow, 1).toString());
                    textBookAuthor.setText(table.getModel().getValueAt(selectedRow, 2).toString());
                    textBookGenre.setText(table.getModel().getValueAt(selectedRow, 3).toString());
                    textBookQuant.setText(table.getModel().getValueAt(selectedRow, 4).toString());
                    textBookRating.setText(table.getModel().getValueAt(selectedRow, 5).toString());
                }
            }
        });

        addRowToJTable();
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Pana aici TAB 1

        JLabel accUsername = new JLabel("Username: ");
        accUsername.setBounds(10, 10, 70, 23);
        panel2.add(accUsername);

        textUsername = new JTextField();
        textUsername.setBounds(80, 10, 120, 23);
        panel2.add(textUsername);
        textUsername.setColumns(10);

        JLabel accPass = new JLabel("Password: ");
        accPass.setBounds(210, 10, 75, 23);
        panel2.add(accPass);

        textPass = new JTextField();
        textPass.setBounds(285, 10, 120, 23);
        panel2.add(textPass);
        textPass.setColumns(10);

        JLabel accRights = new JLabel("Admin rights: ");
        accRights.setBounds(10, 40, 75, 23);
        panel2.add(accRights);

        textRights = new JCheckBox();
        textRights.setBounds(80, 40, 120, 23);
        panel2.add(textRights);

        JButton btnAddUser = new JButton("Add User");
        btnAddUser.addActionListener(actionEvent -> {
            adminViewController.addAccount();
            accounts = adminViewController.returnAccounts();
            addRowToAccountTable();
        });
        btnAddUser.setBounds(10, 80, 100, 23);
        panel2.add(btnAddUser);

        JButton btnDeleteUser = new JButton("Delete User");
        btnDeleteUser.addActionListener(actionEvent -> {
            adminViewController.deleteAccount();
            accounts = adminViewController.returnAccounts();
            addRowToAccountTable();

        });
        btnDeleteUser.setBounds(10, 110, 100, 23);
        panel2.add(btnDeleteUser);

        JButton btnUpdateUser = new JButton("Update User");
        btnUpdateUser.addActionListener(actionEvent -> {
            adminViewController.updateAccount();
            accounts = adminViewController.returnAccounts();
            addRowToAccountTable();

        });
        btnUpdateUser.setBounds(10, 140, 100, 23);
        panel2.add(btnUpdateUser);

        String[] searchStrings = {"PDF", "TEXT"};
        JComboBox searchList = new JComboBox(searchStrings);
        searchList.setSelectedIndex(1);
        searchList.setBounds(10, 230, 100, 23);
        panel2.add(searchList);

        JButton btnGenerateRaport= new JButton("Generate Raport");
        btnGenerateRaport.addActionListener(actionEvent -> {
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showSaveDialog(null);
            reportLocation = f;
            adminViewController.generateRaport(searchList.getSelectedItem().toString());
        });
//        btnGenerateRaport.addActionListener(e -> adminViewController.generateRaport(searchList.getSelectedItem().toString()));
        btnGenerateRaport.setBounds(10, 200, 150, 23);
        panel2.add(btnGenerateRaport);

        JButton btnLogout2 = new JButton("Logout");
        btnLogout2.addActionListener(e -> this.setVisible(false));
        btnLogout2.addActionListener(e -> ClientConnection.main(null));
        btnLogout2.setBounds(720, 10, 100, 23);
        panel2.add(btnLogout2);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(177, 80, 620, 293);
        panel2.add(scrollPane2);

        table2 = new JTable();
        scrollPane2.setViewportView(table2);
        table2.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID", "Username", "Password", "Admin rights", "Borrow left"
                }
        ));
        this.setVisible(true);

        ListSelectionModel lsm2 = table2.getSelectionModel();
        lsm2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {return;}

                ListSelectionModel lsm2 = (ListSelectionModel)e.getSource();
                if (!lsm2.isSelectionEmpty()) {
                    int selectedRow = lsm2.getMinSelectionIndex();
                    tempID = Integer.parseInt(table2.getModel().getValueAt(selectedRow, 0).toString());
                    textPass.setText(table2.getModel().getValueAt(selectedRow, 2).toString());
                    textUsername.setText(table2.getModel().getValueAt(selectedRow, 1).toString());
                    textRights.setText(table2.getModel().getValueAt(selectedRow, 3).toString());
                }
            }
        });

        addRowToAccountTable();

        tabPane.add("Tab 1", panel1);
        tabPane.add("Tab 2", panel2);
        this.add(tabPane);
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public String getTitle(){
        return textBookTitle.getText();
    }

    @Override
    public String getAuthor(){
        return textBookAuthor.getText();
    }

    @Override
    public String getGenre(){
        return textBookGenre.getText();
    }

    @Override
    public String getStringId(){
        return String.valueOf(tempID);
    }

    @Override
    public long getID(){
        return 1;
    }

    @Override
    public String getUsername(){
        return textUsername.getText();
    }

    @Override
    public String getPassword(){
        return textPass.getText();
    }

    @Override
    public boolean getAdminRights(){
        return textRights.isSelected();
    }

    @Override
    public int getQuantity(){
        return Integer.parseInt(textBookQuant.getText());
    }

    public long getLoggedInId(){
        return 0;
    }

    @Override
    public JFileChooser getReportLocation(){
        return  this.reportLocation;
    }

    @Override
    public int getRating(){ return Integer.parseInt(textBookRating.getText()); }
}
