# survey-service

This MicroService is for posting a survey and retrieving statistics. *question-service* MicroService must run to make
this MicroService fully functional as it sends request there for validation. However, this MicroService could also run
independently but posting survey might not be possible due to the mentioned validation. 

When this application runs it also creates some data in order to test easier.

Swagger UI URL: **http://localhost:8082/swagger-ui.html**