package com.github.luccaFreitass.screensound.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.github.luccaFreitass.screensound.model.Artista;
import com.github.luccaFreitass.screensound.model.Musica;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

	Optional<Artista> findByNomeContainingIgnoreCase(String nomeArtista);

	@Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nomeArtista%")
	List<Musica> musicasPorArtista(String nomeArtista);

}
