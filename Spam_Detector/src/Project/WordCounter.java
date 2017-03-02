package Project;

import java.io.*;
import java.util.*;

/**
 * Created by faizan on 19/02/17.
 */

public class WordCounter {

    //#of files
    private int numHam;
    private int numSpam;

    // maps to hold the ham and spam word frequency
    private Map<String,Integer> trainHamFreq;
    private Map<String,Integer> trainSpamFreq;

    // map to hold the probabilities
    private Map<String,Float> probabilityFileisSpam;
    private Map<String,Float> probabilitywordInSpam;
    private Map<String,Float> probabilitywordInHam;



    public WordCounter() {
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        probabilityFileisSpam = new TreeMap<>();
        probabilitywordInSpam = new TreeMap<>();
        probabilitywordInHam = new TreeMap<>();

        numHam = 0;
        numSpam = 0;
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
                numHam++;
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

                numSpam++;

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
     * @return boolean true/false
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
     * Calculate the probability the word is in the spam file, ham file and the probability that a file is spam given
     * that it contains a certain word.  Save the probabilities into corresponding maps
     */
    public void calculateProbability(){

        // calculate and save the probability that a word in the trainHamFreq map appears in a ham file into
        // the probabilitywordInHam map
        Set<String> keys = trainHamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = trainHamFreq.get(key);
            probabilitywordInHam.put(key,(float)count/numHam);
        }

        // calculate and save the probability that a word in the trainHamSpam map appears in a spam file into
        // the probabilitywordInSpam map
        keys = trainSpamFreq.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = trainSpamFreq.get(key);
            probabilitywordInSpam.put(key,(float)count/numSpam);
        }


        // calculate and save the probability that a file is spam given that it contains a word in a spam file
        keys = probabilitywordInSpam.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if(probabilitywordInHam.containsKey(key))
                probabilityFileisSpam.put(key,probabilitywordInSpam.get(key)/(probabilitywordInSpam.get(key) + probabilitywordInHam.get(key)));
            else
                probabilityFileisSpam.put(key,1.0f);
        }


    }

    /**
     * Prints out all the words and number of times they were seen in the Frequency maps
     */
    public void printWordCounts(){

//        System.out.println("Number of ham files: " + numHam);
//        System.out.println(trainHamFreq);
//        System.out.println(probabilitywordInHam);
//
//        System.out.println("Number of spam files: " +numSpam);
//        System.out.println(trainSpamFreq);
//        System.out.println(probabilitywordInSpam);


        // print all the words and probability in the map probabilityFileisSpam (Pr(𝑆|𝑊𝑖)) line by line
        Set<String> keys = probabilityFileisSpam.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            Float count = probabilityFileisSpam.get(key);

            System.out.println(key + " " + count);
        }
    }


}
