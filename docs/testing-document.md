# Testing

#### What is being tested?
Everything except UI and UI related classes.

#### How is it being tested?
Testing is implemented with JUnit 4. Most methods have multiple tests to cover different scenarios.

#### How much is being tested?
As of now, CodeCov reports **93%** complexity coverage and **89%** line coverage.


#### How can I verify the results?
You can run the tests with `./gradlew test`. After that the **jacoco** report can be found at `build/reports/jacoco/tests/html/`.

#### What about testing the UI?
It has to be tested manually through the console interface. If you find any bugs, a bug report or issue is much appreciated.
