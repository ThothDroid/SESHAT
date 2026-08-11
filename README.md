# SESHAT (Symbol Engine for Sketched Hieroglyph Art Transformation)
A simple Android library for exporting egyptian hieroglyphic texts into images.

*This library is part of the [Egyptian Writer](https://github.com/ThothDroid/Egyptian_Writer) Android App.*

## Disclaimer
This library uses the `GlyphX` and the `MdC` code for encoding Hieroglyphs.

A library for converting GlyphX to MdC and back is stored here: [GlyphConverter](https://github.com/ThothDroid/GlyphConverter)

> [!TIP]
> **If you want to display the hieroglyphs on Android**, then you can use the `THOTH`-library:\
> [ThothDroid/THOTH](https://github.com/ThothDroid/THOTH)

## Implementation with jitpack
Add this to your `settings.gradle.kts` at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
}
```
Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation("com.github.ThothDroid:SESHAT:1.0.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#ThothDroid/SESHAT/)

## Implementation with `.aar` file
Download the `SESHAT_debug_versionname.aar` file from this repository, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/SESHAT_debug_versionname.aar"))
}
```

> [!IMPORTANT]
> If you renamed the `.aar` file you also have to change the name in the dependencies
