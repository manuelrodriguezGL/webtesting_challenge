# Oscar Valerio's Feedback
1. I think it could be better to have the jenkinsfile and the .gitignore files into the project, webtesting folder in this case.
2. There are several screenshots in the repository, it seems like the .gitignore is not working for that folder.
3. (Optional) implement something better in the custom TestNG listener.
4. BASE_URL = "https://www.saucedemo.com": it could be in the TestNG xmls files.
5. We can take methods in the BasePage like: assesElementTextEquals, assesElementTextContains, textContains, etc to another class in charged of manage this utilities.
6. Use of softasserts as we talked in our meeting.
7. There are some methods with comments and other methods without comments, add comments to missing methods.
8. Add content to the readme file with instructions about how to run the project.
9. Try to avoid commented code like: "//e.getText();".
10. Use of BotStyle test pattern to reuse some code in PageObjects: https://github.com/SeleniumHQ/selenium/wiki/Bot-Style-Tests 
11. I think that there could be more validations in the test cases than validating booleans. That way you can validate and have more custom details on failures when the assert fails.
12. There is a mix between TestNg and Junit, you can use just testNG since it also has assert methods.
13. Use valid credentials out of the code, it is a good practice to not have usernames and passwords in teh code, you can use environemnt variables instead.
14. Organize a little bit the POM.xml file, remove dependencies that you are not using and so on.