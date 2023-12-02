package test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Card;
import model.DeckOfCards;
import model.Game;
import model.Player;
import model.Rank;
import model.Suits;
import model.User;

public class UnitTest {
	
	@Test
	void cardToString() {
		Card card = new Card(Rank.ace, Suits.spades);
		
		assertEquals(card.toString(), "ace_of_spades");
	}
	
	@Test
	void testSwitchCardsNumber() {
		Player player = new Player();
		Card testCard = new Card(Rank.four, Suits.hearts);
		player.setCardInHand(testCard);
		DeckOfCards deck = new DeckOfCards(1);
		
		player.setTableCards(deck.drawCards(player.getTableCardsNumber()).toArray(player.getTableCards()));
		player.switchTableCard();
		
		assertEquals(testCard, player.getTableCards()[3]);
	}
	
	
}
