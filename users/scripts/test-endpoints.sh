#!/bin/bash
#
# Calls some endpoints via cURL
#
# 02.01.2020, Achim Wiedemann
#

CURL_CMD="curl"
BASEL_URL=http://localhost:8080/workers

echo "Add item via POST"
$CURL_CMD -X POST -d '{ "name": "John Doe" }' -H "Content-Type: application/json" http://localhost:8080/workers
echo

echo "Add item via PUT"
$CURL_CMD -X PUT -d '{ "name": "Jane Doe" }' -H "Content-Type: application/json" http://localhost:8080/workers/f65c0972-0000-0000-0000-75b6ed7cb2b4
echo

echo "Get all items"
$CURL_CMD http://localhost:8080/workers
echo

echo "Update item via PUT"
$CURL_CMD -X PUT -d '{ "id": "f65c0972-0000-0000-0000-75b6ed7cb2b4", "name": "Jane Doe" }' -H "Content-Type: application/json" http://localhost:8080/workers/f65c0972-0000-0000-0000-75b6ed7cb2b4
echo

echo "Get one item"
$CURL_CMD http://localhost:8080/workers/f65c0972-0000-0000-0000-75b6ed7cb2b4
echo

# echo "Delete item"
# $CURL_CMD -X DELETE http://localhost:8080/workers/f65c0972-0000-0000-0000-75b6ed7cb2b4
# echo
#
# echo "Get deleted item"
# $CURL_CMD http://localhost:8080/workers/f65c0972-0000-0000-0000-75b6ed7cb2b4
# echo
