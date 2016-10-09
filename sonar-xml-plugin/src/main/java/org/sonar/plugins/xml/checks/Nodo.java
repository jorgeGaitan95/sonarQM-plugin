/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * sonarqube@googlegroups.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.xml.checks;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class Nodo {
	
	ArrayList<Integer> hijosPos = new ArrayList<Integer>();
	
	private Node nodoReferencia;
	private int posicion = -1;
	private String nombre = "";
	private int anteriorNodo = -1;
	private int siguienteNodo = -1;
	private String tipo = "";
	private int padre = -1;
	private String amteriorRelation = "";
	private String siguienteRelation = "";
	
	


	public Nodo(int posicion, String nombre, Node nodoReferencia)
	{
		this.nombre=nombre;
		this.posicion=posicion;
		this.nodoReferencia=nodoReferencia;

	}
	
	public Nodo(String nombre, String tipo, int pos, Node nodoReferencia)
	{
		this.nombre=nombre;
		this.posicion = pos;
		this.nodoReferencia=nodoReferencia;
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Node getNodoReferencia() {
		return nodoReferencia;
	}
	
	public int getAnteriorNodo() {
		return anteriorNodo;
	}

	public void setAnteriorNodo(int anteriorNodo) {
		this.anteriorNodo = anteriorNodo;
	}

	public int getSiguienteNodo() {
		return siguienteNodo;
	}

	public void setSiguienteNodo(int siguienteNodo) {
			this.siguienteNodo = siguienteNodo;
	}

	public String getAmteriorRelation() {
		return amteriorRelation;
	}

	public void setAmteriorRelation(String amteriorRelation) {
		this.amteriorRelation = amteriorRelation;
	}

	public String getSiguienteRelation() {
		return siguienteRelation;
	}

	public void setSiguienteRelation(String siguienteRelation) {
		this.siguienteRelation = siguienteRelation;
	}

	public int getPosicion() {
		return posicion;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<Integer> getHijosPos() {
		return hijosPos;
	}

	public void setHijosPos(ArrayList<Integer> hijosPos) {
		this.hijosPos = hijosPos;
	}


	public int getPadre() {
		return padre;
	}

	public void setPadre(int padre) {
		this.padre = padre;
	}
	
	public boolean verificarHijo (int hijo){
		int control = -1;
		for (int i = 0; i < hijosPos.size(); i++ ){
			if( hijosPos.get(i) == hijo){
				control = 0;
			}
		}
		
		if (control == -1){
			return true;
		}
		else{
			return false;
		}
	}


}
