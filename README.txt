TEAM NAME: Rona Season
CONTRIBUTORS: Sean Stewart, Oscar Byrne Carty, Caolán Power


HOW TO RUN:
-Select which dataset is to be used when creating the students and staff Lists and projects Hashmap( Staff&Projects(60/120/240/500).xlsx and Students&Preferences(60/120/240/500).xlsx )
-Run the GenerateSolution class

APPROACH:
We wanted to create two classes one to represent a solution object and the other to generate and store a list of these solution objects.
We decided that a solution should represent a student and project object.
We also included a number of public methods that may be useful later in the project.
The methods will allow us to:
    - get the student name and number
    - get the project title
    - get the supervisor overseeing the project
    - get the preference of the project assigned
In regards to the generate solution class we decided it best to split it into three core components;
    - first to assign any student with a unique preference at a specific rank
    - secondly to then randomly assign a project to one of the students in the cases where there are multiple students with the same preference at a given rank
    - thirdly to randomly assign a project to any student who did not get one of their preferences
With regards to the second part we made sure to design it keeping in mind that this will have to be changed from random assigning in a later sprint.
It should be easily changeable to implement a check such as student GPA etc.
We also thought it may be useful to include a way to count how many students got what preference if any on their list.
As this would give us a nice way to generate a solution score for when we start to implement SA and GA.

THE METHODS:
genSolution()
-This method brings all the below methods together to generate a solution. It assigns the students and staff Lists and
-the projects Hashmap and then goes through each preference row, using the assignUnique and randomlyAssign methods
-to generate solutions for students who got projects on their preferences. It then iterates through the students List,
-checking if there are any students who have not been given a project and randomly assigns them a project.

randomlyAssign(List<Student> students, int preference)
-This method takes in a preference as an integer and goes through a row of each students preference, checking if
-there are any duplicate preferences on the row. If it finds any it adds them to a temporary List. A number between
-0 and the size of the List is randomly selected and the element which is in that position on the List is selected
-to have the project. A solution is then created of the randomly selected student along with the project.

boolean checkForOthers(List<Student> students, int pref, int n, String project)
-This method checks if there are any other preferences on the row that are the same, if it finds one it
-returns true, else it returns false.

Project giveRandomProject()
-This method tests if the project that has been generated is already taken. If it has not been taken then it sets the
-project as taken as a solution is about to be created which uses that project.

Project genProject()
-This method randomly generates a number, which is then used to select a project.

assignUnique(List<Student> students, HashMap<String, Project> projects, int preference)
-This method takes in a preference as an integer and goes through a row of each students preference, checking if
-there are any preferences on the row that are unique to that row. If it finds a unique preference, it creates
-a solution which consists of the student and the project which was unique to that row.

