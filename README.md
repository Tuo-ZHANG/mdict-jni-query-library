# mdict-jni-query-library
mdict (\*.mdx) file reader based on [dictlab/mdict-cpp](https://github.com/dictlab/mdict-cpp) written by [terasum](https://github.com/terasum).

Several changes have been made with respect to the original work and some bugs have been fixed. The JNI layer, which the original library doesn't provided, is implemented by me.

## Usage
1. Making sure you have both NDK and cmake in Android studio, the query wouldn't work without them as they are the prerequisites of JNI. 

2. Import this Gradle project with Android Studio and connect your phone to the computer, make sure USB debugging is enabled.

3. run the APP on your phone, usually, the APP will fail to run properly at the first attempt, this is because the mdx file has not been put into the scoped storage of the APP 
yet.

4. put the mdx file into the scoped storage of the APP, in my case, I have put the mdx into /storage/emulated/0/Android/data/com.example.mdictjnilibrary/files/

5. check the MainActivity file in the project, you should modify the global variable path and the global variable word. The path is your mdx file name and the word is the search item. Run the APP 
again you will see that the returned HTML string is successful rendered.
## Note
The query doesn't seem to work on some of the mdx file I have tested, this is currently under my investigation.

## Acknowledgment
Many thanks to [terasum](https://github.com/terasum) for helping me understand his library and the consultation he provided. 

## Reference
https://bitbucket.org/xwang/mdict-analysis/src/master/

https://github.com/zhansliu/writemdict

https://github.com/terasum/js-mdict

https://github.com/dictlab/mdict-cpp



