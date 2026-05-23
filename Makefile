.PHONY: build-all test-all docker-build-all clean

build-all:
	mvn package -DskipTests
	npm run build:frontend

test-all:
	mvn test
	npm run test:frontend

docker-build-all:
	docker build -t enterprise/quarkus-theme-api:latest backend-libs/quarkus-theme-api
	docker build -t enterprise/nuxt-theme-lib:latest frontend-libs/nuxt-theme-lib

clean:
	mvn clean
	find frontend-libs -name "node_modules" -type d -exec rm -rf {} + 2>/dev/null; true
	find frontend-libs -name "dist" -type d -exec rm -rf {} + 2>/dev/null; true

release:
	@echo "Bumping version to $(VERSION)"
	mvn versions:set -DnewVersion=$(VERSION)
