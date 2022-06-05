# api-project

# Compilation
mvn -X clean install

Each method can be executed in IDE with TestNg/Utest individually.
Approach:My approach is to separate configuration of requests, responses, and tests. This way each section can be developed (extended) separately, regardless of current amount of running tests. 
Pros: Each section define type, headers, body and uri while test been written separately. 
Cons: Current configuration of tests is partially hardcoded and required to create dataset or connection with DB. Also needed added flexibility with execution agains different environments. 
