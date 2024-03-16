package com.github.luccaFreitass.screensound.pricipal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.github.luccaFreitass.screensound.model.Artista;
import com.github.luccaFreitass.screensound.model.Musica;
import com.github.luccaFreitass.screensound.repository.ArtistaRepository;

public class Principal {

	private Scanner in = new Scanner(System.in);

	private ArtistaRepository repositorio;

	private List<Artista> artistas = new ArrayList<>();

	public Principal(ArtistaRepository repositorio) {
		this.repositorio = repositorio;
	}

	public void exibeMenu() {
		int opcao = -1;

		while (opcao != 0) {
			String menu = """
					---> ScreenSound <---
					1 - Cadastrar artistas
					2 - Cadastrar músicas
					3 - Listar músicas
					4 - Buscar músicas por artistas

					0 - Sair
					""";

			System.out.println(menu);
			opcao = in.nextInt();
			in.nextLine();
			switch (opcao) {
			case 1:
				cadastrarArtista();
				break;
			case 2:
				cadastrarMusica();
				break;
			case 3:
				listarMusicas();
				break;
			case 4:
				buscarMusicasPorArtista();
				break;

			default:
				System.out.println("Opção inválida.");
				break;
			}
		}
	}

	private void buscarMusicasPorArtista() {
		listarArtistas();

		System.out.println("Fale o artista para filtrar as musicas: ");
		String nomeArtista = in.nextLine();

		List<Musica> musicas = repositorio.musicasPorArtista(nomeArtista);
		musicas.forEach(System.out::println);

	}

	private void listarMusicas() {
		artistas = repositorio.findAll();
		artistas.forEach(System.out::println);
	}

	private void cadastrarMusica() {
		listarArtistas();
		System.out.println("De qual artista é essa musica? ");
		String nomeArtista = in.nextLine();

		Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

		if (artista.isPresent()) {
			System.out.println("Digite o nome da musica: ");
			String nomeMusica = in.nextLine();
			Musica musica = new Musica(nomeMusica);
			musica.setArtista(artista.get());
			artista.get().getMusicas().add(musica);
			repositorio.save(artista.get());
		} else
			System.out.println("Artista não encontrado");
	}

	private void listarArtistas() {
		artistas = repositorio.findAll();
		artistas.forEach(System.out::println);
	}

	private void cadastrarArtista() {

		String opcao2 = "";

		do {
			System.out.println("Informe o nome desse artista:");
			String nome = in.nextLine();

			System.out.println("Informe o tipo desse artista: (solo, dupla, banda)");
			String tipo = in.nextLine();

			Artista artista = new Artista(nome, tipo);
			repositorio.save(artista);

			System.out.println("Cadastrar outro artista? (S/N)");
			opcao2 = in.nextLine();
		} while (opcao2.equalsIgnoreCase("s"));

		in.close();
	}

}
