# Timeledger — time tracking from your terminal

Timeledger is a small command-line app that helps you track how long you spend on each client/project. You “start” a project when you begin working, “stop” when you’re done, and generate a report when you want to invoice.

This project is built as a backend-style application (clean architecture + tests), packaged as a runnable JAR.

---

## What you can do with it

- Start tracking: `start <project>`
- Check what’s running: `status`
- Stop tracking: `stop`
- See total time for a project: `report <project>`
- Report within a time range: `report <project> --since <ISO> --until <ISO>`

**Data is saved locally with SQLite**, so your time entries persist even after you close the terminal.

---

## Run it locally (copy/paste)

### Requirements
- Java 17+

### Build
```bash
mvn clean test
mvn clean package
```

### Use
```bash
java -jar target/timeledger-1.0-SNAPSHOT.jar start project-alpha
java -jar target/timeledger-1.0-SNAPSHOT.jar status
java -jar target/timeledger-1.0-SNAPSHOT.jar stop

java -jar target/timeledger-1.0-SNAPSHOT.jar report project-alpha
java -jar target/timeledger-1.0-SNAPSHOT.jar report project-alpha --since 2026-02-01T00:00:00Z --until 2026-02-28T23:59:59Z
```

## Where your data is stored

Timeledger saves its database here:

Windows: C:\Users\<you>\.timeledger\timeledger.db

macOS/Linux: ~/.timeledger/timeledger.db

## Notes

Report output is currently in minutes (e.g., Total: 90m).

Range reporting currently counts entries fully inside the window (simple and predictable).


## Architecture (high-level)

Timeledger is structured like a small backend service with a CLI front-end.

- **CLI layer** (`dev.timeledger.cli`): picocli commands (`start/stop/status/report`) and minimal wiring.
- **Application layer** (`dev.timeledger.tracking.usecase`): use-cases that enforce business rules (no overlapping sessions, reporting totals).
- **Domain model** (`dev.timeledger.tracking.model`): simple immutable objects (`ActiveSession`, `TimeEntry`).
- **Ports** (`dev.timeledger.tracking.port`): abstractions like `Clock` and repositories (programming against interfaces).
- **Adapters/Infra** (`dev.timeledger.tracking.infra.sqlite`): SQLite implementations behind the ports.
- **Testing**: TDD-first with unit tests for use-cases + integration tests for SQLite repos + CLI tests using deterministic clocks and temp DB files.

## Demo

🎥 Terminal demo (asciinema): _add link here_


## For recruiters / reviewers

If you want to skim the code quickly:
- CLI commands: `src/main/java/dev/timeledger/cli`
- Core logic (use-cases): `src/main/java/dev/timeledger/tracking/usecase`
- Tests (unit + CLI + SQLite): `src/test/java/dev/timeledger`