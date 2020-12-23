**Spring Mongodb**

In this project, CRUD operation is performed on the domain model
<a href="src/main/java/com/github/mitrakumarsujan/springmongodb/model/Student.java">Student</a>
with MongoDB as its database engine.<br/>

Database is manipulated in three different ways.
<ul>
<li>
Using MongoDB's java driver with Documents
</li>
<li>
Using MongoDB's java driver with PojoCodecs
</li>
<li>
Using Spring data MongoTemplate (personally recommended)
</li>
<li>
Using Spring Data MongoRepository.
</li>
</ul>
Clone the project and see the <a href="src/main/java/com/github/mitrakumarsujan/springmongodb/dao">DAO</a> layer

To Run:
1. Place your mongodb database coordinates in <a href="src/main/resources/application-dev.yml">application-dev.yml</a>
2. Run the main class

To Test:
1. Place your mongodb test database coordinates in <a href="src/test/resources/application-test.yml">application-test.yml</a>
2. Run all tests