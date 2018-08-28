import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SortUtil {

    public static void main(String[] args) throws Exception {

        String filepath = "C:\\data\\files\\"; //update this to loc where your source file is placed
        String sourceFileName = "source.txt"; //update this to name of your source file

        long t1 = new Date().getTime();  // timestamp for performance measurement
        int i = 0; // to append to temp file names
        Set<String> set = new TreeSet<String>();
        BufferedReader br = new BufferedReader(new FileReader(new File( filepath + sourceFileName))); //reading source file
        String s = br.readLine(); // reading first line
        while( s != null ) {
            String[] words = s.split(" ");
            for (String w : words) {
                if (w != null && w != " " && w != "") {
                    set.add(w.replaceAll("[^a-zA-Z0-9]", "")); //getting rid of spl chars - storing only alphanumeric chars of the words
                }
            }  // storing each word of first line in set


            if(set.size() >= 6000) {  //checking set size to flush it and add contents to a temp file
                FileWriter fw = new FileWriter(new File(filepath + "temp-" +i+".txt"));
                for (String x : set) {
                    fw.write(x + System.getProperty("line.separator"));  //adding linebreak after each word added in a new temp file for ease of using readline for reading later
                }
                fw.close();
                i++;
                set = new TreeSet<String>(); //reinitializing the treeset
            }
            s = br.readLine(); //reading next line
        }

        File f = new File(filepath + "temp-0.txt");
        if(!f.exists()) {   //checking if no temp file created cos set size never went past specified limit, in that case creating one temp file with all set data
            FileWriter fw = new FileWriter(new File(filepath + "output.txt"));
            for (String x : set) {
                fw.write(x + System.getProperty("line.separator"));  //adding linebreak after each word added in a new temp file for ease of using readline for reading later
            }
            fw.close();
            long t2 = new Date().getTime();
            System.out.println("Program runtime: " +(t2-t1)/1000 + " sec"); //printing  performace on console
            System.out.println("Output file: " + filepath + "output.txt"); //printing output file location on console
            System.exit(0); //exit the prog here since operation is performed via single temp file
        }

        br.close(); // bufferedreader closed

        Map<String, Integer> map = new TreeMap<String, Integer>(); //creating a treemap - addresses our problem since treemap contains keys in asc sort and Map class has only unique keys

        BufferedReader[] brArr = new BufferedReader[i+1]; //array of i (no of temp files) bufferedreaders

        for(int j=0; j<i; j++) {
            brArr[j] = new BufferedReader(new FileReader(new File(filepath + "temp-" + j +".txt"))); //reading a temp file
            brArr[j].readLine(); //reading first empty line in temp files and disregarding
            map.put(brArr[j].readLine(), j); //putting a word from that temp file as key with fileno. as value - if a non-unique word is found in a temp file then treemap handles that (key just gets updated without dup entry)
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath + "output.txt"))); //reading the output file

        while(!map.isEmpty()) {
            s = map.keySet().iterator().next(); //setting iterator on map values (the words)
            i = map.get(s); // getting temp fileno. of the word (the i prefix)
            map.remove(s); //removing this record from map
            bw.write(s + System.getProperty("line.separator")); //writing this word in output file and adding linebreak after each word is written (depends on requirement we can change it to space)
            s = brArr[i].readLine(); //reading next line (effectively next word) from temp file whose entry is removed from map
            if(s != null ) {
                map.put(s, i); //adding word and index from that temp file to map again
            }
        }
        bw.close();

        for(int j=0; j<brArr.length-1; j++) {
            brArr[j].close();
            new File(filepath + "temp-" +j+".txt").delete(); //deleting all temp files
        }

        long t2 = new Date().getTime();
        System.out.println("Program runtime: " +(t2-t1)/1000 + " sec"); //printing  performace on console
        System.out.println("Output file: " + filepath + "output.txt"); //printing output file location on console
    }

}
