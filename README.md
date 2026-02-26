## Build & Run

Requirements: Java 17+

### Build
mvn clean package

### Run
java -jar target/timeledger-1.0-SNAPSHOT.jar -h
java -jar target/timeledger-1.0-SNAPSHOT.jar start <project>
java -jar target/timeledger-1.0-SNAPSHOT.jar status
java -jar target/timeledger-1.0-SNAPSHOT.jar stop
java -jar target/timeledger-1.0-SNAPSHOT.jar report <project>

### Data storage
Timeledger stores data locally in `~/.timeledger/timeledger.db`.