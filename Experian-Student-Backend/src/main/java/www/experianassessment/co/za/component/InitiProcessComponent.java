package www.experianassessment.co.za.component;

import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import www.experianassessment.co.za.enums.ProcessVariablesEnum;

@Component
public class InitiProcessComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(InitiProcessComponent.class);

	@Autowired
	private RuntimeService runtimeService;

	public void initializeProcess(String executionId, String processInstanceId) {
		runtimeService.setVariable(executionId, ProcessVariablesEnum.SERVICE_SUCCESS.variableName(), true);
		runtimeService.setVariable(executionId, ProcessVariablesEnum.CONTINUE_PROCESS.variableName(), true);
		runtimeService.setVariable(executionId, ProcessVariablesEnum.SYSTEM_TIMEOUT.variableName(), false);
		runtimeService.setVariable(executionId, ProcessVariablesEnum.MY_EXECUTION_ID.variableName(), executionId);
		runtimeService.setVariable(executionId, ProcessVariablesEnum.MY_PROCESS_ID.variableName(), processInstanceId);

	}

}
