package enjine.core.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;
import enjine.core.gl.Face;
import enjine.core.gl.FullBlownModel;
import enjine.core.gl.Material;
import enjine.core.gl.Mesh;
import enjine.core.gl.Texture;
import enjine.core.gl.particles.AnimatedParticle;
import enjine.core.gl.particles.PhysicsParticle;
import enjine.core.logging.Logger;
import enjine.core.logging.Logger.LogLevel;
import enjine.core.math.Vector2f;
import enjine.core.math.Vector3f;
import enjine.core.math.Vertex;
import enjine.core.utils.Common;

/**
 * TODO: Should change to {@link Hashtable} when we get to threading.
 * 
 * @author BraynStorm
 *        
 */
public class ResourceManager {
    
    /** Contains all textures that Enjine uses. */
    private static HashMap<String, Texture> textureMap = new HashMap<>();
    
    /** Contains all meshes that Enjine uses. */
    private static HashMap<String, Mesh> meshMap = new HashMap<>();
    
    /** Contains all models that Enjine uses. */
    private static HashMap<String, FullBlownModel> modelMap = new HashMap<>();
    
    /** Contains all particles that Enjine uses */
    private static HashMap<String, PhysicsParticle> physicsParticlesMap = new HashMap<>(); 
    private static HashMap<String, AnimatedParticle> animatedParticlesMap = new HashMap<>(); 
    
    /**
     * int verison of {@link ResourceManager#getTexture(String)}.
     * @param name The name of the texture
     * @return
     */
    public static Texture getTexture(int name){
        return getTexture(String.valueOf(name));
    }
    /**
     * Gets a texture form the 'repository' of the ResourceManager. If the texture hasn't been loaded, it will attempt to load it.
     * @param name
     * @return
     */
    public static Texture getTexture(String name){ 
        Texture t = textureMap.get(name);
        
        if(t == null){
            File potentialTextureFile = new File(Common.makeAbsoluteDataPath("textures/" + name + ".png"));
            if(!potentialTextureFile.exists() || potentialTextureFile.isDirectory()){
                Logger.getInstance().log(LogLevel.WARNING, "Texture \""+ name +"\" couldn't be found.");
            }else{
                try {
                    t = registerTexture(name, loadTexture(new FileInputStream(potentialTextureFile)));
                } catch (IOException e) {
                    Logger.getInstance().log(e);
                }
            }
        }
        
        return t;
    }
    
    public static Mesh getMesh(String name){
        Mesh mesh = meshMap.get(name);
        
        if(mesh == null){
            File potentialMeshFile1 = new File(Common.makeAbsoluteDataPath("meshes/" + name + ".mesh"));
            if(!potentialMeshFile1.exists() || potentialMeshFile1.isDirectory()){
                File potentialMeshFile2 = new File(Common.makeAbsoluteDataPath("meshes/" + name + ".obj"));
                if(!potentialMeshFile2.exists() || potentialMeshFile2.isDirectory()){
                    Logger.getInstance().log(LogLevel.WARNING, "Mesh \""+ name +"\" couldn't be found.");
                }else{
                    mesh = registerMesh(name, loadMesh("meshes/" + name + ".obj"));
                }
            }else{
                mesh = registerMesh(name, loadMesh("meshes/" + name + ".mesh"));
            }
        }
        
        return mesh;
    }
    
    public static AnimatedParticle getAnimatedParticle(String name){
        AnimatedParticle particle = animatedParticlesMap.get(name);
        
        if(particle == null){
            System.out.println("WTF");
        }
        
        return particle;
    }
    /**
     * Loades a new .obj file and converts it to a Map.
     * 
     * @param relObjPath
     *            The path to the .obj file.
     */
    private static Map<String, PreMesh> convertObj(String relObjPath) {
        final String absObjPath = Common.makeAbsoluteDataPath(relObjPath);
        
        List<String> listLines = null;
        
        try {
            listLines = Common.readAllLines(absObjPath);
        } catch (IOException e) {
            Common.killProgram(e);
        }
        
        return createPreMeshMap(listLines);
    }
    
    /**
     * Loades a new .mtl file and converts it to a Map.
     * 
     * @param relMtlPath
     *            The path to the .mtl file.
     */
    private static Map<String, Material> convertMtl(String relMtlPath) {
        final String absMtlPath = Common.makeAbsoluteDataPath(relMtlPath);
        List<String> listLines = null;
        
        try {
            listLines = Common.readAllLines(absMtlPath);
        } catch (IOException e) {
            Common.killProgram(e);
        }
        
        return createMaterialMap(listLines);
    }
    
    /**
     * Use this to acutally convert a obj-mtl pair into a .mesh file.
     * @param relObjPath
     */
    private static void mergeObjAndMtlIntoMesh(String relObjPath){
        Map<String, PreMesh> objs = convertObj(relObjPath);
        Map<String, Material> mtls = convertMtl(relObjPath.replace(".obj", ".mtl"));
        Map<String, JSONObject> meshes = new HashMap<>();
        
        objs.forEach((name, obj) ->{
            JSONObject meshObject = obj.getJSON();
            JSONObject materialObject;
            Material mtl = mtls.get(obj.materialName); //Is it true ?
            
            if(mtl != null)
                materialObject = mtl.getJSON();
            else
                materialObject = new JSONObject();
            
            meshObject.append(name, materialObject);
            meshes.put(name, meshObject);
        });
        
        meshes.forEach((k,v) -> {
            try {
                PrintWriter writer = new PrintWriter(Common.makeAbsoluteDataPath("meshes/" + k + ".mesh"));
                
                writer.write(v.toString());
                writer.write('\n');
                
                writer.close();
            } catch (FileNotFoundException e) {
                Logger.getInstance().log(e);
            }
        });
    }
    
    /**
     * Loades a new .model file.
     * 
     * @param objPath
     *            The path to the .obj
     * @param mtlPath
     *            The path to the .mtl
     * @return The name of the model.
     */
    private static String loadModel(String relModelPath) {
        relModelPath = Common.makeAbsoluteDataPath(relModelPath);
        
        if (!relModelPath.endsWith(".model")) {
            Common.killProgram("\"Model\" file extension has to be \".model\"");
        }
        
        Matcher matcher = Common.patternMatchFilename.matcher(relModelPath);
        matcher.find();
        
        /*
         * TODO loadModel(String).
         */
        
        
        return matcher.group(1);
    }
    
    private static Mesh loadMesh(String relMeshPath){
        
        if (relMeshPath.endsWith(".obj")){
            mergeObjAndMtlIntoMesh(relMeshPath);
        }
        
        Mesh mesh = null;
        String lines = "{}";
        
        try {
            lines = Common.readAllLinesToString(Common.makeAbsoluteDataPath(relMeshPath));
        } catch (IOException e) {
            Logger.getInstance().log(e);
            return mesh;
        }
        
        JSONObject meshObj = new JSONObject(lines);
        if(meshObj.has("positions") && meshObj.has("faces")){
            boolean hasNormals = meshObj.has("normals");
            boolean hasTexCoords = meshObj.has("texCoords");
            
            JSONArray normalsArray = new JSONArray();
            JSONArray texCoordsArray = new JSONArray();
            
            if (hasTexCoords) texCoordsArray = meshObj.getJSONArray("texCoords");
            if (hasNormals) normalsArray = meshObj.getJSONArray("normals");
            
            List<Vector3f> posList = Common.jsonArrayToVector3fList(meshObj.getJSONArray("positions"));
            List<Vector2f> texCoordsList = Common.jsonArrayToVector2fList(texCoordsArray);
            List<Vector3f> normalsList = Common.jsonArrayToVector3fList(normalsArray);
            
            List<Vertex> verticesList = new ArrayList<>(posList.size() + 1);
            
            // Merge the three lists into the Vertex List
            for(int i = 0; i < posList.size(); i++){
                Vector3f position = posList.get(i);
                Vector2f texCoord = hasTexCoords ? texCoordsList.get(i) : new Vector2f(1, 0);
                Vector3f normal = hasNormals ? normalsList.get(i) : new Vector3f(0, -1, 0);
                
                verticesList.add( new Vertex(position, texCoord, normal) );
            }
            
            List<Face> facesList = Common.jsonArrayToFaceList(meshObj.getJSONArray("faces"));
            Material material = new Material(meshObj.getJSONObject("material"));
            
            mesh = new Mesh(verticesList, facesList, material);
        }
        
        if(mesh == null)
            Logger.getInstance().log(Logger.LogLevel.WARNING, "Trying to load an invalid mesh file. Skipping");
        else{
            Matcher m = Common.patternMatchFilename.matcher(relMeshPath);
            registerMesh(m.group(1), mesh);
        }
        
        return mesh;
    }
    
    /**
     * 
     * Note: JPD - Json Particle Description.
     * @param relJpdPath
     * @return
     */
    private static void loadParticles(){
        String fileData = "{}";
        
        try {
             fileData = Common.readAllLinesToString(Common.makeAbsoluteDataPath("particles.jpd"));
        } catch (IOException e) {
            Common.killProgram(e);
        }
        
        JSONObject allParticles = new JSONObject(fileData);
        
        allParticles.keySet().forEach(k -> {
            String type = "generic";
            JSONObject particleObj = allParticles.getJSONObject(k);
            
            if(particleObj.has("type"))
                type = particleObj.getString("type");
            switch(type){
                case "generic":
                    
                    break;
                case "animated":
                    AnimatedParticle animParticle = new AnimatedParticle(getTexture(particleObj.getString("texture")), particleObj.getInt("frameCount"));
                    animParticle.setIsAffectedByGravity(particleObj.getBoolean("affectedByGravity"));
                    animatedParticlesMap.put(k, animParticle);
                    break;
            }
            
        });
        
    }
    
    private static Texture loadTexture(InputStream stream) throws IOException {
        return loadTexture(stream, false, true, GL11.GL_LINEAR);
    }
    private static Texture loadTexture(String relTexturePath) throws IOException {
        return loadTexture(relTexturePath, true);
    }
    private static Texture loadTexture(String relTexturePath, boolean repeat) throws IOException {
        return loadTexture(relTexturePath, repeat, GL11.GL_LINEAR);
    }
    private static Texture loadTexture(String relTexturePath, int filter) throws IOException {
        return loadTexture(relTexturePath, true, filter);
    }
    private static Texture loadTexture(String relTexturePath, boolean repeat, int filter) throws IOException {
        return loadTexture(new FileInputStream(Common.makeAbsoluteDataPath(relTexturePath)), false, repeat, filter);
    }
    private static Texture loadTexture(InputStream stream, boolean flipped, boolean repeat, int filter)
            throws IOException {
        PNGDecoder decoder = new PNGDecoder(stream);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        
        decoder.decode(buffer, decoder.getWidth() * 4, Format.RGBA);
        buffer.flip();
        
        int textureID = GL11.glGenTextures();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, repeat ? GL11.GL_REPEAT : GL11.GL_CLAMP);
        
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        
        Texture t = new Texture(textureID, decoder.getWidth(), decoder.getHeight(), Format.RGBA);
        
        return t;
    }
    
    private static Texture registerTexture(String name, Texture texture){
        if (textureMap.containsKey(name)) {
            return textureMap.get(name);
        }
        
        if(textureMap.containsValue(texture)){
            Logger.getInstance().log(LogLevel.WARNING, "Texture with name \"" + name + "\" has already been loaded under a different name.");
            return texture;
        }
        
        textureMap.put(name, texture);
        return texture;
    }
    
    private static Mesh registerMesh(String name, Mesh mesh){
        if(meshMap.containsKey(name)){
            return meshMap.get(name);
        }
        
        if(meshMap.containsValue(mesh)){
            Logger.getInstance().log(LogLevel.WARNING, "Mesh with name \"" + name + "\" has already been loaded under a different name.");
            return mesh;
        }
        
        meshMap.put(name, mesh);
        return mesh;
    }
    
    /**
     * Creates a fake {@link Texture} object, containing only the 'path'.
     * 
     * @param relTexturePath
     *            Path to the png file, relative to the data folder.
     * @return A fake Texture object.
     */
    private static Texture loadFakeTexture(String relTexturePath) {
        Texture t = new Texture(0, 0, 0, PNGDecoder.Format.RGBA);
        t.path = relTexturePath;
        return t;
    }
    
    /**
     * Create a {@link Map} that contains all the Materials in the {@link List}.
     * 
     * @param data
     * @return
     */
    private static Map<String, Material> createMaterialMap(List<String> data) {
        
        final Map<String, Material> map = new HashMap<String, Material>();
        Material currentMaterial = null;
        String currentMaterialName = "";
        
        for (String line : data) {
            String[] parts = line.split("  *");
            Matcher matcher;
            switch (parts[0].toLowerCase()) {
                case "newmtl":
                    if (currentMaterial != null)
                        map.put(currentMaterialName, currentMaterial);
                        
                    currentMaterial = new Material();
                    currentMaterialName = parts[1];
                    break;
                case "kd":
                    currentMaterial.kd = new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3]));
                    break;
                case "ka":
                    currentMaterial.ka = new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3]));
                    break;
                case "ks":
                    currentMaterial.ks = (new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    break;
                case "ke":
                    currentMaterial.ke = (new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    break;
                case "d":
                    currentMaterial.d = (Float.parseFloat(parts[1]));
                    break;
                case "ns":
                    currentMaterial.ns = (Float.parseFloat(parts[1]));
                    break;
                case "illum":
                    currentMaterial.illum = (Integer.parseInt(parts[1]));
                    break;
                case "map_kd":
                    matcher = Common.patternMatchFilename.matcher(parts[1]);
                    matcher.find();
                    
                    currentMaterial.map_Kd = loadFakeTexture(matcher.group(1));
                    break;
                case "map_ka":
                    matcher = Common.patternMatchFilename.matcher(parts[1]);
                    matcher.find();
                    
                    currentMaterial.map_Ka = Integer.valueOf(matcher.group(1));
                    break;
                case "map_ks":
                    matcher = Common.patternMatchFilename.matcher(parts[1]);
                    matcher.find();
                    
                    currentMaterial.map_Ks = Integer.valueOf(matcher.group(1));
                    break;
                case "map_ke":
                    matcher = Common.patternMatchFilename.matcher(parts[1]);
                    matcher.find();
                    
                    currentMaterial.map_Ke = Integer.valueOf(matcher.group(1));
                    break;
                case "disp":
                    matcher = Common.patternMatchFilename.matcher(parts[1]);
                    matcher.find();
                    
                    currentMaterial.map_Displ = Integer.valueOf(matcher.group(1));
                    break;
            }
        }
        
        map.put(currentMaterialName, currentMaterial);
        
        return map;
    }
    
    /**
     * Creates a {@link Map} containing all data form the provided {@link List}.
     * 
     * @param data
     * @return
     */
    private static Map<String, PreMesh> createPreMeshMap(List<String> data) {
        if (data == null)
            Common.killProgram("OBJ file is empty.");
            
        Map<String, PreMesh> meshes = new HashMap<String, PreMesh>();
        
        PreMesh currentMesh = null;
        String currentMeshName = "";
        
        boolean hasTexCoords = false;
        
        // Oh Java.. Y U NO HAVE CLOSURES?!
        for (String line : data) {
            String[] parts = line.split("  *");
            switch (parts[0]) {
                case "#":
                    break;
                case "o":
                    if (currentMesh != null)
                        meshes.put(currentMeshName, currentMesh);
                        
                    currentMesh = new PreMesh();
                    currentMeshName = parts[1];
                    break;
                case "mtllib":
                    break;
                case "v":
                    currentMesh.positions.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    break;
                case "vt":
                    currentMesh.texCoords.add(new Vector2f(Float.parseFloat(parts[1]), 1 - Float.parseFloat(parts[2])));
                    break;
                case "vn":
                    currentMesh.normals.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]),
                            Float.parseFloat(parts[3])));
                    hasTexCoords = true;
                    break;
                case "f":
                    int[] indices = new int[3];
                    int I = 0;
                    for (int i = 1; i < parts.length; i++) {
                        String[] p = parts[i].split("/");
                        
                        int pos = Integer.parseInt(p[0]) - 1;
                        int tex = -1;
                        int norm = Integer.parseInt(p[1]) - 1;
                        
                        // Texture
                        if (hasTexCoords) {
                            tex = Integer.parseInt(p[1]) - 1;
                            norm = Integer.parseInt(p[2]) - 1;
                        }
                        
                        Vector3f posVector = currentMesh.positions.get(pos);
                        Vector2f texVector = hasTexCoords ? currentMesh.texCoords.get(tex) : null;
                        Vector3f normVector = currentMesh.normals.get(norm);
                        
                        Vertex potentialVertex = new Vertex(posVector, texVector, normVector);
                        
                        boolean found = false;
                        
                        for (int j = 0; j < currentMesh.vertices.size(); j++) {
                            if (currentMesh.vertices.get(j).equals(potentialVertex)) {
                                indices[I] = j;
                                I++;
                                found = true;
                                break;
                            }
                        }
                        
                        if (!found) {
                            indices[I] = currentMesh.vertices.size();
                            I++;
                            currentMesh.vertices.add(potentialVertex);
                        }
                    }
                    
                    currentMesh.faces.add(new Face(indices));
                    
                    break;
            }
        }
        
        meshes.put(currentMeshName, currentMesh);
        return meshes;
    }
    
    private static class PreMesh {
        // Temps
        ArrayList<Vector3f> positions;
        ArrayList<Vector2f> texCoords;
        ArrayList<Vector3f> normals;
        
        ArrayList<Face> faces;
        ArrayList<Vertex> vertices;
        
        public String materialName;
        
        public PreMesh() {
            this.positions = new ArrayList<Vector3f>();
            this.texCoords = new ArrayList<Vector2f>();
            this.normals = new ArrayList<Vector3f>();
            this.faces = new ArrayList<Face>();
            this.vertices = new ArrayList<Vertex>();
        }
        
        /**
         * Creates a {@link JSONObject} containing all PreMesh data.
         * 
         * @return
         */
        public JSONObject getJSON() {
            JSONObject masterObj = new JSONObject();
            JSONArray positionArray = new JSONArray();
            JSONArray texCoordArray = new JSONArray();
            JSONArray normalArray = new JSONArray();
            JSONArray faceArray = new JSONArray();
            
            FloatBuffer vBuffer = Vertex.bufferfy(vertices);
            
            while (vBuffer.hasRemaining()) {
                positionArray.put(vBuffer.get());
                positionArray.put(vBuffer.get());
                positionArray.put(vBuffer.get());
                
                texCoordArray.put(vBuffer.get());
                texCoordArray.put(vBuffer.get());
                
                normalArray.put(vBuffer.get());
                normalArray.put(vBuffer.get());
                normalArray.put(vBuffer.get());
            }
            
            IntBuffer faceBuffer = Face.meshify(faces);
            
            while (faceBuffer.hasRemaining())
                faceArray.put(faceBuffer.get());
                
            masterObj.put("positions", positionArray);
            masterObj.put("texCoords", texCoordArray);
            masterObj.put("normals", normalArray);
            masterObj.put("faces", faceArray);
            
            masterObj.append("bones", new JSONArray());
            masterObj.append("weights", new JSONArray());
            masterObj.append("poses", new JSONArray());
            
            return masterObj;
        }
        
        
    }
    
    private static boolean I = false;
    
    public static void initializeParticles() {
        if(!I)
            loadParticles();
        I = true;
    }
}
