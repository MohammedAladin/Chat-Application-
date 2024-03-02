# ChatApp

The project is a comprehensive chat application encompassing both client-side and server-side components. On the client side, users register with a unique phone number, update profiles, add contacts, and engage in one-to-one or group chats with rich text capabilities, including various message attributes. Additional features include status management, contact blocking, notifications, the ability to exit the application without forgetting user information, and a chatbot service. On the server side, the system manages user registration, tracks client status, facilitates server announcements, and provides statistics on user demographics. It ensures data availability by maintaining user contact lists and supporting chat session persistence.


## Features

- **Direct and Group Messaging**: Engage in both one-to-one and group conversations.
- **AI Chatbot Integration**: Interact with an AI-powered chatbot for automated assistance.
- **Message Persistence**: Store chat messages securely in a database for future reference.
- **User Blocking with Soft Blocking**: Manage user interactions with the ability to temporarily restrict communication.
- **Status Updates**: Easily update and display your current online status.
- **File Sharing**: Share various file types, including images, documents, and videos.
- **Profile Management**: Customize your profile information and preferences.
- **Server-side Statistics**: Access real-time statistics about user activity and apply database changes accordingly.
- **Announcement Broadcast**: Broadcast important announcements to all users from the server.


## Technologies Used

- **JavaFX**
- **FXML** 
- **CSS**
- **IO/NIO**
- **SceneBuilder**
- **Java** 
- **JDBC** 
- **RMI** 
- **MySQL**

  
## Use the Application:

1. **Ensure Java and Maven are Installed**: Make sure you have Java version 17 or above and Maven installed on your system.

2. **Clone the Repository**: Clone the repository to your local machine using Git. Open a terminal or command prompt and run the following command:

   ```bash
   git clone https://github.com/OmarAminn27/Chat-Application-.git

3. **Package the Application**: Navigate to the project directory and run the Maven package command to build the application:
4. 
   ```MVN
   mvn clean package
5. **Run the Server**: navigate to the server directory and run the following command:
   
   ```java
   java -jar target/Server-1.0-SNAPSHOT-shaded.jar
7. **Run the Client**: navigate to the Client directory and run the following command:
   
   ```java
   java -jar target/Client-1.0-SNAPSHOT-shaded.jar
## Contributors

- [Mohamed Alaa](https://github.com/MohammedAladin)
- [Youssef Ehab](https://github.com/youssef-Ehab)
- [Nada Mahmoud](https://github.com/boooTomatoes)
- [Omar Amin](https://github.com/OmarAminn27)
