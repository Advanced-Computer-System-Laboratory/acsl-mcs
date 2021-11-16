# BAB 6 - Room 

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

5. Setelah entity terbuat selanjutnya membuat __DAO__. Buat `interface` baru dengan nama `NoteDao`, kemudian lakukan import berkas-berkas berikut.

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

7. Setelah __Entity__ dan __DAO__ terbuat, berikutnya kita perlu membuat berkas  __Database__. Buat `abstract class` baru dengan nama `NoteDatabase`. Tambahkan beberapa import di bawah ini 

```java
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
```

kemudian kondisikan berkas tersebut seperti di bawah ini.

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

10. Tambahkan kode berikut di dalam  `MainActivity`. 

```java

import...

public class MainActivity extends AppCompatActivity {

    private NoteDatabase noteDatabase;
    private RecyclerView rvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Membuat instance database, agar dapat digunakan.
        noteDatabase = NoteDatabase.getInstance(MainActivity.this);

        //Inisialisasi views.
        EditText edtTitle = findViewById(R.id.edt_title);
        EditText edtContent = findViewById(R.id.edt_content);
        Button btnSave = findViewById(R.id.btn_save);
        rvNote = findViewById(R.id.rv_note);


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
        
        //Menyimpan note.
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEntity note = new NoteEntity(edtTitle.getText().toString(),
                        edtContent.getText().toString());

                // Menambahkan background thread agar dapat memasukkan data ke dalam database.
                new InsertTask(MainActivity.this,note).execute();
            }
        });
    }

    private class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private NoteEntity note;
        private WeakReference<MainActivity> activityReference;


        //WeakReference agar dapat mengakses properti yang berada di MainActivity.
        InsertTask(MainActivity context, NoteEntity note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        //Memasukan data ke dalam database menggunakan background thread.
        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        //Method ini dapat digunakan setelah pekerjaan di background thread selesai dan menggunakan main thread kembali.
        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                Toast.makeText(activityReference.get(), "Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        }
    }
}
```

12. Setelah menyimpan data kita perlu menampilkannya menggunakan `RecyclerView`. Buat layout baru dengan nama `item_note.xml` dan kondisikan layout tersebut seperti di bawah ini.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="10dp">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="12dp">

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentStart="true"
            android:layout_toStartOf="@id/btn_delete"
            android:textAppearance="@style/TextAppearance.AppCompat.Title"
            android:textSize="18sp"
            tools:text="lorem ipsum dolor sit amet" />

        <TextView
            android:id="@+id/tv_content"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_below="@id/tv_title"
            android:layout_alignParentStart="true"
            android:layout_toStartOf="@id/btn_delete"
            tools:text="lorem ipsum dolor sit amet" />

        <ImageView
            android:id="@+id/btn_delete"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_alignParentEnd="true"
            android:layout_centerVertical="true"
            android:clickable="true"
            android:focusable="true"
            android:src="@drawable/ic_delete" />

    </RelativeLayout>


</androidx.cardview.widget.CardView>
```
13. Buat `class` baru dengan nama `NoteListAdapter` kemudian import berkas-berkas berikut.

```java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
```
14. Kondisikan `NoteListAdapter` seperti di bawah ini.

```java
public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private ArrayList<NoteEntity> notes = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    //Memasukkan data note kedalam RecyclerView.
    public NoteListAdapter(List<NoteEntity> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    //Menginisialisai Interface OnItemClickListener agar dapat mengakses data note
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //Memuat layout di RecyclerView.
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    //Menampilkan note ke dalam view (TextView) dan menghadle klik pada item (Button). 
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteEntity note = notes.get(position);

        holder.tvTitle.setText(note.getTitle());
        holder.tvContent.setText(note.getContent());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemDelete(note);
            }
        });
    }

    //Menentukan berapa banyak data yang ditampilkan di RecyclerView.
    @Override
    public int getItemCount() {
        return notes.size(); //notes.size() mengembalikan jumlah data yang berada di list notes
    }


    //Melakukan inisialisasi pada views
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnDelete;
        TextView tvTitle, tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

    interface OnItemClickListener {
        void onItemDelete(NoteEntity note);
    }
}

```
15.  Kemudian kembali ke `MainActivity` dan tambahkan fungsi hapus note. Kondisikan `MainActivity` seperti di bawah ini.

```java
public class MainActivity extends AppCompatActivity {

    ...
    private NoteListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ...

        //Menambahkan background thread agar dapat melakukan operasi delete
        new RetrieveTask(this).execute();

    }



    private static class RetrieveTask extends AsyncTask<Void, Void, List<NoteEntity>> {


        //Reference dari MainActivity agar dapat mengakses properti pada Activity 
        private WeakReference<MainActivity> activityReference;

        
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        //Melakukan operasi background
        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            // Jika MainActivity sudah terbuat atau MainActivity bukan null
            if (activityReference.get() != null)
                //Ambil data yang terdapat di database menggunakan NoteDao dan memanggil getAll() 
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        //Melakukan operasi foregound
        @Override
        protected void onPostExecute(List<NoteEntity> notes) {
            // Jika note bukan null dan ukuran dari list note lebih dari 0 (terdapat note)
            if (notes != null && notes.size() > 0) {

                //Reference dari MainActivity agar dapat mengakses properti pada Activity tersebut
                MainActivity mainActivityReference = activityReference.get();


                //Membuat recyclerview adapter dan menampilkan data
                mainActivityReference.adapter = new NoteListAdapter(notes);
                mainActivityReference.rvNote.setLayoutManager(new LinearLayoutManager(activityReference.get()));
                mainActivityReference.rvNote.setAdapter(activityReference.get().adapter);

                //Mendeteksi klik pada tombol hapus
                mainActivityReference.adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemDelete(NoteEntity note) {
                        //Menghapus data
                        mainActivityReference.noteDatabase.getNoteDao().delete(note);

                        //Menghancurkan Activity kemudian membuat Activity kembali, seperti fungsi refresh.
                        mainActivityReference.finish();
                        mainActivityReference.startActivity(mainActivityReference.getIntent());

                        Toast.makeText(mainActivityReference, "Berhasil dihapus!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    ...
}
```

16. Maka keseluruhan kode pada `MainActivity` akan terlihat seperti ini.
```java
package com.acsl.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NoteDatabase noteDatabase;
    private RecyclerView rvNote;

    private NoteListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteDatabase = NoteDatabase.getInstance(MainActivity.this);
        EditText edtTitle = findViewById(R.id.edt_title);
        EditText edtContent = findViewById(R.id.edt_content);
        Button btnSave = findViewById(R.id.btn_save);
        rvNote = findViewById(R.id.rv_note);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteEntity note = new NoteEntity(edtTitle.getText().toString(),
                        edtContent.getText().toString());

                new InsertTask(MainActivity.this, note).execute();
            }
        });

        new RetrieveTask(this).execute();

    }


    

    private static class RetrieveTask extends AsyncTask<Void, Void, List<NoteEntity>> {

        private WeakReference<MainActivity> activityReference;

        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().noteDatabase.getNoteDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<NoteEntity> notes) {
            if (notes != null && notes.size() > 0) {

                MainActivity mainActivityReference = activityReference.get();


                mainActivityReference.adapter = new NoteListAdapter(notes);
                mainActivityReference.rvNote.setLayoutManager(new LinearLayoutManager(activityReference.get()));
                mainActivityReference.rvNote.setAdapter(activityReference.get().adapter);

                mainActivityReference.adapter.setOnItemClickListener(new 
                
                NoteListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemDelete(NoteEntity note) {
                        mainActivityReference.noteDatabase.getNoteDao().delete(note);

                        mainActivityReference.finish();
                        mainActivityReference.startActivity(mainActivityReference.getIntent());

                        Toast.makeText(mainActivityReference, "Berhasil dihapus!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }
    }

    private class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private NoteEntity note;
        private WeakReference<MainActivity> activityReference;


        InsertTask(MainActivity context, NoteEntity note) {
            activityReference = new WeakReference<>(context);
            this.note = note;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            activityReference.get().noteDatabase.getNoteDao().insert(note);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                Toast.makeText(activityReference.get(), "Berhasil ditambahkan", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
            }
        }
    }


}
```
17. Jalankan Aplikasi.





## LP
1. Jelaskan apa itu Web API!
2. Sebutkan dan jelaskan method yang terdapat pada Http!
3. Jelaskan apa itu JSON!
4. Jelaskan kegunaan dari GSON!

## LA
Buatlah kesimpulan mengenai materi yang sudah diajarkan!
