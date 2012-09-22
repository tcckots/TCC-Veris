package com.kots.sidim.web.util;


public class Biblio {

	public static void tratarErro(String origem, Exception e) {
		System.out.println(origem + " - Erro: " + e.toString());
	}

	
   public static boolean validaEndEmail(String pEndereco, boolean pRequerido) {
       if (strVazio(pEndereco)) {
           if (!pRequerido) {
               return true;
           } else {
               return false;
           }
       }
       // comentado para permitir varios destinatarios
       //if (pEndereco.length() > 50) {
       //    return false;
       //}
       if (!pEndereco.contains("@")) {
           return false;
       }
       if (!pEndereco.contains(".")) {
           return false;
       }
       return true;
   }
   
   

   public static Boolean strVazio(String pStr) {
       if (pStr == null) {
           return true;
       }
       Boolean wNaoVazio = false;
       int wPos = -1, wTam;
       wTam = pStr.length();
       if (wTam > 0) {
           do {
               wPos++;
               if (pStr.charAt(wPos) != ' ') {
                   wNaoVazio = true;
               }
           } while (!(wPos >= (wTam - 1)) && !(wNaoVazio));
       }
       return !wNaoVazio;
   }
	

	

}
