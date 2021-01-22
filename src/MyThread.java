import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread{

    private DbxClientV2 client;


    public MyThread(DbxClientV2 client){
        this.client = client;
    }

    @Override
    public void run(){

        //1 - создаем скриншот

        // + задаём время
        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMdd_Hmss");
        Date now = new Date();
        String time = formatter.format(now);

        // + делаем скриншот
        BufferedImage image = null;
        try {
            image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException e) {
            e.printStackTrace();
            System.out.println("Проблемы с захватом скриншота");
        }

        //2 - конвертируем BufferedImage в InputStream

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Проблема с конвертацией файла");
        }
        InputStream inputStr = new ByteArrayInputStream(os.toByteArray());

        //3 - заливаем файл в Дропбокс

        try {
            //вместо InputStream in = new FileInputStream("/Users/daniella/Desktop/"); :
            InputStream in = inputStr;
            FileMetadata metadata = client.files().uploadBuilder("/" + time + ".png").uploadAndFinish(in);
            //название в дропбоксе будет форматированное (время)
        } catch (Exception dbe) {
            dbe.printStackTrace();
            System.out.println("Проблема с загрузкой файла в Dropbox");
        }

    }
}
