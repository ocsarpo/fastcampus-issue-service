//plugins {
//    id("java")
//}

//group = "com.fastcampus"
//version = "0.0.1-SNAPSHOT"
//
//repositories {
//    mavenCentral()
//}

apply(plugin = "kotlin-jpa")

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")

//    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
//    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

//tasks.getByName<Test>("test") {
//    useJUnitPlatform()
//}
