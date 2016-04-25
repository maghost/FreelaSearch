package org.freelasearchtest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringEscapeUtils;
import org.freelasearch.dtos.DtoCategoria;
import org.freelasearch.dtos.DtoUsuario;

public class Main {

	public static void main(String[] args) throws Exception {
		// enviarObjeto();
		// receberObjeto();
		usuarioPorEmail();
	}

	private static void usuarioPorEmail() throws Exception {
		URL url = new URL("http://localhost:8080/FreelaSearchServer/usuario/login");

		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);

		PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());
		pw.print("&email=matheus_maghost@hotmail.com123&senha=matheus");
		pw.close();

		ObjectInputStream ois = new ObjectInputStream(urlConnection.getInputStream());

		try {
			DtoUsuario dto = (DtoUsuario) ois.readObject();
			System.out.println("id=" + dto.id + "; nome = " + dto.nome + "; email " + dto.email);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void receberObjeto() throws Exception {
		URL url = new URL("http://localhost:8080/FreelaSearchServer/categoria/buscar");

		URLConnection urlConnection = url.openConnection();
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);

		PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());
		pw.print("&id=4");
		pw.close();

		ObjectInputStream ois = new ObjectInputStream(urlConnection.getInputStream());

		DtoCategoria dto = (DtoCategoria) ois.readObject();

		System.out.println("id=" + dto.id + "; titulo = " + dto.nome + "; descricao " + dto.descricao);
	}

	private static void enviarObjeto() throws IOException {
		DtoCategoria dto = new DtoCategoria();
		// dto.id = 6; // Se informado id, o server irá atualizar o registro
		dto.nome = "Descrição";
		dto.descricao = "Descrição da categoria Descrição";

		URL url = new URL("http://localhost:8080/FreelaSearchServer/categoria/salvar");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setDoInput(true);
		urlConnection.setDoOutput(true);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(urlConnection.getOutputStream());
			oos.writeObject(dto);
			oos.close();

			urlConnection.getResponseCode();

			System.out.println(StringEscapeUtils.unescapeHtml4(StringEscapeUtils.unescapeHtml4(urlConnection.getResponseMessage())));
		} finally {
			urlConnection.disconnect();
		}
	}
}
