package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shriekgeist")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class Shriekgeist extends Card
{
	public static final class ShriekgeistAbility1 extends EventTriggeredAbility
	{
		public ShriekgeistAbility1(GameState state)
		{
			super(state, "Whenever Shriekgeist deals combat damage to a player, that player puts the top two cards of his or her library into his or her graveyard.");
			this.addPattern(whenThisDealsCombatDamageToAPlayer());
			SetGenerator thatPlayer = TakerOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(millCards(thatPlayer, 2, "That player puts the top two cards of his or her library into his or her graveyard."));
		}
	}

	public Shriekgeist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Whenever Shriekgeist deals combat damage to a player, that player
		// puts the top two cards of his or her library into his or her
		// graveyard.
		this.addAbility(new ShriekgeistAbility1(state));
	}
}
