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

public class AttributeTypeCheckTest extends AbstractCheckTester{
	Variables var= Variables.getInstance();
	@Test
	public void checkAttributeName() throws IOException {
		XmlSourceCode sourceCode = parseAndCheck(
				createTempFile(
						"<er:Diagram xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:er='http://er'>"
						  +"<listTable nombreTabla=''>"
						    +"<listAtributes nombreAtributo='Cedula' tipo='' propiedad='K'/>"
						  +"</listTable>"
						  +"<listTable nombreTabla='Programa'>"
						    +"<listAtributes nombreAtributo='NombrePrograma'  propiedad='K'/>"
						    +"<listAtributes nombreAtributo='NombrePrograma2' tipo='String' propiedad='N'/>"
						    +"<listAtributes nombreAtributo='NombrePrograma3' tipo='String' propiedad='N'/>"
						  +"</listTable>"
						  +"<listRelation xsi:type='er:ManyToMany' source='//@listTable.1' target='//@listTable.0'/>"
						  +"<listRelation xsi:type='er:OneToOne' source='//@listTable.0' target='//@listTable.1'/>"
						  +"<listRelation xsi:type='er:OneToOneOptional' source='//@listTable.0' target='//@listTable.1'/>"
						  +"<listRelation xsi:type='er:OneToManyOptional' source='//@listTable.0' target='//@listTable.1'/>"
						+"</er:Diagram>"						
		    		  ),
						new AttributeTypeCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 2, sourceCode.getXmlIssues().size());
	}
}
