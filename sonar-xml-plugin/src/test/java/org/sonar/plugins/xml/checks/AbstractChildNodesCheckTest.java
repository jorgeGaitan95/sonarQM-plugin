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

public class AbstractChildNodesCheckTest extends AbstractCheckTester {
	Variables var=Variables.getInstance();
	@Test
	public void checkOnlyParent() throws IOException {
		XmlSourceCode sourceCode = parseAndCheck(
				createTempFile(
						"<"+var.NODE_DIAGRAM_CTT+" name='ctt1'>"
								+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_ABSTRACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='Despegar'/>"
								+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='DefinirViaje' theTarea='//@itsPackage.2/@listDialogTask.0'/>"
								+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='SeleccionarBoleto' theTarea='//@itsPackage.3/@listDialogTask.0'/>"
								+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INTERACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='RealizarPago' theTarea='//@itsPackage.4/@listDialogTask.0'/>"
								+"<"+var.NODE_LIST_TASK_CTT+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_ABSTRACTION_TASK+"' "+var.ATTRIBUTE_NAME+"='DefinirOrigen' theTarea='//@itsPackage.2/@listDialogTask.3'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.1'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.2'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.2' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.3'/>"
								+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.2' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.4'/>"
								+"</"+var.NODE_DIAGRAM_CTT+">"
						),
				new AbstractChildNodesCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 1, sourceCode.getXmlIssues().size());
	}
}
