/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baitaptuan1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class bai2 {

    public static void main(String[] args) {

        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);

        String menu = """
                ===== CHƯƠNG TRÌNH QUẢN LÝ SÁCH =====
                1. Thêm 1 cuốn sách
                2. Xóa 1 cuốn sách theo mã
                3. Thay đổi thông tin sách
                4. Xuất danh sách sách
                5. Tìm sách có tiêu đề chứa "lập trình"
                6. Lấy thông tin sách <= giá nhập vào
                7. Lấy sách theo tác giả
                0. Thoát
                Chọn chức năng: 
                """;

        int chon;
        do {
            System.out.print(menu);
            chon = x.nextInt();

            switch (chon) {

                case 1 -> {
                    Book b = new Book();

                    while (true) {
                        b.input();

                        boolean trungId = listBook.stream()
                                .anyMatch(p -> p.getId() == b.getId());

                        if (trungId) {
                            System.out.println("❌ Mã sách đã tồn tại, vui lòng nhập lại!");
                        } else {
                            listBook.add(b);
                            System.out.println("✔ Thêm sách thành công");
                            break;
                        }
                    }
                }

                case 2 -> {
                    System.out.print("Nhập mã sách cần xóa: ");
                    int id = x.nextInt();
                    Book find = listBook.stream()
                            .filter(p -> p.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        listBook.remove(find);
                        System.out.println("✔ Đã xóa sách");
                    } else {
                        System.out.println("✖ Không tìm thấy sách");
                    }
                }

                case 3 -> {
                    System.out.print("Nhập mã sách cần sửa: ");
                    int id = x.nextInt();
                    x.nextLine();

                    Book find = listBook.stream()
                            .filter(p -> p.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (find != null) {
                        System.out.println("Nhập lại thông tin:");
                        find.input();
                        System.out.println("✔ Đã cập nhật sách");
                    } else {
                        System.out.println("✖ Không tìm thấy sách");
                    }
                }

                case 4 -> {
                    System.out.println("\n--- DANH SÁCH SÁCH ---");
                    listBook.forEach(Book::output);
                }

                case 5 -> {
                    List<Book> list5 = listBook.stream()
                            .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                            .toList();

                    System.out.println("\n--- SÁCH CÓ TỪ 'LẬP TRÌNH' ---");
                    list5.forEach(Book::output);
                }

                case 6 -> {
                    System.out.print("Nhập số cuốn sách cần lấy(k): ");
                    int K = x.nextInt();

                    System.out.print("Nhập giá sách(P): ");
                    long P = x.nextLong();

                    System.out.println("\n--- DANH SÁCH SÁCH THỎA MÃN ---");

                    listBook.stream()
                            .filter(b -> b.getPrice() <= P)
                            .limit(K)
                            .forEach(Book::output);
                }

                case 7 -> {
                    x.nextLine(); // clear buffer

                    System.out.print("Nhập danh sách tác giả (cách nhau bởi dấu phẩy): ");
                    String input = x.nextLine();

                    Set<String> authorSet = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet());

                    System.out.println("\n--- SÁCH THEO DANH SÁCH TÁC GIẢ ---");

                    listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor().toLowerCase()))
                            .forEach(Book::output);
                }

                case 0 ->
                    System.out.println("👋 Thoát chương trình");

                default ->
                    System.out.println("❌ Chọn sai chức năng");
            }

        } while (chon != 0);
    }
}
