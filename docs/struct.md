  # Directory Structure

Below is the directory structure of the entire project.

```
├── admin3-server                  # Backend service module
│   ├── src/main/java
│   │    └──tech.wetech.admin3
│   │       ├── common             # Common utilities
│   │       ├── controller         # Controllers
│   │       ├── infra              # Infrastructure, middleware configurations, and technical implementations of service and repository layers
│   │       └── sys                # Business module, sys business module
│   ├── src/main/test              # Test cases
│   └── pom.xml                    # Maven config
├── admin3-ui                      # Frontend module
│   ├── public                 
│   ├── src                
│   │   ├── api                    # API request definitions
│   │   ├── assets                 # Local static resources
│   │   ├── components             # Business common components
│   │   ├── router                 # Route configuration
│   │   ├── store                  # Global storage, state management
│   │   ├── utils                  # Utility libraries
│   │   ├── views                  # View pages
│   │   ├── App.vue                # Vue template entry
│   │   └── main.ts                # Vue JS entry
│   ├── .env                       # Environment variables
│   ├── index.html                 # Home page
│   ├── package.json               # Node.js config
│   ├── vite.config.ts             # Vite config
│   └── pom.xml
├── pom.xml                        
└── README.md                      
```

## Recommended Code Structure

To make the project code organization more standardized and allow developers to locate related page component code more conveniently, a set of conventions is defined here. These conventions are currently only recommended guidance, not mandatory.

### Backend

```
└──src/main/java
    └──tech.wetech.admin3
       ├── controller 
       │   └── XxxController.java           # Controllers; inter-module service calls can be handled at the controller layer. To improve project cohesion, it is not recommended to place controllers in business modules.
       ├── infra                            # Infrastructure, middleware configurations, and technical implementations of service and repository layer interfaces
       └── sys                              # sys business module
       └── custombusiness                   # Custom business modules, create new packages according to business division
           ├── event                        # Define events for decoupling between businesses
           │   ├──  XxxCreated.java
           │   └──  XxxUpdated.java
           ├── exception                   
           │   ├──  XxxException.java       # Business exception
           │   └──  XxxResultStatus.java    # Business exception code
           ├── model
           │   └── Xxx.java                 # Business model
           ├── repository
           │   └── XxxRepository.java       # Repository object
           │
           └── service 
               ├── dto
               │   └── XxxDTO.java          # Data transfer object
               └── XxxService.java          # Business service; avoid defining interfaces unless necessary to prevent code bloat
```

### Frontend

TODO
