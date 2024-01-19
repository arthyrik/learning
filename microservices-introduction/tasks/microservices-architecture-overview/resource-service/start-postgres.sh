#!/usr/bin/env bash

set -euo pipefail

mkdir -p "$HOME"/.local/docker/postgresql

docker run --rm --name pg-docker2 -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=resource-service -d -p 5431:5432 -e PGDATA=/var/lib/postgresql/data/pgdata -v "$HOME"/.local/docker/postgresql/data2:/var/lib/postgresql/data postgres