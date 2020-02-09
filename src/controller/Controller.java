package controller;

import java.util.Scanner;

import model.data_structures.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	/**
	 * Interaccion del usuario con el programa.
	 */
	public void run()
	{
		Scanner lector = new Scanner(System.in);
		String rta = "";
		view.printMenu();

		int option = lector.nextInt();

		while(option != 6)
		{
			if(option==1)
			{
				modelo.loadJSON();
				if(modelo.darTamano()!=1)
					view.printMessage("La lista tiene " + modelo.darTamano() + " comparendos.");
				else
					view.printMessage("La lista tiene " + modelo.darTamano() + " comparendo.");
				view.printMessage("");
				view.printMenu();
			}
			else if(option==2)
			{
				if(modelo.darTamano()!=1)
					view.printMessage("La lista tiene: " + modelo.darTamano() + " comparendos.");
				else
					view.printMessage("La lista tiene " + modelo.darTamano() + " comparendo.");
			}
			else if(option==3)
			{
				if( modelo.primero().getItem()!= null)
					view.printMessage("El comparendo encontrado fue: "+ modelo.primero().getItem().toString());
				else
					view.printMessage("No se encontro el comparendo.");

				view.printMessage("");
				view.printMenu();
			}
			else if(option==4)
			{
				Comparendo borrado = modelo.eliminar();
				if(borrado != null)
					view.printMessage(borrado.toString());
				else
					view.printMessage("No se pudo eliminar el comparendo.");

				view.printMessage("La lista tiene " + modelo.darTamano() + " comparendos.");
				view.printMessage("");
				view.printMenu();
			}
			else if(option==5)
			{
				view.printModelo(modelo);
				view.printMenu();
			}
			else
			{
				view.printMessage("Numero invalido");
				view.printMessage("");
				view.printMenu();
			}
			option = lector.nextInt();
		}
		if(option==6)
		{
			System.out.println("Ha salido con exito.");
		}
	}	
}
