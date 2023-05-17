#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
# shellcheck disable=SC2223
: ${HOST=localhost}
# shellcheck disable=SC2223
: ${PORT=8080}

#array to hold all our test data ids
allTestDeliveryIds=()
allTestCheckoutsIds=()
allTestProductsIds=()
allTestClientsIds=()


function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  # shellcheck disable=SC2155
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    # shellcheck disable=SC2124
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

#CREATE SOME PURCHASE ORDER TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#all use clientId c3540a89-cb47-4c96-888e-ff96708db4d8

body=\
'{
  "deliveryId":"6eb13ffa-67fb-4bf7-aa44-a07050aa036a",
  "checkoutId":"bb56bd39-0104-43f8-9a3e-624ec898d48f",
  "clientId":"c3540a89-cb47-4c96-888e-ff96708db4d8",
  "productsId":"1bc9adfd-cd19-4d6e-9b62-f0e952569141"
  "salePrice": 70500.00,
  "status":"PURCHASE_OFFER",
  "numberOfMonthlyPayments":60,
  "monthlyPaymentAmount":858.34,
  "downPaymentAmount":15000.00,
  "purchaseOfferDate":"2023-05-11"
}'
    recreatePurchaseOrderAggregate 1 "$body" "c3540a89-cb47-4c96-888e-ff96708db4d8"

#CREATE SOME INVENTORY TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
body=\
'{
  "type":"electric-vehicles"
}'
    recreateInventoryAggregate 1 "$body"

}

#USING PURCHASE ORDER TEST DATA - EXECUTE POST REQUEST
function recreatePurchaseOrderAggregate() {
    local testId=$1
    local aggregate=$2
    local clientId=$3

    #create the purchaseorder aggregates and record the generated purchaseOrderIds
    purchaseOrderId=$(curl -X POST http://$HOST:$PORT/api/v1/clients/$clientId/purchaseorders -H "Content-Type:
    application/json" --data "$aggregate" | jq '.purchaseOrderId')
    allTestPurchaseOrderIds[$testId]=$purchaseOrderId
    echo "Added PurchaseOrder Aggregate with purchaseOrderId: ${allTestPurchaseOrderIds[$testId]}"
}

#USING INVENTORY TEST DATA - EXECUTE POST REQUEST
function recreateInventoryAggregate() {
    local testId=$1
    local aggregate=$2

    #create the inventory aggregates and record the generated inventoryIds
    inventoryId=$(curl -X POST http://$HOST:$PORT/api/v1/inventories -H "Content-Type:
    application/json" --data "$aggregate" | jq '.inventoryId')
    allTestInventoryIds[$testId]=$inventoryId
    echo "Added Inventory Aggregate with inventoryId: ${allTestInventoryIds[$testId]}"
}

#don't start testing until all the microservices are up and running
function waitForService() {
    # shellcheck disable=SC2124
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

# shellcheck disable=SC2199
if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
#I've set up an inventory with no vehicles in it
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/inventories/2c865c34-c30a-456d-af13-257869a2a715


setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
## Verify that a normal get by id of earlier posted purchase order works
echo -e "\nTest 1: Verify that a normal get by id of earlier posted purchase order works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/clients/c3540a89-cb47-4c96-888e-ff96708db4d8/purchaseorders/${allTestPurchaseOrderIds[1]} -s"
assertEqual ${allTestPurchaseOrderIds[1]} $(echo $RESPONSE | jq .purchaseOrderId)
assertEqual "\"Alick\"" $(echo $RESPONSE | jq ".clientFirstName")
#
#
##verify that a get all inventories works
echo -e "\nTest 2: Verify that a get all inventories works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/inventories -s"
assertEqual 4 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted inventory works
echo -e "\nTest 3: Verify that a normal get by id of earlier posted inventory works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/inventories/${allTestInventoryIds[1]} -s"
assertEqual ${allTestInventoryIds[1]} $(echo $RESPONSE | jq .inventoryId)
assertEqual "\"electric-vehicles\"" $(echo $RESPONSE | jq ".type")
#
#
## Verify that an update of an earlier posted inventory works - put at api-gateway has no response body
echo -e "\nTest 4: Verify that an update of an earlier posted inventory works"
body=\
'{
  "type":"big-vehicles"
}'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/inventories/${allTestInventoryIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#verify doing a get by id on the updated inventory
assertCurl 200 "curl http://$HOST:$PORT/api/v1/inventories/${allTestInventoryIds[1]} -s"
# shellcheck disable=SC2046
assertEqual ${allTestInventoryIds[1]} $(echo $RESPONSE | jq .inventoryId)
# shellcheck disable=SC2046
assertEqual "\"big-vehicles\"" $(echo $RESPONSE | jq ".type")
#
#
# Verify that a 404 (Not Found) error is returned for a non existing clientId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get purchase order request with a non existing clientId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/clients/c3540a89-cb47-4c96-888e-ff96708db4d7/purchaseorders/${allTestPurchaseOrderIds[1]} -s"
#
# Verify that a 404 (Not Found) error is returned for a non existing purchaseId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 6: Verify that a 404 (Not Found) error is returned for a get purchase order request with a non existing purchaseOrderId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/clients/c3540a89-cb47-4c96-888e-ff96708db4d8/purchaseorders/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"
#
# Verify that a 422 (Unprocessable Entity) error is returned for a an InventoryId that is too short
echo -e "\nTest 7: Verify that a 422 (Unprocessable Entity) error is returned for an inventoryId that is too short"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/inventories/d846a5a7-2e1c-4c79-809c-4f3f471e826 -s"
#
#
##Verify that a DELETE inventory request works for an existing inventoryId - this inventory has no vehicles in it
echo -e"\nTest 8: Verify that a delete request works with a valid inventoryId that contains no vehicles"
assertCurl 204 "curl -X DELETE http://localhost:8080/api/v1/inventories/${allTestInventoryIds[1]} -s"



# shellcheck disable=SC2199
if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi