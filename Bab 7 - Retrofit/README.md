# BAB 5 - Retrofit
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

<br/><br/><br/><br/>

## Tujuan
Dapat melakukan parsing JSON menggunakan Retrofit.

## Teori

Retrofit merupakan library type-safe HTTP client untuk Android dan Java yang dibuat oleh developers di perusahaan Square. Retrofit membantu memudahkan parsing JSON atau XML dengan mengubahnya menjadi Plain Old Java Object (POJO). Ini tentu memudahkan pengembang aplikasi Android untuk memproses data yang terdapat di Web API atau Backend ke dalam aplikasi Android. 


```gradle
    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"
```

Proses pengambilan data dari backend sebelum adanya Retrofit terbilang cukup kompleks. Dengan kehadiran GSON proses tersebut menjadi lebih ringkas.


Melakukan request menggunakan Retrofit.

``` java
@GET("/users")
Call<List<UserData>> getListUsers();
```
Parsing file JSON menggunakan Retrofit dan GSON.

``` java
public class UserData{
 
   @SerializedName("user_name")
   private String username;
 
   @SerializedName("id")
   private int id;
 
   @SerializedName("avatar")
   private String avatar;
 
 
   public String getUsername(){
      return username;
   }
 
   public int getId(){
      return id;
   }
 
   public String getAvatar(){
      return avatar;
   }
}
```


Pengambilan file JSON secara manual. 

```java
 try {
      URL url = new URL("https://jsonplaceholder.typicode.com/users");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      InputStream in = connection.getInputStream();
      InputStreamReader reader = new InputStreamReader(in);
      int data = reader.read();

        while (data != -1) {
            char current = (char) data;
            result+=current;
            data = reader.read();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

```
Parsing JSON secara manual.
```java

List<UserData> listUser = new ArrayList<UserData>();
JSONObject jsonObject = JSONObject(response.body().toString());
JSONArray jsonArray = jsonObject.getJSONArray("data");
for (int i = 0; i < jsonArray.length(); i++) {
    int id = jsonArray.getJSONObject(i).getInt("id");
    String username = jsonArray.getJSONObject(i).getString("user_name");
    String avatar = jsonArray.getJSONObject(i).getString("avatar");
    UserData user = UserData(username, id, avatar);
    listUser.add(user);
}
```

## Codelab
1. Buatlah project baru pada Android Studio dengan kriteria sebagai berikut : 

| Field     | Isian |
| ---      | ---       |
| Nama Project  | **Cats API**   |
| Target & Minimum Target SDK  | **Phone and Tablet, API level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

2. Buka file `build.gradle(Module)`  kemudian masukkan library Retrofit, GSON dan Glide di dalam block `dependencies` kemudian lakukan __sync now__

```gradle
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'   
implementation 'com.github.bumptech.glide:glide:4.12.0'
```
3. Buat `class` baru dengan nama `CatBreedModel` dan kondisikan `class` tersebut seperti di bawah ini. Ini merupkana `class` __POJO__ yang merupakan representasi dari __JSON__. 

``` java
import com.google.gson.annotations.SerializedName;

public class CatModel {

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("id")
	private int id;
	@SerializedName("url")
	private String url;
	
	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getUrl(){
		return url;
	}
}
```

4. Buat `interface` baru dengan nama CatApi dan kondisikan `class` tersebut seperti berikut.

``` java

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface CatApi {
    
    @GET
    Call<List<CatModel>> getCats();
    
}
```

5. Buat `class` baru dengan nama `GithubClientApi`, kemudian kondisikan `class` tersebut seperti di bawah ini. Class ini berperan sebagai perantara koneksi ke server. 

``` java
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClientApi {


    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    
}
```

6. Kita akan menggunakan RecyclerView untuk menampilkan data. Buat layout baru dengan nama `item_cat.xml`

``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginTop="20dp"
    android:layout_marginBottom="10dp">

    <ImageView
        android:id="@+id/img_cat"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="16dp"
        android:scaleType="centerCrop"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:src="@android:color/darker_gray" />

    <TextView
        android:id="@+id/tv_breed_name"
        style="@style/TextAppearance.AppCompat.Title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:textColor="@color/black"
        app:layout_constraintStart_toEndOf="@id/img_cat"
        app:layout_constraintTop_toTopOf="@id/img_cat"
        tools:text="Breed" />

    <TextView
        android:id="@+id/tv_description"
        style="@style/TextAppearance.AppCompat.Body1"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        app:layout_constrainedWidth="true"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="@id/img_cat"
        app:layout_constraintTop_toBottomOf="@id/tv_breed_name" />


</androidx.constraintlayout.widget.ConstraintLayout>
```
7. Buat `class` baru dengan nama `CatBreedAdapter`. Class ini digunakan sebagai adapter untuk RecyclerView.

``` java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;

public class CatBreedAdapter extends RecyclerView.Adapter<CatBreedAdapter.ViewHodler> {

    private List<CatModel> dataList;

    public CatBreedAdapter(List<CatModel> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cat, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        CatModel item = dataList.get(position);
        
        holder.tvBreedName.setText(item.getName());
        holder.tvDescription.setText(item.getDescription());
        
        Glide.with(holder.itemView.getContext())
                .load(item.getUrl())
                .into(holder.imgCat);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHodler extends RecyclerView.ViewHolder {
        
        TextView tvBreedName, tvDescription;
        ImageView imgCat;
        
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            tvBreedName = itemView.findViewById(R.id.tv_breed_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
            imgCat = itemView.findViewById(R.id.img_cat);
        }
    }
}
```

8. Buka file `activity_main.xml` dan kondisikan file tersebut seperti di bawah ini. 

```xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:orientation="vertical"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_cat_breed"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Hello World!"
        tools:listitem="@layout/item_cat" />

</LinearLayout>

```
9. Buka `MainActivity` kemudian kondisikan class tersebut seperti di bawah ini.

```Java
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<CatModel> cats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Membuat RecyclerView
        CatBreedAdapter adapter = new CatBreedAdapter(cats);
        RecyclerView rvCatBreed = findViewById(R.id.rv_cat_breed);
        rvCatBreed.setLayoutManager(new LinearLayoutManager(this));
        rvCatBreed.setAdapter(adapter);


        // Membuat instance Retrofit
        CatApi api = GithubClientApi.getRetrofitInstance().create(CatApi.class);

        // Memanggil getCats() secara asynchronous di background thread.
        api.getCats().enqueue(new Callback<List<CatModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CatModel>> call, @NonNull Response<List<CatModel>> response) {
                List<CatModel> result = response.body();
                // jika respons dari server sukses dan list CatModel berukuran lebih dari 0.
                if (response.isSuccessful() && result.size() > 0) {
                    // Masukkan list Cat Model ke dalam ArrayList CatModel agar dapat digunakan Recyclerview adapter.
                    cats.addAll(result);
                    // Beritahu RecyclerView adapter bahwa terjadi perubahan data.
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable t) {
                //Apabila terjadi error, print error tersebut di dalam konsol.
                t.printStackTrace();
            }
        });
    }
}
```

10. Buka file `AndroidManifest.xml` dan tambahkan _permission_ internet.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.acsl.catapi">

    <uses-permission android:name="android.permission.INTERNET"/>

    <application...>

</manifest>
```

11. Jalankan Aplikasi.


## LP
...

## LA
...
