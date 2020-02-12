package model.logic;

import model.data_structures.Comparendo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import model.data_structures.Node;
import model.data_structures.Queue;
import model.data_structures.Stack;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	public Queue cola;

	/**
	 * Atributos del modelo del mundo
	 */
	public Stack pila;


	/**
	 * Gson utilizado para deserializar el archivo
	 */
	private Gson gson;

	/**
	 * Direccion del archivo de datos.
	 */
	public final static String SMALL = "./data/comparendos_dei_2018_small.geojson";

	public final static String BIG = "./data/comparendos_dei_2018_small.geojson";
	/**
	 * Constructor del modelo del mundo
	 */
	public Modelo()
	{
		cola = new Queue();
		gson = new Gson();
		pila = new Stack();
	}


	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamanoCola() {
		return cola.size();
	}

	public int darTamanoPila() {
		return pila.getLength();
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Comparendo x)
	{	
		cola.enqueue(x);
		pila.push(x);
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 * @throws Exception 
	 */

	public Node primeroQueue() {
		return cola.head();
	}

	public Node primeroStack() {
		return pila.getHead();
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar_
	 * @return dato eliminado
	 * @throws Exception 
	 */
	public Comparendo eliminarQueue() {
		return (Comparendo) cola.dequeue();	
	}

	public Comparendo eliminarStack() {
		return (Comparendo) pila.pop();
	}

	/**
	 * Carga del geojson a objetos de la lista.
	 */
	public void loadJSON() {
		ArrayList<Comparendo> objects = new ArrayList<>();
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(SMALL));
			JsonElement element = JsonParser.parseReader(reader);
			JsonArray featuresArray = element.getAsJsonObject().get("features").getAsJsonArray();

			SimpleDateFormat dateParser=new SimpleDateFormat("yyyy/MM/dd");

			for(JsonElement e: featuresArray) {
				Comparendo c = new Comparendo();
				c.OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	
				c.FECHA_HORA = dateParser.parse(s); 
				c.MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETE").getAsString();
				c.CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
				c.TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVI").getAsString();
				c.INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				c.DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRAC").getAsString();	
				c.LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				c.longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();
				c.latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				agregar(c);
			}
		} 
		catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
	}

	public Queue<Comparendo> cluster()
	{
		Queue<Comparendo> rta = new Queue<Comparendo>();
		Queue<Comparendo> rta2 = new Queue<Comparendo>();
		Comparendo head = (Comparendo) cola.dequeue();
		String infraccion = "";
		while(head!=null) {
			if(head.INFRACCION.equals(infraccion))
				rta2.enqueue(head);
			else {
				infraccion = head.INFRACCION;
				rta2.restart();
				rta2.enqueue(head);
			}
			if(rta.size()<rta2.size()) {
				rta.restart();
				int limit = rta2.size();
				for(int i = 0; i < limit; i++) {
					rta.enqueue(rta2.dequeue());
				}
			}
			head = (Comparendo) cola.dequeue();
		}
		return rta;
	}

	public void imprimirCluster() {
		Queue<Comparendo> cluster = cluster();
		Node<Comparendo> actual = cluster.head();
		System.out.println("El numero de comparendos es: " + cluster.size());
		while(actual != null) {
			System.out.println(actual.getItem().toString());
			actual = actual.getNext();
		}
	}
	/**
	 * Imprimir los elemntos de la lista por medio de sus metodos toString().
	 */
	public void imprimirLista() {
		Node actual = primeroQueue();
		for(int i = 0; i < darTamanoCola(); i++) {
			System.out.println(actual.getItem().toString());
			actual = actual.getNext();
		}
	}

	public Stack<Comparendo> comparendosInfraccion(int N, String pInfraccion) {
		Stack<Comparendo> infracciones = new Stack<Comparendo>();
		return infracciones;
	}

	public void imprimirInfraccion(int n, String pInfraccion) {
			Stack<Comparendo> nueva = darElementos(n, pInfraccion);
			Node<Comparendo> actual = nueva.getHead();
			if(nueva.getLength()==0)
				System.out.println();
			System.out.println("El numero de comparendos es: " + nueva.getLength());
			while(actual != null) {
				System.out.println(actual.getItem().toString());
				actual = actual.getNext();
			}
	}

	public Stack<Comparendo> darElementos(int n , String pIfraccion) {
			Stack<Comparendo> nueva = new Stack();
			Stack<Comparendo> borrados = new Stack();
			while(pila.getHead() != null && n > 0) {
				Comparendo pop = (Comparendo) pila.pop();
				if(pop.INFRACCION.equals(pIfraccion)) {
					nueva.push(pop);
					n--;
				}
				else
					borrados.push(pop);	
			}
			while(borrados.getLength()!=0)
				pila.push(borrados.pop());	

			return nueva;
	}

}
