TEAM NAME: Rona Season
CONTRIBUTORS: Sean Stewart, Oscar Byrne Carty, Caolán Power

SPRINT 5:

HOW TO RUN:
-Select which data set is to be used when creating the students and staff Lists and projects Hashmap( Staff&Projects(60/120/240/500).xlsx and Students&Preferences(60/120/240/500).xlsx ) in the GenerateSolution class
-If you wish to find a solution using Hill Climbing, run the HillClimbing class. If you wish to adjust how many times the Hill Climbing is performed, decrease/increase the number in the loop in main.
-If you wish to find a solution using Simulated Annealing, run the Simulated Annealing class
-If you wish to test the methods, run the TestSuite class
-There is a solve class which can run the Simulated Annealing class and which will be used to run the Genetic Algorithm in future sprints

APPROACH:
We created a Solver interface to allow plug-in compatibility in the future.
We then started off by creating a hill climbing class that will make a change to a solution and accept it
if the overall energy score is lower than the original. We decided it best to use GPA and preference score to indicate who if changed would be most likely to produce an overall better
score.
We the moved onto the SimulatedAnnealing class which we implement the solver interface in. We started off by creating a call to the hill climbing and making sure that the acceptance was
working correctly. Then we worked on introducing the boltzmann distribution to accept worse solution on our path for finding a better solution.
We then began to introduce a cooling schedule. We had to do a bit of experimentation with values for cooling but we made what we feel is quite a good solution that adjusts based on the
size of the data set being operated on and takes into account how many changes are rejected in a row and increases cooling when this occurs.

METHODS ADDED:
acceptance
-We added two acceptance classes. One in the HillClimbing class which returns the solution with the better score out of the
-two solutions inputted and another in the SimulatedAnnealing class which works almost exactly the same way except it takes
-the Boltzmann distribution into account

change(List<Solution> solutions)
-We added a new change class to HillClimbing as the original change class in ScoringFunctions wasn't giving us the results
-that we were looking for. This change class looks at which students are needed to be given and takes their GPA and
-preferences into account. The class only changes one student at a time.

simulatedAnnealing()
-This class performs the Simulated Annealing approach to getting the best set of solutions. The temperature variable is
-based of how many solutions there are in the list and is decreased each time, with the amount that it is decreased by
-dependant on if there have been any changes to the set of solutions and if no changes have been made, the size of the
-list is taken into account. When making changes, Boltzmann distribution is also taken into account.

boltzmann(double temp, double energyOne, double energyTwo)
-This class returns the number produced by the Boltzmann formula, taking the temperature and energy into consideration.

findStudentByProject(List<Solution> solutions, String project)
-This method takes a list of solutions and a String as input. It searches through the solutions and if a student has
-the project assigned to them, the student is returned.

findSolNumberByStudent(List<Solution> solutions, Student student)
-This method takes a list of solutions and a Student as input. It searches through the solutions and if the list contains
-that Student, the number at which the student is stored in the list is returned.

findProjectByTitle(List<Solution> solutions, String project)
-This method takes a list of solutions and a String as input. It searches through the solutions and if one of the solutions
-contains a project of that name, the project is returned.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
SPRINT 4:

APPROACH:
We wanted created a class that would be able to make a random change to our generated solutions and be able to give the solutions a score by adding penalties for any undesirable properties in the change.
To do this we decided first start with a change function:
    -We wanted to create a change function which would completely randomize which students got which project, only taking students stream into account along
     with their preferences. In our GenerateSolution class GPA, preferences and stream are the three factors that are taken into account when choosing our
     solutions and we decided to introduce a more randomized way of changing the solutions. The method is designed to ensure that almost all students will
     be assigned a different project from what they were originally assigned in the generateSolution method.
Then we addressed the evaluation of this new randomized solution by adding penalties.
We decided that to create penalty function for any students with duplicate projects and another function to penalise any solution where there was a stream mismatch between
the student and project.
We gave a harsh penalty of 100 in the case either of these occurrences. We decides to give such a harsh penalty in order to help prevent the change being accepted
as they are in violation of hard constraints.
We also added in a function to analyse the solution and give some statistical information such as the percentage of student who receive their top preference and the
number of students who got one of their top five preferences.
We also decided to add a method for allowing user defined GPA importance. It is not currently in use but we thought that it may be beneficial for implement it now for
use in later sprints when we start building out the gui.

METHODS ADDED:
assignByGPA(List<Student> students, int preference)
-This method takes in a preference as an integer and goes through a row of each students preference, checking if there are any
-duplicate preferences on the row. If it finds any it adds them to a temporary List. The student with the biggest GPA on the
-List is selected to have the project. A solution is then created of the selected student along with the project.

genGPA()
-method which generates a GPA between 1.0 and 4.2, approx. one tenth of students will have a GPA of 1.0-2.4, approx. three tenths
-of students will have a GPA of 2.5-3.1, approx. two fifths of students will have a GPA of 3.2-3.7 and approx. one fifth of
-students will have a GPA of 3.8-4.2

change(List<Solution> solutions)
-this method provides the "change" functionality that allows the system to introduce changes to given candidate solutions. The
-method makes random changes to a list of solutions while also ensuring that some students get a project that is on their preference
-list.

checkForPref(String project, Student student)
-method which takes a String and student as input and outputs the boolean value true if the student has the project in their list of
-preferences, outputs false if the student does not have the project in their preferences

getPrefNumber(String project, Student student)
-method which takes a String and Student as input, and outputs the preference at which the student has the project at. If the student
-does not have the project in their list of preferences, then the integer value 10 is returned. The method has been designed to only
-be used if it is known that the student has the project in their preferences

returnNumber(List<Solution> solutions, Solution s)
-method which takes in a list of solutions and a solution s and returns which number of the list the solution is on

analyse(List<Solution> solutions)
-method for analyzing a solution, takes in a list of solutions and prints out the number of students who got one of their top
-five preferences, the percentage of students who got their first preference, the percentage of students who got one of their
-top five preferences and lastly each students name along with the title of the project which they will be working on

addPenalties(List<Solution> solutions)
-method which takes the solution and calls other methods on it to check for any violations
-calls checkDuplicates() and adds a penalty for any found
-call checkStream() which makes sure that the project is suitable for the student assigned it

testSuite()
-There were also test methods which were created. This method calls them and prints the results.

-------------------------------------------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

SPRINT 3:

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
    - second to then randomly assign a project to one of the students in the cases where there are multiple students with the same preference at a given rank
    - third to randomly assign a project to any student who did not get one of their preferences
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

