# Admin3

## Project Description

The project uses the latest technology stack (backend Java21, SpringBoot3.2, frontend TypeScript, Vite3, Vue3, Element Plus), providing only the core best practices for user and permission management, suitable for secondary custom development, private projects, source code learning, and other scenarios.

## Main Features

* No security framework is used; session management and permission control are elegantly implemented, supporting request URL permission interception, menu and button level permission control.
* Sessions use the database as a secondary cache, so login status is not lost even after restart.
* Supports AOT compilation for Native images, saving at least 50% memory compared to traditional JVM.
* Frontend resources support both independent deployment and webjars embedded into the backend application.
* Persistence layer uses SpringData JPA, supporting multiple database adaptations.
* All interfaces have end-to-end test case coverage; any code changes will trigger a full test run to ensure absolute functionality.
* Unified object storage encapsulation, achieving isolation between the application and uploaded file locations, supporting local upload and all S3-compliant object storage solutions (Alibaba Cloud, Huawei Cloud, MinIO, etc.).

## Online Demo

- admin / 123456 (has all permissions)
- employee / employee (has partial permissions)
- guest / guest (has view-only permissions)

Demo URL: https://admin3.wetech.tech

Online Deployment: https://gitpod.io/#https://github.com/cjbi/admin3

## Project Preview

<table>
    <tr>
        <td><img src="docs/image/login.jpg"/></td>
        <td><img src="docs/image/user.png"/></td>
    </tr>
    <tr>
        <td><img src="docs/image/role-auth.png"/></td>
        <td><img src="docs/image/role-member.png"/></td>
    </tr>
    <tr>
       <td><img src="docs/image/permission.png"/></td>
       <td><img src="docs/image/log-list.png"/></td>
    </tr>
    <tr>
       <td><img src="docs/image/storage.png"/></td>
    </tr>
</table>

## Technical Documentation

- [Permission Management](docs/authority-management.md)

- [Directory Structure](docs/struct.md)

- [API Reference](docs/api-reference.md)

- [Object Storage](docs/storage.md)

- [Common Commands](docs/command.md)

## Software Requirements

- JDK 17
- MySQL 8.0
- Maven 3.6.0+

## Local Deployment

- Download the source code via git
- Create database admin3 with UTF-8 encoding
- Modify application.yml to change MySQL username and password
- Start the service and visit the admin console at: http://localhost:8080/admin3
- Swagger URL: http://localhost:8080/admin3/swagger-ui/index.html

## Todo List

- [x] Operation Logs

- [x] File Upload (unified encapsulation of common third-party object storage services), supports changing user avatar

- [ ] Internationalization

- [ ] More features please leave a message in [Issues](https://github.com/cjbi/admin3/issues)

Welcome to contribute to the project!

## License

[MIT](LICENSE)
