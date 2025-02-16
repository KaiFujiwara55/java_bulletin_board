FROM openjdk:11-slim

RUN apt-get update
RUN mkdir -p /usr/app/src /usr/app/lib

COPY ./lib/* /usr/app/lib/ 

WORKDIR /usr/app/src
