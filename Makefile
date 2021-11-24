.DEFAULT_GOAL:=help

# --------------------------
include .env
export
# --------------------------

.PHONY: build up

build: #📦 Build docker image of lol application
	DOCKER_BUILDKIT=1 docker build -t lol .

up: #🚀 Deploy containers
	docker-compose up -d

down: #🛬 turn off containers
	docker-compose down

help:
	@echo "Special Parakeet project"
	@echo "---------------------"
	@echo "Follow the next steps:"
	@echo "make build 📦"
	@echo "make up 🚀"
	@echo "make down 🛬"
	@echo "---------------------"
