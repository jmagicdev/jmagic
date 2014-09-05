package org.rnd.jmagic.cards;

import static org.junit.Assert.*;

import org.junit.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.gameTypes.*;
import org.rnd.jmagic.testing.*;

public class QuestforPureFlameTest extends JUnitTest
{
	@Test
	public void questForPureFlame()
	{
		this.addDeck(QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, MoggFanatic.class);
		this.addDeck(Shock.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, QuestforPureFlame.class, MoggFanatic.class);
		this.startGame(new Open());

		this.respondWith(this.getPlayer(0));
		this.keep();
		this.keep();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(MoggFanatic.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		this.goToPhase(Phase.PhaseType.PRECOMBAT_MAIN);
		this.respondWith(this.getSpellAction(QuestforPureFlame.class));
		this.addMana("R");
		this.donePlayingManaAbilities();
		this.pass();
		this.pass();

		// mogg fan pings 1 (will trigger quest)
		this.respondWith(this.getAbilityAction(MoggFanatic.SacPing.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.pass();

		// 1 shocks himself (will NOT trigger quest)
		this.respondWith(this.getSpellAction(Shock.class));
		this.respondWith(this.getTarget(this.player(1)));
		this.addMana("R");
		this.donePlayingManaAbilities();

		assertEquals(2, this.game.actualState.stack().objects.size());
		assertEquals(20, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(18, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();

		assertEquals(1, this.game.actualState.stack().objects.size());
		assertEquals(17, this.player(1).lifeTotal);
		assertEquals(0, this.game.actualState.battlefield().objects.get(0).counters.size());

		this.pass();
		this.pass();
		this.respondWith(Answer.YES);

		assertEquals(0, this.game.actualState.stack().objects.size());
		assertEquals(17, this.player(1).lifeTotal);
		assertEquals(1, this.game.actualState.battlefield().objects.get(0).counters.size());
	}
}