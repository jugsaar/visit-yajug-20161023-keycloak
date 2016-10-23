#!/bin/bash

if [ $KEYCLOAK_ADMIN_USER ] && [ $KEYCLOAK_ADMIN_PASSWORD ]; then
    keycloak/bin/add-user-keycloak.sh --user $KEYCLOAK_ADMIN_USER --password $KEYCLOAK_ADMIN_PASSWORD
fi

echo "Configuring Keycloak instance with keycloak-setup.cli"

cd $JBOSS_HOME

echo "yes" | bin/jboss-cli.sh --file=./keycloak-setup.cli
echo 
echo "Configuration complete."
echo 
echo "Starting Keycloak"
java -version

exec $JBOSS_HOME/bin/standalone.sh $@ 
exit $?