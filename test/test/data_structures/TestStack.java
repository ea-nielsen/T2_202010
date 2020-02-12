package test.data_structures;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.Comparendo;
import model.data_structures.Stack;
import model.logic.Modelo;

public class TestStack {

	private Stack<Comparendo> stack;

	private Modelo modelo;

	private final static int NUM_ELEMENTOS = 20;

	@Before
	public void setUp1() {
		modelo = new Modelo();
	}

	public void setUp2() {
		modelo.loadJSON();
		stack = modelo.pila;
	}

	@Test
	public void testPush() {
		setUp1();
		setUp2();
		assertTrue("El primer elemento de la lista deberia ser: ID-29042", 29042==stack.topStack.getItem().OBJECTID);
	}

	@Test
	public void testPop() {
		setUp1();
		setUp2();
		int i = 0;
		while( i < NUM_ELEMENTOS) {
			stack.pop();
			i++;
		}
		assertTrue("El tama�o de la lista deberia ser " + 0, stack.getLength()==0);
	}

	@Test
	public void testGetLength() {
		setUp2();
		assertTrue("El tama�o de la lista deberia ser " + NUM_ELEMENTOS, stack.getLength()==NUM_ELEMENTOS);
	}

	@Test
	public void testIsEmpty() {
		setUp1();
		setUp2();
		assertTrue("La lista no deberia estar vacia", !stack.isEmpty());
	}

	@Test
	public void testGetHead() {
		setUp2();
		assertTrue("El primer elemento de la lista deberia ser: ID-29042", 29042==stack.topStack.getItem().OBJECTID);
	}
}

