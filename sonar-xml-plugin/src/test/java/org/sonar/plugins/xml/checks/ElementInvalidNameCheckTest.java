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

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class ElementInvalidNameCheckTest extends AbstractCheckTester {
	Variables var= Variables.getInstance();
	@Test
	  public void checkAttributeTarget() throws IOException {
	    XmlSourceCode sourceCode = parseAndCheck(
	    		createTempFile(
	  	    		  "<herramienta:ModelFactory xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:herramienta='http:///herramienta.ecore' xmlns:herramienta.diagrams.domain='http:///herramienta/diagrams/domain.ecore'><itsPackage xsi:type='herramienta.diagrams.domain:Domain_Diagram'><itsArc xsi:type='herramienta.diagrams.domain:Relacion' aNavegable='no' bAgregacion='si' source='//@itsPackage.0/@itsClases.0' target='//@itsPackage.0/@itsClases.2'/><itsArc xsi:type='herramienta.diagrams.domain:Herencia' source='//@itsPackage.0/@itsClases.0' target='//@itsPackage.0/@itsClases.1'/><itsClases name='4Clase1'><listaAtributos name='AtClase1' tipo='Integer'/><listaAtributos name='At.2Clase1' tipo='Double'/><listMetodos name='MTClase1' body='//ComentarioPrueba' returnType='void'><listParametros name='ATMTClase1' tipo='int'/><listParametros name='AT2MTClase1'/></listMetodos></itsClases><itsClases name='Clase2'></itsClases><itsClases name='Clase3'><listaAtributos name='AtClase3'/><listMetodos name='MTClase3' body='' returnType='void'><listParametros name='AtMTClase3'/><listParametros name='AtMT2Clase3'/></listMetodos></itsClases></itsPackage></herramienta:ModelFactory>"
	  	    		  ),
	  	      new ElementInvalidNameCheck());

	    assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 2, sourceCode.getXmlIssues().size());
	  }
}
