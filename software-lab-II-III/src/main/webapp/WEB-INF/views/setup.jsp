
<h1>YAZILIM LABORATUVARI PROJE 3</h1>
<h2>ZAYIF WEB UYGULAMALARI GELİŞTİRİLMESİ PROJE DÖKÜMANI</h2>

<h3>1-Kurulum Yapılabileceği İşletim Sistemleri</h3>
Windows 7 ve sonrasında,Linux/GNU,Mac OS X ...
<h3>2- Web sunucu yazılımının kurulumu</h3>
Localhost için Tomcat Server kullanıldı ve bu Intellij IDE'sin de kullanıldı.
Öncelikle bu url den Tomcat'i indirelim <a href="http://tomcat.apache.org/">TomCat</a>
Daha sonra indirdiğimiz dosyayı intellij'de ayarlar kısmına girerek 'Application Servers' a tıklıyoruz ve + simgesine tıklayarak Tomcat'i ekliyoruz.
Daha sonra Run sekmesinden Edit Configuration'a giriyoruz ve karşımıza Run/Debug Configurations çıkıyor '+' kısmına tıklıyoruz ve buradan Tomcat Server'ı bularak 'local' e tıklıyoruz .
Deployment sekmesine gelerek tekrar '+' simgesine tıklıyoruz ve [projemizin adı]:war exploded yazan deployment'a tıklıyoruz bu bizim server startup'ımız oluyor.Bunu yaptıktan sonra en altta 'before launch' adında bir kısım orada da '+' simgesine tıklıyoruz ve Build Artifact diyoruz yukarda seçtiğimiz war exploded 'ı seçiyoruz.bu şekil de işlemimizi tamamlıyoruz artık serverımızı çalıştırabiliriz.
İsteğe bağlı Maven build'i de eklenebilir.

<h3>3-Veri Tabanı Kurulumu</h3>
Veri tabanı olarak mysql kullanıldı.Ubuntu için kurulumu.Server kurulumunu Windows'ta yaptık 'Bash on Ubuntu on Windows' ta ise mysql kurulumunu gerçekleştirdik.
https://www.howtogeek.com/249966/how-to-install-and-use-the-linux-bash-shell-on-windows-10/
Yukarıdaki linkten Bash Ubuntu kurulumu yapılabilir.
<p>Öncelikle hostname kontrolü yapılıyor.<br>
hostname<br>
    hostname -f</p>

<p>Daha sonra sitem güncelleştirilmesi yapılıyor.<br>
sudo apt-get update<br>
sudo apt-get upgrade<br>
Yükleme için komut<br>
    sudo apt-get install mysql-server</p>

<p>Daha temiz ve daha güvenli mysql için<br>
    sudo mysql_secure_installation</p>

MySql'e root kullanıcı olarak giriş yapalım<br>
mysql -u root -p<br>

Eğer yukarıdaki gibi secure girildiyse password soracaktır onu girin.Ve mysql'e adımımızı atmış oluyoruz.
<h3>4-Veri Tabanın Kurulumu</h3>
Mysql 'e yukarıda giriş yapmıştık.Şimdi bir database oluşturup kullanıcı oluşturma ile veritabanının kurulumunu gerçekleştirilicek.
mysql database ve kullanıcı oluşturma
<p>-------------------------------------------------------------------------------------<br>
create database koudb<br>
CREATE USER 'kou'@'localhost' IDENTIFIED BY 'kou123' ;<br>
CREATE USER 'kou'@'%' IDENTIFIED BY 'kou123' ;<br>
GRANT ALL ON *.* TO 'kou'@'localhost' ;<br>
GRANT ALL ON *.* TO 'kou'@'%' ;<br>
FLUSH PRIVILEGES ;<br>
    -------------------------------------------------------------------------------------</p>
Yukarıda görüldüğü üzere kurulumu tamamladık.




<%@ page contentType="text/html; charset=UTF-8" %>
<h3>1.1-SQL Injection Zafiyeti</h3>
İnternet sitelerinin bir çoğunda, sayfayı dinamik tutmak için veritabanından yararlanılır. Güncel veritabanı yazılımlarında birçoğunda (MySQL, MSSQL,  Sqlite, Oracle SQL) SQL (Structured Query Language) denilen ortak bir dil kullanılır.
SQL Injection, SQL sorgusunun amacına müdahale ederek farklı bilgileri elde etmeye denir.
Yani SQL Injection yöntemi ile üye bilgileri, yönetici şifreleri gibi veritabanında bulunup herkese açık olmayan bilgiler elde edilebilir.
SQL Injection kullanılarak Yahoo! gibi büyük internet sitelerinin bile veritabanları ele geçirilmiştir.

<h3>1.2-Güvensiz Kodlama</h3>
"select * from user where username='" + username + "' and password='" + password + "'";<br>
Kodda kullanılan bu SQL komutunda user tablosundan x kişisinin adına ve x kişisinin şifresine sahip olan veriyi getirmektedir.İşte burada bulunan zafiyet login sayfasın da gelen username ve password'un kontrollerinin zayıf olmasıyla ilgilidir.
<p>Session currentSession = sessionFactory.getCurrentSession();</p>
<p>String sql = "select * from user where username='" + username + "' and password='" + password + "'";//SQL string</p>


<p>SQLQuery query = currentSession.createSQLQuery(sql);//SQL komutunun querye eklenmesi</p>
<p>query.addEntity(User.class);</p>


<h3>1.3-Sömürü</h3>
"select * from user where username=' ' or 1=1 ' AND password=' ' or 1=1';<br>
<p>girildiğinde yani username ve passworde yukarıdaki karakterler girildiğinde SQL dönen komut ile ilk veri tabanından gelen herhangi bir kullanıcı ile sisteme giriş yapılmış olacaktır.Bu şekilde sistemdeki kişilerin bilgileri sömürülebilir.
    Örneğin aşağıdaki get isteği yapıldığında tablodaki ilk kullanıcı ile giriş yapılacaktır.</p>
http://localhost:8080/sqlInjection?username=' or '1'='1&password=' or '1'='1

<h3>1.4-İyileştirme</h3>
Eğer aldığımız değerleri direkt sql query’de kullanmak yerine özel karakterleri filtreleyebiliriz. Bizim örneğimizde where kısmının Criteria ile yaptığımız durumda filtreleme otomatik yapılacaktır.<br>
Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);<br>
criteria.add(Restrictions.eq("username", username));<br>
criteria.add(Restrictions.eq("password", password));<br>
return criteria.list().size() > 0 ? (User) criteria.list().get(0) : null;<br>



<h3>2.1-XSS-Cross Site Script Zafiyeti</h3>
XSS (Cross Site Scripting) script kodları üzerinden (genelde javascript) bir web sayfasına saldırı yapılmasıdır.XSS çoğunlukla tarayıcıda saklanan bilgiler olan cookielere saldırı amacı ile kullanılmaktadır.

<h3>2.2-Güvensiz Kodlama</h3>
c:forEach var="comment" items="${comments}"<br>
  c:out escapeXml="false" value="${comment.message}">/c:out // gelen deger xml’den kacmadan yaziliyor<br>
/c:forEach<br>
XSS script kodları üzerinden gerçekleştirilen bir saldırılır ve bu zafiyeti gidermek için herhangi bir kontrol konulmadığından dolayı kolaylıkla erişim sağlanır.

<h3>2.3-Sömürü</h3>
Sömürü kolaylıkla' script>"herhangi bir komut" /script ' bu şekilde sağlanabilir. Örneğin <%--<script>alert('XSS')</script> --%>şeklinde siteye bir comment eklediğimiz de giren kullanıcılara XSS diye bir alet gösterilecektir.Bu şekilde zafiyetten yararlanılarak bir çok saldırı yapılabilir.
Örneğin aşağıdaki gibi bir istek yapıldığında bu sayfayı görüntüleyen tüm kullanıcılarda Hello yazan bir alert görünecektir.<br>
http://localhost:8080/xss?postComment=%3Cscript%3Ealert(%27hello%27);%3C/script%3E

<h3>2.4-İyileştirme</h3>
Kullanıcının yorum olarak girdiği değeri gösterir iken html karakterini es geçer isek bu saldırıyı engellemiş oluruz.<br>
c:forEach var="comment" items="${comments}"<br>
   c:out escapeXml="true" value="${comment.message}">/c:out // bu durumda girilen html veyaz js calismayacaktir--%><br>
/c:forEach<br>


<h3>3.1-Command Injection Zafiyeti</h3>
Command Injection, yani komut enjeksiyonu saldırganın zafiyet barındıran bir uygulama üzerinden hedef sistemde dilediği komutları çalıştırabilmesine denir. Komut ile kastedilen şey Windows'ta CMD ve Linux'ta Terminal pencerelerine girilen sistem komutlarıdır. Literatürde Shell kodlaması diye de geçer. Command Injection saldırısı büyük oranda yetersiz input denetleme mekanizması nedeniyle gerçekleşmektedir.

<h3>3.2-Güvensiz Kodlama</h3>
public static String executeCommand(String command)//komutun çalıştığı kısım<br>
{<br>
command = "cmd.exe /c " + command;<br>
StringBuffer output = new StringBuffer();<br>

Process p;<br>
try<br>
{<br>
p = Runtime.getRuntime().exec(command);<br>
p.waitFor();<br>
BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));<br>

String line;<br>
while ((line = reader.readLine()) != null)<br>
{<br>
output.append(line + "\n");<br>
}<br>

}<br>
catch (Exception e)<br>
{<br>
e.printStackTrace();<br>
}<br>

return output.toString();<br>

}<br>

Yukarıda komutun gönderildiği ve çalıştırıldığı fonksiyon bulunmaktadır.Aşağıda ise command'in gönderildiği controller bulunmaktadır.Fakat controller'a gelen host için herhangi bir kontrol yapılmamaktadır.Controller içinde host'la ilgili bazı kontroller yapılması gerekmektedir gelen host'a bazı kısıtlamalar getirilmeli yada karakterleri belli bir patterne göre alınmalıdır ki injection engellensin.
<br>
if (isNotBlank(host))//Basit blank kontrolu<br>
{<br>
String command = "ping  " + host; //gelen hostun command stringine eklenir.<br>
model.addAttribute("result", executeCommand(command)); // execute olması için fonksiyonuna gönderilir<br>
}<br>






<h3>3.3-Sömürü</h3>
Saldırgan cmd'ye rahat bir şekilde ulaşabildiği için artık bir çok şeyi yapabilir.Mesela 1;ls yazdığımız da 1 sayısı ile 1 adresine ping at demiş oluyoruz bu geçersiz bir komut olarak sayılacağı için ekrana sadece gönderdiğimiz komut ile ilgili bilgiler gelecektir.ls komutu ile de dizindeki dosyalarının isimlerini görüyoruz cd .. ile devam ederek dosyalar arasında geçişler yaparak tüm dosyalarına ulaşabiliriz.Bu yönden çok tehlikeli bir zafiyettir.
Örneğin aşağıdaki örnekte olduğu gibi kendi kodumuzu çalıştırabiliriz. Burada son kısımda yazan işlem echo test bizim ekledigimiz bir koddur ve calisacaktir.<br>
http://localhost:8080/commandInjection?host=www.google.com%26%26echo%20test


<h3>3.4-İyileştirme</h3>
Eğer gelen komutu direkt olarak çalıştırmayız aşağıdaki gibi bir filtreden geçirir isek bu saldırıyı engellemiş oluruz. Bizim örneğimiz için aşağıdaki gibi sadece beklenen url pattern’i varsa sadece o değer için işlem yapılacaktır.
<br>
private String filterHost(String command)<br>
{<br>
String regex = "www.[a-z-Z]{0,}.(com|net|org)";<br>
Pattern r = Pattern.compile(regex);<br>
Matcher m = r.matcher(command);<br>
if(m.find())<br>
return m.group();<br>
return "";<br>
}<br>
