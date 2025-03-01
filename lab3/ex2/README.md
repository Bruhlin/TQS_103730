# Ex 3c) What could be the advantages and disadvantages of using a real database connection in testing activities?

## Advantages:

- More Realistic Testing;
- Catches Schema and Query Issues;
- Tests Compatibility with Database-Specific Features;
- Catches Data Persistence Bugs;
- More Accurate Performance Testing.

## Disadvantages:

- Slower Test Execution;
- Increased Complexity;
- Risk of Data Pollution;
- Harder to Isolate Tests;
- Requires Database Availability.

## If performance is not a concern, a real database is the best choice for integration testing. However, for fast feedback cycles, in-memory databases or mocking solutions are better.