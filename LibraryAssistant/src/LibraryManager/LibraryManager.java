package LibraryManager;
//Program written by Logan Hubbard

//The purpose of this program is to be a simple library system manager that
//stores books/items in a file

import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.logging.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;


public class LibraryManager extends Application {
    private static TableView<AddBook> tableBookData = new TableView<>();
    private static ObservableList<AddBook> data = 
                FXCollections.observableArrayList();
    
    public TextField addBookNum = new TextField();
    public TextField addAvailability = new TextField();
    public TextField addNewBook = new TextField();
    
    Stage assistantWindow;    
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        assistantWindow = primaryStage;
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        tableBookData.setEditable(true);
        
        File F;
        F  = new File("SavedLibraryItems.txt");
        try (Scanner S = new Scanner(F)) {
            while (S.hasNextLine()) {
                AddBook e = new AddBook(S.next(), S.next(), S.nextLine());
                data.add(e);
            }
        }
        
        final Label label = new Label("Library Assistant V 1.0");
        label.setFont(new Font("Impact", 22));
        
        TableColumn bookNameCol = new TableColumn("Book Name");
        bookNameCol.setMinWidth(250);
        bookNameCol.setCellValueFactory(
                new PropertyValueFactory<AddBook, String>("item"));
 
        TableColumn bookNumCol = new TableColumn("Book Number");
        bookNumCol.setMinWidth(150);
        bookNumCol.setCellValueFactory(
                new PropertyValueFactory<AddBook, String>("number"));
 
        TableColumn availablilityCol = new TableColumn("Availability");
        availablilityCol.setMinWidth(450);
        availablilityCol.setCellValueFactory(
                new PropertyValueFactory<AddBook, String>("notes"));
        
        tableBookData.setItems(data);
        tableBookData.getColumns().addAll
        (bookNameCol, bookNumCol, availablilityCol);
      
        addBookNum.setMaxWidth(bookNumCol.getPrefWidth());
        addBookNum.setPromptText("Book Number");
        
        addNewBook.setPromptText("Book Name");
        addNewBook.setMaxWidth(bookNameCol.getPrefWidth());
        
        addAvailability.setMaxWidth(availablilityCol.getPrefWidth());
        addAvailability.setPromptText("Availability");
 
        final Button addButton = new Button("Create New Entry");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new AddBook(
                        addNewBook.getText(),
                        addBookNum.getText(),
                        addAvailability.getText()));
                addNewBook.clear();
                addBookNum.clear();
                addAvailability.clear();
            }
        });
        
        TableColumn col_action = new TableColumn<>("Delete Entry");
        tableBookData.getColumns().add(col_action);
        
        col_action.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<AddBook, Boolean>, 
                ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean>
                call(TableColumn.CellDataFeatures<AddBook, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        
        col_action.setCellFactory(
                new Callback<TableColumn<AddBook, Boolean>,
                        TableCell<AddBook, Boolean>>() {

            @Override
            public TableCell<AddBook, Boolean> 
                call(TableColumn<AddBook, Boolean> p) {
                return new ButtonCell(tableBookData);
            }
        
        });
        
        Button quit_button = new Button();
        quit_button.setText("Exit Program");
        quit_button.setOnAction((ActionEvent event) -> {
            closeProgram();
        });
        
        Button savebtn = new Button();
        savebtn.setText("Save");
        savebtn.setOnAction(e -> {
            try {
                saveInventory();
            } catch (Exception ex) {
               Logger.getLogger(LibraryManager.class.getName()
                ).log(Level.SEVERE, null, ex);
            }
        });
        
       Button saveNQuit = new Button();
        saveNQuit.setText("Save and Exit");
        saveNQuit.setOnAction(e -> closeProgram());
 
        hBox1.getChildren().addAll
        (addNewBook, addBookNum, addAvailability, addButton);
        hBox1.setSpacing(3);
        hBox2.getChildren().addAll(quit_button, saveNQuit, savebtn);
        hBox2.setSpacing(15);
        
        StackPane root = new StackPane();
        root.getChildren().addAll(tableBookData);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, tableBookData, hBox1, hBox2);
        
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        assistantWindow.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        
        primaryStage.setWidth(1050);
        primaryStage.setHeight(550);
        primaryStage.setTitle("Library Assistant V1.0 - Writes to"
                + " SavedLibraryItems.txt");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private class ButtonCell extends TableCell<AddBook, Boolean> {
        final Button cellButton = new Button("Delete Entry");
         
        ButtonCell(final TableView tblView){
             
            cellButton.setOnAction((ActionEvent t) -> {
                int selectEntry = getTableRow().getIndex();
                data.remove(selectEntry);
            });
        }
        
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }
    
    public void saveInventory() throws Exception{
        File F;
        F = new File("SavedLibraryItems.txt");
        FileOutputStream out = new FileOutputStream(F);
        PrintStream P = new PrintStream( out);
        
        for(AddBook person : data){
            String text = person.getItem()+ " " + person.getNumber() + " "
                    + person.getNotes();
            P.println(text);
        }
        
     }
    
    private void closeProgram(){
        Boolean answer = CloseProgram.display("Exit Program", 
                "Are you sure you want to exit the program?");
        if(answer){
            try {
                saveInventory();
            } catch (Exception ex) {
                Logger.getLogger(LibraryManager.class.getName()
                ).log(Level.SEVERE, null, ex);
            }
            assistantWindow.close();
        }
    }
}
