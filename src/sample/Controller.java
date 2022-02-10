package sample;




import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Controller {
    @FXML
    TextArea messageIn;
    @FXML
    ListView list;
    @FXML
    TextArea messageOut;
    @FXML
    TextField hash;
    @FXML
    Button Prev; @FXML Button Next; @FXML Button Edit;  @FXML Button Dlt; @FXML Button Open;
    @FXML
    Button Up;   @FXML Button Dw;   @FXML Button Print; @FXML Button Add; @FXML Button Save;
    @FXML Button GetMAS;
    @FXML
    Label File;
    @FXML
    Label Msg;
    @FXML
    Label MsgOut;

    ObservableList<String> itims= FXCollections.observableArrayList();
    static ArrayList<String> messeges=new ArrayList<>();
    int ii=0;
    String hashText="";
    ArrayList<String> messegesOut=new ArrayList<>();

    @FXML
    void initialize(){

    }

    @FXML
    void next(){
        list.setItems(null);
        hash.setText("");
        messageOut.setText("");
        if(ii<messeges.size()-1) {
            ii++;
            messageIn.setText(messeges.get(ii));
        } else ii=messeges.size()-1;
        Msg.setText(ii+1+" из "+messeges.size());

    }

    @FXML
    void prev(){
        list.setItems(null);
        hash.setText("");
        messageOut.setText("");
        if(ii>0 ) {
            ii--;
            messageIn.setText(messeges.get(ii));
        } else ii=0;
        Msg.setText(ii+1+" из "+messeges.size());
    }

    @FXML
    void edit(){
        itims.clear();
        list.refresh();
        String edText=messageIn.getText();
        //убираем разрывы строк
        String [] stT=edText.split("\n");
        edText="";
        for (String st:stT){edText+=st;}
        //убираем  !
        edText=edText.replaceAll("!","");
        //убираем первое =
        edText=edText.substring(0,1).replace("=","")+edText.substring(1);
        //убираем эмодзи
        String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Z}]";
        //
        String [] strText=edText.split("=");
        for (String str:strText){

            str=str.replaceAll(regex,"");
            str=str.trim();
            itims.add(str.toLowerCase());
        }
        //System.out.println(itims);
        list.setItems(itims);
    }
    @FXML
    void editList(){



        list.refresh();
    }
    @FXML
    void opAr(){
    messageIn.setText(messeges.get(0));
    String lbl="1 из "+messeges.size();
    Msg.setText(lbl);
    System.out.println(messeges);
    System.out.println(messeges.size());
    File.setText("BOT: OK");
}
    @FXML
    void  open(){

        try {
            Stage mainStage = new Stage();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,300);
            mainStage.setScene(scene);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("*.txt", "*.txt" )
                    , new FileChooser.ExtensionFilter("all", "*.*")
            );

            File selectedFile = fileChooser.showOpenDialog(mainStage);
            if (selectedFile != null) {
                //System.out.println(selectedFile);
                FileReader fr=new FileReader(selectedFile);
                BufferedReader br=new BufferedReader(fr);
                String s;
                while((s=br.readLine())!=null){

                   if(!s.equals("")) messeges.add(s);
                }
                File.setText("File: OK");
                messageIn.setText(messeges.get(0));
                String lbl="1 из "+messeges.size();
                Msg.setText(lbl);
                System.out.println(messeges);
                System.out.println(messeges.size());
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
     }
     static void getMas(){
    messeges.addAll(Bot.mesages);
        Bot.mesages.clear();

    }


    @FXML
    void dlt(){
        int num=list.getSelectionModel().getSelectedIndex();
        itims.remove(num);
        list.setItems(itims);
    }

    @FXML
    void up(){

        int num=list.getSelectionModel().getSelectedIndex();
        if (num>0) {
            String numer = itims.get(num);
            String numerMin = itims.get(num - 1);
            itims.set(num, numerMin);
            itims.set(num - 1, numer);
            list.setItems(itims);
            list.getSelectionModel().select(num-1);
        }
    }
    @FXML
    void dw() {

        int num = list.getSelectionModel().getSelectedIndex();
        if (num < itims.size() - 1) {
            String numer = itims.get(num);
            String numerPl = itims.get(num + 1);
            itims.set(num, numerPl);
            itims.set(num + 1, numer);
            list.setItems(itims);
            list.getSelectionModel().select(num+1);
        }
    }

    @FXML
    void print(){
        hashText="";
        String hT= hash.getText();
        if (!hT.equals("")) {
            String[] hText = hT.split(" ");
            for (String htr : hText) {
                //System.out.println(str);
                hashText += "#" + htr + " ";
            }
        }
        ObservableList<String> itimss= FXCollections.observableArrayList();
        itimss.addAll(list.getItems());
        String txtItims="";
        for(String txt:itimss){
            if(!txt.equals("")) {
                int l=txt.length()-1;

                if (txt.substring(l).equals(".")||txt.substring(l).equals(";"))
                txtItims += "- " + txt.substring(0, 1).toUpperCase() + txt.substring(1,l) + "\n";
                else txtItims += "- " + txt.substring(0, 1).toUpperCase() + txt.substring(1) + "\n";
            }
        }
        String textOut= hashText+"\n"+txtItims;
        messageOut.setText(textOut);
    }

    @FXML
    void add(){
    messegesOut.add(messageOut.getText());
        MsgOut.setText("Подготовлено "+messegesOut.size()+" сообщ.");
    }

    @FXML
    void save(){
        try {
            Stage mainStage = new Stage();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,300);
            mainStage.setScene(scene);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("*.txt", "*.txt")
            );
            File selectedFile = fileChooser.showSaveDialog(mainStage);

            if (selectedFile != null) {
        try(FileWriter writer = new FileWriter(selectedFile))
        {
            // запись всей строки
            String text = "";
            for(String txt:messegesOut){
                text+=txt+"\n"+"\n";
            }
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){ System.out.println(ex.getMessage());  }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

}
