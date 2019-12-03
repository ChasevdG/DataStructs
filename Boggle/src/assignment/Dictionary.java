/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author Angelo
 */
public class Dictionary implements BoggleDictionary
{
    private DictionaryMap radixTree;
    public void loadDictionary(String filename)
    {   
        try
        {
            radixTree = new DictionaryMap();
            
            BufferedReader reader = 
                  new BufferedReader(new FileReader(filename));
            String newWord;
            while(!(newWord = reader.readLine()).equals(""))
            {
                radixTree.addWord(newWord);
            }

        }catch(IOException e)
        {
        
        }
    }
    
    public boolean isPrefix(String prefix)
    {
        return radixTree.isPrefix(prefix);
    }
    
    public boolean contains(String word)
    {
        
        return false;
    }

    @Override
    public Iterator<String> iterator() {
       
/*        if ( == null)
        return;
    else {
        System.out.print(root);
        preorder(root.getLeftPtr());
        preorder(root.getRightPtr());
    }
        */
        return null;
    }
    
    
}
