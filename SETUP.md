# Setup Environment — enterprise-libs

Panduan instalasi semua tools yang dibutuhkan di Windows 10.

---

## 1. Java 21 (GraalVM — untuk native binary)

GraalVM menggantikan JDK biasa dan mendukung native compilation.

### Cara Install:
1. Buka: https://www.graalvm.org/downloads/
2. Pilih: **GraalVM for JDK 21** → **Windows (x64)** → download `.zip`
3. Ekstrak ke `C:\Program Files\GraalVM\graalvm-jdk-21`
4. Set environment variables:
   ```powershell
   # Buka PowerShell sebagai Administrator
   [System.Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\GraalVM\graalvm-jdk-21", "Machine")
   [System.Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\GraalVM\graalvm-jdk-21\bin", "Machine")
   ```
5. Verifikasi (buka terminal baru):
   ```powershell
   java -version
   # Output: java version "21.x.x" ...GraalVM
   ```

---

## 2. Maven 3.9+

### Cara Install:
1. Buka: https://maven.apache.org/download.cgi
2. Download: **apache-maven-3.9.x-bin.zip**
3. Ekstrak ke `C:\Program Files\Maven\apache-maven-3.9.x`
4. Set environment variables:
   ```powershell
   [System.Environment]::SetEnvironmentVariable("MAVEN_HOME", "C:\Program Files\Maven\apache-maven-3.9.x", "Machine")
   [System.Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\Maven\apache-maven-3.9.x\bin", "Machine")
   ```
5. Verifikasi:
   ```powershell
   mvn -version
   # Output: Apache Maven 3.9.x ...
   ```

---

## 3. Docker Desktop

### Cara Install:
1. Buka: https://www.docker.com/products/docker-desktop/
2. Download **Docker Desktop for Windows**
3. Jalankan installer → ikuti wizard
4. Restart PC setelah install
5. Verifikasi:
   ```powershell
   docker -v
   # Output: Docker version 27.x.x ...
   ```

> **Catatan:** Docker Desktop membutuhkan WSL 2. Jika belum ada, Windows akan memandu instalasi otomatis.

---

## 4. Quarkus CLI (opsional)

```powershell
# Install via Scoop (jika belum ada Scoop):
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression

# Install Quarkus CLI
scoop install quarkus-cli

# Verifikasi
quarkus --version
```

---

## Setelah Semua Terinstall — Jalankan Proyek

### Backend (quarkus-theme-api):
```powershell
cd d:\Java-Syamsu\enterprise-libs\backend-libs\quarkus-theme-api

# Copy env
Copy-Item .env.example .env

# Jalankan dengan Docker (PostgreSQL otomatis ikut):
docker-compose up

# ATAU: Dev mode (butuh PostgreSQL jalan terpisah):
mvn quarkus:dev
```

### Frontend (nuxt-theme-lib):
```powershell
cd d:\Java-Syamsu\enterprise-libs\frontend-libs\nuxt-theme-lib

# Install dependencies
npm install

# Build module
npm run build
```
