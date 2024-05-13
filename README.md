# Warehouse Management Setup

This guide outlines the process of setting up a PostgreSQL database and a pgAdmin management interface using Docker containers. The services come pre-configured with default settings, though you can customize them according to your requirements.

## Prerequisites

- Ensure Docker is installed on your system.

## Installation Steps

1. Clone this repository to your local machine.

2. Navigate to the directory containing the `docker-compose.yml` file.

3. Open a terminal window and execute the following command:

```bash
docker-compose up -d
```

## Configuration

### PostgreSQL

- **Container Name:** postgres_container
- **Image:** postgres:16.2
- **Exposed Port:** 5432
- **Environment Variables:**
  - `POSTGRES_USER`: Default username (default: gersi).
  - `POSTGRES_PASSWORD`: Default password (default: gersi).
  - `POSTGRES_DB`: Default database name (default: warehouse_management).
- **Volumes:**
  - `postgres_volume:/var/lib/postgresql/data`: Persists data between container restarts.
  - `./schema.sql:/docker-entrypoint-initdb.dl/schema.sql`: Mounts SQL schema file for initialization.

### pgAdmin

- **Container Name:** pgadmin_container
- **Image:** dpage/pgadmin4:8.5
- **Exposed Port:** 7000
- **Environment Variables:**
  - `PGADMIN_DEFAULT_EMAIL`: Default email for pgAdmin login (default: gersimuca@aol.com).
  - `PGADMIN_DEFAULT_PASSWORD`: Default password for pgAdmin login (default: gersimuca).
  - `PGADMIN_DEFAULT_ADDRESS`: Default address (default: 6000).
  - `PGADMIN_LISTEN_PORT`: Default listen port (default: 6000).
- **Volumes:**
  - `pgadmin_volume:/var/lib/pgadmin`: Persists pgAdmin data.

## Accessing Services

### PostgreSQL

- The database can be accessed using any PostgreSQL client.
  - **Host:** localhost
  - **Port:** 5432
  - **Credentials:**
    - **Username:** gersi (default) or as specified in `POSTGRES_USER`.
    - **Password:** gersi (default) or as specified in `POSTGRES_PASSWORD`.
    - **Database:** warehouse_management (default) or as specified in `POSTGRES_DB`.

### pgAdmin

- Access the pgAdmin interface by navigating to [http://localhost:7000](http://localhost:7000) in your web browser.
- Login using the provided credentials:
  - **Email:** gersimuca@aol.com (default) or as specified in `PGADMIN_DEFAULT_EMAIL`.
  - **Password:** gersimuca (default) or as specified in `PGADMIN_DEFAULT_PASSWORD`.

## Additional Notes

- Ensure the specified ports (5432, 7000) are not in use by other services on your machine.

## Project Application Configuration

### Management

```yaml
management:
  server:
    port: 9000
    ssl:
      enabled: false
  info:
    git:
      mode: full
  health:
    diskspace:
      enabled: true
      threshold: 5GB
  endpoint:
    health:
      show-components: always
      show-details: always
  endpoints:
    web:
      cors:
        allowed-origins: "*"
        allowed-methods: OPTIONS, GET, POST
        allowed-headers: "*"
      exposure:
        include: '*'
```

- **Description:** Manages the server, providing details on Git repository, health checks, and endpoint configurations.
- **Server Port:** Configures the port for server management.
- **Health Diskspace:** Monitors disk space usage with a specified threshold.
- **Endpoint Configuration:** Controls CORS (Cross-Origin Resource Sharing) settings and endpoint exposure.

### Logging

```yaml
logging:
  file:
    path: ./logs
```

- **Description:** Defines logging settings, including the directory where log files are stored.

### Server

```yaml
server:
  shutdown: graceful
  tomcat:
    max-keep-alive-requests: 5000
    connection-timeout: 60000
    mbeanregistry:
      enabled: true
    accesslog:
      buffered: true
      directory: access
      enabled: true
      file-date-format: .yyyy-MM-dd
      pattern: common
      prefix: access_log
      request-attributes-enabled: true
      rotate: true
      suffix: .log
    basedir: ./logs
    max-threads: 100
  servlet:
    context-path: /rest-api
  port: ${CONTAINER_PORT:9443}
  forward-headers-strategy: native
  use-forward-headers: true
  error:
    path: /user/error
    whitelabel:
      enabled: true
```

- **Description:** Configures various aspects of the embedded Tomcat server, including shutdown behavior, access logging, and error handling.
- **Tomcat Settings:** Specifies Tomcat-specific settings like maximum threads, access log details, and context path.
- **Port Configuration:** Defines the server port, with an option for environment variable fallback.
- **Forward Headers:** Enables forwarding of headers.
- **Error Handling:** Specifies the path for error handling.

### Spring

```yaml
spring:
  profiles:
    active: ${ACTIVE_PROFILE:dev}
  ...
```

- **Description:** Contains extensive configuration for the Spring framework, including profiles, data sources, caching, and security.
- **Profiles:** Sets the active profile, with a default value of 'dev'.
- **Data Source:** Defines the PostgreSQL database connection details.
- **Caching:** Configures caching mechanism with caffeine.
- **Security:** Specifies security settings such as user credentials and roles.
- **Servlet:** Configures servlet-related settings like multipart handling and JPA (Java Persistence API) configurations.

These configuration files are essential for the proper functioning of the project application. Adjustments can be made to tailor the application to specific deployment environments or business requirements.

## Additional Notes

- Ensure the specified ports (9000, 9443) are not in use by other services on your machine.
