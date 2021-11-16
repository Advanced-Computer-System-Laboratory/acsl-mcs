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
| Nama Project  | **Sensors**   |
| Target & Minimum Target SDK  | **Phone and Tablet, API level 21**  |
| Tipe Activity | **Empty Activity** |
| Activity Name | **MainActivity** | 
| Language | **Java** |

...

## LP
Jelaskan deskripsi serta cara kerja dari sensor :
1. Accelerometer
2. Proximity
3. Light Sensor

## LA
Jabarkan tipe dan perusahaan manufaktur sensor Accelerometer, Proximity, dan Light Sensor yang tertanam pada smartphone kalian!
