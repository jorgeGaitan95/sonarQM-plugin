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

public class ParentNodeCheckTest extends AbstractCheckTester {
	
	Variables var= Variables.getInstance();
	
	@Test
	  public void checkAttributeTarget() throws IOException {
	    XmlSourceCode sourceCode = parseAndCheck(
	      createTempFile(
	    		  "<"+var.NODE_DIAGRAM_CTT+" name='ctt1'>"
	    		  	+"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.5'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_DISABLING+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.7' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.6'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_ENABLING+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.1' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.2'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_ENABLING+"'' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.2' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.3'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.1'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.1' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.7'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.7' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.4'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.7' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.5'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.8'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.9'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.10'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.11'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.12'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.5' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.13'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"'' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.11' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.12'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_INDEPENDENTCONCURRENCY+"'' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.12' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.13'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.2'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.3'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.1' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.6'/>"
	    		    +"</"+var.NODE_DIAGRAM_CTT+">"
	    		  ),
	      new ParentNodeCheck());

	    assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 0, sourceCode.getXmlIssues().size());
	  }
	
	@Test
	  public void checkAttributeTarget2() throws IOException {
	    XmlSourceCode sourceCode = parseAndCheck(
	      createTempFile(
	    		  "<"+var.NODE_DIAGRAM_CTT+" name='ctt1'>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.1'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.1' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.7'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.9'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.0' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.3'/>"
	    		    +"<"+var.NODE_LIST_RELATION_TASK+" "+var.ATTRIBUTE_XSI_TYPE+"='"+var.CTT_AGREGATION+"' "+var.ATTRIBUTE_SOURCE+"='//@itsPackage.1/@listTaskCTT.4' "+var.ATTRIBUTE_TARGET+"='//@itsPackage.1/@listTaskCTT.10'/>"
	    		    +"</"+var.NODE_DIAGRAM_CTT+">"
	    		  ),
	      new ParentNodeCheck());

	    assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 1, sourceCode.getXmlIssues().size());
	  }
}
