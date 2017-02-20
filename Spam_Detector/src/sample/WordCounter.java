package sample;

import java.io.*;
import java.util.*;

/**
 * Created by faizan on 19/02/17.
 */

public class WordCounter {

    // maps to hold the ham and spam word frequency
    private Map<String,Integer> trainHamFreq;
    private Map<String,Integer> trainSpamFreq;

    public WordCounter() {
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
    }


    /**
     *  Goes through all the folders and files in the directory and save all word counts into appropriate Maps
     * @param file The directory to go through
     * @throws IOException
     */
    public void processFile(File file) throws IOException {

        //if the file is a directory
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
        } else {
            // if the current file is in the training ham folder
            if(file.getAbsolutePath().contains("train/ham")) {
                // read the fil word by word
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    if (isWord(word)) {
                        // add the current word to the trainHapFreq map
                        countWord(word,trainHamFreq);
                    }
                }
             // if the current file is in the training spam folder
            }else if(file.getAbsolutePath().contains("train/spam")) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String word = scanner.next();
                    if (isWord(word)) {
                        // add the current word to the trainSpamFreq map
                        countWord(word,trainSpamFreq);
                    }
                }
            }


        }
    }

    /**
     * Checks to see if the word is already in the map. If it is then increment the number of times it had been seen,
     * else add it to the map
     * @param word The current word you want to add to the map
     * @param map The map you want to add it too
     */
    private void countWord(String word, Map<String,Integer> map) {
        if (map.containsKey(word)) {
            // increment the count
            int oldCount = map.get(word);
            map.put(word, oldCount+1);
        } else {
            // add the word with count of 1
            map.put(word, 1);
        }
    }

    /**
     *  check to see if the word is a word
     * @param token The word you want to check
     * @return
     */
    private boolean isWord(String token) {
        String pattern = "^[a-zA-Z]*$";
        if (token.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Prints out all the words and number of times they were seen in the Frequency maps
     */
    public void printWordCounts(){

        System.out.println(trainHamFreq);
        System.out.println(trainSpamFreq);

    }


}
