# trygve
The trygve language project - Building a DCI-centric language from the ground up

For now, all pull requests will be rejected without ceremony. The code is under heavy development. We'll open it up a bit further a bit later, probably in 1Q2017.

## Running

Just build and execute with `gradlew run`.

## Developing

Import as a Gradle project into your IDE. (Or as a regular project if you wish.) Then run or debug `info.fulloo.trygve.editor.Main`.

Here's a quick-and-dirty guide from Andreas Söderlund for an initial bring-up on Windows:

1. Download Java from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html (one of the windows x64/x86 versions) and install
2. Download the full package from http://cmder.net/ (a nice console emulator) and install
3. Start cmder and execute:
git clone https://github.com/jcoplien/trygve.git
cd trygve
gradlew run


For details on how to configure for eclipse, see the appendix of the user manual (trygve1.docx). The instructions are for the Mac but generlising them to Windows (and to other versions of eclipse) should be straightforward.

Also remember to enable assertions ([Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions), [IntelliJ](http://stackoverflow.com/questions/18168257/where-to-add-compiler-options-like-ea-in-intellij-idea))

To rebuild ANTLR parser run `gradlew antlr`.

To build a distribution `gradlew build`.