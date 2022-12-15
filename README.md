# My Task Manager

## My Task Version 1

- Added a home page that has a heading at the top of the page, an image to mock the “my tasks” view, and buttons at the bottom of the page to allow going to the “add tasks” and “all tasks” page.
- Added an add a task page that allows the users to type in details about a new task, specifically a title and a body. When users click the “submit” button, show a “submitted!” label on the page.
- Added an All Tasks Page. 


## My Task Version 1.1
- Added a Task Detail activity that has the task title and a Lorem Ipsum description.
- Added a settings activity that allows the user to enter their username, and save it.
- Added to the Main activity, an image link the redirects the user the settings activity.
- The user's username is diplayed in the main activity as "Username's Tasks"
- Added three text view link tasks in the main activity, that redirects the user to the task detial activity, and displays the clicked task as the title. 


## My Task Version 1.2
- The Main Activity is now dynamic, and the user can scroll through all its tasks
- Main acitvity also displays the description, and the state of the task
- UI/UX changes 

## My Task Version 1.3

- The app can add a Task and save the data in a database using Room.
- UI/UX Changes
- The Main Activity displays the saved Tasks, as well as the Task Detail Activity Page

## My Task Version 1.4 

- Room was removed from the application as local storage
- AWS Amplify was added as a scalable backend 
- The task entity was replicated with AWS Amplify schema 
- The app can add a Task and save the data in a database using AWS DynamoDB
- The Main Activity displays the saved Tasks, as well as the Task Detail Activity Page

## My Task Version 1.5

- A team Entity was created to create a one to many relationship between a team and tasks.
- The User can Add a task and assigned it to a team
- The User can select a team in the settings page to filter the tasks displayed on the main page of the selected team. 

## My Task Version 1.6

- AuthO was added 
  - A user is Able to Sign up (Then redirected to the verification activity)
  - The user will verify its account with a code emailed to the user(if verified, the user is redirected to the login page)
  - A user is able to login (If the user is authenticated, the user will be ridirected to the main activity)
  - A user is able to logout
  
![Screenshot 2022-12-14 at 8 15 46 PM](https://user-images.githubusercontent.com/103771906/207749436-c1f7b824-1db4-421f-a482-6dd889f8404d.png)




