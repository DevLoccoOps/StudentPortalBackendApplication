package www.experianassessment.co.za.util;

public interface CamundaComponentMessageHandler {
	enum MessageTypeEnum {
		ERROR, WARNING, INFO,
	}

	/**
	 * Handle message
	 * 
	 * @param type
	 * @param executionId
	 * @param message
	 */
	public void handleMessage(MessageTypeEnum type, String executionId, String message);

	/**
	 * Handle message
	 * 
	 * @param type
	 * @param parentExecutionId
	 * @param executionId
	 * @param message
	 */
	public void handleMessage(MessageTypeEnum type, String parentExecutionId, String executionId, String message);
}
