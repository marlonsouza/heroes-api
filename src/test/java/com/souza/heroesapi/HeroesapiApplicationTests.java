package com.souza.heroesapi;

import com.souza.heroesapi.constants.HeroesConstant;
import com.souza.heroesapi.document.Heroes;
import com.souza.heroesapi.repository.HeroesRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@SpringBootTest
class HeroesapiApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	HeroesRepository heroesRepository;

	@Test
	public void getOneHero() {
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"), 10)
			.exchange()
			.expectStatus().isOk()
			.expectBody();
	}

	@Test
	public void getOneHeroNotFound(){
		webTestClient.get().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"),20)
				.exchange()
				.expectStatus()
				.isNotFound();
	}

	@Test
	public void saveHero(){
		Heroes heroes = new Heroes();
		heroes.setId("99999");
		heroes.setFilms(2);
		heroes.setName("Batman");
		heroes.setUniverse("DC");

		webTestClient.post().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL)
				.bodyValue(heroes)
				.exchange()
				.expectStatus()
				.isCreated()
				.expectBody(Heroes.class);
	}

	@Test
	public void deleteHero(){
		webTestClient.delete().uri(HeroesConstant.HEROES_ENDPOINT_LOCAL.concat("/{id}"), 99999)
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Void.class);
	}

}
