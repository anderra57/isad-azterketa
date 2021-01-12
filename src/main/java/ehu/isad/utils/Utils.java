package ehu.isad.utils;

import com.google.gson.Gson;
import ehu.isad.model.ProbaModel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Utils {

    public static Properties getProperties()  {
        Properties properties = null;

        try (InputStream in = Utils.class.getResourceAsStream("/setup.properties")) {
            properties = new Properties();
            properties.load(in);

        } catch (
                IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    private ProbaModel readFromUrl(String value) throws IOException {
        // get JSON
        URL api = new URL("website.com?id="+value+"");
        URLConnection yc = api.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine = in.readLine(); // raw data
        in.close();
        // process if needed
        Gson gson = new Gson();
        return gson.fromJson(inputLine, ProbaModel.class);
    }

    private void saveImage(String izena){
        //Emandako izena duen irudia zerbitzarira eskatzen da, eta sisteman gordetzen da
        BufferedImage image;
        try{
            URL url =new URL("website.com"+izena+"/foto.png");
            image = ImageIO.read(url);
            ImageIO.write(image, "png", new File(Utils.getProperties().getProperty("pathToImages")+izena+".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}