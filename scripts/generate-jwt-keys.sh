#!/bin/bash

# Generate RSA keys for JWT signing

# Generate private key
echo "Generating RSA private key..."
openssl genrsa -out privateKey.pem 2048

# Generate public key
echo "Generating RSA public key..."
openssl rsa -in privateKey.pem -pubout -out publicKey.pem

# Move keys to resources
echo "Moving keys to resources directory..."
mv -f privateKey.pem gateway/src/main/resources/
mv -f publicKey.pem gateway/src/main/resources/

echo "Keys generated successfully!"
echo "Private key: gateway/src/main/resources/privateKey.pem"
echo "Public key: gateway/src/main/resources/publicKey.pem"
