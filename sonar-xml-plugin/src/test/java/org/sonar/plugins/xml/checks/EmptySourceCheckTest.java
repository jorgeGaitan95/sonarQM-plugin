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

public class EmptySourceCheckTest extends AbstractCheckTester {

	Variables var= Variables.getInstance();

	@Test
	public void checkAttributeTarget() throws IOException {
		String a= "<"+var.NODE_DIAGRAM_CTT+" "+var.ATTRIBUTE_NAME+"='ctt1'>"
				+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.4'/>"
				+"</"+var.NODE_DIAGRAM_CTT+">";

		XmlSourceCode sourceCode = parseAndCheck(
				createTempFile(a),
				new EmptySourceCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 1, sourceCode.getXmlIssues().size());
	}

	@Test
	public void checkAttributeTarget1() throws IOException {
		String a= "<"+var.NODE_DIAGRAM_CTT+" "+var.ATTRIBUTE_NAME+"='ctt1'>"
				+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.4'/>"
				+"</"+var.NODE_DIAGRAM_CTT+">";
		
		XmlSourceCode sourceCode = parseAndCheck(
				createTempFile(a),
				new EmptySourceCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 0, sourceCode.getXmlIssues().size());
	}
}
