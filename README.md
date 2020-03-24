# Etldemo
Demo ETL build whit Spring Batch.

Spring Batch version 4.2.1

Example base on https://spring.io/guides/gs/batch-processing/

### Summary:
Load a CSV file with one million+ of records into database, it took 1.25 min in a local data base.
if we need go trough network decrease CHUNK_SIZE variable would be a good ide, if you do so, it will take a bit longer.

#### get project:
###### Inteli IDE
`./gradlew ideaProject` 

###### Eclipse
`./gradlew eclipseProject` 

#### Compile:
`./gradlew build`

#### Exec:
`java -jar build/libs/demo-0.0.1-SNAPSHOT.jar` 

