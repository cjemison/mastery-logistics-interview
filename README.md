# Mastery Logistics Coding Assignment 

# Introduction

[Details for requirements](https://gist.github.com/rmw/fd88d71ca39b525ec855aa198cee3d7e)

[Reference](http://mathworld.wolfram.com/Bin-PackingProblem.html)

## Build the Code
```bash
$ make javaBuild
```


## Deploy to Docker Machine 
```bash
# Enable Docker Machine
$ brew install docker docker-compose docker-machine docker-machine-nfs
$ docker-machine create default --virtualbox-memory=4096 --virtualbox-no-share
$ docker-machine start default
$ eval $(docker-machine env default) # switch to docker context
$ make docker

# Runs on your local development docker machine on port 8280, actuator endpoints are on 8281
# ex. http://localdev:8280 # localdev is the dns for your docker machine. 
# you can add this value to /etc/hosts  ex. 192.168.99.117 localdev

# To stop the container: docker-compose stop
```

Sorting the shipment by capacity first, then sorting the truck containers by size capacity
produced the best outcome for being evenly distrubuted. 

[Sort the trucks and shipments](http://localdev:8280/v1/sortFirstFit)

[Swagger Documentation](http://localdev:8280/swagger-ui.html)