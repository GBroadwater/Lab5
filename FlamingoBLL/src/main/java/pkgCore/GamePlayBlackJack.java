package pkgCore;
/*
Broadwater, Geoffrey
Hewitt, Anthony
Moglia, David
Walker, Julia
All members participated equally
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import pkgException.DeckException;
import pkgException.HandException;
import pkgEnum.eBlackJackResult;
import pkgEnum.eGameType;

public class GamePlayBlackJack extends GamePlay {

	private Player pDealer = new Player("Dealer", 0);
	private Hand hDealer = new HandBlackJack();
	private ArrayList<GamePlayerHand> GPH = new ArrayList<GamePlayerHand>();

	public GamePlayBlackJack(HashMap<UUID, Player> hmTablePlayers, Deck dGameDeck) {

		super(eGameType.BLACKJACK, hmTablePlayers, dGameDeck);

		Iterator it = hmTablePlayers.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Player p = (Player) pair.getValue();
			Hand h = new HandBlackJack();
			GamePlayerHand GPH = new GamePlayerHand(this.getGameID(), p.getPlayerID(), h.getHandID());
			this.GPH.add(GPH);
			this.putHandToGame(GPH, h);
		}
	}

	public ArrayList<GamePlayerHand> getGPH() {
		return GPH;
	}

	@Override
	protected Card Draw(GamePlayerHand GPH) throws DeckException, HandException {

		Card c = null;

		if (bCanPlayerDraw(GPH)) {
			Hand h = this.gethmGameHand(GPH);
			c = h.Draw(this.getdGameDeck());

			h.AddCard(c);

			this.putHandToGame(GPH, h);

		}
		return c;
	}

	private boolean bCanPlayerDraw(GamePlayerHand GPH) throws HandException {
		boolean bCanPlayerDraw = false;

		Hand h = this.gethmGameHand(GPH);

		HandScoreBlackJack HSB = (HandScoreBlackJack) h.ScoreHand();

		for (int i : HSB.getNumericScores()) {
			if (i <= 21) {
				bCanPlayerDraw = true;
				break;
			}
		}
		return bCanPlayerDraw;
	}

	public boolean bDoesDealerHaveToDraw() throws HandException {
		boolean bDoesDealerHaveToDraw = false;

		HandScoreBlackJack HSB = (HandScoreBlackJack) hDealer.ScoreHand();

		for (int i : HSB.getNumericScores()) {
			if (i <= 16)
				bDoesDealerHaveToDraw = true;
			else if ((i <= 21) && (i >= 17))
				bDoesDealerHaveToDraw = false;
		}
		return bDoesDealerHaveToDraw;
	}

	public void ScoreGame(GamePlayerHand GPH) throws HandException {

		HandScoreBlackJack DealerHandScore = (HandScoreBlackJack) hDealer.ScoreHand();
		Iterator it = this.getHmGameHands().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			GamePlayerHand sGPH = (GamePlayerHand) pair.getKey();
			HandBlackJack sH = (HandBlackJack) pair.getValue();

			HandScoreBlackJack PlayerHandScore = (HandScoreBlackJack) sH.ScoreHand();

			eBlackJackResult bIsHandWinner = null;

			for (int pScore : PlayerHandScore.getNumericScores()) {

				for (int dScore : DealerHandScore.getNumericScores()) {

					if (pScore <= 21) {
						if (dScore <= 21) {

							if (pScore > dScore) {
								bIsHandWinner = eBlackJackResult.WIN;
							}
							if (pScore < dScore) {
								bIsHandWinner = eBlackJackResult.LOSE;
							}
							if (pScore == dScore) {
								bIsHandWinner = eBlackJackResult.TIE;
							}
						} else if ((dScore > 21) && (bIsHandWinner == null))
							bIsHandWinner = eBlackJackResult.WIN;
					} else if ((pScore > 21) && (bIsHandWinner == null)) {
						bIsHandWinner = eBlackJackResult.LOSE;
					}
				}
			}
			sH.setbWinner(bIsHandWinner);
			this.putHandToGame(sGPH, sH);
		}
	}

	public Hand gethDealer() {
		return hDealer;
	}

	public void sethDealer(Hand hDealer) {
		this.hDealer = hDealer;
	}

}
