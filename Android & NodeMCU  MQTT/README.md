# Bab 8 - Android & NodeMCU MQTT

<img align="left" src="../images/logo.png" width="400">
<img align="left" src="../images/logo_ug.jpg" width="50">
<br/><br/><br/><br/>

## Tujuan
Pada percobaan Bab ini, kalian akan memahami bagaimana perangkat Android dapat berkomunikasi dengan perangkat sensor (NodeMCU) melalui protokol MQTT. 

## Aplikasi Pada Kehidupan Sehari-hari.
1. a
2. b 
3. c 

## Teori
MQTT merupakan protokol pengiriman pesan machine-to-machine (M2M)/"Internet of Things" yang didesain untuk komunikasi antar perangkat dengan keterbatasan sumber daya, low-bandwith, atau perangkat pada jaringan yang tidak stabil dan high-latency. Prinsip desain dari protokol MQTT adalah untuk meminimalkan network bandwith dan sumber daya perangkat sementara tetap menjaga realibilitas serta jaminan pengiriman pesan. MQTT dikembangkan oleh Dr Andy Stanford-Clark dari IBM, dan Arlen Nipper dari Arcom (sekarang Eurotech) pada tahun 1999. 

#### Request-Response VS Publish-Subscribe (Pub/Sub)
  Umumnya, bentuk komunikasi komputer pada jaringan berbentuk request-response, sebuah komputer sebagai client meminta (request) data kepada server sehingga server merespon (reponse) permintaan tersebut dengan suatu data. Namun kelemahan dari bentuk komunikasi Request-Response adalah perangkat client tidak dapat secara langsung mengetahui adanya perubahan data pada server sehingga mengharuskan client untuk meminta data kepada server dalam secara terus-menerus pada suatu interval waktu. 

  Disinilah model komunikasi Publish-Subscribe (Pub/Sub) hadir untuk menangani masalah tersebut, dan MQTT merupakan salah-satu protokol yang menerapkan konsep ini. Anggap MQTT sebagai grup WhatsApp dari perangkat keras. Setiap perangkat keras harus terhubung pada suatu grup agar dapat saling berkomunikasi. Setiap perangkat keras dapat mengirimkan pesan ke grup (publish), dan pada waktu yang sama setiap perangkat keras lainnya juga dapat menerima pesan yang dikirim tersebut (subscribe). Grup disini bertindak sebagai "Broker", yang berfungsi untuk menerima pesan masuk dan mengirimkannya kembali kepada seluruh perangkat keras yang terhubung (subscribers).
  
  (IMG VISUALISASI PUBLISH SUBSCRIBE BROKER)
