[![GitHub Repo stars](https://img.shields.io/github/stars/ThothDroid/SESHAT?style=for-the-badge&logo=github&color=yellowgreen)](https://github.com/ThothDroid/SESHAT/stargazers)
[![Static part of Badge](https://img.shields.io/badge/Part%20of-Egyptian%20Writer%20App-%233DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/)
[![GitHub License](https://img.shields.io/github/license/ThothDroid/SESHAT?style=for-the-badge&logo=gnu&color=yellow)](https://github.com/ThothDroid/SESHAT?tab=GPL-3.0-1-ov-file)
[![GitHub forks](https://img.shields.io/github/forks/ThothDroid/SESHAT?style=for-the-badge&logo=git&logoColor=white&color=%23F05032)](https://github.com/ThothDroid/SESHAT/forks)
\
[![jitpack](https://jitpack.io/v/ThothDroid/SESHAT.svg)](https://jitpack.io/#ThothDroid/SESHAT)
[![Static wiki Badge](https://img.shields.io/badge/Egyptian%20Writer-WIKI-yellow?style=flat&logo=gitbook&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/wiki)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ThothDroid/SESHAT?color=blue)](https://github.com/ThothDroid/SESHAT)
[![GitHub Release](https://img.shields.io/github/v/release/ThothDroid/SESHAT?color=%23F05032)](https://github.com/ThothDroid/SESHAT/releases/latest)

# SESHAT (Symbol Engine for Sketched Hieroglyph Art Transformation)
A simple Android library for exporting Egyptian hieroglyphic texts into images. It supports the `GlyphX` encoding as input and exports into the following file formats:
- `SVG` (Scalable Vector Graphics)
- `PNG` (Portable Network Graphics)
- `JPEG` (Joint Photographic Experts Group)

*This library is part of the [Egyptian Writer](https://github.com/ThothDroid/Egyptian_Writer) Android App.*

## Disclaimer
This library uses the `GlyphX` and code for encoding Hieroglyphs.
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
Download the [`SESHAT_versionname.aar`](https://github.com/ThothDroid/SESHAT/releases/latest) file from the latest release, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/SESHAT_versionname.aar"))
}
```

> [!IMPORTANT]
> If you renamed the `.aar` file you also have to change the name in the dependencies

## Usage
First, you have to initialize the library:
```
Seshat seshat = new Seshat(context, new Handler(context.getMainLooper()));
```
If you do not use the `SeshatListener`, you do not have to provide a `Handler` (you can just type `null`). 
But for Thread safety, you should pass in the Handler of the `UI-Thread`, so that you can do UI Actions in the Listener, 
even when the exporting is happening on another Thread. Of course, you can also provide a Handler of a different Thread.<br>
If you are a beginner, leave it like in the example.



### export settings
Most of the settings come from the `BoundProperty` of the [`MAAT`](https://github.com/ThothDroid/MAAT)-Library, which calculates the positions of the signs.
Here I will explain all the possible export settings:
#### basic settings
- `textSize`: Height of one line of big hieroglyphs. *Default: `100px`*
- `verticalOrientation`: This parameter can only have three values and defines the vertical position of smaller signs (like `n`): *Default: `1` (Middle)*
    - `0` (Top): Put signs to the top of the line
    - `1` (Middle): Center signs vertically
    - `2` (Bottom): Drop signs on Baseline
- `writingDirection`: This parameter only has two possible values and determines if the signs should be written from left to right or from right to left: *Default: `0` (Left to right)*
    - `0` (Left to right): Write like in most European languages
    - `1` (Right to left): Write like for example in Arabic
- `writingLayout`: This parameter only has two possible values and determines if the signs should be written in lines or in columns: *Default: `0` (Lines)*
    - `0` (Lines): Write signs in lines
    - `1` (Columns): Write signs in columns
- `drawLines`: If enabled, lines are drawn between the lines or columns of the text. *Default: `false`*
- `roundLineCap`: If true, the ends of the lines between the text are rounded up. *Default: `true`*
- `lineThickness`: How thick the lines between the text are. *Default: `2`*
- `pagePaddingLeft`: The amount of free space left to the text. *Default: `0`*
- `pagePaddingRight`: The amount of free space right to the text. *Default: `0`*
- `pagePaddingTop`: The amount of free space above to the text. *Default: `0`*
- `pagePaddingBottom`: The amount of free space under to the text. *Default: `0`*
- `signPadding`: This value defines the padding between two signs which are not grouped *Default: `10`*
- `layoutSignPadding`: This value defines the relative space between two signs which are in the same group *Default: `5`*
- `interLinePadding`: This is the space between the lines or columns of the text *Default: `25`*
- `backgroundColor`: Defines the background color of the image. *Default: `#ffffff`*
- `primarySignColor`: Defines the color of the hieroglyphs. *Default: `#000000`*

#### SVG settings
- `title`: Contains the content for the title element in the SVG document
- `description`: Contains the content for the description element in the SVG document
- `backgroundWithCSS`: If enabled, a style tag is attached to the `<svg>`-root Tag which colors the background. If disabled, a rect with width and height of `100%` is generated. For editing svg images or using them in the internet, the first option is more elegant. But the second option is compatible with more of the SVG renderers.
- `backgroundTransparent`: if true, the background will be transparent. If false, it will be filled with the specified background color

#### JPEG settings
- `outputFile`: The File to write the JPG image to
- `width`: The width of the image in pixels
- `height`: The height of the image in pixels
- `quality`: Value between `0` and `100`. It defines, how much the image is getting compressed. Smaller values mean smaller file size and bigger values mean better image quality (e.g. fewer artifacts)
- `autoSizeRatio`: If enabled, the `height` value is ignored and replaced by a calculated height, based on the `width` and the ratio of the content.

#### PNG settings
Mostly the same as the JPEG settings, but with the `backgroundTransparent` setting from SVG.

### Changing values at runtime
To change the Attributes during runtime, you can call the `getter` and `setter` for the values. For example:
```
binding.thothView.setTextSize(200);     // Set the value for the textSize in pixels
```

You can also use some other functions which are explained here:

- `getGlyphXText()`: Returns the hieroglyphic text as `GlyphX`-String.
- `getMdCText()`: Returns the hieroglyphic text as `MdC`-String.
- `isAltTextTested()`: Returns whether the view is in `AltTextTesting`-Mode or not.
- `getLineThickness()`: Returns the thickness of the lines drawn between the columns / lines of the text in pixels
- `isDrawLines()`: Returns whether there should be lines drawn between the columns / lines of the text
- `getPagePaddingLeft()`: Returns the left padding of the text as a whole
- `getPagePaddingTop()`: Returns the top padding of the text as a whole
- `getPagePaddingRight()`: Returns the right padding of the text as a whole
- `getPagePaddingBottom()`: Returns the bottom padding of the text as a whole
- `getSignPadding()`: Returns the padding between signs outside of groups
- `getLayoutSignPadding()`: Returns the padding between signs inside a group
- `getInterLinePadding()`: Returns the padding between the lines / columns of the text

- `setGlyphXText(String glyphX)`: Change the hieroglyphic text during runtime by transferring the text as `GlyphX`-String.
- `setGlyphXText(org.w3c.dom.Document glyphX)`: Change the hieroglyphic text during runtime by transferring the text as `GlyphX`-XML-Document.
- `setMdCText(String mdc)`: Change the hieroglyphic text during runtime by transferring the text as `MdC`-String.
- `testAltText(boolean b)`: Enable or disable `AltTextTesting`-Mode. If `AltTextTesting`-Mode is enabled, the hieroglyph will not be rendered
  and the view will act like if the hieroglyphs are currently loaded into memory. This is useful for testing how the alternative text looks like.
- `setLineThickness(float lineThickness)`: Sets the thickness of the lines drawn between the columns / lines of the text in pixels
- `setDrawLines(boolean drawLines)`: Determines if there should be drawn lines between the columns / lines of the text
- `setPagePaddingLeft(float pagePaddingLeft)`: Sets the left padding of the text as a whole
- `setPagePaddingTop(float pagePaddingTop)`: Sets the top padding of the text as a whole
- `setPagePaddingRight(float pagePaddingRight)`: Sets the right padding of the text as a whole
- `setPagePaddingBottom(float pagePaddingBottom)`: Sets the bottom padding of the text as a whole
- `setSignPadding(float signPadding)`: Sets the padding between signs outside of groups
- `setLayoutSignPadding(float layoutSignPadding)`: Sets the padding between signs inside a group
- `setInterLinePadding(float interLinePadding)`: Sets the padding between the lines / columns of the text

> [!NOTE]
> Currently the lines between the columns / lines of the texts are not drawn

> [!NOTE]
> Currently the view isn't displaying the alternative text correctly.\
> Especially when the `altTextSize` is very big, the text is not centered vertically.