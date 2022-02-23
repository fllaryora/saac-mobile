package phi.saac.comunicame.ui.speech

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.Engine
import android.util.Log
import java.util.*
import android.speech.tts.Voice
import android.os.Bundle
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import java.lang.Exception
import java.lang.NullPointerException

object TTSHelper {
    private val TAG = "TTS"

    fun setSpeechRate(rate: Float, mTTS: TextToSpeech) {
        mTTS.setSpeechRate(rate)
    }

    fun isLanguageAvailable(locale: Locale, mTTS: TextToSpeech): Boolean {
        return mTTS.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE
    }
    fun isLanguageInstalled(language: String, mTTS: TextToSpeech): Boolean {
        val locale = Locale.forLanguageTag(language)
        if (isLanguageAvailable(locale, mTTS)) {
            var voiceToCheck: Voice? = null
            for (v in mTTS.voices) {
                if (v.locale == locale && !v.isNetworkConnectionRequired) {
                    voiceToCheck = v
                    break
                }
            }
            if (voiceToCheck != null) {
                val features = voiceToCheck.features
                return (features != null
                        && !features.contains(Engine.KEY_FEATURE_NOT_INSTALLED))
            }
        }
        return false
    }

    fun areLanguagesInstalled(languages: List<String>, mTTS: TextToSpeech): Map<String, Boolean> {
        val result: MutableMap<String, Boolean> = HashMap()
        for (language in languages) {
            result[language] = isLanguageInstalled(language, mTTS)
        }
        return result
    }

    fun setEngine(mContext: Context, engine: String, onInitListener: TextToSpeech.OnInitListener): TextToSpeech{
        return TextToSpeech(mContext, onInitListener, engine)
    }

    fun setLanguage(language: String, mTTS: TextToSpeech): Boolean {
        val locale = Locale.forLanguageTag(language)
        if(isLanguageAvailable(locale, mTTS)) {
            val result = mTTS.setLanguage(locale)
            if( (TextToSpeech.LANG_MISSING_DATA == result) or
                (TextToSpeech.LANG_NOT_SUPPORTED == result) ){
                Log.e(TAG, "Language not supported.")
                return false
            }
            return true
        }
        return false
    }

    fun setVoice(voice: HashMap<String, String>, mTTS: TextToSpeech):Boolean {
        for (ttsVoice in mTTS.voices) {
            if (ttsVoice.name == voice["name"] && ttsVoice.locale.toLanguageTag() == voice["locale"]) {
                mTTS.voice = ttsVoice
                return true
            }
        }
        Log.d(TAG, "Voice name not found: $voice")
        return false
    }

    fun setVolume(volume: Float, bundle: Bundle):Boolean {
        return if (volume >= 0.0f && volume <= 1.0f) {
            bundle.putFloat(Engine.KEY_PARAM_VOLUME, volume)
            true
        } else {
            Log.d(TAG, "Invalid volume $volume value - Range is from 0.0 to 1.0")
            false
        }
    }

    fun setPitch(pitch: Float, mTTS: TextToSpeech):Boolean {
        if (pitch >= 0.5f && pitch <= 2.0f) {
            mTTS.setPitch(pitch)
            return true
        } else {
            Log.d(TAG, "Invalid pitch $pitch value - Range is from 0.5 to 2.0")
            return false
        }
    }

    fun getVoices(mTTS: TextToSpeech) : ArrayList<HashMap<String, String>> {
        val voices: ArrayList<HashMap<String, String>> = ArrayList()
        return try {
            for (voice in mTTS.voices) {
                val voiceMap: HashMap<String, String> = HashMap()
                voiceMap["name"] = voice.name
                voiceMap["locale"] = voice.locale.toLanguageTag()
                voices.add(voiceMap)
            }
            voices
        } catch (e: NullPointerException) {
            Log.d(TAG, "getVoices: " + e.message)
            voices
        }
    }

    fun getLanguages(mTTS: TextToSpeech) : ArrayList<String>{
        val locales: ArrayList<String> = ArrayList()
        try {
            if (VERSION.SDK_INT >= VERSION_CODES.M) {
                // While this method was introduced in API level 21, it seems that it
                // has not been implemented in the speech service side until API Level 23.
                for (locale in mTTS.availableLanguages) {
                    locales.add(locale.toLanguageTag())
                }
            } else {
                for (locale in Locale.getAvailableLocales()) {
                    if (locale.variant.isEmpty() && isLanguageAvailable(locale, mTTS)) {
                        locales.add(locale.toLanguageTag())
                    }
                }
            }
        } catch (e: MissingResourceException) {
            Log.d(TAG, "getLanguages: " + e.message)
        } catch (e: NullPointerException) {
            Log.d(TAG, "getLanguages: " + e.message)
        }
       return locales
    }

    fun getEngines(mTTS: TextToSpeech): ArrayList<String> {
        val engines: ArrayList<String> = ArrayList()
        try {
            for (engineInfo in mTTS.engines) {
                engines.add(engineInfo.name)
            }
        } catch (e: Exception) {
            Log.d(TAG, "getEngines: " + e.message)
        }
        return engines
    }

    fun getDefaultEngine(mTTS: TextToSpeech): String {
        return  mTTS.defaultEngine
    }

    fun getSpeechRateValidRange() : HashMap<String, String>{
        // Valid values available in the android documentation.
        // https://developer.android.com/reference/android/speech/tts/TextToSpeech#setSpeechRate(float)
        val data = HashMap<String, String>()
        data["min"] = "0"
        data["normal"] = "0.5"
        data["max"] = "1.5"
        data["platform"] = "android"
        return data
    }

    private fun getMaxSpeechInputLength(): Int {
        return TextToSpeech.getMaxSpeechInputLength()
    }


}