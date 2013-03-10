#include "bytesNativeEvalFunc.h"
#include <vector>
#include <iostream>
#include <dlfcn.h>

using namespace std;

/*
 * Class:     CVJNI
 * Method:    Invoked in jni
 * Signature: ([B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_me_sheimi_pig_eval_BytesNativeEvalFunc_invokeNative
(JNIEnv * env, jclass jc, jstring dlpath, jstring funcname, jbyteArray jba) {
  cout << "This is in JNI" << endl;
  jboolean isCopy;
  // get the imageSource
  jbyte* jbae = env->GetByteArrayElements(jba, &isCopy);
  jsize len = env->GetArrayLength(jba);
  char * imageSource = (char *)jbae;
  vector<unsigned char> imageSourceV;
  imageSourceV.resize(len);
  copy(imageSource, imageSource + len, imageSourceV.begin());
  if (isCopy == JNI_TRUE) {
    env->ReleaseByteArrayElements(jba, jbae, 0);
  }

  const char * dlpath_native= env->GetStringUTFChars(dlpath, &isCopy);
  void* handle = dlopen(dlpath_native, RTLD_LAZY);
  if (isCopy == JNI_TRUE) {
    env->ReleaseStringUTFChars(dlpath, dlpath_native);
  }

  typedef void (*cvcv_t)(vector<unsigned char>* byte);
  cout << "handle loaded" << endl;

  const char * funcname_native = env->GetStringUTFChars(funcname, &isCopy);
  dlerror();
  cvcv_t cvcv = (cvcv_t) dlsym(handle, funcname_native);
  const char *dlsym_error = dlerror();
  if (dlsym_error) {
      cerr << "Cannot load symbol 'hello': " << dlsym_error <<
      '\n';
    dlclose(handle);
    return jba;
  }
  if (isCopy == JNI_TRUE) {
    env->ReleaseStringUTFChars(funcname, funcname_native);
  }

  cout << "function loaded" << endl;
  cvcv(&imageSourceV);
  dlclose(handle);
  
  jbyte* result_e = reinterpret_cast<jbyte*>(&imageSourceV[0]);
  jbyteArray result = env->NewByteArray(imageSourceV.size());
  env->SetByteArrayRegion(result, 0, imageSourceV.size(), result_e);

  return result;
}
