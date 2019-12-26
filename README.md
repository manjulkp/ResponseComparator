# ResponseComparator


                                                      ResponseComparator 
A Comparator Tool for testing the response of requests read from two different files 


Used : Java,TestNg,Cucumber,Maven,RestAssured

CommandLine way to execute the code is -----------mvn verify

Design Pattern used :
1.Used Cucumber feature file to describe the functionality of the test case (only one test case at the moment

2.Steps Package define the under lying java glue for the feature file 

3.TestRunner converted to testng xml that help for the execution of test cases

This holds below information 
-features file and their binding step definition location 

-location to store the result in html

-activity that needs to executed based on the testng annotation

NOTE: The setup and teardown is not implemented in TestRunner


4.POM.xml stores all the dependencies that are need for the project .

5.The files are stored under /src/test/resources/properties

6.package utility; contains the below class 

-IncorrectHttpProtocolException
-ParseJson
-ReadContentFromFile
-RestAssuredHelper

ReadContentFromFile.java :
  -Read the two files 
  
  -Each line from the file is passed as a parameter to getReponse method 
  
   res1 = new RestAssuredHelper().getResponse(line1);
   
  -The json response is stored as string 
  
  -The json response of two request are compared and the output is printed based on the output .

Basically all the xpath of the json with key value pair is stored in the Map 

Then the two map are compared to print whether they are equal or not 

RestAssuredHelper.java

-Basically takes an url and return the response 

-It used RestAssured Library 

-If the uiPattern does not start with http or https then custom exception is raised ,implemented by the class 

-If the request is successful,the response is in JSON object .

Please Note :
The headers ,ContentType,StatusLine,StatusCode is not handled here while comparing (A basic comparision for json response is considered ).

ParseJson.java

-The method getAllXpathAndValueFromJsonObject is used to extract all the xpath and their values

-Returned as Map so that later the Map can be compared to decide whether the response are equal or not  



Report Location

The Report are in html and are stored under /API/target/cucumber-html-reports/overview-features.html

Feel of how the Path of Json looks like :


The Key is data.avatar-------------the value ishttps://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg

The Key is data.first_name-------------the value isJanet

The Key is data.id-------------the value is2

The Key is data.email-------------the value isjanet.weaver@reqres.in

The Key is data.last_name-------------the value isWeaver

The Key is data.avatar-------------the value ishttps://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg

The Key is data.first_name-------------the value isJanet

The Key is data.id-------------the value is2

The Key is data.email-------------the value isjanet.weaver@reqres.in

The Key is data.last_name-------------the value isWeaver

https://reqres.in/api/users/2response is equal to response of https://reqres.in/api/users/2




 



