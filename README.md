# Belajar Kafka Consumer

Project ini adalah Kafka consumer berbasis Spring Boot untuk membaca topik `thermostat_readings`.

## Prasyarat

- Java 21
- Maven Wrapper (sudah tersedia di repository: `mvnw` / `mvnw.cmd`)

## Konfigurasi Environment Variable

Aplikasi membaca credential Confluent Cloud dari environment variable berikut:

- `CONFLUENT_CLOUD_API_KEY`
- `CONFLUENT_CLOUD_API_SECRET`

### PowerShell (Windows)

```powershell
$env:CONFLUENT_CLOUD_API_KEY="your_api_key"
$env:CONFLUENT_CLOUD_API_SECRET="your_api_secret"
```

### CMD (Windows)

```cmd
set CONFLUENT_CLOUD_API_KEY=your_api_key
set CONFLUENT_CLOUD_API_SECRET=your_api_secret
```

### Bash (Linux/macOS)

```bash
export CONFLUENT_CLOUD_API_KEY="your_api_key"
export CONFLUENT_CLOUD_API_SECRET="your_api_secret"
```

## Cara Menjalankan

Jalankan dari root repository:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=consumer
```

Untuk Windows PowerShell, gunakan:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=consumer
```

## Catatan

- Pastikan environment variable sudah diset pada terminal yang sama sebelum menjalankan aplikasi.
- Saat ini konfigurasi utama ada di `src/main/resources/application.properties`.
