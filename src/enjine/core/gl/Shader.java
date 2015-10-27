package enjine.core.gl;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import enjine.core.logging.Logger;
import enjine.core.math.Matrix4f;
import enjine.core.math.Vector2f;
import enjine.core.math.Vector3f;
import enjine.core.math.Vector4f;
import enjine.core.utils.Common;

public class Shader {
	
	private String shadersFolder = Common.dataFolder + "shaders/";
	
	private HashMap<String, Integer> uniforms;
	private int programID = 0;
	private int shaders[];
	
	public static Shader currentlyBound = null;
	
	public Shader() {
		shaders = new int[2];
		uniforms = new HashMap<String, Integer>();
		programID = glCreateProgram();
	}
	
	public boolean loadShader(String name){
		loadShader(name + ".fs", GL_FRAGMENT_SHADER);
		boolean res = loadShader(name + ".vs", GL_VERTEX_SHADER);
		compileProgram();
		return res;
	}
	
	/**
	 * @param name Shader name (filename without .glsl)
	 * @param type GL_VERTEX_SHADER | GL_FRAGMENT_SHADER
	 * @return if the shader has loaded and 
	 */
	public boolean loadShader(String name, int type){
		String shaderCode = "";
		int shader = 0;
		
		if(type == GL_VERTEX_SHADER)
			shader = shaders[0];
		
		if(type == GL_FRAGMENT_SHADER)
			shader = shaders[1];
		
		try {
			Scanner s = new Scanner(new File(shadersFolder + name + ".glsl"));
			
			while(s.hasNextLine())
				shaderCode += s.nextLine() + "\n";
			
			s.close();
			
			if(!glIsShader(shader)){
				shader = glCreateShader(type);
			}
			
			glShaderSource(shader, shaderCode);
			glCompileShader(shader);
			
			if(checkError(shader, GL_COMPILE_STATUS, false, "[ERROR] Shader comilation failed: ")){
				
				glBindAttribLocation(programID, 0, "position");
				glBindAttribLocation(programID, 1, "texCoord");
				glBindAttribLocation(programID, 2, "normal");
				
				glAttachShader(programID, shader);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void setUniformi(String uniform, int value){
		glUniform1i(uniforms.get(uniform), value);
	}
	
	public void setUniformf(String uniform, float value){
		glUniform1f(uniforms.get(uniform), value);
	}
	
	public void setUniform(String uniform, Vector2f value){
		glUniform2f(uniforms.get(uniform), value.x, value.y);
	}
	
	public void setUniform(String uniform, Vector3f value){
		glUniform3f(uniforms.get(uniform), value.x, value.y, value.z);
	}
	
	public void setUniform(String uniform, Vector4f value){
		glUniform4f(uniforms.get(uniform), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniform, Matrix4f value){
		glUniformMatrix4fv(uniforms.get(uniform), true,
				Common.createBuffer(value.getData()));
	}
	
	
	public void addUniform(String uniform){
		int handle = glGetUniformLocation(programID, uniform);
		
		if(handle == -1){ // a.k.a invalid
			Logger.getInstance().log(new Exception("[ERROR] Couldn't find uniform " + uniform));
		}else{
			uniforms.put(uniform, handle);
		}
	} 
	
	private boolean checkError(int handler, int flag, boolean isProgram, String message){
		int success = 0;
		
		ByteBuffer sucBuf = BufferUtils.createByteBuffer(4);
		
		if(isProgram){
			glGetProgramiv(handler, flag, sucBuf);
		}else{
			glGetShaderiv(handler, flag, sucBuf);
		}
		
		success = sucBuf.get();
		if(success == GL_FALSE){
			if(isProgram){
				System.out.println(message + glGetProgramInfoLog(handler));
			}else{
				System.out.println(message + glGetShaderInfoLog(handler));
			}
			return false;
		}
		return true;
	}
	
	public void compileProgram(){
		glLinkProgram(programID);
		if(checkError(programID, GL_LINK_STATUS, true, "[ERROR] Shader program linking failed: ")){
			glValidateProgram(programID);
			checkError(programID, GL_VALIDATE_STATUS, true, "[ERROR] Shader program validation failed: ");
		}
		
	}
	
	public void bind(){
		Texture.unbind();
		glUseProgram(programID);
		currentlyBound = this;
	}
	
	public static void unbind(){
		glUseProgram(0);
		currentlyBound = null;
	}
	
	public void delete(){
		//for(int i = 0; i < shaders.length; i++)
		//	glDetachShader(programID, shaders[i]);
		glDeleteProgram(programID);
	}
}
