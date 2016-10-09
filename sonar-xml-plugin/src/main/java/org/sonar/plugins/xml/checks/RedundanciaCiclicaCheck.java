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

import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * @author Jorge
 *
 */
@Rule(key="RedundanciaCiclica",
name="Relationships must not contain cyclic redundancy",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
public class RedundanciaCiclicaCheck extends AbstractXmlCheck{
	 
	/*
	 * valida el primer patron, en el cual si no existe un nodo inicial porque todos estan conectados
	 */
	public boolean hasRelationCyclic(ArrayList<Nodo> nodos,ArrayList<Integer> nodosHijos){
		for (int i = 0; i < nodosHijos.size(); i++) {
			int pos=nodosHijos.get(i);
			if(nodos.get(pos).getAnteriorNodo()<0){
				return false;
			}
		}
		return true;
	}
	public void validateNodesDiagram(TreeAbstract arbol){
		ArrayList <Nodo> nodos=arbol.getArbol();
		for (int i = 0; i < nodos.size(); i++) {
			if(nodos.get(i).getHijosPos().size()>0){
				if(hasRelationCyclic(nodos,nodos.get(i).getHijosPos())){
					createViolation(getWebSourceCode().getLineForNode(nodos.get(i).getNodoReferencia())
							,"check that relations between nodes do not form a cycle");
				}
			}
		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);
		if (getArbol()!=null) {
			validateNodesDiagram(getArbol());
		}
	}
}
