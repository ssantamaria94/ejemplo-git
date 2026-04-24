package audio;

import javax.sound.sampled.*;

public class AudioCapturador {

    private TargetDataLine microfono;
    private AudioFormat formato;
    private boolean capturando;

    public AudioCapturador() {
        this.formato = crearFormato();
    }

    private AudioFormat crearFormato() {
        return new AudioFormat(
                44100.0f, // sample rate
                16,       // sample size in bits
                1,        // mono
                true,     // signed
                false     // little endian
        );
    }

    public void iniciar() throws LineUnavailableException {
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, formato);
        microfono = (TargetDataLine) AudioSystem.getLine(info);
        microfono.open(formato);
        microfono.start();
        capturando = true;

        System.out.println("🎙️ Micrófono activado");
    }

    public byte[] capturarAudio() {
        byte[] buffer = new byte[2048];
        if (capturando) {
            microfono.read(buffer, 0, buffer.length);
        }
        return buffer;
    }

    public void detener() {
        capturando = false;
        microfono.stop();
        microfono.close();
        System.out.println("🎙️ Micrófono detenido");
    }
}
