#!/bin/bash

echo "shutting down docker instances..."

docker stop eagles_postgres_local
docker stop eagles_app_local
