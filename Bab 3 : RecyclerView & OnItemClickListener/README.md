# BAB 3 RecyclerView & OnItemClick

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
Pada Bab ini kalian akan mempelajari cara menampilkan data dalam bentuk list menggunakan _RecyclerView_, kemudian menerima dan memproses event click pada item list.

## Teori
RecyclerView merupakan sebuah _ViewGroup_ yang digunakan untuk menampilkan koleksi data. Baik dalam bentuk _List, Grid_ ataupun _Staggered grid._ RecyclerView memiliki performa yang lebih baik di bandingkan pendahulunya yaitu _ListView_, karena ia hanya akan memuat daftar item yang perlu di tampilkan, sehingga menjadikan nya efisien dalam penggunaan memori. 

<p align="center">
  <img width="400" src="images/recyclerview vs listview.png">
</p>

Dalam implementasi nya, RecylerView membutuhkan _LayoutManager_ untuk mengatur bagaimana koleksi data di tampilkan. Kita dapat menggunakan __LinearLayoutManager__ untuk menampilkan data dalam bentuk list (Vertically & Horizontally) dan __GridLayoutManager__ untuk menampilkan data dalam bentuk grid. Sumber data yang ingin di tampilkan dapat berupa _Array, List, dan Map._ 

<p align="center">
  <img width="460" src="images/RecyclerView.png">
</p>

RecyclerView membutuhkan komponen _Adapter_ untuk dapat bekerja. _Adapter_ di gunakan untuk memuat tampilan individual _item_ pada list. Views yang terdapat pada _layout_ item di inisialisai di dalam class `ViewHolder`. Setiap _ViewHolder_ bertugas untuk menampilkan satu item.

## Setup Project Baru
Kita akan mengimpementasi teori diatas dengan membuat aplikasi yang berisikan list gambar dan nama-nama perangkat jaringan.

| Field     | Isian |
| ---      | ---       |
| Nama Project  | **Networking Devices**   |
| Target & Minimum Target SDK  | **Phone and Tablet, Api level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

## Codelab
1. Untuk dapat menggunakan RecyclerView, kita perlu menambahkan library __RecyclerView AndroidX__ di dalam project kita dengan menambahkan dependency di dalam __app/build.gradle(Module.app)__.
```gradle
dependencies {
   ...
   
   //RecyclerView
   implementation "androidx.recyclerview:recyclerview:1.1.0"
}
```
2. Karena gambar yang akan di tampilkan kedalam list berasal dari internet, kita perlu menggunakan _Third party library_ untuk dapat mendownload dan menampilkan gambar tersebut. Library yang kita gunakan adalah _Glide_. 

Tambahkan Glide ke dalam __app/build.gradle(Module.app)__ di dalam blok `dependencies`.
```gradle
dependencies {
   ...

   //Image Loader
   implementation 'com.github.bumptech.glide:glide:4.11.0'
}
```
Sehingga block `dependecies` akan terlihat seperti ini.
```gradle
...

dependencies {
   implementation fileTree(dir: 'libs', include: ['*.jar'])

   implementation 'androidx.appcompat:appcompat:1.1.0'
   implementation 'androidx.constraintlayout:constraintlayout:1.1.3'

   //RecyclerView
   implementation "androidx.recyclerview:recyclerview:1.1.0"
   //Image Loader
   implementation 'com.github.bumptech.glide:glide:4.11.0'


   testImplementation 'junit:junit:4.12'
   androidTestImplementation 'androidx.test.ext:junit:1.1.1'
   androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
}
```
Selanjutnya lakukan “sync now”.

<p align="left">
  <img width="500" src="images/build gradle.PNG">
</p>

Apabila terjadi error pastikan tidak terdapat _salah ketik_.


3. Buka __activity_main.xml__ dan kondisikan seperti yang di bawah ini.
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   android:orientation="vertical"
   tools:context=".MainActivity">

   <androidx.recyclerview.widget.RecyclerView
       android:id="@+id/rv_netdevices"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       tools:listitem="@layout/item_netdevices" />

</LinearLayout>
```
4.  Selanjutnya kita perlu membuat layout yang nantinya digunakan pada setiap item pada list.
Buat layout baru di dalam __res/layout__ dan beri nama __item_netdevice.xml__.
Kondisikan layoutnya seperti di bawah ini.
```xml 
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:padding="16dp">

   <ImageView
       android:id="@+id/img_device"
       android:layout_width="100dp"
       android:layout_height="100dp"
       android:layout_marginStart="16dp"
       android:layout_marginEnd="16dp"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toTopOf="parent"
       tools:src="@android:color/darker_gray"/>

   <TextView
       android:id="@+id/tv_name"
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:layout_marginStart="16dp"
       app:layout_constraintBottom_toBottomOf="@id/img_device"
       app:layout_constraintStart_toEndOf="@id/img_device"
       app:layout_constraintTop_toTopOf="@id/img_device"
       tools:text="Name" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
5. Berikutnya buat class model dengan nama __NetworkDevice__ dengan cara __Klik kanan pada nama package -> new -> Java Class__
<p align="left">
  <img width="350" src="images/create model class.PNG">
</p>

Setelah itu tambahkan variabel instance sebagai berikut 
```java
private String imageUrl;
private String name;
```
Buat Constructor untuk meng inisialisasi variabel.
```java
...

NetworkDevice(String name, String imageUrl) {
   this.name = name;
   this.imageUrl = imageUrl;
}
```
Buat getter dengan cara __Klik kanan__ di dalam __NetworkDevice__ kemudian __generate -> getter__.

<p align="left">
  <img width="200" src="images/generate.jpg">
</p>

Pilih variabel yang telah di buat.

<p align="left">
  <img width="200" src="images/gereate getters.PNG">
</p>
Tekan tombol OK.

Sehingga kode pada class __NetworkDevice__ menjadi seperti ini.
```java
public class NetworkDevice {

   private String imageUrl;
   private String name;

   NetworkDevice(String name, String imageUrl) {
       this.name = name;
       this.imageUrl = imageUrl;
   }

   public String getImageUrl() {
       return imageUrl;
   }

   public String getName() {
       return name;
   }
}
```
6. Berikutnya kita perlu menambahkan class yang berisikan koleksi data.
Buat class baru dan beri nama  __NetworkDeviceData__.
<p align="left">
  <img width="350" src="images/new network device data class.PNG">
</p>

Kondisikan class tersebut seperti di bawah ini.
```java
public class NetworkDeviceData {

   private static ArrayList<NetworkDevice> networkDevices = new ArrayList<>();

   public static ArrayList<NetworkDevice> getNetworkDevices() {
       networkDevices.add(new NetworkDevice("Hub","https://www.elprocus.com/wp-content/uploads/network-hub.jpg"));
       networkDevices.add(new NetworkDevice("Switch","https://www.elprocus.com/wp-content/uploads/network-switch.jpg"));
       networkDevices.add(new NetworkDevice("Router","https://www.elprocus.com/wp-content/uploads/router.jpg"));
       networkDevices.add(new NetworkDevice("Bridge","https://www.elprocus.com/wp-content/uploads/bridge.jpg"));
       networkDevices.add(new NetworkDevice("Repeater","https://www.elprocus.com/wp-content/uploads/repeater.jpg"));
       networkDevices.add(new NetworkDevice("Gateway","https://www.elprocus.com/wp-content/uploads/gateway-device.jpg"));
       networkDevices.add(new NetworkDevice("RJ45 Connector","https://www.tutorialspoint.com/communication_technologies/images/rj45_connector.jpg"));
       networkDevices.add(new NetworkDevice("Ethernet Card","https://www.tutorialspoint.com/communication_technologies/images/ethernet_card.jpg"));

       return networkDevices;
   }
}
```
7. Kita sudah memiliki koleksi data. Berikutnya kita perlu membuat _Adapter RecyclerView_. Buat kelas baru dengan nama __ListNetworkDeviceAdapter__.
<p align="left">
  <img width="500" src="images/create adapter class.PNG">
</p>

Kita perlu meng-extends `RecyclerView.Adapter` seperti berikut.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

}
```
Akan muncul _underline_ berwarna merah. Tekan __alt+enter__ pada `RecyclerView.Adapter`, lalu _implement methods_.
<p align="left">
  <img width="500" src="images/implement adapter methods.jpg">
</p>

Pilih seluruh method yang dimiliki `RecyclerView.Adapter` lalu tekan tombol OK.
<p align="left">
  <img width="200" src="images/adapter methods.PNG">
</p>

Sehingga __ListNetworkDeviceAdapter__ akan terlihat seperti ini.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {


    @NonNull
    @Override
    public ListNetworkDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListNetworkDeviceAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
```

8. Sekarang kita membutuhkan class `ViewHolder`. Tekan __alt+enter__ pada `ViewHolder` untuk membuat class tersebut.
<p align="left">
  <img width="500" src="images/create view holder.png">
</p>

Berikutnya extends `RecyclerView.ViewHolder` pada class `ViewHolder` tersebut lalu buat constructor nya dengan cara, tekan __alt+enter__ pada class tersebut lalu pilih _create constructor matching super_.
<p align="left">
  <img width="500" src="images/constructor matching super.png">
</p>

Maka class `ViewHolder` akan terlihat seperti ini.
```java
public class ViewHolder extends RecyclerView.ViewHolder  {
   public ViewHolder(@NonNull View itemView) {
       super(itemView);
   }
}
```

Kita perlu menginisialisasi view yang terdapat pada __item_netdevice.xml__ di dalam class `ViewHolder` sehingga menjadi seperti ini.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

    ...

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView imgDevice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            imgDevice = itemView.findViewById(R.id.img_device);
        }
    }
}
```

9. Selanjutnya buat sebuah ArrayList dan constructor untuk menerima koleksi data.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

    private ArrayList<NetworkDevice> networkDevices;
    
    public ListNetworkDeviceAdapter(ArrayList<NetworkDevice> networkDevices) {
        this.networkDevices = networkDevices;
    }
    
    ...
}
```
10. Sekarang masukan kode berikut di dalam method `onCreateViewHolder()`.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

   ...

   @NonNull
   @Override
   public ListNetworkDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_netdevices, parent, false);
       return new ViewHolder(view);
   }

   ...
}
```

Agar dapat menampilkan layout item di dalam RecyclerView, kita perlu menggunakan `LayoutInflater` yang di _assign_  ke dalam objek `view`.
Lalu objek tersebut di gunakan oleh `ViewHolder` untuk melakukan _casting_ view dengan `findViewById()`.

11. Selanjutnya kita perlu menampilkan data di dalam method `onBindViewHolder()` dengan menambah kode sebagai berikut.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

   private ArrayList<NetworkDevice> networkDevices;

   ...

   @Override
   public void onBindViewHolder(@NonNull ListNetworkDeviceAdapter.ViewHolder holder, int position) {
       NetworkDevice networkDevice = networkDevices.get(position);

       Glide.with(holder.itemView.getContext())
               .load(networkDevice.getImageUrl())
               .apply(new RequestOptions().override(100,100))
               .into(holder.imgDevice);

       holder.tvName.setText(networkDevice.getName());

   }
    
   ...
}
```

12. Sekarang tambah kode berikut di dalam method `getItemCount()`.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

   ...
    
   @Override
   public int getItemCount() {
       return networkDevices.size();
   }
   
   ...
}
```
Method tersebut digunakan untuk menentukan berapa banyak item yang akan di tampilkan di dalam RecyclerView. 

Sehingga __ListNetworkDeviceAdapter__ akan terlihat seperti ini.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

   private ArrayList<NetworkDevice> networkDevices;

   public ListNetworkDeviceAdapter(ArrayList<NetworkDevice> networkDevices) {
       this.networkDevices = networkDevices;
   }

   @NonNull
   @Override
   public ListNetworkDeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_netdevices, parent, false);
       return new ViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull ListNetworkDeviceAdapter.ViewHolder holder, int position) {
       NetworkDevice networkDevice = networkDevices.get(position);

       Glide.with(holder.itemView.getContext())
               .load(networkDevice.getImageUrl())
               .into(holder.imgDevice);

       holder.tvName.setText(networkDevice.getName());

   }

   @Override
   public int getItemCount() {
       return networkDevices.size();
   }

   public class ViewHolder extends RecyclerView.ViewHolder {

       TextView tvName;
       ImageView imgDevice;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           tvName = itemView.findViewById(R.id.tv_name);
           imgDevice = itemView.findViewById(R.id.img_device);
       }
   }
}
```

13. Sekarang buka __MainActivity__. Lakukan inisialisasi RecyclerView dan ambil data yang berasal dari class `NetworkDeviceData` untuk di gunakan oleh adapter.
```java

public class MainActivity extends AppCompatActivity {

   private RecyclerView rvNetDevice;
   private ArrayList<NetworkDevice> networkDevices = new ArrayList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       rvNetDevice = findViewById(R.id.rv_netdevices);

       networkDevices.addAll(NetworkDeviceData.getNetworkDevices());

       initRecyclerView();
   }

   private void initRecyclerView() {
       ListNetworkDeviceAdapter adapter = new ListNetworkDeviceAdapter(networkDevices);
       rvNetDevice.setLayoutManager(new LinearLayoutManager(this));
       rvNetDevice.setAdapter(adapter);
   }
}
```
Perhatikan kode di dalam method `initRecyclerView()`. Kita membuat objek adapter dan memberi koleksi data kedalam constructor nya. __RecyclerView__ yang telah di inisialisasi di atur tampilan nya menggunakan __LinearLayoutManager__ agar menampilkan koleksi data dalam bentuk list. Kemudian __RecyclerView__ tersebut memanggil `setAdapter()` dan memasukan object adapter yang telah di buat.

14. Sebelum kita menjalankan programnya, kita perlu membuat _request_ kepada sistem Android untuk menggunakan internet, karena gambar yang akan di kita tampilkan berasal dari internet. Request tersebut dapat di lakukan di dalam berkas __AndroidManifest.xml__.

Sekarang buka __AndroidManifest.xml__, tambahkan request di bawah ini. Letakkan di dalam tag `<manifest>` dan di luar tag  `<application>`.

```xml
    <uses-permission android:name="android.permission.INTERNET"/>
```

Sehingga __AndroidManifest.xml__ akan terlihat seperti ini.
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.networkingdevices">

    //Meminta akses Internet.
    <uses-permission android:name="android.permission.INTERNET"/>

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

Sekarang jalankan programnya, maka tampilan nya akan seperti ini.

--Image--

15. Kita sudah berhasil membuat aplikasi yang berisikan daftar gambar dan nama-nama perangkat jaringan.
Sekarang  tinggal menambahkankan kode untuk mendeteksi dan memproses event klik pada item list.

Buka kembali ke class __ListNetworkDeviceAdapter__ dan tambahkan sebuah `interface` di dalamnya.
```java

...
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

    ...

    interface OnItemClickListener {
        void onItemClicked(String name);
    }
}

...
```

16. Buat instance dari interface tersebut.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {
    
    private OnItemClickListener onItemClickListener;

    
    ...

    interface OnItemClickListener {
        void onItemClicked(String name);
    }
}
```

Kemudian buatkan method setter. 
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {
    
    private OnItemClickListener onItemClickListener;

    
    ...
    
     public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    ...
    
    interface OnItemClickListener {
        void onItemClicked(String name);
    }
}
```

Selanjutnya masukan kode ini di dalam method `onBindViewHolder()`.
```java
public class ListNetworkDeviceAdapter extends RecyclerView.Adapter<ListNetworkDeviceAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    ...
    
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull ListNetworkDeviceAdapter.ViewHolder holder, int position) {
        
        ...

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClicked(networkDevice.getName());
            }
        });
    }

    ...
    
    interface OnItemClickListener {
        void onItemClicked(String name);
    }
}
```
17. Kita sudah berhasil membuat interface. Sekarang kembali ke __MainActivity__ dan tambahkan kode ini di dalam method `initRecyclerView()`.

```java
public class MainActivity extends AppCompatActivity {
    ...

    private void initRecyclerView() {
        ListNetworkDeviceAdapter adapter = new ListNetworkDeviceAdapter(networkDevices);
        rvNetDevice.setLayoutManager(new LinearLayoutManager(this));
        rvNetDevice.setAdapter(adapter);

        adapter.setOnItemClickListener(new ListNetworkDeviceAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(String name) {
                Toast.makeText(getApplicationContext(),name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

Kita mengggunakan interface tersebut agar respon click yang kita terima dapat di proses di dalam `MainActivity`. Sekarang jalankan kembali programnya.  apabila item pada list di tekan, aplikasi akan memunculkan pesan __Toast__ yang  menampilkan nama perangkat jaringan yang di tekan.

-- GIF --






























