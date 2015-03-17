/*
 * soundtouch c++ library.
 */

#include <jni.h>
/* Header for class ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch */
#ifndef _Included_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch
#define _Included_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch

#include "SoundTouch/SoundTouch.h"
#define BUFFER_SIZE 4096

soundtouch::SoundTouch mSoundTouch;


/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    setSampleRate
 * Signature: (I)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setSampleRate
  (JNIEnv *env, jobject obj, jint sampleRate)
{
	mSoundTouch.setSampleRate(sampleRate);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    setChannels
 * Signature: (I)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setChannels
  (JNIEnv *env, jobject obj, jint channel)
{
	mSoundTouch.setChannels(channel);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    setTempoChange
 * Signature: (F)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setTempoChange
  (JNIEnv *env, jobject obj, jfloat newTempo)
{
	mSoundTouch.setTempoChange(newTempo);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    setPitchSemiTones
 * Signature: (I)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setPitchSemiTones
  (JNIEnv *env, jobject obj, jint pitch)
{
	mSoundTouch.setPitchSemiTones(pitch);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    setRateChange
 * Signature: (F)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_setRateChange
  (JNIEnv *env, jobject obj, jfloat newRate)
{
	mSoundTouch.setRateChange(newRate);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    putSamples
 * Signature: ([SI)V
 */
extern "C" JNIEXPORT void JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_putSamples
  (JNIEnv *env, jobject obj, jshortArray samples, jint len)
{
	jshort *input_samples = env->GetShortArrayElements(samples, NULL);
	mSoundTouch.putSamples(input_samples, len);
	env->ReleaseShortArrayElements(samples, input_samples, 0);
}

/*
 * Class:     ntou_cs_lab505_hearingaid_sound_frequenceshift
 * Method:    receiveSamples
 * Signature: ([SI)I
 */
extern "C" JNIEXPORT jshortArray JNICALL Java_ntou_cs_lab505_hearingaid_sound_frequenceshift_JNISoundTouch_receiveSamples
  (JNIEnv *env, jobject obj)
{
	short buffer[BUFFER_SIZE];
	int nSamples = mSoundTouch.receiveSamples(buffer, BUFFER_SIZE);

	jshortArray receiveSamples = env->NewShortArray(nSamples);
	env->SetShortArrayRegion(receiveSamples, 0, nSamples, buffer);

	return receiveSamples;
}

#endif


