# DropwizardRethinkDB

This project demonstrates several features of building a restfulApi service that one can
use as a starting point and can build upon.

Below are the main features.

- RethinkDB as a backend database.
- Dropwizard Swagger for API discovery.
- Dropwizard Oauth2 authentication with it's own implementation.
- Uses bcrypt for password encryption
- Full User CRUD operations and validations on basic fields.
- HealthCheck (app wont start if it can't connect to RethinkDB)
- Example of Unit and Integration test (lacking but more TBA)



How to start the ExampleService application
---

1. Get RethinkDB up and running and database/tables created. See config.yml file for details.
1. Run `mvn clean package` to build your application
1. Start application with `java -jar target/DropwizardRethinkdb-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8085`
1. Browse available endpoints in swagger `http://localhost:8085/swagger`

Health Check
---

To see your applications health enter url `http://localhost:8085/healthcheck`


To Do
-----
- Add caching https://www.dropwizard.io/1.3.8/docs/manual/auth.html#caching
- More unit/integration tests
- Restlet API Test Suite
- Refactor sloppy code (there are likely some lurking)

License
-------
- Provided as is without any warranty or liability.
- You are free to copy/paste/sell/resell as much as you like at your own risk.

Credits
-------
- https://github.com/dropwizard/dropwizard/tree/master/dropwizard-example
- https://github.com/remmelt/dropwizard-oauth2-provider