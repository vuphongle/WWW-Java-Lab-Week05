Chuẩn bị trước khi chạy dự án:
 + Chạy file sql kèm theo. (để tạo data trước như skill).
 + Mở application.properties để cấu hình lại mariadb userName và passWord cho phù hợp với máy.
 + Mở chú thích ở file LabWeek05Application.java (run) xong đóng chú thích lại (mục đích thêm 1000 mẫu tin vào csdl).
Giới thiệu về dự án (truy cập http://localhost:8080):
   - Trang chủ trang web tuyển dụng:
      + Hiển thị các thông job của các công ty đăng tuyển.
      + Ứng tuyển (chưa phát triển ở màn hình này)
   - Danh sách ứng viên (Phân trang và không phân trang)
      + Thêm ứng viên mới
      + Xóa ứng viên (Không khuyến khích)
      + Chỉnh sửa ứng viên
   - Ứng viên login
      + Đăng nhập (nhập email để đăng nhập)
      + Thêm kỹ năng (sẽ gợi ý các kỹ năng ứng viên chưa có để thêm vào)
      + Xóa kỹ năng (không khuyến khích)
      + Chỉnh sửa lại hồ sơ của ứng viên
      + Tìm việc phù hợp dựa trên skill của ứng viên có
   - Công ty login
      + Chưa có công ty được thêm vào có thể đăng ký
      + Đăng nhập bằng email
      + Đăng tin tuyển dụng - Sau khi đăng tin tuyển dụng sẽ hiển thị ở trang chủ trang web tuyển dụng
      + Danh sách công việc sẽ có tìm kiếm ứng viên phù hợp cho công việc đó
      + Có thể sửa hồ sơ của công ty
   ......
Lưu ý khi sử dụng:
 + Không sử dụng tiếng việt có dấu (Vì không hổ trợ tiếng Việt)