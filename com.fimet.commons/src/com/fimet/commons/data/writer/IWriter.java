package com.fimet.commons.data.writer;

public interface IWriter {
	
	static final short[] asciiToEbcdicTable = { 0, 1, 2, 3, 55, 45, 46, 47, 22, 5, 37, 11, 12, 13, 14, 15, 16, 17, 18, 19, 60, 61, 50, 38, 24, 25, 63, 39, 34, 29, 53, 31, 64, 90, 127, 123, 91, 108, 80, 125, 77, 93, 92, 78, 107, 96, 75, 97, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 122, 94, 76, 126, 110, 111, 124, 193, 194, 195, 196, 197, 198, 199, 200, 201, 209, 210, 211, 212, 213, 214, 215, 216, 217, 226, 227, 228, 229, 230, 231, 232, 233, 173, 224, 189, 95, 109, 121, 129, 130, 131, 132, 133, 134, 135, 136, 137, 145, 146, 147, 148, 149, 150, 151, 152, 153, 162, 163, 164, 165, 166, 167, 168, 169, 139, 106, 155, 161, 7 };
	
	void move(int offser);
	void append(String text);
	void append(byte b);
	void append(byte[] bytes);
	void preappend(String text);
	void preappend(byte[] bytes);
	void insert(int index, byte[] bytes);
	void insert(int index, String text);
	void replace(int index, byte[] bytes);
	void replace(int index, String text);
	byte[] getBytes();
	int length();
}
