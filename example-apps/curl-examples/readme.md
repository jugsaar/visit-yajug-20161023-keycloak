# Some examples for working with Keycloak via CURL

```
KC_REALM=demo
KC_USERNAME=tester
KC_PASSWORD=test
KC_CLIENT=demo-petclinic
KC_CLIENT_SECRET=07f0b8d8-bfe4-44ce-93c4-9496f2d3e63c
KC_SERVER=login.acme.local:8081
KC_CONTEXT=auth
```

# Retrieve Tokens with credentials
```
KC_RESPONSE=$( \ 
   curl -k -v -X POST \
        -H "Content-Type: application/x-www-form-urlencoded" \
        -d "username=$KC_USERNAME" \
        -d "password=$KC_PASSWORD" \
        -d 'grant_type=password' \
        -d "client_id=$KC_CLIENT" \
        -d "client_secret=$KC_CLIENT_SECRET" \
        "http://$KC_SERVER/$KC_CONTEXT/realms/$KC_REALM/protocol/openid-connect/token" \
    | jq . 
)

KC_ACCESS_TOKEN=$(echo $KC_RESPONSE| jq -r .access_token)
KC_ID_TOKEN=$(echo $KC_RESPONSE| jq -r .id_token)
KC_REFRESH_TOKEN=$(echo $KC_RESPONSE| jq -r .refresh_token)
```

# Show all keycloak env variables
```
set | grep -a "KC_*"
```

# Introspect Keycloak Request Token
```
curl -k -v \
     -X POST \
     -u "$KC_CLIENT:$KC_CLIENT_SECRET" \
     -d "token=$KC_ACCESS_TOKEN" \
   "http://$KC_SERVER/$KC_CONTEXT/realms/$KC_REALM/protocol/openid-connect/token/introspect" | jq .
```

# Access Keycloak User Info
```
curl -k -v \
     -X POST \
     -H "Content-Type: application/x-www-form-urlencoded" \
     -d "access_token=$KC_ACCESS_TOKEN" \
   "http://$KC_SERVER/$KC_CONTEXT/realms/$KC_REALM/protocol/openid-connect/userinfo" | jq .
```

# One liner to decode JWT in the shell
```
echo -n $KC_ACCESS_TOKEN | cut -d "." -f 2 | base64 -d | jq .
```