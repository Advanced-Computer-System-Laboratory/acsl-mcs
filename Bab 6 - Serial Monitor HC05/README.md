# BAB 6 - Android & NodeMCU - Bluetooth Serial Monitor

<img align="left" src="../images/logo.png" width="400">
<img align="left" src="../images/logo_ug.jpg" width="60">
<a href="https://github.com/lefalya">
  <img align="right" src="../images/Github.png" width="30">
</a>
<a href="https://www.linkedin.com/in/berninofalya/">
  <img align="right" src="../images/LinkedIn.png" width="30">
</a>
<img align="right" src="../images/BerninoFalya.png" width="200">
<br/><br/><br/><br/>

## Tujuan
Pada percobaan ini kita akan mempraktikan komunikasi Bluetooth antara perangkat Android dan Arduino menggunakan modul HC05. Hasil akhir dari Codelab pada Bab ini adalah praktikan dapat mengirimkan pesan dari Arduino dengan bantuan modul HC05 ke perangkat Android melalui kanal Bluetooth secara satu arah, menjadikan perangkat Android bertindak selayaknya sebuah *Serial Monitor*. Codelab Bab ini menerapkan konsep *multi-threading* pada bagian akuisisi data pada perangkat Android.

## Teori
### Bluetooth
Bluetooth merupakan teknologi nirkabel untuk mengirim dan menerima data pada frekuensi 2.4GHz. Jaringan Bluetooth atau biasa disebut dengan piconets bersifat *ad-hoc* dan menerapkan model master/slave. Sebuah master dapat terhubung maksimal tujuh slave. Sebuah slave hanya dapat terhubung ke satu master. **Secara default, sebuah smartphone Android akan bertindak sebagai master pada sebuah jaringan piconets.**  

<p align="center">
<img src="images/piconets.png" align="center"><br />
*Gambar 1 : Contoh topologi Piconets*<br />
</p>

Bluetooth terdiri dari dua jenis susunan protokol, yaitu *controller stack* dan *host stack*. Namun dalam Codelab Bab ini, pengaturan pada *controller stack* diabaikan dan memfokuskan pada penggunaan salah satu protokol pada *host stack* yaitu RFCOMM (Radio Frequency Communication). RFCOMM merupakan protokol yang menirukan sebuah port serial, menyediakan aliran data serupa dengan TCP. Penerapan protokol RFCOMM memungkinkan kita untuk mengirim pesan seperti "Hallo Dunia" secara mudah dan cepat. Android secara default menyediakan dukungan pada protokol RFCOMM melalui API `BluetoothDevice` dengan fungsi `createRfcommSocketToServiceRecord(UUID uuid)` dan `createInsecureRfcommSocketToServiceRecord(UUID uuid)`. Pada Codelab ini digunakan protokol RFCOMM mode Insecure. 

### HC05
HC05 merupakan modul Bluetooth SPP (Serial Port Protocol) yang menerapkan komunikasi serial UART dengan perangkat MCU. Modul HC05 dapat dioperasikan pada tegangan 3.6-6V dengan baud rate 9600 pada *data mode*  dan baud rate 38400 pada *command mode*.

<p align="center">
<img src="images/kit_bluetooth_hc05.jpg" align="center"><br />
*Gambar 1 : Modul HC05*<br />
</p>

## Codelab
[![BAB 6 - Android & NodeMCU - Bluetooth Serial Monitor](https://img.youtube.com/vi/8gt0H6WOc10/0.jpg)](https://www.youtube.com/watch?v=8gt0H6WOc10)
<br/>Available on YouTube : https://www.youtube.com/watch?v=8gt0H6WOc10

## LP
1. Jelaskan yang dimaksud dengan model komunikasi Maste/Slave!
2. Jelaskan apa itu protokol!

## LA
1. Jelaskan cara kerja transmisi pesan dari Arduino ke Android dengan bahasa kalian masing-masing!
