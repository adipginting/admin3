# Common Commands

## Server Commands

- Compile and package
```
mvn clean package
```

- Install to local repository
```
mvn clean install
```
Note: If the server uses webjars to manage frontend resources, it is strongly recommended to install admin3-ui to the local repository.

- Run and deploy
```shell
cd admin3-server/target
nohup java -jar -Dspring.datasource.url=xxx -Dspring.datasource.username=xxx -Dspring.datasource.password=xxx admin3-server-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
```

- Package JVM image
```shell
mvn spring-boot:build-image -Dmaven.test.skip=true -Ddocker.image-name=xxx -Ddocker.username=xxx -Ddocker.password=xxx
```

- Package native image
```shell
 mvn -Pnative spring-boot:build-image -Dmaven.test.skip=true -Ddocker.image-name=xxx -Ddocker.username=xxx -Ddocker.password=xxx
```

- Test native image locally

> Note: You need to install [graalvm](https://www.graalvm.org/latest/docs/getting-started/) locally.

```shell
mvn clean -X package -Pnative,nativeTest
```


## Frontend Commands

- Install dependencies
```
yarn install
```

- Run in development mode
```
yarn dev
```

- Build project
```
yarn build
```
