plugins {
  kotlin("multiplatform") version "1.5.0"
  id("com.bnorm.template.kotlin-ir-plugin")
}

template {
  stringProperty.set("Hello World")
  fileProperty.set(buildDir)
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }
    testRuns["test"].executionTask.configure {
      useJUnit()
    }
    withJava()
  }
  val hostOs = System.getProperty("os.name")
  val isMingwX64 = hostOs.startsWith("Windows")
  val nativeTarget = when {
    hostOs == "Mac OS X" -> macosX64("native")
    hostOs == "Linux" -> linuxX64("native")
    isMingwX64 -> mingwX64("native")
    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
  }

  nativeTarget.apply {
    binaries {
      executable {
        entryPoint = "test.main"
      }
    }
  }
  js(IR) {
    binaries.executable()
    nodejs {
      testTask {
        useMocha()
      }
    }
  }
  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }
    val jvmMain by getting
    val jvmTest by getting
    val nativeMain by getting
    val nativeTest by getting
    val jsMain by getting
    val jsTest by getting
  }
}

group = "com.bnorm.template"
version = "0.1.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
}
