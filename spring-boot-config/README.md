# Configuring Spring applications

## Application properties

Accessible via the @Value annotation. For example,
`@Value("${my.greeting: some default value}")`

- Base defaults are defined in src/main/resources/application.properties
  
- An application.properties file in the same path as the built jar will override the 
properties in the default application.properties (which is inside the jar)
  
- Properties can be specified via command line arguments to override application.properties files.
```
java -jar <jarname> --my.greeting="My greeting"
```

- Lists can also be specified in the application properties.

```
my.list=one,two,three
```

```java
@Value("${my.list}")
private List<String> myList;
```

- Key value pairs can also be specified as properties.

```
dbValues={connectionString: "somethingsomething", password: "pass"}
```

Using `#{}` implies evaluation
```java
@Value("#{${dbValues}}")
private Map<String, String> dbValues;
```

- The `@ConnectionProperties` annotation allows picking up ALL of the properties with a particular prefix.
    - Say, you could get all db properties in a Bean/class if all related properties are prefixed with `db.`.
    - Provides basic typechecking as well
  
- If spring-actuator is added as a dependency to the project, Config properties can be viewed at the endpoint `/actuator/configprops`

- It is possible to have an `application.yml` instead of `application.properties`. YAML is great.

## Spring Profiles

A profile is essentially a unique configuration set of properties.

- Created by creating a file `application-{env}.yml`. For example, `application-test.yml` will be used as the `test` profile.

- The active profile can be set by setting `spring.profiles.active: {name}` in the default application.yml. Or set the config via 
  the command line when running the jar.
  - This works because the default profile is always active. Additional set profiles simply override the default profile configurations.
  
### Specifying Beans to run only for particular profiles

- You can use the  `@Profile` annotation to specify that a particular Bean should run only for a particular profile.
```java
@Profile("dev")
public class LoadMockData {
    // Something
}
```

## The Environment Object

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class MyController {
  @Autowired
  private Environment env;
  
  public String getEnv(property String) {
      return env.getProperty("DB_HOST");
  }
}
```

- Generally, everything that the Environment object can do is doable using @Value and other properties. So it should be avoided in general.
- Note: The @Value annotation is pretty powerful and can also read environment variables.

## Spring Config Server

- Properties can be accessed at `{BASE_URL}/<file-name>/<profile>` in the browser of via API.
  - For example: `http://localhost:8888/application/default`
  
- To update config values in real time when it changes in the remote server, one way is to use Actuator to provide and endpoint which triggers a reload of environment.
  - All beans that need to have the refreshed values should be annotated with `@RefreshScope`
  - The endpoint is POST `/actuator/refresh`. This request causes the app to reload its config from the remote config server.
  - The endpoint has to be hit manually. The config-server won't do it automatically.
  
## Best practices

- Stuff has will rarely change, if ever, and does not need to be different for instances of a microservice: Use properties file.
- Is it constantly changing? Can it be changed often? Use the Config server.
- Securing config:
  - Use spring security to secure the spring-cloud server
  - Use SSH for git access.
  - Use encrypted data in git. Spring cloud server can be configured to decrypt these values.
  
- Use sensible defaults.