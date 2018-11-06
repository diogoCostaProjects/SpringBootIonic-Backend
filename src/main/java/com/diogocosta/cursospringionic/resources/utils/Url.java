package com.diogocosta.cursospringionic.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//Classe de utilitário para pegar uma lista passada na URL em String e montar um array de categorias 
public class Url {

	public static List<Integer> decodeintList(String s) {

		// COM LAMBDA
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}

	public static String decodeParam(String s) { // Decodifica caso o usuário passe nome com espaço no parâmetro
		try {
			return URLDecoder.decode(s, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) {
			return "";
		}

	}

}

// SEM LAMBDA

/*
 * String[] vet = s.split(","); List<Integer> list = new ArrayList<>(); for (int
 * i = 0; i < vet.length; i++){ list.add(Integer.parseInt(vet[i])); } return
 * list;
 */