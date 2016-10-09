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

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

/**
 * The names of the diagram elements must be unique
 * @author Jorge Gaitán
 */
@Rule(key="SingleNodeNameCheck",
name="Item names must be diferent",
priority= Priority.INFO)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.INFO)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("10min")
public class SingleNodeNameCheck extends AbstractXmlCheck{

	private boolean tieneNombreIgual(ArrayList<Nodo> arbolNodos,String nombreNodo, int posActual){
		for (int i = 0; i < arbolNodos.size(); i++) {
			if(!(i==posActual)){
				if(arbolNodos.get(i).getNombre().equals(nombreNodo)){
					return true;
				}
			}
		}
		return false;
	}

	private void validateNodeName(TreeAbstract arbol){
		ArrayList <Nodo> nodos=arbol.getArbol();
		for (int i = 0; i < nodos.size(); i++) {

			if(tieneNombreIgual(nodos, nodos.get(i).getNombre(),i))
			{
				if(nodos.get(i).getNodoReferencia()!=null)
				{
					createViolation(getWebSourceCode().getLineForNode(nodos.get(i).getNodoReferencia())
							,"Change the name of Node, It is not recommended that exist nodes called equal");
				}
			}
		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);
		if (getArbol()!=null) {
			validateNodeName(getArbol());
		}
	}

}
