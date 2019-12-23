# Bab 8 - Android & NodeMCU MQTT

<img align="left" src="../images/logo.png" width="400">
<img align="left" src="../images/logo_ug.jpg" width="60">
<br/><br/><br/><br/>

## Tujuan
Pada percobaan Bab ini, kalian akan memahami bagaimana perangkat Android dapat berkomunikasi dengan tiga perangkat sensor (NodeMCU) melalui protokol MQTT. Terdapat dua codelab, yaitu codelab NodeMCU dan Android. 

## Aplikasi Pada Kehidupan Sehari-hari.
1. a
2. b 
3. c 
<hr/>

## Teori
MQTT merupakan protokol pengiriman pesan machine-to-machine (M2M)/"Internet of Things" yang didesain untuk komunikasi antar perangkat dengan keterbatasan sumber daya, low-bandwith, atau perangkat pada jaringan yang tidak stabil dan high-latency. Prinsip desain dari protokol MQTT adalah untuk meminimalkan network bandwith dan sumber daya perangkat sementara tetap menjaga realibilitas serta jaminan pengiriman pesan. MQTT dikembangkan oleh Dr Andy Stanford-Clark dari IBM, dan Arlen Nipper dari Arcom (sekarang Eurotech) pada tahun 1999. 

#### Request-Response VS Publish-Subscribe (Pub/Sub)
  Umumnya, bentuk komunikasi komputer pada jaringan berbentuk request-response, sebuah komputer sebagai client meminta (request) data kepada server sehingga server merespon (reponse) permintaan tersebut dengan suatu data. Namun kelemahan dari bentuk komunikasi Request-Response adalah perangkat client tidak dapat secara langsung mengetahui adanya perubahan data pada server sehingga mengharuskan client untuk meminta data kepada server dalam secara terus-menerus pada suatu interval waktu. 

  Disinilah model komunikasi Publish-Subscribe (Pub/Sub) hadir untuk menangani masalah tersebut, dan MQTT merupakan salah-satu protokol yang menerapkan konsep ini. Anggap MQTT sebagai grup WhatsApp dari perangkat keras. Setiap perangkat keras harus terhubung pada suatu grup agar dapat saling berkomunikasi. Setiap perangkat keras dapat mengirimkan pesan ke grup **(publish)**, dan pada waktu yang sama setiap perangkat keras lainnya juga dapat menerima pesan yang dikirim tersebut **(subscribe)**. Grup disini bertindak sebagai **"Broker"**, yang berfungsi untuk menerima pesan masuk dan mengirimkannya kembali kepada seluruh perangkat keras yang terhubung (subscribers). Setiap grup memiliki nama, yang dimana dalam MQTT disebut sebagai **Topics**. Setiap perangkat harus terhubung ke Topics yang sama agar dapat saling berkomunikasi. Pada implementasinya, broker MQTT berbentuk suatu software yang berjalan pada komputer server. Salah satu software broker MQTT yang tersedia secara gratis yaitu Ecplise Mosquitto (https://mosquitto.org/). 
  
(IMG VISUALISASI PUBLISH SUBSCRIBE BROKER)
<hr/>

## Setup MQTT 
- Broker MQTT sudah tersedia. Tanyakan kepada asisten untuk alamat IP broker MQTT. Pastikan perangkat smartphone **terhubung pada jaringan WiFi lab** sebelum melakukan running test. 
- Referensi installasi Broker dan Client MQTT pada Ubuntu : https://www.vultr.com/docs/how-to-install-mosquitto-mqtt-broker-server-on-ubuntu-16-04

## Codelab NodeMCU
1. Buatlah sketch baru pada Arduino IDE. Sehingga akan muncul tampilan awal seperti ini : 
<img src="images/ss_arduino_ide.png" width="400">

2. Tambahkan library `ESP8266Wifi` dan `PubSubClient` pada sketch yang sudah dibuat.

```c++
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  // put your main code here, to run repeatedly:

}
```
- Library `ESP8266Wifi` berfungsi untuk melakukan koneksi NodeMCU ke jaringan WiFi.
- Library `PubSubClient` berfungsi untuk menjadikan NodeMCU sebagai client dari broker MQTT.

3. Tambahkan beberapa variable yang dibutuhkan untuk konektifitas NodeMCU ke Wifi, state dari LED, dan interval pembacaan sensor LDR.
```c++ 
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

const char* ssid = "Lab Lanjut 121";
const char* password = "TanyaAsisten";
const char* mqtt_server = "192.168.121.105";

bool ledState = LOW;
long lastMsg = 0;

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  // put your main code here, to run repeatedly:

}
```
- Tanyakan isian `SSID_WIFI`, `PASSWORD_WIFI`, dan `ALAMAT_IP_BROKER` kepada asisten.

4. Inisialisasikan library yang sudah dimuat dengan menambahkan dua baris dibawah :
```c++ 
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

const char* ssid = "Lab Lanjut 121";
const char* password = "TanyaAsisten";
const char* mqtt_server = "192.168.121.105";

bool ledState = LOW;
long lastMsg = 0;

// tambahkan dua baris dibawah ini
WiFiClient espClient;
PubSubClient client(espClient);

void setup() {
  // put your setup code here, to run once:

}

void loop() {
  // put your main code here, to run repeatedly:

}
```

5. Kemudian, buatlah fungsi `setup_wifi()` yang berfungsi untuk konfigurasi koneksi NodeMCU ke WiFi.
```c++ 
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

...

void setup_wifi() {

  delay(10);
  
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  // Mencoba koneksi ke WiFi
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  // Jika koneksi WiFi berhasil maka akan menampilkan teks dibawah pada Serial.
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void setup() {
  // put your setup code here, to run once:
}

void loop() {
  // put your main code here, to run repeatedly:
}
```
6. Kemudian, tambahkan baris program dibawah pada fungsi `setup()`. 
```c++ 
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

...

void setup_wifi() {
 
 ...
 
}

void setup() {
  pinMode(D5, OUTPUT); // Menginisialisasikan pin D5 sebagai output
  
  Serial.begin(115200); // Set baud-rate komunikasi serial monitor sebesar 115200 bps.  
  setup_wifi(); // Memanggil fungsi setup_wifi() yang sudah dibuat sebelumnya
  
  // Print teks "connected" pada serial monitor jika koneksi ke WiFi berhasil.
  Serial.println("connected"); 
}

void loop() {
  // put your main code here, to run repeatedly:
}
```

7. Lalu, buatlah fungsi `callback()`. Fungsi ini diperuntukan untuk menangani pesan masuk dari Broker (Subscribe).
```c++ 
#include <ESP8266WiFi.h>
#include <PubSubClient.h>

...

void setup_wifi() {
 
 ...
 
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message arrived [");
  Serial.print(topic);
  Serial.print("] ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  // Switch on the LED if an 1 was received as first character
  Serial.print("payload ");
  Serial.println(payload[0]);
  if (payload[0] == '2') {
    if (payload[2] == 'a') digitalWrite(D5, !ledState);   
    ledState = !ledState;
  }
}

void setup() {
  ...
}

void loop() {
  // put your main code here, to run repeatedly:
}
```
