package enjine.core.gl;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import enjine.core.math.Vector3f;
import enjine.core.resources.ResourceManager;
import enjine.core.utils.Common;

public class Material {
    public Vector3f kd = new Vector3f(1, 1, 1);
    public Vector3f ka = new Vector3f(1, 1, 1);
    public Vector3f ks = new Vector3f(0, 0, 0);
    public Vector3f ke = new Vector3f(1, 1, 1);
    public float d = 0;
    public float ns = 0; // rang
    
    public int illum;
    
    public Texture map_Kd;
    //Not really ints.
    public int map_Ka = -1;
    public int map_Ks = -1;
    public int map_Ke = -1;
    public int map_Displ = -1;
    
    public Material() {}
    
    public Material(JSONObject obj) {
        
        /*
         * Vector3f
         */
        if(obj.has("kd"))
            kd = Common.jsonArrayToVector3f(obj.getJSONArray("kd"));
        if(obj.has("ka"))
            ka = Common.jsonArrayToVector3f(obj.getJSONArray("ka"));
        if(obj.has("ks"))
            ks = Common.jsonArrayToVector3f(obj.getJSONArray("ks"));
        if(obj.has("ke"))
            ke = Common.jsonArrayToVector3f(obj.getJSONArray("ke"));
        
        /*
         * float
         */
        if(obj.has("d"))
            d = (float)obj.getDouble("d");
        if(obj.has("ns"))
            ns = (float)obj.getDouble("ns");
        
        /*
         * int
         */
        if(obj.has("illum"))
            illum = obj.getInt("illum");
        
        /*
         * Maps (Textures)
         */
        if(obj.has("map_Kd"))
            map_Kd = ResourceManager.getTexture(obj.getString("map_Kd"));
        
    }

    public void bind() {
        Texture.bind(map_Kd);
        Shader.currentlyBound.setUniform("material.diffuseColor", kd);
    }
    
    public JSONObject getJSON() {
        JSONObject master = new JSONObject();
        
        // float[]
        master.put("kd", new JSONArray(Arrays.asList(kd.getData())));
        master.put("ka", new JSONArray(Arrays.asList(ka.getData())));
        master.put("ks", new JSONArray(Arrays.asList(ks.getData())));
        master.put("ke", new JSONArray(Arrays.asList(ke.getData())));
        
        // float
        master.put("d", d);
        master.put("ns", ns);
        
        // int
        master.put("illum", illum);
        
        // String
        master.put("map_Kd", map_Kd.path);
        master.put("map_Ks", ""); //TODO
        master.put("map_Ke", ""); //TODO
        master.put("map_Displ", ""); //TODO
        
        return master;
    }
}
