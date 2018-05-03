# Station Finder

## Functionality

* Persist station (implicit insert/update)
* Search by id or zipcode or latitude

## Get started
```
./gradlew clean bootrun
```

## Example request: Add/Update a station
```
curl -XPOST -H "Content-Type:application/json" \
-d '{"id":"id", "zipcode":"zipcode","latitude":"1","longitude":"1"}' \
http://localhost:8080/stations 
```

## Example request: 
### find all stations
```
curl http://localhost:8080/stations/search
```
### find by id
```
curl http://localhost:8080/stations/search?id=id
```

### find by zipcode
```
curl http://localhost:8080/stations/search?zipcode=12345
```

### find by geo location
```
curl http://localhost:8080/stations/search?latitude=1.234&longitude=3.123&airDistanceInMeter=100
```