/*
Dynamo Web Services is a web service project for administering LucidDB
Copyright (C) 2010 Dynamo Business Intelligence Corporation

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the Free
Software Foundation; either version 2 of the License, or (at your option)
any later version approved by Dynamo Business Intelligence Corporation.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package com.dynamobi.ws.util;

import org.springframework.security.providers.encoding.ShaPasswordEncoder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LucidDBEncoder
    extends ShaPasswordEncoder
{

    public LucidDBEncoder(int strength)
    {
        super(strength);
    }

    private String hexHash(String str) {
      MessageDigest md = getMessageDigest();
      byte[] mdbytes = md.digest(str.getBytes());
      StringBuffer hex = new StringBuffer();
      for (int i = 0; i < mdbytes.length; i++) {
        String add = Integer.toHexString(0xFF & mdbytes[i]);
        if (add.length() == 1) hex.append(0);
        hex.append(add);
      }
      return hex.toString();
    }

    public String encodePassword(String rawPass, Object salt)
    {    
      // support a case: password is null.   
      if(rawPass.isEmpty()){
        return "";
      }

      String saltedPass = mergePasswordAndSalt(rawPass, salt, false);
      MessageDigest messageDigest = getMessageDigest();

      byte[] digest;

      try {
        digest = messageDigest.digest(saltedPass.getBytes("UTF-16LE"));
      } catch (UnsupportedEncodingException e) {
        throw new IllegalStateException("UTF-16LE not supported!");
      }

      if (getEncodeHashAsBase64()) {
        return new String(Base64.encodeBase64(digest));
      } else {
        return new String(Hex.encodeHex(digest));
      }
    }

    public boolean isPasswordValid(String encPass, String rawPass, Object salt)
    {
      String[] parts = rawPass.split(":", 2);
      // uuid, optional pw
      if (parts.length < 1)
        return false;
      else if (parts.length == 2 &&
          encodePassword(parts[1], salt).equals(encPass)) {
        //                  ^pw                  ^dbpass
        return true;
      } else { // try a UUID lookup
        return ConnectionManager.conns.containsKey(parts[0]);
      }
    }

}
