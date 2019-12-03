/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.Tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class GameDictionary implements BoggleDictionary
{
    private DictionaryMap radixTree;
    public GameDictionary(String fileName){
    	radixTree = new DictionaryMap();
    	loadDictionary(fileName);
    }
    public void loadDictionary(String filename)
    {   
        try
        {
            BufferedReader reader = 
                  new BufferedReader(new FileReader(filename));
            String newWord = reader.readLine();
            newWord = newWord.toLowerCase();
            while(!(newWord == null) && !newWord.equals(""))
            {
                radixTree.addWord(newWord);
                newWord = reader.readLine();
            }

        }catch(IOException e)
        {
        System.err.println("File not Found");
        }
    }
    
    public boolean isPrefix(String prefix)
    {
        return radixTree.isPrefix(prefix);
    }
    
    public boolean contains(String word)
    {
        return radixTree.containsWord(word);
    }
   
    @Override
    public Iterator<String> iterator() 
    {
        return new DictionaryIterator(radixTree);
    }
    
    public void deleteSubtree(String word){
    	radixTree.trimTree(word);
    }
}
