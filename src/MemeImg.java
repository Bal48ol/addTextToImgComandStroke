import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class MemeImg {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.print("\nВведите команду (help/meme)");
            System.out.println("\nПример ввода: java app_path <comand> [arguments]");
            return;
        }

        String command = args[0];
        switch (command) {
            case "help":
                displayHelpManual();
                break;
            case "meme":
                if (args.length != 5) {
                    System.out.println("\nОтсутствуют / Неверные аргументы. Прочитайте мануал (help)");
                    return;
                }
                String imagePath = args[1];
                String text = args[2];
                String position = args[3];
                String textFont = args[4];
                addTextToImage(imagePath, text, position, textFont);
                break;
            default:
                System.out.println("\nНеизвестная команда: " + command);
        }
    }

    private static void displayHelpManual() {
        System.out.println("\nКоманды:");
        System.out.println("  help\tМануал по использованию программы");
        System.out.println("  meme\tДобавить текст к картинке");

        System.out.println("\nАргументы:");
        System.out.println("  <img_path>\tПуть к файлу картинки");
        System.out.println("  <text>\tТекст, который нужно добавить картинке");
        System.out.println("  <position>\tНомер места в котором будет картинка:");
        System.out.println("    1\tВверху");
        System.out.println("    2\tВ центре");
        System.out.println("    3\tВнизу");
        System.out.println("  <text_font>\tНомер шрифта текста:");
        System.out.println("    1\tImpact");
        System.out.println("    2\tComic Sans MS");
        System.out.println("    3\tTimes New Roman");
        System.out.println("    4\tTahoma");

        System.out.println("\nПример ввода: java C:\\Users\\dayof\\IdeaProjects\\MemeImg\\src\\MemeImg.java meme C:\\Users\\dayof\\Desktop\\sobes\\123.png \"какой то текст\" 1 2");
    }

    private static void addTextToImage(String imagePath, String text, String position, String textFont) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Graphics2D graphics = image.createGraphics();

            // Установка шрифта и других свойств текста
            List<Font> fonts = new ArrayList<>();
            switch (textFont){
                case "1":
                    Font font1 = new Font("Impact", Font.BOLD, 36);
                    fonts.add(font1);
                    break;
                case "2":
                    Font font2 = new Font("Comic Sans MS", Font.BOLD, 36);
                    fonts.add(font2);
                    break;
                case "3":
                    Font font3 = new Font("Times New Roman", Font.BOLD, 36);
                    fonts.add(font3);
                    break;
                case "4":
                    Font font4 = new Font("Tahoma", Font.BOLD, 36);
                    fonts.add(font4);
                    break;
                default:
                    System.out.println("\nНеверный номер шрифта. Прочитайте мануал (help)");
                    return;
            }
            for (Font font : fonts){
                graphics.setFont(font);
                graphics.setColor(Color.WHITE);
            }

            // Расчет позиции для размещения текста
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(text);
            int textHeight = fontMetrics.getHeight();
            int x;
            int y;

            if (position.equalsIgnoreCase("1")) {
                x = (image.getWidth() - textWidth) / 2;
                y = textHeight;
            }
            else if (position.equalsIgnoreCase("2")) {
                x = (image.getWidth() - textWidth) / 2;
                y = (image.getHeight() - textHeight) / 2;
            }
            else if (position.equalsIgnoreCase("3")) {
                x = (image.getWidth() - textWidth) / 2;
                y = image.getHeight() - textHeight;
            } else {
                System.out.println("\nНеверное значение позиции текста. Прочитайте мануал (help)");
                return;
            }

            // Добавление текста на изображение
            graphics.drawString(text, x, y);
            graphics.dispose();

            // Сохранение измененного изображения
            String outputImagePath = getOutputImagePath(imagePath);
            ImageIO.write(image, "png", new File(outputImagePath));

            System.out.println("\nТекст добавлен к картинке. Путь мем-картинки: " + outputImagePath);
        }
        catch (IOException e) {
            System.out.println("\nОшибка: " + e.getMessage());
        }
    }

    private static String getOutputImagePath(String imagePath) {
        String directory = new File(imagePath).getParent();
        String fileName = new File(imagePath).getName();
        return directory + File.separator + "meme_" + fileName;
    }
}
