plugins {
    alias(libs.plugins.blossom)
    java
}

group = "com.github.selfcrafted"
version = "1.0.0"

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}

tasks {
    blossom {
        val plugin = "src/main/java/com/github/selfcrafted/velocity/allowlist/VelocityAllowlist.java"

        replaceToken("&&name", project.name, plugin)
        replaceToken("&&version", version, plugin)
    }
}