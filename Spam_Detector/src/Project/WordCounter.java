package Project;

import java.io.*;
import java.util.*;

/**
 * Created by faizan on 19/02/17.
 */

public class WordCounter {

    private Optimize optimize;

    //#of files
    private int numHam;
    private int numSpam;

    // maps to hold the ham and spam word frequency when training
    private Map<String,Integer> trainHamFreq;
    private Map<String,Integer> trainSpamFreq;


    // maps to hold the ham and spam word frequency when testing
    private Map<String,Double> testHamProb;
    private Map<String,Double> testSpamProb;

    // map to hold the probabilities
    private Map<String,Double> probabilityFileisSpam;
    private Map<String,Double> probabilitywordInSpam;
    private Map<String,Double> probabilitywordInHam;

    public WordCounter() {
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();

        testHamProb = new TreeMap<>();
        testSpamProb = new TreeMap<>();

        probabilityFileisSpam = new TreeMap<>();
        probabilitywordInSpam = new TreeMap<>();
        probabilitywordInHam = new TreeMap<>();

        numHam = 0;
        numSpam = 0;
    }

    /**
     * Goes through all the directories and if you get to a file that is
     * not the directory send it to the processFile function
     * @param file The directory to go through
     * @throws IOException
     */
    public void traverseDirectory(File file)throws IOException{
        //if the file is a directory
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            for (int i = 0; i < filesInDir.length; i++) {
                traverseDirectory(filesInDir[i]);
            }
        }
        else {
            if(file.getAbsolutePath().contains("train"))
                processFileTrain(file);
            else
                processFileTest(file);
        }
    }

    /**
     * read all the words in the train file and save it into the appropriate map
     * @param file The file to go through
     * @throws IOException
     */
    public void processFileTrain(File file) throws IOException {

        Scanner scanner = new Scanner(file);
        // if the current file is in the training ham folder
        if(file.getAbsolutePath().contains("train/ham")) {
            numHam++;
            // read the fil word by word
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (isWord(word)) {
                    // add the current word to the trainHapFreq map
                    countWord(word,trainHamFreq);
                }
            }
            // if the current file is in the training spam folder
        }
        else if(file.getAbsolutePath().contains("train/spam")) {
            numSpam++;
            while (scanner.hasNext()) {
                String word = scanner.next().toLowerCase();
                if (isWord(word)) {
                    // add the current word to the trainSpamFreq map
                    countWord(word,trainSpamFreq);
                }
            }
        }
    }

    /**
     * read all the words in the test file and save it into the appropriate map, calculate if the file is ham or spam
     * and display it onto the table
     * @param file The file to go through
     * @throws IOException
     */
    public void processFileTest(File file) throws IOException {
        Scanner scanner = new Scanner(file);


        double probWordSpam = 0;
        while (scanner.hasNext()) {
            String word = scanner.next();
            if (isWord(word) && probabilityFileisSpam.containsKey(word))
                // computes combined probability
                probWordSpam += calculateSpamProbability(word);
        }
        double fileIsSpam = 1/(1+(Math.pow(Math.E,probWordSpam)));

        if(file.getAbsolutePath().contains("test/ham")) {
            testHamProb.put(file.getName(),fileIsSpam);
            EmailList.setEmail(file.getName(), fileIsSpam, "Ham");
        }
        else if(file.getAbsolutePath().contains("test/spam")) {
            testSpamProb.put(file.getName(),fileIsSpam);
            EmailList.setEmail(file.getName(), fileIsSpam, "Spam");
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

        optimize = new Optimize();
        optimize.optFreqMap(trainSpamFreq,5);

        // calculate and save the probability that a word in the trainHamFreq map appears in a ham file into
        // the probabilitywordInHam map
        Set<String> keys = trainHamFreq.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = trainHamFreq.get(key);
            probabilitywordInHam.put(key,(double)count/numHam);
        }

        // calculate and save the probability that a word in the trainHamSpam map appears in a spam file into
        // the probabilitywordInSpam map
        keys = trainSpamFreq.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            int count = trainSpamFreq.get(key);
            probabilitywordInSpam.put(key,(double)count/numSpam);
        }

        // calculate and save the probability that a file is spam given that it contains a word in a spam file
        keys = probabilitywordInSpam.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if(probabilitywordInHam.containsKey(key))
                probabilityFileisSpam.put(key,probabilitywordInSpam.get(key)/(probabilitywordInSpam.get(key) + probabilitywordInHam.get(key)));
            else
                probabilityFileisSpam.put(key,1.0);
        }
    }


    public double calculateSpamProbability(String word) {
        double trainSpamProb = probabilityFileisSpam.get(word);
        return (Math.log(1-trainSpamProb) - Math.log(trainSpamProb));
    }

    /**
     * Calculate the accuracy
     * @return the accuracy
     */
    public double calculateAccuracy(){

        // go through maps containing test ham for the files and calculate the number of correct guesses
        int correct = 0;
        Set<String> keys = testHamProb.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double count = testHamProb.get(key);
            if(count < 0.5){
                correct++;
            }
        }

        // go through maps containing test spam for the files and calculate the number of correct guesses
        keys = testSpamProb.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double count = testSpamProb.get(key);
            if(count >= 0.5){
                correct++;
            }
        }

        double accuracy = (double)correct/(testHamProb.size()+testSpamProb.size());
        return accuracy;
    }


    /**
     * calculate the precision
     * @return the precision
     */
    public double calculatePrecision(){
        double precision;
        int truePos = 0;
        int falsePos = 0;

        Set<String> keys = testHamProb.keySet();
        Iterator<String> keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double count = testHamProb.get(key);
            if(count >= 0.5){
                falsePos++;
            }
        }

        keys = testSpamProb.keySet();
        keyIterator = keys.iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            double count = testSpamProb.get(key);
            if(count >= 0.5){
                truePos++;
            }
        }
        precision = (double)truePos / (falsePos+truePos);
        return precision;
    }
}
