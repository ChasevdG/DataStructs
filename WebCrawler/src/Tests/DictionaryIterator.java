/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment.Tests;

import java.util.Iterator;

/**
 *
 * @author Angelo
 */
/*
 * Traverses the way by starting in the far right(left/however you think about 
 * it)(At the first Index) of the tree or the end of the word on the right hand
 * edge
 * The program checks the next nodes to see if they have been visited and if 
 * all of them have been visited the program will be set to visited and go to
 * its parent node.
 * 
 * Iterates through the dictionary in the dictionaries order
 * 
 * reset() sets nodes to not visited
 */
public class DictionaryIterator implements Iterator<String>{
    private DictionaryNode current, lastNode;
    private DictionaryMap map;
    private int currentTree;
    
    //RESET NODES TO NOTBEENVISITED
    DictionaryIterator(DictionaryMap m){
        map = m;
        
        currentTree = 0;
        if(map.getDictionary().isEmpty()){
            System.err.print("ERROR: EMPTY DICTIONARY");
            return;
        }
        current = map.getDictionary().get(currentTree);
        int z = 0;
        while(!current.getPointers().isEmpty()&&z<20){
            z++;
            if(current.isEndOfWord())
                break;
            current = current.getPointers().get(0);
        }
        
        lastNode = map.getDictionary().get(map.getDictionary().size()-1);
        while(!lastNode.getPointers().isEmpty())
        {
            if(lastNode.isEndOfWord())
                break;
            lastNode = lastNode.getPointers().get(lastNode.getPointers().size()-1);
        }
        
        for(DictionaryNode node : map.getDictionary())
        {
            reset(node);
        }
       currentTree = 0;
    }

    @Override
    public boolean hasNext()
    {
        return !(current == null);
    }

    @Override
    public String next()
    {
        //return word here
        String nextWord = turnToWord();
        current.setToVisited();
        int z = 0;
        
        x: while((!current.isEndOfWord()||(current.hasBeenVisited())))
        {
            z++;
            //System.out.print("@"+current.getLetter());
            //System.out.print(current.isEndOfWord());
            //System.out.print(current.hasBeenVisited());
            int size = current.getPointers().size();
            
            if(size == 0){
                //System.out.println("*");
                    current.setToVisited();
                    current = current.getParent();
                    if(current == null)
                    {
                        currentTree++;
                        if(currentTree >= map.getDictionary().size()){
                        	current = null;
                            return nextWord;
                        }
                        setNewTree();
                    }
                    continue x;
            }
            
            y: for(int i = 0; i < size; i++)
            {
                if(!current.getPointers().get(i).hasBeenVisited())
                {
                    current = current.getPointers().get(i);
                    break y;
                }
                if(i == size - 1)
                {
                    //System.out.println("#");
                    current.setToVisited();
                    current = current.getParent();
                    if(current == null)
                    {
                        currentTree++;
                        if(currentTree >= map.getDictionary().size()){
                        	current = null;
                            return nextWord;
                        }
                        setNewTree();
                    }
                    continue x;
                }
            }
        }
        return nextWord;
    }
    
    public char getCurrentNode(){
        return current.getLetter();
    }
    public String turnToWord()
    {
     String s = "";
     DictionaryNode temp = current;
     s = temp.getLetter() + s;
     while(temp.getParent() != null){
         temp = temp.getParent();
         s = temp.getLetter() + s;
     }
     //System.out.println("$$$ " + s);
     
     return s;
    }
    public void setNewTree()
    {
        current = map.getDictionary().get(currentTree);
        int z = 0;
        while(!current.getPointers().isEmpty() && z<20){
            z++;
            if(current.isEndOfWord())
                break;
            current = current.getPointers().get(0);
        }
    }
    public void reset(DictionaryNode node)
    {
        node.setToNotVisited();
        for(DictionaryNode child : node.getPointers())
        {
            reset(child);
        }
    }
    
}
