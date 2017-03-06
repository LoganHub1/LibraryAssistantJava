package LibraryManager;
 
import javafx.beans.property.SimpleStringProperty;

public class AddBook {
    
    private final SimpleStringProperty bookName;
    private final SimpleStringProperty Quantity;
    private final SimpleStringProperty Availability;
    
    AddBook(String item, String number, String notes) {
            this.bookName = new SimpleStringProperty(item);
            this.Quantity = new SimpleStringProperty(number);
            this.Availability = new SimpleStringProperty(notes);
        }
    
    public String getItem() {
            return bookName.get();
        }
 
        public void setItem(String Item) {
            bookName.set(Item);
        }
 
        public String getNumber() {
            return Quantity.get();
        }
 
        public void setNumber(String fName) {
            Quantity.set(fName);
        }
 
        public String getNotes() {
            return Availability.get();
        }
 
        public void setNotes(String fName) {
            Availability.set(fName);
        }
}
