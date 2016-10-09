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

public class RelationsTypeCheckTest extends AbstractCheckTester{
	Variables var= Variables.getInstance();
	@Test
	public void checkAttributeName() throws IOException {
		XmlSourceCode sourceCode = parseAndCheck(
				createTempFile(
						"<usecase:Diagram xmi:version='2.0' xmlns:xmi='http://www.omg.org/XMI' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:usecase='http://usecase'>"
		    	    		  	+"<listUseCase name='caso1'/>"
		    	    		  	+"<listUseCase name='caso2'/>"
		    	    		  	+"<listUseCase/>"
		    	    		  	+"<listUseCase name='caso3'/>"
		    	    		  	+"<listActors name='actor1'/>"
		    	    		  	+"<listActors name=''/>"
		    	    		  	+"<listRelation xsi:type='tipoInvalido' source='//@listUseCase.1'/>"
								+"<listRelation xsi:type='' source='' target='//@listUseCase.2'/>"
								+"<listRelation xsi:type='usecase:Hiearchy' source='//@listActors.0' target='//@listActors.1'/>"
		    	    		    +"</usecase:Diagram>"
		    		  ),
						new RelationsTypeCheck());

		assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 2, sourceCode.getXmlIssues().size());
	}
}
