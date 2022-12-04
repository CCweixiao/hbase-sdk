package com.github.CCweixiao.hbase.sdk.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author leojie 2022/12/4 17:09
 */
public final class UnsafeAccess {

    private static final Logger LOG = LoggerFactory.getLogger(UnsafeAccess.class);

    public static final Unsafe THE_UNSAFE;

    /**
     * The offset to the first element in a byte array.
     */
    public static final long BYTE_ARRAY_BASE_OFFSET;

    public static final boolean LITTLE_ENDIAN = ByteOrder.nativeOrder()
            .equals(ByteOrder.LITTLE_ENDIAN);

    /**
     * This number limits the number of bytes to copy per call to Unsafe's
     * copyMemory method. A limit is imposed to allow for safepoint polling
     * during a large copy
     */
    static final long UNSAFE_COPY_THRESHOLD = 1024L * 1024L;

    static {
        THE_UNSAFE = (Unsafe) AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Field f = Unsafe.class.getDeclaredField("theUnsafe");
                    f.setAccessible(true);
                    return f.get(null);
                } catch (Throwable e) {
                    LOG.warn("sun.misc.Unsafe is not accessible", e);
                }
                return null;
            }
        });

        if (THE_UNSAFE != null) {
            BYTE_ARRAY_BASE_OFFSET = THE_UNSAFE.arrayBaseOffset(byte[].class);
        } else {
            BYTE_ARRAY_BASE_OFFSET = -1;
        }
    }

    private UnsafeAccess() {
    }

    // APIs to read primitive data from a byte[] using Unsafe way

    /**
     * Converts a byte array to a short value considering it was written in big-endian format.
     *
     * @param bytes  byte array
     * @param offset offset into array
     * @return the short value
     */
    public static short toShort(byte[] bytes, int offset) {
        if (LITTLE_ENDIAN) {
            return Short.reverseBytes(THE_UNSAFE.getShort(bytes, offset + BYTE_ARRAY_BASE_OFFSET));
        } else {
            return THE_UNSAFE.getShort(bytes, offset + BYTE_ARRAY_BASE_OFFSET);
        }
    }

    /**
     * Converts a byte array to an int value considering it was written in big-endian format.
     *
     * @param bytes  byte array
     * @param offset offset into array
     * @return the int value
     */
    public static int toInt(byte[] bytes, int offset) {
        if (LITTLE_ENDIAN) {
            return Integer.reverseBytes(THE_UNSAFE.getInt(bytes, offset + BYTE_ARRAY_BASE_OFFSET));
        } else {
            return THE_UNSAFE.getInt(bytes, offset + BYTE_ARRAY_BASE_OFFSET);
        }
    }

    /**
     * Converts a byte array to a long value considering it was written in big-endian format.
     *
     * @param bytes  byte array
     * @param offset offset into array
     * @return the long value
     */
    public static long toLong(byte[] bytes, int offset) {
        if (LITTLE_ENDIAN) {
            return Long.reverseBytes(THE_UNSAFE.getLong(bytes, offset + BYTE_ARRAY_BASE_OFFSET));
        } else {
            return THE_UNSAFE.getLong(bytes, offset + BYTE_ARRAY_BASE_OFFSET);
        }
    }

    // APIs to write primitive data to a byte[] using Unsafe way

    /**
     * Put a short value out to the specified byte array position in big-endian format.
     *
     * @param bytes  the byte array
     * @param offset position in the array
     * @param val    short to write out
     * @return incremented offset
     */
    public static int putShort(byte[] bytes, int offset, short val) {
        if (LITTLE_ENDIAN) {
            val = Short.reverseBytes(val);
        }
        THE_UNSAFE.putShort(bytes, offset + BYTE_ARRAY_BASE_OFFSET, val);
        return offset + BytesUtil.SIZEOF_SHORT;
    }

    /**
     * Put an int value out to the specified byte array position in big-endian format.
     *
     * @param bytes  the byte array
     * @param offset position in the array
     * @param val    int to write out
     * @return incremented offset
     */
    public static int putInt(byte[] bytes, int offset, int val) {
        if (LITTLE_ENDIAN) {
            val = Integer.reverseBytes(val);
        }
        THE_UNSAFE.putInt(bytes, offset + BYTE_ARRAY_BASE_OFFSET, val);
        return offset + BytesUtil.SIZEOF_INT;
    }

    /**
     * Put a long value out to the specified byte array position in big-endian format.
     *
     * @param bytes  the byte array
     * @param offset position in the array
     * @param val    long to write out
     * @return incremented offset
     */
    public static int putLong(byte[] bytes, int offset, long val) {
        if (LITTLE_ENDIAN) {
            val = Long.reverseBytes(val);
        }
        THE_UNSAFE.putLong(bytes, offset + BYTE_ARRAY_BASE_OFFSET, val);
        return offset + BytesUtil.SIZEOF_LONG;
    }

    // APIs to read primitive data from a ByteBuffer using Unsafe way

    /**
     * Reads a short value at the given buffer's offset considering it was written in big-endian
     * format.
     *
     * @param buf
     * @param offset
     * @return short value at offset
     */
    public static short toShort(ByteBuffer buf, int offset) {
        if (LITTLE_ENDIAN) {
            return Short.reverseBytes(getAsShort(buf, offset));
        }
        return getAsShort(buf, offset);
    }

    /**
     * Reads a short value at the given Object's offset considering it was written in big-endian
     * format.
     *
     * @param ref
     * @param offset
     * @return short value at offset
     */
    public static short toShort(Object ref, long offset) {
        if (LITTLE_ENDIAN) {
            return Short.reverseBytes(THE_UNSAFE.getShort(ref, offset));
        }
        return THE_UNSAFE.getShort(ref, offset);
    }

    static short getAsShort(ByteBuffer buf, int offset) {
        if (buf.isDirect()) {
            return THE_UNSAFE.getShort(((DirectBuffer) buf).address() + offset);
        }
        return THE_UNSAFE.getShort(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset);
    }

    public static int toInt(ByteBuffer buf, int offset) {
        if (LITTLE_ENDIAN) {
            return Integer.reverseBytes(getAsInt(buf, offset));
        }
        return getAsInt(buf, offset);
    }

    public static int toInt(Object ref, long offset) {
        if (LITTLE_ENDIAN) {
            return Integer.reverseBytes(THE_UNSAFE.getInt(ref, offset));
        }
        return THE_UNSAFE.getInt(ref, offset);
    }

    static int getAsInt(ByteBuffer buf, int offset) {
        if (buf.isDirect()) {
            return THE_UNSAFE.getInt(((DirectBuffer) buf).address() + offset);
        }
        return THE_UNSAFE.getInt(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset);
    }

    public static long toLong(ByteBuffer buf, int offset) {
        if (LITTLE_ENDIAN) {
            return Long.reverseBytes(getAsLong(buf, offset));
        }
        return getAsLong(buf, offset);
    }

    public static long toLong(Object ref, long offset) {
        if (LITTLE_ENDIAN) {
            return Long.reverseBytes(THE_UNSAFE.getLong(ref, offset));
        }
        return THE_UNSAFE.getLong(ref, offset);
    }

    static long getAsLong(ByteBuffer buf, int offset) {
        if (buf.isDirect()) {
            return THE_UNSAFE.getLong(((DirectBuffer) buf).address() + offset);
        }
        return THE_UNSAFE.getLong(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset);
    }

    /**
     * Put an int value out to the specified ByteBuffer offset in big-endian format.
     *
     * @param buf    the ByteBuffer to write to
     * @param offset offset in the ByteBuffer
     * @param val    int to write out
     * @return incremented offset
     */
    public static int putInt(ByteBuffer buf, int offset, int val) {
        if (LITTLE_ENDIAN) {
            val = Integer.reverseBytes(val);
        }
        if (buf.isDirect()) {
            THE_UNSAFE.putInt(((DirectBuffer) buf).address() + offset, val);
        } else {
            THE_UNSAFE.putInt(buf.array(), offset + buf.arrayOffset() + BYTE_ARRAY_BASE_OFFSET, val);
        }
        return offset + BytesUtil.SIZEOF_INT;
    }

    public static void copy(byte[] src, int srcOffset, ByteBuffer dest, int destOffset, int length) {
        long destAddress = destOffset;
        Object destBase = null;
        if (dest.isDirect()) {
            destAddress = destAddress + ((DirectBuffer) dest).address();
        } else {
            destAddress = destAddress + BYTE_ARRAY_BASE_OFFSET + dest.arrayOffset();
            destBase = dest.array();
        }
        long srcAddress = srcOffset + BYTE_ARRAY_BASE_OFFSET;
        unsafeCopy(src, srcAddress, destBase, destAddress, length);
    }

    private static void unsafeCopy(Object src, long srcAddr, Object dst, long destAddr, long len) {
        while (len > 0) {
            long size = Math.min(len, UNSAFE_COPY_THRESHOLD);
            THE_UNSAFE.copyMemory(src, srcAddr, dst, destAddr, size);
            len -= size;
            srcAddr += size;
            destAddr += size;
        }
    }
    public static void copy(ByteBuffer src, int srcOffset, byte[] dest, int destOffset,
                            int length) {
        long srcAddress = srcOffset;
        Object srcBase = null;
        if (src.isDirect()) {
            srcAddress = srcAddress + ((DirectBuffer) src).address();
        } else {
            srcAddress = srcAddress + BYTE_ARRAY_BASE_OFFSET + src.arrayOffset();
            srcBase = src.array();
        }
        long destAddress = destOffset + BYTE_ARRAY_BASE_OFFSET;
        unsafeCopy(srcBase, srcAddress, dest, destAddress, length);
    }

    public static void copy(ByteBuffer src, int srcOffset, ByteBuffer dest, int destOffset,
                            int length) {
        long srcAddress, destAddress;
        Object srcBase = null, destBase = null;
        if (src.isDirect()) {
            srcAddress = srcOffset + ((DirectBuffer) src).address();
        } else {
            srcAddress = (long) srcOffset + src.arrayOffset() + BYTE_ARRAY_BASE_OFFSET;
            srcBase = src.array();
        }
        if (dest.isDirect()) {
            destAddress = destOffset + ((DirectBuffer) dest).address();
        } else {
            destAddress = destOffset + BYTE_ARRAY_BASE_OFFSET + dest.arrayOffset();
            destBase = dest.array();
        }
        unsafeCopy(srcBase, srcAddress, destBase, destAddress, length);
    }

    // APIs to add primitives to BBs

    /**
     * Put a short value out to the specified BB position in big-endian format.
     *
     * @param buf    the byte buffer
     * @param offset position in the buffer
     * @param val    short to write out
     * @return incremented offset
     */
    public static int putShort(ByteBuffer buf, int offset, short val) {
        if (LITTLE_ENDIAN) {
            val = Short.reverseBytes(val);
        }
        if (buf.isDirect()) {
            THE_UNSAFE.putShort(((DirectBuffer) buf).address() + offset, val);
        } else {
            THE_UNSAFE.putShort(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset, val);
        }
        return offset + BytesUtil.SIZEOF_SHORT;
    }

    /**
     * Put a long value out to the specified BB position in big-endian format.
     *
     * @param buf    the byte buffer
     * @param offset position in the buffer
     * @param val    long to write out
     * @return incremented offset
     */
    public static int putLong(ByteBuffer buf, int offset, long val) {
        if (LITTLE_ENDIAN) {
            val = Long.reverseBytes(val);
        }
        if (buf.isDirect()) {
            THE_UNSAFE.putLong(((DirectBuffer) buf).address() + offset, val);
        } else {
            THE_UNSAFE.putLong(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset, val);
        }
        return offset + BytesUtil.SIZEOF_LONG;
    }

    /**
     * Put a byte value out to the specified BB position in big-endian format.
     *
     * @param buf    the byte buffer
     * @param offset position in the buffer
     * @param b      byte to write out
     * @return incremented offset
     */
    public static int putByte(ByteBuffer buf, int offset, byte b) {
        if (buf.isDirect()) {
            THE_UNSAFE.putByte(((DirectBuffer) buf).address() + offset, b);
        } else {
            THE_UNSAFE.putByte(buf.array(),
                    BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset, b);
        }
        return offset + 1;
    }

    /**
     * Returns the byte at the given offset
     *
     * @param buf    the buffer to read
     * @param offset the offset at which the byte has to be read
     * @return the byte at the given offset
     */
    public static byte toByte(ByteBuffer buf, int offset) {
        if (buf.isDirect()) {
            return THE_UNSAFE.getByte(((DirectBuffer) buf).address() + offset);
        } else {
            return THE_UNSAFE.getByte(buf.array(), BYTE_ARRAY_BASE_OFFSET + buf.arrayOffset() + offset);
        }
    }

    /**
     * Returns the byte at the given offset of the object
     *
     * @param ref
     * @param offset
     * @return the byte at the given offset
     */
    public static byte toByte(Object ref, long offset) {
        return THE_UNSAFE.getByte(ref, offset);
    }
}