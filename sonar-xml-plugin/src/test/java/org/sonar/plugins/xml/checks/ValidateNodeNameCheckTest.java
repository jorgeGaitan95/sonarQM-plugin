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

public class ValidateNodeNameCheckTest extends AbstractCheckTester {

	Variables var= Variables.getInstance();

	@Test
	public void checkNodeName() throws IOException {
		XmlSourceCode sourceCode = parseAndCheck(

				createTempFile(
						"<prj:ProjectMDD xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:herramienta='http:///herramienta.ecore' xmlns:herramienta.diagrams.dialog.concret='http:///herramienta/diagrams/dialog/concret.ecore' xmlns:herramienta.diagrams.domain='http:///herramienta/diagrams/domain.ecore' xmlns:herramienta.diagrams.interaction='http:///herramienta/diagrams/interaction.ecore' xmlns:herramienta.diagrams.navegation='http:///herramienta/diagrams/navegation.ecore' xmlns:herramienta.diagrams.ui='http:///herramienta/diagrams/ui.ecore'>"
								+"<itsPackage "+var.ATTRIBUTE_XSI_TYPE+"='herramienta.diagrams.ui:UI_Diagram' name='CUI'>"
		    	    		  	+"</itsPackage>"
		    	    		  	+"<"+var.NODE_DIAGRAM_CTT+">"
		    	    		  	+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_ABSTRACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='4Despegar'/>"
		    	    		    +"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='SeleccionarBoleto' theTarea='//@itsPackage.3/@listDialogTask.0'/>"
		    	    		    +"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='' theTarea='//@itsPackage.4/@listDialogTask.0'/>"
		    	    		    +"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='Definir.Origen' theTarea='//@itsPackage.2/@listDialogTask.3'/>"
		    	    		    +"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='DefinirOrigen2' theTarea='//@itsPackage.2/@listDialogTask.3'/>"
		    	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.5'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.10'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.11'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.12'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.13'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.2' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.12'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.12' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.13'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.2'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.3'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.1' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.6'/>"
								+"</"+var.NODE_DIAGRAM_CTT+">"
		    	    		    +"</prj:ProjectMDD>"
		    		  ),

						new ValidateNodeNameCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 2, sourceCode.getXmlIssues().size());
	}

	
}
