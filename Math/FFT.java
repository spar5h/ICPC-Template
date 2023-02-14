void fft(double[] real, double[] imag, boolean invert) {
    int n = real.length;
    int log_n = 0;
    while ((1 << log_n) < n)
        log_n++;
    for (int i = 1, j = 0; i < n; i++) {
        int bit = log_n - 1;
        while (((j >> bit) & 1) == 1) {
            j ^= 1 << bit;
            bit--;
        }
        j ^= 1 << bit;
        if (i < j) {
            double tempReal = real[i];
            double tempImag = imag[i];
            real[i] = real[j];
            imag[i] = imag[j];
            real[j] = tempReal;
            imag[j] = tempImag;
        }
    }
    for (int len = 2; len <= n; len <<= 1) {
        int halfLen = len >> 1;
        double angle = 2 * Math.PI / len;
        if (invert) {
            angle = -angle;
        }
        double wLenReal = Math.cos(angle);
        double wLenImag = Math.sin(angle);
        for (int i = 0; i < n; i += len) {
            double wReal = 1;
            double wImag = 0;
            for (int j = 0; j < halfLen; j++) {
                double uReal = real[i + j];
                double uImag = imag[i + j];
                double vReal = wReal * real[i + j + halfLen] - wImag * imag[i + j + halfLen];
                double vImag = wReal * imag[i + j + halfLen] + wImag * real[i + j + halfLen];
                real[i + j] = uReal + vReal;
                imag[i + j] = uImag + vImag;
                real[i + j + halfLen] = uReal - vReal;
                imag[i + j + halfLen] = uImag - vImag;
                double nextWReal = wReal * wLenReal - wImag * wLenImag;
                double nextWImag = wReal * wLenImag + wImag * wLenReal;
                wReal = nextWReal;
                wImag = nextWImag;
            }
        }
    }
    if (invert) {
        for (int i = 0; i < n; i++) {
            real[i] /= n;
            imag[i] /= n;
        }
    }
}

long[] multiply(long[] a, long[] b) {
    int n = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
    if (n == 0)
        n = 1;
    double[] aReal = new double[n];
    double[] aImag = new double[n];
    double[] bReal = new double[n];
    double[] bImag = new double[n];
    for (int i = 0; i < a.length; i++)
        aReal[i] = a[i];
    for (int i = 0; i < b.length; i++)
        bReal[i] = b[i];
    fft(aReal, aImag, false);
    fft(bReal, bImag, false);
    for (int i = 0; i < n; i++) {
        double real = aReal[i] * bReal[i] - aImag[i] * bImag[i];
        double imag = aReal[i] * bImag[i] + aImag[i] * bReal[i];
        aReal[i] = real;
        aImag[i] = imag;
    }
    fft(aReal, aImag, true);
    long[] c = new long[n];
    for (int i = 0; i < n; i++) {
        c[i] = Math.round(aReal[i]);
    }
    return c;
}

// long[] c = multiply(a, b));
