package de.comparus.opensource.longmap;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Tests for LongMapImpl.
 */
public class LongMapImplTest {
    @Test
    public void put() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        String expectedValue = "Hello";
        longMap.put(42, expectedValue);
        String previousValue = longMap.put(42, "World");
        assertEquals("Returns replaced value", expectedValue, previousValue);
    }

    @Test
    public void get() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        String expectedValue = "Hello";
        longMap.put(42, expectedValue);
        assertEquals("Contains expected value", expectedValue, longMap.get(42));
        assertNull("Returns null on missing key", longMap.get(1));
    }

    @Test
    public void remove() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        String expectedValue = "Hello";
        longMap.put(42, expectedValue);
        String removedValue = longMap.remove(42);
        assertEquals("Returns removed value", expectedValue, removedValue);
        assertNull("Does not contain a removed value", longMap.get(42));
    }

    @Test
    public void isEmpty() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        assertTrue("Newly created map is empty", longMap.isEmpty());
        longMap.put(42, "Hello");
        assertFalse("Map with elements is not empty", longMap.isEmpty());
        longMap.remove(42);
        assertTrue("Cleared map is empty", longMap.isEmpty());
    }

    @Test
    public void containsKey() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        long expectedKey = 42;
        longMap.put(expectedKey, "Hello");
        assertTrue("Contains an expected key", longMap.containsKey(expectedKey));
    }

    @Test
    public void containsValue() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        String expectedValue = "Hello";
        longMap.put(42, expectedValue);
        assertTrue("Contains an expected value", longMap.containsValue(expectedValue));
    }

    @Test
    public void keys() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        long[] expectedKeys = { 1, 5, 7 };
        longMap.put(expectedKeys[0], "Foo");
        longMap.put(expectedKeys[1], "Bar");
        longMap.put(expectedKeys[2], "Baz");
        assertArrayEquals(expectedKeys, longMap.keys());
    }

    @Test
    public void values() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        String[] expectedValues = { "Foo", "Baz", "Baz" };
        longMap.put(1, expectedValues[0]);
        longMap.put(5, expectedValues[1]);
        longMap.put(7, expectedValues[2]);
        assertArrayEquals(expectedValues, longMap.values());
    }

    @Test
    public void size() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1, "Foo");
        assertEquals(1, longMap.size());
        longMap.put(1, "Bar");
        assertEquals(1, longMap.size());
        longMap.put(5, "Baz");
        assertEquals(2, longMap.size());
        longMap.remove(1);
        assertEquals(1, longMap.size());
    }

    @Test
    public void clear() throws Exception {
        LongMap<String> longMap = new LongMapImpl<>();
        longMap.put(1, "Foo");
        longMap.put(2, "Bar");
        longMap.put(3, "Baz");
        longMap.clear();
        assertEquals(0, longMap.size());
    }

}