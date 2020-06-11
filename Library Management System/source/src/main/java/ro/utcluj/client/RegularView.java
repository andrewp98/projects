package ro.utcluj.client;

import ro.utcluj.intermediar.Account;
import ro.utcluj.intermediar.Book;
import ro.utcluj.intermediar.BookAccount;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


class RegularView extends JFrame implements IRegularView {

    private Account loggedIn;

    private JTextField textBookTitle = new JTextField(10);
    private JTextField textBookGenre = new JTextField(10);
    private JTextField textBookAuthor = new JTextField(10);
    private JTextField textBookTime = new JTextField(10);
    private JTextField textBookRev;
    private JTextField textBookRate;

    private JTable table;

    private JTable table2;

    private List<Book> books;
    private List<BookAccount> bookAccounts;

    protected static int tempID;
    private RegularViewController rvc;

    public RegularViewController getRvc() {
        return rvc;
    }

    public void setRvc(RegularViewController rvc) {
        this.rvc = rvc;
    }

    public void addRowToJTable(	) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[4];
        for(Book bk : books) {
            rowData[0] = bk.getId();
            rowData[1] = bk.getTitle();
            rowData[2] = bk.getAuthor();
            rowData[3] = bk.getGenre();
            model.addRow(rowData);
        }

    }

    public void addRowToBorrowTable() {
        DefaultTableModel model = (DefaultTableModel) table2.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[2];
        for(BookAccount ba : bookAccounts) {
            rowData[0] = ba.getBookName();
            rowData[1] = ba.getTimeLeft();
            model.addRow(rowData);
        }
    }


    public RegularView(Account acc) {

        this.loggedIn = acc;
        this.getContentPane().setLayout(null);
        this.setTitle("Regular view");
        RegularVCRequest req = new RegularVCRequest();
        RegularViewController regularViewController = new RegularViewController(this, req);
//
        this.setRvc(regularViewController);
        this.setBounds(100, 100, 755, 457);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        books = regularViewController.returnBooks();
        bookAccounts = regularViewController.returnBookAccountsByID();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = this.getSize().width;
        int h = this.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2 - 100;

        // Move the window
        this.setLocation(x, y);

        JLabel bookTitle = new JLabel("Book title: ");
        bookTitle.setBounds(10, 10, 70, 23);
        this.add(bookTitle);

        textBookTitle = new JTextField();
        textBookTitle.setBounds(80, 10, 120, 23);
        this.add(textBookTitle);
        textBookTitle.setColumns(10);

        JLabel bookAuthor = new JLabel("Book Author: ");
        bookAuthor.setBounds(210, 10, 75, 23);
        this.add(bookAuthor);

        textBookAuthor = new JTextField();
        textBookAuthor.setBounds(285, 10, 120, 23);
        this.add(textBookAuthor);
        textBookAuthor.setColumns(10);

        JLabel bookRate = new JLabel("Rate(1-5): ");
        bookRate.setBounds(410, 10, 60, 23);
        this.add(bookRate);

        textBookRate = new JTextField();
        textBookRate.setBounds(470, 10, 100, 23);
        this.add(textBookRate);
        textBookRate.setColumns(10);

        JLabel bookGenre = new JLabel("Book Genre: ");
        bookGenre.setBounds(10, 40, 75, 23);
        this.add(bookGenre);

        textBookGenre = new JTextField();
        textBookGenre.setBounds(80, 40, 120, 23);
        this.add(textBookGenre);
        textBookGenre.setColumns(10);

        JLabel bookTime = new JLabel("Book Time: ");
        bookTime.setBounds(210, 40, 75, 23);
        this.add(bookTime);

        textBookTime = new JTextField();
        textBookTime.setBounds(285, 40, 120, 23);
        this.add(textBookTime);
        textBookTime.setColumns(10);

        JLabel bookRev = new JLabel("Review: ");
        bookRev.setBounds(410, 40, 60, 23);
        this.add(bookRev);

        textBookRev = new JTextField();
        textBookRev.setBounds(470, 40, 100, 23);
        this.add(textBookRev);
        textBookRev.setColumns(10);

        String[] searchStrings = { "Genre", "Title", "Author", "Rating"};
        JComboBox searchList = new JComboBox(searchStrings);
        searchList.setSelectedIndex(1);
        searchList.setBounds(10, 230, 100, 23);
        this.add(searchList);

        JButton btnSearchBook = new JButton("Search Book");
        btnSearchBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (searchList.getSelectedItem().toString()){
                    case "Title":
                        books = regularViewController.searchByTitle();
                        addRowToJTable();
                        break;
                    case "Genre":
                        books = regularViewController.searchByGenre();
                        addRowToJTable();
                        break;
                    case "Author":
                        books = regularViewController.searchByAuthor();
                        addRowToJTable();
                        break;
                    case "Rating":
                        books = regularViewController.searchByRating();
                        addRowToJTable();
                    default:
                        //do nada
                }

            }
        });
        btnSearchBook.setBounds(10, 80, 100, 23);
        this.add(btnSearchBook);

        JButton btnBorrowBook = new JButton("Borrow Book");
        btnBorrowBook.addActionListener(actionEvent -> {
            regularViewController.borrowBook();
            bookAccounts = regularViewController.returnBookAccountsByID();
            addRowToBorrowTable();
        });
        btnBorrowBook.setBounds(10, 110, 100, 23);
        this.add(btnBorrowBook);

        JButton btnReturnBook = new JButton("Return Book");
        btnReturnBook.addActionListener(actionEvent -> {
            regularViewController.returnBook();
            bookAccounts = regularViewController.returnBookAccountsByID();
            addRowToBorrowTable();
        });
        btnReturnBook.setBounds(10, 140, 100, 23);
        this.add(btnReturnBook);

        JButton btnAddToFav = new JButton("Add to fav");
        btnAddToFav.addActionListener(actionEvent -> {
            regularViewController.addToFav();
        });
        btnAddToFav.setBounds(10, 170, 100, 23);
        this.add(btnAddToFav);

        JLabel srcBy = new JLabel("Search by:");
        srcBy.setBounds(10, 200, 100, 23);
        this.add(srcBy);

        JButton btnRefreshTable = new JButton("Refresh");
        btnRefreshTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                books = regularViewController.returnBooks();
                addRowToBorrowTable();
                addRowToJTable();
            }
        });
        btnRefreshTable.setBounds(10, 260, 100, 23);
        this.add(btnRefreshTable);

        JButton btnRateBook = new JButton("Rate book!");
        btnRateBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                regularViewController.rateBook();
            }
        });
        btnRateBook.setBounds(10, 290, 100, 23);
        this.add(btnRateBook);

        JButton btnRevBook = new JButton("Add review!");
        btnRevBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                regularViewController.revBook();
            }
        });
        btnRevBook.setBounds(10, 320, 100, 23);
        this.add(btnRevBook);

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> this.setVisible(false));
        btnLogout.addActionListener(e -> ClientConnection.main(null));
        btnLogout.setBounds(600, 10, 100, 23);
        this.add(btnLogout);


        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(177, 80, 524, 200);
        this.getContentPane().add(scrollPane1);

        table = new JTable();
        scrollPane1.setViewportView(table);
        table.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "ID",  "Title", "Author", "Genre",
                }
        ));
        this.setVisible(true);

        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBounds(177, 290, 524, 100);
        this.getContentPane().add(scrollPane2);

        table2 = new JTable();
        scrollPane2.setViewportView(table2);
        table2.setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "Title", "Time Left"
                }
        ));
        this.setVisible(true);

        ListSelectionModel lsm = table.getSelectionModel();
        lsm.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {return;}

                ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                if (!lsm.isSelectionEmpty()) {
                    int selectedRow = lsm.getMinSelectionIndex();
                    tempID = Integer.parseInt(table.getModel().getValueAt(selectedRow, 0).toString());
                    textBookTitle.setText(table.getModel().getValueAt(selectedRow, 1).toString());
                    textBookAuthor.setText(table.getModel().getValueAt(selectedRow, 2).toString());
                    textBookGenre.setText(table.getModel().getValueAt(selectedRow, 3).toString());
                }
            }
        });

        addRowToJTable();
        addRowToBorrowTable();
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

    public int getQuantity(){
        return 0;
    }

    public void setLoggedIn(Account loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Account getLoggedIn() {
        return loggedIn;
    }

    public long getLoggedInId(){
        return this.loggedIn.getId();
    }

    public String getReturnDate(){
        return this.textBookTime.getText();
    }

    public int getRating(){
        int i = 0;
        try{
            i = Integer.parseInt(this.textBookRate.getText());
        }catch (Exception e){
            this.showErrorMessage("Please complete all fields!");
        }
        return i;
    }

    public String getReview(){
        return this.textBookRev.getText();
    }
    public void refreshTables(){
        System.out.println("O DAT REFRESH");
        books = rvc.returnBooks();
        bookAccounts = rvc.returnBookAccountsByID();
        this.addRowToBorrowTable();
        this.addRowToJTable();
    }

}
