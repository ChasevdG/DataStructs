/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.Tests;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Angelo
 */
public class DictionaryMap 
{
    ArrayList<DictionaryNode> node;
    
    DictionaryMap()
    {
        node = new ArrayList<DictionaryNode>();
    }
    
    public void addWord(String word)
    {
       DictionaryNode nextLetter = CheckNodes(word.charAt(0), node);
       if(nextLetter == null)
       {
          node.add(new DictionaryNode(word.charAt(0)));
          nextLetter = CheckNodes(word.charAt(0), node);
       }
       //Accounting for single character words
       if(word.length() == 1){
          nextLetter.setEndOfWord();
          return;
       }
       DictionaryNode origLetter;
       
       for(int i = 1; i < word.length(); i++)
       {
           //nextLetter = CheckNodes(word.charAt(i), nextLetter.getPointers());
           origLetter = nextLetter;
           nextLetter = CheckNodes(word.charAt(i), nextLetter.getPointers());
           if(nextLetter == null)
           {
               origLetter.addNewPointer(new DictionaryNode(word.charAt(i)));
               nextLetter =  CheckNodes(word.charAt(i), origLetter.getPointers());
           }
       }
       //Need to account for last letter
       nextLetter.setEndOfWord();
    }
    
    public DictionaryNode CheckNodes(char letter, ArrayList<DictionaryNode> nodes)
    {
        for(DictionaryNode n: nodes)
        {
            if(n.getLetter() == letter)
            {
                return n;
            }
        }
        return null;
    }
    
    public ArrayList<DictionaryNode> getDictionary()
    {
        return node;
    }
    
    public boolean containsWord(String word)
    {
        if(word.equals(""))
        {
            return false;
        }
        int index = binarySearch(node, word.charAt(0));
        //System.out.print(index);
        if(index < 0)
        {
            //System.out.println(node.get(0).getLetter()+" _ "+node.get(0).isEndOfWord()+" _ EXIT");
            return false;
        }
        
        DictionaryNode currentNode = node.get(index);
        //System.out.println(node.get(0).getLetter()+" _ "+node.get(0).isEndOfWord());
        if(word.length() == 1)
        {
        	//System.out.println("***" + currentNode.getLetter());
            return currentNode.isEndOfWord();
        }
        
        DictionaryNode nextNode;
        //index = binarySearch(node.get(index).getPointers(), word.charAt(1));
        
        for(int i = 1; i < word.length(); i++)
        {
            index = binarySearch(currentNode.getPointers(), word.charAt(i));
            //System.out.println(currentNode.getLetter());
            if(index < 0)
            {
                //System.out.println(currentNode.getLetter()+" _ "+currentNode.isEndOfWord()+" _ EXIT");
            	//System.out.println("***" + currentNode.getLetter());
            	return false;
            }
            currentNode = currentNode.getPointers().get(index);
            //index = binarySearch(currentNode);
            //System.out.println(currentNode.getLetter()+" _ "+currentNode.isEndOfWord());
        }
        //System.out.println(currentNode.getLetter()+" _ "+currentNode.isEndOfWord());
        return currentNode.isEndOfWord();
    }
    
    public boolean isPrefix(String word)
    {
        if(word.length() == 0)
        {
            return node.size() > 0;
        }
        int index = binarySearch(node, word.charAt(0));
        //System.out.print(index);
        if(index < 0)
        {
            return false;
        }
        
        DictionaryNode currentNode = node.get(index);
        if(word.length() == 1)
        {
            return !currentNode.getPointers().isEmpty();
        }
        
        
        for(int i = 1; i < word.length(); i++)
        {
            index = binarySearch(currentNode.getPointers(), word.charAt(i));
            if(index < 0)
            {
                return false;
            }
            currentNode = currentNode.getPointers().get(index);
        }
        return !currentNode.getPointers().isEmpty();
    }
    
    //fix to make it actual binary search
    public int binarySearch(ArrayList<DictionaryNode> list, char letter)
    {
        ArrayList<Character> charList = new ArrayList();
        for(int i = 0; i < list.size(); i++)
        {
            charList.add(list.get(i).getLetter());
        }
        return Collections.binarySearch(charList, letter);
    }
    
    public void trimTree(String word)
    {
    	if(word.length() == 0)
        {
            return;
        }
        int index = binarySearch(node, word.charAt(0));
        //System.out.print(index);
        if(index < 0)
        {
            return;
        }
        
        DictionaryNode currentNode = node.get(index);
        if(word.length() == 1)
        {
            node.remove(currentNode);
        }
        for(int i = 1; i < word.length(); i++)
        {
            index = binarySearch(currentNode.getPointers(), word.charAt(i));
            if(index < 0)
            {
                return;
            }
            currentNode = currentNode.getPointers().get(index);
        }
        currentNode.removeThisNode();
    }

}
