import org.ajoberstar.grgit.Grgit

val currentGroup = "${ext["platformGroup"]!!}.rest.common"
val platformVersion = "${ext["platformVersion"]!!}"

plugins {
    maven
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-aop")
    compile("org.springframework.boot:spring-boot-configuration-processor")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin")

    compile("io.springfox:springfox-swagger2:2.9.2")

    implementation("org.apache.commons:commons-lang3:")
    implementation(project(":core"))
}

tasks.getByName<Upload>("uploadArchives") {
    repositories {
        withConvention(MavenRepositoryHandlerConvention::class) {
            mavenDeployer {
                withGroovyBuilder {
                    "repository"("url" to uri("https://nexus.ci.funtrips.io/repository/maven-releases/")) {
                        "authentication"("userName" to System.getenv("SONATYPE_USERNAME"), "password" to System.getenv("SONATYPE_PASSWORD"))
                    }
                    "snapshotRepository"("url" to uri("https://nexus.ci.funtrips.io/repository/maven-snapshots/")) {
                        "authentication"("userName" to System.getenv("SONATYPE_USERNAME"), "password" to System.getenv("SONATYPE_PASSWORD"))
                    }
                }

                pom {
                    groupId = currentGroup
                    artifactId = "common"
                    version = environmentPlatformVersion()
                }
            }
        }
    }
}

fun environmentPlatformVersion(): String = when (Grgit.open(mapOf("dir" to file("../../"))).branch.current().name) {
    "develop" -> "$platformVersion-SNAPSHOT"
    "acceptance" -> "$platformVersion-acceptance-SNAPSHOT"
    "master" -> platformVersion
    else -> platformVersion
}
