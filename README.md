# RestServiceToRegisterString
This is a rest service project. A rest controller named AzraServiceController is added which supports 2 methods POST and GET.
POST URL -> /service/register/idToRegister="abbc"
GET URL -> /service/{registeredId}

Build instruction
------------------------
Project can be built using maven clean build from the root folder of this project

War Deploy
--------------------
generated war can be found under ./target
War should be deployed like a traditional war file to a container

