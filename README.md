# Welcome to 7Facette

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/p7s1-ctf/Sevenfacette/7Facette?label=7Facette%20build)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/p7s1-ctf/Sevenfacette?color=orange&label=latest%20version)


7Facette is an open-source multiplatform test automation library written in Kotlin with support for Kotlin Native, JVM and JS. It is designed to provide a structure
for developing higher quality automated acceptance and regression tests easier, faster and of course with more fun. So your team can hit the ground running 
and not have to waste time needlessly building and maintaining their own framework. It`s ideal for agile software delivery teams who want to collaborate
around living documentation. 

## Where can I learn more?

| **[User Docs][userdocs]**     | **[Setup Guide][get-started]**     | **[Examples][examples]**           | **[Contributing][contributing]**           |
|:-------------------------------------:|:-------------------------------:|:-----------------------------------:|:---------------------------------------------:|
| [![i1][userdocs-image]][userdocs]<br>Learn more about using 7Facette | [![i2][getstarted-image]][get-started]<br> Getting started with 7Facette | [![i3][examples-image]][examples]<br>Some 7Facette Examples | [![i4][contributing-image]][contributing]<br>How can you contribute to 7Facette? |

[userdocs-image]:/misc/images/docs.png
[getstarted-image]:/misc/images/setup.png
[examples-image]:/misc/images/roadmap.png
[contributing-image]:/misc/images/contributing.png

[userdocs]:https://github.com/p7s1-ctf/SevenFacette/wiki
[get-started]:https://github.com/p7s1-ctf/SevenFacette/wiki/Getting-Started
[examples]:https://github.com/p7s1-ctf/SevenFacette/wiki
[contributing]:CONTRIBUTING.md

## Download

### Repository

You can download 7Facette packages from JFRog Bintray

#### JFrog Bintray

```kotlin
repositories {
    maven {
	  url "https://dl.bintray.com/p7s1qa/sevenfacette-jvm"
	}
}
```

### Packages

#### JVM

Actual we provide a JVM implementation of the 7Facette core and web module. The JS implementation is planned
for the next release. 

```kotlin
implementation 'de.p7s1.qa.sevenfacette:core-jvm:0.5.3'
implementation 'de.p7s1.qa.sevenfacette:web-jvm:0.5.3'
```

## Questions or need help?

### Troubleshooting

Context specific **Troubleshooting** guide is available in relevant pages of the [7Facette Wiki](https://github.com/p7s1-ctf/SevenFacette/wiki/Troubleshooting).

### Talk with us

Please don't file an issue to ask a question, you'll get faster answers by using the appropriate resource (see below).

- I need help -- [7Facette help channel @ Spektrum.chat](https://spectrum.chat/7facette/help?tab=posts)
- I got this error and I'm sure it's a bug -- create an [issue](https://github.com/p7s1-ctf/SevenFacette/issues)
- I have an idea/request -- create an [issue](https://github.com/p7s1-ctf/SevenFacette/issues) using our [template](https://github.com/p7s1-ctf/SevenFacette/blob/documentation/create_readme/misc/templates/ISSUE_TEMPLATE/Feature_Request.md)
- Why do you? -- [7Facette Spectrum.chat](https://spectrum.chat/7facette)
- When will you? -- [7Facette Spectrum.chat](https://spectrum.chat/7facette)
