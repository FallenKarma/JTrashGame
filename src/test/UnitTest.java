package test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suits;

public class UnitTest {
	
	@Test
	void cardToString() {
		Card card = new Card(Rank.ACE, Suits.spades);
		
		assertEquals(card.toString(), "ACE_of_spades");
	}
}
