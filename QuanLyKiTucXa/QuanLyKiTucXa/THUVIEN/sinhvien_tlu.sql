-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 23, 2024 lúc 04:27 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `sinhvien_tlu`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `class`
--

CREATE TABLE `class` (
  `stClass` int(10) NOT NULL,
  `className` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `class`
--

INSERT INTO `class` (`stClass`, `className`) VALUES
(1, '64CNTT1'),
(2, '64CNTT2'),
(3, '64CNTT3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `feedback`
--

CREATE TABLE `feedback` (
  `feedbackID` int(11) NOT NULL,
  `stID` int(11) DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `feedback`
--

-- INSERT INTO `feedback` (`feedbackID`, `stID`, `comment`, `status`) VALUES
-- (1, 1, 'Phòng ở rất sạch sẽ và thoải mái.', 1),
-- (2, 10, 'Dịch vụ tốt, nhân viên thân thiện.', 1),
-- (3, 30, 'Phòng ốc có vấn đề về tiếng ồn từ bên ngoài.', 0),
-- (4, 4, 'Yêu cầu sửa chữa cửa sổ phòng ngủ.', 0),
-- (5, 5, 'Không có nước nóng trong phòng tắm.', 0),
-- (6, 6, 'Phòng không có ánh sáng đủ.', 0),
-- (7, 21, 'Wifi không ổn định.', 0),
-- (8, 12, 'Thiết bị điều hòa hỏng.', 0),
-- (9, 40, 'Cần cải thiện vấn đề vệ sinh.', 1),
-- (10, 5, 'Phòng ốc có mùi khó chịu.', 0),
-- (11, 15, 'Điện không ổn định, thường xuyên mất điện.', 1),
-- (12, 18, 'Khoá cửa không hoạt động tốt.', 0),
-- (13, 31, 'Cần cải thiện dịch vụ giặt ủi.', 0),
-- (14, 24, 'Thiếu đồ dùng như giường, tủ quần áo.', 1),
-- (15, 27, 'Cần sửa chữa vấn đề về cửa sổ.', 1),
-- (16, 2, 'Phòng ốc không được làm sạch kỹ lưỡng.', 0),
-- (17, 3, 'Yêu cầu thêm đèn trong phòng.', 0),
-- (18, 7, 'Ghế sofa bị hỏng.', 0),
-- (19, 9, 'Cần cải thiện vấn đề vệ sinh.', 1),
-- (20, 11, 'Phòng ngủ thiếu đèn.', 0),
-- (21, 13, 'Cần sửa chữa vấn đề về vòi sen.', 0),
-- (22, 16, 'Thiếu đồ dùng như bàn, ghế.', 1),
-- (23, 19, 'Gạch lát sàn bị vỡ.', 0),
-- (24, 22, 'Cần cải thiện dịch vụ giặt ủi.', 0),
-- (25, 23, 'Cần sửa chữa vấn đề về bồn tắm.', 1),
-- (26, 25, 'Yêu cầu thêm tiện nghi như tủ lạnh.', 0),
-- (27, 26, 'Cần cải thiện vấn đề vệ sinh.', 1),
-- (28, 28, 'Cửa sổ không đóng kín.', 0),
-- (29, 32, 'Không có bàn làm việc.', 0),
-- (30, 35, 'Ghế ngồi không thoải mái.', 0);



-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `paymenthistory`
--

CREATE TABLE `paymenthistory` (
  `paymentID` int(11) NOT NULL,
  `stayID` int(11) DEFAULT NULL,
  `paymentDate` date DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `paymentMethod` varchar(50) DEFAULT NULL,
  `paymentStatus` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rooms`
--

CREATE TABLE `rooms` (
  `stRoom` int(11) NOT NULL,
  `roomNumber` int(11) NOT NULL,
  `floor` int(11) DEFAULT NULL,
  `roomType` int(11) DEFAULT NULL,
  `capacity` int(11) DEFAULT NULL,
  `building` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `rooms`
--

INSERT INTO `rooms` (`stRoom`, `roomNumber`, `floor`, `roomType`, `capacity`, `building`, `status`) VALUES
(1, 101, 1, 1, 4, 'k1', 'Trống'),
(2, 102, 1, 1, 6, 'k1', 'Đầy'),
(3, 201, 2, 0, 5, 'k2', 'Trống'),
(4, 202, 2, 0, 0, 'k2', 'Đang bảo dưỡng'),
(5, 301, 3, 0, 3, 'k3', 'Trống'),
(6, 302, 3, 1, 6, 'k3', 'Đầy'),
(7, 203, 2, 1, 2, 'k1', 'Trống'),
(8, 204, 2, 1, 2, 'k1', 'Trống'),
(9, 303, 3, 0, 6, 'k1', 'Đầy'),
(10, 304, 3, 0, 5, 'k1', 'Trống'),
(11, 104, 1, 1, 5, 'k1', 'Trống'),
(12, 103, 1, 1, 2, 'k1', 'Trống'),
(13, 205, 2, 1, 5, 'k3', 'Trống'),
(14, 206, 2, 1, 5, 'k3', 'Trống'),
(15, 105, 1, 0, 1, 'k3', 'Trống'),
(16, 106, 1, 0, 0, 'k3', 'Đang bảo dưỡng'),
(17, 305, 3, 1, 1, 'k3', 'Trống'),
(18, 306, 3, 1, 1, 'k3', 'Trống'),
(19, 401, 4, 1, 1, 'k3', 'Trống'),
(20, 402, 4, 1, 5, 'k3', 'Trống'),
(21, 403, 4, 0, 6, 'k3', 'Đầy'),
(22, 404, 4, 0, 5, 'k3', 'Trống'),
(23, 405, 4, 1, 5, 'k3', 'Trống'),
(24, 406, 4, 1, 5, 'k3', 'Trống'),
(25, 407, 4, 1, 5, 'k3', 'Trống'),
(26, 408, 4, 1, 5, 'k3', 'Trống'),
(27, 409, 4, 0, 5, 'k3', 'Trống'),
(28, 410, 4, 0, 5, 'k3', 'Trống'),
(29, 411, 4, 1, 5, 'k3', 'Trống'),
(30, 412, 4, 1, 6, 'k3', 'Đầy');




-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `stayhistory`
--

CREATE TABLE `stayhistory` (
  `stayID` int(11) NOT NULL,
  `stID` int(11) DEFAULT NULL,
  `stRoom` int(11) DEFAULT NULL,
  `checkInDate` date DEFAULT NULL,
  `checkOutDate` date DEFAULT NULL,
  `stayStatus` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `student`
--

CREATE TABLE `student` (
  `stID` int(10) NOT NULL,
  `stMSV` varchar(20) DEFAULT NULL,
  `stName` varchar(100) NOT NULL,
  `stBirth` date NOT NULL,
  `stGender` tinyint(1) NOT NULL,
  `stPhone` int(20) NOT NULL,
  `stClass` int(10) NOT NULL,
  `stPlace` varchar(100) NOT NULL,
  `stRoom` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `student`
--

INSERT INTO `student` (`stID`, `stMSV`, `stName`, `stBirth`, `stGender`, `stPhone`, `stClass`, `stPlace`, `stRoom`) VALUES
(1, '2251061847', 'Nguyen Dinh Long', '2018-12-05', 0, 961507110, 1, 'Ha Noi', 1),
(2, '2251061858', 'Nguyen Hai Vu', '2018-12-05', 0, 961507121, 1, 'Ha Noi', 2),
(3, '2251061869', 'Phung Tieu Y', '2018-12-05', 1, 961507132, 2, 'Ha Noi', 4),
(4, '2251061880', 'Nguyen Tran Trong', '2018-12-05', 0, 961507143, 3, 'Hai Duong', 6),
(5, '2251061882', 'Vu Cam Dao', '2018-12-05', 1, 961507145, 3, 'Hai Duong', 6),
(6, '2251061883', 'Vu Viet Huu', '2018-12-05', 0, 961507146, 3, 'Hai Duong', 6),
(7, '2251061884', 'Le Minh Anh', '2018-12-05', 1, 961507147, 3, 'Hai Duong', 19),
(8, '2251061885', 'Dinh Cong Trang', '2018-12-05', 0, 961507148, 3, 'Hai Duong', 19),
(9, '2251061886', 'Hoang Tuan Kiet', '2018-12-05', 0, 961507149, 3, 'Hai Duong', 19),
(10, '2251061848', 'Le Thanh Ba', '2018-12-05', 0, 961507111, 1, 'Ha Noi', 1),
(11, '2251061849', 'Ngo Dinh An', '2001-12-12', 0, 961507112, 1, 'Ha Noi', 1),
(12, '2251061850', 'Tran Thai Ha', '2018-12-05', 0, 961507113, 1, 'Ha Noi', 1),
(13, '2251061851', 'Bui Thanh Nha', '2018-12-05', 1, 961507114, 1, 'Ha Noi', 1),
(14, '2251061852', 'Tran Van Loc', '2018-12-05', 0, 961507115, 1, 'Ha Noi', 1),
(15, '2251061853', 'Ngo Ma Hai', '2018-12-05', 0, 961507116, 1, 'Ha Noi', 2),
(16, '2251061854', 'Ngo Si Thanh', '2018-12-05', 1, 961507117, 1, 'Ha Noi', 2),
(17, '2251061855', 'Luong Tuan Hai', '2018-12-05', 0, 961507118, 1, 'Ha Noi', 2),
(18, '2251061856', 'Vu Thanh Nguyet', '2018-12-05', 1, 961507119, 1, 'Ha Noi', 2),
(19, '2251061857', 'Dang Tieu Binh', '2018-12-05', 0, 961507120, 1, 'Ha Noi', 2),
(20, '2251061859', 'Andrea Gomes', '2018-12-05', 0, 961507122, 1, 'Ha Noi', 2),
(21, '2251061860', 'Le Thanh Bay', '2001-12-12', 0, 961507123, 2, 'Ha Noi', 3),
(22, '2251061861', 'Tran Duc Ha', '2018-12-05', 0, 961507124, 2, 'Ha Noi', 3),
(23, '2251061862', 'Le Nguyet Ha', '2018-12-05', 1, 961507125, 2, 'Ha Noi', 3),
(24, '2251061863', 'Tran Tien Vu', '2018-12-05', 0, 961507126, 2, 'Ha Noi', 3),
(25, '2251061864', 'Ha Nhu An', '2018-12-05', 1, 961507127, 2, 'Ha Noi', 3),
(26, '2251061865', 'Nguyen Thanh Hoang', '2018-12-05', 0, 961507128, 2, 'Ha Noi', 3),
(27, '2251061866', 'Hoang Duc An', '2018-12-05', 0, 961507129, 2, 'Ha Noi', 3),
(28, '2251061867', 'Dinh Viet Anh', '2018-12-05', 0, 961507130, 2, 'Ha Noi', 4),
(29, '2251061868', 'La Nhu Minh', '2018-12-05', 0, 961507131, 2, 'Ha Noi', 4),
(30, '2251061870', 'Tran Van Luc', '2018-12-05', 0, 961507133, 2, 'Hai Duong', 4),
(31, '2251061871', 'Nguyen Xuan Phuc', '2018-12-05', 1, 961507134, 2, 'Hai Duong', 4),
(32, '2251061872', 'Nguyen Thang Long', '2018-12-05', 0, 961507135, 2, 'Hai Duong', 4),
(33, '2251061873', 'Le Duc Tho', '2018-12-05', 0, 961507136, 2, 'Hai Duong', 5),
(34, '2251061874', 'Tran Van Van', '2018-12-05', 1, 961507137, 3, 'Hai Duong', 5),
(35, '2251061875', 'Tran Van Linh', '2018-12-05', 0, 961507138, 3, 'Hai Duong', 5),
(36, '2251061876', 'Tran Ba Dao', '2018-12-05', 1, 961507139, 3, 'Hai Duong', 5),
(37, '2251061877', 'Le Hoang Ha', '2018-12-05', 1, 961507140, 3, 'Hai Duong', 5),
(38, '2251061878', 'Obama', '2018-12-05', 0, 961507141, 3, 'Hai Duong', 5),
(39, '2251061879', 'Nguyen Hai Long', '2018-12-05', 0, 961507142, 3, 'Hai Duong', 6),
(40, '2251061881', 'Le Da Thanh', '2018-12-05', 0, 961507144, 3, 'Hai Duong', 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `userID` int(10) NOT NULL,
  `userName` varchar(40) NOT NULL,
  `passWord` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`userID`, `userName`, `passWord`) VALUES
(1, 'admin', 'admin'),
(2, 'abc', '123');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `class`
--
ALTER TABLE `class`
  ADD PRIMARY KEY (`stClass`);

--
-- Chỉ mục cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedbackID`),
  ADD KEY `stID` (`stID`);

--
-- Chỉ mục cho bảng `paymenthistory`
--
ALTER TABLE `paymenthistory`
  ADD PRIMARY KEY (`paymentID`),
  ADD KEY `fk_StayID` (`stayID`);

--
-- Chỉ mục cho bảng `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`stRoom`),
  ADD UNIQUE KEY `RoomNumber_UNIQUE` (`roomNumber`);

--
-- Chỉ mục cho bảng `stayhistory`
--
ALTER TABLE `stayhistory`
  ADD PRIMARY KEY (`stayID`),
  ADD KEY `fk_stID` (`stID`),
  ADD KEY `fk_stRoom` (`stRoom`);

--
-- Chỉ mục cho bảng `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`stID`),
  ADD KEY `fk_stClass` (`stClass`),
  ADD KEY `fk_stRoom_student` (`stRoom`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `class`
--
ALTER TABLE `class`
  MODIFY `stClass` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `feedback`
--
ALTER TABLE `feedback`
  MODIFY `feedbackID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `paymenthistory`
--
ALTER TABLE `paymenthistory`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `rooms`
--
ALTER TABLE `rooms`
  MODIFY `stRoom` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT cho bảng `stayhistory`
--
ALTER TABLE `stayhistory`
  MODIFY `stayID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `student`
--
ALTER TABLE `student`
  MODIFY `stID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`stID`) REFERENCES `student` (`stID`);

--
-- Các ràng buộc cho bảng `paymenthistory`
--
ALTER TABLE `paymenthistory`
  ADD CONSTRAINT `fk_StayID` FOREIGN KEY (`stayID`) REFERENCES `stayhistory` (`stayID`);

--
-- Các ràng buộc cho bảng `stayhistory`
--
ALTER TABLE `stayhistory`
  ADD CONSTRAINT `fk_stID` FOREIGN KEY (`stID`) REFERENCES `student` (`stID`),
  ADD CONSTRAINT `fk_stRoom` FOREIGN KEY (`stRoom`) REFERENCES `rooms` (`stRoom`);

--
-- Các ràng buộc cho bảng `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_stClass` FOREIGN KEY (`stClass`) REFERENCES `class` (`stClass`),
  ADD CONSTRAINT `fk_stRoom_student` FOREIGN KEY (`stRoom`) REFERENCES `rooms` (`stRoom`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
