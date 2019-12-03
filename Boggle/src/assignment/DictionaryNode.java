/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;

/**
 *
 * @author Angelo
 */
public class DictionaryNode {
    private char letter;
    private boolean endOfWord;
    private ArrayList<DictionaryNode> pointers;
    private DictionaryNode parentNode;
    private boolean visited;
    
    public DictionaryNode()
    {
        letter = ' ';
        endOfWord = false;
        visited = false;
        pointers = new ArrayList();
        parentNode = null;
        
    }
    
    public DictionaryNode(char inputLetter)
    {
        letter = inputLetter;
        endOfWord = false;
        visited = false;
        pointers = new ArrayList();
    }
    
    public boolean isEndOfWord()
    {
        return endOfWord;
    }
    public void setEndOfWord()
    {
        endOfWord = true;
    }
    
    public char getLetter()
    {
        return letter;
    }
    
    public ArrayList<DictionaryNode> getPointers()
    {
        return pointers;
    }
    
    public void addNewPointer(DictionaryNode child)
    {
        pointers.add(child);
        child.setParent(this);
    }
    
    public void setParent(DictionaryNode par)
    {
        parentNode = par;
    }
    
    public DictionaryNode getParent()
    {
        return parentNode;
    }
    
    public boolean hasBeenVisited()
    {
        return visited;
    }
    
    public void setToVisited()
    {
        visited = true;
    }
}
