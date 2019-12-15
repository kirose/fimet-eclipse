package com.fimet.commons.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import com.fimet.commons.Activator;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public final class EncryptionUtils {
	private static final String CHARS_HEX = "0123456789ABCDEF";
	private EncryptionUtils() {}

	public static String decrypt(String dataEncrypted, String keySerialNumber, String bdk) {
		return decrypt(dataEncrypted, keySerialNumber, bdk, false);
	}
	public static String encrypt(String dataEncrypted, String keySerialNumber, String bdk) {
		return decrypt(dataEncrypted, keySerialNumber, bdk, true);
	}
	private static String decrypt(String dataEncrypted, String keySerialNumber, String bdk, boolean encrypt) {
		try {
			byte[] ksn_cifrado = new byte[8];
			byte[] bdk_byte = new byte[16];
			byte[] curkey = new byte[16];
	
			byte[] ksn_byte = new byte[8];
			byte[] C0xor = new byte[16];
			byte[] bdkxorc0 = new byte[16];
			byte[] ksn_bdkxorc0 = new byte[8];
	
			byte[] right_curkey = new byte[8];
			byte[] left_curkey = new byte[8];
	
			byte[] r8a = new byte[8];
			byte[] r8b = new byte[8];
			byte[] r8_byte = new byte[8];
			byte[] curkeyxorc0 = new byte[16];
	
			byte[] arreglo_data = stringToBcd(dataEncrypted);
			long data_length_int = dataEncrypted.length() / 2;
			byte[] data = new byte[(int)data_length_int];
			for (int a = 0; a < data_length_int; a++) {
				data[a] = arreglo_data[a];
			}
			/*System.out.println("ksn: " + keySerialNumber + "\n");
			System.out.println("data_length: " + data_length_int + "\n");
			System.out.println("data: " + bcdToString(data) + "\n");
			System.out.println("bdk: " + bdk + "\n");*/
	
	
			bdk_byte = stringToBcd(bdk);
			TripleDES tripledes = new TripleDES(bdk_byte);
			long ksn_clareado = 0L;
			ksn_clareado = bcdtoint(keySerialNumber.substring(14, 20));
			ksn_clareado &= 0xFFE00000;
			byte[] sig_ksn = new byte[4];
			sig_ksn = intToByteArray(ksn_clareado);
			String sig_ksn_str = bcdToString(sig_ksn);
			String ksn_clareado_f = keySerialNumber.substring(0, 14) + sig_ksn_str.substring(2, 8);
	
			String ksn_16 = keySerialNumber.substring(0, 16);
			ksn_16 = ksn_clareado_f.substring(0, 16);
			ksn_byte = stringToBcd(ksn_16);
			ksn_cifrado = tripledes.Encrypt(ksn_byte);
			C0xor = stringToBcd("C0C0C0C000000000C0C0C0C000000000");
			for (int a = 0; a < bdk_byte.length; a++) {
				bdkxorc0[a] = ((byte)(C0xor[a] ^ bdk_byte[a]));
			}
			//System.out.println("" + bdk);
			TripleDES bdk_xor_c0 = new TripleDES(bdkxorc0);
			ksn_bdkxorc0 = bdk_xor_c0.Encrypt(ksn_byte);
			left_curkey = ksn_cifrado;
			right_curkey = ksn_bdkxorc0;
			byte[] key_init = new byte[ksn_cifrado.length + ksn_bdkxorc0.length];
			System.arraycopy(ksn_cifrado, 0, key_init, 0, ksn_cifrado.length);
			System.arraycopy(ksn_bdkxorc0, 0, key_init, ksn_cifrado.length, ksn_bdkxorc0.length);
			//System.out.println("key_init: " + bcdToString(key_init) + "\n");
	
	
			curkey = key_init;
			String r8 = keySerialNumber.substring(4, 20);
	
			long mapea_aceros = bcdtoint(r8.substring(10, 16));
	
			mapea_aceros &= 0xFFE00000;
	
			long byte_util = mapea_aceros;
			byte[] respuesta = new byte[4];
			respuesta = intToByteArray(mapea_aceros);
			String Resultado_final = bcdToString(respuesta);
			r8 = r8.substring(0, 10) + Resultado_final.substring(2, 8);
			r8_byte = stringToBcd(r8);
	
			long r3 = bcdtoint(keySerialNumber.substring(14, 20));
			r3 &= 0x1FFFFF;
			long r8_right = 0L;
			long sr = 1048576L;
			for (int a = 1; a <= 21; a++) {
				if ((sr & r3) == 0L) {
					sr >>>= 1;
				} else {
					long sr_xor_r8_right = sr | r8_right;
					r8_right = sr_xor_r8_right | byte_util;
					respuesta = intToByteArray(r8_right);
					Resultado_final = bcdToString(respuesta);
					r8 = r8.substring(0, 10) + Resultado_final.substring(2, 8);
					r8_byte = stringToBcd(r8);
					right_curkey = desfragmenta_curkey(curkey, 0);
					left_curkey = desfragmenta_curkey(curkey, 1);
					for (int b = 0; b < 8; b++) {
						r8a[b] = ((byte)(right_curkey[b] ^ r8_byte[b]));
					}
					DES encryp_r8a = new DES(left_curkey);
					r8a = encryp_r8a.encrypt(r8a);
					for (int b = 0; b < 8; b++) {
						r8a[b] = ((byte)(right_curkey[b] ^ r8a[b]));
					}
					for (int b = 0; b < 16; b++) {
						curkeyxorc0[b] = ((byte)(curkey[b] ^ C0xor[b]));
					}
					right_curkey = desfragmenta_curkey(curkeyxorc0, 0);
					left_curkey = desfragmenta_curkey(curkeyxorc0, 1);
					for (int b = 0; b < 8; b++) {
						r8b[b] = ((byte)(right_curkey[b] ^ r8_byte[b]));
					}
					DES encryp_r8b = new DES(left_curkey);
					r8b = encryp_r8b.encrypt(r8b);
					for (int b = 0; b < 8; b++) {
						r8b[b] = ((byte)(right_curkey[b] ^ r8b[b]));
					}
					System.arraycopy(r8b, 0, curkey, 0, r8b.length);
					System.arraycopy(r8a, 0, curkey, r8b.length, r8a.length);
					//System.out.println("curkey: " + bcdToString(curkey) + "\n");
					sr >>>= 1;
				}
			}
			byte[] vector = new byte[16];
			vector = stringToBcd("0000000000FF00000000000000FF0000");
			byte[] kv = new byte[16];
			for (int b = 0; b < 16; b++) {
				kv[b] = ((byte)(curkey[b] ^ vector[b]));
			}
			right_curkey = desfragmenta_curkey(kv, 0);
			left_curkey = desfragmenta_curkey(kv, 1);
	
			TripleDES variante = new TripleDES(kv);
			byte[] right_kv = new byte[8];
			byte[] left_kv = new byte[8];
			byte[] bdkey = new byte[16];
			right_kv = variante.Encrypt(right_curkey);
			left_kv = variante.Encrypt(left_curkey);
			System.arraycopy(left_kv, 0, bdkey, 0, left_kv.length);
			System.arraycopy(right_kv, 0, bdkey, left_kv.length, right_kv.length);
			TripleDES bdkey_k = new TripleDES(bdkey);
	
			byte[] data_retorno = new byte[(int)data_length_int];
			byte[] cadena_decode = new byte[8];
			int f = 0;
			int a = 0;
			for (int b = 0; a < data.length; b++)
			{
				cadena_decode[b] = data[a];
				if (b == 7)
				{
					b = -1;
					if (encrypt) {
						cadena_decode = bdkey_k.Encrypt(cadena_decode);
					} else {
						cadena_decode = bdkey_k.Decrypt(cadena_decode);
					}
					for (int d = 0; d < 8; f++)
					{
						data_retorno[f] = cadena_decode[d];d++;
					}
				}
				a++;
			}
			return bcdToString(data_retorno);
		} catch (Exception e) {}
		return null;
	}
	public static byte[] stringToBcd(String s) {
		byte[] letras = s.getBytes();
		int len = s.length();
		int len2 = len;
		int[] nums = new int[len];
		for (int i = 0; i < len; i++) {
			if (((letras[i] >= 65 ? 1 : 0) & (letras[i] <= 70 ? 1 : 0)) != 0) {
				letras[i] -= 55;
			} else {
				letras[i] -= 48;
			}
		}
		byte[] bcd = new byte[s.length() / 2];
		byte[] buf = new byte[s.length() / 2];
		for (int i = 0; i < len2; i += 2) {
			bcd[(i / 2)] = ((byte)(nums[(i + 1)] & 0xF | nums[i] << 4 & 0xF0));
		}
		int j = 0;
		for (int i = 0; i < buf.length; i++) {
			buf[i] = ((byte)(Character.digit(s.charAt(j++), 16) << 4 | Character.digit(s.charAt(j++), 16)));
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			str.append(CHARS_HEX.charAt(buf[(i + 0)] >>> 4 & 0xF));
			str.append(CHARS_HEX.charAt(buf[(i + 0)] & 0xF));
		}
		return buf;
	}
	public static String bcdToString(byte[] buf) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			str.append(CHARS_HEX.charAt(buf[(i + 0)] >>> 4 & 0xF));
			str.append(CHARS_HEX.charAt(buf[(i + 0)] & 0xF));
		}
		return new String(str);
	}
	public static byte[] intToByteArray(long value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++){
			int offset = (b.length - 1 - i) * 8;
			b[i] = ((byte)(int)(value >>> offset & 0xFF));
		}
		return b;
	}
	public static byte[] desfragmenta_curkey(byte[] cadena_16, int tipo)
	{
	  byte[] cadena = new byte[8];
	  if (tipo == 1)
	  {
	    for (int a = 0; a < 8; a++) {
	      cadena[a] = cadena_16[a];
	    }
	  }
	  else
	  {
	    int a = 8;
	    for (int b = 0; a < 16; b++)
	    {
	      cadena[b] = cadena_16[a];
	      a++;
	    }
	  }
	  return cadena;
	}
   public static long bcdtoint(String len)
   {
     long value = 0L;
     long retornar = 0L;
     
     char[] arreglo = new char[len.length()];
     int b = len.length() * 4 - 4;
     for (int i = 0; i < len.length(); b -= 4)
     {
       arreglo[i] = len.charAt(i);
       value = arreglo[i];
       if (value >= 64L)
       {
         value -= 65L;
         value += 10L;
       }
       else
       {
         value -= 48L;
       }
       value <<= b;
       retornar += value;
       value = 0L;i++;
     }
     return retornar;
   }
	public static class TripleDES
	{
		DES left_half;
		DES right_half;

		public TripleDES(byte[] llave)
		{
			byte[] lhalf = new byte[8];
			byte[] rhalf = new byte[8];
			for (int i = 0; i < 8; i++) {
				lhalf[i] = llave[i];
			}
			int i = 8;
			for (int j = 0; i < 16; j++)
			{
				rhalf[j] = llave[i];i++;
			}
			this.left_half = new DES(lhalf);
			this.right_half = new DES(rhalf);
		}

		public byte[] Encrypt(byte[] data)
		{
			byte[] cipherData = null;
			cipherData = this.left_half.encrypt(data);
			cipherData = this.right_half.decrypt(cipherData);
			cipherData = this.left_half.encrypt(cipherData);
			
			return cipherData;
		}

		public byte[] Decrypt(byte[] data)
		{
			byte[] decipherData = null;
			decipherData = this.left_half.decrypt(data);
			decipherData = this.right_half.encrypt(decipherData);
			decipherData = this.left_half.decrypt(decipherData);

			return decipherData;
		}
	}
	public static class DES{

		private SecretKey ks;

		public DES(byte[] kspec) {
			try {
				ks = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(kspec));
			} catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		public byte[] encrypt(byte[] data)
		{
			byte[] cipherData = null;
			try {
				Cipher cipher = Cipher.getInstance("DES/ /NoPadding");
				cipher.init(1, this.ks);
				cipherData = cipher.doFinal(data);
			} catch (NoSuchAlgorithmException ex) {
				Activator.getInstance().warning(null, ex);
			} catch (NoSuchPaddingException ex) {
				Activator.getInstance().warning(null, ex);
			} catch (InvalidKeyException ex) {
				Activator.getInstance().warning(null, ex);
			} catch (IllegalBlockSizeException ex) {
				Activator.getInstance().warning(null, ex);
			} catch (BadPaddingException ex) {
				Activator.getInstance().warning(null, ex);
			}
			return cipherData;
		}

		public byte[] decrypt(byte[] data)
		{
			byte[] cipherData = null;
			
			try {
				Cipher cipher = Cipher.getInstance("DES/ /NoPadding");
				cipher.init(2, this.ks);
				cipherData = cipher.doFinal(data);
			}  catch (InvalidKeyException e) {
				Activator.getInstance().warning(null, e);
			} catch (NoSuchAlgorithmException ex) {
				Activator.getInstance().warning(null, ex);
			} catch (NoSuchPaddingException ex){
				Activator.getInstance().warning(null, ex);
			} catch (IllegalBlockSizeException ex){
				Activator.getInstance().warning(null, ex);
			} catch (BadPaddingException ex){
				Activator.getInstance().warning(null, ex);
			}
			return cipherData;
		}
	}

	public static Long crc32(String value) {
		String str = value;
		byte[] bytes = str.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes, 0, bytes.length);
		return  checksum.getValue();
	}
	
	public static String crc32hex(String value) {
		String str = value;
		byte[] bytes = str.getBytes();
		Checksum checksum = new CRC32();
		checksum.update(bytes, 0, bytes.length);
		return bcdToString(intToByteArray(checksum.getValue()));
	}
	public static String crc32Unmarshall(String value) {
        HexBinaryAdapter hexAdapter = new HexBinaryAdapter();
        byte[] bytes = hexAdapter.unmarshal(value);
        Checksum checksum = new CRC32();
        checksum.update(bytes, 0, bytes.length);
        long lngChecksum = checksum.getValue();
        return StringUtils.leftPad(Integer.toHexString((int)lngChecksum).toUpperCase(),8,'0');
	}
}
