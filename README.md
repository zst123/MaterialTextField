MaterialTextField
=====
Backport of Android L's Text Field.
Compatible with Android 3.0 (API 11) and above. It can be used on Android 2.X by using 


Usage
-----
MaterialTextField is used like a normal EditText. Here is a sample code for XML layouts:

    <com.zst.library.material.textfield.MaterialTextField
        android:id="@android:id/button1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:text="hi"
        themeColor="#259b24"
        barAnimDuration="1000"
        flashAnimDuration="600" />

The additional attributes `themeColor`, `barAnimDuration` and `flashAnimDuration` are optional and can be omitted.


You can change those 3 variables on the fly using: <br>
`setThemeColor(YOUR_COLOR);` <br>
`setBarAnimationDuration(MILLISECONDS)` <br>
`setFlashAnimationDuration(MILLISECONDS)` <br>

For more info, refer to the code itself.


License
--------

Copyright 2014 zst123

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
