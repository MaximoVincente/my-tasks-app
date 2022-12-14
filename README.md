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
  
![Screenshot 2022-12-14 at 8 20 44 PM](https://user-images.githubusercontent.com/103771906/207750036-662debe9-5c3b-4303-a86e-f0a1e6f00694.png)

## My Task Version 1.7

- Amplify S3 is the storage for uploaded files to the application
- The user can add an Image to the task 

![Screenshot 2022-12-14 at 11 41 14 PM](https://user-images.githubusercontent.com/103771906/207774529-c5b624be-c1cc-4eb6-a3ff-34ab619c9765.png)

## My Task App Publishing to Google Play Store

- Ensured that the application followec Google's guidlines
- Built a signed Android Application Bundle
  - When I first tried uploading the AAB, I got an error, stating the the application's icon's graphic was not supported. I checked the used icon, and changed it format to support it, rebuilt the AAB and was able to successfully upload the AAB.
- A privacy policy was created for the application using Termly services. 
- Link to Privacy Policy: 
  - [Application Privacy Policy](https://docs.google.com/document/d/1E8whE24qtxLPOsqM2AlPExQ06-UoIPnFBTpXZP3TzCA/edit?usp=sharing)
- Completed Google Play's questionares about the use of the app
- Published the application and it is currently under review


### Steps to publish the application 

#### Production 

- Task Buddy Release
  - Start full rollout
- Countries / regions
  - region: United States

#### Open testing

- 1 (1.0)
  - Start full rollout
- Countries / regions
  - region: United States
- Track status
  - Resume track
- Testers
  - Change feedback channel to mavincen@icloud.com

#### Closed testing - Alpha

- First release
  - Start full rollout
- Countries / regions
  - United States
- Testers
  - Set testers to be managed by email lists: Test Task Buddy
  
#### Main store listing

- English (United States) – en-US

#### App content

- Content Rating
  - Submit new questionnaire
- Target audience and content
  - Target age is 18 and older.
- Privacy policy
  - Set Privacy policy URL to https://docs.google.com/document/d/1E8whE24qtxLPOsqM2AlPExQ06-UoIPnFBTpXZP3TzCA/edit?usp=sharing
- Ads declaration
  - Update ads declaration
- Data safety
  - Complete Data safety questionnaire
  
#### Store settings

- App category
  - (Productivity app)

## My Task App Version 1.8 

- App now has location funcitonalities
- User can add its location to a task 

## My Task App Version 1.9 

- Added funcionality to record activity events using analytics
- Added functionality to read out the task’s description using the Amplify Predictions library.

### Current bugs with Analytics and Predictions

- Although the functionalities of analytics and predications are added to the application, the following bug is interrupting the emulator to run the appliccation successfully
<img width="1393" alt="Screenshot 2022-12-20 at 7 21 09 PM" src="https://user-images.githubusercontent.com/103771906/208800192-1aae2190-f8d0-421f-b2ff-5c3b066b7101.png">
