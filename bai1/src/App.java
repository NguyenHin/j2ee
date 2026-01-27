import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String msg = """
                Chương trình quản lý sách
                1. Thêm 1 cuốn sách
                2. Xoá 1 cuốn sách
                3. Thay đổi sách
                4. Xuất thông tin
                5. Tìm sách lập trình
                6. Lấy sách tối đa theo giá
                7. Tìm kiếm theo tác giả
                0. Thoát
                Chọn chức năng: """;
        int chon = 0;
        do{
            System.out.println();
            System.out.printf(msg);
            chon = x.nextInt();
            x.nextLine();
            switch (chon) {
                case 1-> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }
                case 2-> {
                    System.out.print("Nhập vào mã sách cần xoá: ");
                    int bookid = x.nextInt();
                    Book find = listBook.stream().filter(p-> p.getId() == bookid).findFirst().orElseThrow();
                    listBook.remove(find);
                    System.out.print("Đã xoá sách thành công");
                }
                case 3-> {
                    System.out.print("Nhập vào mã sách cần điều chỉnh: ");
                    int bookid = x.nextInt();
                    Book find = listBook.stream().filter(p->p.getId() == bookid).findFirst().orElseThrow();
                    System.out.println("Nhập lại thông tin sách:");
                    find.input();
                }
                case 4-> {
                    System.out.println("\nXuất thông tin danh sách ");
                    listBook.forEach(p->p.output());
                }
                case 5-> {
                    List<Book> list5 = listBook.stream()
                        .filter(u -> u.getTitle().toLowerCase().contains("lập trình"))
                        .toList();
                        list5.forEach(Book :: output);
                }
                case 6-> {
                    System.out.print("Nhập số k: ");
                    int k = x.nextInt();
                    System.out.print("Nhập giá sách: ");
                    double p = x.nextDouble();
                    listBook.stream().filter(b->b.getPrice() <= p).limit(k).forEach(Book :: output);
                }
                case 7 -> {
                    System.out.print("Nhập danh sách tác giả (cách nhau dấu phẩy): ");
                    String input = x.nextLine();

                    Set<String> authors = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .collect(Collectors.toSet());

                    listBook.stream()
                            .filter(b -> authors.contains(b.getAuthor()))
                            .forEach(Book::output);
                }
            }
        }while (chon!=0); 

        x.close();   
    }
}
