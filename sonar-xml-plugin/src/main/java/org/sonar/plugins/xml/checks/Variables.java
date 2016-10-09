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

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Variables {
	public  String CTT_AGREGATION;
	public  String CTT_ENABLING;
	public  String CTT_DISABLING;
	public  String CTT_INDEPENDENTCONCURRENCY;
	//Variable que representa el nodo del diagrama ctt--
	public  String NODE_DIAGRAM_CTT;
	public String NODE_TYPE_DIAGRAM_CTT;
	public  String NODE_LIST_RELATION_TASK;
	public  String NODE_LIST_TASK_CTT;
	//name
	public  String ATTRIBUTE_NAME;
	//source
	public  String ATTRIBUTE_SOURCE;
	//xsi:type
	public  String ATTRIBUTE_XSI_TYPE;
	//target
	public  String ATTRIBUTE_TARGET;
	
	public  String CTT_ABSTRACTION_TASK;
	public  String CTT_SYSTEM_TASK;
	public  String CTT_USER_TASK;
	public  String CTT_INTERACTION_TASK;
	
	
	private static Variables instance;
	//nombre etiqueta diagrama clase
	public String CLASS_DIAGRAM_NAME="itsPackage";
	//valor xsi:type del diagrama de clase
	public String TYPE_CLASS="herramienta.diagrams.domain:Domain_Diagram";
	//etiqueta clase
	public String NODE_CLASS="itsClases";
	//ETIQUETA RELACION
	public String NODE_RELATIONSHIP_CLASS="itsArc";
	//etiqueta atributo del diagrama de clases
	public String NODE_ATTRIBUTE_CLASS="listaAtributos";
	//etiqueta metodos del diagrama de clases
	public String NODE_OPERATION_CLASS="listMetodos";
	//etiqueta de los atributos de un método
	public String NODE_ATTRIBUTE_OPERATION="listParametros";
	//atributo del tipo de atributo del diagrama de clase
	public String ATTRIBUTE_TYPE_ATTRIBUTENODE="tipo";
	//ATRIBUTO DEL CONTENIDO DE LA OPRACION DEL DIAGRAMA DE CLASES
	public String ATTRIBUTE_BODY_CLASS="body";
	public String ATTRIBUTE_RETURN_OPERATION_CLASS="returnType";
	//valor xsi:type de la relacion de herencia
	public String CLASS_INHERITED_RELATION="herramienta.diagrams.domain:Herencia";
	
	public String[] CLASS_RESERVED_WORDS={"void","int","if","public","void","static","for","class"};
	
	//CASE USE DIAGRAM
	public int NUMBER_USE_CASE=9;
	public int NUMBER_ACTORS=5;
	public int NUMBER_COMMUNICATIONS=9;
	//nombre etiqueta diagrama de casos de uso
	public String USE_CASE_DIAGRAM_NAME="usecase:Diagram";
	//ETIQUETA RELACION
	public String NODE_ACTOR="listActors";
	//etiqueta atributo del diagrama de clases
	public String NODE_USE_CASE="listUseCase";
	public String NODE_RELATION_USECASE="listRelation";
	public String RELATION_INCLUDE="usecase:Include";
	public String RELATION_EXCLUDE="usecase:Exclude";
	public String RELATION_HIEARCHY="usecase:Hiearchy";
	public String RELATIONS_ASSOCIATIONS="usecase:Associations";
	
	//E-R DIAGRAM
	public String ATTRIBUTE_TIPOATRIBUTO="tipo";
    public String NODE_ATTRIBUTE_ER="listAtributes";
	public String ER_DIAGRAM_NAME="er:Diagram";
	public String NODE_TABLE="listTable";
	public String NODE_RELATIONER="listRelation";
	public String RELATION_MANYTOMANY="er:ManyToMany";
	public String RELATION_ONETOONE="er:OneToOne";
	public String RELATION_ONETOONEOPTIONAL="er:OneToOneOptional";
	public String RELATION_ONETOMANYOPTIONAL="er:OneToManyOptional";
	public String[] ER_RESERVED_WORDS={"void","int","if","public","static","for","class"};
	//nombreAtributo ER
	public  String ATTRIBUTE_NAME_ATRIBUTE_TABLE="nombreAtributo";
	//nombreAtributo ER
	public  String ATTRIBUTE_NAME_TABLE="nombreTabla";
	//BPMN CONSTANTES
	public String BPNM_MODEL_PROCESS="model:process";
	
	//BPMN START eVENT
	public String BPNM_START_EVENT="model:startEvent";
	
	public static Variables getInstance(){
		if(instance==null){
			instance=new Variables();
		}
		return instance;
	}
	
	
	private Variables() {
		
		inicializarVariables();
	}
	
	private void inicializarVariables() {
		  try {
		   Properties propiedades = new Properties();
		   propiedades
		     .load(getClass().getResourceAsStream("/variables/variables.properties"));
		   
		   CTT_AGREGATION = propiedades.getProperty("CTT_AGREGATION");
		   CTT_ENABLING = propiedades.getProperty("CTT_ENABLING");
		   CTT_DISABLING=propiedades.getProperty("CTT_DISABLING");
		   CTT_INDEPENDENTCONCURRENCY=propiedades.getProperty("CTT_INDEPENDENTCONCURRENCY");
		   NODE_DIAGRAM_CTT=propiedades.getProperty("NODE_DIAGRAM_CTT");
		   NODE_TYPE_DIAGRAM_CTT=propiedades.getProperty("NODE_TYPE_DIAGRAM_CTT");
		   NODE_LIST_RELATION_TASK=propiedades.getProperty("NODE_LIST_RELATION_TASK");
		   NODE_LIST_TASK_CTT=propiedades.getProperty("NODE_LIST_TASK_CTT");
		   ATTRIBUTE_NAME=propiedades.getProperty("ATTRIBUTE_NAME");
		   ATTRIBUTE_SOURCE=propiedades.getProperty("ATTRIBUTE_SOURCE");
		   ATTRIBUTE_XSI_TYPE=propiedades.getProperty("ATTRIBUTE_XSI_TYPE");
		   ATTRIBUTE_TARGET=propiedades.getProperty("ATTRIBUTE_TARGET");
		   CTT_ABSTRACTION_TASK=propiedades.getProperty("CTT_ABSTRACTION_TASK");
		   CTT_SYSTEM_TASK=propiedades.getProperty("CTT_SYSTEM_TASK");
		   CTT_USER_TASK=propiedades.getProperty("CTT_USER_TASK");
		   CTT_INTERACTION_TASK=propiedades.getProperty("CTT_INTERACTION_TASK");
		  } catch (FileNotFoundException e) {
		   System.out.println("Error, El archivo no exite");
		  } catch (IOException e) {
		   System.out.println("Error, No se puede leer el archivo");
		  }
}
		

/**
	public static String getNombreNodo(String source,Node node){
		
		if(source.equals("")||source==null)
		{
			return "";
		}
		String[] splitSource=source.split("\\.");
		int numeroNodo=Integer.parseInt(splitSource[splitSource.length-1]);
		String nombreNodo="";

		int contador=0;
		for (Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()) {

			if(sibling.getNodeName()==Variables.NODE_LIST_TASK_CTT)
			{
				if(contador==numeroNodo)
				{
					NamedNodeMap attribute=sibling.getAttributes();
					nombreNodo=attribute.getNamedItem(Variables.ATTRIBUTE_NAME).getNodeValue();
					return nombreNodo;
				}
				else
					contador++;
			}
		}
		return nombreNodo;
	}
	*/
}
