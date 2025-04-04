/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TQS.lab8.ex1.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import TQS.lab8.ex1.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;
    private BoundedSetOfNaturals setE;
    private BoundedSetOfNaturals setF;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{40, 50});
        setD = BoundedSetOfNaturals.fromArray(new int[]{15, 45});
        setE = BoundedSetOfNaturals.fromArray(new int[]{25, 40});
        setF = BoundedSetOfNaturals.fromArray(new int[]{40, 50});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = setC = null;
    }

    @DisplayName("test adding elements to sets")
    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(11), "add: adding to full does not throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> setC.add(50), "add: adding duplicate does not throw IllegalArgumentException");
        assertThrows(IllegalArgumentException.class, () -> setA.add(-1), "add: adding negative number does not throw IllegalArgumentException");
    }

    @DisplayName("test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @DisplayName("tests if intersects is working")
    @Test
    public void testIntersects() {
        assertTrue(setB.intersects(setC));
        assertFalse(setB.intersects(setD));
        assertFalse(setB.intersects(setE));
    }

    @DisplayName("tests if contains is working")
    @Test
    public void testContains() {
        assertTrue(setB.contains(30));
        assertFalse(setC.contains(20));
    }

    @DisplayName("tests if hashCode is working")
    @Test
    public void testHashCode() {
        assertEquals(setC.hashCode(), setF.hashCode());
        assertNotEquals(setB.hashCode(), setD.hashCode());
    }

    @DisplayName("tests if equals is working")
    @Test
    public void testEquals() {
        assertTrue(setB.equals(setB));
        assertFalse(setC.equals(null));
        assertFalse(setD.equals("string"));
    }
}
