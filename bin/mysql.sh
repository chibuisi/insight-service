#!/usr/bin/env bash

docker-compose run -T --rm --entrypoint 'mysql' mysql --user=root --host=minor-insights-mysql "$@"