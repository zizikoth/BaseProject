# https://www.jianshu.com/p/5049f53c0b75

-keep class * implements androidx.viewbinding.ViewBinding {
    *;
}

-keepclassmembers public class * extends androidx.lifecycle.ViewModel {
    public <init>(...);
}