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
In regards to the generate solution class we decided it best to split it into three core components;
    - first to assign any student with a unique preference at a specific rank
    - secondly to then randomly assign a project to one of the students in the cases where there are multiple students with the same preference at a given rank
    - thirdly to randomly assign a project to any student who did not get one of their preferences
With regards to the second part we made sure to design it keeping in mind that this will have to be changed from random assigning in a later sprint.
It should be easily changeable to implement a check such as student GPA etc.
We also thought it may be useful to include a way to count how many students got what preference if any on their list.
As this would give us a nice way to generate a solution score for when we start to implement SA and GA.


