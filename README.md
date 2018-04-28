# GOAPer

[![Travis CI](https://travis-ci.org/doc97/GOAPer.svg?branch=master)](https://travis-ci.org/doc97/GOAPer)
[![CodeCov](https://img.shields.io/codecov/c/github/doc97/GOAPer/master.svg)](https://codecov.io/github/doc97/GOAPer/)

A project for a [course](https://github.com/TiraLabra/2018-kevat-p4/wiki) at The University of Helsinki

## Getting Started

1. Clone the repository
```
~$ git clone https://github.com/doc97/GOAPer.git
```

2. Run with Gradle
```
~$ cd GOAPer
~/GOAPer$ ./gradlew run
```
**Note:** If you are on Windows, use `gradlew.bat` instead.

3. Execute a sample scenario
```
load res/scenario1.json
plan
show plan
```

You can exit the console with
```
exit
```
or try out some other scenarios. They can be found in the `res/` folder.

Check out the documentation for other [console commands](https://github.com/doc97/GOAPer/wiki/Console).

## Documentation

[The wiki](https://github.com/doc97/GOAPer/wiki) contains all documentation.

## Problems?

Please create a [Github issue](https://github.com/doc97/GOAPer/issues) and describe the problem.
If it's a bug, please try to document the reproduction steps and supply a short description of the bug.
