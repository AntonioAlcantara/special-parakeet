# LoL

## Requirements

- Java 15
- [Docker 17.05 or higher](https://docs.docker.com/install/)
- [Docker-Compose 3 or higher](https://docs.docker.com/compose/install/)
- 2GB RAM (For Windows and MacOS make sure Docker's VM has more than 2GB+ memory.)

## Setup

1. Clone the repository

```bash
git clone https://github.com/AntonioAlcantara/special-parakeet.git
```

2. Build the project

```bash
$ make build
```

3. Raise the project

```bash
$ make up
```

4. Visit `Swagger Api documentation` at [http://localhost:8085/swagger-ui/](http://localhost:8085/swagger-ui/)

### Remember to login using the authorize button:

#### Use the following credentials into the basic authorization:

    user: taric
    password: test