# Tên Dự Án: BlogForum (Reddit Clone)

## 📌 Tổng quan dự án
Đây là ứng dụng web diễn đàn (Forum) được code dựa trên cấu trúc mô phỏng UX của **Reddit**. Người dùng có thể Đăng tải bài viết, Tham gia tương tác (Upvote / Downvote), Bình luận đa tầng (Nested Comments), và Cập nhật thời gian thực (Real-time WebSockets).

**Công nghệ sử dụng (Tech Stack):**
- **Frontend:** Angular 17+ (RxJS, Bootstrap/CSS)
- **Backend:** Java 17, Spring Boot 3, Spring Security 6 (JWT)
- **Cơ sở dữ liệu:** MySQL (Spring Data JPA)
- **Triển khai:** Docker & Docker Compose

---

## 👥 Danh sách Thành viên & Phân công Chức năng (Features Breakdown)

Dự án được chia nhỏ thành các Module chức năng (Features) độc lập, nhằm đảm bảo **Tiến độ (Sprint)** và **Giảm thiểu xung đột Code (Merge Conflict)** trên Git.

### 1.Minh Huy - Project Leader & System Architect
*(Quản lý hạ tầng & Ráp nối hệ thống)*
- **[DevOps] Khởi tạo hệ thống:** Set up thư mục gốc, Git Repo, Docker Compose (`.yml`), tạo môi trường dev chung cho nhóm.
- **[Database] Thiết kế CSDL (Schema):** Tạo và duy trì bảng Users, Roles, Posts, Comments, Post_Likes. 
- **[Architecture] Chuẩn hóa Data:** Review Code của Frontend và Backend trước khi Merge vào nhánh chính (`main`). Hỗ trợ cấu trúc Model / Entity chung.
- **[Reviewer]** Giải quyết các Lỗi xung đột Merge trên Git.

### Backend Security & Auth Flow
*(Chịu trách nhiệm Cánh cổng Bảo mật)*
- **[API] Chức năng Đăng nhập & Đăng ký:** Validate Password sinh mã băm (BCrypt); code Endpoint `/api/v1/auth/signup` và `/signin`.
- **[Security] JWT Authentication:** Code bộ lọc `AuthTokenFilter`, sinh và xác thực JSON Web Token.
- **[Security] Phân quyền (Authorization):** Giới hạn đường dẫn API cho `[ROLE_USER]` và `[ROLE_ADMIN]` (Admin có quyền xoá comment rác).
- **[Feature] Fake Auth Data:** Cung cấp tài khoản test mẫu cho QA và Frontend làm việc.

### Khánh - Backend Core Business
*(Chịu trách nhiệm Xử lý Nghiệp vụ Lõi)*
- **[API] CRUD Bài Viết (Post):** Code Endpoint Tạo mới bài viết, Lấy danh sách toàn bộ bài viết, Lấy riêng Chi tiết 1 bài (`/api/v1/posts`).
- **[API] Tính năng Vote (Upvote/Downvote):** Thuật toán tính tổng điểm số hiển thị của Bài viết; tự động nhả vote nếu bấm 2 lần.
- **[API] Hệ thống Bình Luận Đa tầng:** Đệ quy (Recursive) lấy danh sách bình luận Cha - Con của 1 bài viết.
- **[Feature] Real-time Trigger:** Gắn WebSocket Stomp thông báo đẩy (Push Notification) khi có người Comment/Vote.

### Quý - UI/UX & Layout Core
*(Chịu trách nhiệm Giao diện Chung & Trải nghiệm Người dùng)*
- **[UI] Component Navbar & Footer:** Thanh điều hướng cố định trên cùng, Responsive trên Mobile.
- **[UI] Component Auth Modal (Login/Register):** Code giao diện Popup đăng nhập dạng Reddit (không chuyển trang), chứa nút Social Login (Giao diện 4 nút).
- **[UI] Component Trang chủ (Home):** Layout lưới danh sách bài đăng (Post Card) dạng Feed.
- **[UX]** Viết CSS dùng chung cho toàn dự án (`app.component.css`), tối ưu hiệu ứng Animation mượt mà. Đảm bảo form nhập liệu có báo lỗi đỏ khi sai định dạng.

### Sang - Feature Integration & Data
*(Chịu trách nhiệm Móc nối API vào UI & Xử lý Logic Angular)*
- **[Logic] Auth Services:** Code `auth.service.ts` gọi API Backend, lưu Token vào LocalStorage, xử lý lệnh Chặn Route (`AuthGuard`) đẩy bảng Đăng nhập bật lên.
- **[Logic] Data Services:** Kéo danh sách Bài viết từ Backend thả vào Giao diện Trang chủ của Frontend 1.
- **[Feature] Chi tiết Bài viết (Post Detail):** Code Logic cho Nút Vote đổi màu, Ô nhập Bình luận. Render (vẽ) luồng Bình luận đệ quy lồng nhau ra màn hình.
- **[Logic] Live Notification:** Viết Service bắt sóng Socket báo Toast dưới góc màn hình.

### Thành, Tiến - Quality Assurance & Bug Tracking
*(Chịu trách nhiệm Đảm bảo Chất lượng Không có Lỗi tàng hình)*
- **[Testing] Kịch bản Postman:** Bắn thử tất cả các API của Backend-1 & Backend-2. Bắt các mã Status Code (200 OK, 401 Unauthorized, 500 Error...).
- **[Testing] Kiểm thử Giao diện (Manual Test):** Đóng vai người dùng (User) bấm linh tinh các nút ở Frontend. Spam click nút Vote, bỏ trống Comment vẫn ấn Gửi, thử Login bằng chữ Tàu...
- **[Tracker] Ghi chú Lỗi (Bugzilla / Jira):** Mở thẻ (Ticket) ghi chép lại lỗi, tag thẳng tên Dev làm Module đó vào để sửa.
- **[Docs] Tài liệu hóa:** Viết sơ đồ API cho API Document, hoặc giúp Trưởng nhóm cập nhật file README này.

---

> 💡 **Quy tắc làm việc Nhóm (Team Rules):**
> 1. Không ai được Push thẳng code lên nhánh `main`. Phải tạo nhánh tính năng riêng (vd: `feature/login-api`) và tạo Pull Request (PR).
> 2. Ticket/Tính năng nào gắn tên người đó trong README thì người đó hoàn thành.
> 3. QA là người quyết định Tính năng đó đã Đạt (Pass) hay chưa để được đẩy vào bản Tích hợp Cuối.
