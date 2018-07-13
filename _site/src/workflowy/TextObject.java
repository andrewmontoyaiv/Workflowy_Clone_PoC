
package workflowy;

//serves as an instance of DataField. Used for saving objects to a map. (this is the second variable in a map
public class TextObject {
    private int tab;
    private String text;

    public TextObject() {
        
    }

    public TextObject(int tab, String text) {
        this.tab = tab;
        this.text = text;
    }

    public int getTab() {
        return tab;
    }

    public String getText() {
        return text;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
