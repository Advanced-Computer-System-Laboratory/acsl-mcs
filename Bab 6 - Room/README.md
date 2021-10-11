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

4. Kemudian masukan kode berikut di dalam `NoteEntity`.

```java
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

    public String getTitle() {
        return title;
    }
    
    public String getContent() {
        return content;
    }
}
```

5. Setelah entity terbuat selanjutnya kita akan membuat __DAO__. Buat `interface` baru dengan nama `NoteDAO`, kemudian lakukan import berkas-berkas berikut.

```java
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
```

6. Selanjutnya masukan kode berikut di dalam `NoteDao`.

```Java
@Dao
public interface NoteDao {

    //operasi untuk mendapatkan semua data yang tersimpan di dalam tabel note_table.
    @Query("SELECT * FROM note_table")
    List<NoteEntity> getAll();

    
    //OnConlictStrategy.REPLACE digunakan untuk menimpa data yang sudah ada. 
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //operasi untuk memasukkan data kedalam tabel note_table.
    void insert(NoteEntity note);

    //operasi untuk menghapus data di dalam tabel note_table.
    @Delete
    void delete(NoteEntity note);

}
```

7. Setelah __Entity__ dan __DAO__ terbuat, berikutnya kita akan membuat  __Database__. Buat `class` baru dengan nama `NoteDatabase`. Kemudian Masukan kode berikut.

```Java
/*
- Menggunakan class abstrak dan melakukan extend RoomDatabase.
- Mendifinisikan tabel yang digunakan yakni Note Entitiy.
- Mendefinisikan versi database.
 */
@Database(entities = {NoteEntity.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase  {

    //untuk memanggil DAO
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
        return Room.databaseBuilder(context,
                NoteDatabase.class, //class abstrak yang dianotasikan @Database
                "NoteDB.db") // nama dari file database
                .allowMainThreadQueries().build(); // Mengizinkan query di dalam main thread
    }
}
```

8. 

## LP


## LA
