import java.util.ArrayList;
import java.util.Scanner;

public class KutuphaneSistemi {
    private static final ArrayList<Kitap> kitaplar = new ArrayList<>();
    private static int sonrakiId = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean devam = true;

        while (devam) {
            System.out.println("\n--- Kütüphane Sistemi ---");
            System.out.println("1. Kitapları listele");
            System.out.println("2. Kitap ekle");
            System.out.println("3. Kitap ödünç al");
            System.out.println("4. Kitap iade et");
            System.out.println("0. Çıkış");

            int secim = sayiOku(scanner, "Seçiminiz: ");

            switch (secim) {
                case 1:
                    kitaplariListele();
                    break;
                case 2:
                    kitapEkle(scanner);
                    break;
                case 3:
                    kitapOduncAl(scanner);
                    break;
                case 4:
                    kitapIadeEt(scanner);
                    break;
                case 0:
                    devam = false;
                    System.out.println("Program kapatıldı.");
                    break;
                default:
                    System.out.println("Geçersiz seçim.");
            }
        }

        scanner.close();
    }

    private static void kitaplariListele() {
        if (kitaplar.isEmpty()) {
            System.out.println("Kütüphanede henüz kitap yok.");
            return;
        }

        System.out.println("\nKitaplar:");
        for (Kitap kitap : kitaplar) {
            String durum = kitap.oduncte ? "Ödünçte" : "Mevcut";
            System.out.println(
                kitap.id + " - " + kitap.ad + " / " + kitap.yazar + " (" + durum + ")"
            );
        }
    }

    private static void kitapEkle(Scanner scanner) {
        System.out.print("Kitap adı: ");
        String ad = scanner.nextLine();

        System.out.print("Yazar adı: ");
        String yazar = scanner.nextLine();

        kitaplar.add(new Kitap(sonrakiId++, ad, yazar));
        System.out.println("Kitap eklendi.");
    }

    private static void kitapOduncAl(Scanner scanner) {
        int id = sayiOku(scanner, "Ödünç alınacak kitabın ID'si: ");
        Kitap kitap = kitapBul(id);

        if (kitap == null) {
            System.out.println("Bu ID ile bir kitap bulunamadı.");
        } else if (kitap.oduncte) {
            System.out.println("Bu kitap zaten ödünç alınmış.");
        } else {
            kitap.oduncte = true;
            System.out.println("Kitap ödünç alındı.");
        }
    }

    private static void kitapIadeEt(Scanner scanner) {
        int id = sayiOku(scanner, "İade edilecek kitabın ID'si: ");
        Kitap kitap = kitapBul(id);

        if (kitap == null) {
            System.out.println("Bu ID ile bir kitap bulunamadı.");
        } else if (!kitap.oduncte) {
            System.out.println("Bu kitap zaten kütüphanede.");
        } else {
            kitap.oduncte = false;
            System.out.println("Kitap iade edildi.");
        }
    }

    private static Kitap kitapBul(int id) {
        for (Kitap kitap : kitaplar) {
            if (kitap.id == id) {
                return kitap;
            }
        }
        return null;
    }

    private static int sayiOku(Scanner scanner, String mesaj) {
        while (true) {
            System.out.print(mesaj);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lütfen geçerli bir sayı girin.");
            }
        }
    }

    private static class Kitap {
        int id;
        String ad;
        String yazar;
        boolean oduncte;

        Kitap(int id, String ad, String yazar) {
            this.id = id;
            this.ad = ad;
            this.yazar = yazar;
            this.oduncte = false;
        }
    }
}