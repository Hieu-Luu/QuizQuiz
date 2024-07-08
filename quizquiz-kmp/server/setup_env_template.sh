#!/bin/zsh

# Export environment variables
export KTOR_ENV="your-environment"
export KTOR_PORT="your-port"

# Generate JWT Secret
SECRET=$(openssl rand -base64 32)
export JWT_SECRET="$SECRET"
export JWT_ISSUER="http://0.0.0.0:8080/"
export JWT_AUDIENCE="http://0.0.0.0:8080/"
export JWT_REALM="Access to the '/' path"

# Postgresql database configuration
export STORAGE_DRIVER="org.postgresql.Driver"
export JDBC_URL="jdbc:postgresql://localhost:5432/your_database"
export JDBC_USER="your-user"
export JDBC_PWD="your-password"

# MongoDB database configuration
export MONGO_USER="your-user"
export MONGO_PWD="your-password"
export MONGO_AUTH_MECHANISM="SCRAM-SHA-1"
export MONGO_HOST="127.0.0.1"
export MONGO_PORT="27017"
export MONGO_DB="your-database"
export MONGO_ATLAS_HOST="your-host"
export MONGO_ATLAS_USER="your-user"
export MONGO_ATLAS_PWD="your-password"
export MONGO_ATLAS_DB="your-database"

# S3 configuration
export AWS_S3_BUCKET="your-s3-bucket"
export AWS_S3_REGION="us-east-1"
export AWS_S3_ACCESS_KEY="your-s3-access-key"
export AWS_S3_SECRET_KEY="your-s3-secret-key"

# SSL configuration
export SSL_KEY_STORE="your-key-store-path"
export SSL_KEY_STORE_PWD="your-key-store-password"
export SSL_KEY_ALIAS="your-key-alias"
export SSL_KEY_PWD="your-key-password"

# Function to execute commands for macOS
setup_macos() {
  # Remove existing environment variables from .zshrc
  sed -i '' '/^export KTOR_ENV=/d' ~/.zshrc
  sed -i '' '/^export KTOR_PORT=/d' ~/.zshrc

  # Remove existing JWT_SECRET from .zshrc
  sed -i '' '/^export JWT_SECRET=/d' ~/.zshrc
  sed -i '' '/^export JWT_ISSUER=/d' ~/.zshrc
  sed -i '' '/^export JWT_AUDIENCE=/d' ~/.zshrc
  sed -i '' '/^export JWT_REALM=/d' ~/.zshrc

  sed -i '' '/^export STORAGE_DRIVER=/d' ~/.zshrc
  sed -i '' '/^export JDBC_URL=/d' ~/.zshrc
  sed -i '' '/^export JDBC_USER=/d' ~/.zshrc
  sed -i '' '/^export JDBC_PWD=/d' ~/.zshrc

  sed -i '' '/^export MONGO_USER=/d' ~/.zshrc
  sed -i '' '/^export MONGO_PWD=/d' ~/.zshrc
  sed -i '' '/^export MONGO_AUTH_MECHANISM=/d' ~/.zshrc
  sed -i '' '/^export MONGO_HOST=/d' ~/.zshrc
  sed -i '' '/^export MONGO_PORT=/d' ~/.zshrc
  sed -i '' '/^export MONGO_DB=/d' ~/.zshrc
  sed -i '' '/^export MONGO_ATLAS_HOST=/d' ~/.zshrc
  sed -i '' '/^export MONGO_ATLAS_USER=/d' ~/.zshrc
  sed -i '' '/^export MONGO_ATLAS_PWD=/d' ~/.zshrc
  sed -i '' '/^export MONGO_ATLAS_DB=/d' ~/.zshrc

  sed -i '' '/^export AWS_S3_BUCKET=/d' ~/.zshrc
  sed -i '' '/^export AWS_S3_REGION=/d' ~/.zshrc
  sed -i '' '/^export AWS_S3_ACCESS_KEY=/d' ~/.zshrc
  sed -i '' '/^export AWS_S3_SECRET_KEY=/d' ~/.zshrc

  sed -i '' '/^export SSL_KEY_STORE=/d' ~/.zshrc
  sed -i '' '/^export SSL_KEY_STORE_PWD=/d' ~/.zshrc
  sed -i '' '/^export SSL_KEY_ALIAS=/d' ~/.zshrc
  sed -i '' '/^export SSL_KEY_PWD=/d' ~/.zshrc

  # Append to .zshrc to make variables persist across shell sessions
  {
    echo "export KTOR_ENV=\"${KTOR_ENV}\"";
    echo "export KTOR_PORT=\"${KTOR_PORT}\"";

    # Append Jwt variables
    echo "export JWT_SECRET=\"${JWT_SECRET}\"";
    echo "export JWT_ISSUER=\"${JWT_ISSUER}\"";
    echo "export JWT_AUDIENCE=\"${JWT_AUDIENCE}\"";
    echo "export JWT_REALM=\"${JWT_REALM}\"";

    # Append Postgresql variables
    echo "export STORAGE_DRIVER=\"${STORAGE_DRIVER}\"";
    echo "export JDBC_URL=\"${JDBC_URL}\"";
    echo "export JDBC_USER=\"${JDBC_USER}\"";
    echo "export JDBC_PWD=\"${JDBC_PWD}\"";

    # Append MongoDB variables
    echo "export MONGO_USER=\"${MONGO_USER}\"";
    echo "export MONGO_PWD=\"${MONGO_PWD}\"";
    echo "export MONGO_AUTH_MECHANISM=\"${MONGO_AUTH_MECHANISM}\"";
    echo "export MONGO_HOST=\"${MONGO_HOST}\"";
    echo "export MONGO_PORT=\"${MONGO_PORT}\"";
    echo "export MONGO_DB=\"${MONGO_DB}\"";
    echo "export MONGO_ATLAS_HOST=\"${MONGO_ATLAS_HOST}\"";
    echo "export MONGO_ATLAS_USER=\"${MONGO_ATLAS_USER}\"";
    echo "export MONGO_ATLAS_PWD=\"${MONGO_ATLAS_PWD}\"";
    echo "export MONGO_ATLAS_DB=\"${MONGO_ATLAS_DB}\"";

    # Append AWS variables
    echo "export AWS_S3_BUCKET=\"${AWS_S3_BUCKET}\"";
    echo "export AWS_S3_REGION=\"${AWS_S3_REGION}\"";
    echo "export AWS_S3_ACCESS_KEY=\"${AWS_S3_ACCESS_KEY}\"";
    echo "export AWS_S3_SECRET_KEY=\"${AWS_S3_SECRET_KEY}\"";

    # Append SSL variables
    echo "export SSL_KEY_STORE=\"${SSL_KEY_STORE}\"";
    echo "export SSL_KEY_STORE_PWD=\"${SSL_KEY_STORE_PWD}\"";
    echo "export SSL_KEY_ALIAS=\"${SSL_KEY_ALIAS}\"";
    echo "export SSL_KEY_PWD=\"${SSL_KEY_PWD}\"";
  } >> ~/.zshrc

  # Reload .zshrc to apply changes
  # shellcheck disable=SC1090
  source ~/.zshrc

  echo "Environment variables are set and .zshrc reloaded."
}

# Function to execute commands for Linux
setup_linux() {
  # Remove existing environment variables from .bashrc
  sed -i '/^export KTOR_ENV=/d' ~/.bashrc
  sed -i '/^export JWT_SECRET=/d' ~/.bashrc
  sed -i '/^export JWT_ISSUER=/d' ~/.bashrc
  sed -i '/^export JWT_AUDIENCE=/d' ~/.bashrc
  sed -i '/^export JWT_REALM=/d' ~/.bashrc

  # Append new environment variables to .bashrc
  {
    echo "export KTOR_ENV=\"${KTOR_ENV}\""
    echo "export JWT_SECRET=\"${JWT_SECRET}\""
    echo "export JWT_ISSUER=\"${JWT_ISSUER}\""
    echo "export JWT_AUDIENCE=\"${JWT_AUDIENCE}\""
    echo "export JWT_REALM=\"${JWT_REALM}\""
  } >> ~/.bashrc

  # Reload .bashrc to apply changes
  # shellcheck disable=SC1090
  source ~/.bashrc

  echo "Environment variables are set and .bashrc reloaded."
}

# Check the operating system and execute the corresponding commands
case "$OSTYPE" in
  darwin*)
    setup_macos
    ;;
  linux*)
    setup_linux
    ;;
  *)
    echo "Unsupported OS: $OSTYPE"
    ;;
esac