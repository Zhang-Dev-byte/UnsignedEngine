package Engine.Compiler;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigCompiler {

	public static List<String> LoadFile(String path) throws Exception{
		List<String> tokens = new ArrayList<String>();
        FileReader fr = new FileReader(path);
        int i;
        String code = "";
        while((i = fr.read())!=-1)
        code += (char)i;

        for(String token : code.split("\n")) {
        	tokens.add(token);
        }
        return tokens;
	}
}
