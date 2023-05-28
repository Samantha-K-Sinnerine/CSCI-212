import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;


/**
 * This class implements an ActionListener to handle "File" menu events for the RomanNumeralGUI.
 * It has methods to open a file and read its contents, populating a TreeMap consisting of RomanNumerals
 * and their Integer Arabic values. The key/value pairs are then displayed in the respective TextAreas of the given JFrame.
 */
public class FileMenuHandler implements ActionListener {
   RomanNumeralGUI jframe;
   public FileMenuHandler (RomanNumeralGUI jf) {
      jframe = jf;
   }
   /**
    * Handles the action events generated by menu items in the application.
    * @param event the ActionEvent object representing the action event generated by a menu item.
    */
   public void actionPerformed(ActionEvent event) {
      //get name of event
      String menuName = event.getActionCommand();
      if (menuName.equals("Open"))
         openFile();
      else if (menuName.equals("Quit"))
         System.exit(0);
   } //actionPerformed

   /**
    * Opens a file using a file chooser dialog.
    * The selected file is then passed to the readSource() method for further processing.
    */
   private void openFile(){
      int status;
      JFileChooser chooser = new JFileChooser();
      status = chooser.showOpenDialog(null);
      readSource(chooser.getSelectedFile());
   } //openFile

   /**
    * Reads lines of text from a file containing Roman numerals seperated by commas, tokenzies it and inserts each
    * Roman numeral and its Arabic value as a key/value pair in a TreeMap
    * @param chosenFile the file to read Roman numerals from
    */
   private void readSource(File chosenFile) {

      //generate empty TreeMap
      TreeMap<RomanNumeral, Integer> romanNumeralTreeMap = new TreeMap<>(new RomanNumeralComparator());

      //retrieve the absolute path of the chosen input file
      String chosenFileName = chosenFile.getAbsolutePath();

      //read a line of text from the file and tokenize it
      TextFileInput in = new TextFileInput(chosenFileName);
      String line = in.readLine();
      StringTokenizer myTokens = new StringTokenizer(line, ",");

      //populate the TreeMap
      while(true) {
         //increment through each Roman numeral (token) in the line
         for (int i = 0; myTokens.hasMoreTokens(); i++) {
            //temporary placeholder for the Roman numeral string
            String temp = myTokens.nextToken();
            //validates each character in the Roman numeral token, outputs invalid numerals to console
            try{
               RomanNumeral r = new RomanNumeral(temp);
               romanNumeralTreeMap.put(r,r.getArabicValue());
            } catch (IllegalRomanNumeralException irne){
               System.out.println("Invalid character(s) in Roman Numeral: "+ temp);
            }
         }//end for
         line = in.readLine();
         //exit the while loop when all lines containing Roman numerals have been read
         if (line == null) {
            break;
         } else {
            myTokens = new StringTokenizer(line, ",");
         }//end if
      }//end while

      //output contents of TreeMap to GUI
      Set set = romanNumeralTreeMap.entrySet();
      Iterator i = set.iterator();
      Map.Entry<RomanNumeral,Integer> me;

      while(i.hasNext()){
         me = (Map.Entry)i.next();
         jframe.myRomanNumerals.append(me.getKey()+"\n");
         jframe.myArabicValues.append(me.getValue()+"\n");
      }
   } //readSource

}


