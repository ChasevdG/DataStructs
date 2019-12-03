/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Angelo
 */
public class DictionaryIteratorTest {
    
    public DictionaryIteratorTest() {
    }

    /**
     * Test of hasNext method, of class DictionaryIterator.
     */
    @Test
    public void testHasNext() {
    }

    /**
     * Test of next method, of class DictionaryIterator.
     */
    @Test
    public void testNext() {
        DictionaryMap dict = new DictionaryMap();
        dict.addWord("apc");
        dict.addWord("apdd");
        dict.addWord("apde");
        dict.addWord("apdf");
        dict.addWord("bpc");
        dict.addWord("bpdd");
        dict.addWord("bpde");
        dict.addWord("bpdf");
        DictionaryIterator it = new DictionaryIterator(dict);
        assertEquals("apc",it.next());
        assertEquals("apdd",it.next());
        assertEquals("apde",it.next());
        assertEquals("apdf",it.next());
        assertEquals("bpc",it.next());
        assertEquals("bpdd",it.next());
        assertEquals("bpde",it.next());
        assertEquals("bpdf",it.next());
    }

    /**
     * Test of turnToWord method, of class DictionaryIterator.
     */
    @Test
    public void testTurnToWord() {
        DictionaryMap dict = new DictionaryMap();
        dict.addWord("add");
        DictionaryIterator it = new DictionaryIterator(dict);
        assertEquals("add",it.turnToWord());
        assertFalse("ad".equals(it.turnToWord()));
        assertFalse("balloon".equals(it.turnToWord()));
        
        //test extra words
    }

    /**
     * Test of setNewTree method, of class DictionaryIterator.
     */
    @Test
    public void testSetNewTree() {
    }
    
}
