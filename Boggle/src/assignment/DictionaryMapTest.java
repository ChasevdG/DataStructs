/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Angelo
 */
public class DictionaryMapTest {
    
    public DictionaryMapTest() 
    {
    }
    

    /**
     * Test of addWord method, of class DictionaryMap.
     */
    @Test
    public void testAddWord() 
    {
        DictionaryMap dict = new DictionaryMap();
        //check if word adds normally
        dict.addWord("add");
        ArrayList<DictionaryNode> nodes = dict.getDictionary();
        assertEquals(nodes.get(0).getLetter(), 'a');
        assertEquals(nodes.get(0).getPointers().get(0).getLetter(), 'd');
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getLetter(), 'd');
        assertTrue(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).isEndOfWord());
        //no extra leaves
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers(), new ArrayList());
        
        //check if no extra nodes created  when adding word that starts same
        dict.addWord("added");
        assertEquals(nodes.get(0).getLetter(), 'a');
        assertEquals(nodes.size(), 1);
        assertEquals(nodes.get(0).getPointers().get(0).getLetter(), 'd');
        assertEquals(nodes.get(0).getPointers().size(), 1);
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getLetter(), 'd');
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().size(), 1);
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().get(0).getLetter(),'e');
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().size(), 1);
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().get(0).
                     getPointers().get(0).getLetter(),'d');
        assertTrue(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().get(0).
                     getPointers().get(0).isEndOfWord());
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().get(0).
                     getPointers().size(), 1);
        assertEquals(nodes.get(0).getPointers().get(0).
                     getPointers().get(0).getPointers().get(0).
                     getPointers().get(0).getPointers(), new ArrayList());
        
        //check if word that starts differently starts properly
        dict.addWord("bat");
        assertEquals(nodes.get(1).getLetter(), 'b');
        assertEquals(nodes.size(), 2);
        assertEquals(nodes.get(1).getPointers().get(0).getLetter(), 'a');
        assertEquals(nodes.get(1).getPointers().size(), 1);
        assertEquals(nodes.get(1).getPointers().get(0).
                     getPointers().get(0).getLetter(), 't');
        assertTrue(nodes.get(1).getPointers().get(0).
                     getPointers().get(0).isEndOfWord());
        assertEquals(nodes.get(1).getPointers().get(0).
                     getPointers().size(), 1);
        assertEquals(nodes.get(1).getPointers().get(0).
                     getPointers().get(0).getPointers(), new ArrayList());
        
        //check if one letter word adds properly
        dict.addWord("n");
        assertEquals(nodes.get(2).getLetter(), 'n');
        assertTrue(nodes.get(2).isEndOfWord());
    }

    /**
     * Test of CheckNodes method, of class DictionaryMap.
     */
    @Test
    public void testCheckNodes() 
    {
        
    }
    
    @Test
    public void testContainsWord() 
    {
        DictionaryMap dict = new DictionaryMap();
        //check if can find word that was added and not other word
        dict.addWord("add");
        assertTrue(dict.containsWord("add"));
        assertFalse(dict.containsWord("app"));
        assertFalse(dict.containsWord("a"));
        assertFalse(dict.containsWord("ad"));
        assertFalse(dict.containsWord("added"));
        
        dict.addWord("added");
        assertTrue(dict.containsWord("added"));
        assertTrue(dict.containsWord("add"));
        assertFalse(dict.containsWord("a"));
        assertFalse(dict.containsWord("addedd"));
        //assertFalse(dict.containsWord("subtract"));
        
        dict.addWord("bat");
        assertTrue(dict.containsWord("bat"));
        assertTrue(dict.containsWord("added"));
        assertTrue(dict.containsWord("add"));
        assertFalse(dict.containsWord("a"));
        assertFalse(dict.containsWord("addedd"));
        assertFalse(dict.containsWord("ba"));
        assertFalse(dict.containsWord("batted"));
        
        dict.addWord("c");
        assertTrue(dict.containsWord("c"));
        assertTrue(dict.containsWord("bat"));
        assertTrue(dict.containsWord("added"));
        assertTrue(dict.containsWord("add"));
        assertFalse(dict.containsWord("ca"));
        assertFalse(dict.containsWord("a"));
    }
    
    @Test
    public void testIsPrefix()
    {
        DictionaryMap dict = new DictionaryMap();
        assertFalse(dict.isPrefix(""));
        
        dict.addWord("stripes");
        assertTrue(dict.isPrefix(""));
        assertTrue(dict.isPrefix("strip"));
        assertTrue(dict.isPrefix("stripe"));
        assertFalse(dict.isPrefix("stripes"));
        assertFalse(dict.isPrefix("a"));
        assertFalse(dict.isPrefix("sa"));
        assertFalse(dict.isPrefix("stripesss"));
        
        dict.addWord("u");
        assertFalse(dict.isPrefix("u"));
        assertFalse(dict.isPrefix("q"));
        assertFalse(dict.isPrefix("uuu"));
    }
}
