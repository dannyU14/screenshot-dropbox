import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class Main {

    public static void main(String[] args) {


        String ACCESS_TOKEN = Token.Token1;

        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        for(;;){


            MyThread thread = new MyThread(client);
            thread.start();

            try {
                thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Проблема с приостановкой потока");
            }

        }

    }
}
