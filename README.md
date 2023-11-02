Project on the topic "Development of a client-server application
using Java Enterprise Edition"
Tools and technologies used:
• Java EE
•	Apache Tomcat
•	SpringBoot, SpringData
•	MySQL DB
•	Java FX
•	Jackson

Terms of reference:
To develop a client-server application using the above technologies to create, modify, delete records of the fencing school administrator. To do this, implement the following set of Java classes and database tables corresponding to them.
1. User – the user of the system, has the following fields
   •	id
   •	login
   •	password
   •	name
   •	regDate
2. Apprentice – a student of a fencing school who attends training sessions with different coaches. Has the following fields
   •	id
   •	surname
   •	name
   •	patronymic
   •	phoneNumber
3. Trainer – a fencing school coach who conducts trainings for students. Each coach has an experience, measured in the number of years worked in this field. Has the following fields
   •	id
   •	surname
   •	name
   •	patronymic
   •	experience
4. TrainerSchedule – coach's schedule, shows on which days of the week at what time the coach conducts classes, fields:
   •	idTrainer
   •	mondayStart
   •	mondayEnd
   •	tuesdayStart
   •	tuesdayEnd
   •	wednesdayStart
   •	wednesdayEnd
   •	thursdayStart
   •	thursdayEnd
   •	fridayStart
   •	fridayStart
   •	saturdayStart
   •	saturdayEnd
   •	sundayStart
   • sundayEnd
5. Training – training conducted at the fencing school. It has the number of the hall, the date and time of the lesson, as well as the coach and the student. When adding a new training session for a coach, it must be taken into account that the coach cannot receive more than 3 students at the same time, and also does not conduct an appointment outside of working hours. Only 10 students can study in the hall at the same time. A student cannot attend several training sessions on the same day.
   The training lasts 90 minutes.
   •	id
   •	numberGym
   •	trainer
   •	apprentice
   •	date
   •	timeStart
   Implement relationships between database tables in the form:
   Trainer – TrainerSchedule: One to one, Apprentice – Training: 1 to many, Trainer – Training: 1 to many

The server part of the application:
A REST API is being developed that generates responses in JSON format. For each entity of the data model, develop a repository for accessing the database in accordance with the API of the server application. Server Application Classes:
1. UserController
   Methods:
   • post – receives data and registers a new user of the school in the system. Correctly processes the existence of the user in the database (all)
   • get – checks the correspondence of the username and password for the user in the database (authorization, gives the token)
   • get – displays the user with the specified id (admin)
   • delete – deletes the user with the specified id from the database (admin)
2. ApprenticeController (1-to-1 relationship with user?! Inheritance!?)
   Methods:
   • post – adds a new student to the database(all)
   • get – receives all students (admin), as well as the student by his id (admin)
   • put – updates the student by his admin id
   • delete – deletes the student and all records associated with him by the admin
3. TrainerController(1-to-1 communication with user?!)
   Methods:
   • post – adds a new trainer to the admin database
   • get – receives all coaches (authenticated), as well as the coach by his id authenticated
   • put – updates the trainer by his id (admin)
   • delete – removes the trainer from the database by his admin id
4. TrainerScheduleController
   Methods:
   • post – admin adds a schedule for a specific coach
   • get – gets the schedule for the coach with the specified id authenticated
   • put(post) – updates the coach's schedule with the specified id (admin, coach)
   • delete – deletes the coach's schedule with the specified id (admin, coach)
5. TrainingController
   Methods:
   • post – adds a new workout for a given user id and for a given trainer id (student, admin)
   • get – receives training by her id, training by user id or trainer id (authenticated)
   • delete – deletes training by her id (admin student)

The client part of the application
It is a set of graphical interface forms that interact with each other. From the forms, the API of the server application is accessed via an http connection, sending a request to the server and receiving a response from it in JSON format, then parsing it and displaying the results in the form of user interface elements. A set of forms and classes of the client part:
1. Program – the main class of the application. Checks for a previously authorized user in the system, if the user is authorized, then the transition to the main form of the MainController application takes place, if not, then the transition to the AuthorizationController is carried out for the authorization procedure
2. AuthorizationController – a form that performs the authorization procedure in the system. Allows you to enter your username and password and log in. It is also possible to switch from this form to the registration form of a new RegistrationController user or, if successful, to the MainController
3. RegistrationController – a form that allows you to register a new user in the system, after successful registration, the transition is made to the AuthorizationController form
4. MainController – the main form of the application. Allows you to add, delete, update students, coaches and their schedules, as well as training sessions. To develop an ergonomic and user-friendly interface. The MainController should call other forms created independently, based on the modularity of the project and rationality, to implement this interface.