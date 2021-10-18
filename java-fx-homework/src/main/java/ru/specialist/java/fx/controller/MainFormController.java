package ru.specialist.java.fx.controller;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import ru.specialist.java.fx.Author;

import java.sql.*;
import java.util.Optional;

public class MainFormController {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "postgres";

    @FXML
    public ContextMenu contextMenu;

    @FXML
    private TextField authorNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TableView<Author> tableAuthors;

    @FXML
    private TableColumn<Author, String> authorNameColumn;

    @FXML
    private TableColumn<Author, String> lastNameColumn;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        // устанавливаем тип и значение которое должно хранится в колонке
        authorNameColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        authorNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        // заполняем таблицу данными
        tableAuthors.setItems(initData());
        addButtonToTable();
//Раскоментировать для того, чтобы в таблице небыло лишних строк
//        tableAuthors.setFixedCellSize(25);
//        tableAuthors.prefHeightProperty().bind(Bindings.size(tableAuthors.getItems()).multiply(tableAuthors.getFixedCellSize()).add(30));
        tableAuthors.getSelectionModel().select(0);
    }

    @FXML
    private void addPerson(ActionEvent event) {
        ObservableList<Author> data = tableAuthors.getItems();
        String authorName = authorNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        if (authorName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Поле с именем автора не должно быть пустым.");
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.showAndWait();
            return;
        } else if (lastName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Поле с фамилией автора не должно быть пустым.");
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.showAndWait();
            return;
        }
        data.add(new Author(insertInAuthors(authorName, lastName),
                authorName,
                lastName
        ));
        authorNameField.setText("");
        lastNameField.setText("");
    }

    @FXML
    private void deleteRow(ActionEvent actionEvent) {
        deletePerson(tableAuthors.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void editAuthorName(TableColumn.CellEditEvent<Author, String> event) {
        TablePosition<Author, String> pos = event.getTablePosition();
        String newAuthorName = event.getNewValue();
        int row = pos.getRow();
        Author author = event.getTableView().getItems().get(row);
        author.setAuthorName(newAuthorName);
        updateAuthors(author);
    }

    @FXML
    private void editLastName(TableColumn.CellEditEvent<Author, String> event) {
        TablePosition<Author, String> pos = event.getTablePosition();
        String newLastName = event.getNewValue();
        int row = pos.getRow();
        Author author = event.getTableView().getItems().get(row);
        author.setLastName(newLastName);
        updateAuthors(author);
    }

    @FXML
    private void keyPressed(KeyEvent keyEvent) {
        int selectedIndex = tableAuthors.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        switch (keyEvent.getCode().toString()) {
            case "DOWN":
                int rowQuantity = tableAuthors.getItems().size();
                if (selectedIndex == (rowQuantity - 1)) {
                    System.out.println("Запрос на добавление новой строки");
                }
                break;
            case "DELETE":
                deletePerson(selectedIndex);
                break;
        }
    }

    @FXML
    private void buttonExitClick(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void deletePerson(int row) {
        ObservableList<Author> data = tableAuthors.getItems();
        Author author = data.get(row);
        int deleteResult = 0;
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены, что хотите удалить этого автора?");
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Подтверждение");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteResult = delete(author.getId());
        } else {
            return;
        }
        if (deleteResult != 0) {
            data.remove(row);
        } else {
            alert = new Alert(Alert.AlertType.ERROR, "Не удалось удалить этого автора?");
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка");
            alert.showAndWait();
        }
        tableAuthors.getSelectionModel().select(row);
    }

    private int insertInAuthors(String authorName, String lastName) {
        //"INSERT INTO authors (author_name, last_name) VALUES (?, ?)";
        int author_id = 0;
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement statement = c.prepareStatement("INSERT INTO authors (author_name, last_name) " +
                    "VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, authorName);
            statement.setString(2, lastName);
            statement.executeUpdate();

            ResultSet set = statement.getGeneratedKeys();
            while (set.next()) {
                author_id = set.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return author_id;
    }

    private void updateAuthors(Author author) {
        //"UPDATE authors SET author_name = '', last_name = 'Воронеж' WHERE author_id = 1";
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement statement;
            statement = c.prepareStatement("UPDATE authors SET author_name = ?, last_name = ? WHERE author_id = ?", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, author.getAuthorName());
            statement.setString(2, author.getLastName());
            statement.setInt(3, author.getId());
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            while (set.next()) {
                System.out.println("Generated book_id: " + set.getInt(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // подготавливаем данные для таблицы
    private ObservableList<Author> initData() {
        ObservableList<Author> authorsList = FXCollections.observableArrayList();
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            Statement statement = c.createStatement();
            ResultSet set = statement.executeQuery("SELECT author_id, author_name, last_name " +
                    "FROM authors ORDER BY 1");
            while (set.next()) {
                int id = set.getInt("author_id");
                String authorName = set.getString("author_name");
                String lastName = set.getString("last_name");
                authorsList.add(new Author(id, authorName, lastName));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return authorsList;
    }

    private int delete(int value) {
        //"DELETE FROM authors WHERE author_id = 1";
        int result = 0;
        try (Connection c = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            PreparedStatement statement = c.prepareStatement("DELETE FROM authors WHERE author_id = ?", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, value);
            statement.executeUpdate();
            ResultSet set = statement.getGeneratedKeys();
            result = set.getMetaData().getColumnCount();
            if (result == 0) {
                System.out.println("Nothing to delete");
            }
            while (set.next()) {
                System.out.printf("row %d was deleted%n", set.getInt(1));
            }
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
        }
        return result;
    }

    private void addButtonToTable() {
        TableColumn<Author, Void> colBtn = new TableColumn<>("Del");
        Callback<TableColumn<Author, Void>, TableCell<Author, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Author, Void> call(final TableColumn<Author, Void> param) {
                final TableCell<Author, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("-");

                    {
                        btn.setOnAction((ActionEvent event) -> {
//                            Author author = getTableView().getItems().get(getIndex());
                            deletePerson(getIndex());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        tableAuthors.getColumns().add(colBtn);
    }
}
