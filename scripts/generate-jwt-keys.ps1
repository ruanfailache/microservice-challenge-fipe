# Generate RSA keys for JWT signing

# Generate private key
Write-Host "Generating RSA private key..."
openssl genrsa -out privateKey.pem 2048

# Generate public key
Write-Host "Generating RSA public key..."
openssl rsa -in privateKey.pem -pubout -out publicKey.pem

# Move keys to resources
Write-Host "Moving keys to resources directory..."
Move-Item -Force privateKey.pem gateway/src/main/resources/
Move-Item -Force publicKey.pem gateway/src/main/resources/

Write-Host "Keys generated successfully!"
Write-Host "Private key: gateway/src/main/resources/privateKey.pem"
Write-Host "Public key: gateway/src/main/resources/publicKey.pem"
