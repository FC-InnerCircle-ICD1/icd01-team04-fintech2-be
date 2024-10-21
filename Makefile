.PHONY:
# Docker Compose 실행
docker-up:
	@echo "Starting Docker Compose..."
	@docker-compose up --build
