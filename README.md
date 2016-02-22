# trygve
The trygve language project - Building a DCI-centric language from the ground up

For now, all pull requests will be rejected without ceremony. The code is under heavy development. We'll open it up a bit further a bit later, probably in 1Q2017.

## Running

Clone or download the repo, then build and execute with `gradlew run`.

### Running on Windows

Here's a quick-and-dirty guide from Andreas Söderlund for an initial bring-up on Windows:

1. Download the Java JDK from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html (one of the Windows x64/x86 versions) and install it.
2. For git, download the full package from http://cmder.net/ (a nice console emulator) and install it.
3. Start cmder, cd to an appropriate directory and execute:

```
git clone https://github.com/jcoplien/trygve.git
cd trygve
gradlew run
```

If you want to update to the latest version at a later time, execute the following inside the `trygve` directory:

```
git pull
gradlew run
```

## User manual

Available in the repository, `trygve1.docx`. Many docx viewers are available, here is [the official one](https://www.microsoft.com/en-us/download/details.aspx?id=4) for Windows.

An online version is available at http://fulloo.info/Documents/trygve/trygve1.html

## Developing

Import as a Gradle project into your IDE. (Or as a regular project if you wish.) Then run or debug `info.fulloo.trygve.editor.Main`.

To rebuild ANTLR parser run `gradlew antlr`.

To build a distribution `gradlew build`.

Also remember to enable assertions ([Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions), [IntelliJ](http://stackoverflow.com/questions/18168257/where-to-add-compiler-options-like-ea-in-intellij-idea))

### Eclipse

For details on how to configure for eclipse, see the appendix of the user manual. The instructions are for the Mac but generalizing them to Windows (and to other versions of eclipse) should be straightforward.

## Questions

Send questions to cope@gertrudandcope.com.