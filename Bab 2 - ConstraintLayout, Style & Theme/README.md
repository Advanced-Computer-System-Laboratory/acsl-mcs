# BAB 2 - ConstraintLayout, Style & Theme

<img align="left" src="images/logo.png" width="400">
<img align="left" src="images/logo_ug.jpg" width="60">

<a href="https://github.com/fahmisbas">
  <img align="right" src="../images/Github.png" width="30">
</a>
<a href="https://www.linkedin.com/in/fahmisbas/">
  <img align="right" src="../images/LinkedIn.png" width="30">
</a>
<img align="right" src="../images/FahmiSulaimanBaswedan.png" width="250">


<br/><br/><br/><br/>

## Tujuan
Pada bab ini kalian akan memahamai penggunaan _ConstraintLayout_ yaitu salah satu layout terbaru dari _Android Studio_. Dan juga _Style & Theme_ untuk mempermudah pembuatan layout aplikasi.

## Teori
### ConstraintLayout
_ConstraintLayout_ merupakan sebuah _layout_ yang banyak digunakan oleh pengembang aplikasi Android karena memiliki beberapa kelebihan, salah satunya adalah dapat mengatur _View_ secara flexible tanpa memerlukan _nested layout_ (Kelompok tampilan bertingkat) sehingga tercipta hierarki tampilan datar.

Contoh _Nested Layout_ :
```xml
<LinearLayout>
    <LinearLayout>
        <TextView/>
        <EditText/>
    </LinearLayout>
    <LinearLayout>
        <TextView/>
        <EditText/>
    </LinearLayout>
    <LinearLayout>
        <TextView/>
        <EditText/>
    </LinearLayout>
</LinearLayout>
```
Contoh Hirearki Datar menggunakan ConstraintLayout :
```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <TextView/>
    <EditText/>
    <TextView/>
    <EditText/>
    <TextView/>
    <EditText/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

Dengan _ConstraintLayout_ kita dapat mengatur layout menggunakan _Visual Layout Editor_ lebih mudah. Hanya dengan menarik _anchor point_ (Top, Bottom, Left, Right) yang terdapat pada View, lalu dihubungkan dengan Parent atau View lainnya. 

<p align="center">
  <img src="images/anchor point.gif">
</p>

Yang perlu diperhatikan saat menggunakan _ConstraintLayout_ adalah, _Achor Point_ yang terdapat pada _View_ baik vertikal maupun horizontal harus terhubung dengan _Anchor Point_ yang dimiliki _View_ lainnya ataupun sisi dari _Parent_.

<p align="center">
  <img width="150" src="images/cosntraint.png">
</p>

Salah satu kelebihan menggunakan _Visual Layout Editor_ adalah kode yg dibutuhkan __layout.xml__ akan otomatis di-generate oleh Android Studio, sehingga tidak menjadi masalah apabila tidak ingin membuat layout di dalam _Code Editor_. _ConstraintLayout_ juga dapat membuat tampilan UI pada aplikasi menjadi responsif (Cocok untuk bermacam macam ukuran device).

### Style
Saat membangun sebuah layout terkadang kita menemukan _View_ yang memiliki atribut sama dengan _View_ lain nya, yang dibuat dengan cara duplikasi. Hal tersebut tidaklah efisien dan memperbesar kode sehingga akan sulit untuk melakukan perubahan. Contoh nya adalah seperti ini :

`activity_main.xml`
```xml
<TextView
   android:id="@+id/title_1"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_margin="10dp"
   android:text="Title 1"
   android:textAppearance="@style/TextAppearance.AppCompat.Title"
   android:textColor="#000000"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toTopOf="parent" />


<TextView
   android:id="@+id/title_2"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_margin="10dp"
   android:text="Title 2"
   android:textAppearance="@style/TextAppearance.AppCompat.Title"
   android:textColor="#000000"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toBottomOf="@id/title_1" />
```

Solusi dari permasalahan tersebut adalah dengan menggunakan _Style_. _Style_ dapat mendefinisikan atribut umum yang gunakan oleh _View_ seperti _Font Color, Font Size, Background Color_ dan banyak lagi. Untuk membuat _Style_ baru dapat dilakukan di __res/values/style.xml__. Berikut adalah contoh implementasinya :

`style.xml`
```xml
<style name="TitleText" >
   <item name="android:layout_width">wrap_content</item>
   <item name="android:layout_height">wrap_content</item>
   <item name="android:textColor">#000000</item>
   <item name="android:textAppearance">@style/TextAppearance.AppCompat.Title</item>
</style>
```
`activity_main.xml`
```xml
<TextView
   android:id="@+id/title_1"
   style="@style/TitleText"
   android:text="Title 1"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toTopOf="parent" />

<TextView
   android:id="@+id/title_2"
   style="@style/TitleText"
   android:text="Title 2"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toBottomOf="@id/title_1" />
```
_Style_ dapat digunakan oleh satu atau lebih _View_, sehingga kode akan lebih ringkas dan lebih mudah untuk membuat perubahan tanpa harus melakukan nya secara manual.

### Theme
Theme adalah jenis _Style_ yang diterapkan pada _Activity_ dan _Application_. Saat kita menerapkan _Style_ sebagai _Theme_, maka seluruh _View_ pada _Activity_ akan menerapkan setiap atribut _Style_ yang didukungnya. Berikut contoh dari _Theme_.

`style.xml`
```xml
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```
Perhatikan kode di atas. Kita menerapkan _Style_ sebagai _Theme_ dengan mendeklarasikan __Theme.AppCompat.Light.DarkActionBar__ sebagai _Parent_ `parent="Theme.AppCompat.Light.DarkActionBar`. Untuk dapat menggunakan _Theme_ kita perlu mendefinisikan nya di dalam berkas __AndroidManifest.xml__ di dalam tag `<application>`.

```xml 
android:theme="@style/AppTheme" 
```

## Setup Project Baru
Kita akan mengimpementasi teori diatas dengan membangun layout _Login Screen_. Sekarang buka Android Studio dan buat project baru dengan kriteria sebagai berikut.

| Field     | Isian |
| ---      | ---       |
| Nama Project  | **Login Screen**   |
| Target & Minimum Target SDK  | **Phone and Tablet, Api level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

## Codelab
1. Buat file baru di dalam __res/values__ dengan cara 
__klik kanan__ pada folder __values__ lalu __-> New -> Values Resource File.__
<p align="left">
  <img width="560" src="images/create dimens.xml.png">
</p>

2. Beri nama file tersebut __dimens__ lalu tekan tombol OK.
<p align="left">
  <img width="560" src="images/dimens file.PNG">
</p>

Kita akan mendefinisikan atribut dimensi di dalam __dimens.xml__ yang nantinya akan digunakan oleh _View_, dengan memberinya nama dan nilai di dalam tag `<dimen>`.
Sekarang buka  __dimens.xml__ dan masukan kode di bawah ini di dalam tag `<resource>`.
```xml
    <dimen name="horizontal_margin">20dp</dimen>
    <dimen name="vertical_margin">20dp</dimen>

    <dimen name="app_icon_large">150dp</dimen>
    <dimen name="app_icon_default">100dp</dimen>
    <dimen name="app_icon_small">50dp</dimen>

    <dimen name="editText_width_default">250dp</dimen>

    <dimen name="button_width_large">200dp</dimen>
    <dimen name="button_width_default">150dp</dimen>
    <dimen name="button_width_small">100dp</dimen>
```

3. Buka __string.xml__ dan masukan kode di bawah ini.
 ```xml
<string name="hint_email">E-mail</string>
<string name="hint_password">Password</string>

<string name="signIn">Sign In</string>
<string name="signUp">Sign Up</string>
```

4. Sekarang tambahkan atribut warna di dalam __colors.xml__
```xml
<color name="colorGreen">#a4c639</color>
<color name="colorWhite">#ffffff</color>
<color name="colorBlue">#0000ff</color>
<color name="colorBlack">#000000</color>
```

5. Buka __style.xml__ dan masukan beberapa style beserta atributnya sebagai berikut.
```xml
<style name="LoginTextField" parent="Widget.AppCompat.EditText">
   <item name="android:layout_width">@dimen/editText_width_default</item>
   <item name="android:layout_height">wrap_content</item>
   <item name="android:textColor">@color/colorBlack</item>
</style>

<style name="LoginButton" parent="Widget.AppCompat.Button">
   <item name="android:layout_width">@dimen/button_width_default</item>
   <item name="android:layout_height">wrap_content</item>
   <item name="android:textColor">@color/colorWhite</item>
   <item name="android:background">@color/colorGreen</item>
</style>

<style name="LoginButton.small">
   <item name="android:layout_width">@dimen/button_width_small</item>
</style>

<style name="LoginButton.large">
   <item name="android:layout_width">@dimen/button_width_large</item>
</style>
```
Perhatikan penggunaan _dimens_ dan _colors_. _dimens_ digunakan untuk mendefinisikan ukuran sedangkan sedangkan _colors_ untuk mendefinisikan warna. 

Jika ingin memiliki atribut pada _View_ tertentu, dalam hal ini _Button_ maka kita perlu mewariskan __Widget.AppCompat.Button__ seperti ini `parent="Widget.AppCompat.Button"` letakkan sesudah nama _Style_. 

6. Sekarang, buka __activity_main.xml|__ dan ganti dengan kode di bawah ini.
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/img_appIcon"
        android:layout_width="@dimen/app_icon_large"
        android:layout_height="@dimen/app_icon_large"
        android:layout_marginTop="@dimen/vertical_margin"
        android:src="@mipmap/ic_launcher"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/edt_email"
        style="@style/LoginTextField"
        android:layout_marginTop="@dimen/vertical_margin"
        android:hint="@string/hint_email"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/img_appIcon" />

    <EditText
        android:id="@+id/edt_password"
        style="@style/LoginTextField"
        android:layout_marginTop="@dimen/vertical_margin"
        android:hint="@string/hint_password"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/edt_email" />


    <Button
        android:id="@+id/btn_login"
        style="@style/LoginButton"
        android:layout_margin="@dimen/vertical_margin"
        android:text="@string/signIn"
        app:layout_constraintBottom_toTopOf="@id/btn_signUp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/edt_password" />

    <Button
        android:id="@+id/btn_signUp"
        style="@style/LoginButton"
        android:layout_marginTop="@dimen/vertical_margin"
        android:text="@string/signUp"
        app:layout_constraintEnd_toEndOf="@id/btn_login"
        app:layout_constraintStart_toStartOf="@id/btn_login"
        app:layout_constraintTop_toBottomOf="@id/btn_login" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
Maka tampilan nya akan seperti ini.
<p align="center">
  <img width="150" src="images/login activity.PNG">
</p>

_*Penjelasan lebih lanjut mengenai ConstraintLayout dan penggunaan Visual Layout Editor akan dilakukan pada saat praktikum_.

Perhatikan penggunaan _Style_ di dalam __activity_main.xml__. Kita dapat memanggil _Style_ dan menggunakan atribut yang di milikinya untuk lebih dari satu _View_. 
Apabila kita mengubah atau menambah nilai atribut _Style_, maka _View_ yg menggunakan _Style_ tersebut akan ikut menyesuaikan.

7. Sekarang Buka __style.xml__, Kita akan menambahkan atribut baru di dalam _Theme_.

Berikut adalah _Theme_ yang dibuat oleh Android Studio secara otomatis saat kita membuat project baru.
```xml
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```
Sedikit penjelasan mengenai atribut yang digunakan.

- __ColorPrimary__ : Untuk memberikan warna pada app bar dan elemen UI primer lainnya .
- __ColorPrimaryDark__ : Varian _ColorPrimary_ yang lebih gelap, digunakan untuk _status bar_ (di Android 5.0+).
- __ColorAccent__ : Warna sekunder untuk kontrol seperti _checkbox_ dan _text fields_.

Sekarang kita akan menambahkan atribut baru di dalam _Theme_ tersebut, yaitu `android:windowBackground` yang berfungsi untuk memberikan warna background pada Activity.
sehingga  `AppTheme` akan terlihat seperti ini : 
```xml
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:windowBackground">@color/colorAccent</item>
    </style>
```
Selanjutnya kita perlu memastikan bahwa `AppTheme` sudah didefinisikan di dalam __AndroidManifest.xml__.

<p align="left">
  <img width="560" src="images/applied theme.PNG">
</p>

Background pada _Activity_ akan berubah menjadi warna yang digunakan colorAccent.

<p align="center">
  <img width="150" src="images/theme bg color.PNG">
</p>












