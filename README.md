- CHUẨN BỊ TRƯỚC KHI CHẠY DỰ ÁN
   + Chạy file sql kèm theo - để tạo các table tương ứng
   + Mở application.properties để cấu hình lại mariadb userName và passWord cho phù hợp với máy.
   + Chạy chương trình sẽ tự đông thêm 1000 mẫu tin ứng viên và thêm 20 skill mặc định vào csdl
- GIỚI THIỆU VỀ DỰ ÁN (truy cập http://localhost:8080):
   - Trang chủ trang web tuyển dụng:
      + Hiển thị các thông công việc của tất cả các công ty đang đăng tuyển. 
   - Danh sách ứng viên (Phân trang và không phân trang)
      + Thêm ứng viên mới (Nhập đầy đủ thông tin của ứng viên và địa chỉ kèm theo để thêm ứng viên)
      + Xóa ứng viên (Chọn ứng viên cần xóa và bấm xác nhận để thực hiện bỏ ứng viên - không khuyến kích xóa)
      + Chỉnh sửa ứng viên (Hiện thị các thông tin của ứng viên trước đó và thay đổi nếu cần và thực hiện cập nhật)
   - Ứng viên login
      + Đăng nhập (nhập email để đăng nhập)
      + Thêm kỹ năng (sẽ gợi ý các kỹ năng ứng viên chưa có để thêm vào)
      + Xóa kỹ năng (Chọn kỹ năng cần xóa nếu thêm sai, sau đó xác nhận xóa)
      + Chỉnh sửa lại hồ sơ của ứng viên (Hiện thị các thông tin của ứng viên trước đó và thay đổi nếu cần và thực hiện cập nhật)
      + Tìm việc phù hợp dựa trên skill của ứng viên có (Ứng dụng sẽ gợi ý công việc cho ứng viên dựa trên các công việc được các nhà tuyển dụng đăng tuyển, nếu skill của ứng viên đáp ứng được thì sẽ hiển thị)
   - Công ty login
      + Nếu chưa có công ty có thể thực hiện chức năng đăng ký ( nhập các thông tin cần thiết để đăng ký)
      + Đăng nhập bằng email
      + Đăng tin tuyển dụng (Sẽ bao gòm các thông tin của công việc và các skill yêu cầu cũng như level skill - Sau khi đăng tin tuyển dụng sẽ hiển thị ở trang chủ trang web tuyển dụng)
      + Tìm kiếm ứng viên phù hợp với công việc (gợi ý dựa vào ứng viên nào đáp ứng được yêu cầu các kỹ năng cũng như độ thành thạo - level skill)
      + Có thể sửa hồ sơ của công ty (khi thông tin bị sai có thể chỉnh sửa hồ sơ công ty)
- LƯU Ý KHI SỬ DỤNG
   + Không sử dụng tiếng việt có dấu (vì data sql setup không hổ trợ tiếng Việt có dấu)
- MACHINE LEARNING (CHƯA LÀM ĐƯỢC)