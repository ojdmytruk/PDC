package task4;

import java.util.ArrayList;

public class DocumentResult {
    private String documentName;
    public ArrayList<String> itWords = new ArrayList<>();

    DocumentResult(String documentName){
        this.documentName = documentName;
    }

    public String getDocumentName(){
        return this.documentName;
    }

}
