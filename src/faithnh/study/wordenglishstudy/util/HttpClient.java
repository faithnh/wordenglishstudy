package faithnh.study.wordenglishstudy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {
    public static byte[] getByteArrayFromURL(String strUrl) {   
        byte[] byteArray = new byte[1024];   
        byte[] result = null;   
        HttpURLConnection con = null;   
        InputStream in = null;   
        ByteArrayOutputStream out = null;   
        int size = 0;   
        try {   
            URL url = new URL(strUrl);   
            con = (HttpURLConnection) url.openConnection();   
            con.setRequestMethod("GET");   
            con.connect();   
            in = con.getInputStream();   
  
            out = new ByteArrayOutputStream();   
            while ((size = in.read(byteArray)) != -1) {   
                out.write(byteArray, 0, size);   
            }   
            result = out.toByteArray();   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (con != null)   
                    con.disconnect();   
                if (in != null)   
                    in.close();   
                if (out != null)   
                    out.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }
        return result;
    }
    public static String getContentByPOSTMethod(String uri, String param)
    	throws Exception{
    	InputStreamReader ir1 = null;
    	BufferedReader br1 = null;
    	try{
	    	URL url = new URL(uri);
	    	URLConnection con = url.openConnection();
	    	con.setDoOutput(true);
	    	con.connect();
	
	        // 出力ストリームを取得
	    	PrintStream out = new PrintStream(con.getOutputStream());
	    	out.print(param);
	    	out.close();
	    	con.getContent(); //This is needed or setDataSource will throw IOException
			ir1 = new InputStreamReader(con.getInputStream());
			br1 = new BufferedReader(ir1);
			String file = "";
			String line;
			while((line=br1.readLine()) != null)
			{
				file = file + line;
			}
			return file;
    	}finally{
    		if(br1 != null){
    			br1.close();
    		}
    		if(ir1 != null){
    			ir1.close();
    		}
    	}
    }

}
