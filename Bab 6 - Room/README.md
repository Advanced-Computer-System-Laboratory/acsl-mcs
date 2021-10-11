# BAB 6 - Room 

<img align="left" src="../images/logo.png" width="400">
<img align="left" src="../images/logo_ug.jpg" width="60">
<a href="https://github.com/fahmisbas">
  <img align="right" src="../images/Github.png" width="30">
</a>
<a href="https://www.linkedin.com/in/fahmisbas/">
  <img align="right" src="../images/LinkedIn.png" width="30">
</a>
<img align="right" src="../images/FahmiSulaimanBaswedan.png" width="200">
<br/><br/><br/><br/>

## Tujuan
Dapat menyimpan data secara persisten menggunakan database lokal.

## Teori
### Database dengan Room
Database merupakan kumpulan data terstruktur yang dapat diambil, disimpan, diubah, dihapus dan ditelusuri. Kumplan data tersebut bersifat persisten karena ditulis langsung di dalam Disk atau SSD sehingga tetap tersedia selama belum dihapus, tidak seperti data yang berada di memory yang hilang saat berganti sesi.

Terdapat berbagai macam software pengelola database diantaranya adalah MySQL, Oracle, dan SQLite. Penyimpanan database dalam Aplikasi Android biasa ditangani oleh SQLite, namun penggunaan SQLite secara langsung sudah mulai ditinggalkan karena sudah hadir library yang dapat memanfaatkan SQLite dengan lebih optimal yaitu Room. Room adalah library yang digunakan untuk menyederhanakan penggunaan SQLite. Dengan menggunakan Room, proses transaksi di dalam SQLite menjadi lebih ringkas.


### Komponen pada Room

__Database__ - Sebagai perantara koneksi ke database.

__DAO (Data Access Object)__ - Kumpulan operasi untuk meng-akses data. Create, Read, Update & Delete (CRUD)

__Entity__ - Representasi tabel pada database.

<p align="center">
  <img src="images/room_architecture.png">
</p>


## Codelab
1. Buatlah Project baru di Android Studio dengan ktriteria berikut :

| Field     | Isian |
| ---      | ---       |
| Nama Project  | **NoteApp**   |
| Target & Minimum Target SDK  | **Phone and Tablet, API level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

2. Buka `Gradle.build(Module)` dan tambahkan perintah berikut di dalam block `dependencies`.

```gradle
dependencies {
  
    ....

    implementation 'androidx.room:room-runtime:2.3.0'
    annotationProcessor 'androidx.room:room-compiler:2.3.0'
    
}

```

2. Lakukan sinkronisasi.
<p align="left">
  <img width="500" src="images/build gradle.PNG">
</p>

3. Sebelum membuat database, kita akan membuat __Entity__ terlebih dahulu. Buatlah `class` dengan nama `NoteEntity`, dan import berkas-berkas berikut.

```java
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
```

4. Kemudian kondisikan berkas `NoteEntity` seperti di bawah ini.

```java
//Mendefinisikan nama tabel
@Entity(tableName = "note_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    private int note_id;

    private String title;

    @ColumnInfo(name = "note_content")
    private String content;

    public NoteEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getTitle() {
        return title;
    }
    
    public String getContent() {
        return content;
    }
}
```

5. Setelah entity terbuat selanjutnya membuat __DAO__. Buat `interface` baru dengan nama `NoteDAO`, kemudian lakukan import berkas-berkas berikut.

```java
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
```

6. Selanjutnya kondisikan berkas `NoteDao` seperti di bawah ini.

```Java
@Dao
public interface NoteDao {

    //Operasi untuk mendapatkan semua data yang tersimpan di dalam tabel note_table.
    @Query("SELECT * FROM note_table")
    List<NoteEntity> getAll();

    
    //OnConlictStrategy.REPLACE digunakan untuk menimpa atau mengganti data yang sudah ada. 
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //Operasi untuk memasukkan data kedalam tabel note_table.
    void insert(NoteEntity note);

    //Operasi untuk menghapus data di dalam tabel note_table.
    @Delete
    void delete(NoteEntity note);

}
```

7. Setelah __Entity__ dan __DAO__ terbuat, berikutnya buat  __Database__. Buat `class` baru dengan nama `NoteDatabase` kemudian kondisikan berkas tersebut seperti di bawah ini.

```Java
/*
- Menggunakan class abstrak dan melakukan extend RoomDatabase.
- Mendifinisikan tabel yang digunakan yakni Note Entitiy.
- Mendefinisikan versi database.
 */
@Database(entities = {NoteEntity.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase  {

    //Untuk memanggil DAO
    public abstract NoteDao getNoteDao();

    private static NoteDatabase noteDB;

    //Memanggil instance database menggunakan singleton pattern
    public static NoteDatabase getInstance(Context context) {
        if (noteDB == null) {
            noteDB = buildDatabaseInstance(context);
        }
        return noteDB;
    }

    private static NoteDatabase buildDatabaseInstance(Context context) {
      //databaseBuilder() digunakan untuk membangun database.
        return Room.databaseBuilder(context,
                NoteDatabase.class, //class abstrak yang dianotasikan @Database
                "NoteDB.db") // nama dari file database
                .allowMainThreadQueries().build(); // Mengizinkan query di dalam main thread
    }
}
```

8. Buka `activity_main.xml` kemudian masukan kode berikut.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:layout_margin="20dp"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/edt_title"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Judul"/>

    <EditText
        android:id="@+id/edt_content"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:hint="Konten"/>

    <Button
        android:id="@+id/btn_save"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="Simpan"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_note"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:orientation="vertical"/>

</LinearLayout>

```

9. Selanjutnya kita akan mengimplementasi penggunaan database. Buka `MainActivity` dan lakukan import pada berkas-berkas berikut.

```java
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.ref.WeakReference;
```

10. Tambahkan kode berikut di dalam  `MainActivity`. Melakukan inisialisasi pada setiap objek.
```java
public class MainActivity extends AppCompatActivity {

    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(MainActivity.this);
        EditText edtTitle = findViewById(R.id.edt_title);
        EditText edtContent = findViewById(R.id.edt_content);
        Button btnSave = findViewById(R.id.btn_save);
        RecyclerView rvNote = findViewById(R.id.rv_note);

    }
}
```

11. Tambahkan operasi menyimpan data.

```java
public class MainActivity extends AppCompatActivity {

    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEntity note = new NoteEntity(edtTitle.getText().toString(),
                        edtContent.getText().toString());

                // menambahkan thread baru untuk memasukan data
                new InsertTask(MainActivity.this,note).execute();
            }
        });
    }

    private class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private NoteEntity note;
        private WeakReference<MainActivity> activityReference;


        // WeakReference agar dapat mengakses properti yang berada di MainActivity.
        InsertTask(MainActivity context, NoteEntity note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        // Memasukan data ke dalam database menggunakan background thread.
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        // Method ini dapat digunakan setelah pekerjaan di background thread selesai dan menggunakan main thread kembali.
        @Override
        protected void onPostExecute(Boolean bool) {
            Toast.makeText(activityReference.get(), "Berhasil ditambahkan", Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
        }
    }
}
```


## LP


## LA
