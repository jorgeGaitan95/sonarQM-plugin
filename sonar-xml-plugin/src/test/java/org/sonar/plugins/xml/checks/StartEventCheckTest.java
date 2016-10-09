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

public class StartEventCheckTest  extends AbstractCheckTester{
	Variables var= Variables.getInstance();
	@Test
	public void checkDiagram() throws IOException {
	    XmlSourceCode sourceCode = parseAndCheck(
	    createTempFile(
	    		  "<model:definitions xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'><model:collaboration id='_lGu3gaA-EeKVlrNCq9gs1g'><model:participant id='_VUCHEF_0EeaNFIKiBP4nNg' name='MesaDeAyudaTI' processRef='_lMv_8KA-EeKVlrNCq9gs1g'/><model:participant id='_YwomQF_zEeaNFIKiBP4nNg' name='Empleado'/></model:collaboration><model:process id='_lMv_8KA-EeKVlrNCq9gs1g' name='MesaDeAyudaTI'><model:ioSpecification id='_VUCHE1_0EeaNFIKiBP4nNg'><model:inputSet id='_VUCHFF_0EeaNFIKiBP4nNg'/><model:outputSet id='_VUCHFV_0EeaNFIKiBP4nNg'/></model:ioSpecification><model:laneSet id='MesaDeAyudaTI_laneSet'><model:lane id='_lQdbQKA-EeKVlrNCq9gs1g' name='Empleado'><model:flowNodeRef>_lUJBYKA-EeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_atIpoKBCEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_YsDtQKBDEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_Z2jKcKBDEeKVlrNCq9gs1g</model:flowNodeRef></model:lane><model:lane id='_FYI3cKBBEeKVlrNCq9gs1g' name='Soporte General'><model:flowNodeRef>_ejz1sKBBEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_fhGkYKBBEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_YRd4cKBCEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_TcyQUKElEeKJBNcaO7vGIw</model:flowNodeRef><model:flowNodeRef>_ShGBAPXQEeKOHrdHXlGUVA</model:flowNodeRef></model:lane><model:lane id='_FnF5cKBBEeKVlrNCq9gs1g' name='Soporte Avanzado'><model:flowNodeRef>_XQXn0KBCEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_WOAL4KBCEeKVlrNCq9gs1g</model:flowNodeRef><model:flowNodeRef>_yriSwPXREeKOHrdHXlGUVA</model:flowNodeRef><model:flowNodeRef>_8pUKgPXREeKOHrdHXlGUVA</model:flowNodeRef></model:lane></model:laneSet><model:startEvent id='_lUJBYKA-EeKVlrNCq9gs1g' name='Abrir nuevo caso'/><model:serviceTask id='_atIpoKBCEeKVlrNCq9gs1g' name='Notificar a los empleados de la resolución del caso'/><model:userTask id='_YsDtQKBDEeKVlrNCq9gs1g' name='Verificar el caso'/><model:startEvent id='_lUJBYKA-EeKVlrNCq9gs1g' name='Inicio2'/><model:exclusiveGateway id='_Z2jKcKBDEeKVlrNCq9gs1g' name='Verificar el caso' default='_cikEwKBDEeKVlrNCq9gs1g'/><model:userTask id='_ejz1sKBBEeKVlrNCq9gs1g' name='Analizar caso'/></model:process><model:process id='_NCI9oPXTEeKOHrdHXlGUVA' name='Programar visita en sitio'><model:ioSpecification id='_VUCHN1_0EeaNFIKiBP4nNg'><model:inputSet id='_VUCHOF_0EeaNFIKiBP4nNg'/><model:outputSet id='_VUCHOV_0EeaNFIKiBP4nNg'/></model:ioSpecification><model:laneSet id='Programar visita en sitio_laneSet'><model:lane id='_U2VSsPXTEeKOHrdHXlGUVA' name='Coordinador de visitas en sitio'><model:flowNodeRef>_TCn5YPXTEeKOHrdHXlGUVA</model:flowNodeRef><model:flowNodeRef>_btQuQPXTEeKOHrdHXlGUVA</model:flowNodeRef><model:flowNodeRef>_dqWGAPXTEeKOHrdHXlGUVA</model:flowNodeRef></model:lane></model:laneSet><model:startEvent id='_TCn5YPXTEeKOHrdHXlGUVA' name='Inicio'/><model:userTask id='_btQuQPXTEeKOHrdHXlGUVA' name='Programar visita'/><model:serviceTask id='_dqWGAPXTEeKOHrdHXlGUVA' name='Notificar el equipo de la visita'/><model:userTask id='_sLolMPXTEeKOHrdHXlGUVA' name='Visita en sitio y resolucion del caso'/><model:boundaryEvent id='_-lVgYPXTEeKOHrdHXlGUVA' name='Alerta automatica todos los dias' attachedToRef='_sLolMPXTEeKOHrdHXlGUVA' cancelActivity='false'><model:timerEventDefinition id='eventdef-Alerta automatica todos los dias'><model:timeCycle>86400000L</model:timeCycle></model:timerEventDefinition></model:boundaryEvent><model:serviceTask id='_JBfWMPXUEeKOHrdHXlGUVA' name='Notificar al equipo de la visita'/><model:endEvent id='_LMRLsPXUEeKOHrdHXlGUVA' name='Fin'/><model:endEvent id='_cendQPXUEeKOHrdHXlGUVA' name='Vsita completada'/></model:process></model:definitions>"
	     ),
	    new StartEventCheck());
	    assertEquals(INCORRECT_NUMBER_OF_VIOLATIONS, 1, sourceCode.getXmlIssues().size());
	}
}
