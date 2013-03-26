#include "bytesStringNativeEvalFunc.h"
#include <vector>
#include <string>
#include <map>
#include <iostream>
#include <dlfcn.h>

using namespace std;

typedef void (*cv_byte_str_proxy_t)(vector<unsigned char>* in, string* out);

/*
 * Class:     CVJNI
 * Method:    Invoked in jni
 * Signature: ([B)[B
 */

JNIEXPORT jstring JNICALL Java_me_sheimi_pig_eval_BytesStringNativeEvalFunc_invokeNative(
        JNIEnv *env, jclass jc, jstring dlpath, jstring handler_name,
        jbyteArray jba) {

    cout << "This is in JNI" << endl;
    // help variable to convert jvm type to local type
    jboolean is_copy;

    // get the multimedia source (byte array)
    jbyte *media_src_raw = env->GetByteArrayElements(jba, &is_copy);
    // the lenth of multimedia byte array
    jsize len_of_media_src = env->GetArrayLength(jba);

    vector<unsigned char> media_src_input;
    media_src_input.resize(len_of_media_src);
    copy((char *) media_src_raw, (char *) media_src_raw + len_of_media_src,
            media_src_input.begin());
    if (is_copy == JNI_TRUE) {
        env->ReleaseByteArrayElements(jba, media_src_raw, 0);
    }

    // load the native dynamic library path
    const char *dlpath_native = env->GetStringUTFChars(dlpath, &is_copy);
    void *dllib = dlopen(dlpath_native, RTLD_LAZY);
    if (is_copy == JNI_TRUE) {
        env->ReleaseStringUTFChars(dlpath, dlpath_native);
    }

    cout << "handle loaded" << endl;

    // get the native handler from dl library
    const char *handler_name_native = env->GetStringUTFChars(handler_name,
            &is_copy);
    dlerror();
    cv_byte_str_proxy_t cv_proxy = (cv_byte_str_proxy_t) dlsym(dllib,
            handler_name_native);
    const char *dlsym_error = dlerror();
    if (dlsym_error) {
        cerr << "Cannot load symbol" << handler_name_native << ": "
                << dlsym_error << endl;
        dlclose(dllib);
        return NULL;
    }
    if (is_copy == JNI_TRUE) {
        env->ReleaseStringUTFChars(handler_name, handler_name_native);
    }
    cout << "function loaded" << endl;

    string output;
    cv_proxy(&media_src_input, &output);
    dlclose(dllib);

    jstring result = env->NewStringUTF(output.c_str());
    return result;
}
