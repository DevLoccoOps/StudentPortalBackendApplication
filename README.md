
###Backend Student Application

Frameworks

Java 8
1. Camunda BPMN 7.18.0 
2. Springboot 2.7.5

Database Configuration
1. Postgresql 

Junit 
1. Mockito 
2. Rest assured
3. Junit 4

Project Interaction Layout

- Workflow (StudentWorkflowAPI.class)
  - The worklow is the class that consists of API's
  - The API's consists of Camunda processes to givern the process driven development
  - The API's use the same Request POJO (StudentRequest) for the Request body
    - StudentRequest consists of StudentInfo POJO that is resuable thought the system for any API's request
  - The API's use the same Reply POJO (StudentReply) for the Reply body
    - StudentReply consists of StudentInfo POJO that is resuable thought the system for any API's replys
  
- Component (StudentComponent.class)
  - The component class has all the methods that interact with the camunda processes
  
Resources folder consits of the project folder as follows:
- alerts (Messages)
        - error
          - errors.properties
        - warning
          - warning.properties
          
-META-INF (JPA)
        - jpa.named.queries.properties
        
- Processes (Camunda)
        - All BPMN processes are placed here
        
- application.properties


API Consuming process

- Workflow(URL exectued, camunda process starts) ->> Components(Methods executed based on process) ->> Back to Workflow to respond with the reply


###How to start the application

1. Verify database settings on the application.properties file for your machine
2. server port is 8080 (Can be changed in the application.properties file)
3. Application has a context path : /experian




   

        
        
  
  
  
  



