package Engine.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import Engine.Core.IOUtil;
import Engine.Rendering.Camera;
import Engine.Rendering.Entity;
import Engine.Rendering.Loader;
import Engine.Rendering.Texture;
import Engine.Structure.Structure;

public class Json {
	public static void SeralizeEntity(Entity e) throws Throwable {
	    JSONObject obj = new JSONObject();

	    obj.put("name",e.name);
	    obj.put("X", e.position.x);
	    obj.put("Y", e.position.y);
	    obj.put("Z", e.position.z);
	    obj.put("RX", e.rotation.x);
	    obj.put("RY", e.rotation.y);
	    obj.put("RZ", e.rotation.z);
	    obj.put("S", e.scale);
	    obj.put("T", e.mesh.texture.path);
	    obj.put("obj", e.object);

	    PrintWriter out = new PrintWriter(new File(e.name+".json"));
	    obj.write(out);
	    out.close();
	}
	public static JSONObject Deserialize(String path) throws Throwable {
		return new JSONObject(IOUtil.loadFile(path));
	}
	public static void Serialize(String path, JSONObject obj) throws Throwable {
	    PrintWriter out = new PrintWriter(new File(path));
	    obj.write(out);
	    out.close();
	}
	public static void DeserializeEntity(Structure struct, Camera cam) throws Throwable {

	    File[] f = new File("./").listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".json");
			}

	    });

	    for(File file : f) {
	    	String s = IOUtil.loadFile(file.getPath());
			JSONObject obj3 = new JSONObject(s);
			Entity e = new Entity(Loader.LoadFile((String)obj3.get("obj"), new Texture((String)obj3.get("T"))),cam,(String)obj3.get("name"), (String)obj3.get("obj"));
			e.position.x = obj3.getFloat("X");
			e.position.y = obj3.getFloat("Y");
			e.position.z = obj3.getFloat("Z");
			e.rotation.x = obj3.getFloat("RX");
			e.rotation.y = obj3.getFloat("RY");
			e.rotation.z = obj3.getFloat("RZ");
			e.scale = obj3.getFloat("S");
			struct.AddObject(e);
	    }

	}
	public static void SerializeCamera(Camera e) throws Throwable {
	    JSONObject obj = new JSONObject();

	    obj.put("X", e.position.x);
	    obj.put("Y", e.position.y);
	    obj.put("Z", e.position.z);

	    PrintWriter out = new PrintWriter(new File("scene/Camera.json"));
	    obj.write(out);
	    out.close();
	}
	public static void DeserializeCamera(Camera e) throws Throwable {
    	String s = IOUtil.loadFile("scene/Camera.json");
		JSONObject obj3 = new JSONObject(s);
		e.position.x = obj3.getFloat("X");
		e.position.y = obj3.getFloat("Y");
		e.position.z = obj3.getFloat("Z");
	}
}
