package com.yofujitsu.rsocket;

import com.yofujitsu.rsocket.controller.AgentController;
import com.yofujitsu.rsocket.entity.Agent;
import com.yofujitsu.rsocket.entity.Role;
import com.yofujitsu.rsocket.repository.AgentRepository;
import com.yofujitsu.rsocket.service.AgentService;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RsocketApplicationTests {

	@Mock
	private AgentService agentService;

	@Mock
	private AgentRepository agentRepository;

	@InjectMocks
	private AgentController agentController;

	@Mock
	private RSocketRequester requester;

	@BeforeEach
	public void connect() {
		requester = RSocketRequester.builder()
				.rsocketStrategies(builder -> builder.decoder(new
						Jackson2JsonDecoder()))
				.rsocketStrategies(builder -> builder.encoder(new
						Jackson2JsonEncoder()))
				.rsocketConnector(connector -> connector
						.payloadDecoder(PayloadDecoder.ZERO_COPY))
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON)
				.tcp("localhost", 8088);
	}

	@AfterEach
	public void disconnect() {
		requester.dispose();
	}

	@Test
	void testFindByName() {
		// Arrange
		String name = "test-agent";
		Agent agent = new Agent(UUID.randomUUID(), name, Role.SENTINEL, "test");
		agentRepository.save(agent);

		// Act
		Mono<Agent> result = requester.route("getAgent")
				.data(agent.getName())
				.retrieveMono(Agent.class);


		// Assert
		StepVerifier.create(result)
				.expectComplete()
				.verify();
	}

	@Test
	void testCreate() {
		// Arrange
		Agent agent = new Agent(UUID.randomUUID(), "test-agent", Role.SENTINEL, "test");
		agentRepository.save(agent);

		// Act
		Mono<Agent> result = requester.route("createAgent")
				.data(agent)
				.retrieveMono(Agent.class);


		Agent resultAgent = result.block();
		assertNotNull(resultAgent);
	}

	@Test
	void testUpdate() {
		// Arrange
		Agent agent = new Agent(UUID.randomUUID(), "test-agent", Role.SENTINEL, "test");
		agentRepository.save(agent);

		// Act
		Mono<Agent> result = requester.route("updateAgent")
				.data(agent)
				.retrieveMono(Agent.class);


		// Assert
		Agent resultAgent = result.block();
		assertNotNull(resultAgent);
	}

	@Test
	void testDelete() {
		// Arrange
		UUID id = UUID.randomUUID();
		agentRepository.deleteById(id);

		// Act
		Mono<Agent> result = requester.route("deleteAgent")
				.data(id)
				.retrieveMono(Agent.class);


		// Assert
		StepVerifier.create(result)
				.expectComplete()
				.verify();
	}

	@Test
	public void testGetCats() {
		Flux<Agent> result = requester.route("getAll")
				.retrieveFlux(Agent.class);
		assertNotNull(result.blockFirst());
	}

}
