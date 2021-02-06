package com.github.CCweixiao.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hbase.thirdparty.com.google.common.base.Preconditions;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author leojie 2021/2/4 10:42 下午
 */
public class RegionSplitter {
    public interface SplitAlgorithm {
        /**
         * Split a pre-existing region into 2 regions.
         *
         * @param start
         *          first row (inclusive)
         * @param end
         *          last row (exclusive)
         * @return the split row to use
         */
        byte[] split(byte[] start, byte[] end);

        /**
         * Split an entire table.
         *
         * @param numRegions
         *          number of regions to split the table into
         *
         * @throws RuntimeException
         *           user input is validated at this time. may throw a runtime
         *           exception in response to a parse failure
         * @return array of split keys for the initial regions of the table. The
         *         length of the returned array should be numRegions-1.
         */
        byte[][] split(int numRegions);

        /**
         * Some MapReduce jobs may want to run multiple mappers per region,
         * this is intended for such usecase.
         *
         * @param start first row (inclusive)
         * @param end last row (exclusive)
         * @param numSplits number of splits to generate
         * @param inclusive whether start and end are returned as split points
         * @return split结果
         */
        byte[][] split(byte[] start, byte[] end, int numSplits, boolean inclusive);

        /**
         * In HBase, the first row is represented by an empty byte array. This might
         * cause problems with your split algorithm or row printing. All your APIs
         * will be passed firstRow() instead of empty array.
         *
         * @return your representation of your first row
         */
        byte[] firstRow();

        /**
         * In HBase, the last row is represented by an empty byte array. This might
         * cause problems with your split algorithm or row printing. All your APIs
         * will be passed firstRow() instead of empty array.
         *
         * @return your representation of your last row
         */
        byte[] lastRow();

        /**
         * In HBase, the last row is represented by an empty byte array. Set this
         * value to help the split code understand how to evenly divide the first
         * region.
         *
         * @param userInput
         *          raw user input (may throw RuntimeException on parse failure)
         */
        void setFirstRow(String userInput);

        /**
         * In HBase, the last row is represented by an empty byte array. Set this
         * value to help the split code understand how to evenly divide the last
         * region. Note that this last row is inclusive for all rows sharing the
         * same prefix.
         *
         * @param userInput
         *          raw user input (may throw RuntimeException on parse failure)
         */
        void setLastRow(String userInput);

        /**
         * @param input
         *          user or file input for row
         * @return byte array representation of this row for HBase
         */
        byte[] strToRow(String input);

        /**
         * @param row
         *          byte array representing a row in HBase
         * @return String to use for debug &amp; file printing
         */
        String rowToStr(byte[] row);

        /**
         * @return the separator character to use when storing / printing the row
         */
        String separator();

        /**
         * Set the first row
         * @param userInput byte array of the row key.
         */
        void setFirstRow(byte[] userInput);

        /**
         * Set the last row
         * @param userInput byte array of the row key.
         */
        void setLastRow(byte[] userInput);
    }

    public static class HexStringSplit extends NumberStringSplit {
        final static String DEFAULT_MIN_HEX = "00000000";
        final static String DEFAULT_MAX_HEX = "FFFFFFFF";
        final static int RADIX_HEX = 16;

        public HexStringSplit() {
            super(DEFAULT_MIN_HEX, DEFAULT_MAX_HEX, RADIX_HEX);
        }

    }

    public static class DecimalStringSplit extends NumberStringSplit {
        final static String DEFAULT_MIN_DEC = "00000000";
        final static String DEFAULT_MAX_DEC = "99999999";
        final static int RADIX_DEC = 10;

        public DecimalStringSplit() {
            super(DEFAULT_MIN_DEC, DEFAULT_MAX_DEC, RADIX_DEC);
        }

    }

    public abstract static class NumberStringSplit implements SplitAlgorithm {

        String firstRow;
        BigInteger firstRowInt;
        String lastRow;
        BigInteger lastRowInt;
        int rowComparisonLength;
        int radix;

        NumberStringSplit(String minRow, String maxRow, int radix) {
            this.firstRow = minRow;
            this.lastRow = maxRow;
            this.radix = radix;
            this.firstRowInt = BigInteger.ZERO;
            this.lastRowInt = new BigInteger(lastRow, this.radix);
            this.rowComparisonLength = lastRow.length();
        }

        @Override
        public byte[] split(byte[] start, byte[] end) {
            BigInteger s = convertToBigInteger(start);
            BigInteger e = convertToBigInteger(end);
            Preconditions.checkArgument(!e.equals(BigInteger.ZERO));
            return convertToByte(split2(s, e));
        }

        @Override
        public byte[][] split(int n) {
            Preconditions.checkArgument(lastRowInt.compareTo(firstRowInt) > 0,
                    "last row (%s) is configured less than first row (%s)", lastRow,
                    firstRow);
            // +1 to range because the last row is inclusive
            BigInteger range = lastRowInt.subtract(firstRowInt).add(BigInteger.ONE);
            Preconditions.checkState(range.compareTo(BigInteger.valueOf(n)) >= 0,
                    "split granularity (%s) is greater than the range (%s)", n, range);

            BigInteger[] splits = new BigInteger[n - 1];
            BigInteger sizeOfEachSplit = range.divide(BigInteger.valueOf(n));
            for (int i = 1; i < n; i++) {
                // NOTE: this means the last region gets all the slop.
                // This is not a big deal if we're assuming n << MAXHEX
                splits[i - 1] = firstRowInt.add(sizeOfEachSplit.multiply(BigInteger
                        .valueOf(i)));
            }
            return convertToBytes(splits);
        }

        @Override
        public byte[][] split(byte[] start, byte[] end, int numSplits, boolean inclusive) {
            BigInteger s = convertToBigInteger(start);
            BigInteger e = convertToBigInteger(end);

            Preconditions.checkArgument(e.compareTo(s) > 0,
                    "last row (%s) is configured less than first row (%s)", rowToStr(end),
                    end);
            // +1 to range because the last row is inclusive
            BigInteger range = e.subtract(s).add(BigInteger.ONE);
            Preconditions.checkState(range.compareTo(BigInteger.valueOf(numSplits)) >= 0,
                    "split granularity (%s) is greater than the range (%s)", numSplits, range);

            BigInteger[] splits = new BigInteger[numSplits - 1];
            BigInteger sizeOfEachSplit = range.divide(BigInteger.valueOf(numSplits));
            for (int i = 1; i < numSplits; i++) {
                // NOTE: this means the last region gets all the slop.
                // This is not a big deal if we're assuming n << MAXHEX
                splits[i - 1] = s.add(sizeOfEachSplit.multiply(BigInteger
                        .valueOf(i)));
            }

            if (inclusive) {
                BigInteger[] inclusiveSplitPoints = new BigInteger[numSplits + 1];
                inclusiveSplitPoints[0] = convertToBigInteger(start);
                inclusiveSplitPoints[numSplits] = convertToBigInteger(end);
                System.arraycopy(splits, 0, inclusiveSplitPoints, 1, splits.length);
                return convertToBytes(inclusiveSplitPoints);
            } else {
                return convertToBytes(splits);
            }
        }

        @Override
        public byte[] firstRow() {
            return convertToByte(firstRowInt);
        }

        @Override
        public byte[] lastRow() {
            return convertToByte(lastRowInt);
        }

        @Override
        public void setFirstRow(String userInput) {
            firstRow = userInput;
            firstRowInt = new BigInteger(firstRow, radix);
        }

        @Override
        public void setLastRow(String userInput) {
            lastRow = userInput;
            lastRowInt = new BigInteger(lastRow, radix);
            // Precondition: lastRow > firstRow, so last's length is the greater
            rowComparisonLength = lastRow.length();
        }

        @Override
        public byte[] strToRow(String in) {
            return convertToByte(new BigInteger(in, radix));
        }

        @Override
        public String rowToStr(byte[] row) {
            return Bytes.toStringBinary(row);
        }

        @Override
        public String separator() {
            return " ";
        }

        @Override
        public void setFirstRow(byte[] userInput) {
            firstRow = Bytes.toString(userInput);
        }

        @Override
        public void setLastRow(byte[] userInput) {
            lastRow = Bytes.toString(userInput);
        }

        /**
         * Divide 2 numbers in half (for split algorithm)
         *
         * @param a number #1
         * @param b number #2
         * @return the midpoint of the 2 numbers
         */
        public BigInteger split2(BigInteger a, BigInteger b) {
            return a.add(b).divide(BigInteger.valueOf(2)).abs();
        }

        /**
         * Returns an array of bytes corresponding to an array of BigIntegers
         *
         * @param bigIntegers numbers to convert
         * @return bytes corresponding to the bigIntegers
         */
        public byte[][] convertToBytes(BigInteger[] bigIntegers) {
            byte[][] returnBytes = new byte[bigIntegers.length][];
            for (int i = 0; i < bigIntegers.length; i++) {
                returnBytes[i] = convertToByte(bigIntegers[i]);
            }
            return returnBytes;
        }

        /**
         * Returns the bytes corresponding to the BigInteger
         *
         * @param bigInteger number to convert
         * @param pad padding length
         * @return byte corresponding to input BigInteger
         */
        public byte[] convertToByte(BigInteger bigInteger, int pad) {
            String bigIntegerString = bigInteger.toString(radix);
            bigIntegerString = StringUtils.leftPad(bigIntegerString, pad, '0');
            return Bytes.toBytes(bigIntegerString);
        }

        /**
         * Returns the bytes corresponding to the BigInteger
         *
         * @param bigInteger number to convert
         * @return corresponding bytes
         */
        public byte[] convertToByte(BigInteger bigInteger) {
            return convertToByte(bigInteger, rowComparisonLength);
        }

        /**
         * Returns the BigInteger represented by the byte array
         *
         * @param row byte array representing row
         * @return the corresponding BigInteger
         */
        public BigInteger convertToBigInteger(byte[] row) {
            return (row.length > 0) ? new BigInteger(Bytes.toString(row), radix)
                    : BigInteger.ZERO;
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " [" + rowToStr(firstRow())
                    + "," + rowToStr(lastRow()) + "]";
        }
    }

    public static class UniformSplit implements SplitAlgorithm {
        static final byte xFF = (byte) 0xFF;
        byte[] firstRowBytes = ArrayUtils.EMPTY_BYTE_ARRAY;
        byte[] lastRowBytes =
                new byte[] {xFF, xFF, xFF, xFF, xFF, xFF, xFF, xFF};
        @Override
        public byte[] split(byte[] start, byte[] end) {
            return Bytes.split(start, end, 1)[1];
        }

        @Override
        public byte[][] split(int numRegions) {
            Preconditions.checkArgument(
                    Bytes.compareTo(lastRowBytes, firstRowBytes) > 0,
                    "last row (%s) is configured less than first row (%s)",
                    Bytes.toStringBinary(lastRowBytes),
                    Bytes.toStringBinary(firstRowBytes));

            byte[][] splits = Bytes.split(firstRowBytes, lastRowBytes, true,
                    numRegions - 1);
            Preconditions.checkState(splits != null,
                    "Could not split region with given user input: " + this);

            // remove endpoints, which are included in the splits list

            return splits == null? null: Arrays.copyOfRange(splits, 1, splits.length - 1);
        }

        @Override
        public byte[][] split(byte[] start, byte[] end, int numSplits, boolean inclusive) {
            if (Arrays.equals(start, HConstants.EMPTY_BYTE_ARRAY)) {
                start = firstRowBytes;
            }
            if (Arrays.equals(end, HConstants.EMPTY_BYTE_ARRAY)) {
                end = lastRowBytes;
            }
            Preconditions.checkArgument(
                    Bytes.compareTo(end, start) > 0,
                    "last row (%s) is configured less than first row (%s)",
                    Bytes.toStringBinary(end),
                    Bytes.toStringBinary(start));

            byte[][] splits = Bytes.split(start, end, true,
                    numSplits - 1);
            Preconditions.checkState(splits != null,
                    "Could not calculate input splits with given user input: " + this);
            if (inclusive) {
                return splits;
            } else {
                // remove endpoints, which are included in the splits list
                return Arrays.copyOfRange(splits, 1, splits.length - 1);
            }
        }

        @Override
        public byte[] firstRow() {
            return firstRowBytes;
        }

        @Override
        public byte[] lastRow() {
            return lastRowBytes;
        }

        @Override
        public void setFirstRow(String userInput) {
            firstRowBytes = Bytes.toBytesBinary(userInput);
        }

        @Override
        public void setLastRow(String userInput) {
            lastRowBytes = Bytes.toBytesBinary(userInput);
        }


        @Override
        public void setFirstRow(byte[] userInput) {
            firstRowBytes = userInput;
        }

        @Override
        public void setLastRow(byte[] userInput) {
            lastRowBytes = userInput;
        }

        @Override
        public byte[] strToRow(String input) {
            return Bytes.toBytesBinary(input);
        }

        @Override
        public String rowToStr(byte[] row) {
            return Bytes.toStringBinary(row);
        }

        @Override
        public String separator() {
            return ",";
        }

        @Override
        public String toString() {
            return this.getClass().getSimpleName() + " [" + rowToStr(firstRow())
                    + "," + rowToStr(lastRow()) + "]";
        }
    }
}
