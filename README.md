# trygve
The trygve language project - Building a DCI-centric language from the ground up

For now, all pull requests will be rejected without ceremony. The code is under heavy development. We'll open it up a bit further a bit later, probably in 1Q2017.

## Running

Just build and execute with `gradlew run`.

## Developing

Import as a Gradle project into your IDE. (Or as a regular project if you wish.) Then run or debug `info.fulloo.trygve.editor.Main`.

Also remember to enable assertions ([Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions), [IntelliJ](http://stackoverflow.com/questions/18168257/where-to-add-compiler-options-like-ea-in-intellij-idea))

To rebuild ANTLR parser run `gradlew antlr`.

To build a distribution `gradlew build`.