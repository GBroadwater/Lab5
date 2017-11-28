package pkgCore;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.HashMap;

import org.junit.Test;

import pkgEnum.eBlackJackResult;
import pkgEnum.eRank;
import pkgEnum.eSuit;
import pkgException.HandException;

public class GamePlayBlackJackTest {

	@Test
	public void TestPlayerWinning() throws HandException {

		HashMap<UUID, Player> Players = new HashMap<UUID, Player>();
		Player P1 = new Player("Geoffrey", 92097);
		Players.put(P1.getPlayerID(), P1);

		GamePlayBlackJack GBJ = new GamePlayBlackJack(Players, new Deck());

		HandBlackJack hp = new HandBlackJack();
		hp.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hp.AddCard((new Card(eSuit.HEARTS, eRank.EIGHT)));

		HashMap<GamePlayerHand, Hand> Hands = new HashMap<GamePlayerHand, Hand>();

		Hands.put(GBJ.getGPH().get(0), hp);
		GBJ.setHmGameHands(Hands);

		HandBlackJack hd = new HandBlackJack();
		hd.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hd.AddCard((new Card(eSuit.HEARTS, eRank.SEVEN)));

		GBJ.sethDealer(hd);

		GBJ.ScoreGame(GBJ.getGPH().get(0));
		assertEquals(eBlackJackResult.WIN, GBJ.getHmGameHands().get(GBJ.getGPH().get(0)).isbWinner());

	}

	@Test
	public void TestPlayerLosing() throws HandException {

		HashMap<UUID, Player> Players = new HashMap<UUID, Player>();
		Player P1 = new Player("Geoffrey", 92097);
		Players.put(P1.getPlayerID(), P1);

		GamePlayBlackJack GBJ = new GamePlayBlackJack(Players, new Deck());

		HandBlackJack hp = new HandBlackJack();
		hp.AddCard((new Card(eSuit.CLUBS, eRank.ACE)));
		hp.AddCard((new Card(eSuit.HEARTS, eRank.SIX)));

		HashMap<GamePlayerHand, Hand> Hands = new HashMap<GamePlayerHand, Hand>();

		Hands.put(GBJ.getGPH().get(0), hp);
		GBJ.setHmGameHands(Hands);

		HandBlackJack hd = new HandBlackJack();
		hd.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hd.AddCard((new Card(eSuit.HEARTS, eRank.NINE)));

		GBJ.sethDealer(hd);

		GBJ.ScoreGame(GBJ.getGPH().get(0));
		assertEquals(eBlackJackResult.LOSE, GBJ.getHmGameHands().get(GBJ.getGPH().get(0)).isbWinner());


	}

	@Test
	public void TestPlayerPush() throws HandException {

		HashMap<UUID, Player> Players = new HashMap<UUID, Player>();
		Player P1 = new Player("Geoffrey", 92097);
		Players.put(P1.getPlayerID(), P1);

		GamePlayBlackJack GBJ = new GamePlayBlackJack(Players, new Deck());

		HandBlackJack hp = new HandBlackJack();
		hp.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hp.AddCard((new Card(eSuit.HEARTS, eRank.EIGHT)));

		HashMap<GamePlayerHand, Hand> Hands = new HashMap<GamePlayerHand, Hand>();

		Hands.put(GBJ.getGPH().get(0), hp);
		GBJ.setHmGameHands(Hands);

		HandBlackJack hd = new HandBlackJack();
		hd.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hd.AddCard((new Card(eSuit.HEARTS, eRank.EIGHT)));

		GBJ.sethDealer(hd);

		GBJ.ScoreGame(GBJ.getGPH().get(0));
		assertEquals(eBlackJackResult.TIE, GBJ.getHmGameHands().get(GBJ.getGPH().get(0)).isbWinner());


	}

	@Test
	public void TestTwoPlayersWinning() throws HandException {

		HashMap<UUID, Player> Players = new HashMap<UUID, Player>();
		Player P1 = new Player("Geoffrey", 92097);
		Players.put(P1.getPlayerID(), P1);
		Player P2 = new Player("Geoffrey", 92098);
		Players.put(P2.getPlayerID(), P1);
		

		GamePlayBlackJack GBJ = new GamePlayBlackJack(Players, new Deck());

		HandBlackJack hp1 = new HandBlackJack();
		hp1.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hp1.AddCard((new Card(eSuit.HEARTS, eRank.TWO)));
		HandBlackJack hp2 = new HandBlackJack();
		hp2.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hp2.AddCard((new Card(eSuit.HEARTS, eRank.KING)));

		HashMap<GamePlayerHand, Hand> Hands = new HashMap<GamePlayerHand, Hand>();

		Hands.put(GBJ.getGPH().get(0), hp1);
		Hands.put(GBJ.getGPH().get(1), hp2);
		GBJ.setHmGameHands(Hands);

		HandBlackJack hd = new HandBlackJack();
		hd.AddCard((new Card(eSuit.CLUBS, eRank.TEN)));
		hd.AddCard((new Card(eSuit.HEARTS, eRank.JACK)));
		hd.AddCard((new Card(eSuit.HEARTS, eRank.FIVE)));
		
		GBJ.sethDealer(hd);

		GBJ.ScoreGame(GBJ.getGPH().get(0));
		assertEquals(eBlackJackResult.WIN, GBJ.getHmGameHands().get(GBJ.getGPH().get(0)).isbWinner());
		assertEquals(eBlackJackResult.WIN, GBJ.getHmGameHands().get(GBJ.getGPH().get(1)).isbWinner());

	}
}
