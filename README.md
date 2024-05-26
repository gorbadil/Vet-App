# Veteriner Yönetim Sistemi

Veteriner Yönetim Sistemi, veteriner kliniklerinin günlük işlerini düzenlemek ve yönetmek amacıyla oluşturulmuş bir REST API'dir. Bu API ile veteriner çalışanının veteriner doktorları, müşterileri, hayvanları ve aşılarını, randevuları yönetmesi sağlanır.

## Kullanılan Teknolojiler

<code><img width="50" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" title="Maven"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/183896128-ec99105a-ec1a-4d85-b08b-1aa1620b2046.png" alt="MySQL" title="MySQL"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192107858-fe19f043-c502-4009-8c47-476fc89718ad.png" alt="REST" title="REST"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/186711335-a3729606-5a78-4496-9a36-06efcc74f800.png" alt="Swagger" title="Swagger"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192109061-e138ca71-337c-4019-8d42-4792fdaa7128.png" alt="Postman" title="Postman"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108890-200809d1-439c-4e23-90d3-b090cf9a4eea.png" alt="IntelliJ" title="IntelliJ"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108372-f71d70ac-7ae6-4c0d-8395-51d8870c2ef0.png" alt="Git" title="Git"/></code>
<code><img width="50" src="https://user-images.githubusercontent.com/25181517/192108374-8da61ba1-99ec-41d7-80b8-fb2f7c0a4948.png" alt="GitHub" title="GitHub"/></code>

## Özellikler

- Veteriner Doktorları Yönetimi: Veteriner doktorları ekleyebilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Müşteri Yönetimi: Müşterileri kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Hayvan Yönetimi: Hayvanları sisteme kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Aşı Yönetimi: Hayvanlara uygulanan aşıları kaydedebilme, bilgilerini güncelleyebilme, listeleme ve silme yeteneği.
- Randevu Yönetimi: Veteriner doktorları için randevular oluşturabilme, güncelleyebilme, görüntüleyebilme ve silebilme yeteneği.
- Çeşitli filtreleme yetenekleri

## Ekran Görüntüleri
- UML diyagram
<img src="src/main/resources/images/VetApp-uml.png" alt="Diagram_1" width="" />

- Veri tabanında yer alan tablolar
<img src="src/main/resources/images/database.png" alt="Diagram_1" width="" />

## API Kullanımı
Aşağıda, API'nin sunduğu temel endpoint'lerin bir listesi bulunmaktadır:

| Endpoint                                    | HTTP Metodu | Açıklama                                                                           |
|---------------------------------------------|:------------|------------------------------------------------------------------------------------|
| **customers**                               |             |                                                                                    |
| `/api/v1/customers/{id}`                    | GET         | Belirtilen ID'ye sahip hayvan sahibini getirir                                     |
| `/api/v1/customers/{id}`                    | PUT         | Belirtilen ID'ye sahip hayvan sahibini günceller                                   |
| `/api/v1/customers/{id}`                    | DELETE      | Belirtilen ID'ye sahip hayvan sahibini siler                                       |
| `/api/v1/customers`                         | GET         | Tüm hayvan sahiplerini getirir                                                     |
| `/api/v1/customers`                         | POST        | Hayvan sahibi ekler                                                                |
| `/api/v1/customers/searchByName`            | GET         | İsme gore hayvan sahiplerini getirir                                               |
|                                             |             |                                                                                    |
| **animals**                                 |             |                                                                                    |
| `/api/v1/animals/{id}`                      | GET         | Belirtilen ID'ye sahip hayvanı getirir                                             |
| `/api/v1/animals/{id}`                      | PUT         | Belirtilen ID'ye sahip hayvanı günceller                                           |
| `/api/v1/animals/{id}`                      | DELETE      | Belirtilen ID'ye sahip hayvanı siler                                               |
| `/api/v1/animals`                           | GET         | Tüm hayvanları getirir                                                             |
| `/api/v1/animals`                           | POST        | Hayvan ekler                                                                       |
| `/api/v1/animals/searchByName`              | GET         | İsme göre hayvanları filtreler                                                     |
| `/api/v1/animals/searchByCustomer`          | GET         | Hayvan sahiplerine göre hayvanları filtreler                                       |
|                                             |                                    |             |                                                                                    |
| **vaccines**                                |             |                                                                                    |
| `/api/v1/vaccines/{id}`                     | GET         | Belirtilen ID'ye sahip aşıyı getirir                                               |
| `/api/v1/vaccines/{id}`                     | PUT         | Belirtilen ID'ye sahip aşıyı günceller                                             |
| `/api/v1/vaccines/{id}`                     | DELETE      | Belirtilen ID'ye sahip aşıyı siler                                                 |
| `/api/v1/vaccines`                          | GET         | Tum aşıları getirir                                                                |
| `/api/v1/vaccines`                          | POST        | Aşı ekler                                                                          |
| `/api/v1/vaccines/searchByVaccinationRange` | GET         | Girilen tarih araligina gore aşı kayıtlarını getirir                               |
| `/api/v1/vaccines/searchByAnimal`           | GET         | Belirli bir hayvana ait tüm aşı kayıtlarını getirir                                |
|                                             |             |                                                                                    |
| **doctors**                                 |             |                                                                                    |
| `/api/v1/doctors/{id}`                      | GET         | Belirtilen ID'ye sahip doktoru getirir                                             |
| `/api/v1/doctors/{id}`                      | PUT         | Belirtilen ID'ye sahip doktoru günceller                                           |
| `/api/v1/doctors/{id}`                      | DELETE      | Belirtilen ID'ye sahip doktoru siler                                               |
| `/api/v1/doctors`                           | GET         | Tum doktorlari getirir                                                             |
| `/api/v1/doctors`                           | POST        | Doktor ekler                                                                       |
|                                             |             |                                                                                    |
| **available_dates**                         |             |                                                                                    |
| `/api/v1/available_dates/{id}`              | GET         | Belirtilen ID'ye sahip müsait günü getirir                                         |
| `/api/v1/available_dates/{id}`              | PUT         | Belirtilen ID'ye sahip müsait günü günceller                                       |
| `/api/v1/available_dates/{id}`              | DELETE      | Belirtilen ID'ye sahip müsait günü siler                                           |
| `/api/v1/available_dates`                   | GET         | Tüm müsait günü getirir                                                            |
| `/api/v1/available_dates`                   | POST        | Müsait gün ekler                                                                   |
|                                             |             |                                                                                    |
| **appointments**                            |             |                                                                                    |
| `/api/v1/appointments/{id}`                 | GET         | Belirtilen ID'ye sahip randevuyu getirir                                           |
| `/api/v1/appointments/{id}`                 | PUT         | Belirtilen ID'ye sahip randevuyu günceller                                         |
| `/api/v1/appointments/{id}`                 | DELETE      | Belirtilen ID'ye sahip randevuyu siler                                             |
| `/api/v1/appointments`                      | GET         | Tüm randevulari getirir                                                            |
| `/api/v1/appointments`                      | POST        | Randevu ekler                                                                      |
| `/api/v1/appointments/searchByDoctorAndDateRange`             | GET         | Kullanıcı tarafından girilen tarih aralığına ve doktora göre randevuları filtreler |
| `/api/v1/appointments/searchByAnimalAndDateRange`             | GET         | Kullanıcı tarafından girilen tarih aralığına ve hayvana göre randevuları filtreler |


## Kurulum
1. Projeyi klonlayın.
   - git clone https://github.com/alisimsekk/VeterinaryApp.git
2. `src/main/resources/application.properties` dosyasında veri tabanı konfigürasyonunu yapın.
3. Projeyi ayağa kaldırmak için idenizden start edin.
4. Swagger üzerinden api kullanılabilir. Tarayıcınızdan http://localhost:8080/swagger-ui/index.html#/ url'ine gidin.
5. End pointlere istek atabilirsiniz.

## Ortam Değişkenleri

Bu projeyi çalıştırmak için aşağıdaki ortam değişkenlerini application.properties dosyasından değiştirmelisiniz.

spring.datasource.url  
spring.datasource.username  
spring.datasource.password  

## Lisans

[MIT](https://choosealicense.com/licenses/mit/)

