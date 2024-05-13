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
