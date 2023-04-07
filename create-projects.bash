#!/usr/bin/env bash

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=api-gateway \
--package-name=com.onlineperfumeshop.apigateway \
--groupId=com.onlineperfumeshop.apigateway \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
api-gateway


spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=clients-service \
--package-name=com.onlineperfumeshop.clientsservice \
--groupId=com.onlineperfumeshop.clientsservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
clients-service 


spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=delivery-service \
--package-name=com.onlineperfumeshop.deliveryservice \
--groupId=com.onlineperfumeshop.deliveryservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
delivery-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=products-service \
--package-name=com.onlineperfumeshop.productsservice \
--groupId=com.onlineperfumeshop.productsservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
products-service

spring init \
--boot-version=3.0.2 \
--build=gradle \
--type=gradle-project \
--java-version=17 \
--packaging=jar \
--name=checkout-service \
--package-name=com.onlineperfumeshop.checkoutservice \
--groupId=com.onlineperfumeshop.checkoutservice \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
checkout-service


