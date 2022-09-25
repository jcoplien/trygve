# trygve
The trygve language project - Building a DCI-centric language from the ground up

Please contact jcoplien@gmail.com if you are considering working up to a pull request. Pull requests should first be socialized on object-composition@googlegroups.com.

## Running

Clone or download the repo, then build and execute with `gradlew run`.

### Running on Windows

Here's a quick-and-dirty guide from Andreas Söderlund for an initial bring-up on Windows:

1. Download the OpenJava JDK from https://jdk.java.net/18/
2. Install git: https://git-scm.com/downloads
3. cd to an appropriate directory and execute:

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

Available in the repository, `trygve1.docx`. Many docx viewers are available, here is [the official one](https://apps.microsoft.com/store/detail/doc-viewer/9N69KFJDS28Z) for Windows.

An online version is available at http://fulloo.info/Documents/trygve/trygve1.html

## Developing

VS Code is a very accessible way to develop trygve. Installation instructions [are available here](https://code.visualstudio.com/docs/languages/java).

For other environments, import this repository as a Gradle project into your IDE. (Or as a regular project if you wish.) Then run or debug `info.fulloo.trygve.editor.Main`.

To build a distribution `gradlew build`.

Also remember to enable assertions ([Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions), [IntelliJ](http://stackoverflow.com/questions/18168257/where-to-add-compiler-options-like-ea-in-intellij-idea))

### Eclipse

For details on how to configure for eclipse, see the appendix of the user manual. The instructions are for the Mac but generalizing them to Windows (and to other versions of eclipse) should be straightforward.

## Questions

Send questions to cope@gertrudandcope.com.
