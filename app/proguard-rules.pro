# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class com.videosaver.ui.main.home.browser.BrowserFragment$WebViewJavaScriptInterface {
   public *;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn org.immutables.value.Value$Default
-dontwarn org.immutables.value.Value$Immutable
-dontwarn org.immutables.value.Value$Style$BuilderVisibility
-dontwarn org.immutables.value.Value$Style$ImplementationVisibility
-dontwarn org.immutables.value.Value$Style

-keep class com.yausername.youtubedl_android.** { *; }
-keep class com.liulishuo.okdownload.** { *; }
-keep class io.lindstrom.** { *; }
-keep class org.immutables.** { *; }

-keep class **.R$* {*;}

# ----------------------------------------------------------------------------
# 混淆的压缩比例，0-7
-optimizationpasses 5
# 指定不去忽略非公共的库的类的成员
-dontskipnonpubliclibraryclassmembers
# 指定混淆是采用的算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 指定外部模糊字典 proguard.txt 改为混淆文件名，下同
-obfuscationdictionary proguard-1il.txt
# 指定class模糊字典
-classobfuscationdictionary proguard-1il.txt
# 指定package模糊字典
-packageobfuscationdictionary proguard-1il.txt


-keep class org.apache.commons.** { *; }
-keepattributes *Annotation*
-keep public class * {
    public protected *;
}

#noinspection ShrinkerUnresolvedReference
-keep class com.yausername.** { *; }
-keepclassmembers class com.yausername.** { *; }

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

-keep class org.apache.commons.compress.** { *; }
-keepclassmembers class org.apache.commons.compress.** { *; }



# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.oracle.svm.core.annotate.Delete
-dontwarn com.oracle.svm.core.annotate.Substitute
-dontwarn com.oracle.svm.core.annotate.TargetClass
-dontwarn java.beans.ConstructorProperties
-dontwarn java.beans.Transient
-dontwarn java.lang.Module
-dontwarn org.graalvm.nativeimage.hosted.Feature$BeforeAnalysisAccess
-dontwarn org.graalvm.nativeimage.hosted.Feature
-dontwarn org.graalvm.nativeimage.hosted.RuntimeResourceAccess
-dontwarn org.tukaani.xz.ARMOptions
-dontwarn org.tukaani.xz.ARMThumbOptions
-dontwarn org.tukaani.xz.DeltaOptions
-dontwarn org.tukaani.xz.FilterOptions
-dontwarn org.tukaani.xz.FinishableOutputStream
-dontwarn org.tukaani.xz.FinishableWrapperOutputStream
-dontwarn org.tukaani.xz.IA64Options
-dontwarn org.tukaani.xz.LZMA2InputStream
-dontwarn org.tukaani.xz.LZMA2Options
-dontwarn org.tukaani.xz.LZMAInputStream
-dontwarn org.tukaani.xz.PowerPCOptions
-dontwarn org.tukaani.xz.SPARCOptions
-dontwarn org.tukaani.xz.SingleXZInputStream
-dontwarn org.tukaani.xz.UnsupportedOptionsException
-dontwarn org.tukaani.xz.X86Options
-dontwarn org.tukaani.xz.XZ
-dontwarn org.tukaani.xz.XZInputStream
-dontwarn org.tukaani.xz.XZOutputStream
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry