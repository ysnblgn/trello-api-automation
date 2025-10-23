# Trello API Otomasyon Projesi

Bu proje, Trello'nun REST API'si kullanılarak temel Board ve Kart işlemlerinin otomasyonunu gerçekleştirmek üzere tasarlanmış bir Web Servis Otomasyon projesidir. Proje, modern yazılım geliştirme prensipleri ve otomasyon standartlarına uygun olarak geliştirilmiştir.

## 🚀 Proje Amacı ve Kapsamı

Bu otomasyon projesinin temel amacı, Trello API'si üzerinden aşağıdaki iş akışını otomatize etmektir:

1.  **Board Oluşturma:** Yeni bir Trello Board oluşturulur.
2.  **Kart Oluşturma:** Oluşturulan Board üzerindeki varsayılan listeye iki adet yeni Kart oluşturulur.
3.  **Kart Güncelleme:** Oluşturulan bu iki karttan rastgele seçilen bir tanesinin adı güncellenir.
4.  **Kartları Silme:** Oluşturulan tüm Kartlar silinir.
5.  **Board Silme:** Otomasyonun sonunda, oluşturulan Board temizlenir.

## ✨ Kullanılan Teknolojiler ve Mimariler

Proje, aşağıdaki teknolojiler ve mimari prensipler kullanılarak geliştirilmiştir:

| Alan | Teknoloji / Prensip | Açıklama |
| :--- | :--- | :--- |
| **Programlama Dili** | Java (JDK 17+) | Projenin temel geliştirme dili. |
| **Bağımlılık Yönetimi** | Maven | Proje bağımlılıklarının yönetimi ve derleme süreci için kullanılır. |
| **API Test Kütüphanesi** | Rest-Assured | RESTful servisler ile etkileşim kurmak için endüstri standardı kütüphane. |
| **Test Çatısı** | TestNG | Test senaryolarını organize etmek, çalıştırmak ve akış kontrolü (`@Test`, `dependsOnMethods`, `@BeforeClass`, `@AfterClass`) sağlamak için kullanılır. |
| **Loglama** | Log4j 2 | Profesyonel ve seviyelendirilmiş çıktı (INFO, ERROR vb.) için kullanılır. |
| **Mimari Prensip** | Page Object Pattern | API etkileşimlerini soyutlamak ve kod tekrarını önlemek için uygulanmıştır. Her API kaynağı (Board, Card) için ayrı bir "Page" sınıfı bulunur. |
| **Tasarım Prensibi** | Nesne Yönelimli Programlama (OOP) | Kalıtım (`BasePage`), soyutlama ve kapsülleme prensipleri aktif olarak kullanılmıştır. |
| **Yapılandırma Yönetimi** | `config.properties` | Hassas bilgileri (API Key, Token) koddan ayırmak ve esnek yapılandırma sağlamak için kullanılır. |

## 📁 Proje Yapısı

Proje, temizlik ve ölçeklenebilirlik sağlamak amacıyla mantıksal katmanlara ayrılmıştır:

```
trello-api-automation/
├── pom.xml
├── README.md
└── src/
    └── test/
        ├── java/
        │   └── com
        │       ├── utils/          
        │       │   └── ConfigManager.java
        │       └── trello/         
        │           ├── pages/      
        │           │   ├── BasePage.java
        │           │   ├── BoardPage.java
        │           │   └── CardPage.java
        │           └── TrelloAutomationTest.java 
        └── resources/
            ├── config.properties   
            └── log4j2.xml          
```

## ⚙️ Kurulum ve Çalıştırma

### 1. Ön Gereksinimler

*   Java Development Kit (JDK) 17 veya üzeri
*   Apache Maven
*   IntelliJ IDEA veya tercih edilen bir Java IDE

### 2. Projeyi Klonlama ve Açma

1.  **Projeyi Klonlayın:** Projenin kaynak kodunu aşağıdaki komutla yerel bilgisayarınıza klonlayın:

    ```bash
    git clone [SİZİN_GITHUB_URL'İNİZ]
    ```

2.  **IntelliJ'de Açın:** IntelliJ IDEA'da **File > Open...** yolunu izleyerek, yeni klonladığınız proje klasörünü seçin. IntelliJ, projeyi otomatik olarak bir Maven projesi olarak tanıyacak ve gerekli tüm bağımlılıkları indirecektir.

### 3. Yapılandırma Dosyasını Güncelleme

Trello API'si ile iletişim kurmak için bir **API Anahtarı (Key)** ve **Token** gereklidir. Bu bilgileri `config.properties` dosyasına eklemeniz gerekir.
API Key ve Token bilgilerinin nasıl alınamsı gerektiği notlar bölümünde detaylı bir şekilde anlatılmıştır.

`src/test/resources/config.properties` dosyasını açın ve placeholder değerlerini kendi bilgilerinizle değiştirin:

   ```properties
    # key ve token değerlerini kendi bilgilerinizle değiştirin
    apiKey=YOUR_API_KEY
    apiToken=YOUR_API_TOKEN
   ```

### 4. Testi Çalıştırma

1.  IntelliJ IDEA'da `TrelloAutomationTest.java` dosyasını açın.
2.  Sınıf adının yanındaki yeşil oynatma (Run) simgesine tıklayın ve **"Run 'TrelloAutomationTest'"** seçeneğini seçin.

Test, sırasıyla Board oluşturma, Kart oluşturma, rastgele Kart güncelleme, Kartları silme ve Board'u silme işlemlerini gerçekleştirecek ve tüm adımları konsola Log4j 2 formatında yazacaktır.

## 💡 Mimari Detaylar

### ConfigManager.java

Bu yardımcı sınıf, `config.properties` dosyasını okumaktan sorumludur. Tüm yapılandırma değerlerine tek bir merkezi noktadan erişim sağlar ve hassas bilgileri koddan ayırır.

### Page Sınıfları (`BasePage`, `BoardPage`, `CardPage`)

*   **`BasePage.java`**: Tüm API Page sınıflarının temelidir. Rest-Assured `RequestSpecification` objesini oluşturur ve Trello API Key/Token bilgilerini `ConfigManager` üzerinden okuyarak tüm isteklere otomatik olarak ekler.
*   **`BoardPage.java`**: Board oluşturma ve silme gibi Board'a özgü API çağrılarını içerir.
*   **`CardPage.java`**: Kart oluşturma, güncelleme ve silme gibi Kart'a özgü API çağrılarını içerir.

### TrelloAutomationTest.java

Bu sınıf, TestNG çatısını kullanarak tüm iş akışını yönetir. `@Test` notasyonları, test adımlarının bağımlılıklarını ve çalışma sırasını belirler, böylece otomasyon senaryosu mantıksal bir sıra ile ilerler.

## 📝  Notlar

### Trello API Anahtarı ve Token Oluşturma Adımları

1. Power-Up’lar ve Entegrasyonlar sayfasına gidin 
   *   **`https://trello.com/power-ups/admin`**
   
2. `Yeni` butonuna tıklayın. Eğer "`Yeni` butonunu göremezseniz aşağıdaki adımları uygulayın:
   * trello.com ana sayfasına git.
   * Sol menüde veya sağ üstte `Create` (Oluştur) → `Create Workspace` (Çalışma Alanı Oluştur) seç.
   * İsim olarak örneğin API Automation Workspace yaz, gerekirse kısa bir açıklama ekle.
   * Create (Oluştur) düğmesine bas.
   * Tekrar **`https://trello.com/power-ups/admin`** sayfasına dön.

3. Açılan formu doldurup “Oluştur” seçeneğini seçin.
4. Karşınıza çıkan pencerede “Yeni bir API anahtarı oluşturun”
   butonuna tıklayın.
    * Bu alanda görüntülenen değer, API Anahtarınızdır.
5. Token oluşturmak için aynı sayfada yer alan “manuel olarak bir Belirteç (Token) oluşturabilirsiniz.” 
ifadesindeki`Belirteç` bağlantısına tıklayın.
6. Gerekli izinleri verin ve oluşan değeri kopyalayın.
    * Bu değer, uygulamanızda kullanacağınız yeni Tokendır.
7. Artık Trello REST API’sini kullanmaya hazırsınız.
   Ayrıntılı uç nokta bilgileri ve örnek istekler için:
   **`https://developer.atlassian.com/cloud/trello/rest/**`
