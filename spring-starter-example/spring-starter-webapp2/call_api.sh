#!/bin/bash

# Script to call APIs exposed by webapp2

# Base URL for the API
BASE_URL="http://localhost:8082/api"

# Function to make API calls and display results
call_api() {
    echo "Calling: $1"
    response=$(curl -s "$1")
    echo "Response: $response"
    echo "please check webapp logs(メソッド実行前後のログ出力)"
    echo "----------------------------------------"
}

# Call the hello API
call_api "${BASE_URL}/hello"

# Call the hello API with a name parameter
call_api "${BASE_URL}/hello/User"

# Call the calculate API
call_api "${BASE_URL}/calculate/10/20"

echo "All API calls completed."