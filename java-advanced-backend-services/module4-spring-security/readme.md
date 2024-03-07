##Task
(1 start)
1. Create Spring Boot MVC project.
2. Create REST endpoint "GET /info" that provide random stats (ex: "MVC application"). Test this endpoint.
3. Add Spring Security module to your project and configure it for authenticated access to all resources. Use email/password combination for it.
   (2 starts)
4. Use a non-embedded DB to store users.
5. Use salt and hashing to store user passwords.
6. Create additional REST endpoint "GET /about" and configure non-authenticated access to it.
   (3-4 starts)
7. Create one more REST endpoint "GET /admin".
8. Now you need to add authorised access to "GET /info" and "GET /admin", add "VIEW_INFO", "VIEW_ADMIN" permissions for it. Create 3 users with different combination of permissions.
9. Create new Login/Logout pages and configure Spring Security to use new Login/Logout.
10. Add Brute Force protector. BLock user email for 5 minute on 3 unsuccessful login.
11. Create an endpoint to show blocked users
    (5 start)
12. Implement a new Spring Boot MVC application called "Secret providers". Application should provide page with text form. After sending a secret, application must generate uniq link address, to provide one-time access to secret information. After this information must be removed from application.
13. User (sender and recipient) must be authorized and have "STANDARD" permission.
14. Use docker containers to implement solution.
