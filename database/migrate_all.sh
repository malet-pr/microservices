#!/bin/bash

# Path to Flyway executable
FLYWAY_CMD="/home/nuria/Projects/flyway-10.19.0/flyway"

# Run migrations for each schema
$FLYWAY_CMD -configFiles=/home/nuria/Projects/MICROSERVICES/microservices/database/flyway_orders.conf migrate
$FLYWAY_CMD -configFiles=/home/nuria/Projects/MICROSERVICES/microservices/database/flyway_rules.conf migrate
