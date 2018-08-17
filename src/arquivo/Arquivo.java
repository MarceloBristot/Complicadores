package arquivo;

import java.io.*;

public class Arquivo {

    public static StringBuilder lerArquivo(String path) throws FileNotFoundException, IOException {
        StringBuilder result = new StringBuilder();
        FileInputStream arq = null;
        try{
            File file = new File(path);
            arq = new FileInputStream(file);

            int caracterLido = arq.read();

            while(caracterLido != -1){
                result.append((char) caracterLido);
                caracterLido = arq.read();
            }

        }finally{
            if(arq != null)
                arq.close();
        }
        return result;
    }

    public static boolean gravarArquivo(String path, String str) throws FileNotFoundException, IOException{
        FileOutputStream arq = null;
        PrintStream ps = null;
        try {
            File file = new File(path);
            arq = new FileOutputStream(file);

            try {
                ps = new PrintStream(arq);
                ps.println(str);
            }finally {
                if(ps != null)
                    ps.close();
            }
        }finally {
            if(arq != null)
                arq.close();
        }
        return true;
    }
}
