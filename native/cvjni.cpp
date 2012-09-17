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
  vector<char> imageSourceV;
  for (int i = 0; i < len; i++) {
    imageSourceV.push_back(imageSource[i]);
  }
  // convert image binary to Mat
  Mat image = imdecode(imageSourceV, CV_LOAD_IMAGE_COLOR);

  // operate the Mat
  vector<unsigned char> imageDesV;
  imencode(".bmp", image, imageDesV); // encode the Mat to bmp

  // store result to a new jbyteArray
  jbyte* result_e = new jbyte[imageDesV.size()];
  for (int i = 0; i < imageDesV.size(); i++) {
    result_e[i] = (jbyte)imageDesV[i];
  }
  jbyteArray result = env->NewByteArray(imageDesV.size());
  env->SetByteArrayRegion(result, 0, imageDesV.size(), result_e);
  return result;
}
