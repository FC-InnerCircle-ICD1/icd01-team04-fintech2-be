.PHONY: clean build run logs

run: build
	@echo "Docker Compose로 컨테이너 실행"
	docker-compose up -d

build: stop
	@echo "모듈별 JAR 파일 빌드 및 Docker 이미지 생성"
	./gradlew :incer-payment:bootJar -x test
	./gradlew :incer-member:bootJar -x test
	./gradlew :incer-celler:bootJar -x test
	./gradlew :incer-paygate:bootJar -x test
	docker build -t incer-payment:0.0.1-SNAPSHOT ./incer-payment
	docker build -t incer-member:0.0.1-SNAPSHOT ./incer-member
	docker build -t incer-celler:0.0.1-SNAPSHOT ./incer-celler
	docker build -t incer-paygate:0.0.1-SNAPSHOT ./incer-paygate

# 모든 서비스 중지
stop:
	@echo "Docker Compose 서비스 중지"
	docker-compose down

# 모든 로그 확인
logs:
	@echo "실행 중인 서비스 로그"
	docker-compose logs -f

