package tao.hotplug.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HotLoader extends ClassLoader{

    public HotLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> loadClass(String pathname,String classname) throws ClassNotFoundException {
       
        try {
            //String url = "file:C:/data/projects/tutorials/web/WEB-INF/" +
            //                "classes/reflection/MyObject.class";
            String url = pathname;
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(classname,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}