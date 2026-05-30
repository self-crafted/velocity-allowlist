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

sourceSets {
    main {
        blossom {
            javaSources {
                property("name", project.name)
                property("version", version.toString())
            }
        }
    }
}
