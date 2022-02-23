package phi.saac.comunicame.ui.speech
import android.content.Context
import android.speech.tts.TextToSpeech

import android.os.Bundle
import android.speech.tts.Voice
import android.util.Log
import java.util.*
import java.lang.IllegalArgumentException
import java.lang.reflect.Field


class TextToSpeechService(val mContext: Context):MyUtteranceProgressListener.Listener {
    private val TAG = "TTS"
    private val googleTtsEngine = "com.google.android.tts"
    //Set silence// durationInMs
    private var silencems: Long =0L
    private var mBundle: Bundle
    private lateinit var mTTS: TextToSpeech
    private var queueMode = TextToSpeech.QUEUE_FLUSH
    var speaking:Boolean = false
    var synth:Boolean = false

    var awaitSynthCompletion:Boolean = false
    var awaitSpeakCompletion:Boolean = false
    var isTtsInitialized:Boolean = false

    private val utteranceProgressListener = MyUtteranceProgressListener(
        observer = this,
        queueMode = queueMode,
        speaking = speaking,
        synth = synth,
        awaitSynthCompletion = awaitSynthCompletion,
        awaitSpeakCompletion= awaitSpeakCompletion
    )

    private val onInitListener = TextToSpeech.OnInitListener { status: Int ->
        //onInit
        if (TextToSpeech.SUCCESS == status) {
            mTTS.setOnUtteranceProgressListener(utteranceProgressListener)
            try {
                val locale = mTTS.defaultVoice.locale
                if(TTSHelper.isLanguageAvailable(locale, mTTS)){
                    val result = mTTS.setLanguage(locale)
                    // result = mTTS.setLanguage(Locale("es","ES"))
                    if( (TextToSpeech.LANG_MISSING_DATA == result) or
                        (TextToSpeech.LANG_NOT_SUPPORTED == result) ){
                        Log.e(TAG, "Language not supported.")
                    }
                }
            } catch (exception: Exception){
                Log.e(TAG, "getDefaultLocale: " + exception.message)
            }
        } else {
            Log.e(TAG, "Failed to initialize TextToSpeech")
        }
    }

    init {
        mBundle =  Bundle()
        mTTS =  TextToSpeech(mContext, onInitListener, googleTtsEngine)
    }

    fun onPause(){
        mTTS.stop()
    }
    fun onDetach(){
        mTTS.shutdown()
    }

    fun setHandsomeBritish(shouldBeBritish: Boolean) {
        if (!shouldBeBritish) {
            mTTS.voice = mTTS.defaultVoice
            mTTS.setSpeechRate(2.7f)
            return
        }
        val britishVoices = mutableListOf<Voice>()
        var britishVoice: Voice? = null
        val voices = mTTS.voices
        for (voice in voices) {
            if (voice.name == "en-GB-language") {
                britishVoice = voice
                continue
            }
            if (voice.locale == Locale.UK && !voice.isNetworkConnectionRequired &&
                voice.quality >= Voice.QUALITY_VERY_HIGH) {
                britishVoices.add(voice)
            }
        }
        if (britishVoice != null) {
            mTTS.voice = britishVoice
            mTTS.setSpeechRate(1.0f)
        } else {
            if (britishVoices.size != 0) {
                mTTS.voice = britishVoices[0] // Just set any old British voiceo
                mTTS.setSpeechRate(1.0f)
            }
        }
    }

    fun setHandsomeSpanish() {
        val locale = Locale("es","ES")
        if(TTSHelper.isLanguageAvailable(locale, mTTS)){
            val result = mTTS.setLanguage(locale)
            if( (TextToSpeech.LANG_MISSING_DATA == result) or
                (TextToSpeech.LANG_NOT_SUPPORTED == result) ){
                Log.e(TAG, "Language not supported.")
            }
        }

    }

    fun speak(text: String) : Boolean{
        val uuid = UUID.randomUUID().toString()
        utteranceProgressListener.utterances.put(uuid, text)
        return if (ismServiceConnectionUsable(mTTS)) {
            if (silencems > 0L) {
                mTTS.playSilentUtterance(
                    silencems,
                    TextToSpeech.QUEUE_FLUSH,
                    SILENCE_PREFIX.toString() + uuid
                )
                TextToSpeech.SUCCESS ==  mTTS.speak(text, TextToSpeech.QUEUE_ADD, mBundle, uuid)
            } else {
                TextToSpeech.SUCCESS == mTTS.speak(text, queueMode, mBundle, uuid)
            }
        } else {
            isTtsInitialized = false
            mTTS = TextToSpeech(mContext, onInitListener, googleTtsEngine)
            false
        }
    }

    override fun invokeMethod(text: String, condition: Any) {
        //TODO("Not yet implemented")
    }

    override fun speakResult(condition: Int) {
        //TODO("Not yet implemented")
    }

    override fun synthResult(condition: Int) {
       // TODO("Not yet implemented")
    }
    private fun ismServiceConnectionUsable(tts: TextToSpeech?): Boolean {
        var isBindConnection = true
        if (tts == null) {
            return false
        }
        val fields: Array<Field> = tts.javaClass.declaredFields
        for (j in fields.indices) {
            fields[j].setAccessible(true)
            if ("mServiceConnection" == fields[j].getName() &&
                "android.speech.tts.TextToSpeech\$Connection" == fields[j].getType().getName()
            ) {
                try {
                    if (fields[j].get(tts) == null) {
                        isBindConnection = false
                        Log.e(TAG, "*******TTS -> mServiceConnection == null*******")
                    }
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        return isBindConnection
    }

}