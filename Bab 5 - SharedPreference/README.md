# BAB 5 - SharedPreference

<img align="left" src="../images/logo.png" width="400">
<img align="left" src="../images/logo_ug.jpg" width="60">

<a href="https://github.com/fahmisbas">
  <img align="right" src="../images/Github.png" width="30">
</a>
<a href="https://www.linkedin.com/in/fahmisbas/">
  <img align="right" src="../images/LinkedIn.png" width="30">
</a>
<img align="right" src="../images/FahmiSulaimanBaswedan.png" width="250">
<br/><br/><br/><br/>

## Tujuan
Dapat menyimpan data berukuran kecil secara persisten.
## Teori
### SharedPreference
`SharedPreference` merupakan objek yang dapat digunakan untuk meyimpan data berukuran kecil secara persisten, seperti data E-mail dan Kata sandi. Metode penyimpanannya juga cukup mudah, yaitu dengan menggunakan pasangan _key-value_. `SharedPreference` hanya dapat digunakan untuk menyimpan String, Double, Integer, Long, atau Boolean sederhana dan tidak direkomendasikan untuk menyimpan data yang kompleks. Untuk menyimpan data yang besar dapat menggunakan SQLite.

Terdapat dua Method yang dapat digunakan dalam membuat `SharedPreference` yaitu `getSharedPreference()` dan `getPreference()`. 

- `getSharedPreference()` memiliki dua parameter yaitu nama dari  `SharedPreference` dan cakupannya. `SharedPreference` dapat dipanggil menggunakan nama yang sudah didefinisikan di dalam parameter. 

  `SharedPreferences sharedPreference = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);`
 
- `getPreference()` secara default namanya adalah Activity dimana ia dibuat, dan biasa digunakan di dalam Activity tersebut saja.

  `SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);`

### Membuat SharedPreference

Buat objek `SharedPreference` kemudian beri cakupan Private di dalam parameter.
Buat `SharedPrefence.Editor` dengan memanggil `edit()` pada objek `SharedPreference` yang telah dibuat. Masukan data dengan editor menggunakan Method `putInt()` dan `putString()`. Gunakan Method `commit()` atau `apply()` untuk menyimpan data.

```java
 SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
 SharedPreferences.Editor editor = sharedPref.edit();
 editor.putString("nama", "Kerry"); // putString(key, value)
 editor.putInt("npm", "12818398")
 editor.commit();
```

### Membaca Data dari SharedPreference

Untuk mengambil nilai dari `SharedPreference`, panggil Method seperti `getInt()` dan `getString()`, dengan memberikan key untuk nilai yang diinginkan, dan bisa juga memberi nilai default jika key tidak ada.

```java
 SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
 String nama = sharedPref.getString("nama", "Nama tidak terdaftar"); //(key, defaultValue)
 int npm = sharedPref.getInt("npm", 0) 
```


## Codelab
1. Buatlah Project baru di Android Studio dengan ktriteria berikut :

| Field     | Isian |
| ---      | ---       |
| Nama Project  | **MySharedPref**   |
| Target & Minimum Target SDK  | **Phone and Tablet, API level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

2. Buka file `activit_main.xml` kemudian ganti keseluruhan kode dengan yang di bawah ini :

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <EditText
        android:id="@+id/edt_nama"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:hint="Nama"
        android:inputType="text" />

    <EditText
        android:id="@+id/edt_npm"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:hint="NPM"
        android:inputType="number" />

    <Button
        android:id="@+id/btn_simpan"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="20dp"
        android:text="Simpan" />

</LinearLayout>
```
3. Buka berkas `MainActivity` dan inisialisai variabel berikut.
```Java
public class MainActivity extends AppCompatActivity {

    private SharedPreferences mahasiswaPref;
    private EditText edtNama;
    private EditText edtNpm;

    public static final String SHARED_PREF_NAME = "mahasiswa";
    public static final String PREF_NAME_KEY = "nama";
    public static final String PREF_NPM_KEY = "npm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNama = findViewById(R.id.edt_nama);
        edtNpm = findViewById(R.id.edt_npm);
        Button btnSimpan = findViewById(R.id.btn_simpan);

        mahasiswaPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }
}
```
4. Tambahkan event klik pada `btnSimpan` dan panggill Method `simpanIdentitas()` di dalamnya.

```java
public class MainActivity extends AppCompatActivity {
    
    ...
 
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanIdentitas();
            }
        });
    }
}
```

5. Tambahkan Method `simpanIdentitas()` yang digunakan untuk menyimpan data ke dalam `SharedPreferences` menggunakan Method `apply()`

```java
public class MainActivity extends AppCompatActivity {
    
    ...
 
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

    }

    private void simpanIdentitas() {
        String nama = edtNama.getText().toString();
        int npm = Integer.parseInt(edtNpm.getText().toString());

        SharedPreferences.Editor editor = mahasiswaPref.edit();
        editor.putString(PREF_NAME_KEY, nama);
        editor.putInt(PREF_NPM_KEY, npm);
        editor.apply();

        pindahActivity();
    }
}
```
6. Tambahkan Method `pindahActivity()` agar mempermudah dalam melakukan perpindahan Activity hanya dengan memanggil method tersebut.

```java
public class MainActivity extends AppCompatActivity {
    
    ...
 
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

    }

    ...

    private void pindahActivity() {
        Intent intent = new Intent(MainActivity.this, MahasiswaActivity.class);
        startActivity(intent);
    }
}
```

7. Tambahkan kode berikut di dalam Method `onCreate()` agar dapat memeriksa apakah data sudah tersimpan di dalam `SharedPreference` atau belum. Apabila sudah tersimpan lakukan perpindahan Activity.

```java
public class MainActivity extends AppCompatActivity {
    
    ...
 
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        mahasiswaPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String nama = mahasiswaPref.getString(PREF_NAME_KEY, null);
        int npm = mahasiswaPref.getInt(PREF_NPM_KEY, 0);
        if (nama != null && npm != 0) {  // Memeriksa apakah sudah ada data atau belum. Jika sudah ada maka pindah Activity
            pindahActivity();
        }

        ...

    }

    ...

}
```
8. Buat Activity baru dengan nama `MahasiswaActivity`.
9. Buka berkas `activity_mahasiswa.xml` dan ganti keseluruhan kode di dalamnya dengan yang di bawah ini.

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MahasiswaActivity">

    <TextView
        android:id="@+id/tv_nama"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:gravity="center_horizontal"
        android:textSize="25sp"
        tools:text="ACSL "/>

    <TextView
        android:id="@+id/tv_npm"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:layout_below="@id/tv_nama"
        android:layout_marginTop="10dp"
        android:textSize="20sp"
        tools:text="112128"/>

</RelativeLayout>

```
10. Buka `MahasiswaActivity` dan tambahkan kode berikut di dalam `onCreate()`.

```Java
public class MahasiswaActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        TextView tvNama = findViewById(R.id.tv_nama);
        TextView tvNpm = findViewById(R.id.tv_npm);

        SharedPreferences mahasiswaPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);  

        String nama = mahasiswaPref.getString(PREF_NAME_KEY, null);
        int npm = mahasiswaPref.getInt(PREF_NPM_KEY, 0);


        if (nama != null && npm != 0) {
            tvNama.setText(nama);
            tvNpm.setText(String.valueOf(npm));
        }
    }
}

```
11. Jalankan aplikasi.

## LP


## LA
