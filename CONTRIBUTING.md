# Contributing to trygve

The trygve language project - Building a DCI-centric language from the ground up

Please contact cope@gertrudandcope.com if you are considering working up to a pull request. Pull requests should first be socialized on object-composition@googlegroups.com.

## Testing

Your changes should compile and run in your IDE using Java SE 18 as a run-time environment. The trygve environment should pass all smoke tests after you have made your change. Start the environment and press the "Run Tests" button. If it succeeds, you're good.

Include a nice suite of tests that validate that your contribution works. Include any new tests in the test folder. Tests are ordinary trygve source files intended to be parsed and run. There is a comment field at the end of each test (q.v.) documenting the expected output from the test. At this writing, tests may not create grahical window, nor may they ask for keyboard or mouse input.

You also need to update the user manual as appropriate (trygve1.docx).

## General Coding Guidelines

Please adhere to the established code formatting and naming conventions. Class instance member data tend to be named with a trailing underscore. Be attentive to good formatting. Don't hesitate to clean up other code you feel needs it (and I know there is some).

If you find a bug on your journies through trygve, you are welcome aslo to fix it. Whether you do or not, please file a GitHub issue describing the problem. It's really important to include an example that reproduces the problem. The recommded spot for such examples is in the tests/ folder.

## Ownership

Approved changes (those that are merged into the trunk) become part of the community product as described in GPL 2.0.

## Questions

Send questions to cope@gertrudandcope.com.
