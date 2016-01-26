lrmlibrary
===================================


Gradle 版本
-----------------------------------
1.classpath 'com.android.tools.build:gradle:1.3.0'<br />
2.版本 - gradle-2.4-all.zip<br />

新项目引用
-----------------------------------
Project-build.gradle

     buildscript {
         repositories {
             //从中央库里面获取依赖
             jcenter()
         }
         dependencies {
             classpath 'com.android.tools.build:gradle:1.3.0'
         }
     }
     
     allprojects {
         repositories {
             jcenter()
             //远程仓库
             //maven { url "https://github.com/vdolrm/lrmlibrary_aar/raw/master"}
         }
     }

App-build.gradle:
    android {
        //配置信息
        packagingOptions {
        exclude 'META-INF/services/javax.annotation.processing.Processor'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/NOTICE.txt'
        }
    }

Gradle依赖
-----------------------------------
App-build.gradle:<br />

   compile 'vdolrm.lrmlibrary:base:1.0.1' <br /> 


AndroidManifest 权限
-----------------------------------
<!-- SDCard中创建与删除文件权限 --><br />
    
    <uses-permissioandroid:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
<!-- 读写权限 --><br />
    
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/><br />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/><br />
<!-- 网络权限 --><br />

    <uses-permission android:name="android.permission.INTERNET" />

Wiki
-----------------------------------
later

引用架构项目 - 官网地址
-----------------------------------
笨鸟雅思 : http://www.benniaoyasi.com/

版本更新说明
-----------------------------------
###v1.0.1 - 正在维护中
1.初始版<br />


混淆过滤--待修改
-----------------------------------
#### 保留签名，解决泛型问题<br />
-keepattributes Signature<br />

#### J2W
-keep class j2w.team.** { *; } <br />
-dontwarn j2w.team.**<br />

#### JAVAX
-dontwarn javax.annotation.**<br />
-keep class javax.annotation.**<br />
-dontwarn javax.inject.**<br />
-keep class javax.inject.**<br />

#### View注入
-keep class * extends java.lang.annotation.Annotation { *; }<br />

#### picasso
-dontwarn com.squareup.okhttp.**<br />

#### butterknife
-keep class butterknife.** { *; }<br />
-dontwarn butterknife.internal.**<br />
-keep class **$$ViewInjector { *; }<br />
-keepclasseswithmembernames class * {<br />
    @butterknife.* <fields>;<br />
}<br />
-keepclasseswithmembernames class * {<br />
    @butterknife.* <methods>;<br />
}<br />

#### okio
-dontwarn okio.**<br />
-dontwarn in.srain.cube.**<br />
-keep class in.srain.cube.**<br />

#### EventBus
-keepclassmembers class ** {<br />
    public void onEvent(**);<br />
}<br />
-keepclassmembers class * extends j2w.team.common.event.J2WEvent {*;}<br />
-keepclassmembers class *$* extends j2w.team.common.event.J2WEvent {*;}<br />

#### GSON
-keep class sun.misc.Unsafe { *; }<br />
-keep class com.google.gson.examples.android.model.* { *; } <br />

#### 网络架构实体类
-keepclassmembers class * extends j2w.team.mvp.model.J2WModel {*;} <br />
