# Code and Slides from YaJUG talk in Luxembourg Oct 2016

[Identity Management with Keycloak Oct. 2016](https://github.com/jugsaar/visit-yajug-20161023-keycloak/blob/master/OSS_SSO_IDM_KEYCLOAK_V4.pdf)

# Simple demo environment for a clustered Keycloak setup

[Simple Docker based idm-system](https://github.com/jugsaar/visit-yajug-20161023-keycloak/tree/master/idm-system)

## Docker compose

The composed system can be started via
```
docker-compose up
```

To shut the system down, just execute 
```
docker-compose down
```

## Keycloak

### Build tdlabs/keycloak image  

```
cd keycloak
./build.sh
```

### Keycloak admin-console

URL: http://localhost:8080/u/auth  
Username: admin  
Password: admin  

### Docker-Demo Realm account login

URL: http://localhost:8080/u/auth/realms/docker-demo/account  
Username: tester  
Password: test  


## Graylog

### Setup Graylog

* Create input  
Type: GELF UDP  
Name: idm demo gelf  
Global: yes  
Port: 12123  

* Create Stream  
Name: idm demo Stream  
Descript: idm log messages  

* Add Stream Rule  
Field: SystemGroup  
Operator: ExactMatch  
Value: idm  

* Start stream

* Create demo Username  
User: demo  
Password: demo123  
Timezone: Berlin  

### Graylog admin-console

URL: http://localhost:9000  
Username: admin  
Password: admin  

## ActiveMQ

### ActiveMQ admin-console

URL: http://localhost:8161/admin  
Username: admin  
Password: admin  

## HAProxy

### HA Proxy stats

URL: http://localhost:1936/  
Username: stats  
Password: stats  
