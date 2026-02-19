# SafeNet Backend

SafeNet Backend is a **Spring Boot (Java 17)** service intended to discover devices on configured networks and store network visibility data in PostgreSQL.

> Current status: this repository is an **early-stage backend foundation**. Core scanning and persistence flows exist, while API endpoints, auth flows, and threat-analysis logic are still incomplete.

---

## What this project currently does

- Loads network definitions from the database.
- Runs a scheduled network scan (`fixedDelay = 30_000`) for each configured CIDR block.
- Uses ICMP reachability checks to identify active hosts.
- Tries to resolve host MAC addresses from the ARP table.
- Upserts discovered devices into the `devices` table.
- Maintains basic entity models for alerts, IP activity logs, users/roles, and threat scores.

---

## Tech stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Apache Commons Net** (`SubnetUtils`)

---

## Project structure

```text
src/main/java/com/safenet/backend
├── entities/              # JPA entities: Network, Device, Alert, User, Role, etc.
├── repositories/          # Spring Data repositories
├── services/
│   ├── network/           # network retrieval + discovery orchestration
│   └── device/            # find-or-create device logic
├── networkScanner/        # scanner interface, ICMP implementation, CIDR/IP helpers
├── dtos/                  # ScanResult DTO
└── SafenetBackendApplication.java
```

---

## Prerequisites

- JDK 17+
- PostgreSQL 13+ (or compatible)
- Gradle wrapper (already included)
- OS/network permissions that allow ICMP checks and ARP table access

---

## Configuration

Default configuration is in `src/main/resources/application.yaml`:

- Database URL: `jdbc:postgresql://localhost:5432/safenet_db`
- Username: `sanjar`
- Password: `12345`
- JPA schema mode: `update`

### Recommended override (local)

Do **not** commit real secrets. Prefer environment-variable overrides, for example:

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/safenet_db
SPRING_DATASOURCE_USERNAME=your_user
SPRING_DATASOURCE_PASSWORD=your_password
```

---

## Run the application

```bash
./gradlew bootRun
```

Or build and run:

```bash
./gradlew clean build
java -jar build/libs/safenet-backend-0.0.1-SNAPSHOT.jar
```

---

## Testing

Run tests with:

```bash
./gradlew test
```

> Note: `@SpringBootTest` loads the full app context, so local DB configuration can affect test execution.

---

## Database notes

The data model includes these primary entities:

- `Network` – CIDR blocks to scan.
- `Device` – discovered hosts per network.
- `Alert` – basic alert records (currently not fully wired).
- `IPActivityLog` – IP activity audit rows (currently not fully wired).
- `ThreatScore` – threat scoring placeholder.
- `User`, `Role`, `Configuration` – future security/config features.

---

## Current limitations / known issues

This section is intentionally explicit so contributors can prioritize work quickly.

1. **No REST controllers yet**
   - The backend has core services but no public API layer.
2. **Credentials are hardcoded in `application.yaml`**
   - Should be replaced with environment-based or secret-managed config.
3. **Scanner robustness**
   - ARP parsing is platform-dependent and may fail across OS variants.
   - ICMP reachability can require elevated permissions depending on environment.
4. **Logging**
   - Uses `System.out.println` / `System.err.println` instead of structured logging.
5. **Security is incomplete**
   - `User` / `Role` entities exist but authentication/authorization is not implemented.
6. **Partially implemented tracking flow**
   - `DeviceTrackingService` logic is commented out and not integrated.
7. **Tests are minimal**
   - Current test coverage is only application context loading.

---

## Suggested GitHub issues to create

If you are planning project management in GitHub, these are good first issues:

- [ ] Add REST controllers for network CRUD and scan triggering.
- [ ] Add Flyway/Liquibase migrations and remove reliance on `ddl-auto=update`.
- [ ] Replace hardcoded DB credentials with env vars + profile config.
- [ ] Implement Spring Security with JWT-based auth.
- [ ] Rework MAC resolution for cross-platform compatibility.
- [ ] Replace console printing with SLF4J + structured log format.
- [ ] Add integration tests with Testcontainers (PostgreSQL).
- [ ] Re-enable and finalize `DeviceTrackingService` workflow.
- [ ] Add API documentation (OpenAPI/Swagger).

---

## Contributing

1. Create a branch from `main` (or your team’s integration branch).
2. Keep changes focused and small.
3. Run `./gradlew test` before opening a PR.
4. Update this README for any architecture/configuration changes.

---

## License

No license file is currently provided in this repository.
Add one (for example MIT/Apache-2.0) before public distribution.
