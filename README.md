hailscala
=========

Minimal example of scala projet for android with intellij and sbt, using robolectric for tests

Sources :
Alexis Agahi : https://github.com/aagahi/spotmint-android/wiki/Android-Scala-Setup-Tutorial
Jan Berkel : https://github.com/jberkel/android-plugin/wiki/getting-started



How to create a new project :

Prerequisites to install on your computer (these versions used in October 2012 work well together) :
jdk 1.6.0_37 (warning : do not user java 7 with sbt for android)
sbt 0.12.0 : http://www.scala-sbt.org/
scala-2.9.2 : http://www.scala-lang.org/
scalatest_2.9.0-1.8 : http://www.scalatest.org/
android-sdk avec API 10 (Gingerbread) : http://developer.android.com/sdk/index.html
sbt-android-plugin and giter8 : https://github.com/jberkel/android-plugin/wiki/getting-started
sbt-idea 1.1.0
IntelliJ IDEA : http://www.jetbrains.com/idea/

Environment variables :
ANDROID_SDK_ROOT={full path to android home}
ANDROID_HOME={full path to android home, same as above}
SCALA_HOME={full path to scala home}
JAVA_HOME={full path to jdk 6 home}
SBT_HOME={full path to sbt.sh}
And add this to your PATH : $ANDROID_HOME:$ANDROID_HOME/platform-tools:$SBT_HOME:$SCALA_HOME/bin:$JAVA_HOME/bin


**********************************
Installation of some prerequisites
**********************************

- giter8 :
curl https://raw.github.com/n8han/conscript/master/setup.sh | sh
~/bin/cs n8han/giter8
=> this generates an executable "g8" in ~/bin which allows to create a project from scratch, with the necessary configuration for making an android application in scala.

- sbt plugin for intelliJ and external resolvers : I added these lines to my ~/.sbt/plugins/build.sbt so that they are available for all my sbt projects. You can add these to the same file or you can add these sames lines only to your project in PROJECT_FOLDER/project/plugins.sbt (this file will be create later in this tutorial, when executing g8). Do not forget the blank line between the two instructions :

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0")

resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releasesSonatype"  at "http://oss.sonatype.org/content/repositories/releases",
                    "robospecsSnapshots" at "http://jbrechtel.github.com/repo/snapshots",
                    "robospecsReleases" at "http://jbrechtel.github.com/repo/releases/",
                    "snapshotsScalaTools" at "http://scala-tools.org/repo-snapshots",
                    "releasesScalaTools" at "http://scala-tools.org/repo-releases",
                    "centralMaven" at "http://repo1.maven.org/maven2")


*************************
Part I - Project creation
*************************

- ls into a workspace folder without creating any new subfolder for the future project (a new one will be automatically generated)

- Execute this line and answer at the questions at command-line (just Enter to use default proposed values) : ~/bin/g8 jberkel/android-app
package [my.android.project]: my.android.scala.project
name [My Android Project]: MyProject            => with no spaces ion the name
main_activity [MainActivity]: 
scala_version [2.9.2]: 
api_level [10]: 
useProguard [true]: false
scalatest_version [1.8]: 

Rem : for now, don't activate proguard, answer "false"

This will create a folder myproject

- cd to myproject

- edit file project/Build.scala

- add these values to object General :
  val libRoboSpecs = "com.github.jbrechtel" %% "robospecs" % "0.2" % "test"
  val libMockito = "org.mockito" % "mockito-all" % "1.9.5" % "test"
  val libSpecs2 = "org.specs2" %% "specs2" % "1.12.2" % "test"
  val libRobolectric = "com.pivotallabs" % "robolectric" % "1.1-jar-with-dependencies" % "test"
  val scalaTest = "org.scalatest" %% "scalatest" % "1.8" % "test"

- alter the lazy val in the same object General :
replace : libraryDependencies += "org.scalatest" %% "scalatest" % "1.8" % "test"
with    : libraryDependencies ++= Seq(libRoboSpecs, libMockito, libSpecs2, libRobolectric, scalaTest)

- save and exit Build.scala

- go to myproject root and launch sbt : sbt

- in the sbt command-line, generate intellij files : gen-idea no-classifiers

- compile : compile

- generate the apk : android:package-debug

- Exit sbt and customize an android emulator image for scala : follow the "Using the emulator" section of aagahi :
https://github.com/aagahi/spotmint-android/wiki/Android-Scala-Setup-Tutorial 

-ensure emulator is running

- launch sbt and deploy the apk into the emulator : android:start-emulator
The application should launch in the emulator and show "hello, world!" on a black background


*****************************
Part II - Working in intellij
*****************************

- open intellij, Open a project, and select myproject folder

- look at the "Event log" window : intellij should detect the Android framework. Click on "configure" in event log (or in the popup baloon if shown)

- select both android manifest and validate

- In Project files, remove folder "tests" (intellij may need to remove it twice : one for the project, one for the files).

- open File > Project Structure...

- in Project, select android sdk

- in Project, select an output directory for intellij. For example : PROJECT_PATH/myproject/out

- in modules > myproject > Sources, check that these folders contains these entries :
Source Folders :
src/main/scala
src/main/java
src/main/resources
target/scala-2.9.2/src_managed/main/java
target/scala-2.9.2/src_managed/main/scala
gen (eventually)
.idea_modules/gen

Test Source Folders :
src/test/scala
src/test/java
src/test/resources

- in modules > myproject : select Android sub-item and adapt both paths to Manifest and resources directory (which are generated wrong) :
PROJECT_PATH/myproject/src/main/AndroidManifest.xml
PROJECT_PATH/myproject/src/main/res

- in modules > myproject : select Scala sub-item and check that scala compiler is found (if not, select it)

- in Facets : check that a facet Android and a facet Scala is well detected for myproject

- Dependencies : intellij need to find dependencies to these libs :
robospecs_2.8.1-0.1-SNAPSHOT.jar
mockito-all-1.9.5.jar
specs_2.9.1-1.6.9.jar
specs2_2.9.2-1.12.2.jar
specs2-scalaz-core_2.9.2-6.0.1.jar
robolectric-1.2-20121030.213744-165-jar-with-dependencies.jar

For example, you can use a repo or copy these libs to PROJECT_PATH/myproject/libsForIntelliJ
DO NOT copy these to a folder called "lib" : sbt would search libraries in that default folder and generate errors.
If you copy into a folder like libsForIntelliJ, add this folder in :
File > Project Structure... > Modules > MyProject > Dependencies > "add jars or directories" and select libsForIntelliJ, and use scope "Test"

- Validate settings. Intellij should find all dependencies and code completion. If errors are still there, clear intellij caches :
File > Invalidate Caches... and wait for the end of indexing (can takes minutes)

- you can put your tests in test/scala/ and execute them in sbt : sbt test


ToDo :
- find how to execute tests in intellij too
- find how to remove warnings in binding shadow classes of Robolectric while executing tests
- why sbt goes out of memory if executing tests twice without quitting it ?
- adapt this file to be readable in html format for github
