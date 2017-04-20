-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontnote
-dontwarn
-dontshrink
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-libraryjars <java.home>/lib/rt.jar
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment

-keep class org.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class android.support.v13.app.** { *; }
-keep interface android.support.v13.app.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-keep class com.google.android.** { *; }
-keep interface com.google.android.** { *; }
-keep class com.google.gson.** { *; }
-keep class com.google.gson.reflect.** { *; }
-keep interface com.google.gson.** { *; }


# Gson specific
-keepattributes Signature
-keep class sun.misc.Unsafe { *; }

-keep class java.util.** { *; }
-keep class * implements java.io.Serializable { *; }
-keepattributes *Annotation*
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable { *; }

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# --- Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class com.android.gms.** { *; }
-keep interface com.android.gms.** { *; }
-keep class com.google.android.gms.** { *; }
-keep interface com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**


# Keep native methods
-keepclassmembers class * {
    native <methods>;
}


# --- Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


# Android Data Binding
-keep class com.android.binding.** { *; }
-keep interface com.android.binding.** { *; }


# Support v7 preferences
-keep public class android.support.v7.preference.Preference { *; }
-keep public class * extends android.support.v7.preference.Preference { *; }

-dontwarn javax.annotation.**
-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn io.reactivex.**
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# Apache legacy
-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-keep class android.net.http.** { *; }
-dontwarn android.net.http.**

# Test dependencies
-dontwarn org.mockito.**
-dontwarn sun.reflect.**
-dontwarn android.test.**

-dontwarn org.hamcrest.**
-dontwarn android.test.**
-dontwarn android.support.test.**

-keep class org.hamcrest.** {
   *;
}

-keep class org.junit.** { *; }
-dontwarn org.junit.**

-keep class junit.** { *; }
-dontwarn junit.**

-keep class sun.misc.** { *; }
-dontwarn sun.misc.**

-dontwarn org.assertj.**
