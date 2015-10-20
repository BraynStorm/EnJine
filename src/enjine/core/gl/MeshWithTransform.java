package enjine.core.gl;

public class MeshWithTransform implements Transformable3D {
	protected Mesh mesh;
	protected MeshTransform transform;
	
	/**
	 * New {@link MeshWithTransform} with the default {@link MeshTransform}.
	 * @param mesh - The mesh.
	 */
	public MeshWithTransform(Mesh mesh){
		this(mesh, new MeshTransform());
	}
	
	public MeshWithTransform(Mesh mesh, MeshTransform transform) {
		this.mesh = mesh;
		this.transform = transform;
	}

	public void render(){ render(false); }
	public void render(boolean pickingMode) {
		mesh.render(transform, pickingMode);
	}
	
	@Override public MeshTransform getTransform(){ return transform; }
	@Override public void transformationOccured() {}
}
