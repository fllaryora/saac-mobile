package phi.saac.comunicame.ui.speech

import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log


class MyUtteranceProgressListener(val observer: MyUtteranceProgressListener.Listener,
                                  var queueMode: Int,
                                  var speaking:Boolean,
                                  var synth:Boolean,
                                  var awaitSynthCompletion:Boolean,
                                  var awaitSpeakCompletion:Boolean): UtteranceProgressListener() {
    private val TAG = "TTS"

    //mete y saca trabajos a leer
    val utterances: HashMap<String, String> = HashMap()

    interface Listener {
        fun invokeMethod(text:String, condition: Any)
        fun speakResult( condition: Int)
        fun synthResult( condition: Int)
    }

    fun speakCompletion(success: Int) {
        speaking = false
        observer.speakResult(success)
    }

    fun synthCompletion(success: Int) {
        synth = false
        observer.synthResult(success)
    }
    override fun onStart(utteranceId: String?) {
        if (utteranceId != null && utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            Log.d(TAG, "Utterance ID has started: " + utteranceId)
            observer.invokeMethod("synth.onStart", true)
        } else {
            Log.d(TAG, "Utterance ID has started: " + utteranceId)
            observer.invokeMethod("speak.onStart", true)
        }
        if (Build.VERSION.SDK_INT < 26) {
            this.onProgress(utteranceId, 0, utterances[utteranceId]?.length?:0)
        }
    }

    // Requires Android 26 or later
    override fun onRangeStart(utteranceId: String?, startAt: Int, endAt: Int, frame: Int) {
        if (utteranceId != null && !utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            super.onRangeStart(utteranceId, startAt, endAt, frame)
            onProgress(utteranceId, startAt, endAt)
        }
    }

    override fun onDone(utteranceId: String?) {
        if (utteranceId != null && utteranceId.startsWith(SILENCE_PREFIX)) return
        if (utteranceId != null && utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            Log.d(TAG, "Utterance ID has completed: " + utteranceId)
            if (awaitSynthCompletion) {
                synthCompletion(1)
            }
            observer.invokeMethod("synth.onComplete", true)
        } else {
            Log.d(TAG, "Utterance ID has completed: " + utteranceId)
            if (awaitSpeakCompletion && queueMode == TextToSpeech.QUEUE_FLUSH) {
                speakCompletion(1)
            }
            observer.invokeMethod("speak.onComplete", true)
        }
        utterances.remove(utteranceId)
    }

    private fun onProgress(utteranceId: String?, startAt: Int, endAt: Int) {
        if (utteranceId != null && !utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            val text: String = utterances[utteranceId] ?:""
            val data: HashMap<String, String> = HashMap()
            data["text"] = text
            data["start"] = Integer.toString(startAt)
            data["end"] = Integer.toString(endAt)
            data["word"] = text.substring(startAt, endAt)
            observer.invokeMethod("speak.onProgress", data)
        }
    }

    override fun onError(utteranceId: String?) {
        if (utteranceId != null && utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            if (awaitSynthCompletion) {
                synth = false
            }
            observer.invokeMethod("synth.onError", "Error from TextToSpeech (synth)")
        } else {
            if (awaitSpeakCompletion) {
                speaking = false
            }
            observer.invokeMethod("speak.onError", "Error from TextToSpeech (speak) " )
        }
    }

    override fun onError(utteranceId: String?, errorCode: Int ) {
        if (utteranceId != null && utteranceId.startsWith(SYNTHESIZE_TO_FILE_PREFIX)) {
            if (awaitSynthCompletion) {
                synth = false
            }
            observer.invokeMethod("synth.onError", "Error from TextToSpeech (synth) - " + errorCode)
        } else {
            if (awaitSpeakCompletion) {
                speaking = false
            }
            observer.invokeMethod("speak.onError", "Error from TextToSpeech (speak) - " + errorCode)
        }
    }

}
const val SILENCE_PREFIX = "SIL_"
const val SYNTHESIZE_TO_FILE_PREFIX = "STF_"