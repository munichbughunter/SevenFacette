# Welcome to 7Facette

![GitHub Workflow Status](https://img.shields.io/github/workflow/status/munichbughunter/Sevenfacette/7Facette?label=7Facette%20build)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/munichbughunter/Sevenfacette?color=orange&label=latest%20version)
![GitHub](https://img.shields.io/github/license/munichbughunter/SevenFacette)
|[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/dashboard?id=7Facette-Core)<br>**7Facette-Core**  [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=7Facette-Core&metric=coverage)](https://sonarcloud.io/dashboard?id=7Facette-Core)|[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-black.svg)](https://sonarcloud.io/dashboard?id=7Facette-Web)<br>**7Facette-Web**  [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=7Facette-Web&metric=coverage)](https://sonarcloud.io/dashboard?id=7Facette-Web)|
|---|---|

7Facette is an open-source multiplatform test automation library written in Kotlin with support for JVM and JS. It is designed to provide a structure
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

[userdocs]:https://github.com/munichbughunter/SevenFacette/wiki
[get-started]:https://github.com/munichbughunter/SevenFacette/wiki#what-problem-does-7facette-solve
[examples]:https://github.com/munichbughunter/SevenFacette/wiki/Examples-for-usage-7Facette
[contributing]:CONTRIBUTING.md

## Download

### Repository

You can download 7Facette packages from Jitpack

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
dependencies {
    implementation 'com.github.munichbughunter:SevenFacette:Tag'
}
```

### Packages

#### JVM

To use the JVM implementation of the 7Facette core and web module.

```kotlin
implementation 'de.p7s1.qa.sevenfacette:core-jvm:1.10.0'
implementation 'de.p7s1.qa.sevenfacette:web-jvm:1.10.0'
```

#### JS

To install the 7Facette core module for JS execute the following command

```kotlin
npm i --save sevenfacette
```

## Questions or need help?

### Troubleshooting

Context specific **Troubleshooting** guide is available in relevant pages of the [7Facette Wiki](https://github.com/munichbughunter/SevenFacette/wiki/Troubleshooting).

### Talk with us

Please don't file an issue to ask a question, you'll get faster answers by using the appropriate resource (see below).

- I need help -- [7Facette slack channel](https://7facette.slack.com)
- I got this error and I'm sure it's a bug -- create an [issue](https://github.com/munichbughunter/SevenFacette/issues)
- I have an idea/request -- create an [issue](https://github.com/munichbughunter/SevenFacette/issues) using our [template](https://github.com/munichbughunter/SevenFacette/blob/documentation/create_readme/misc/templates/ISSUE_TEMPLATE/Feature_Request.md)
- Why do you? -- [7Facette slack channel](https://7facette.slack.com)
- When will you? -- [7Facette slack channel](https://7facette.slack.com)
