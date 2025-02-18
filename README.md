
---

## 🌟 Giới Thiệu

Hệ Thống Quản Lý Thư Viện (LMS) là một phần mềm trực tuyến giúp thư viện và người quản lý dễ dàng quản lý kho tài nguyên của mình, bao gồm sách, tạp chí, báo, và các tài liệu nghiên cứu khác. LMS giúp tối ưu hóa việc mượn, trả, và theo dõi tài liệu, đồng thời cung cấp các tính năng hỗ trợ quản lý và phục vụ nhu cầu người dùng một cách hiệu quả.

- **Công nghệ sử dụng**: 
- **Backend**:
  - Spring Boot
  - Spring Security
  - Spring JPA
  - Hibernate

- **Frontend**:
  - HTML, CSS, Bootstrap
  - JavaScript, jQuery

- **Database**:
  - MySQL

- **Containerization**:
  - Docker
---

![Database](https://i.ibb.co/1sW6KH4/Untitled-2.png)

## ✨ Tính Năng Của User

- Mượn sách và trả sách
- Tìm kiếm sách theo các giá trị của sách, tìm kiếm sách nhờ vào api google books.
- Xem lịch sử mượn sách
- Xem những sách đang mượn
- Đặt trước sách khi hết số lượng sách
- Được tự động mượn sách khi đặt trước nếu sách đã có ở thư viện
- Có thể thêm sách vào giỏ hàng để mượn được nhiều sách
- Được thông báo real-time về quá trình mượn sách, được admin thông báo để cập nhật tình hình mới nhất

## ✨ Tính Năng Của Admin

- Theo dõi được số liệu mượn sách và trả sách của người dùng
- Có các tính năng search sách giống người dùng
- Xem lịch sử mượn sách của người dùng
- Thêm sách, chỉnh sửa thông tin sách, xóa sách, chỉnh sửa thông tin người dùng
- Có thể xóa người dùng khi họ không online
- Có thể ẩn tạm thời những sách muốn ẩn và hiện lại bất cứ khi nào muốn 
- Thông báo về tình hình mới nhất cho tất cả user

---


## 💻 Cài Đặt Môi Trường

Để chạy ứng dụng Spring Boot, bạn cần cài đặt một số công cụ và phần mềm sau:

### 1. **Java Development Kit (JDK)**
   - Bạn cần cài đặt **JDK 17** (hoặc phiên bản Java mới hơn).
   - Tải JDK tại [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) hoặc sử dụng **OpenJDK**.

### 2. **Maven (hoặc Gradle)**
   - Ứng dụng này sử dụng **Maven** để quản lý dependencies và build dự án.
   - Tải Maven tại [Maven](https://maven.apache.org/download.cgi) (nếu chưa cài đặt).

### 3. **Cơ Sở Dữ Liệu (nếu có)**
   - Nếu ứng dụng của bạn sử dụng cơ sở dữ liệu, hãy đảm bảo bạn đã cài đặt và cấu hình MySQL (hoặc các hệ quản trị cơ sở dữ liệu khác).
   - Cập nhật các thông tin kết nối cơ sở dữ liệu trong `application.properties` hoặc `application.yml` của bạn.

---

## 🚀 Cách Chạy Ứng Dụng

### 1. **Clone Dự Án**

Trước tiên, bạn cần clone dự án về máy của mình.

```
git clone https://github.com/nguyentanjr/lms
cd <repo>
```
2. Cài Đặt Dependencies
Sử dụng Maven để cài đặt tất cả các dependencies.

```
mvn install
```
3. Chạy Ứng Dụng
Sau khi cài đặt xong tất cả dependencies, bạn có thể chạy ứng dụng bằng lệnh Maven:

```
mvn spring-boot:run
Hoặc bạn có thể chạy trực tiếp file .jar đã build.
```
```
Copy code
java -jar target/<tên-file-jar>.jar
```
