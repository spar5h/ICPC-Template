long modExp(long x, long y, long mod) {
    if (y == 0)
        return 1 % mod;
    long ret = modExp(x, y >> 1, mod);
    ret = ret * ret % mod;
    if ((y & 1) == 1)
        ret = ret * (x % mod) % mod;
    return ret;
}
