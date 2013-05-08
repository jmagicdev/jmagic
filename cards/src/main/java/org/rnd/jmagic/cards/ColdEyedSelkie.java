package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cold-Eyed Selkie")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.ROGUE})
@ManaCost("1(G/U)(G/U)")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class ColdEyedSelkie extends Card
{
	public static final class ColdEyedSelkieAbility1 extends EventTriggeredAbility
	{
		public ColdEyedSelkieAbility1(GameState state)
		{
			super(state, "Whenever Cold-Eyed Selkie deals combat damage to a player, you may draw that many cards.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			SetGenerator thatMany = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(youMay(drawCards(You.instance(), thatMany, "Draw that many cards."), "You may draw that many cards."));
		}
	}

	public ColdEyedSelkie(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Islandwalk
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Whenever Cold-Eyed Selkie deals combat damage to a player, you may
		// draw that many cards.
		this.addAbility(new ColdEyedSelkieAbility1(state));
	}
}
