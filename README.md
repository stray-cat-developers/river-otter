# river-otter
river-otter is an application that can manage holidays and business days for each country.

[![Build Status](https://travis-ci.org/fennec-fox/river-otter.svg?branch=master)](https://travis-ci.org/fennec-fox/river-otter)

# New Features!
* Supports Korean and US holidays search.
* Create a customized holiday calendar for each topic.
* Calculate the business days.
* Convert the solar and lunar calendars.

# Installation
### Quick start
Java, Docker must be installed before starting.
Standalone system using embedded mongo database.

```sh
git clone https://github.com/fennec-fox/river-otter.git
./quick-start.sh
```

Swagger api page is http://localhost:6400/swagger-ui.html

### Customize issue
* Copy application-default.yml and make the appropriate settings.

# How to use
*  run ./quick-start.sh
*  http://localhost:6400/swagger-ui.html
*  crawling country holiday
    * use api
        ```js
        curl -X POST "http://localhost:6400/calendar/holiday/crawling" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"year\": 2020}"  
        ```
    * Public holidays in the United States and Korea collected.

## Public holiday calendar
*  find all korean holiday    
    ```js
    curl -X GET "http://localhost:6400/calendar/holiday/country/KR/year/2020" -H "accept: */*"
    ```
*  find all united States holiday    
    ```js
    curl -X GET "http://localhost:6400/calendar/holiday/country/US/year/2020" -H "accept: */*"
    ```   
## Topic holiday calendar
*  Add topic 
   ```js 
   curl -X POST "http://localhost:6400/topics" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Test Topic\"}"     
   ```
*  Add topic holiday or working day
    ```js
    curl -X POST "http://localhost:6400/topics/5f7c3c712a1f945eeb736fdb/holidays/KR" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"date\": \"2020-01-02\", \"description\": \"Party day!\", \"name\": \"Free day\"}"
    ```   
*  Find topic calendar
    ```js
    curl -X GET "http://localhost:6400/topics/5f7c3c712a1f945eeb736fdb/calendar/country/KR/year/2020/month/1" -H "accept: */*"
    ``` 

# Document
[Korean Guide](https://github.com/fennec-fox/river-otter/wiki/Korean-Guide)

# Note

To do the api test in river-otter, you need to do the following:
1. The api key registered in application-default.yml must be changed and used.
2. In order to register Korean holidays, you need to obtain a key from the public data portal( [Holiday calendar](https://www.data.go.kr/data/15012690/openapi.do) ).
3. Holiday information for each country can be found at https://app.abstractapi.com. 
