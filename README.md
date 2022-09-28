# trygve

The trygve language project - Building a DCI-centric language from the ground up

Please contact jcoplien@gmail.com if you are considering working up to a pull request. Pull requests should first be socialized on https://groups.google.com/g/object-composition

## Running

Executables with installation instructions can be downloaded at https://fulloo.info/Downloads/

## User manual

The user manual is available online at https://fulloo.info/Documents/trygve/trygve1.html

## Developing

You'll need OpenJDK 17 to build trygve. It can be downloaded from https://adoptium.net/ or installed with a package manager on your system.

To download the source, first install git: https://git-scm.com/downloads

Then clone (or fork) the trygve repository:

```
git clone https://github.com/jcoplien/trygve.git
```

Finally, inside the repository directory, execute:

```
gradlew run
```

To build a distribution:

```
gradlew build
```

### IDE environments

For IDE:s, import this repository into your IDE, then run or debug `info.fulloo.trygve.editor.Main`.

Also remember to enable assertions ([Eclipse](http://stackoverflow.com/questions/5509082/eclipse-enable-assertions), [IntelliJ](http://stackoverflow.com/questions/18168257/where-to-add-compiler-options-like-ea-in-intellij-idea), [VS Code](https://code.visualstudio.com/docs/java/java-debugging#_launch))

## Questions

Send questions to cope@gertrudandcope.com.
