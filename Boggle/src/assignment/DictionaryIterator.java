/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.Iterator;

/**
 *
 * @author Angelo
 */
public class DictionaryIterator implements Iterator<String>{
    private DictionaryNode current;
    private DictionaryMap map;
    private int currentTree;
    DictionaryIterator(DictionaryMap m){
        map = m;
        currentTree = 0;
        if(map.getDictionary().isEmpty()){
            System.err.print("ERROR: EMPTY DICTIONARY");
            return;
        }
        current = map.getDictionary().get(currentTree);
        while(!current.getPointers().isEmpty()){
            if(current.isEndOfWord())
                break;
            current = current.getPointers().get(0);
        }
        
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String next()
    {
        //return word here
        String nextWord = turnToWord();
        current.setToVisited();
        int z = 0;
        
        x: while(!current.isEndOfWord()||current.hasBeenVisited())
        {
            System.out.print("@"+current.getLetter());
            System.out.print(current.isEndOfWord());
            System.out.print(current.hasBeenVisited());
            int size = current.getPointers().size();
            
            if(size == 0){
                System.out.println("*");
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
                    System.out.println("#");
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
     System.out.println("$$$ " + s);
     return s;
    }
    public void setNewTree()
    {
        current = map.getDictionary().get(currentTree);
        while(!current.getPointers().isEmpty()){
            if(current.isEndOfWord())
                break;
            current = current.getPointers().get(0);
        }
    }
}
