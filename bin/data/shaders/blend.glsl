vec4 blend(vec4 f, vec4 b){
  return vec4(
    (f.xyz * f.w) + (b.xyz * b.w * (1 - f.w)),
    f.w + b.w *(1 - f.w)
  );
}