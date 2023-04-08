plugins {
  id("java")
  id("org.jetbrains.kotlin.jvm") version "1.7.20"
  id("org.jetbrains.intellij") version "1.10.1"
  id("org.jetbrains.grammarkit") version "2022.3.1"
}

group = "dev.aripiprazole.kind"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

grammarKit {
  jflexRelease.set("1.7.0-1")
  grammarKitRelease.set("2021.1.2")
  intellijRelease.set("203.7717.81")
}

tasks.generateParser {
  sourceFile.set(file("src/main/kotlin/dev/aripiprazole/kind/idea/Kind.bnf"))
  pathToParser.set("dev/aripiprazole/kind/idea/parser/KindParser")
  pathToPsiRoot.set("dev/aripiprazole/kind/idea/psi")
  targetRoot.set("src/main/gen")
  purgeOldFiles.set(true)
}

tasks.generateLexer {
  sourceFile.set(file("src/main/kotlin/dev/aripiprazole/kind/idea/_KindLexer.flex"))
  targetDir.set("src/main/gen/dev/aripiprazole/kind/idea/lexer")
  targetClass.set("KindLexer")
  purgeOldFiles.set(true)
}

tasks.compileKotlin {
  dependsOn(tasks.generateParser)
  dependsOn(tasks.generateLexer)
}

sourceSets["main"].java.srcDirs("src/main/gen")

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2022.1.4")
  type.set("IC") // Target IDE Platform

  plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
  // Set the JVM compatibility versions
  withType<JavaCompile> {
    sourceCompatibility = "11"
    targetCompatibility = "11"
  }
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
  }

  patchPluginXml {
    sinceBuild.set("221")
    untilBuild.set("231.*")
  }

  signPlugin {
    certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
    privateKey.set(System.getenv("PRIVATE_KEY"))
    password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
  }

  publishPlugin {
    token.set(System.getenv("PUBLISH_TOKEN"))
  }
}
