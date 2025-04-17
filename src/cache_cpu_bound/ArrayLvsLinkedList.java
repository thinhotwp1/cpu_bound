package cache_cpu_bound;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ArrayLvsLinkedList {
    public static void main(String[] args) {
//        arrayVsLinkedList();

        arrayListVsLinkedList();

    }

    private static void arrayVsLinkedList() {
        int[] arr = new int[1_000_000]; // Array chắc chắn nhanh hơn LinkedList nếu dùng để xử lý dữ liệu nguyên thủy như int, long,... vì Unboxing và lưu theo địa chỉ index -> cache lưu giá trị cạnh nhau
        for (int i = 0; i < arr.length; i++) arr[i] = i;
        long sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < arr.length; i++) sum += arr[i];
        long end = System.nanoTime();
        System.out.println("- Array sum: " + (end - start) / 1e6 + " ms");


        ArrayList<Integer> arrayList = new ArrayList<>(); // ArrayList lưu thông tin 1 node chỉ có địa chỉ giá trị của object -> nằm cạnh nhau trong cache CPU
        for (int i = 0; i < 1_000_000; i++) arrayList.add(i);
        sum = 0;
        start = System.nanoTime();
        for (Integer value : arrayList) sum += value;
        end = System.nanoTime();
        System.out.println("- ArrayList sum: " + (end - start) / 1e6 + " ms");

        LinkedList<Integer> linkedList = new LinkedList<>(); // LinkedList lưu thông tin 1 node gồm element + địa chỉ node trước + địa chỉ node sau -> nằm rải rác trong cache CPU
        for (int i = 0; i < 1_000_000; i++) linkedList.add(i);
        sum = 0;
        start = System.nanoTime();
        for (Integer value : linkedList) sum += value;
        end = System.nanoTime();
        System.out.println("- LinkedList sum: " + (end - start) / 1e6 + " ms");

        System.out.println("===> Array luôn nhanh hơn List trong việc xử lý các biến nguyên thủy, vì không bọc Unboxing & friendly cache do các dữ liệu nằm cạnh nhau trong cache CPU.\n" +
                "===> ArrayList lưu dữ liệu với index, truy xuất với chi phí O(1), nhanh hơn LinkedList khi sử dụng dữ liệu theo danh sách liên kết, truy xuất với chi phí O(n)");
    }

    private static void arrayListVsLinkedList() {
        record Book(String title, String author) {
        }

        List<Book> arrayList = new ArrayList<>();
        List<Book> linkedList = new LinkedList<>();

        // Insert đầu danh sách
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            Book b = new Book("Title " + i, "Author " + i);
            arrayList.add(0, b);
        }
        System.out.println("\nArrayList<Book> insert first index: " + (System.nanoTime() - start) / 1e6 + " ms");

        start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            Book b = new Book("Title " + i, "Author " + i);
            linkedList.add(0, b);
        }
        System.out.println("LinkedList<Book> insert first index: " + (System.nanoTime() - start) / 1e6 + " ms");
        System.out.println("===> Đối với insert đầu danh sách, LinkedList sẽ hiệu quả hơn ArrayList vì lưu object theo danh sách liên kết, khi insert vào đầu chỉ cần sửa object cũ liên kết với object mới và thêm 1 object chứa địa chỉ dữ liệu object O(1), còn với ArrayList thì cần chuyển toàn bộ các index phía sau lùi thêm 1 O(n)");

        arrayList.clear();
        linkedList.clear();

        // Insert cuối danh sách
        start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            Book b = new Book("Title " + i, "Author " + i);
            arrayList.add(b);
        }
        System.out.println("\nArrayList<Book> insert last index: " + (System.nanoTime() - start) / 1e6 + " ms");

        start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            Book b = new Book("Title " + i, "Author " + i);
            linkedList.add(b);
        }
        System.out.println("LinkedList<Book> insert last index: " + (System.nanoTime() - start) / 1e6 + " ms");
        System.out.println("===> Đối với insert cuối danh sách, ArrayList sẽ hiệu quả hơn LinkedList tận dụng cache tốt hơn, dữ liệu lưu trong cache CPU của ArrayList cạnh nhau, cả ArrayList và LinkedList đều O(1) khi insert vào cuối danh sách do ArrayList thêm object vào index cuối, còn LinkedList thì lưu cả địa chỉ của node đầu tiên và node cuối cùng");


        // Truy cập tuần tự
        start = System.nanoTime();
        for (Book b : arrayList) b.title();
        System.out.println("\nArrayList<Book> iterate access: " + (System.nanoTime() - start) / 1e6 + " ms");

        start = System.nanoTime();
        for (Book b : linkedList) b.title();
        System.out.println("LinkedList<Book> iterate access: " + (System.nanoTime() - start) / 1e6 + " ms");
        System.out.println("===> Đối với truy cập tuần tự, ArrayList sẽ nhanh hơn LinkedList, vì friendly cache, dữ liệu lưu trong cache CPU cạnh nhau, còn LinkedList duyệt lần lượt O(n)");


        // Truy cập random
        start = System.nanoTime();
        Random rand = new Random();
        for(int i = 0; i < 1_000; i++) {
            arrayList.get(rand.nextInt(arrayList.size())).title();
        }
        System.out.println("\nArrayList<Book> random access: " + (System.nanoTime() - start) / 1e6 + " ms");

        start = System.nanoTime();
        for(int i = 0; i < 1_000; i++) {
            linkedList.get(rand.nextInt(linkedList.size())).title();
        }
        System.out.println("LinkedList<Book> random access: " + (System.nanoTime() - start) / 1e6 + " ms");
        System.out.println("===> Đối với truy cập random, ArrayList sẽ nhanh hơn LinkedList, vì friendly cache, dữ liệu lưu trong cache CPU cạnh nhau, đồng thời truy cập bằng index O(1) nhanh hơn duyệt lần lượt O(n)");

        System.out.println("\n<---> Tóm lại, truy cập nhanh → ArrayList. Thao tác linh hoạt → LinkedList");
    }

}
