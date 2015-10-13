package enjine.core.resources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import enjine.core.gl.particles.Particle;
import enjine.core.utils.Common;

/**
 * DEPRECATED. Use {@link ResourceManager}.
 * @author BraynStorm
 *
 */
@Deprecated
public class ParticleLoader {
	public static final String path = Common.dataFolder;
	
	public static HashMap<Integer, Particle> particles = new HashMap<Integer, Particle>();
	
	public static void loadParticles(){
		try{
			Scanner s = new Scanner(new File(Common.dataFolder + "particles.json"));
			StringBuilder data = new StringBuilder();
			
			while(s.hasNext())
				data.append(s.nextLine());
			
			JSONArray particleList = new JSONArray(data.toString());
			
			for(int i = 0; i < particleList.length(); i++){
				loadParticle(i, particleList.getJSONObject(i));
			}
			
			s.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	private static void loadParticle(int asID, JSONObject particle){
		JSONArray scaleArray = particle.getJSONArray("scale");
		JSONArray colorArray = particle.getJSONArray("defaultColor");
		/*particles.put(asID,
				new Particle(
						asID,
						particle.getInt("origin"),
						(float)particle.getDouble("densityMultiplier"),
						new Vector3f(
								(float)scaleArray.getDouble(0),
								(float)scaleArray.getDouble(1),
								(float)scaleArray.getDouble(2)
						),
						new Vector3f(
								(float)colorArray.getDouble(0),
								(float)colorArray.getDouble(1),
								(float)colorArray.getDouble(2)
								//(float)colorArray.getDouble(3)
						)
				)
		);*/
	}
	
	public static Particle getParticle(int id){
		return particles.get(id);
	}
	
}
