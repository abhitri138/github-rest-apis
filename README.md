The rest client is implemented using Jersey-client library in java.

File [RestUtils.java](src/main/java/edu/ncsu/se/utils/RestUtils.java) contains generic code for making get, post and patch calls

File [App.java](src/main/java/edu/ncsu/se/app/App.java) contains specific functions corresponding to each task that make use of RestUtils's functions

Task and corresponding Methods (In src/main/java/edu/ncsu/se/app/App.java) :

1.1 -> listBranches</br>
1.2 -> createNewRepo</br>
1.3 -> createIssue</br>
1.4 -> editRepo</br>
1.5 -> addAssignee</br>
1.6 -> getReactionsOfIssue</br>


Instructions to build :
- clone the project
    - In Command line, install maven and then run mvn clean install
    - In any IDE
        - menu bar -> file -> import maven project( or import from existing sources and choose maven) -> choose the project folder and source maven
        - When the project opens up in the IDE, goto Menu bar - > build -> click on build

Instructions to run :
- create a file (.properties) with properties :
    1. user.token="TOKEN"
    2. user.name="username"
    3. github.base="base api url" // optional, default api.github.com

  From Terminal
- run "java -cp <jar path> edu.ncsu.se.app.App.Main"
  From IDE
- Run the edu.ncsu.se.app.App.Main.java class

The application will provide following options  :

1 List all branches of an existing repo</br>
2 Create a new repo</br>
3 Create an issue for an existing repo</br>
4 Add an assignee to an existing issue</br>
5 Edit a repo to disable issues</br>
6 List reactions for an issue</br>
7 Exit</br>

choose an option and the app will ask for inputs as needed and will return the response.
