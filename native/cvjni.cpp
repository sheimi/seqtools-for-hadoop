#include "bytesNativeEvalFunc.h"
#include <opencv2/opencv.hpp>
#include <vector>
#include <iostream>

using namespace std;
using namespace cv;

/*
 * Class:     CVJNI
 * Method:    Invoked in jni
 * Signature: ([B)[B
 */
JNIEXPORT jbyteArray JNICALL Java_me_sheimi_pig_eval_BytesNativeEvalFunc_invokeNative
(JNIEnv * env, jclass jc, jbyteArray jba) {
  cout << "This is in JNI" << endl;
  unsigned char * isCopy;
  // get the imageSource
  jbyte* jbae = env->GetByteArrayElements(jba, isCopy);
  jsize len = env->GetArrayLength(jba);
  char * imageSource = (char *)jbae;
  vector<unsigned char> imageSourceV;
  imageSourceV.resize(len);
  copy(imageSource, imageSource + len, imageSourceV.begin());
  // convert image binary to Mat
  Mat image = imdecode(imageSourceV, CV_LOAD_IMAGE_COLOR);

  imageSourceV.clear();
  imencode(".bmp", image, imageSourceV); // encode the Mat to bmp
  cout << imageSourceV.size() << endl;
  int size = imageSourceV.size();
  jbyte* result_e = reinterpret_cast<jbyte*>(&imageSourceV[0]);
  jbyteArray result = env->NewByteArray(imageSourceV.size());
  env->SetByteArrayRegion(result, 0, imageSourceV.size(), result_e);
  env->ReleaseByteArrayElements(jba, jbae, 0);
  return result;
}
